package com.example.covidtracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class StateRVAdapter(private val stateList:List<StateModel>):
    RecyclerView.Adapter<StateRVAdapter.StateRVViewHolder>() {

    class StateRVViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val stateNameTV:TextView=itemView.findViewById(R.id.idTVState)
        val caseTV:TextView=itemView.findViewById(R.id.idTVcases)
        val deathTV:TextView=itemView.findViewById(R.id.idTVDeath)
        val RecoveredTV:TextView=itemView.findViewById(R.id.idTVRecovered)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateRVViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.state_rv_item,parent,false)
        return StateRVViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return stateList.size
    }

    override fun onBindViewHolder(holder: StateRVViewHolder, position: Int) {
        val stateData=stateList[position]
        holder.stateNameTV.text=stateData.state
        holder.caseTV.text=stateData.cases.toString()
        holder.deathTV.text=stateData.death.toString()
        holder.RecoveredTV.text=stateData.recoverd.toString()

    }


}