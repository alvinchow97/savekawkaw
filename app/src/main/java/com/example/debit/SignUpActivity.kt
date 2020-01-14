@file:Suppress("DEPRECATION")

package com.example.debit

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson

class SignUpActivity : AppCompatActivity() {
    internal var RegisterURL = "https://savekawkaw.000webhostapp.com/API/AppUser/register.php"
    private var editTextTelno : EditText? = null
    private var editTextPassword :EditText? = null
    private var editTextNickname:EditText? = null
    private var editTextOccupation:EditText? = null
    private var editTextBirthday:EditText? = null
    private var buttonRegister : Button? = null
    private var textViewLogin: TextView? = null
    private var preferenceHelper: PreferenceHelper? = null
    private val RegTask = 1
    private var mProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        preferenceHelper = PreferenceHelper(this)

        //if (preferenceHelper!!.getIsLogin()) {
        //    val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
        //    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        //    startActivity(intent)
        //    this.finish()
        //}

        editTextTelno = findViewById<View>(R.id.editTextTelno) as EditText
        editTextPassword = findViewById<View>(R.id.editTextPassword) as EditText
        editTextNickname = findViewById<View>(R.id.editTextNickname) as EditText
        editTextOccupation = findViewById<View>(R.id.editTextOccupation) as EditText
        editTextBirthday = findViewById<View>(R.id.editTextBirthday) as EditText

        buttonRegister = findViewById<View>(R.id.buttonRegister) as Button
        textViewLogin = findViewById<View>(R.id.textViewLogin) as TextView

        textViewLogin!!.setOnClickListener {
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        buttonRegister!!.setOnClickListener {
            try {
                register()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

    }
    @Throws(IOException::class, JSONException::class)
    private fun register() {

        showSimpleProgressDialog(this@SignUpActivity, null, "Loading...", false)

        try {

            Fuel.get(RegisterURL, listOf("userTel" to  editTextTelno!!.text.toString()
                , "userPsw" to  editTextPassword!!.text.toString()
                , "userName" to  editTextNickname!!.text.toString()
                , "userOccupation" to  editTextOccupation!!.text.toString()
                , "userBd" to  editTextBirthday!!.text.toString()
            )).responseJson { request, response, result ->
                onTaskCompleted(result.get().content,RegTask)
            }
        } catch (e: Exception) {

        } finally {

        }

    }

    private fun onTaskCompleted(response: String, task: Int) {
        Log.d("responseJson", response)
        removeSimpleProgressDialog()  //will remove progress dialog
        when (task) {
            RegTask -> if (isSuccess(response)) {
                saveInfo(response)
                Toast.makeText(this@SignUpActivity, "Registered Successfully!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                this.finish()
            } else {
                Toast.makeText(this@SignUpActivity, getErrorMessage(response), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun saveInfo(response: String) {
        preferenceHelper!!.putIsLogin(true)
        try {
            val jsonObject = JSONObject(response)
            if (jsonObject.getString("status") == "true") {
                val dataArray = jsonObject.getJSONArray("data")
                for (i in 0 until dataArray.length()) {

                    val dataobj = dataArray.getJSONObject(i)
                    preferenceHelper!!.putTelNo(dataobj.getString("telno"))
                    preferenceHelper!!.putPassword(dataobj.getString("password"))
                    preferenceHelper!!.putNickname(dataobj.getString("nickname"))
                    preferenceHelper!!.putOccupation(dataobj.getString("occupation"))
                    preferenceHelper!!.putBirthday(dataobj.getString("birthday"))
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    fun isSuccess(response: String): Boolean {
        try {
            val jsonObject = JSONObject(response)
            return jsonObject.optString("status") == "true"

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return false
    }

    fun getErrorMessage(response: String): String {
        try {
            val jsonObject = JSONObject(response)
            return jsonObject.getString("message")

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return "No data"
    }

    fun showSimpleProgressDialog(context: Context, title: String?, msg: String, isCancelable: Boolean) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg)
                mProgressDialog!!.setCancelable(isCancelable)
            }
            if (!mProgressDialog!!.isShowing) {
                mProgressDialog!!.show()
            }

        } catch (ie: IllegalArgumentException) {
            ie.printStackTrace()
        } catch (re: RuntimeException) {
            re.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog!!.isShowing) {
                    mProgressDialog!!.dismiss()
                    mProgressDialog = null
                }
            }
        } catch (ie: IllegalArgumentException) {
            ie.printStackTrace()

        } catch (re: RuntimeException) {
            re.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
