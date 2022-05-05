package com.example.ch10_notification

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.ch10_notification.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //binding
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

}