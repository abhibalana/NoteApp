package com.example.internproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CountryAdapter(val listener:onCounrtryClick): RecyclerView.Adapter<countryViewHolder>() {
    val array : HashMap<String,String> = HashMap()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): countryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.countrydesign,parent,false)

        val viewHolder = countryViewHolder(view)
        viewHolder.text.setOnClickListener {
            listener.onClick(array.get(array.keys.toList().get(viewHolder.adapterPosition))!!)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: countryViewHolder, position: Int) {
        val keys = array.keys
        holder.text.text = keys.toList().get(position)
    }

    override fun getItemCount(): Int {
        return array.size
    }
    fun update(hm:HashMap<String,String>){
        array.clear()
        array.putAll(hm)
        notifyDataSetChanged()
    }
}
class countryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val text = itemView.findViewById<TextView>(R.id.countrytext)
}
interface onCounrtryClick{
    fun onClick(cn:String)
}