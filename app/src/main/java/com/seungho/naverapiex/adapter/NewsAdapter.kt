package com.seungho.naverapiex.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.seungho.naverapiex.databinding.ItemNewsBinding
import com.seungho.naverapiex.retrofit.Items
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

class NewsAdapter(private val listData: ArrayList<Items>, private val context: Context): RecyclerView.Adapter<NewsAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setNews(listData[position],context)
    }

    override fun getItemCount(): Int {
        return listData.count()
    }

    inner class Holder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun setNews(items: Items, context: Context) {
            binding.titleTextView.text = items.title

            val cd = items.pubDate
            val dateTime = LocalDateTime.parse(cd, DateTimeFormatter.RFC_1123_DATE_TIME)
            val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

            binding.dateTextView.text = dateTime.format(dateFormatter).toString()
            binding.descriptionTextView.text = items.description

            binding.view.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(items.link))
                ContextCompat.startActivity(context, intent, null)
            }
        }
    }
}