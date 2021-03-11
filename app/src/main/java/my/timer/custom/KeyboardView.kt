package my.timer.custom

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.util.Log.v
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import my.timer.R
import my.timer.databinding.KeyboardBinding


open class KeyboardView : View.OnClickListener, FrameLayout {
//    private var mPasswordField: EditText? = null
    private lateinit var mBinding : KeyboardBinding
    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attr: AttributeSet? = null) : super(context, attr) {
        initView()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        initView()
    }

    /**
     * init View Here
     */
    private fun initView() {
        //*study*
        //아래 주석은 데이타 바인딩 사용 안할 때
//        val rootView = (context
//            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
//            .inflate(R.layout.keyboard, this, true)

        val inflater = LayoutInflater.from(context)
        mBinding = DataBindingUtil.inflate(inflater,R.layout.keyboard,this,
            true)
        mBinding.view = this

        mBinding.t9Key0.setOnClickListener(this);
        mBinding.t9Key1.setOnClickListener(this);
        mBinding.t9Key2.setOnClickListener(this);
        mBinding.t9Key3.setOnClickListener(this);
        mBinding.t9Key4.setOnClickListener(this);
        mBinding.t9Key5.setOnClickListener(this);
        mBinding.t9Key6.setOnClickListener(this);
        mBinding.t9Key7.setOnClickListener(this);
        mBinding.t9Key8.setOnClickListener(this);
        mBinding.t9Key9.setOnClickListener(this);
        mBinding.t9KeyClear.setOnClickListener(this);
        mBinding.t9KeyBackspace.setOnClickListener(this);



//        mPasswordField =  name(R.id.password_field)
//        name<View>(R.id.t9_key_0).setOnClickListener(this);
//        name<View>(R.id.t9_key_1).setOnClickListener(this);
//        name<View>(R.id.t9_key_2).setOnClickListener(this);
//        name<View>(R.id.t9_key_3).setOnClickListener(this);
//        name<View>(R.id.t9_key_4).setOnClickListener(this);
//        name<View>(R.id.t9_key_5).setOnClickListener(this);
//        name<View>(R.id.t9_key_6).setOnClickListener(this);
//        name<View>(R.id.t9_key_7).setOnClickListener(this);
//        name<View>(R.id.t9_key_8).setOnClickListener(this);
//        name<View>(R.id.t9_key_9).setOnClickListener(this);
//        name<View>(R.id.t9_key_clear).setOnClickListener(this);
//        name<View>(R.id.t9_key_backspace).setOnClickListener(this);
        // Load and use rest of views here
        //val awesomeBG = rootView.findViewById<ImageView>(R.id.awesomeBG)

    }

    override fun onClick(v: View?) {
        // handle number button click
        if (v!!.tag != null && "number_button" == v.tag) {
            var clickView = v as TextView
            if(mBinding.secondField.text.length >= 2){
                if(mBinding.passwordField.text.length>=2){
                    return;
                } else{
                    mBinding.passwordField.append(clickView.text)
                }
            } else{
                mBinding.secondField.append(clickView.text)
            }

            return
        }
        when (v?.id) {
            R.id.t9_key_clear -> { // handle clear button
                mBinding.passwordField.setText("")
                mBinding.secondField.setText("")
            }
            R.id.t9_key_backspace -> { // handle backspace button
                // delete one character
                var minuteCount = mBinding.passwordField.text!!.length
                var secondCount = mBinding.secondField.text!!.length
                if (secondCount > 0) {
                    mBinding.secondField.text!!.delete( secondCount- 1, secondCount)
                } else if(minuteCount >0){
                    mBinding.passwordField.text!!.delete(minuteCount - 1, minuteCount)
                }
            }
        }
    }

    protected open fun <T : View?> name(@IdRes id: Int): T {
        return super.findViewById<View>(id) as T
    }
}