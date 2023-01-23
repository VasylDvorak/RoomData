package ru.gb.roomdata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import ru.gb.roomdata.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val historySource = HistorySource(contentResolver)
        historySource.query()

        binding.insert.setOnClickListener {
            historySource.insert(HistoryEntity(19, "13 друзей оушена", 2014, 1))
        }
        binding.get.setOnClickListener {
            var current_list = historySource.getHistory()

            for (item in current_list) {

                binding.currentRequest.addView(AppCompatTextView(applicationContext).apply {
                    text = item.name + "  " + item.year
                })
            }
        }
        binding.getByPosition.setOnClickListener {
            historySource.getMovieByPosition(1)
        }
        binding.update.setOnClickListener {
            historySource.update(HistoryEntity(1, "Форсаж", 2021, 1))
        }
        binding.delete.setOnClickListener {
            historySource.delete(HistoryEntity(2))
        }
    }
}
