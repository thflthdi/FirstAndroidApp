package com.example.firsttodoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var TAG = "MyTag";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addTitleEditView.setText("동후 천재")

        var watcher = MyEditWatcher()
        addTitleEditView.addTextChangedListener(watcher)

        addTitleEditView.setOnEditorActionListener(MyEnterListener())

        submit_btn.setOnClickListener{
            textText.text = addTitleEditView.text
        }
    }


    inner class MyEditWatcher:TextWatcher{
        //텍스트가 변경되기 바로 전에 동작
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            Log.d(TAG, "beforeTextChanged:$p0")

        }

        //텍스트가 변경되는 동시에 동작
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            Log.d(TAG, "onTextChanged:$p0")
        }

        //텍스트가 변경된 이후에 동작
        override fun afterTextChanged(p0: Editable?) {
            // null 체크를 하는 이유: 여러개의 EditTextView를 사용하여 TextWatcher를 사용하는 경우
            // 무한루프가 발생하기 때문에 -- removeTextChangedListener()를 사용하여 제거해야함
            // 귀찮다면 null 체크를 해주자
            if(p0 != null && !p0.toString().equals("")){
                Log.d(TAG, "afterTextChange:$p0")
            }
        }
    }

    inner class MyEnterListener:TextView.OnEditorActionListener{
        override fun onEditorAction(p0: TextView?, actionId: Int, p2: KeyEvent?): Boolean {
            var handled = false //키보드 내림
            if(actionId == EditorInfo.IME_ACTION_DONE) { // 완료 버튼 클릭 됨
                Log.d(TAG,"KeyEvent.KEYCODE_ENTER"+KeyEvent.KEYCODE_ENTER)
                Log.d(TAG, "IME_ACTION_DONE" + p0?.text)
                textText.text = addTitleEditView.text
                addTitleEditView.setText("")
            } else if (actionId == EditorInfo.IME_ACTION_NEXT){
                Log.d(TAG, "IME_ACTION_NEXT" + p0?.text)
                handled = true //키보드 유지
            }
            return handled
        }
    }
}


