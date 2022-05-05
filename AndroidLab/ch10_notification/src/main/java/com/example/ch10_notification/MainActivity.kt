package com.example.ch10_notification

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.ch10_notification.databinding.ActivityMainBinding
import com.example.ch10_notification.databinding.ActivityOneBinding
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //binding
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //button 비활성화
        binding.level2.isEnabled = false
        binding.level3.isEnabled = false
        binding.level4.isEnabled = false
        binding.level5.isEnabled = false

        //toast
        binding.toastBtn.setOnClickListener {
            val toast = makeText(this, "toast test", Toast.LENGTH_SHORT)
            toast.show()
        }

        //toast callback
        binding.toastCallbackBtn.setOnClickListener {
            Log.d("toast", "get ready ${Build.VERSION.SDK_INT}")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                showToast()
            }
        }

        //date picker dialog
        binding.datePickerBtn.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year: Int = calendar.get(Calendar.YEAR)
            val month: Int = calendar.get(Calendar.MONTH)
            val day: Int = calendar.get(Calendar.DATE)
            Log.d("date", "get ready")
            DatePickerDialog(
                this,
                { p0, p1, p2, p3 -> Log.d("date", "year: $p1 ,month: ${p2 + 1} ,day: $p3") },
                year, month, day
            ).show()
        }

        //time picker dialog
        binding.timePickerBtn.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour: Int = calendar.get(Calendar.HOUR_OF_DAY)
            val minute: Int = calendar.get(Calendar.MINUTE)
            TimePickerDialog(
                this,
                { p0, p1, p2 -> Log.d("date", "time: $p1, minute: $p2") },
                hour, minute, false
            ).show()
        }

        //alert dialog
        val eventHandler = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if (p1 == DialogInterface.BUTTON_POSITIVE) {
                    pickCorrect(1)
                } else {
                    pickWrong(1)
                }
            }
        }
        binding.alertDefaultBtn.setOnClickListener {
            AlertDialog.Builder(this).run {
                setIcon(android.R.drawable.ic_menu_view)
                setTitle("내가 더 좋아하는 과일이 뭐게?")
                setMessage("사과 vs 복숭아")
                setPositiveButton("복숭아", eventHandler)
                setNegativeButton("사과", eventHandler)
                show()
            }.setCanceledOnTouchOutside(false)
        }

        binding.level2.setOnClickListener {
            levelTwo()
        }
        binding.level3.setOnClickListener {
            levelThree()
        }
        binding.level4.setOnClickListener {
            levelFour()
        }

        binding.level5.setOnClickListener {
            final()
        }
    }


    //API LEVEL 30 이상
    @RequiresApi(Build.VERSION_CODES.R)
    fun showToast() {
        val toast = makeText(this, "toast callback test", Toast.LENGTH_LONG)
        toast.addCallback(
            object : Toast.Callback() {
                override fun onToastHidden() {
                    super.onToastHidden()
                    Log.d("toast", "toast hidden")
                }

                override fun onToastShown() {
                    super.onToastShown()
                    Log.d("toast", "toast shown")
                }
            }
        )
        toast.show()
    }

    fun pickWrong(level: Int) {
        AlertDialog.Builder(this).run {
            setIcon(android.R.drawable.ic_menu_view)
            setTitle("땡!")
            setMessage("레벨 ${level}단계에서 탈락이네 아쉽다!")
            setPositiveButton("ok", null)
            show()
        }.setCanceledOnTouchOutside(false)
    }

    fun pickCorrect(level: Int) {
        val eventHandler = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                // getColor에 줄 뜨는데? 이렇게 쓰는게 맞는가?
                val color = getResources().getColor(R.color.purple_500)
                if (p1 == DialogInterface.BUTTON_POSITIVE) {
                    if (level == 1) {
                        val btn = findViewById<Button>(R.id.level_2)
                        btn.isEnabled = true
                        btn.setBackgroundColor(color)
                        levelTwo()
                    } else if (level == 2) {
                        val btn = findViewById<Button>(R.id.level_3)
                        btn.isEnabled = true
                        btn.setBackgroundColor(color)
                        levelThree()
                    } else if (level == 3) {
                        val btn = findViewById<Button>(R.id.level_4)
                        btn.isEnabled = true
                        btn.setBackgroundColor(color)
                        levelFour()
                    } else if (level == 4) {
                        val btn = findViewById<Button>(R.id.level_5)
                        btn.isEnabled = true
                        btn.setBackgroundColor(color)
                        final()
                    }
                }
            }
        }
        AlertDialog.Builder(this).run {
            setIcon(android.R.drawable.ic_menu_view)
            setTitle("맞췄다!")
            setMessage("${level}단계 클리어, 다음 레벨 도전해볼래?")
            setPositiveButton("응", eventHandler)
            setNegativeButton("아니", null)
            show()
        }.setCanceledOnTouchOutside(false)
    }

    fun levelTwo() {
        val items = arrayOf<String>("A", "B", "O", "AB")
        AlertDialog.Builder(this).run {
            setIcon(android.R.drawable.ic_menu_view)
            setTitle("내 혈액형이 뭐게?")
            setItems(items, object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    if (p1 == 1) {
                        pickCorrect(2)
                    } else {
                        pickWrong(2)
                    }
                }
            })
            setNegativeButton("그만할래", null)
            show()
        }.setCanceledOnTouchOutside(false)
    }

    fun levelThree() {
        var isCorrect = false
        val items = arrayOf<String>("세계에서 가장 비싼 다이아목걸이", "자가용 비행기", "테슬라 주식", "대저택")
        val eventHandler = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if (p1 == DialogInterface.BUTTON_POSITIVE) {
                    if (isCorrect) {
                        pickCorrect(3)
                    } else {
                        pickWrong(3)
                    }
                }
            }
        }
        AlertDialog.Builder(this).run {
            setIcon(android.R.drawable.ic_menu_view)
            setTitle("내가 부자라면 가장 먼저 뭘 살 것 같아?")
            setSingleChoiceItems(items, 0, object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    if (p1 == 3) {
                        isCorrect = true
                    } else {
                        isCorrect = false
                    }
                }
            })
            setPositiveButton("정답 선택", eventHandler)
            setNegativeButton("그만할래", null)
            show()
        }.setCanceledOnTouchOutside(false)
    }

    fun levelFour() {
        val itemsBoolean = arrayOf<Boolean>(false, true, true, false)
        var answer = arrayOf<Boolean>(false, false, false, false)
        val items = arrayOf<String>("바이오하자드 시리즈", "동물의 숲 시리즈", "심즈4", "포켓몬스터 스칼렛·바이올렛")
        val eventHandler = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if (p1 == DialogInterface.BUTTON_POSITIVE) {
                    for ((index, value) in answer.withIndex()) {
                        Log.d("alert", "${index}, ${itemsBoolean[index]}, ${value}")
                        if (itemsBoolean[index] !== value) {
                            pickWrong(4)
                            break
                        } else if (itemsBoolean[index] === value && index === (answer.size - 1)) {
                            pickCorrect(4)
                        }
                    }

                }
            }
        }
        AlertDialog.Builder(this).run {
            setIcon(android.R.drawable.ic_menu_view)
            setTitle("내가 좋아하는 게임을 모두 골라봐")
            setMultiChoiceItems(items,
                booleanArrayOf(false, false, false, false),
                object : DialogInterface.OnMultiChoiceClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int, p2: Boolean) {
                        Log.d("alert", "${p1}, ${p2}")
                        answer[p1] = p2
                    }
                })
            setPositiveButton("정답 선택", eventHandler)
            setNegativeButton("그만할래", null)
            show()
        }.setCanceledOnTouchOutside(false)
    }

    fun final() {
        val one_binding = ActivityOneBinding.inflate(layoutInflater)
        val eventHandler = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if (p1 == DialogInterface.BUTTON_POSITIVE) {
                    val str = one_binding.inputLine.text
                    var str_data:String = str.toString().replace(" ","")
                    if(str_data.equals("아몬드봉봉")){
                        finalCorrect()
                    }else{
                        pickWrong(5)
                    }
                }
            }
        }
        AlertDialog.Builder(this).run {
            setTitle("내가 가장 좋아하는 베라 아이스크림 이름은?")
            setView(one_binding.root)
            setPositiveButton("정답", eventHandler)
            setNegativeButton("그만할래", null)
            show()
        }.setCanceledOnTouchOutside(false)
    }

    fun finalCorrect() {
        AlertDialog.Builder(this).run{
            setTitle("모든 레벨을 완료하였습니다!")
            setMessage("나를 정말 잘 아는구나 고마워")
            setNegativeButton("안녕~", null)
            show()
        }
    }
}