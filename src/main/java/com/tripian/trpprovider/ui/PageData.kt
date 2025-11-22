package com.tripian.trpprovider.ui

import com.tripian.trpprovider.domain.model.BaseModel
import com.tripian.trpprovider.domain.model.ProviderData

/**
 * Created by semihozkoroglu on 5.08.2020.
 */
class PageData : BaseModel() {

    var holdId: String = ""
    var providerData: ProviderData? = null
    var date: Long = 0
    var covers: String = ""
    var time: String = ""

}