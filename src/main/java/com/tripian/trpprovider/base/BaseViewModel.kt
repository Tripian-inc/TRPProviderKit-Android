package com.tripian.trpprovider.base

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.annotation.CallSuper
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.tripian.trpprovider.util.dialog.DGActionListener
import com.tripian.trpprovider.util.AlertType
import com.tripian.trpprovider.util.OnBackPressListener
import com.tripian.trpprovider.util.Strings
import com.tripian.trpprovider.util.ViewListener
import com.tripian.trpprovider.util.extensions.navigateToFragment
import com.tripian.trpprovider.util.extensions.setUseCasesListener
import com.tripian.trpprovider.util.fragment.FragmentFactory
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject
import kotlin.reflect.KClass
import kotlin.Array as Array1


/**
 * Created by Semih Özköroğlu on 29.09.2019
 */
abstract class BaseViewModel(vararg cases: BaseUseCase<*, *>) : ViewModel(), OnBackPressListener {

    @Inject
    lateinit var eventBus: EventBus

    @Inject
    lateinit var strings: Strings

    var arguments: Bundle? = null

    /**
     * Use case'lerde request'leri dispose etmek için use case listesini tutuyoruz
     */
    var useCases = arrayListOf(*cases)

    var fragmentManager: FragmentManager? = null
    var viewListener: ViewListener? = null

    @CallSuper
    open fun onViewCreated(savedInstanceState: Bundle?) {
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this)
        }

        setUseCasesListener()

        viewListener?.hideLoading()
    }

    @CallSuper
    open fun onStart() {
    }

    @CallSuper
    open fun onDestroy() {
        if (eventBus.isRegistered(this)) {
            eventBus.unregister(this)
        }
    }

    @CallSuper
    open fun onResume() {
    }

    @CallSuper
    open fun onPause() {
    }

    @CallSuper
    open fun onSaveInstanceState(outState: Bundle?) {
    }

    /**
     * ViewModel lifecycle'ı sonlandığında çağırılır.
     * RxJava Disposable'larını temizler.
     */
    override fun onCleared() {
        super.onCleared()

        for (useCase in useCases) {
            useCase.clear()
        }
    }

    // StartActivity isSuspend true durumunda suspendable olan domain objeleri dinlemeyi birakir
    private fun onClearSocket() {
        for (useCase in useCases) {
            if (useCase.isSuspendEnable()) {
                useCase.clear()
            }
        }
    }

    open fun onDestroyView() {
        for (useCase in useCases) {
            useCase.clear()
        }
    }

    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    open fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array1<String>,
        grantResults: IntArray
    ) {
    }

    override fun isBackEnable(): Boolean {
        return false
    }

    override fun onBackPressed(): Boolean {
        return true
    }

    fun startActivity(kClass: KClass<out FragmentActivity>, bundle: Bundle? = null, isSuspend: Boolean = false) {
        viewListener?.startActivity(kClass, bundle)

        if (isSuspend) {
            onClearSocket()
        }
    }

    fun finishActivity() {
        viewListener?.finishActivity()
    }

    @JvmOverloads
    fun showDialog(
        title: String? = null, contentText: String?,
        positiveBtn: String? = null, negativeBtn: String? = null,
        positive: (() -> Unit)? = null,
        negative: (() -> (Unit))? = null,
        isCloseEnable: Boolean = true
    ) {
        val fragment = FRWarningFragment.newInstance(title, contentText, positiveBtn, negativeBtn, isCloseEnable)
        fragment.positiveListener = object : DGActionListener {
            override fun onClicked(o: Any?) {
                positive?.invoke()
                fragment.dismiss()
            }
        }
        fragment.negativeListener = object : DGActionListener {
            override fun onClicked(o: Any?) {
                negative?.invoke()
                fragment.dismiss()
            }
        }

        navigateToFragment(fragment)
    }

    @Subscribe
    fun onDummyBusEvent(dummy: Unit) {
        // NOTE: Burasi silinmemeli, bu kısım register oldugunda ihtiyac duyuluyor
    }

    fun showFragment(factory: FragmentFactory) {
        viewListener?.showFragment(factory)
    }

    fun showAlert(type: AlertType, message: String?, duration: Long = 1500) {
        if (!TextUtils.isEmpty(message)) {
            viewListener?.showAlert(type, message!!, duration)
        }
    }
}