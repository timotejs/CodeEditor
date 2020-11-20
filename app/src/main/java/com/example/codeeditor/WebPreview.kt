package com.example.codeeditor

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_file_editor.back_button
import kotlinx.android.synthetic.main.activity_web_preview.*
import java.io.File


class WebPreview : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_preview)

        val fileName = intent.getStringExtra("fileName")
        val projectPath = intent.getStringExtra("projectPath")

        web_preview_title.text = fileName

        preview_web_view.webViewClient = WebViewClient()

        preview_web_view.loadUrl("file:///$projectPath/$fileName")

        back_button.setOnClickListener {
            finish()
        }

    }
}