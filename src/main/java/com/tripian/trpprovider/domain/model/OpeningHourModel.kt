package com.tripian.trpprovider.domain.model

/**
 * Created by semihozkoroglu on 7.08.2020.
 */
class OpeningHourModel : BaseModel() {
    var date: String? = null
    var times: List<TimesModel>? = null
}