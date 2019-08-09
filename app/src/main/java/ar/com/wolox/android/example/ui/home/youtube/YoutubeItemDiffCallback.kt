package ar.com.wolox.android.example.ui.home.youtube

import androidx.recyclerview.widget.DiffUtil
import ar.com.wolox.android.example.model.youtube.Item

class YoutubeItemDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id.videoId == newItem.id.videoId
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}