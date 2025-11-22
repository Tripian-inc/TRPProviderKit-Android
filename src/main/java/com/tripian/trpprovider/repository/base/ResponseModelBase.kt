package com.tripian.trpprovider.repository.base

import com.tripian.trpprovider.domain.model.BaseModel

/**
 * Created by semihozkoroglu on 2019-09-09.
 */
open class ResponseModelBase : BaseModel() {
    val statusCode: Int? = null
    val statusMessage: String? = null
}