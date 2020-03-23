package ru.alexb.currencyrates.di.component

import dagger.Subcomponent
import ru.alexb.currencyrates.di.annotation.scope.RatesScope
import ru.alexb.currencyrates.di.module.rates.DataModule
import ru.alexb.currencyrates.di.module.rates.DomainModule
import ru.alexb.currencyrates.di.module.rates.ServiceModule
import ru.alexb.currencyrates.rates.service.CurrencyRatesService

@Subcomponent(
    modules = [
        DataModule::class,
        DomainModule::class,
        ServiceModule::class
    ]
)
@RatesScope
interface RatesComponent {

    fun currencyRatesService(): CurrencyRatesService
}
