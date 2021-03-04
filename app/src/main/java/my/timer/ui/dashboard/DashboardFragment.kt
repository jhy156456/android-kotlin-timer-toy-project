package my.timer.ui.dashboard

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.HandlerCompat.postDelayed
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.progressindicator.CircularProgressIndicator
import my.timer.R
import my.timer.databinding.FragmentDashboardBinding
import my.timer.utils.EventObserver
import org.jetbrains.anko.db.INTEGER

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
        dashboardViewModel.progress.observe(viewLifecycleOwner, {
            Log.d("jhy", "progress : $it")
            activity
                ?.findViewById<CircularProgressIndicator>(R.id.circular_progress)
                ?.setProgressCompat(it, true)
        })

        dashboardViewModel.saveButtonEvent.observe(viewLifecycleOwner, EventObserver {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.System.canWrite(activity)) {
                        val alertDialog = AlertDialog.Builder(activity)
                            .setTitle("권한 요청")
                            .setMessage("시스템 설정 변경 권한이 필요합니다.\n권한 허용 창으로 이동합니다. 권한을 허용해 주세요.")
                            .setPositiveButton("계속") { dialogInterface: DialogInterface, _: Int ->
                                val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                                intent.data = Uri.parse("package:${activity?.packageName}")
                                startActivityForResult(intent, 0)
                                dialogInterface.dismiss()
                            }.setNegativeButton("취소") { dialogInterface: DialogInterface, _: Int ->
                                Toast.makeText(activity, "권한을 얻지 못하였습니다", Toast.LENGTH_SHORT).show()
                                dialogInterface.cancel()
                            }.create()
                        alertDialog.show()
                    } else {
                        Settings.System.putInt(
                            activity?.contentResolver,
                            Settings.System.SCREEN_OFF_TIMEOUT,
                            100

                        );
//                    Integer.valueOf(dashboardViewModel.content.get())
                    }
                }




        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.System.canWrite(activity)) {
                    Toast.makeText(activity, "권한을 얻었습니다", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, "권한을 얻지 못하였습니다", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}