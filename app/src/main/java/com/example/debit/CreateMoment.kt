package com.example.debit


import android.app.Activity
import android.content.Intent

import android.os.Bundle

import android.text.TextUtils
import android.util.Log

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.debit.MySingleton

import kotlinx.android.synthetic.main.activity_create_moment.*
import org.json.JSONObject


class CreateMoment : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_moment)
        var telno = intent.getStringExtra("TELNO")
        btnPost.setOnClickListener {

            savePost(telno);
        }
    }
    private fun savePost(telno:String){
        if(TextUtils.isEmpty(editTextTopic.text)){
            editTextTopic.setError(getString(R.string.error_value_required))
            return
        }
        if(TextUtils.isEmpty(editTextContent.text)){
            editTextContent.setError(getString(R.string.error_value_required))
            return
        }
        val topic = editTextTopic.text.toString()
        val content = editTextContent.text.toString()
        val telno:String = telno.toString()
       val intent= Intent()

        intent.putExtra(EXTRA_TOPIC, topic)
        intent.putExtra(EXTRA_CONTENT, content)
        intent.putExtra(EXTRA_TELNO,telno)
        setResult(Activity.RESULT_OK, intent)
        
        finish()
    }
    companion object{
        const val EXTRA_TOPIC = "com.example.debit.TOPIC"
        const val EXTRA_CONTENT = "com.example.debit.CONTENT"
        const val EXTRA_TELNO ="com.example.debit.TELNO"
    }
}
