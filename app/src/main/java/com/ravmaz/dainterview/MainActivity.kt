package com.ravmaz.dainterview

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import com.ravmaz.dainterview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val itemsCount = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val layoutManager = InterviewLayoutManager(2, 5, false)
        binding.recyclerView.layoutManager = layoutManager
        //val snapHelper = InterviewSnapHelper()
        //snapHelper.attachToRecyclerView(binding.recyclerView)
        binding.recyclerView.adapter = ItemAdapter(generateSomeItems(itemsCount))
    }

    private fun generateSomeItems(count: Int): List<String> {
        return (1..count).map { "Item$it" }
    }

    fun buttonClick(view: View): Unit {
        val position = binding.numberInput.text.toString()
        if (position.isNotEmpty()) {
            val positionIndex = (position.toInt() - 1).coerceIn(0, itemsCount - 1)

            binding.recyclerView.layoutManager?.smoothScrollToPosition(binding.recyclerView, null,
                positionIndex)
        }
    }
}