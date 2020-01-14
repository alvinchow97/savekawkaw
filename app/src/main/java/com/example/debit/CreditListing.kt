package com.example.debit
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.credit_fill.*
import kotlinx.android.synthetic.main.credit_record.*
import kotlinx.android.synthetic.main.expenses_list.*
import kotlinx.android.synthetic.main.main_credit_listing.*
import org.json.JSONArray
import org.json.JSONObject


class CreditListing :AppCompatActivity() {

    lateinit var creditList: ArrayList<Credit>
    lateinit var adapter: DebitAdapter
   // val telno:String = intent.getStringExtra("TELNO")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_credit_listing)

        creditList = ArrayList<Credit>()
        adapter = DebitAdapter(this)
        adapter.setCredit(creditList)
        syncCredits()

        //creditList.add(Credit("35.10","Car","JAN"))
        buttonBack.setOnClickListener {
            //syncCredits()
            val intent = Intent(this, main_menu_support::class.java)
            startActivity(intent)
        }
    }


    private fun syncCredits() {


        val url = getString(R.string.url_credit_retrieve) + "?userid=0105588993" //+ //telno

        //Delete all user records
        creditList.clear()

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
                            var user: Credit = Credit(jsonUser.getString("creditamount"),
                                jsonUser.getString("credittype"),jsonUser.getString("creditdate"))

                            creditList.add(user)
                        }
                        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewCredit)
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager = LinearLayoutManager(this)

                        Toast.makeText(applicationContext, "Record found :" + size, Toast.LENGTH_LONG).show()
                        //progress.visibility = View.GONE
                    }

                   // adapter = new DebitAdapter(creditList,getApplicationContent()):
                   // recyclerViewCredit.setAdapter(adapter)
                }catch (e:Exception){
                    Log.d("Main", "Response: %s".format(e.message.toString()))
                    //progress.visibility = View.GONE

                }
            },
            Response.ErrorListener { error ->
                Log.d("Main", "Response: %s".format(error.message.toString()))
                //progress.visibility = View.GONE
            }
        )

        //Volley request policy, only one time request
        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            0, //no retry
            1f
        )

        // Access the RequestQueue through your singleton class.
        jsonObjectRequest.tag = TAG
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onBackPressed() {


        MySingleton.getInstance(this).cancelRequest(TAG)
        super.onBackPressed()
    }

    private fun isConnected(): Boolean{
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    companion object{
        const val TAG = "com.example.debit"
    }


}
