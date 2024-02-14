package com.me.screens.components.wheel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Rect
import androidx.lifecycle.ViewModel

abstract class WheelViewModel : ViewModel() {
    abstract val bigWheelPosition: MutableState<Rect?>
}

class WheelViewModelStub : WheelViewModel() {
    override val bigWheelPosition: MutableState<Rect?> = mutableStateOf(null)
}

class WheelViewModelReal : WheelViewModel() {
    override val bigWheelPosition: MutableState<Rect?> = mutableStateOf(null)
}
