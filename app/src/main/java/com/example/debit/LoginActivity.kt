@file:Suppress("DEPRECATION")

package com.example.debit

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson

class LoginActivity : AppCompatActivity() {
    internal var LoginURL = "https://savekawkaw.000webhostapp.com/API/AppUser/login.php"
    private var etTelno: EditText? = null
    private var etPassword: EditText? = null
    private var btnLogin: Button? = null
    private var tvRegister: TextView? = null
    private val LoginTask = 1
    private var preferenceHelper: PreferenceHelper? = null
    internal var CheckEditText: Boolean? = null
    private var mProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        preferenceHelper = PreferenceHelper(this)

        etTelno = findViewById<View>(R.id.editTextTelno) as EditText
        etPassword = findViewById<View>(R.id.editTextPassword) as EditText

        btnLogin = findViewById<View>(R.id.buttonLogin) as Button
        tvRegister = findViewById<View>(R.id.textViewRegister) as TextView

        tvRegister!!.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

        btnLogin!!.setOnClickListener {
            CheckEditTextIsEmptyOrNot()
            if (CheckEditText!!) {

                login()

            } else {

                Toast.makeText(
                    this@LoginActivity,
                    "Please fill all form fields.",
                    Toast.LENGTH_LONG
                ).show()

            }
        }
    }

    fun CheckEditTextIsEmptyOrNot() {

        etTelno = findViewById<View>(R.id.editTextTelno) as EditText
        etPassword = findViewById<View>(R.id.editTextPassword) as EditText

        CheckEditText =
            !(TextUtils.isEmpty(etTelno!!.text.toString()) || TextUtils.isEmpty(etPassword!!.text.toString()))
    }

    @Throws(IOException::class, JSONException::class)
    private fun login() {

        showSimpleProgressDialog(this@LoginActivity, null, "Loading...", false)

        try {

            Fuel.get(LoginURL, listOf(
                "userTel" to  etTelno!!.text.toString()
                , "userPsw" to  etPassword!!.text.toString()
            )).responseJson { request, response, result ->
                Log.d("plzzzzz", result.get().content)
                onTaskCompleted(result.get().content,LoginTask)
            }
        } catch (e: Exception) {

        } finally {

        }
    }

    private fun onTaskCompleted(response: String, task: Int) {
        Log.d("responseJson", response)
        removeSimpleProgressDialog()  //will remove progress dialog
        when (task) {
            LoginTask -> if (isSuccess(response)) {
                saveInfo(response)
                Toast.makeText(this@LoginActivity, "Login Successfully!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@LoginActivity, main_menu_support::class.java) // change to after login page
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                //TODO
                intent.putExtra("TELNO", etTelno?.text.toString())
                startActivity(intent)
                this.finish()
            } else {
                Toast.makeText(this@LoginActivity, getErrorMessage(response), Toast.LENGTH_SHORT).show()
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