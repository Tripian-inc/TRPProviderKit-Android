package com.tripian.trpprovider.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tripian.trpprovider.base.FRWarningDialogVM
import com.tripian.trpprovider.di.ViewModelFactory
import com.tripian.trpprovider.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Semih Özköroğlu on 29.09.2019
 */
@Module
abstract class AppViewModels {

    /**
     * COMMON
     */
    @Binds
    @IntoMap
    @ViewModelKey(FRWarningDialogVM::class)
    abstract fun bindFRWarningDialogVM(repoViewModel: FRWarningDialogVM): ViewModel

    /**
     * ViewModelProvider'in en altta olmasi gerekiyor
     */
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}