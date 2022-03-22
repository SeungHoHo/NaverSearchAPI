package com.seungho.naverapiex

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.seungho.naverapiex.adapter.HistoryAdapter
import com.seungho.naverapiex.databinding.ActivitySearchBinding
import com.seungho.naverapiex.room.History

class SearchActivity: AppCompatActivity() {

    private val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }
    private lateinit var db: AppDatabase
    private lateinit var historyAdapter: HistoryAdapter

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "historyDB"
        ).build()

        binding.btnIntent.setOnClickListener {
            searchIntent()
        }

        historyAdapter = HistoryAdapter(historyDeleteClickListener = {
            deleteSearchKeyword(it)
        })

        binding.editText.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                search(binding.editText.text.toString())
                searchEnterIntent()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        binding.editText.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                showHistoryView()
            }

            return@setOnTouchListener false
        }


        binding.historyRecyclerView.adapter = historyAdapter
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(this)
        clickHistoryText()
    }

    private fun searchIntent() {
        val word = binding.editText.text.toString()
        saveSearchKeyword(binding.editText.text.toString())
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("search", word)
        startActivity(intent)
    }

    private fun searchEnterIntent() {
        val word = binding.editText.text.toString()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("search", word)
        startActivity(intent)
    }

    private fun search(text: String) {
        hideHistoryView()
        saveSearchKeyword(text)
    }

    private fun showHistoryView() {
        Thread(Runnable {
            db.historyDao().getAll().reversed().run {
                runOnUiThread {
                    binding.historyRecyclerView.isVisible = true
                    historyAdapter.submitList(this)
                }
            }
        }).start()
    }

    private fun hideHistoryView() {
        binding.historyRecyclerView.isVisible = false
    }

    private fun saveSearchKeyword(keyword: String) {
        Thread(Runnable {
            db.historyDao().insertHistory(History(null, keyword))
        }).start()
    }

    private fun deleteSearchKeyword(keyword: String) {
        Thread(Runnable {
            db.historyDao().delete(keyword)
            showHistoryView()
        }).start()
    }

    private fun clickHistoryText() {
        historyAdapter.setTextViewClickListener(object : HistoryAdapter.OnTextViewClickListener{
            override fun onClick(t: TextView, position: Int) {
                val insertText = t.text.toString()
                binding.editText.setText(insertText)
            }
        })
    }
}