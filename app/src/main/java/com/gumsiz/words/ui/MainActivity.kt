package com.gumsiz.words.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gumsiz.words.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
