package com.saurav.therickandmorty_sauravc.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saurav.therickandmorty_sauravc.databinding.ActivityCreatureDetailBinding

class CreatureDetail : AppCompatActivity() {
    private lateinit var binding: ActivityCreatureDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatureDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }
}