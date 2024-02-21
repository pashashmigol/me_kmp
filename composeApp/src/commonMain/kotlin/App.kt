import androidx.compose.runtime.Composable
import data.di.di
import org.kodein.di.direct

@Composable
fun App() {
    MainContent(di.direct)
}
