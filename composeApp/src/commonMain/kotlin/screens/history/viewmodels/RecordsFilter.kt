package screens.history.viewmodels

import model.Emotion

data class RecordsFilter(val text: String, val emotions: List<Emotion>) {
    fun isSelected(emotion: Emotion) = emotions.contains(emotion)

    companion object {
        val noFilter = RecordsFilter(text = "", emotions = emptyList())
    }
}
