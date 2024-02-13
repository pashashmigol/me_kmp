import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.StackNavigationSource
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import screens.HomeScreen


sealed class Screen : Parcelable {

    @Parcelize
    class Today() : Screen()

    @Parcelize
    data class OneDay(val text: String = "") : Screen()

    @Parcelize
    data class OneWeek(val text: String = "") : Screen()

    @Parcelize
    data class OneMonth(val text: String = "") : Screen()

    @Parcelize
    data class Days(val text: String = "") : Screen()

    @Parcelize
    data class Weeks(val text: String = "") : Screen()

    @Parcelize
    data class Months(val text: String = "") : Screen()

    @Parcelize
    data class Home(val text: String = "") : Screen()
}

val LocalComponentContext: ProvidableCompositionLocal<ComponentContext> =
    staticCompositionLocalOf { error("Root component context was not provided") }


@Composable
fun ProvideComponentContext(
    componentContext: ComponentContext,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalComponentContext provides componentContext,
        content = content
    )
}

@Composable
inline fun <reified C : Parcelable> ChildStack(
    source: StackNavigationSource<C>,
    noinline initialStack: () -> List<C>,
    modifier: Modifier = Modifier,
    handleBackButton: Boolean = false,
    animation: StackAnimation<C, ComponentContext>? = null,
    noinline content: @Composable (C) -> Unit,
) {
    val componentContext = LocalComponentContext.current

    Children(
        stack = remember {
            componentContext.childStack(
                source = source,
                initialStack = initialStack,
                handleBackButton = handleBackButton,
                childFactory = { _, childComponentContext -> childComponentContext },
            )
        },
        modifier = modifier,
        animation = animation,
    ) { child ->
        ProvideComponentContext(child.instance) {
            content(child.configuration)
        }
    }
}

@Composable
fun MainContent() {
    val navigation: StackNavigation<Screen> = remember { StackNavigation<Screen>() }

    ChildStack(
        source = navigation,
        initialStack = { listOf(Screen.Home("")) },
        handleBackButton = true,
        animation = stackAnimation(fade() + scale()),
    ) { screen ->
        when (screen) {
            is Screen.Home -> HomeScreen(navigation)
            is Screen.Months -> Stub(onItemClick = { })
            is Screen.Weeks -> Stub(onItemClick = { })
            is Screen.Days -> Stub(onItemClick = { })
            is Screen.Today -> Stub(onItemClick = { })
            else -> {}
        }
    }
}

@Composable
fun Stub(onItemClick: (String) -> Unit) {
    Text("STUB")
}
