package my.timer.ui.dashboard

import android.os.SystemClock
import android.provider.Settings
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import my.timer.custom.KeyboardViewReverseBinding
import my.timer.utils.Event
import java.util.*

class DashboardViewModel : ViewModel() {

    val progress = MutableLiveData<Int>().apply {
        value = 0
    }
    val content = ObservableField<String>()
    val second = ObservableField<String>()

    private val initialTime: Long = SystemClock.elapsedRealtime()
    private val elapsedTime = MutableLiveData<Long>()
    private lateinit var myTimer : Timer
    val saveButtonEvent = MutableLiveData<Event<Boolean>>().apply {
    }
    val checkPermissionEvent = MutableLiveData<Event<Boolean>>()

    fun onClickSave() {
        myTimer = Timer()
        val second: Int =
            Integer.valueOf(strLenSafe(content.get())) * 60 + Integer.valueOf(strLenSafe(second.get()))
        val plusSecond = 100 / second
        //        *study*
//        myTimer backgroundThread에서 saveButtonEvent setValue를 할 수 없다.
        myTimer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val progressValue = if (plusSecond >= 100) {
                    100
                } else {
                    plusSecond
                }
                val nextProgressValue = progress.value?.plus(progressValue) as Int
                progress.postValue(nextProgressValue)
                if (nextProgressValue >= 100) {
                    saveButtonEvent.postValue(Event(true))
                    progress.postValue(0)
                    myTimer.cancel()
                }
            }
        }, 0, 1000L)
    }

    fun onClickPause() {
        myTimer.cancel()
        progress.postValue(0)
    }

    fun strLenSafe(s: String?): Int = if (s != null && s.isNotEmpty()) Integer.valueOf(s) else 0
}

