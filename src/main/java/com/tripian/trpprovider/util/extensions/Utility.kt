package com.tripian.trpprovider.util.extensions

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Point
import android.net.ConnectivityManager
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import java.net.InetAddress
import java.net.UnknownHostException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import kotlin.math.roundToInt

fun dp2Px(dp: Float): Float {
    val metrics = Resources.getSystem().displayMetrics
    val px = dp * (metrics.densityDpi / 160f)
    return px.roundToInt().toFloat()
}

fun getDateMMMMyyyy(dateText: String?): String {
    var sdf: SimpleDateFormat? = null
    var date: Date? = null
    if (dateText != null && dateText.isNotEmpty()) {

        sdf = SimpleDateFormat("dd.MM.yyyy")
        try {

            date = sdf.parse(dateText)

            sdf = SimpleDateFormat("MMMM yyyy", Locale("tr"))

            return sdf.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

    }

    return ""
}

fun EditText.showKeyboard() {
    try {
        val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    } catch (e: java.lang.Exception) {
        //Log.e("KeyBoardUtil", e.toString(), e);
    }
}

fun EditText.hideKeyboard() {
    try {
        val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    } catch (e: java.lang.Exception) {
        //Log.e("KeyBoardUtil", e.toString(), e);
    }
}

fun FragmentActivity.hideKeyboard() {
    try {
        val view: View? = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    } catch (e: java.lang.Exception) {
    }
}

fun getDate(timestamp: Long): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp

    val sdf: SimpleDateFormat?

    try {
        sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

        return sdf.format(calendar.time)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return ""
}

fun getYear(timestamp: Long): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp

    val sdf: SimpleDateFormat?

    try {
        sdf = SimpleDateFormat("yyyy", Locale("tr"))

        return sdf.format(calendar.time)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return ""
}

fun getMonth(timestamp: Long): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp

    val sdf: SimpleDateFormat?

    try {
        sdf = SimpleDateFormat("MM", Locale("tr"))

        return sdf.format(calendar.time)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return ""
}

fun getDay(timestamp: Long): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp

    val sdf: SimpleDateFormat?

    try {
        sdf = SimpleDateFormat("dd", Locale("tr"))

        return sdf.format(calendar.time)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return ""
}

fun isValidEmail(target: CharSequence): Boolean {
    return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
}

fun isValidTCKN(identityNumber: String): Boolean {
    if (TextUtils.isEmpty(identityNumber)
        || identityNumber.length != 11
        || !identityNumber.matches("^([1-9]{1}[0-9]{10})$".toRegex())
    ) {
        return false
    }
    var oddNumberTotal = 0
    var evenNumberTotal = 0
    var total = 0
    var tenthNumber = 0
    var eleventhNumber = 0
    for (i in 0 until 11) {
        val charNumber = Character.getNumericValue(identityNumber.toCharArray()[i])
        if (i == 0 || i == 2 || i == 4 || i == 6 || i == 8) {
            oddNumberTotal += charNumber
            total += charNumber
        } else if (i == 1 || i == 3 || i == 5 || i == 7) {
            evenNumberTotal += charNumber
            total += charNumber
        } else if (i == 9) {
            tenthNumber = charNumber
            total += charNumber
        } else if (i == 10) {
            eleventhNumber = charNumber
        }
    }
    return (oddNumberTotal * 7 - evenNumberTotal) % 10 == tenthNumber && total % 10 == eleventhNumber && eleventhNumber % 2 == 0
}

fun clearTurkishChars(str: String?): String? {
    if (TextUtils.isEmpty(str)) {
        return str
    }

    var ret = str
    val turkishChars = charArrayOf(
        0x131.toChar(),
        0x130.toChar(),
        0xFC.toChar(),
        0xDC.toChar(),
        0xF6.toChar(),
        0xD6.toChar(),
        0x15F.toChar(),
        0x15E.toChar(),
        0xE7.toChar(),
        0xC7.toChar(),
        0x11F.toChar(),
        0x11E.toChar()
    )
    val englishChars = charArrayOf('i', 'I', 'u', 'U', 'o', 'O', 's', 'S', 'c', 'C', 'g', 'G')
    for (i in turkishChars.indices) {
        ret = ret!!.replace(
            String(charArrayOf(turkishChars[i])).toRegex(),
            String(charArrayOf(englishChars[i]))
        )
    }
    return ret
}

fun changeColor(fromColor: Int, toColor: Int, task: (Int) -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val anim = ValueAnimator.ofFloat(0f, 1f)
        anim.addUpdateListener { animation ->
            // Use animation position to blend colors.
            val position = animation.animatedFraction

            task(blendColors(fromColor, toColor, position))
        }

        anim.setDuration(500).start()
    }
}

