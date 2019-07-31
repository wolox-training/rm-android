package ar.com.wolox.android.example.ui.home.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ar.com.wolox.android.R
import ar.com.wolox.android.example.model.New
import kotlinx.android.synthetic.main.item_new.view.*

class NewsAdapter : ListAdapter<New, NewsAdapter.NewsViewHolder>(DiffCallback()) {

    private var newsItem = listOf<New>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_new, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bindTo(newsItem[position])
    }

    fun addAllNews(news: List<New>) {
        newsItem = news
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return newsItem.size
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindTo(new: New) {
            itemView.let {
                it.vNewTitle.text = new.title
                it.vNewContent.text = new.content
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<New>() {
    override fun areItemsTheSame(oldItem: New, newItem: New): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: New, newItem: New): Boolean {
        return oldItem == newItem
    }
}