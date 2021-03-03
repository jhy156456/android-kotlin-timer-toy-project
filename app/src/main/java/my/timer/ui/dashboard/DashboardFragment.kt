package my.timer.ui.dashboard

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.HandlerCompat.postDelayed
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.progressindicator.CircularProgressIndicator
import my.timer.R
import my.timer.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    lateinit var fragmentDashboardBinding :FragmentDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentDashboardBinding = FragmentDashboardBinding.inflate(LayoutInflater.from(context),null,false)
        return fragmentDashboardBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //*study*
        //Fragment : 아래 2줄의 코드를 실행하지 않으면 layout에서 onClick 메소드가 실행되지 않는다.
        fragmentDashboardBinding.lifecycleOwner = viewLifecycleOwner
        fragmentDashboardBinding.dashboardViewModel = dashboardViewModel
        dashboardViewModel.progress.observe(viewLifecycleOwner,{
            activity
                ?.findViewById<CircularProgressIndicator>(R.id.circular_progress)
                ?.setProgressCompat(it, true)
        })

        dashboardViewModel.saveButtonEvent.observe(viewLifecycleOwner,{


        })
    }

}