package com.tripian.trpprovider.base

import android.text.TextUtils
import androidx.annotation.CallSuper
import com.google.gson.Gson
import com.tripian.trpprovider.R
import com.tripian.trpprovider.repository.base.ErrorModel
import com.tripian.trpprovider.repository.base.RequestModelBase
import com.tripian.trpprovider.repository.base.ResponseModelBase
import com.tripian.trpprovider.util.Strings
import com.tripian.trpprovider.util.UseCaseListener
import com.tripian.trpprovider.util.dialog.DGContent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException

/**
 * Created by Semih Özköroğlu on 29.09.2019
 */
abstract class BaseUseCase<Response, Params>(vararg cases: BaseUseCase<*, *>) {

    private val requestTag = this.javaClass.simpleName

    private var useCases: Array<BaseUseCase<*, *>> = arrayOf(*cases)

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var strings: Strings

    private var success: ((Response) -> Unit)? = null
    private var error: ((ErrorModel) -> Unit)? = null

    var useCaseListener: UseCaseListener? = null
        set(value) {
            for (useCase in useCases) {
                useCase.useCaseListener = value
            }

            field = value
        }

    open fun isWarningEnable(): Boolean {
        return true
    }

    open fun isSuspendEnable(): Boolean {
        return false
    }

    /**
     * ViewModel kapanması sonrasında içerisindeki RxJava çağrılarının döndürdüğü
     * disposable'ların temizlenmesi için tutulan değişken
     */
    private val compositeDisposable = CompositeDisposable()

    abstract fun on(params: Params? = null)

    /**
     * Servis cagrisinde servisi belirtmek icin kullanilir
     */
    @CallSuper
    fun on(
        params: Params? = null,
        success: ((Response) -> Unit)? = null,
        error: ((ErrorModel) -> Unit)? = null
    ) {
        this.success = success
        this.error = error

        clear()

        on(params)
    }

    /**
     * Lifecycle tamamlandığında silinecek disposable'lar tutulur
     *
     * @param disposable disposable
     */
    private fun add(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    /**
     * Memory leak önlenmesi için disposable'lar silinir.
     */
    @CallSuper
    open fun clear() {
        compositeDisposable.clear()
    }

    fun <Request : RequestModelBase> sendRequest(
        request: Request,
        task: (Request) -> Observable<Response>
    ) {
        add(
            task(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getResponseListener())
        )
    }

    fun sendRequest(task: () -> Observable<Response>) {
        add(
            task()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getResponseListener())
        )
    }

    open fun onSendError(error: ErrorModel) {
        this.error?.invoke(error)
    }

    open fun onSendSuccess(t: Response) {
        this.success?.invoke(t)
    }

    private fun getResponseListener(): DisposableObserver<Response> {
        return object : DisposableObserver<Response>() {
            override fun onComplete() {
            }

            override fun onNext(t: Response & Any) {
                if (t is ResponseModelBase) {
                    // TODO: statusCode null olayi backend'de duzelti, canliya gecince olmucak
                    if (t.statusCode == 0 || t.statusCode == 200 || t.statusCode == null) {
                        onSendSuccess(t)
                    } else {
                        if (!TextUtils.isEmpty(t.statusMessage) && isWarningEnable()) {
                            val message = t.statusMessage!!

                            val dgContent = DGContent()
                            dgContent.positiveBtn = strings.getString(R.string.ok)
                            dgContent.content = message

                            showDialog(dgContent)

                            onSendError(ErrorModel(errorDesc = message))
                        } else {
                            onSendError(ErrorModel())
                        }
                    }
                } else {
                    onSendSuccess(t)
                }
            }
//
//            override fun onNext(t: Response) {
//                if (t is ResponseModelBase) {
//                    // TODO: statusCode null olayi backend'de duzelti, canliya gecince olmucak
//                    if (t.statusCode == 0 || t.statusCode == 200 || t.statusCode == null) {
//                        onSendSuccess(t)
//                    } else {
//                        if (!TextUtils.isEmpty(t.statusMessage) && isWarningEnable()) {
//                            val message = t.statusMessage!!
//
//                            val dgContent = DGContent()
//                            dgContent.positiveBtn = strings.getString(R.string.ok)
//                            dgContent.content = message
//
//                            showDialog(dgContent)
//
//                            onSendError(ErrorModel(errorDesc = message))
//                        } else {
//                            onSendError(ErrorModel())
//                        }
//                    }
//                } else {
//                    onSendSuccess(t)
//                }
//            }

            @CallSuper
            override fun onError(e: Throwable) {
                when (e) {
                    is ConnectException -> {
//                        val dgContent = DGContent()
//                        dgContent.title = strings.getString(R.string.warning)
//                        dgContent.content = strings.getString(R.string.checkConnection)
//                        dgContent.positiveBtn = strings.getString(R.string.ok)
//
//                        showDialog(dgContent)
                    }
                    is SocketTimeoutException -> {
                        showSnackBarMessage("There is no network connection")
                    }
                    is SSLHandshakeException -> {
                        showSnackBarMessage("There is no network connection")
                    }
                    is HttpException -> {
                        val message = (e.response()?.errorBody())?.string()

                        val errorModel = ErrorModel(
                            if (!TextUtils.isEmpty(message)) {
                                message!!
                            } else {
                                strings.getString(R.string.an_error_occurred)
                            }
                        )

                        val dgContent = DGContent()
                        dgContent.title = strings.getString(R.string.warning)
                        dgContent.content = message
                        dgContent.positiveBtn = strings.getString(R.string.ok)

                        showDialog(dgContent)

                        onSendError(errorModel)
                    }
                    else -> {
                        val message = strings.getString(R.string.an_error_occurred)

                        val dgContent = DGContent()
                        dgContent.positiveBtn = strings.getString(R.string.ok)
                        dgContent.content = message

                        showDialog(dgContent)

                        onSendError(ErrorModel(errorDesc = message))
                    }
                }
            }

        }
    }

    fun showDialog(dgContent: DGContent) {
        useCaseListener?.showDialog(dgContent)
    }


    fun showSnackBarMessage(message: String) {
        useCaseListener?.showSnackBarMessage(message)
    }
}

