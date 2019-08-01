package ar.com.wolox.android.example.ui.home.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ar.com.wolox.android.example.model.New
import kotlinx.android.synthetic.main.item_new.view.*
import org.ocpsoft.prettytime.PrettyTime
import android.annotation.SuppressLint
import ar.com.wolox.android.R
import java.text.SimpleDateFormat

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
                vNewTime.text = formatTime(new.createdAt)
                vNewImage.setImageURI((new.picture).replace(REG_PATTERN, ALLOW_PROTOCOL))
                vNewLikeIcon.background = when (new.likes.contains(userId.toInt())) {
                        true -> ContextCompat.getDrawable(itemView.context, ICON_LIKE_ON)
                        false -> ContextCompat.getDrawable(itemView.context, ICON_LIKE_OFF)
                }
            }
        }

        @SuppressLint("SimpleDateFormat")
        private fun formatTime(createdAt: String): String {
            val format = SimpleDateFormat(ISO_8601_24H_FULL_FORMAT)
            val prettyTime = PrettyTime()
            return prettyTime.format(format.parse(createdAt))
                    .replace(REG_PATTERN_YEARS, YEAR_REPLACEMENT)
                    .replace(REG_PATTERN_MONTHS, MONTH_REPLACEMENT)
                    .replace(REG_PATTERN_WEEKS, WEEK_REPLACEMENT)
                    .replace(REG_PATTERN_DAYS, DAY_REPLACEMENT)
                    .replace(REG_PATTERN_HOURS, HOUR_REPLACEMENT)
                    .replace(REG_PATTERN_MINUTS, MINUTE_REPLACEMENT)
                    .replace(REG_PATTERN_NOW, NOW_REPLACEMENT)
        }
    }

    companion object {
        private val REG_PATTERN = "^http:?:".toRegex()
        private val REG_PATTERN_YEARS = "\\w*(years ago|year ago)".toRegex()
        private val REG_PATTERN_MONTHS = "\\w*(month ago|months ago)".toRegex()
        private val REG_PATTERN_WEEKS = "\\w*(week ago|weeks ago)".toRegex()
        private val REG_PATTERN_DAYS = "\\w*(days ago|day ago)".toRegex()
        private val REG_PATTERN_HOURS = "\\w*(hours ago|hour ago)".toRegex()
        private val REG_PATTERN_MINUTS = "\\w*(minutes ago|minute ago|minute from now)".toRegex()
        private val REG_PATTERN_NOW = "\\w*(moments ago|moments from now)".toRegex()
        private const val ALLOW_PROTOCOL = "https:"
        private const val YEAR_REPLACEMENT = "Y"
        private const val MONTH_REPLACEMENT = "M"
        private const val WEEK_REPLACEMENT = "W"
        private const val DAY_REPLACEMENT = "D"
        private const val HOUR_REPLACEMENT = "h"
        private const val MINUTE_REPLACEMENT = "m"
        private const val NOW_REPLACEMENT = "Now"
        private const val ICON_LIKE_ON = ar.com.wolox.android.R.drawable.ic_like_on
        private const val ICON_LIKE_OFF = ar.com.wolox.android.R.drawable.ic_like_off
        private const val ISO_8601_24H_FULL_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
    }
}