package com.tripian.trpprovider.util.dialog

import com.tripian.trpprovider.domain.model.BaseModel

class DGContent(
        var title: String? = "",
        var content: String? = "",
        var positiveBtn: String? = "",
        var negativeBtn: String? = "",
        var positiveListener: DGActionListener? = null,
        var negativeListener: DGActionListener? = null
) : BaseModel()