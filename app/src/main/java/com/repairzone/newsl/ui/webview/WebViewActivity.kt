package com.repairzone.newsl.ui.webview

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import com.repairzone.newsl.R
import com.repairzone.newsl.databinding.ActivityWebViewBinding
import com.repairzone.newsl.ui.base.BaseActivity

class WebViewActivity : BaseActivity<ActivityWebViewBinding>(R.layout.activity_web_view) {
    companion object{
        const val TITLE = "key-title"
        const val LINK = "key-link"

        fun newInstance(context: Context, title: String, link: String): Intent {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(TITLE, title)
            intent.putExtra(LINK, link)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras?.let { bundle ->
            bundle.containsKey(TITLE).also {
                if (it){
                    binding.tvTitle.text = bundle.getString(TITLE)
                }
            }
            bundle.containsKey(LINK).also {
                if (it){
                    binding.webview.webChromeClient = WebChromeClient()
                    binding.webview.loadUrl(bundle.getString(LINK)!!)
                }
            }
        }

        binding.containerBack.setOnClickListener {
            onBackPressed()
        }
    }
}