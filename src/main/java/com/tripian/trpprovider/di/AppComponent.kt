package com.tripian.trpprovider.di

import android.app.Application
import com.tripian.trpprovider.base.AppConfig
import com.tripian.trpprovider.base.ProviderCore
import com.tripian.trpprovider.di.modules.AppModule
import com.tripian.trpprovider.di.modules.AppPages
import com.tripian.trpprovider.di.modules.AppViewModels
import com.tripian.trpprovider.di.modules.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AppModule::class,
            NetworkModule::class,
            AppPages::class,
            AppViewModels::class
        ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun configurations(appConfig: AppConfig): Builder

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }

    fun inject(provider: ProviderCore)
}