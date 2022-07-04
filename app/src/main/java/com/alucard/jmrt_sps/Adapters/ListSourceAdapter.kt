package com.alucard.jmrt_sps.Adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alucard.jmrt_sps.Adapters.ViewHolder.ListSourceAdapterViewHolder
import com.alucard.jmrt_sps.R
import com.alucard.jmrt_sps.models.Website

class ListSourceAdapter(private val context : Context, private val website: Website): RecyclerView.Adapter<ListSourceAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSourceAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.source_news,parent,false)
        return ListSourceAdapterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListSourceAdapterViewHolder, position: Int) {
        holder!!.source_title.text=website.sources!![position].name
        holder!!.source_description.text=website.sources!![position].description

        holder.button_vermas.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse( website.sources!![position].url))
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return website.sources!!.size
    }

}