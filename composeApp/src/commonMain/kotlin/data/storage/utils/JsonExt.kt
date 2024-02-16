package data.storage.utils

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.Feeling
import model.HashTag
import model.Mention
import model.MoodRecord

fun Feeling.Companion.fromJson(json: String): Feeling {
    return Json.decodeFromString<Feeling>(json)
}

fun Feeling.toJson(): String {
    return Json.encodeToString<Feeling>(this)
}

fun MoodRecord.Companion.fromJson(json: String): MoodRecord {
    return Json.decodeFromString<MoodRecord>(json)
}

fun MoodRecord.toJson(): String {
    return Json.encodeToString<MoodRecord>(this)
}

fun HashTag.Companion.fromJson(json: String): List<HashTag> {
    return Json.decodeFromString<List<HashTag>>(json)
}

fun HashTag.toJson(): String {
    return Json.encodeToString<HashTag>(this)
}

fun List<HashTag>.toJson(): String {
    return Json.encodeToString<List<HashTag>>(this)
}

fun Mention.Companion.fromJson(json: String): List<Mention> {
    return Json.decodeFromString<List<Mention>>(json)
}

fun Mention.toJson(): String {
    return Json.encodeToString<Mention>(this)
}

fun List<Mention>.toJson(): String {
    return Json.encodeToString<List<Mention>>(this)
}