private fun blendColors(from: Int, to: Int, ratio: Float): Int {
    val inverseRatio = 1f - ratio

    val r = Color.red(to) * ratio + Color.red(from) * inverseRatio
    val g = Color.green(to) * ratio + Color.green(from) * inverseRatio
    val b = Color.blue(to) * ratio + Color.blue(from) * inverseRatio

    return Color.rgb(r.toInt(), g.toInt(), b.toInt())
}

fun getScreenSize(context: Context): IntArray {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = windowManager.defaultDisplay
    val size = Point()
    if (Build.VERSION.SDK_INT > 16)
        display.getRealSize(size)
    else
        display.getSize(size)

    return intArrayOf(size.x, size.y)
}

fun getMetrics(context: Context): DisplayMetrics {
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val metrics = DisplayMetrics()
    wm.defaultDisplay.getMetrics(metrics)

    return metrics
}

fun checkConnection(context: Context?): Boolean {
    if (context != null) {
        val conMgr =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (conMgr != null) while (true) {
            val activeInfo = conMgr.activeNetworkInfo
            return activeInfo != null && activeInfo.isConnected
        }
    }

    return true
}

fun internetConnectionAvailable(timeOut: Int): Boolean {
    var inetAddress: InetAddress? = null
    try {
        val future =
            Executors.newSingleThreadExecutor().submit<InetAddress> {
                try {
                    return@submit InetAddress.getByName("google.com")
                } catch (e: UnknownHostException) {
                    return@submit null
                }
            }
        inetAddress = future[timeOut.toLong(), TimeUnit.MILLISECONDS]
        future.cancel(true)
    } catch (e: InterruptedException) {
    } catch (e: ExecutionException) {
    } catch (e: TimeoutException) {
    }

    return inetAddress != null && !inetAddress.equals("")
}

fun View.calculate(): IntArray {
    measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)

    return intArrayOf(measuredWidth, measuredHeight)
}

fun getHourMinute(time: Long): String {
    var diffValue = ""
    val hour = time / (60 * 60 * 1000)
    var remaining = time % (60 * 60 * 1000).toLong()
    val minutes = remaining / (60 * 1000)
    remaining %= (60 * 1000).toLong()
    val seconds = remaining / 1000

    diffValue += if (hour > 0) {
        if (minutes > 0) {
            if (hour < 10) {
                "0$hour:"
            } else {
                "$hour:"
            }
        } else {
            if (hour < 10) {
                "0$hour:"
            } else {
                "$hour:"
            }
        }
    } else {
        "00:"
    }
    diffValue += if (minutes > 0) {
        if (seconds > 0) {
            if (minutes < 10) {
                "0$minutes:"
            } else {
                "$minutes:"
            }
        } else {
            if (minutes < 10) {
                "0$minutes:"
            } else {
                "$minutes:"
            }
        }
    } else {
        "00:"
    }
    diffValue += if (seconds > 0) {
        if (seconds < 10) {
            "0$seconds"
        } else {
            seconds.toString() + ""
        }
    } else {
        "00"
    }
    return diffValue
}

fun getUniqueId(context: Context): String {
    return try {
        Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    } catch (e: Exception) {
        System.currentTimeMillis().toString() + ""
    }
}