package ru.alexb.currencyrates.di.component

import dagger.Subcomponent
import ru.alexb.currencyrates.di.annotation.scope.UiScope
import ru.alexb.currencyrates.di.module.ui.UiModule
import ru.alexb.currencyrates.ui.view.MainActivity
import ru.alexb.currencyrates.ui.viewmodel.MainViewModel

@Subcomponent(
    modules = [
        UiModule::class
    ]
)
@UiScope
interface UiComponent {

    fun inject(activity: MainActivity)

    fun inject(viewModel: MainViewModel)
}