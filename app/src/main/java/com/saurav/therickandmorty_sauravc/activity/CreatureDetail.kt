package com.saurav.therickandmorty_sauravc.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.saurav.therickandmorty_sauravc.beans.Creature
import com.saurav.therickandmorty_sauravc.databinding.ActivityCreatureDetailBinding
import com.saurav.therickandmorty_sauravc.utils.Constants

class CreatureDetail : AppCompatActivity() {
    private var creature: Creature? = null
    private lateinit var binding: ActivityCreatureDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatureDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        getExtras()
        initiate()
        setData()
    }

    fun getExtras() {
        intent.extras?.getString(Constants.CREATURE)?.let {
            creature = Gson().fromJson(it, Creature::class.java)
            if (creature == null)
                finish()
        } ?: run {
            finish()
        }
    }

    fun initiate() {
        supportActionBar?.hide()
        findViewById<View>(android.R.id.content).setOnClickListener {
            super.onBackPressed()
        }
    }

    fun setData() {
        with(creature!!) {
            binding.name = name ?: ""

            image?.let {
                Glide.with(this@CreatureDetail)
                    .load(it)
                    .into(binding.ivPic)
            }

            binding.status = status
            binding.species = species
            binding.gender = gender
            binding.origin = origin?.name
            binding.location = location?.name


        }
    }
}