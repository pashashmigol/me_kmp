package data

import RepeatableTest
import data.utils.now
import kotlinx.datetime.LocalDateTime
import model.MoodRecord
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.seconds
import data.storage.StorageStub
import data.utils.minus
import data.utils.startOfDay
import data.utils.date
import data.utils.dateTime
import data.utils.plus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import model.HashTag
import model.Mention
import model.MonthRecord
import model.WeekRecord
import kotlin.test.assertContentEquals
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds

class RepositoryTest : RepeatableTest() {
    private val now: LocalDateTime = now()
    private lateinit var repository: Repository

    override fun beforeEach() {
        repository = Repository(
            storage = StorageStub(),
            dispatcher = Dispatchers.Unconfined
        )
    }

    @Test
    fun `one record added adds also one day week and month`() = runTest(times = 100) {
        repository.addRecord(MoodRecord(now()))
        testScheduler.advanceUntilIdle()

        checkNextValueSize(1, repository.todayRecords)
        checkNextValueSize(1, repository.days)
        checkNextValueSize(1, repository.weeks)
        checkNextValueSize(1, repository.months)
    }

    @Test
    fun getTodayRecords() = runTest(times = 100) {
        val todayRecords = listOf(
            MoodRecord(now, "1"),
            MoodRecord(now, "2"),
        )
        val yesterdaysRecords = listOf(
            MoodRecord(now.startOfDay - 3.hours, "1"),
            MoodRecord(now.startOfDay - 3.hours, "2"),
        )
        repository.addRecords(todayRecords + yesterdaysRecords)
        testScheduler.advanceUntilIdle()

        checkNextValues(todayRecords + yesterdaysRecords, repository.records)
        checkNextValues(todayRecords, repository.todayRecords)
    }

    @Test
    fun getWeeks() = runTest(times = 100) {
        val record1 = MoodRecord(date = dateTime(year = 2005, month = 1, dayOfMonth = 10))
        val record2 = MoodRecord(date = dateTime(year = 2005, month = 1, dayOfMonth = 20))
        val records = listOf(record1, record2)

        val weeks = listOf(
            WeekRecord(
                index = 0,
                start = date(year = 2005, month = 1, dayOfMonth = 10),
                end = date(year = 2005, month = 1, dayOfMonth = 16),
                records = listOf(record1)
            ),
            WeekRecord(
                index = 1,
                start = date(year = 2005, month = 1, dayOfMonth = 17),
                end = date(year = 2005, month = 1, dayOfMonth = 23),
                records = listOf(record2)
            )
        )
        repository.addRecords(records)
        testScheduler.advanceUntilIdle()

        checkNextValues(weeks, repository.weeks)
    }

    @Test
    fun getMonths() = runTest(times = 100) {
        val record1 = MoodRecord(date = dateTime(year = 2005, month = 1, dayOfMonth = 10))
        val record2 = MoodRecord(date = dateTime(year = 2005, month = 3, dayOfMonth = 16))

        repository.addRecords(listOf(record1, record2))
        testScheduler.advanceUntilIdle()

        val months = listOf(
            MonthRecord(
                index = 0,
                start = date(2005, 1, 1),
                end = date(2005, 1, 31),
                records = listOf(record1)
            ),
            MonthRecord(
                index = 1,
                start = date(2005, 3, 1),
                end = date(2005, 3, 31),
                records = listOf(record2)
            )
        )
        checkNextValues(months, repository.months)
    }

    @Test
    fun `check that all records are properly split between weeks`() =
        runTest(times = 100, timeout = 1.seconds) {
            val records = listOf(
                MoodRecord(date = dateTime(2005, 1, 15)),
                MoodRecord(date = dateTime(2005, 1, 15)),
                MoodRecord(date = dateTime(2005, 1, 15)),
            )
            repository.addRecords(records)
            testScheduler.advanceUntilIdle()

            val weeks = listOf(
                WeekRecord(
                    index = 0,
                    start = date(2005, 1, 10),
                    end = date(2005, 1, 16),
                    records = records
                )
            )
            checkNextValues(weeks, repository.weeks)
        }

    @Test
    fun addMention() = runTest(times = 100, timeout = 10.seconds) {
        val mention1 = Mention(
            value = "mention 1",
            lastUsed = now() - 1.milliseconds
        )
        repository.addMention(mention1)
        waitForTags(listOf(mention1), repository.mentions)

        val mention2 = Mention(
            value = "mention 2",
            lastUsed = now()
        )
        repository.addMention(mention2)
        waitForTags(listOf(mention1, mention2), repository.mentions)

        val mention3 = mention1.copy(lastUsed = mention1.lastUsed + 1.milliseconds)
        repository.addMention(mention3)
        waitForTags(expected = listOf(mention3, mention2), flow = repository.mentions)
    }

    @Test
    fun addTag() = runTest(times = 100) {
        val hashTag1 = HashTag(value = "tag 1", lastUsed = now())
        repository.addTag(hashTag1)
        waitForTags(listOf(hashTag1), repository.tags)

        val hashTag2 = HashTag(value = "tag 2", lastUsed = now() + 10.milliseconds)
        repository.addTag(hashTag2)
        waitForTags(listOf(hashTag1, hashTag2), repository.tags)

        val hashTag3 = hashTag1.copy(lastUsed = hashTag1.lastUsed + 20.milliseconds)
        repository.addTag(hashTag3)
        waitForTags(listOf(hashTag3, hashTag2), repository.tags)
    }
}

private suspend inline fun <reified T> waitForTags(
    expected: List<T>,
    flow: StateFlow<MutableMap<String, T>>
) {
    var got: Array<T>? = null
    while (!got.contentEquals(expected.toTypedArray())) {
        got = flow.first().values.toList().toTypedArray()
        delay(100)
    }
    assertContentEquals(expected, got?.toList())
}

private suspend inline fun <reified T> checkNextValues(
    expected: List<T>,
    flow: StateFlow<List<T>>
) {
    var got: Array<T>? = null
    while (!got.contentEquals(expected.toTypedArray())) {
        got = flow.first().toTypedArray()
        delay(100)
    }
    assertContentEquals(expected, got?.toList())
}

private suspend fun <T> checkNextValueSize(
    expectedSize: Int,
    flow: StateFlow<List<T>>
) {
    var got: Int? = null
    while (got != (expectedSize)) {
        got = flow.first().size
        delay(100)
    }
    assertEquals(expectedSize, got)
}