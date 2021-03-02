package my.timer.ui.intro

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IntroActivityViewModel : ViewModel() {
    val permissionRequest = MutableLiveData<String>()

    fun onPermissionResult(permission: String, granted: Boolean) {
        TODO("whatever you need to do")
    }
}