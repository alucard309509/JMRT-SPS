package com.alucard.jmrt_sps.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.alucard.jmrt_sps.Adapters.ListSourceAdapter
import com.alucard.jmrt_sps.Interface.NewsService
import com.alucard.jmrt_sps.R
import com.alucard.jmrt_sps.constants.Constants
import com.alucard.jmrt_sps.models.Website
import com.google.gson.Gson
import dmax.dialog.SpotsDialog
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_first.*
import retrofit2.Call
import retrofit2.Response


class FirstFragment : Fragment(R.layout.fragment_first) {

    lateinit var layoutManagerd:LinearLayoutManager
    lateinit var mService: NewsService
    lateinit var adapter: ListSourceAdapter
    lateinit var dialog:android.app.AlertDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Init Cache db
        Paper.init(view.context)
        mService = Constants.newService
        swipe_to_refresh.setOnRefreshListener {
            loagWebSiteSource(true,view.context)
        }
        recycler_view_source_news.setHasFixedSize(true)
        layoutManagerd= LinearLayoutManager(view.context)
        recycler_view_source_news.layoutManager=  layoutManagerd

        dialog = SpotsDialog(activity)
        loagWebSiteSource(false,view.context)

    }


    private fun loagWebSiteSource(isRefresh: Boolean,context: Context) {
        if(!isRefresh)
        {
            val cache = Paper.book().read<String>("cache")
            if(cache != null && !cache.isBlank() && cache !="null"){

                val website = Gson().fromJson<Website>(cache,Website::class.java)
                adapter = ListSourceAdapter(context,website)
                adapter.notifyDataSetChanged()
                recycler_view_source_news.adapter=adapter
            }

            else{
                dialog.show()
                mService.sources.enqueue(object :retrofit2.Callback<Website>{
                    override fun onResponse(call: Call<Website>?, response: Response<Website>) {
                        adapter= ListSourceAdapter(context,response.body()!!)
                        adapter.notifyDataSetChanged()
                        recycler_view_source_news.adapter=adapter
                        Paper.book().write("cache", Gson().toJson(response!!.body()!!))
                        dialog.dismiss()
                    }
                    override fun onFailure(call: Call<Website>, t: Throwable) {
                      //  Toast.makeText(baseContext,"Intento fallido"+t.message, Toast.LENGTH_LONG).show()
                        dialog.dismiss()
                    }
                })
            }
        }
        else{

            swipe_to_refresh.isRefreshing=true
            mService.sources.enqueue(object :retrofit2.Callback<Website>{
                override fun onResponse(call: Call<Website>, response: Response<Website>) {

                    adapter= ListSourceAdapter(context,response.body()!!)
                    adapter.notifyDataSetChanged()

                    recycler_view_source_news.adapter=adapter

                    Paper.book().write("cache", Gson().toJson(response!!.body()!!))
                    swipe_to_refresh.isRefreshing=false
                }
                override fun onFailure(call: Call<Website>, t: Throwable) {

                }
            })
        }

    }
}