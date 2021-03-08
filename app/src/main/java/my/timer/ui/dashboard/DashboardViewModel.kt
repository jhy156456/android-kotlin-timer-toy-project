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

    val progress  = MutableLiveData<Int>().apply {
        value = 0
    }
    val content = ObservableField<String>()
    private val initialTime : Long = SystemClock.elapsedRealtime()
    private val elapsedTime  = MutableLiveData<Long>()
    private val myTimer = Timer()
    val saveButtonEvent  = MutableLiveData<Event<Boolean>>().apply {
    }
    fun onClickSave() {

        val second :Int = Integer.valueOf(content.get()) * 60
        var nowSecond =  0
        val plusSecond:Double = (second / 100).toDouble()
        myTimer.scheduleAtFixedRate(object:TimerTask(){
            override fun run() {
                Log.d("jhy", "nowSecond : $nowSecond")
                nowSecond += plusSecond
                progress.postValue(progress.value?.plus(1))
                if (nowSecond>second){
                    saveButtonEvent.value = Event(true)
                }
            }
        },0,1000L)
    }
    fun onClickPause(){
        myTimer.cancel()
        progress.postValue(0)
    }
}

