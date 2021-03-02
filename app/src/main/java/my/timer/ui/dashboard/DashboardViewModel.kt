package my.timer.ui.dashboard

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import my.timer.utils.Event
import java.util.*

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
    val progress  = MutableLiveData<Int>().apply {
        value = 0
    }
    val saveButtonEvent  = MutableLiveData<Event<Boolean>>().apply {

    }
    fun onClickHistory(view: View?) {

        saveButtonEvent.value = Event(true)
    }
}