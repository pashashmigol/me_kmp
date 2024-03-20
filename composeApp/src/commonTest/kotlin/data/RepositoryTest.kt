package data

import data.utils.now
import kotlinx.datetime.LocalDateTime
import model.MoodRecord
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.seconds
import app.cash.turbine.test
import data.storage.StorageStub
import data.utils.minus
import data.utils.startOfDay
import kotlinx.coroutines.test.runTest
import data.utils.date
import data.utils.dateTime
import data.utils.plus
import kotlinx.coroutines.flow.StateFlow
import model.HashTag
import model.Mention
import model.MonthRecord
import model.WeekRecord
import kotlin.test.assertContentEquals
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds

class RepositoryTest {
    private val now: LocalDateTime = now()
    private lateinit var repository: Repository

    @BeforeTest
    fun init() {
        repository = Repository(StorageStub())
    }

    @Test
    fun `one record added adds also one day week and month`() = runTest(timeout = 1.seconds) {
        repository.addRecord(MoodRecord(now()))

        checkNextValueSize(1, repository.todayRecords)
        checkNextValueSize(1, repository.days)
        checkNextValueSize(1, repository.weeks)
        checkNextValueSize(1, repository.months)
    }

    @Test
    fun getTodayRecords() = runTest {
        val todayRecords = listOf(
            MoodRecord(now, "1"),
            MoodRecord(now, "2"),
        )
        val yesterdaysRecords = listOf(
            MoodRecord(now.startOfDay - 3.hours, "1"),
            MoodRecord(now.startOfDay - 3.hours, "2"),
        )
        repository.addRecords(todayRecords + yesterdaysRecords)

        checkNextValues(todayRecords + yesterdaysRecords, repository.records)
        checkNextValues(todayRecords, repository.todayRecords)
    }

    @Test
    fun getWeeks() = runTest(timeout = 1.seconds) {
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

        checkNextValues(weeks, repository.weeks)
    }

    @Test
    fun getMonths() = runTest(timeout = 1.seconds) {
        val record1 = MoodRecord(date = dateTime(year = 2005, month = 1, dayOfMonth = 10))
        val record2 = MoodRecord(date = dateTime(year = 2005, month = 3, dayOfMonth = 16))

        repository.addRecords(listOf(record1, record2))

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
    fun `check that all records are properly split between weeks`() = runTest(timeout = 1.seconds) {
        val records = listOf(
            MoodRecord(date = dateTime(2005, 1, 15)),
            MoodRecord(date = dateTime(2005, 1, 15)),
            MoodRecord(date = dateTime(2005, 1, 15)),
        )
        repository.addRecords(records)

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
    fun addMention() = runTest(timeout = 1.seconds) {
        val mention1 = Mention(
            value = "mention 1",
            lastUsed = now() - 1.milliseconds
        )
        repository.addMention(mention1)
        checkTags(listOf(mention1), repository.mentions)

        val mention2 = Mention(
            value = "mention 2",
            lastUsed = now()
        )
        repository.addMention(mention2)
        checkTags(listOf(mention1, mention2), repository.mentions)

        val mention3 = mention1.copy(lastUsed = mention1.lastUsed + 1.milliseconds)
        repository.addMention(mention3)
        checkTags(expected = listOf(mention3, mention2), flow = repository.mentions)
    }

    @Test
    fun addTag() = runTest(timeout = 1.seconds) {
        val hashTag1 = HashTag(value = "tag 1", lastUsed = now() - 1.milliseconds)
        repository.addTag(hashTag1)

        repository.tags.test {
            assertEquals(1, awaitItem().size)
        }

        val hashTag2 = HashTag(value = "tag 2", lastUsed = now())
        repository.addTag(hashTag2)

        repository.tags.test {
            val tags = awaitItem()
            assertEquals(2, tags.size)
            assertEquals(hashTag2.value, tags.values.last().value)
        }

        repository.addTag(hashTag1.copy(lastUsed = now() + 1.milliseconds))

        repository.tags.test {
            val tags = awaitItem()
            assertEquals(2, tags.size)
            assertEquals(hashTag1.value, tags.values.first().value)
        }
    }
}

private suspend fun <T> checkTags(
    expected: List<T>,
    flow: StateFlow<MutableMap<String, T>>
) {
    flow.test {
        var got: Collection<T>? = null
        while (expected.size != got?.size) {
            println("### wait for $expected, got $got")
            got = awaitItem().values
        }
        assertContentEquals(expected, got)
    }
}

private suspend fun <T> checkNextValues(
    expected: List<T>,
    flow: StateFlow<List<T>>
) {
    flow.test {
        var got: List<T>? = null
        while (expected.size != got?.size) {
            println("### wait for $expected, got $got")
            got = awaitItem()
        }
        assertContentEquals(expected, got)
    }
}

private suspend fun <T> checkNextValueSize(
    expectedSize: Int,
    flow: StateFlow<List<T>>
) {
    flow.test {
        var item1: List<T>? = null
        while (expectedSize != item1?.size) {
            println("### wait for $expectedSize, got ${item1?.size}")
            item1 = awaitItem()
        }
        assertEquals(expectedSize, item1.size)
    }
}