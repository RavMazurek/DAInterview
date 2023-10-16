package com.ravmaz.dainterview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.ravmaz.dainterview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = InterviewLayoutManager(2, 5)
        binding.recyclerView.adapter = ItemAdapter(generateSomeItems(200))
    }

    private fun generateSomeItems(count: Int): List<String> {
        return (1..count).map { "Item$it" }
    }
}