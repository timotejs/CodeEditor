package com.example.codeeditor

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_file_editor.*
import java.io.*

val mainFolder = "CodeEditor"

class FileEditor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_editor)

        val fileName = intent.getStringExtra("fileName")
        val projectPath = intent.getStringExtra("projectPath")

        file_name_title.text = fileName

        val file = File(projectPath, fileName)

        val text = StringBuilder()

        try {
            val br = BufferedReader(FileReader(file))
            var line: String?
            while (br.readLine().also { line = it } != null) {
                text.append(line)
                text.append('\n')
            }
            br.close()
        } catch (e: IOException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }

        edit_file_input.setText(text.toString())

        back_button.setOnClickListener {
            finish()
        }

        preview_button.setOnClickListener {
            val intentProjectDetail = Intent(this, WebPreview::class.java)
            intentProjectDetail.putExtra("fileName", fileName)
            intentProjectDetail.putExtra("projectPath", projectPath)

            this.startActivity(intentProjectDetail)
        }

        edit_file_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val writer = FileWriter(file)
                writer.append(s.toString())
                writer.flush()
                writer.close()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }
}