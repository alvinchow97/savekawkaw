package com.example.debit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.home.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainPageActivity: AppCompatActivity(){

    private val REQUEST_CODE = 1
    lateinit var recordList: ArrayList<Record>
    lateinit var adapter: RecordListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        //Initialise variables and UI
        recordList = ArrayList<Record>()

        adapter = RecordListAdapter(this)
        adapter.setRecords(recordList)

        //val recyclerView = findViewById<RecyclerView>(R.id.recyclerviewSummary)
        //recyclerView.adapter = adapter
        //recyclerView.layoutManager = LinearLayoutManager(this)

        getUserName("0102938059")
        getSummary()

        pp.setOnClickListener {
            val intent = Intent(this,UserProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getUserName(userTel: String) {
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
                            var user: User = User(userTel, jsonUser.getString("userName"),
                                jsonUser.getString("userPsw"), jsonUser.getString("userBd"),
                                jsonUser.getString("userOccupation") )

                            usernameTextView.text = user.name.toString()
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

    private fun getSummary() {
        val url = getString(R.string.url_server) + getString(R.string.url_ccredit_read)

        //Delete all user records
        recordList.clear()

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                // Process the JSON
                try{
                    if(response != null){
                        val strResponse = response.toString()
                        val jsonResponse  = JSONObject(strResponse)
                        val jsonArray: JSONArray = jsonResponse.getJSONArray("records")
                        val size: Int = jsonArray.length()
                        var sum: Double = 0.0
                        for(i in 0..size-1){
                            var jsonUser: JSONObject = jsonArray.getJSONObject(i)

                            var month = jsonUser.getString("creditdate")
                            var amount = jsonUser.getDouble("creditamount")
                            if(month.equals("JANUARY")){
                                sum += amount
                            }

                        }
                        var summary: Record = Record("JAN", sum)
                        recordList.add(summary)

                        Toast.makeText(applicationContext, "Record found :" + size, Toast.LENGTH_LONG).show()
                        val recyclerView = findViewById<RecyclerView>(R.id.recyclerviewSummary)
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager = LinearLayoutManager(this)
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


}