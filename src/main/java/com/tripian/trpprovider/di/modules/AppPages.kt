package com.tripian.trpprovider.di.modules

import com.tripian.trpprovider.base.FRWarningFragment
import com.tripian.trpprovider.di.modules.provider.ProviderModule
import com.tripian.trpprovider.di.modules.provider.ProviderScope
import com.tripian.trpprovider.ui.ACProvider
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector

/**
 * Created by Semih Özköroğlu on 22.07.
 */
@Module(includes = [AndroidInjectionModule::class])
abstract class AppPages {

    /**
     * PROVIDER
     */
    @ProviderScope
    @ContributesAndroidInjector(modules = [ProviderModule::class])
    abstract fun bindACProvider(): ACProvider

    /**
     * COMMON
     */
    @ContributesAndroidInjector
    abstract fun bindFRWarningDialog(): FRWarningFragment
}