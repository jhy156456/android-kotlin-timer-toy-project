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
import my.timer.R


open class KeyboardView : View.OnClickListener, FrameLayout {
    private var mPasswordField: EditText? = null

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
        val rootView = (context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .inflate(R.layout.keyboard, this, true)
        mPasswordField =  name(R.id.password_field)
        name<View>(R.id.t9_key_0).setOnClickListener(this);
        name<View>(R.id.t9_key_1).setOnClickListener(this);
        name<View>(R.id.t9_key_2).setOnClickListener(this);
        name<View>(R.id.t9_key_3).setOnClickListener(this);
        name<View>(R.id.t9_key_4).setOnClickListener(this);
        name<View>(R.id.t9_key_5).setOnClickListener(this);
        name<View>(R.id.t9_key_6).setOnClickListener(this);
        name<View>(R.id.t9_key_7).setOnClickListener(this);
        name<View>(R.id.t9_key_8).setOnClickListener(this);
        name<View>(R.id.t9_key_9).setOnClickListener(this);
        name<View>(R.id.t9_key_clear).setOnClickListener(this);
        name<View>(R.id.t9_key_backspace).setOnClickListener(this);
        // Load and use rest of views here
        //val awesomeBG = rootView.findViewById<ImageView>(R.id.awesomeBG)

    }

    override fun onClick(v: View?) {
        // handle number button click
        if (v!!.tag != null && "number_button" == v.tag) {
            var clickView = v as TextView
            mPasswordField!!.append(clickView.text)
            return
        }
        when (v?.id) {
            R.id.t9_key_clear -> { // handle clear button
                mPasswordField?.setText("")
            }
            R.id.t9_key_backspace -> { // handle backspace button
                // delete one character
                var charCount = mPasswordField?.text!!.length
                if (charCount > 0) {
                    mPasswordField?.text!!.delete(charCount - 1, charCount)
                }
            }
        }
    }

    protected open fun <T : View?> name(@IdRes id: Int): T {
        return super.findViewById<View>(id) as T
    }
}