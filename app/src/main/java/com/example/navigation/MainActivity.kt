package com.example.navigation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.view.ActionMode
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    var items = arrayListOf<String>()
    var actionMode: ActionMode? = null
    private val actionModeCallback = object : ActionMode.Callback {
        // Called when the action mode is created. startActionMode() is called.
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            // Inflate a menu resource providing context menu items.
            val inflater: MenuInflater = mode.menuInflater
            inflater.inflate(R.menu.sub_menu, menu)
            return true
        }

        // Called each time the action mode is shown. Always called after
        // onCreateActionMode, and might be called multiple times if the mode
        // is invalidated.
        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item.
        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {

            return when (item.itemId) {
                R.id.action_share -> {
                    Log.v("TAG", "share something")
                    mode.finish() // Action picked, so close the CAB.
                    true
                }

                R.id.action_download -> {
                    Log.v("TAG", "dowload something")
                    mode.finish()
                    true
                }

                else -> false
            }
        }

        // Called when the user exits the action mode.
        override fun onDestroyActionMode(mode: ActionMode) {
            actionMode = null
        }
    }

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
        findViewById<Button>(R.id.result_btn).setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java);
            // Lấy giá trị từ 2 input ở mainlayout và truyền tham số sang layout
            intent.putExtra("param1", text1.text.toString())
            intent.putExtra("param2", text2.text.toString())

            launcher.launch(intent)
        }


        // Intent Activity
        findViewById<ImageView>(R.id.img_view).setOnClickListener {
            // call intent
            val callIntent: Intent = Uri.parse("tel:113").let { number ->
                Intent(Intent.ACTION_DIAL, number)
            }
            startActivity(callIntent)
            // website phase intent
//            val webIntent: Intent = Uri.parse("https://www.google.com").let {
//                Intent(Intent.ACTION_VIEW, it)
//            }
//            startActivity(webIntent)
//        }

            // display submenu

        }

        // Context menu
        // registerForContextMenu(findViewById(R.id.imageView))

        repeat(50) { items.add("Item $it") }
        val listView = findViewById<ListView>(R.id.list_view)
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)

        registerForContextMenu(listView)

        findViewById<ImageView>(R.id.img_view).setOnLongClickListener { view ->
            when (actionMode) {
                null -> {
                    // Start the CAB using the ActionMode.Callback defined earlier.
                    //actionMode = activity?.startActionMode(actionModeCallback)
                    actionMode = startSupportActionMode(actionModeCallback)
                    view.isSelected = true
                    true
                }

                else -> false
            }
        }


    }

    // main menu- OptionsMenu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
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

    // sub menu - ContextMenu

    override fun onCreateContextMenu(
        menu: ContextMenu, v: View,
        menuInfo: ContextMenu.ContextMenuInfo
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.sub_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {
            R.id.action_share -> {
                Log.v("TAG", "share something")
                true
            }

            R.id.action_download -> {
                Log.v("TAG", "download something")
                true
            }

            else -> super.onContextItemSelected(item)
        }
    }

}
