package com.example.covidtracker


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import com.android.volley.Request

class MainActivity : AppCompatActivity() {
    lateinit var countryCasesTV:TextView
    lateinit var countryDeathTV:TextView
    lateinit var countryRecoveredTV:TextView
    lateinit var stateRV:RecyclerView
    lateinit var stateRVAdapter: StateRVAdapter
    lateinit var stateList: List<StateModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countryCasesTV=findViewById(R.id.idTVIndiacases)
        countryDeathTV=findViewById(R.id.idTVIndiaDeath)
        countryRecoveredTV=findViewById(R.id.idTVIndiaRecovered)

        stateRV=findViewById(R.id.idRVStates)
        stateList=ArrayList<StateModel>()

        getStateInfo()
    }

    private fun getStateInfo() {
        //val url="https://api.rootnet.in/covid19-in/stats/latest"
        val url="https://data.covid19india.org/state_district_wise.json"
        val queue=Volley.newRequestQueue(this@MainActivity)
        val request=
            JsonObjectRequest(Request.Method.GET,url,null,{
                 response->
                try {
                    val dataObj=response.getJSONObject("data")
                    val summaryObj=dataObj.getJSONObject("summary")
                    val cases:Int=summaryObj.getInt("total")
                    val recovered:Int=summaryObj.getInt( "discharged")
                    val deaths:Int=summaryObj.getInt( "deaths")

                    countryCasesTV.text=cases.toString()
                    countryRecoveredTV.text=recovered.toString()
                    countryDeathTV.text=deaths.toString()

                    val regionalArray=dataObj.getJSONArray("regional")
                    for (i in 0 until regionalArray.length()){
                        val regionObj=regionalArray.getJSONObject(i)
                        val stateName:String=regionObj.getString( "loc")
                        val cases:Int=regionObj.getInt("totalConfirmed")
                        val death:Int=regionObj.getInt("deaths")
                        val recovered:Int=regionObj.getInt("discharged")

                        val stateModel=StateModel(stateName,recovered,death,cases)
                        stateList=stateList+stateModel
                    }

                    stateRVAdapter=StateRVAdapter(stateList)
                    stateRV.layoutManager=LinearLayoutManager(this)
                    stateRV.adapter=stateRVAdapter

                }catch (e:JSONException){
                    e.printStackTrace()
                }
            },{ error ->
                {
                    Toast.makeText(this,"fail to get the data",Toast.LENGTH_SHORT).show()
                }
            })
        queue.add(request)
    }
}