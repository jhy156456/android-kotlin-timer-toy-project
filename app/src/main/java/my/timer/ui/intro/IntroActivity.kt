package my.timer.ui.intro

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import my.timer.MainActivity
import my.timer.ui.common.Prefs
import my.timer.ui.dashboard.DashboardViewModel
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.startActivity
import java.lang.reflect.Array.get

class IntroActivity : AppCompatActivity() {
    private lateinit var introActivityViewModel: IntroActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        IntroActivityUI().setContentView(this)
        introActivityViewModel = ViewModelProviders.of(this).get(IntroActivityViewModel::class.java)

        introActivityViewModel.permissionRequest.observe(this, Observer { permission ->
            TODO("ask for permission, and then call viewModel.onPermissionResult aftwewards")
        })

        GlobalScope.launch(Dispatchers.Main) {
            delay(150)

            if(Prefs.token.isNullOrEmpty()) {
                startActivity<MainActivity>()
            } else {
//                startActivity<ProductMainActivity>()
            }

            finish()
        }
    }

}