package com.me.data

import com.me.data.storage.Storage
import com.me.model.DayRecord
import model.HashTag
import com.me.model.Mention
import com.me.model.MonthRecord
import com.me.model.MoodRecord
import com.me.model.WeekRecord
import korlibs.time.DateTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val storage: Storage) {
    private val scope = CoroutineScope(Dispatchers.IO)

    val records = MutableStateFlow<List<MoodRecord>>(emptyList())
    val tags = MutableStateFlow<MutableMap<String, HashTag>>(mutableMapOf())
    val mentions = MutableStateFlow<MutableMap<String, Mention>>(mutableMapOf())

    val h = Heap()
    val todayRecords: StateFlow<List<MoodRecord>> = records
        .map { todayRecords(it) }
        .stateIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )

    val days: StateFlow<List<DayRecord>> = records
        .map { generateDays(it) }
        .stateIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )

    val weeks: StateFlow<List<WeekRecord>> = records
        .map { generateWeeks(it) }
        .stateIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )

    val months: StateFlow<List<MonthRecord>> = records
        .map { generateMonths(it) }
        .stateIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )

    init {
        scope.launch {
            storage.allRecords()
                .let { records.emit(it) }
        }
        scope.launch {
            storage.tags().let { storageTags ->
                mutableMapOf<String, HashTag>().let { map ->
                    storageTags.forEach { map[it.value] = it }
                    tags.emit(map)
                }
            }
        }
        scope.launch {
            storage.mentions().let { storageMentions ->
                mutableMapOf<String, Mention>().let { map ->
                    storageMentions.forEach { map[it.value] = it }
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
        if (javaClass != other?.javaClass) return false

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
    .fold(initial = mutableMapOf<DateTime, MutableList<MoodRecord>>()) { acc, moodRecord ->
        val monthsStart = moodRecord.date.local.startOfMonth

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
            start = monthsRecords.key.date,
            end = monthsRecords.key.endOfMonth.date,
            records = monthsRecords.value.toList()

        ).let { acc.add(it) }
        acc
    }

private fun generateWeeks(records: List<MoodRecord>): List<WeekRecord> = records
    .fold(initial = mutableMapOf<DateTime, MutableList<MoodRecord>>()) { acc, moodRecord ->
        val weekStart = moodRecord.date.local.startOfIsoWeek

        if (!acc.containsKey(weekStart)) {
            acc[weekStart] = mutableListOf()
        }
        acc[weekStart]!! += moodRecord
        acc
    }.entries
    .foldIndexed(initial = mutableListOf()) { index, acc, weekRecords ->
        WeekRecord(
            index = index,
            start = weekRecords.key.date,
            end = weekRecords.key.endOfIsoWeek.date,
            records = weekRecords.value.toList()
        ).let { acc.add(it) }
        acc
    }

private fun generateDays(records: List<MoodRecord>): List<DayRecord> = records
    .fold(
        initial = mutableMapOf<DateTime, MutableList<MoodRecord>>()
    ) { acc, moodRecord ->
        val dayStart = moodRecord.date.local.startOfDay

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
            start = dayRecords.key.date,
            records = dayRecords.value.toList()
        ).let { acc.add(it) }
        acc
    }

private fun todayRecords(records: List<MoodRecord>) = records.filter {
    it.date.local.date == lazy { DateTime.nowLocal().local.date }.value
}
