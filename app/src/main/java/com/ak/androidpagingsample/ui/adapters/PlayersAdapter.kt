package com.ak.androidpagingsample.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ak.androidpagingsample.R
import com.ak.androidpagingsample.data.model.PlayerItem
import com.ak.androidpagingsample.databinding.AdapterItemBinding

class PlayersAdapter(private val clicked: (PlayerItem) -> Unit) :
    PagingDataAdapter<PlayerItem, PlayersAdapter.PlayersViewHolder>(
        PlayersDiffCallback()
    ) {


    override fun onBindViewHolder(holder: PlayersViewHolder, position: Int) {

        val data = getItem(position)

        holder.bind(data)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayersViewHolder {

        return PlayersViewHolder(
            AdapterItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    inner class PlayersViewHolder(
        private val binding: AdapterItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: PlayerItem?) {

            binding.let {
                data?.let {playerItem->
                    val name = it.root.context.getString(
                        R.string.name,
                        playerItem.firstName, playerItem.lastName
                    )
                    it.root.setOnClickListener {
                        clicked.invoke(playerItem)
                    }
                    it.name.text = name
                    it.position.text = it.root.context.getString(
                        R.string.adapter_item,
                        "Position", playerItem.position
                    )
                    it.team.text = it.root.context.getString(
                        R.string.adapter_item,
                        "Team", playerItem.team?.fullName
                    )
                    it.division.text = it.root.context.getString(
                        R.string.adapter_item,
                        "Division", playerItem.team?.division
                    )
                }
            }
        }
    }

    private class PlayersDiffCallback : DiffUtil.ItemCallback<PlayerItem>() {
        override fun areItemsTheSame(oldItem: PlayerItem, newItem: PlayerItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PlayerItem, newItem: PlayerItem): Boolean {
            return oldItem == newItem
        }
    }

}