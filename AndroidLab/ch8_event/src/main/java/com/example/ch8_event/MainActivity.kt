package com.example.ch8_event

import android.os.Bundle
import android.os.SystemClock
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ch8_event.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var initTime = 0L
    var pauseTime = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startBtn.setOnClickListener {
            binding.chronometer.base = SystemClock.elapsedRealtime() + pauseTime
            /***
             * currentTimeMillis()는 1970.01.01 자정부터 현재 시간까지 간격을 ms로 출력한다.
             * elapsedRealtime()은 디바이스를 부팅한 후 부터 현재 시간까지의 간격을 ms단위로 출력한다.
             * elapsedRealtime() 사용 이유
             *  사용자가 현재 시간을 임의로 바꿨을 경우, elapsedRealtime은 디바이스 부팅 후 부터의 시간을 측정하기 때문에
             *  항상 정확한 시간을 출력할 수 있다.
             * ***/
            binding.chronometer.start()

            binding.stopBtn.isEnabled = true
            binding.resetBtn.isEnabled = true
            binding.startBtn.isEnabled = false
        }

        binding.stopBtn.setOnClickListener {

            pauseTime = binding.chronometer.base - SystemClock.elapsedRealtime()
            binding.chronometer.stop()

            binding.stopBtn.isEnabled = false
            binding.resetBtn.isEnabled = true
            binding.startBtn.isEnabled = true
        }

        binding.resetBtn.setOnClickListener {
            pauseTime = 0L
            binding.chronometer.base = SystemClock.elapsedRealtime()
            binding.chronometer.stop()
            binding.stopBtn.isEnabled = false
            binding.resetBtn.isEnabled = false
            binding.startBtn.isEnabled = true
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode === KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - initTime > 3000) {
                Toast.makeText(this, "종료하려면 한 번 더 누르세요", Toast.LENGTH_SHORT).show()
                initTime = System.currentTimeMillis()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}