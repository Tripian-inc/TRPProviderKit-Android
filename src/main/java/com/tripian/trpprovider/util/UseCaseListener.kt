package com.tripian.trpprovider.util

import com.tripian.trpprovider.util.dialog.DGContent

/**
 * Created by semihozkoroglu on 2019-08-06.
 */
interface UseCaseListener {

    fun showDialog(dgContent: DGContent)

    fun showSnackBarMessage(message: String)
}