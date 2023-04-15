package com.example.study_firebase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.study_firebase.R
import com.example.study_firebase.model.PersonModel

class ItemDataAdapter(private val persosList: List<PersonModel>):RecyclerView.Adapter<ItemDataAdapter.ViewHolder>() {

    // item listener
    private lateinit var listener: onItemClickListener

    interface  onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(clickListener: onItemClickListener){
        listener = clickListener
    }

    /*------------------------------------------------------------------------------------------------------*/
    class ViewHolder(view: View, clickListener: onItemClickListener):RecyclerView.ViewHolder(view){
        val data : TextView = view.findViewById(R.id.txtDataItem)
        init {
            view.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_show_data,parent,false)
        return ViewHolder(itemView,listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.data.setText("Name: "+persosList[position].name)
    }

    override fun getItemCount(): Int {
       return persosList.size
    }
}