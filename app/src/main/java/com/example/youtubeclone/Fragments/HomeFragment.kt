package com.example.youtubeclone.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeclone.Adapters.Lists.HomeRecyclerVIewAdapter
import com.example.youtubeclone.R
import com.example.youtubeclone.User.VideoProperties


class HomeFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var context: Context? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.home_item_list)
        context = requireContext() // Use requireContext() to get the context safely

        val videos: MutableList<VideoProperties> = mutableListOf()

// Create and add 5 similar VideoProperties objects to the list
        for (i in 1..100) {
            val video = VideoProperties()
            val thumbnailUriString = "android.resource://${requireContext().packageName}/${R.drawable.thubnail_demo}"
            video.setThumbnail(thumbnailUriString)
            videos.add(video)
        }

// Create the adapter and set it to the RecyclerView
        val adapter = HomeRecyclerVIewAdapter(videos, requireContext())
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(context)

        return view
    }
}
