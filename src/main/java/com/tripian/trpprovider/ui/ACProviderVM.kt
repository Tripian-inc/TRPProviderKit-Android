package com.tripian.trpprovider.ui

import android.os.Bundle
import com.tripian.trpprovider.base.BaseViewModel
import com.tripian.trpprovider.domain.model.ProviderData
import com.tripian.trpprovider.util.extensions.goBack
import com.tripian.trpprovider.util.extensions.navigateToFragment
import javax.inject.Inject

class ACProviderVM @Inject constructor() : BaseViewModel() {

    @Inject
    lateinit var pageData: PageData

    override fun onViewCreated(savedInstanceState: Bundle?) {
        super.onViewCreated(savedInstanceState)

        pageData.providerData = arguments!!.getSerializable("poiDetail") as ProviderData

        navigateToFragment(fragment = FRYelpInput.newInstance(), addToBackStack = false)
    }

    fun onClickedBack() {
        goBack()
    }
}