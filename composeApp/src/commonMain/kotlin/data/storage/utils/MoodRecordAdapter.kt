package data.storage.utils

//import data.storage.dateFormat
//import com.squareup.moshi.JsonAdapter
//import com.squareup.moshi.JsonReader
//import com.squareup.moshi.JsonWriter
//import data.storage.FORMAT
//import data.utils.format
//import kotlinx.datetime.LocalDateTime
//import kotlinx.serialization.KSerializer
//import kotlinx.serialization.descriptors.PrimitiveKind
//import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
//import kotlinx.serialization.descriptors.SerialDescriptor
//import kotlinx.serialization.encoding.Decoder
//import kotlinx.serialization.encoding.Encoder
//import model.Feeling
//import model.MoodRecord
//import model.dateFormat

//object MoodRecordAdapter : KSerializer<MoodRecord> {
//    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Color",
//        PrimitiveKind.STRING)
//
//    override fun serialize(encoder: Encoder, value: MoodRecord) {
//        val string = value.rgb.toString(16).padStart(6, '0')
//        encoder.encodeString(string)
//    }
//
//    override fun deserialize(decoder: Decoder): MoodRecord {
//
//        decoder.beginStructure(SerialDescriptor.)
//
//        lateinit var date: LocalDateTime
//        lateinit var text: String
//        val feelings = mutableListOf<Feeling>()
//
//        while (reader.hasNext()) {
//            when (reader.nextName()) {
//                "date" -> date = dateFormat.parse(reader.nextString())
//                "text" -> text = reader.nextString()
//                "feelings" -> {
//                    reader.beginArray()
//
//                    while (reader.peek() == JsonReader.Token.BEGIN_OBJECT) {
//                        reader.beginObject()
//
//                        var name = reader.peek()
//                        while (name == JsonReader.Token.NAME) {
//                            if (reader.nextName() == "type") {
//                                feelings.add(Feeling.ofName(reader.nextString()))
//                            } else {
//                                reader.skipValue()
//                            }
//                            name = reader.peek()
//                        }
//                        reader.endObject()
//                    }
//                    reader.endArray()
//                }
//            }
//        }
//        reader.endObject()
//        return MoodRecord(
//            text = text,
//            date = date,
//            feelings = feelings
//        )
//
//    }
//}

//class MoodRecordAdapterq : JsonAdapter<MoodRecord>() {
//    override fun fromJson(reader: JsonReader): MoodRecord? = runCatching {
//        reader.beginObject()
//
//        lateinit var date: LocalDateTime
//        lateinit var text: String
//        val feelings = mutableListOf<Feeling>()
//
//        while (reader.hasNext()) {
//            when (reader.nextName()) {
//                "date" -> date = dateFormat.parse(reader.nextString())
//                "text" -> text = reader.nextString()
//                "feelings" -> {
//                    reader.beginArray()
//
//                    while (reader.peek() == JsonReader.Token.BEGIN_OBJECT) {
//                        reader.beginObject()
//
//                        var name = reader.peek()
//                        while (name == JsonReader.Token.NAME) {
//                            if (reader.nextName() == "type") {
//                                feelings.add(Feeling.ofName(reader.nextString()))
//                            } else {
//                                reader.skipValue()
//                            }
//                            name = reader.peek()
//                        }
//                        reader.endObject()
//                    }
//                    reader.endArray()
//                }
//            }
//        }
//        reader.endObject()
//        return MoodRecord(
//            text = text,
//            date = date,
//            feelings = feelings
//        )
//    }.getOrNull()
//
//    override fun toJson(
//        writer: JsonWriter,
//        moodRecord: MoodRecord?
//    ) {
//        writer.beginObject()
//        writer.name("date").value(
//            moodRecord?.date?.format(FORMAT) ?: ""
//        )
//        writer.name("text").value(moodRecord?.text)
//        writer.name("feelings").beginArray()
//        moodRecord?.feelings?.forEach { feeling ->
//            writer.beginObject()
//            writer.name("type").value(feeling.name())
//            writer.endObject()
//        }
//        writer.endArray()
//        writer.endObject()
//    }
//}
