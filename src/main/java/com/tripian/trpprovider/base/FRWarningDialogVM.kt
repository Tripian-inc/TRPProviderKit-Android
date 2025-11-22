package com.tripian.trpprovider.base

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class FRWarningDialogVM @Inject constructor() : BaseViewModel() {

    var onSetDescriptionListener = MutableLiveData<String>()

    override fun onViewCreated(savedInstanceState: Bundle?) {
        super.onViewCreated(savedInstanceState)

        onSetDescriptionListener.postValue(arguments!!.getString("contentText"))
    }
}