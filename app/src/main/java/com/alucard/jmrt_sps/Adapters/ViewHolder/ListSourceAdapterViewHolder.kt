package com.alucard.jmrt_sps.Adapters.ViewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.source_news.view.*

class ListSourceAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    var source_title = itemView.source_news_name
    var source_description=itemView.source_description
    var button_vermas=itemView.ap

}