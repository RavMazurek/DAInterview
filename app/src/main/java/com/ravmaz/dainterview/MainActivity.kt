package com.ravmaz.dainterview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ravmaz.dainterview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = InterviewLayoutManager(5, 4, false)
        binding.recyclerView.adapter = ItemAdapter(generateSomeItems(200))
    }

    private fun generateSomeItems(count: Int): List<String> {
        return (1..count).map { "Item$it" }
    }
}