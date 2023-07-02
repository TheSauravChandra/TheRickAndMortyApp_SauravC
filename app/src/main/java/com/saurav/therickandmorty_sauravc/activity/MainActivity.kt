package com.saurav.therickandmorty_sauravc.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Pair
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.saurav.therickandmorty_sauravc.R
import com.saurav.therickandmorty_sauravc.databinding.ActivityMainBinding
import com.saurav.therickandmorty_sauravc.databinding.CreatureMiniCardBinding
import com.saurav.therickandmorty_sauravc.adapter.CreatureAdapter
import com.saurav.therickandmorty_sauravc.beans.Creature
import com.saurav.therickandmorty_sauravc.utils.Constants
import com.saurav.therickandmorty_sauravc.utils.Utils.Companion.checkInternet
import com.saurav.therickandmorty_sauravc.utils.Utils.Companion.hideKeyboard
import com.saurav.therickandmorty_sauravc.utils.Utils.Companion.toast
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
        setRecyclerView()
        handleCardClicks()
        setObservers()
        fetchCreatures()
    }

    private fun setRecyclerView() {
        binding.rvList.adapter = adapter
        binding.rvList.layoutManager = LinearLayoutManager(this)

        binding.rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) { //scrolled down. - USER INITIATED
                    if (shouldLoadMoreCreatures()) {
                        // pagination, from when 2 card left we start loading more.
                        fetchCreatures()
                    }
                }
            }
        })
    }

    private fun shouldLoadMoreCreatures(): Boolean {
        val lastVisiblePos = (binding.rvList.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        // not loading from net & only 2 card left to come on screen out of loaded ones.
        return (!(viewModel.getLoading().value)!! && adapter.itemCount - lastVisiblePos < 2) //PageSize & debounce/singleCall
    }

    private fun fetchCreatures() {
        if (checkInternet())
            viewModel.getMoreCreatures()
        else
            askTurnOnInternet()
    }

    private fun askTurnOnInternet() {
        // alert to ask for net: retry or exit
        with(AlertDialog.Builder(this))
        {
            setTitle("Please Turn ON Internet")
            setMessage("After that you can retry:")
            setPositiveButton("Retry") { p0, p1 ->
                fetchCreatures()
            }
            setCancelable(false)
            setFinishOnTouchOutside(false)
            setNeutralButton("Leave App") { _, _ ->
                toast("Developed by SauravC.\n with ❤️ in Bharat(India)\nhttp://sauravc.dx.am/")
                finish()
            }
            show()
        }
    }

    private fun handleCardClicks() {
        adapter.attachCallback { data:Creature?, card:CreatureMiniCardBinding ->
            data?.let {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    detailsWithEffect(card, it)
                } else {
                    detailsWithoutEffect(it)
                }

            } ?: kotlin.run {
                toast(R.string.some_error_occurred)
            }
        }
    }
    private fun detailsWithoutEffect(it: Creature) {
        // plain next page
        startActivity(Intent(this, CreatureDetail::class.java).apply {
            putExtra(Constants.CREATURE, Gson().toJson(it))
        })
    }

    private fun detailsWithEffect(card: CreatureMiniCardBinding, it: Creature) {
        // shared animation
        val options = ActivityOptions.makeSceneTransitionAnimation(
            this,
            Pair(card.ivPic, "photo"), Pair(card.mcvRoundedBg, "card")
        )

        // next page
        startActivity(Intent(this, CreatureDetail::class.java).apply {
            putExtra(Constants.CREATURE, Gson().toJson(it))
        }, options.toBundle())
    }

    private fun setObservers() {
        viewModel.getCreatureList().observe(this, {
            // diff util now will handle updations by min. effort.
            adapter.updateList(it)
            // ux: indicate more available by little scroll
            if (viewModel.getCurrentPageNo() > 2)
                binding.rvList.smoothScrollBy(0, 250)
            else // first time keyboard hide.
                hideKeyboard(this, currentFocus)
        })

        viewModel.getErr().observe(this, {
            toast(it)
        })

    }
}