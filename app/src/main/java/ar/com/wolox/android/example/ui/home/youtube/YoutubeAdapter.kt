package ar.com.wolox.android.example.ui.home.youtube

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ar.com.wolox.android.R
import ar.com.wolox.android.example.model.youtube.Item
import kotlinx.android.synthetic.main.item_video.view.vYoutubeDescription
import kotlinx.android.synthetic.main.item_video.view.vYoutubeImage
import kotlinx.android.synthetic.main.item_video.view.vYoutubeTitle

class YoutubeAdapter : ListAdapter<Item, YoutubeAdapter.ItemViewHolder>(YoutubeItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindTo(itemVideo: Item) {
            itemView.apply {
                itemVideo.snippet.apply {
                    vYoutubeTitle.text = title
                    vYoutubeDescription.text = description
                    vYoutubeImage.setImageURI(thumbnails.medium.url)
                }
            }
        }
    }
}