package my.timer.custom

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingComponent
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import kotlinx.android.synthetic.main.keyboard.view.*

object KeyboardViewReverseBinding  {

    //*study*
    //BindingAdapter는 데이터가 변경되었을 때 수행하고 싶은 작업을 작성
    @BindingAdapter("content")
    @JvmStatic
    fun setKeyboardViewContent(view: KeyboardView, content: String?) {
        val old = view.password_field.text.toString()
        if (old != content) {
            view.password_field.setText(content)
        }
    }
    @JvmStatic
    @BindingAdapter("contentAttrChanged")
    fun setKeyboardViewInverseBindingListener(view: KeyboardView, listener: InverseBindingListener?) {
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }
            override fun afterTextChanged(editable: Editable) {
                listener?.onChange()
            }
        }
        view.password_field.addTextChangedListener(watcher)
    }
    //*study*
    //InverseBindingAdpater 메서드는 역으로 레이아웃의 사용자 정의 속성값이 변경되었을 때 뷰 모델 등과 같은
    //레이아웃 변수에 변경 사항을 전달하여 양방향 바인딩이 구현될 수 있게 한다.
    @InverseBindingAdapter(attribute = "content",event = "contentAttrChanged")
    @JvmStatic
    fun getContent(view: KeyboardView): String {
        return view.password_field.text.toString()
    }
}