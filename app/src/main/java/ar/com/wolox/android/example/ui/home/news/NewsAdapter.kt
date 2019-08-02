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
                vNewImage.setImageURI((new.picture).replace(context.getString(R.string.reg_pattern_protocol).toRegex(),
                        context.getString(R.string.allow_protocol)))
                vNewLikeIcon.background = when (new.likes.contains(userId.toInt())) {
                    true -> ContextCompat.getDrawable(context, ICON_LIKE_ON)
                    false -> ContextCompat.getDrawable(context, ICON_LIKE_OFF)
                }
            }
        }

        @SuppressLint("SimpleDateFormat")
        private fun formatTime(createdAt: String): String {
            val context = itemView.context
            val format = SimpleDateFormat(context.getString(R.string.ISO_8601_24h_full_format))
            val prettyTime = PrettyTime()
            return prettyTime.format(format.parse(createdAt))
                    .replace(context.getString(R.string.reg_pattern_years).toRegex(), context.getString(R.string.year_replacement))
                    .replace(context.getString(R.string.reg_pattern_months).toRegex(), context.getString(R.string.month_replacement))
                    .replace(context.getString(R.string.reg_pattern_weeks).toRegex(), context.getString(R.string.week_replacement))
                    .replace(context.getString(R.string.reg_pattern_days).toRegex(), context.getString(R.string.day_replacement))
                    .replace(context.getString(R.string.reg_pattern_hours).toRegex(), context.getString(R.string.hour_replacement))
                    .replace(context.getString(R.string.reg_pattern_minuts).toRegex(), context.getString(R.string.minuts_replacement))
                    .replace(context.getString(R.string.reg_pattern_now).toRegex(), context.getString(R.string.now_replacement))
        }
    }

    companion object {
        private const val ICON_LIKE_ON = R.drawable.ic_like_on
        private const val ICON_LIKE_OFF = R.drawable.ic_like_off
    }
}