package com.seungho.naverapiex

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.seungho.naverapiex.adapter.NewsAdapter
import com.seungho.naverapiex.databinding.ActivityMainBinding
import com.seungho.naverapiex.retrofit.Items
import com.seungho.naverapiex.retrofit.NetworkManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private var disposable: Disposable? = null

    override fun onDestroy() {
        super.onDestroy()
        disposable?.let { disposable!!.dispose() }
    }

    private val CLIENT_ID = "iP0JO16YljV1HSL4JT9e"
    private val CLIENT_SECRET = "O_IGeZL32C"
    private var word: String = ""

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        word = intent.getStringExtra("search").toString()
        getRepository()

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getRepository() {
        NetworkManager.NaverApi
            .getSearchNews(CLIENT_ID, CLIENT_SECRET, word)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                binding.textView.text = "${word}에 대한 검색결과 ${it.total}건 중"
                Log.d(">>", "$it")
                it.let { updateUI(it.items as ArrayList<Items>) }
            }, { binding.textView.text = "에러가 발생하여 불러올 수 없습니다" })
    }

    private fun updateUI(listData: ArrayList<Items>) {
        val adapter = NewsAdapter(listData, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
}