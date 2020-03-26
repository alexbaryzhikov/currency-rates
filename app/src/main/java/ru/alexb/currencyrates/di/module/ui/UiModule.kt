package ru.alexb.currencyrates.di.module.ui

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import ru.alexb.currencyrates.di.annotation.scope.UiScope
import ru.alexb.currencyrates.rates.service.CurrencyRatesService
import ru.alexb.currencyrates.ui.controller.MainViewController
import ru.alexb.currencyrates.ui.controller.MainViewControllerImpl
import ru.alexb.currencyrates.ui.view.MainActivity
import ru.alexb.currencyrates.ui.view.adapter.CurrencyRecyclerViewAdapter
import ru.alexb.currencyrates.ui.viewmodel.MainViewModel

@Module
class UiModule(private val mainActivity: MainActivity) {

    @Provides
    @UiScope
    fun provideMainViewModel(): MainViewModel {
        return ViewModelProvider(mainActivity).get(MainViewModel::class.java)
    }

    @Provides
    @UiScope
    fun provideCurrencyRecyclerViewAdapter(
        layoutManager: LinearLayoutManager,
        viewController: MainViewController
    ): CurrencyRecyclerViewAdapter {
        return CurrencyRecyclerViewAdapter(layoutManager, viewController)
    }

    @Provides
    @UiScope
    fun provideLayoutManager(): LinearLayoutManager {
        return LinearLayoutManager(mainActivity)
    }

    @Provides
    @UiScope
    fun provideMainViewController(service: CurrencyRatesService): MainViewController {
        return MainViewControllerImpl(service)
    }
}