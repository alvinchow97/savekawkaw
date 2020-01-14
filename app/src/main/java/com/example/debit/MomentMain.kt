package com.example.debit

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.debit.MySingleton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.moment_main.*
import org.json.JSONArray
import org.json.JSONObject

class MomentMain : AppCompatActivity() {
    private val REQUEST_CODE = 1
    lateinit var topicList: ArrayList<Topic>
    lateinit var adapter: MomentListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.moment_main)
        var telno = intent.getStringExtra("TELNO")
        topicList = ArrayList<Topic>()
        adapter = MomentListAdapter(this)
        adapter.setTopic(topicList)

        //val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        //recyclerView.adapter = adapter
        //recyclerView.layoutManager=LinearLayoutManager(this)

        fab.setOnClickListener{
            val intent = Intent(this, CreateMoment::class.java)
            intent.putExtra("TELNO",telno.toString())
            startActivityForResult(intent, REQUEST_CODE)
        }


    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when(item.itemId) {
            R.id.action_sync -> {
                //topicList.add(Topic("Good Night","In TARUC","0102895540"))
                syncContact()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       if(requestCode==REQUEST_CODE){
           if(resultCode==Activity.RESULT_OK){
               data?.let{
                   val topic = Topic(it.getStringExtra(CreateMoment.EXTRA_TOPIC),it.getStringExtra(CreateMoment.EXTRA_CONTENT), it.getStringExtra(CreateMoment.EXTRA_TELNO)) //TODO
                   createTopic(topic)
               }
           }
       }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun createTopic(topic:Topic) {
        val url = getString(R.string.url_server) + getString(R.string.url_topic_create) + "?topictitle=" +topic.topictitle  +
                "&topiccontent=" + topic.topiccontent + "&usertel=0102938959" //TODO


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
                            Toast.makeText(applicationContext, "Moment Posted", Toast.LENGTH_LONG).show()
                            //Add record to user list
                            topicList.add(topic)

                        }else{
                            Toast.makeText(applicationContext, "Record not saved", Toast.LENGTH_LONG).show()

                        }

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


    private fun syncContact() {
        val url = getString(R.string.url_server) + getString(R.string.url_topic_read)

        //Delete all user records
        topicList.clear()

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
                        for(i in 0..size-1){
                            var jsonUser: JSONObject = jsonArray.getJSONObject(i)
                            var topic = Topic(jsonUser.getString("topictitle"),jsonUser.getString("topiccontent"),jsonUser.getString("usertel"))

                            topicList.add(topic)
                        }

                        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager=LinearLayoutManager(this)
                        Toast.makeText(applicationContext, "Record found :" + size, Toast.LENGTH_LONG).show()

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




