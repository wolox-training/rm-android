package ar.com.wolox.android.example.ui.home.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ar.com.wolox.android.example.model.New
import kotlinx.android.synthetic.main.item_new.view.*
import ar.com.wolox.android.R
import ar.com.wolox.android.example.utils.formatDateToTime

class NewsAdapter : ListAdapter<New, NewsAdapter.NewsViewHolder>(NewsDiffCallback()) {

    private lateinit var userId: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_new, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    fun setUserId(userId: String) {
        this.userId = userId
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindTo(new: New) {
            itemView.apply {
                vNewTitle.text = new.title
                vNewContent.text = new.text
                vNewTime.text = formatDateToTime(itemView.context, new.createdAt)
                vNewImage.setImageURI((new.picture).replace(context.getString(R.string.reg_pattern_protocol).toRegex(),
                        context.getString(R.string.allow_protocol)))
                vNewLikeIcon.background = when (new.likes.contains(userId.toInt())) {
                    true -> ContextCompat.getDrawable(context, ICON_LIKE_ON)
                    false -> ContextCompat.getDrawable(context, ICON_LIKE_OFF)
                }
            }
        }
    }

    companion object {
        private const val ICON_LIKE_ON = R.drawable.ic_like_on
        private const val ICON_LIKE_OFF = R.drawable.ic_like_off
    }
}