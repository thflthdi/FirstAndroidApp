package com.example.ch8_event

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ch8_event.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var initTime = 0L
    var pauseTime = 0L
    //pauseTime 변수에 저장한 데이터를 사용하지 않는 이상 필요해보이진 않는다.
    /***
     * 정수 타입
     * Short = 2Byte // 저장 가능 범위 : -3만 2768 ~ 3만 2767
     * Int = 4Byte // 저장 가능 범위 : -21억...  ~ 21억...
     * Long = 8Byte // 저장 가능 범위 : -992경... ~ 992경...
     *
     * 실수 타입
     * Float = 4Byte
     * Double = 8byte
     *
     * byte+short, byte+byte, short+short === int로 자동 형변환
     * ***/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startBtn.setOnClickListener {
//            binding.chronometer.base = SystemClock.elapsedRealtime() + pauseTime
            binding.chronometer.base = SystemClock.elapsedRealtime()
            Log.d("info","base, ${binding.chronometer.base}")
            Log.d("info","realtime ${SystemClock.elapsedRealtime()}")
            Log.d("info","pause $pauseTime")
            Log.d("info", "-----------------start--------------------")

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

//            pauseTime = binding.chronometer.base - SystemClock.elapsedRealtime()
//            pauseTime = SystemClock.elapsedRealtime() - binding.chronometer.base
            // stop은 chronometer의 stop()기능만 사용해도 가능해 보인다.
            Log.d("info","base ${binding.chronometer.base}")
            Log.d("info","real ${SystemClock.elapsedRealtime()}")
            Log.d("info","pasue $pauseTime")
            Log.d("info", "--------------------stop-----------------")
            binding.chronometer.stop()

            binding.stopBtn.isEnabled = false
            binding.resetBtn.isEnabled = true
            binding.startBtn.isEnabled = true
        }

        binding.resetBtn.setOnClickListener {
//            pauseTime = 0L
            binding.chronometer.base = SystemClock.elapsedRealtime()
            // base를 현재 시간(부팅 후 지나가고 있는 시간)으로 할당해야 초기화가 된다.
            Log.d("info","base ${binding.chronometer.base}")
            Log.d("info","real ${SystemClock.elapsedRealtime()}")
            Log.d("info","pause $pauseTime")
            Log.d("info", "----------------reset---------------------")

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