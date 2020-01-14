package com.example.debit
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.credit.*
import android.widget.ProgressBar
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import java.lang.Exception
import java.time.LocalDateTime
import java.time.LocalTime

class CreditMain : AppCompatActivity() {


    //var telno = intent.getStringExtra("TELNO")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.credit)

        var telno = intent.getStringExtra("TELNO")


        //first row
        imageCar.setOnClickListener(){
            val intent = Intent(this, Credit_fill::class.java)
            intent.putExtra("Type", "Car")
            intent.putExtra("TELNO", telno.toString())
            startActivity(intent)
        }

        imageTelco.setOnClickListener(){
            val intent = Intent(this, Credit_fill::class.java)
            intent.putExtra("Type", "Telco")
            intent.putExtra("TELNO", telno.toString())
            startActivity(intent)
        }
        imageEntertain.setOnClickListener(){
            val intent = Intent(this, Credit_fill::class.java)
            intent.putExtra("Type", "Entertainment")
            intent.putExtra("TELNO", telno.toString())
            startActivity(intent)
        }
        imageAmen.setOnClickListener(){
            val intent = Intent(this, Credit_fill::class.java)
            intent.putExtra("Type", "Amenities")
            intent.putExtra("TELNO", telno.toString())
            startActivity(intent)
        }
        //

        //second row
        imageFood.setOnClickListener(){
            val intent = Intent(this, Credit_fill::class.java)
            intent.putExtra("Type", "Food")
            intent.putExtra("TELNO", telno.toString())
            startActivity(intent)
        }
        imageLoan.setOnClickListener(){
            val intent = Intent(this, Credit_fill::class.java)
            intent.putExtra("Type", "Loan")
            intent.putExtra("TELNO", telno.toString())
            startActivity(intent)
        }
        imageFruit.setOnClickListener(){
            val intent = Intent(this, Credit_fill::class.java)
            intent.putExtra("Type", "Fruit")
            intent.putExtra("TELNO", telno.toString())
            startActivity(intent)
        }
        imageGift.setOnClickListener(){
            val intent = Intent(this, Credit_fill::class.java)
            intent.putExtra("Type", "Gift")
            intent.putExtra("TELNO", telno.toString())
            startActivity(intent)
        }
        //

        //third row
        imageShirt.setOnClickListener(){
            val intent = Intent(this, Credit_fill::class.java)
            intent.putExtra("Type", "Shirt")
            intent.putExtra("TELNO", telno.toString())
            startActivity(intent)
        }
        imageShopping.setOnClickListener(){
            val intent = Intent(this, Credit_fill::class.java)
            intent.putExtra("Type", "Shopping")
            intent.putExtra("TELNO", telno.toString())
            startActivity(intent)
        }
        imageMarkUp.setOnClickListener(){
            val intent = Intent(this, Credit_fill::class.java)
            intent.putExtra("Type", "MarkUp")
            intent.putExtra("TELNO", telno.toString())
            startActivity(intent)
        }

        imageMedic.setOnClickListener(){
            val intent = Intent(this, Credit_fill::class.java)
            intent.putExtra("Type", "Medic")
            intent.putExtra("TELNO", telno.toString())
            startActivity(intent)
        }
        //
        //fourth row
        imageDessert.setOnClickListener(){
            val intent = Intent(this, Credit_fill::class.java)
            intent.putExtra("Type", "Dessert")
            intent.putExtra("TELNO", telno.toString())
            startActivity(intent)
        }
        imageSport.setOnClickListener(){
            val intent = Intent(this, Credit_fill::class.java)
            intent.putExtra("Type", "Sport")
            intent.putExtra("TELNO", telno.toString())
            startActivity(intent)
        }
        imageStudy.setOnClickListener(){
            val intent = Intent(this, Credit_fill::class.java)
            intent.putExtra("Type", "Study")
            intent.putExtra("TELNO", telno.toString())
            startActivity(intent)
        }
        imageTransport.setOnClickListener(){
            val intent = Intent(this, Credit_fill::class.java)
            intent.putExtra("Type", "Transport")
            intent.putExtra("TELNO", telno.toString())
            startActivity(intent)
        }
        //

        buttonBack.setOnClickListener(){
            val intent = Intent(this,main_menu_support::class.java)
            startActivity(intent)
        }

    }

}
