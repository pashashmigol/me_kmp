package data.storage.utils

import data.storage.FORMAT
import data.utils.format
import data.utils.toLocalDateTime
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.Feeling
import model.HashTag
import model.Mention
import model.MoodRecord
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.descriptors.listSerialDescriptor
import kotlinx.serialization.descriptors.nullable
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder.Companion.DECODE_DONE
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.serializer

@OptIn(ExperimentalSerializationApi::class)
private val json1 = Json { allowTrailingComma = true }

fun MoodRecord.Companion.fromJson(json: String): MoodRecord {
    return json1.decodeFromString<MoodRecord>(json)
}

fun MoodRecord.toJson(): String {
    return Json.encodeToString<MoodRecord>(this)
}

fun HashTag.Companion.fromJson(json: String): List<HashTag> {
    if (json.isBlank()) return emptyList()
    return Json.decodeFromString<List<HashTag>>(json)
}

fun hashTagsToJson(list: List<HashTag>): String {
    return Json.encodeToString<List<HashTag>>(list)
}

fun Mention.Companion.fromJson(json: String): List<Mention> {
    if (json.isBlank()) return emptyList()
    return Json.decodeFromString<List<Mention>>(json)
}

fun mentionsToJson(list: List<Mention>): String {
    return Json.encodeToString<List<Mention>>(list)
}

object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalDateTime =
        decoder.decodeString().toLocalDateTime(FORMAT)

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(value.format(FORMAT))
    }
}

val json = "{\"date\":\"Fri, 19 Jan 2024 19:43:56 GMT+0200\"," +
        "\"feelings\":[{\"type\":\"Irritated\",\"emotion\":{\"type\":\"Anger\"}}, " +
        "{\"type\":\"Jealous\"}], " +
        "\"text\":\"#work\"}"


object FeelingsListSerializer : KSerializer<List<Feeling>> {
    @OptIn(ExperimentalSerializationApi::class)
    override val descriptor: SerialDescriptor = listSerialDescriptor(
        FeelingSerializer.descriptor
    )

    override fun serialize(encoder: Encoder, value: List<Feeling>) {
        return ListSerializer(FeelingSerializer).serialize(encoder, value)
    }

    override fun deserialize(decoder: Decoder): List<Feeling> {
        return ListSerializer(FeelingSerializer).deserialize(decoder)
    }
}

object FeelingSerializer : KSerializer<Feeling> {

    override val descriptor = buildClassSerialDescriptor(serialName = "model.Feeling") {
        // intField is deliberately ignored by serializer -- not present in the descriptor as well
        element(
            "type",
            serialDescriptor<String>()
        )
        element(
            "emotion",
            buildClassSerialDescriptor(serialName = "model.Emotion") {
                element(
                    "type",
                    serialDescriptor<String>()
                )
            }.nullable
        )
    }

    override fun deserialize(decoder: Decoder): Feeling {
        var feeling: Feeling? = null
        decoder.decodeStructure(descriptor) {
            loop@ while (true) {
                val index = decodeElementIndex(descriptor)
                when (index) {
                    DECODE_DONE -> break@loop
                    0 -> {
                        val type = decodeStringElement(
                            descriptor,
                            index = index
                        )
                        feeling = Feeling.ofName(type)
                    }

                    1 -> decodeSerializableElement(
                        descriptor,
                        index = index,
                        serializer<Map<String, String>>()
                    )

                    else -> {}
                }
            }
        }

        return feeling!!
    }

    override fun serialize(encoder: Encoder, value: Feeling) {
        val composite = encoder.beginStructure(descriptor)
        composite.encodeStringElement(descriptor, index = 0, value.name())
        composite.endStructure(descriptor)
    }
}
