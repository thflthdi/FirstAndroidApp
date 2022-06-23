package com.example.ch13_activity

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.ch13_activity.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // 액티비티에 메뉴 추가.
    // 액티비티 메뉴를 구성할 떄 자동 호출
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // 메뉴 만드는 법
        // 1. xml 메뉴를 액티비티 코드에 적용
        menuInflater.inflate(R.menu.menu_add, menu)
        // 2. add()를 이용하여 menu객체에 메뉴 추가
        val menuItem2: MenuItem? = menu?.add(0,1,0,"menu2")
        return super.onCreateOptionsMenu(menu)
    }

    // 메뉴르 선택했을 때의 이벤트 처리
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.menu_add_save -> {
            val intent = intent
            // 인텐트에 데이터 담아서 보내기
            intent.putExtra("result", binding.addEditView.text.toString())
            // 요청을 제대로 처리 했으면 RESULT_OK로 결과 값 전달
            setResult(Activity.RESULT_OK, intent)
            // 현재 화면에 보이는 액티비티 종료
            finish()
            true
        }
        else -> true
    }
}