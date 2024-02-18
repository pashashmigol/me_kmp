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

fun hashTagsToJson(list : List<HashTag>): String {
    return Json.encodeToString<List<HashTag>>(list)
}

fun Mention.Companion.fromJson(json: String): List<Mention> {
    return Json.decodeFromString<List<Mention>>(json)
}

fun mentionsToJson(list : List<Mention>): String {
    return Json.encodeToString<List<Mention>>(list)
}
