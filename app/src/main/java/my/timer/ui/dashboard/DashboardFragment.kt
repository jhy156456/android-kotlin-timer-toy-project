package my.timer.ui.dashboard

import android.app.AlertDialog
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.progressindicator.CircularProgressIndicator
import my.timer.R
import my.timer.databinding.FragmentDashboardBinding
import my.timer.receiver.AdminReceiver
import my.timer.utils.EventObserver


class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    lateinit var fragmentDashboardBinding: FragmentDashboardBinding

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
        fragmentDashboardBinding =
            FragmentDashboardBinding.inflate(LayoutInflater.from(context), null, false)
        //*study* 아래코드와 차이점 공부하기
        //fragmentDashboardBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_dashboard,container,false)
        return fragmentDashboardBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //*study*
        //Fragment : 아래 2줄의 코드를 실행하지 않으면 layout에서 onClick 메소드가 실행되지 않는다.
        fragmentDashboardBinding.lifecycleOwner = viewLifecycleOwner
        fragmentDashboardBinding.dashboardViewModel = dashboardViewModel
        requestDevicePolicyManager();
        dashboardViewModel.progress.observe(viewLifecycleOwner, {
            Log.d("jhy", "progress : $it")
            activity
                ?.findViewById<CircularProgressIndicator>(R.id.circular_progress)
                ?.setProgressCompat(it, true)
        })
        dashboardViewModel.checkPermissionEvent.observe(viewLifecycleOwner,EventObserver{

        })
        dashboardViewModel.saveButtonEvent.observe(viewLifecycleOwner, EventObserver {
            val pm = context?.getSystemService(Context.POWER_SERVICE) as PowerManager?
            if (pm!!.isScreenOn) {
                val policy =
                    context?.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager?
                try {
                    policy!!.lockNow()
                } catch (ex: SecurityException) {
                    Toast.makeText(
                        context,
                        getString(R.string.timer_permission),
                        Toast.LENGTH_LONG
                    ).show()
                    val admin = ComponentName(requireContext(),AdminReceiver::class.java)
                    val intent: Intent = Intent(
                        DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN
                    ).putExtra(
                        DevicePolicyManager.EXTRA_DEVICE_ADMIN, admin
                    )
                    context?.startActivity(intent)
                }
            }


        })
    }
    private fun requestDevicePolicyManager(){
        val pm = context?.getSystemService(Context.POWER_SERVICE) as PowerManager?
        if (pm!!.isScreenOn) {
            val policy =
                context?.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager?
            val admin = ComponentName(requireContext(),AdminReceiver::class.java)
            if(!policy!!.isAdminActive(admin)){
                Toast.makeText(
                    context,
                    getString(R.string.timer_permission),
                    Toast.LENGTH_LONG
                ).show()
                val intent: Intent = Intent(
                    DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN
                ).putExtra(
                    DevicePolicyManager.EXTRA_DEVICE_ADMIN, admin
                )
                context?.startActivity(intent)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}