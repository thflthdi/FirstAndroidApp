package com.example.firsttodoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val InputTodo = addTitleEditView.text.toString()
        println("inputTodo" + InputTodo)
        if(InputTodo.length > 0){
            submit_btn.setOnClickListener { Toast.makeText(this, "Toast", Toast.LENGTH_SHORT).show() }
        }
    }


    // 내부 저장소에서 데이터 가져오기


}
