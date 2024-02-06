package com.example.youtubeclone.Adapters.Lists

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeclone.R
import com.example.youtubeclone.User.VideoProperties
import kotlinx.coroutines.NonDisposableHandle.parent

class HomeRecyclerVIewAdapter : RecyclerView.Adapter<HomeRecyclerVIewAdapter.ViewHolder> {

    lateinit var list : List<VideoProperties>
    lateinit var context: Context

    constructor(list: List<VideoProperties>, context: Context) : super() {
        this.list = list
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclerVIewAdapter.ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_scrren_list_item, parent, false)
        return  ViewHolder(view)
    }
    override fun onBindViewHolder(holder: HomeRecyclerVIewAdapter.ViewHolder, position: Int) {

        val video = list[position]
        holder.thumbanil.setImageURI(Uri.parse(video.getThumbnail()))
    }

    override fun getItemCount(): Int {


        return  list.size


    }
//    intilaize all properties
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val thumbanil: ImageView = itemView.findViewById(R.id.thubnail_home)
        val channel_logo : ImageButton = itemView.findViewById(R.id.channel_logo)
        val title : TextView = itemView.findViewById(R.id.title)
        val upload_time : TextView = itemView.findViewById(R.id.upload_time)
        val views_count : TextView = itemView.findViewById(R.id.views_count)
        val channel_name : TextView = itemView.findViewById(R.id.channel_name)
    }

}