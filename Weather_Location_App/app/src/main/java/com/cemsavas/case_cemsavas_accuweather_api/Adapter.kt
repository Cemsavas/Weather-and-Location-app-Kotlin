package com.cemsavas.case_cemsavas_accuweather_api

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Adapter ile konum_dataItem classından methodlarla istediğim iki data yı hazırladım
class Adapter(val context:Context,val konumlist:List<Konum_dataItem>):RecyclerView.Adapter<Adapter.ViewHolder>() {


    class ViewHolder(itemVİew: View,) : RecyclerView.ViewHolder(itemVİew) {


        var Key: TextView
        var Localizedname: TextView
        var Country: TextView

        init {
            Localizedname = itemVİew.findViewById(R.id.Localizedname)
            Country = itemVİew.findViewById(R.id.Country)
            Key=itemVİew.findViewById(R.id.Key)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemVİew = LayoutInflater.from(context).inflate(R.layout.card_view_design, parent, false)
        return ViewHolder(itemVİew,)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.Localizedname.text = konumlist[position].LocalizedName.toString()
        holder.Country.text = konumlist[position].Country.toString()
        holder.Key.text=konumlist[position].Key.toString()

        holder.itemView.setOnClickListener{

            val model =konumlist.get(position)
            var konum:String=model.LocalizedName
            var key:String=model.Key


            val intent=Intent(context,Havadurumu::class.java)
            intent.putExtra("iLocalizedname",konum)
            intent.putExtra("iKey",key)
            context.startActivity(intent)
        }

        }
    override fun getItemCount(): Int {
        return konumlist.size
    }
}


