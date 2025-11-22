package com.tripian.trpprovider.di.modules.provider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tripian.trpprovider.di.ViewModelFactory
import com.tripian.trpprovider.di.ViewModelKey
import com.tripian.trpprovider.ui.ACProviderVM
import com.tripian.trpprovider.ui.FRYelpInputVM
import com.tripian.trpprovider.ui.FRYelpPersonVM
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Semih Özköroğlu on 29.09.2019
 */
@Module
abstract class ProviderVMS {

    /**
     * PROVIDER
     */
    @Binds
    @IntoMap
    @ViewModelKey(ACProviderVM::class)
    abstract fun bindACProviderVM(repoViewModel: ACProviderVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FRYelpInputVM::class)
    abstract fun bindFRYelpInputVM(repoViewModel: FRYelpInputVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FRYelpPersonVM::class)
    abstract fun bindFRYelpPersonVM(repoViewModel: FRYelpPersonVM): ViewModel

    /**
     * ViewModelProvider'in en altta olmasi gerekiyor
     */
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}