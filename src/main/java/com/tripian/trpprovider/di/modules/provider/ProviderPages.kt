package com.tripian.trpprovider.di.modules.provider

import com.tripian.trpprovider.ui.FRYelpInput
import com.tripian.trpprovider.ui.FRYelpPerson
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by semihozkoroglu on 17.05.2020.
 */
@Module
abstract class ProviderPages {

    @ContributesAndroidInjector
    abstract fun bindFRYelpInput(): FRYelpInput

    @ContributesAndroidInjector
    abstract fun bindFRYelpPerson(): FRYelpPerson

}