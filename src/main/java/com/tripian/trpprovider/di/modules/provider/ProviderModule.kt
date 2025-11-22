package com.tripian.trpprovider.di.modules.provider

import com.tripian.trpprovider.ui.PageData
import dagger.Module
import dagger.Provides

/**
 * Created by semihozkoroglu on 17.05.2020.
 */
@Module(includes = [ProviderPages::class, ProviderVMS::class])
class ProviderModule {

    @ProviderScope
    @Provides
    fun providePageDate(): PageData = PageData()
}