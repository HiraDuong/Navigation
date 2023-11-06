package com.example.navigation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text1 = findViewById<EditText>(R.id.text1)
        val text2 = findViewById<EditText>(R.id.text2)
        val textResult = findViewById<TextView>(R.id.text_result)


        // Callback Để truyền ngược data về màn hình trươc
        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val result = it.data?.getDoubleExtra("result", 0.0)
                textResult.text = "$result"
            } else {
                textResult.text = "Canceled"
            }
        }
        // Khi ấn nút thì chuyển sang Activity2
        findViewById<Button>(R.id.result_btn).setOnClickListener{
            val intent = Intent(this,MainActivity2::class.java);
            // Lấy giá trị từ 2 input ở mainlayout và truyền tham số sang layout
            intent.putExtra("param1",text1.text.toString())
            intent.putExtra("param2",text2.text.toString())

            launcher.launch(intent)
        }


    }

// main menu- OptionsMenu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
    // Overdrive các đối tượng lựa chọn
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_share) {
            Log.v("TAG", "Share something")
        } else if (item.itemId == R.id.action_download) {
            Log.v("TAG", "Download file")
        } else if (item.itemId == R.id.action_settings) {
            Log.v("TAG", "Open settings")
        }

        return super.onOptionsItemSelected(item)
    }
}