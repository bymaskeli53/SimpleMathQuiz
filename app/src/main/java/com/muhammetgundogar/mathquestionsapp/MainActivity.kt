package com.muhammetgundogar.mathquestionsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    var result:Int?=null
    var handler = Handler()
    var runnable = Runnable {  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        buttonStart.setOnClickListener {
            var countDownTimer = object : CountDownTimer(30000,1000){
                override fun onTick(millisUntilFinished: Long) {
                    timeText.text = "Time: " + millisUntilFinished/1000
                }

                override fun onFinish() {
                    timeText.text = "Time: 0"
                    handler.removeCallbacks(runnable)
                    textViewScore.text = "0"
                    timeText.text = "Finished"
                    textViewNumber1.visibility = View.INVISIBLE
                    textViewNumber2.visibility = View.INVISIBLE
                    textViewOperation.visibility = View.INVISIBLE

                    val alert = AlertDialog.Builder(this@MainActivity)

                    alert.setTitle("Game Over")
                    alert.setMessage("Restart The Game?")
                    alert.setPositiveButton("Yes") {dialog, which ->
                        //Restart
                        val intent = intent
                        finish()
                        startActivity(intent)


                    }

                    alert.setNegativeButton("No") {dialog, which ->
                        Toast.makeText(this@MainActivity,"Game Over", Toast.LENGTH_LONG).show()
                    }

                    alert.show()
                }


            }.start()
            randomOperator()
            buttonStart.visibility = View.INVISIBLE
        }
        var score = 0
        buttonOk.setOnClickListener {
            if (editTextResult.text.toString().toInt() == result ) {
                score += 1
                textViewScore.text = "Your Score : $score"
            }

        }



    }
    fun randomOperator() {

        var handler = Handler(Looper.getMainLooper())
        var runnable = object:Runnable {
            override fun run() {
                var random = Random()
                var firstNumber = random.nextInt(11)
                textViewNumber1.text = firstNumber.toString()
                var secondNumber = random.nextInt(11)
                textViewNumber2.text = secondNumber.toString()
                var operatorNumber = random.nextInt(4)

                 when (operatorNumber){
                    0 ->{result = firstNumber + secondNumber
                         textViewOperation.text = "+" }
                    1 -> {result = firstNumber - secondNumber
                          textViewOperation.text = "-" }
                    2 -> {result = firstNumber * secondNumber
                          textViewOperation.text = "*" }
                    3 -> {result = firstNumber / secondNumber
                          textViewOperation.text = "/"}


                    else -> { println("random object did not work")
                    }
                }


                handler.postDelayed(this,3000)
            }
        }
        handler.post(runnable)

    }
}