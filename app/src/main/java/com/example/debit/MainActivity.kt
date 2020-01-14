package com.example.debit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private var preferenceHelper: PreferenceHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferenceHelper = PreferenceHelper(this)

        //if (preferenceHelper!!.getIsLogin()) {
         //   val intent = Intent(this@MainActivity, main_menu_support::javaClass)// change to after login page
        //    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        //    startActivity(intent)
       //     this.finish()
       // }

        val registerButton = findViewById<Button>(R.id.buttonRegister)
        val signupButton = findViewById<Button>(R.id.buttonLogin)

        registerButton.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        signupButton.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
