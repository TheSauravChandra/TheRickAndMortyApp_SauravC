package com.saurav.therickandmorty_sauravc.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.saurav.therickandmorty_sauravc.databinding.ActivityMainBinding
import com.saurav.therickandmorty_sauravc.adapter.CreatureAdapter
import com.saurav.therickandmorty_sauravc.viewmodel.CreatureViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: CreatureViewModel by viewModel()
    private val adapter: CreatureAdapter by inject<CreatureAdapter>()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        viewModel.getCreatureList().observe(this,{
            Toast.makeText(this,"$it",Toast.LENGTH_LONG).show()
        })

        viewModel.getMoreCreatures()

    }
}