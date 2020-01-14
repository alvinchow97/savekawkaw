package com.example.debit
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.credit_fill.*
import java.time.LocalDateTime
import org.json.JSONArray
import org.json.JSONObject


class Credit_fill :AppCompatActivity(){




    override fun onCreate(savedInstanceState: Bundle?) {
        val telno:String = intent.getStringExtra("TELNO")
        val creditType = intent.getStringExtra("Type")
            super.onCreate(savedInstanceState)
            setContentView(R.layout.credit_fill)

        //val creditType = intent.getStringExtra("Type")
        textViewCredit.text = creditType.toString()

        if(creditType == "Car"){
            imageViewType.setImageResource(R.drawable.car)
        }else if(creditType == "Telco"){
            imageViewType.setImageResource(R.drawable.communication)
        }else if(creditType == "Entertainment"){
            imageViewType.setImageResource(R.drawable.entertainment)
        }else if(creditType == "Amenities"){
            imageViewType.setImageResource(R.drawable.daily)
        }else if(creditType == "Food"){
            imageViewType.setImageResource(R.drawable.food)
        }else if(creditType == "Loan"){
            imageViewType.setImageResource(R.drawable.house)
        }else if(creditType == "Fruit"){
            imageViewType.setImageResource(R.drawable.fruit)
        }else if(creditType == "Gift"){
            imageViewType.setImageResource(R.drawable.gift)
        }else if(creditType == "Shirt"){
            imageViewType.setImageResource(R.drawable.phone)
        }else if(creditType == "Shopping"){
            imageViewType.setImageResource(R.drawable.shopping)
        }else if(creditType == "MarkUp"){
            imageViewType.setImageResource(R.drawable.makeup)
        }else if(creditType == "Medic"){
            imageViewType.setImageResource(R.drawable.medkit)
        }else if(creditType == "Dessert"){
            imageViewType.setImageResource(R.drawable.snack)
        }else if(creditType == "Sport"){
            imageViewType.setImageResource(R.drawable.sport)
        }else if(creditType == "Study"){
            imageViewType.setImageResource(R.drawable.study)
        }else if (creditType == "Transport"){
            imageViewType.setImageResource(R.drawable.transportation)
        }

        buttonSubmit.setOnClickListener(){
            addCredit(creditType,telno)
            val intent = Intent(this, main_menu_support::class.java)
            startActivity(intent)
        }
    }


    private fun addCredit(creditType: String?,telno:String) {
        if(TextUtils.isEmpty(editText.text)){
            editText.setError("Value is required")
            return
        }
        var creditAmount:Float= editText.text.toString().toFloat()
        val currentTime = LocalDateTime.now().month;
        val url : String = getString(R.string.url_server) + getString(R.string.url_Credit_create) + "?creditamount=" + creditAmount +
                "&credittype=" + creditType + "&creditdate=" + currentTime + "&userid=" + telno ; //change later //TODO
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                // Process the JSON
                try{
                    if(response != null){
                        val success: String = response.get("success").toString()

                        if(success.equals("1")){
                            Toast.makeText(applicationContext, "Record saved", Toast.LENGTH_LONG).show()

                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
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
        jsonObjectRequest.tag = TAG
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

    companion object{
        const val TAG = "com.example.debit"
    }
    /*override fun onBackPressed() {


        MySingleton.getInstance(this).cancelRequest(TAG)
        super.onBackPressed()
    }

    private fun isConnected(): Boolean{
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }*/

}