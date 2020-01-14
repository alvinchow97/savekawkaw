package com.example.debit

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.credit.*
import kotlinx.android.synthetic.main.my_profile.*
import kotlinx.android.synthetic.main.my_profile.buttonBack
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class UserProfileActivity : AppCompatActivity() {

    private val REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_profile)

        readProfile("0102938059")

        buttonUpd.setOnClickListener{
            saveUser()
        }

        buttonBack.setOnClickListener(){
            val intent = Intent(this,main_menu_support::class.java)
            startActivity(intent)
        }

    }

    private fun readProfile(userTel:String) {
        var token = getSharedPreferences("userTel", Context.MODE_PRIVATE)
        val readURL = getString(R.string.url_server) + getString(R.string.url_AppUser_readOne) + "?userTel=" + userTel

        // output = (TextView) findViewById(R.id.jsonData);
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, readURL, null,
            Response.Listener { response ->
                try {
                    if (response != null) {
                        val strResponse = response.toString()
                        val jsonResponse  = JSONObject(strResponse)

                        if(jsonResponse != null){
                            val jsonArray: JSONArray = jsonResponse.getJSONArray("records")

                            var jsonUser: JSONObject = jsonArray.getJSONObject(0)
                            Toast.makeText(this, "in", Toast.LENGTH_SHORT).show()
                            var user: User = User(userTel, jsonUser.getString("userName"),
                                jsonUser.getString("userPsw"), jsonUser.getString("userBd"),
                                jsonUser.getString("userOccupation") )

                            Toast.makeText(this, "Retrieve successfully", Toast.LENGTH_SHORT).show()


                            editTextName.setText(user.name)
                            editTextPsw.setText(user.psw)
                            editTextBd.setText(user.bod)
                            editTextOcc.setText(user.occupation)
                            editTextTel.setText(user.tel)
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { e ->
                Log.e("Volley", "Error" + e.message.toString())
                Toast.makeText(this, "Invalid profile", Toast.LENGTH_SHORT).show()
            }
        )
        //Volley request policy, only one time request
        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            0, //no retry
            1f
        )

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    private fun updateUser(user: User) {
        val url = getString(R.string.url_server) + getString(R.string.url_AppUser_update) + "?userTel=" + user.tel +
                "&userName=" + user.name + "&userBd=" + user.bod + "&userPsw=" + user.psw + "&userOccupation=" + user.occupation

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                // Process the JSON
                try{
                    if(response != null){
                        val strResponse = response.toString()
                        val jsonResponse  = JSONObject(strResponse)
                        val success: String = jsonResponse.get("success").toString()

                        if(success.equals("1")){
                            Toast.makeText(applicationContext, "Profile updated", Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(applicationContext, "Cannot be updated ", Toast.LENGTH_LONG).show()
                        }
                    }
                    else{
                        Toast.makeText(applicationContext, "X", Toast.LENGTH_LONG).show()
                    }
                }catch (e:Exception){
                    Log.d("Main", "Response: %s".format(e.message.toString()))
                }
            },
            Response.ErrorListener { error ->
                Log.d("Main", "Response: %s".format(error.message.toString()))

            }
        )

        //Volley request policy, only one time request
        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            0, //no retry
            1f
        )

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

    private fun saveUser(){

        if(TextUtils.isEmpty(editTextName.text)){
            editTextName.setError(getString(R.string.error_value_required))
            return
        }
        if(TextUtils.isEmpty(editTextBd.text)){
            editTextBd.setError(getString(R.string.error_value_required))
            return
        }
        if(TextUtils.isEmpty(editTextTel.text)){
            editTextTel.setError(getString(R.string.error_value_required))
            return
        }
        if(TextUtils.isEmpty(editTextOcc.text)){
            editTextOcc.setError(getString(R.string.error_value_required))
            return
        }
        if(TextUtils.isEmpty(editTextPsw.text)){
            editTextPsw.setError(getString(R.string.error_value_required))
            return
        }

        try {
            val name = editTextName.text.toString()
            val dob = editTextBd.text.toString()
            val tel = editTextTel.text.toString()
            val occ = editTextOcc.text.toString()
            val psw = editTextPsw.text.toString()

            var userdetails: User = User(tel,name, psw, dob, occ )
            updateUser(userdetails)
        }
        catch(e: Exception){

        }


        //setResult(Activity.RESULT_OK, intent)

        //finish()
    }
}
