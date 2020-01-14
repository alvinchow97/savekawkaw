package com.example.debit

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.debit.R.layout.main_menu
import kotlinx.android.synthetic.main.main_menu.*

class main_menu_support :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(main_menu)

        val telno:String = intent.getStringExtra("TELNO")
        //TODO
        textView.text = telno.toString()
        button.setOnClickListener(){
            val intent = Intent(this,CreditMain::class.java)
            intent.putExtra("TELNO", telno.toString())
            startActivity(intent)
        }

        button5.setOnClickListener(){
            val intent = Intent(this, CreditListing::class.java)
            intent.putExtra("TELNO", telno.toString())
            startActivity(intent)
        }

        button2.setOnClickListener(){
            val intent = Intent(this, MomentMain::class.java)
            intent.putExtra("TELNO", telno.toString())
            startActivity(intent)
        }

        button4.setOnClickListener(){
            val intent = Intent(this, MainPageActivity::class.java)
            startActivity(intent)
        }

        button6.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


}