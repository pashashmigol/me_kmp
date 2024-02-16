package screens.components.wheel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Rect
import com.rickclephas.kmm.viewmodel.KMMViewModel

abstract class WheelViewModel : KMMViewModel() {
    abstract val bigWheelPosition: MutableState<Rect?>
}

class WheelViewModelStub : WheelViewModel() {
    override val bigWheelPosition: MutableState<Rect?> = mutableStateOf(null)
}

class WheelViewModelReal : WheelViewModel() {
    override val bigWheelPosition: MutableState<Rect?> = mutableStateOf(null)
}
