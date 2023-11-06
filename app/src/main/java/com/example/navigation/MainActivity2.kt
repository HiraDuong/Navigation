package com.example.navigation

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val textView = findViewById<TextView>(R.id.textView)

        try{
            val param1 = intent.getStringExtra("param1")?.toDouble()
            val param2 = intent.getStringExtra("param2")?.toDouble()

            val result = param1!! + param2!!

            textView.text = "Param 1: $param1\nParam 2: $param2\nResult: $result"

            intent.putExtra("result",result)
            setResult(Activity.RESULT_OK,intent)
        } catch (ex: Exception){
            textView.text = "Failed"
            setResult(Activity.RESULT_CANCELED)
        }

    }
}