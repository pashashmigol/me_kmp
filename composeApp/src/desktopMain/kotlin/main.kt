import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Me Multiplatform") {
        App()
        AtomicInteger
    }
}

@Preview
@Composable
fun AppDesktopPreview() {
    App()
}