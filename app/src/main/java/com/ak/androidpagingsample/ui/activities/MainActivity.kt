package com.ak.androidpagingsample.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.ak.androidpagingsample.data.model.PlayerItem
import com.ak.androidpagingsample.databinding.ActivityMainBinding
import com.ak.androidpagingsample.ui.adapters.PlayersAdapter
import com.ak.androidpagingsample.ui.adapters.PlayersLoadingStateAdapter
import com.ak.androidpagingsample.ui.viewmodels.PlayersViewModel
import com.ak.androidpagingsample.utils.RecyclerViewItemDecoration
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private val playersViewModel: PlayersViewModel by viewModels()
    private val playersAdapter =
        PlayersAdapter { playerItem: PlayerItem -> clickedPlayer(playerItem) }
    private var playerJob: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUpAdapter()
        handleEvents()
        getPlayersFromApi()
    }

    private fun getPlayersFromApi() {
        playerJob?.cancel()
        playerJob = lifecycleScope.launch {
            playersViewModel.searchPlayers()
                .collectLatest {
                    playersAdapter.submitData(it)
                }
        }
    }

    private fun handleEvents() {
        binding?.swipeRefreshLayout?.setOnRefreshListener {
            playersAdapter.refresh()
        }
    }

    private fun setUpAdapter() {

        binding?.rvPlayers?.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            addItemDecoration(RecyclerViewItemDecoration())
        }
        binding?.rvPlayers?.adapter = playersAdapter.withLoadStateFooter(
            footer = PlayersLoadingStateAdapter { retry() }
        )

        playersAdapter.addLoadStateListener { loadState ->

            if (loadState.refresh is LoadState.Loading) {

                if (playersAdapter.snapshot().isEmpty()) {
                    binding?.progress?.isVisible = true
                }
                binding?.errorTxt?.isVisible = false

            } else {
                binding?.progress?.isVisible = false
                binding?.swipeRefreshLayout?.isRefreshing = false

                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error

                    else -> null
                }
                error?.let {
                    if (playersAdapter.snapshot().isEmpty()) {
                        binding?.errorTxt?.isVisible = true
                        binding?.errorTxt?.text = it.error.localizedMessage
                    }

                }

            }
        }

    }

    private fun retry() {
        playersAdapter.retry()
    }

    private fun clickedPlayer(playerItem: PlayerItem) {
        val parentLayout = findViewById<View>(android.R.id.content)
        Snackbar.make(parentLayout, playerItem.firstName.toString(), Snackbar.LENGTH_LONG)
            .show()
    }
}