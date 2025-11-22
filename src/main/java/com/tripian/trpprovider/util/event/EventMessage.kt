package com.fuzzymobilegames.okeyonline.util.event

import com.tripian.trpprovider.domain.model.BaseModel

data class EventMessage<T>(var tag: String? = null, var value: T? = null) : BaseModel()