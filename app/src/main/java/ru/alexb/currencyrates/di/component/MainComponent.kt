package ru.alexb.currencyrates.di.component

import dagger.Component
import ru.alexb.currencyrates.MainActivity
import ru.alexb.currencyrates.di.module.AppModule
import ru.alexb.currencyrates.di.module.DataModule
import ru.alexb.currencyrates.di.module.DomainModule
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        DataModule::class,
        DomainModule::class
    ]
)
@Singleton
interface MainComponent {

    fun inject(activity: MainActivity)
}