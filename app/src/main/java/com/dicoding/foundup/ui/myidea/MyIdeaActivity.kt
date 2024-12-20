package com.dicoding.foundup.ui.myidea

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.foundup.adapter.MyIdeaAdapter
import com.dicoding.foundup.databinding.ActivityMyIdeaBinding
import com.dicoding.foundup.ui.ideDetail.IdeDetailActivity

class MyIdeaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyIdeaBinding
    private val viewModel: MyIdeaViewModel by viewModels()

    private val myIdeaAdapter = MyIdeaAdapter { postId ->
        // Navigate to IdeDetailActivity with the selected postId
        val intent = Intent(this, IdeDetailActivity::class.java).apply {
            putExtra("POST_ID", postId)
        }
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyIdeaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val postId = intent.getIntExtra("POST_ID", -1)
        viewModel.setPostId(postId)

        bindInit()
        bindView()
    }

    private fun bindInit() {
        viewModel.getPost()

        viewModel.myide.observe(this) { response ->
            if (response != null && response.data != null) {
                myIdeaAdapter.setData(response.data)
            }
        }

        viewModel.loading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun bindView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MyIdeaActivity)
            adapter = myIdeaAdapter
        }
    }
}
