package my.timer.ui.dashboard

import android.os.Handler
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import my.timer.utils.Event
import java.util.*

class DashboardViewModel : ViewModel() {


    val progress  = MutableLiveData<Int>().apply {
        Log.d("jhy","mutable")
        value = 0
    }
    val saveButtonEvent  = MutableLiveData<Event<Boolean>>().apply {
    }
    fun onClickSave() {
        Log.d("jhy","onClickSave")
        saveButtonEvent.value = Event(true)
        while (true){
            Log.d("jhy","while")
            Handler().postDelayed({
                progress.value = progress.value?.plus(1)
            },1000)
            if(progress.value==100){
                break
            }
        }
    }
}