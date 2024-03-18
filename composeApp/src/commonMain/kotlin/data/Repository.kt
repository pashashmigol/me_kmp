package data

import com.me.diary.BuildConfig.GENERATE_TEST_RECORDS
import model.HashTag
import data.storage.Storage
import data.utils.endOfIsoWeek
import data.utils.endOfMonth
import data.utils.now
import data.utils.startOfDay
import data.utils.startOfIsoWeek
import data.utils.startOfMonth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import model.DayRecord
import model.Mention
import model.MonthRecord
import model.MoodRecord
import model.WeekRecord

class Repository(val storage: Storage) {
    private val scope = CoroutineScope(Dispatchers.IO)

    val records = MutableStateFlow<List<MoodRecord>>(emptyList())
    val tags = MutableStateFlow<MutableMap<String, HashTag>>(mutableMapOf())
    val mentions = MutableStateFlow<MutableMap<String, Mention>>(mutableMapOf())

    val todayRecords: StateFlow<List<MoodRecord>> = records
        .map { todayRecords(it) }
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    val days: StateFlow<List<DayRecord>> = records
        .map { generateDays(it) }
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    val weeks: StateFlow<List<WeekRecord>> = records
        .map { generateWeeks(it) }
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    val months: StateFlow<List<MonthRecord>> = records
        .map { generateMonths(it) }
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    init {
        println("### ${Repository::class.simpleName}; init()")
        scope.launch {
            val testRecords: List<MoodRecord> = if (GENERATE_TEST_RECORDS) {
                generateSequence { MoodRecord.random() }.take(1000).toList()
            } else {
                emptyList()
            }
            (storage.allRecords() + testRecords).let {
                records.emit(it)
            }
        }
        scope.launch {
            storage.tags().let { storageTags ->
                mutableMapOf<String, HashTag>().let { map ->
                    storageTags.forEach { map[it.value] = it }
                    println("### ${Repository::class.simpleName};  storageTags: $storageTags")
                    println("### ${Repository::class.simpleName};  tags: $map")
                    tags.emit(map)
                }
            }
        }
        scope.launch {
            storage.mentions().let { storageMentions ->
                mutableMapOf<String, Mention>().let { map ->
                    storageMentions.forEach { map[it.value] = it }
                    println("### ${Repository::class.simpleName};  storageMentions: $storageMentions")
                    println("### ${Repository::class.simpleName};  mentions: $map")
                    mentions.emit(map)
                }
            }
        }
    }

    fun addRecord(record: MoodRecord) {
        records.update { it + record }
        scope.launch {
            storage.addRecord(record)
        }
    }

    fun addRecords(recordList: List<MoodRecord>) {
        records.update { it + recordList }
        scope.launch {
            recordList.forEach { record ->
                storage.addRecord(record)
            }
        }
    }

    fun addMention(mention: Mention) {
        mentions.update {
            mentions.value[mention.value] = mention
            mentions.value
        }
        scope.launch {
            storage.addMention(mention)
        }
    }

    fun addTag(tag: HashTag) {
        tags.update {
            tags.value[tag.value] = tag
            tags.value
        }
        scope.launch {
            storage.addTag(tag)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Repository

        if (storage != other.storage) return false
        if (scope != other.scope) return false
        if (records != other.records) return false
        if (tags != other.tags) return false
        if (mentions != other.mentions) return false
        if (todayRecords != other.todayRecords) return false
        if (days != other.days) return false
        if (weeks != other.weeks) return false
        return months == other.months
    }

    override fun hashCode(): Int {
        var result = storage.hashCode()
        result = 31 * result + scope.hashCode()
        result = 31 * result + records.hashCode()
        result = 31 * result + tags.hashCode()
        result = 31 * result + mentions.hashCode()
        result = 31 * result + todayRecords.hashCode()
        result = 31 * result + days.hashCode()
        result = 31 * result + weeks.hashCode()
        result = 31 * result + months.hashCode()
        return result
    }
}

private fun generateMonths(records: List<MoodRecord>): List<MonthRecord> = records
    .fold(initial = mutableMapOf<LocalDateTime, MutableList<MoodRecord>>()) { acc, moodRecord ->
        val monthsStart = moodRecord.date.startOfMonth

        if (!acc.containsKey(monthsStart)) {
            acc[monthsStart] = mutableListOf()
        }
        acc[monthsStart]!! += moodRecord
        acc
    }
    .entries
    .foldIndexed(initial = mutableListOf()) { index, acc, monthsRecords ->
        MonthRecord(
            index = index,
            start = monthsRecords.key,
            end = monthsRecords.key.endOfMonth,
            records = monthsRecords.value.toList()

        ).let { acc.add(it) }
        acc
    }

private fun generateWeeks(records: List<MoodRecord>): List<WeekRecord> = records
    .fold(initial = mutableMapOf<LocalDateTime, MutableList<MoodRecord>>()) { acc, moodRecord ->
        val weekStart = moodRecord.date.startOfIsoWeek

        if (!acc.containsKey(weekStart)) {
            acc[weekStart] = mutableListOf()
        }
        acc[weekStart]!! += moodRecord
        acc
    }.entries
    .foldIndexed(initial = mutableListOf()) { index, acc, weekRecords ->
        WeekRecord(
            index = index,
            start = weekRecords.key,
            end = weekRecords.key.endOfIsoWeek,
            records = weekRecords.value.toList()
        ).let { acc.add(it) }
        acc
    }

private fun generateDays(records: List<MoodRecord>): List<DayRecord> = records
    .fold(
        initial = mutableMapOf<LocalDateTime, MutableList<MoodRecord>>()
    ) { acc, moodRecord ->
        val dayStart = moodRecord.date.startOfDay

        if (!acc.containsKey(dayStart)) {
            acc[dayStart] = mutableListOf()
        }
        acc[dayStart]!! += moodRecord
        acc
    }.entries.foldIndexed(
        initial = mutableListOf()
    ) { index, acc, dayRecords ->
        DayRecord(
            index = index,
            start = dayRecords.key,
            records = dayRecords.value.toList()
        ).let { acc.add(it) }
        acc
    }

private fun todayRecords(records: List<MoodRecord>) = records.filter {
    it.date.date == lazy { now().date }.value
}
