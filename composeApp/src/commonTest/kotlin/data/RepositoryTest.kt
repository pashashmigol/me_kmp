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
import data.utils.plus
import kotlinx.coroutines.flow.StateFlow
import model.HashTag
import model.Mention
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
    fun addRecord() = runTest(timeout = 1.seconds) {
        repository.addRecord(MoodRecord(now()))

        checkNextValue(1, repository.todayRecords)
        checkNextValue(1, repository.days)
        checkNextValue(1, repository.weeks)
        checkNextValue(1, repository.months)
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

        repository.days.test {
            skipItems(1)
            val days = awaitItem()
            assertEquals(2, days.size)
            assertContentEquals(
                todayRecords + yesterdaysRecords,
                days.flatMap { it.records }
            )
        }
        repository.todayRecords.test {
            skipItems(1)
            assertContentEquals(
                todayRecords,
                awaitItem()
            )
        }
    }

    @Test
    fun getWeeks() = runTest(timeout = 1.seconds) {
        arrayOf(
            MoodRecord(date = date(year = 2005, month = 1, dayOfMonth = 10)),
            MoodRecord(date = date(year = 2005, month = 1, dayOfMonth = 18)),
        ).forEach { repository.addRecord(it) }

        repository.weeks.test {
            skipItems(1)
            assertEquals(2, awaitItem().size)
        }
    }

    @Test
    fun getMonths() = runTest(timeout = 1.seconds) {
        arrayOf(
            MoodRecord(date = date(year = 2005, month = 1, dayOfMonth = 10)),
            MoodRecord(date = date(year = 2005, month = 2, dayOfMonth = 16)),
        ).forEach { repository.addRecord(it) }

        repository.months.test {
            skipItems(1)
            assertEquals(2, awaitItem().size)
        }
    }

    @Test
    fun `check that all records are properly split between weeks`() = runTest(timeout = 1.seconds) {
        repository.addRecords(
            listOf(
                MoodRecord(date = date(2005, 1, 15)),
                MoodRecord(date = date(2005, 1, 15)),
                MoodRecord(date = date(2005, 1, 15)),
            )
        )
        repository.weeks.test {
            skipItems(1)
            val weeks = awaitItem()
            assertEquals(1, weeks.size)
            assertEquals(10, weeks[0].start.dayOfMonth)
            assertEquals(16, weeks[0].end.dayOfMonth)
        }
    }

    @Test
    fun addMention() = runTest(timeout = 1.seconds) {
        val mention1 = Mention(
            value = "mention 1",
            lastUsed = now() - 1.milliseconds
        )
        repository.addMention(mention1)
        repository.mentions.test {
            val mentions = awaitItem().values.toList()
            assertEquals(1, mentions.size)
            assertEquals(mention1.value, mentions[0].value)
        }

        val mention2 = Mention(value = "mention 2", lastUsed = now())
        repository.addMention(mention2)

        repository.mentions.test {
            val mentions = awaitItem().values.toList()
            assertEquals(2, mentions.size)
            assertEquals(mention2.value, mentions[1].value)
        }

        repository.addMention(mention1.copy(lastUsed = now() + 1.milliseconds))
        repository.mentions.test {
            val mentions = awaitItem()
            assertEquals(2, mentions.size)
            assertEquals(mention1.value, mentions.values.first().value)
        }
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

private suspend fun <T>checkNextValue(expectedSize: Int, flow: StateFlow<List<T>>) {
    flow.test {
        var item1: List<T>? = null
        while (expectedSize != item1?.size) {
            item1 = awaitItem()
            println("### wait for $expectedSize, got ${item1.size}")
        }
        assertEquals(expectedSize, item1.size)
    }
}