package data.di

import screens.history.viewmodels.OneMonthRecordsViewModel
import data.Repository
import data.storage.StorageFilesSystem
import org.kodein.di.DI
import org.kodein.di.bindProvider
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
    bindProvider<Repository> { Repository(storage = StorageFilesSystem()) }
    bindProvider<TodayRecordsViewModel> { TodayRecordsViewModel(repo = instance()) }
    bindProvider<TagsViewModel> { TagsViewModelReal(repo = instance()) }

    bindProvider<OneDayRecordsViewModel> { OneDayRecordsViewModel(repo = instance()) }
    bindProvider<OneWeekRecordsViewModel> { OneWeekRecordsViewModel(repo = instance()) }
    bindProvider<OneMonthRecordsViewModel> { OneMonthRecordsViewModel(repo = instance()) }

    bindProvider<MonthsViewModel> { MonthsViewModel(repo = instance()) }
    bindProvider<WeeksViewModel> { WeeksViewModel(repo = instance()) }
    bindProvider<DaysViewModel> { DaysViewModel(repo = instance()) }

    bindProvider<DraftRecordViewModel> { DraftRecordViewModelReal(tagsViewModel = instance()) }

    bindProvider<WheelViewModel> { WheelViewModelReal() }
}