package com.example.digikala.feature.main.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.digikala.R
import com.example.digikala.data.repo.Comment
import kotlinx.android.synthetic.main.item_comment.view.*

class CommentListAdapter(val mustShowAll:Boolean): RecyclerView.Adapter<CommentListAdapter.CommentViewHolder>(){

    var comment=ArrayList<Comment>()
    set(value) {
        field=value
        notifyDataSetChanged()
    }




    inner class CommentViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.item_comment,parent,false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
      holder.itemView.TV_commentTitle.text=comment[position].title
        holder.itemView.TV_commentDate.text=comment[position].date
        holder.itemView.TV_author.text=comment[position].author!!.email
        holder.itemView.TV_commentContent.text=comment[position].content

    }

    override fun getItemCount():Int {
        if (comment.size>3 && mustShowAll==false)
            return 3
        else
            return comment.size
    }

}


