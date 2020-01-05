package com.practice.targetassignment.ui.repoDetail


import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.practice.targetassignment.R
import com.practice.targetassignment.model.Repo
import kotlinx.android.synthetic.main.activity_repo_details.*


class RepoDetailActivity : AppCompatActivity() {

    companion object {
        const val KEY_REPO = "repo"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_details)
        init()
    }

    private fun init() {
        fun setFormattedText(textView: TextView, label: String, value: String?) {
            val word = SpannableString(label + ": ")
            word.setSpan(ForegroundColorSpan(Color.DKGRAY), 0, word.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            textView.setText(word)
            val wordTwo = SpannableString(value)
            wordTwo.setSpan(ForegroundColorSpan(Color.GRAY), 0, wordTwo.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            textView.append(wordTwo)
        }
        intent.getParcelableExtra<Repo>(KEY_REPO)?.let {
            setFormattedText(tv_user_name, "Author", it.name)
            setFormattedText(tv_handle_name, "Handle Name", it.userName)
            setFormattedText(tv_user_url, "Profile Link", it.url)
            setFormattedText(tv_repo_name, "Name", it.repoDescription?.name)
            setFormattedText(tv_repo_description, "Description", it.repoDescription?.description)
            setFormattedText(tv_repo_url, "Url", it.repoDescription?.url)
            Glide.with(this).load(it.avatar).into(iv_image)
        }

    }
}