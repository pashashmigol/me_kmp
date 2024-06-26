@file:Suppress("DEPRECATION")

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
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import model.MonthRecord
import model.WeekRecord
import org.kodein.di.DirectDI
import org.kodein.di.instance
import screens.history.HistoryScreen
import screens.history.TodayScreen
import screens.history.viewmodels.DaysViewModel
import screens.history.viewmodels.HistoryViewModel
import screens.history.viewmodels.OneDayRecordsViewModel
import screens.history.viewmodels.OneMonthRecordsViewModel
import screens.history.viewmodels.OneWeekRecordsViewModel
import screens.history.viewmodels.TodayRecordsViewModel
import screens.history.viewmodels.tags.TagsViewModel
import screens.home.HomeScreen

sealed class Screen : Parcelable {

    @Parcelize
    data object Today : Screen()

    @Parcelize
    data class OneDay(val index: Int) : Screen()

    @Parcelize
    data class OneWeek(val index: Int) : Screen()

    @Parcelize
    data class OneMonth(val index: Int) : Screen()

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
fun MainContent(di: DirectDI) {
    val navigation: StackNavigation<Screen> = remember { StackNavigation() }

    ChildStack(
        source = navigation,
        initialStack = { listOf(Screen.Home("")) },
        handleBackButton = true,
        animation = stackAnimation(fade() + scale()),
    ) { screen ->
        when (screen) {
            is Screen.Home -> HomeScreen(navController = navigation)
            is Screen.Months -> HistoryScreen(
                historyViewModel = di.instance<HistoryViewModel<MonthRecord>>(),
                tagsViewModel = di.instance<TagsViewModel>(),
                onItemClick = { navigation.push(Screen.OneMonth(it)) }
            )
            is Screen.Weeks -> HistoryScreen(
                historyViewModel = di.instance<HistoryViewModel<WeekRecord>>(),
                tagsViewModel = di.instance<TagsViewModel>(),
                onItemClick = { navigation.push(Screen.OneWeek(it)) }
            )
            is Screen.Days -> HistoryScreen(
                historyViewModel = di.instance<DaysViewModel>(),
                tagsViewModel = di.instance<TagsViewModel>(),
                onItemClick = { navigation.push(Screen.OneDay(it)) }
            )
            is Screen.Today -> TodayScreen(
                historyViewModel = di.instance<TodayRecordsViewModel>(),
                draftViewModel = di.instance(),
                wheelViewModel = di.instance(),
                tagsViewModel = di.instance(),
            )
            is Screen.OneDay -> HistoryScreen(
                historyViewModel = di.instance<Int, OneDayRecordsViewModel>(arg = screen.index),
                tagsViewModel = di.instance<TagsViewModel>(),
            )
            is Screen.OneWeek -> HistoryScreen(
                historyViewModel = di.instance<Int, OneWeekRecordsViewModel>(arg = screen.index),
                tagsViewModel = di.instance<TagsViewModel>(),
            )
            is Screen.OneMonth -> HistoryScreen(
                historyViewModel = di.instance<Int, OneMonthRecordsViewModel>(arg = screen.index),
                tagsViewModel = di.instance<TagsViewModel>(),
            )
            else -> error("no such screen: $screen")
        }
    }
}
