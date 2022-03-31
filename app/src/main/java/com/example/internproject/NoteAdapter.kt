package com.example.internproject

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(val context: Context) : RecyclerView.Adapter<NoteViewHolder>() {
    val allnotes =  ArrayList<Note>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val viewHolder = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false)
        return NoteViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.text.text = allnotes[position].Note
    }

    override fun getItemCount(): Int {
        return allnotes.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList:ArrayList<Note>){
        allnotes.clear()
        allnotes.addAll(newList)
        notifyDataSetChanged()
    }
}

class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
val text = itemView.findViewById<TextView>(R.id.layoutText)
}
