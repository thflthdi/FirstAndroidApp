package com.example.ch10_notification

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.media.RingtoneManager
import android.net.Uri
import android.os.*
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

        //button λΉνμ±ν
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
                setTitle("λ΄κ° λ μ’μνλ κ³ΌμΌμ΄ λ­κ²?")
                setMessage("μ¬κ³Ό vs λ³΅μ­μ")
                setPositiveButton("λ³΅μ­μ", eventHandler)
                setNegativeButton("μ¬κ³Ό", eventHandler)
                show()
            }.setCanceledOnTouchOutside(false)
        }

        //μμ URI κ°μ Έμ€κΈ°
        val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        //URIλ‘ μμ κ°μ Έμ€κΈ°
        val ringtone = RingtoneManager.getRingtone(applicationContext, notification)
        //μμ²΄ μμ μ¬μ
        // val player: MediaPlayer = MediaPlayer.create(this, R.raw.fallbackring)

        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                this.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator;
        } else {
            getSystemService(VIBRATOR_SERVICE) as Vibrator
        }
        binding.level2.setOnClickListener {
            levelTwo()

            //μ§λ μΈλ¦¬κΈ°
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)
                )
            } else {
                vibrator.vibrate(500)
            }

            //μλ¦¬
            ringtone.play()
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


    //API LEVEL 30 μ΄μ
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
            setTitle("λ‘!")
            setMessage("λ λ²¨ ${level}λ¨κ³μμ νλ½μ΄λ€ μμ½λ€!")
            setPositiveButton("ok", null)
            show()
        }.setCanceledOnTouchOutside(false)
    }

    fun pickCorrect(level: Int) {
        val eventHandler = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                // getColorμ μ€ λ¨λλ°? μ΄λ κ² μ°λκ² λ§λκ°?
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
            setTitle("λ§μ·λ€!")
            setMessage("${level}λ¨κ³ ν΄λ¦¬μ΄, λ€μ λ λ²¨ λμ ν΄λ³Όλ?")
            setPositiveButton("μ", eventHandler)
            setNegativeButton("μλ", null)
            show()
        }.setCanceledOnTouchOutside(false)
    }

    fun levelTwo() {
        val items = arrayOf<String>("A", "B", "O", "AB")
        AlertDialog.Builder(this).run {
            setIcon(android.R.drawable.ic_menu_view)
            setTitle("λ΄ νμ‘νμ΄ λ­κ²?")
            setItems(items, object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    if (p1 == 1) {
                        pickCorrect(2)
                    } else {
                        pickWrong(2)
                    }
                }
            })
            setNegativeButton("κ·Έλ§ν λ", null)
            show()
        }.setCanceledOnTouchOutside(false)
    }

    fun levelThree() {
        var isCorrect = false
        val items = arrayOf<String>("μΈκ³μμ κ°μ₯ λΉμΌ λ€μ΄μλͺ©κ±Έμ΄", "μκ°μ© λΉνκΈ°", "νμ¬λΌ μ£Όμ", "λμ ν")
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
            setTitle("λ΄κ° λΆμλΌλ©΄ κ°μ₯ λ¨Όμ  λ­ μ΄ κ² κ°μ?")
            setSingleChoiceItems(items, 0, object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    if (p1 == 3) {
                        isCorrect = true
                    } else {
                        isCorrect = false
                    }
                }
            })
            setPositiveButton("μ λ΅ μ ν", eventHandler)
            setNegativeButton("κ·Έλ§ν λ", null)
            show()
        }.setCanceledOnTouchOutside(false)
    }

    fun levelFour() {
        val itemsBoolean = arrayOf<Boolean>(false, true, true, false)
        var answer = arrayOf<Boolean>(false, false, false, false)
        val items = arrayOf<String>("λ°μ΄μ€νμλ μλ¦¬μ¦", "λλ¬Όμ μ² μλ¦¬μ¦", "μ¬μ¦4", "ν¬μΌλͺ¬μ€ν° μ€μΉΌλ Β·λ°μ΄μ¬λ ")
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
            setTitle("λ΄κ° μ’μνλ κ²μμ λͺ¨λ κ³¨λΌλ΄")
            setMultiChoiceItems(items,
                booleanArrayOf(false, false, false, false),
                object : DialogInterface.OnMultiChoiceClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int, p2: Boolean) {
                        Log.d("alert", "${p1}, ${p2}")
                        answer[p1] = p2
                    }
                })
            setPositiveButton("μ λ΅ μ ν", eventHandler)
            setNegativeButton("κ·Έλ§ν λ", null)
            show()
        }.setCanceledOnTouchOutside(false)
    }

    fun final() {
        val one_binding = ActivityOneBinding.inflate(layoutInflater)
        val eventHandler = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if (p1 == DialogInterface.BUTTON_POSITIVE) {
                    val str = one_binding.inputLine.text
                    var str_data: String = str.toString().replace(" ", "")
                    if (str_data.equals("μλͺ¬λλ΄λ΄")) {
                        finalCorrect()
                    } else {
                        pickWrong(5)
                    }
                }
            }
        }
        AlertDialog.Builder(this).run {
            setTitle("λ΄κ° κ°μ₯ μ’μνλ λ² λΌ μμ΄μ€ν¬λ¦Ό μ΄λ¦μ?")
            setView(one_binding.root)
            setPositiveButton("μ λ΅", eventHandler)
            setNegativeButton("κ·Έλ§ν λ", null)
            show()
        }.setCanceledOnTouchOutside(false)
    }

    fun finalCorrect() {
        AlertDialog.Builder(this).run {
            setTitle("λͺ¨λ  λ λ²¨μ μλ£νμμ΅λλ€!")
            setMessage("λλ₯Ό μ λ§ μ μλκ΅¬λ κ³ λ§μ")
            setNegativeButton("μλ~", null)
            show()
        }
    }
}