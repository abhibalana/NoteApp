package com.example.internproject

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_layout.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NoteAdapter(val context: Context,val listener:noteRVAdapter) : RecyclerView.Adapter<NoteViewHolder>() {
    val allnotes =  ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false)
        val viewHolder = NoteViewHolder(view)
        viewHolder.deletebutton.setOnClickListener {
            listener.onItemClicked(allnotes[viewHolder.adapterPosition])
        }
        viewHolder.fullView.setOnClickListener {
            listener.onNoteClick(allnotes[viewHolder.adapterPosition])
        }


        return viewHolder
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.text.text = allnotes[position].title
        holder.note.text = allnotes[position].Note
        holder.date.text = allnotes[position].date

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
    val note = itemView.findViewById<TextView>(R.id.layoutNote)
    val date  = itemView.findViewById<TextView>(R.id.date)
    val deletebutton = itemView.findViewById<ImageView>(R.id.deleteButton)
    val fullView = itemView.findViewById(R.id.fullview) as ConstraintLayout
}
interface noteRVAdapter{
    fun onItemClicked(note:Note)
    fun onNoteClick(note:Note)

}
