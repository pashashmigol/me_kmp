package data.di

import screens.history.viewmodels.OneMonthRecordsViewModel
import data.Repository
import data.storage.StorageFilesSystem
import org.kodein.di.DI
import org.kodein.di.bindEagerSingleton
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import screens.components.wheel.WheelViewModel
import screens.components.wheel.WheelViewModelReal
import screens.history.viewmodels.DaysViewModel
import screens.history.viewmodels.MonthsViewModel
import screens.history.viewmodels.OneDayRecordsViewModel
import screens.history.viewmodels.OneWeekRecordsViewModel
import screens.history.viewmodels.TodayRecordsViewModel
import screens.history.viewmodels.WeeksViewModel
import screens.history.viewmodels.draft.DraftRecordViewModel
import screens.history.viewmodels.draft.DraftRecordViewModelReal
import screens.history.viewmodels.tags.TagsViewModel
import screens.history.viewmodels.tags.TagsViewModelReal


val di: DI = DI {
    bindSingleton<Repository> { Repository(storage = StorageFilesSystem()) }
    bindSingleton<TodayRecordsViewModel> { TodayRecordsViewModel(repo = instance()) }
    bindSingleton<TagsViewModel> { TagsViewModelReal(repo = instance()) }

    bindSingleton<OneDayRecordsViewModel> { OneDayRecordsViewModel(repo = instance()) }
    bindSingleton<OneWeekRecordsViewModel> { OneWeekRecordsViewModel(repo = instance()) }
    bindSingleton<OneMonthRecordsViewModel> { OneMonthRecordsViewModel(repo = instance()) }

    bindSingleton<MonthsViewModel> { MonthsViewModel(repo = instance()) }
    bindSingleton<WeeksViewModel> { WeeksViewModel(repo = instance()) }
    bindSingleton<DaysViewModel> { DaysViewModel(repo = instance()) }

    bindSingleton<DraftRecordViewModel> { DraftRecordViewModelReal(tagsViewModel = instance()) }

    bindSingleton<WheelViewModel> { WheelViewModelReal() }
}