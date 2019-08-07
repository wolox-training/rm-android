package ar.com.wolox.android.example.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ar.com.wolox.android.example.ui.home.news.NewsFragment

fun RecyclerView.addOnItemClickListener(onClickListener: NewsFragment.OnItemClickListener) {
    this.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener {
        override fun onChildViewDetachedFromWindow(view: View) {
            view.setOnClickListener(null)
        }

        override fun onChildViewAttachedToWindow(view: View) {
            view.setOnClickListener {
                val holder = getChildViewHolder(view)
                onClickListener.onItemClicked(holder.adapterPosition, view)
            }
        }
    })
}