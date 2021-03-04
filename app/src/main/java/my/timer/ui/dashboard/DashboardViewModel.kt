package my.timer.ui.dashboard

import android.os.SystemClock
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
        saveButtonEvent.value = Event(true)
        content.set("hihihihi")
//        Log.d("jhy","minute : $content")
//
//        myTimer.scheduleAtFixedRate(object:TimerTask(){
//            override fun run() {
//                progress.postValue(progress.value?.plus(1))
//            }
//        },1000L,1000L)
    }
    fun onClickPause(){
        myTimer.cancel()
        progress.postValue(0)
    }
}

