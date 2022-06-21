package com.ak.androidpagingsample.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ak.androidpagingsample.databinding.NetworkStateItemBinding

class PlayersLoadingStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<PlayersLoadingStateAdapter.PlayersLoadStateViewHolder>() {

    inner class PlayersLoadStateViewHolder(
        private val binding: NetworkStateItemBinding,
        private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.textViewError.text = loadState.error.localizedMessage
            }
            binding.progressbar.visibility = if (loadState is LoadState.Loading) {
                View.VISIBLE
            } else {
                View.GONE
            }
            binding.buttonRetry.visibility = if (loadState is LoadState.Error) {
                View.VISIBLE
            } else {
                View.GONE
            }
            binding.textViewError.visibility = if (loadState is LoadState.Error) {
                View.VISIBLE
            } else {
                View.GONE
            }
            binding.buttonRetry.setOnClickListener {
                retry()
            }
        }
    }

    override fun onBindViewHolder(holder: PlayersLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = PlayersLoadStateViewHolder(
        NetworkStateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        retry
    )
}