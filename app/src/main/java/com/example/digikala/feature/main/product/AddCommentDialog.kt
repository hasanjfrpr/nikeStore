package com.example.digikala.feature.main.product

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.digikala.R
import com.example.digikala.data.repo.TokenContainer
import com.example.digikala.feature.main.auth.AuthActivity
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.dialog_add_comment.*

class AddCommentDialog : DialogFragment() {
var dialogEvent:DialogEvent?=null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)
       val view = layoutInflater.inflate(R.layout.dialog_add_comment,null,false)
        builder.setView(view)
        val btn=view.findViewById<MaterialButton>(R.id.MBTN_add_comment)
        val title=view.findViewById<EditText>(R.id.ET_add_comment_title)
        val content=view.findViewById<EditText>(R.id.ET_add_comment_content)
        btn.setOnClickListener {

            dialogEvent!!.addComment(title.text.toString(),content.text.toString())
            dismiss()
        }
        return builder.create()
    }
    interface DialogEvent{
        fun addComment(title:String,content:String)
    }
}