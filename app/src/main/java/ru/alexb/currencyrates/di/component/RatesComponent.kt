package ru.alexb.currencyrates.di.component

import dagger.Subcomponent
import ru.alexb.currencyrates.di.annotation.scope.RatesScope
import ru.alexb.currencyrates.di.module.rates.DataModule
import ru.alexb.currencyrates.di.module.rates.DomainModule
import ru.alexb.currencyrates.rates.service.CurrencyRatesServiceImpl

@Subcomponent(
    modules = [
        DataModule::class,
        DomainModule::class
    ]
)
@RatesScope
interface RatesComponent {

    fun inject(service: CurrencyRatesServiceImpl)
}
