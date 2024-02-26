import data.Repository
import data.utils.now
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDateTime
import model.MoodRecord
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.seconds
import app.cash.turbine.test
import data.storage.StorageFilesSystem
import data.utils.minus
import data.utils.startOfDay
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import data.utils.date
import data.utils.plus
import kotlinx.coroutines.flow.first
import model.HashTag
import model.Mention
import kotlin.test.assertContentEquals
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds


@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryTest {

    private val now: LocalDateTime = now()
    private lateinit var repository: Repository

    @BeforeTest
    fun init() {
        repository = Repository(StorageFilesSystem())
    }

    @Test
    fun addRecord() = runTest(timeout = 1.seconds) {
        repository.addRecord(MoodRecord(now))

        repository.todayRecords.test { assertEquals(1, awaitItem().size) }
        repository.days.test { assertEquals(1, awaitItem().size) }
        repository.weeks.test { assertEquals(1, awaitItem().size) }
        repository.months.test { assertEquals(1, awaitItem().size) }
    }

    @Test
    fun getTodayRecords() = runTest {
        val todayRecords = arrayOf(
            MoodRecord(now),
            MoodRecord(now),
        )
        val yesterdaysRecords = arrayOf(
            MoodRecord(now.startOfDay - 3.hours),
            MoodRecord(now.startOfDay - 3.hours),
        )

        (todayRecords + yesterdaysRecords).forEach { repository.addRecord(it) }
        advanceUntilIdle()

        repository.todayRecords.test {
            val item: List<MoodRecord> = awaitItem()

            assertContentEquals(
                todayRecords,
                item.toTypedArray()
            )
        }

        assertEquals(2, repository.days.first().size)
        assertContentEquals(
            todayRecords + yesterdaysRecords,
            repository.days.first().flatMap { it.records }.toTypedArray()
        )
    }

    @Test
    fun getWeeks() = runTest(timeout = 1.seconds) {
        arrayOf(
            MoodRecord(date = date(year = 2005, month = 1, dayOfMonth = 10)),
            MoodRecord(date = date(year = 2005, month = 1, dayOfMonth = 18)),
        ).forEach { repository.addRecord(it) }

        repository.weeks.test { assertEquals(2, awaitItem().size) }
    }

    @Test
    fun getMonths() = runTest(timeout = 1.seconds) {
        arrayOf(
            MoodRecord(date = date(year = 2005, month = 1, dayOfMonth = 10)),
            MoodRecord(date = date(year = 2005, month = 2, dayOfMonth = 16)),
        ).forEach { repository.addRecord(it) }

        repository.months.test { assertEquals(2, awaitItem().size) }
    }

    @Test
    fun `check that all records are properly split between weeks`() = runTest(timeout = 1.seconds) {
        arrayOf(
            MoodRecord(date = date(2005, 1, 15)),
        ).forEach { repository.addRecord(it) }

        delay(1)
        assertEquals(1, repository.weeks.first().size)
        repository.weeks.first().first().let {
            assertEquals(10, it.start.dayOfMonth)
            assertEquals(16, it.end.dayOfMonth)
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

        assertEquals(1, repository.tags.first().size)

        val hashTag2 = HashTag(value = "tag 2", lastUsed = now())
        repository.addTag(hashTag2)

        assertEquals(2, repository.tags.first().size)
        assertEquals(hashTag2.value, repository.tags.first().values.last().value)

        repository.addTag(hashTag1.copy(lastUsed = now() + 1.milliseconds))

        assertEquals(2, repository.tags.first().size)
        assertEquals(hashTag1.value, repository.tags.first().values.first().value)
    }
}