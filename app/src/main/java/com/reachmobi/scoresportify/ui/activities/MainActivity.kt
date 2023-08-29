package com.reachmobi.scoresportify.ui.activities

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.reachmobi.scoresportify.R
import com.reachmobi.scoresportify.databinding.ActivityMainBinding
import com.reachmobi.scoresportify.models.Player
import com.reachmobi.scoresportify.ui.adapters.PlayerAdapter
import com.reachmobi.scoresportify.ui.viewmodels.MainViewModel
import com.reachmobi.scoresportify.utils.ActivityUtils
import com.reachmobi.scoresportify.utils.DialogUtils
import com.reachmobi.scoresportify.utils.ItemClickListener
import com.reachmobi.scoresportify.utils.Response
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private val playerAdapter = PlayerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupAds()
        setupViews()
        setupListeners()
        observeViewModel()
    }

    private fun setupAds() {
        MobileAds.initialize(this)

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    private fun setupViews() {
        binding.rvPlayer.layoutManager = LinearLayoutManager(this)
        binding.rvPlayer.adapter = playerAdapter
    }

    private fun setupListeners() {
        binding.etSearchBox.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                handleSearchAction(v.text.toString())
            }
            true
        }

        binding.refresh.setOnRefreshListener {
            binding.refresh.isRefreshing = false
            viewModel.searchPlayer(binding.etSearchBox.text.toString())
        }

        playerAdapter.setItemClickListener(object : ItemClickListener {
            override fun onItemClicked(pos: Int) {
                val player = playerAdapter.currentList[pos]
                if (player.strDescriptionEN == null) {
                    Toast.makeText(
                        this@MainActivity,
                        "${player.strPlayer} does not have any description.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                DialogUtils.showMessage(
                    this@MainActivity,
                    player.strPlayer,
                    player.strDescriptionEN,
                    null
                )
            }
        })

        binding.adView.adListener = object : AdListener() {
            override fun onAdClicked() {
                viewModel.logAdsEvent("adClicked")
            }

            override fun onAdClosed() {
                viewModel.logAdsEvent("adClosed")
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                viewModel.logAdsEvent("adFailedToLoad")
            }

            override fun onAdImpression() {
                viewModel.logAdsEvent("adImpression")
            }

            override fun onAdLoaded() {
                viewModel.logAdsEvent("adLoaded")
            }

            override fun onAdOpened() {
                viewModel.logAdsEvent("adOpened")
            }
        }
    }

    private fun observeViewModel() {
        viewModel.playerList.observe(this) { response ->
            when (response) {
                is Response.Error -> {
                    Toast.makeText(this, response.error, Toast.LENGTH_SHORT).show()
                    showErrorState()
                }

                is Response.Loading -> showLoadingState()
                is Response.Success -> showSuccessState(response.data)
            }
        }
    }

    private fun handleSearchAction(query: String) {
        ActivityUtils.closeKeyBoard(this)
        viewModel.searchPlayer(query)
        playerAdapter.submitList(emptyList())
    }

    private fun showErrorState() {
        binding.lottieSearchAnimation.setAnimation(R.raw.animation_no_data)
        binding.lottieSearchAnimation.visibility = View.VISIBLE
        binding.lottieSearchAnimation.playAnimation()
        binding.rvPlayer.visibility = View.GONE
    }

    private fun showLoadingState() {
        binding.lottieSearchAnimation.setAnimation(R.raw.animation_search)
        binding.lottieSearchAnimation.visibility = View.VISIBLE
        binding.rvPlayer.visibility = View.GONE
        binding.lottieSearchAnimation.playAnimation()
    }

    private fun showSuccessState(playerList: List<Player>?) {
        playerAdapter.submitList(playerList)
        binding.lottieSearchAnimation.visibility = View.GONE
        binding.rvPlayer.visibility = View.VISIBLE
    }
}