package com.example.codeeditor

import android.os.Bundle
import android.os.Environment
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_project_detail.*
import java.io.File
import java.io.FileWriter

class ProjectDetail : AppCompatActivity() {

    private var projectDetailList = arrayOf<File>()
    private var projectDetailManager = LinearLayoutManager(this)
    val mainFolder = "CodeEditor"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_detail)

        val projectName = intent.getStringExtra("projectName")

        listFiles(projectName)

        back_button.setOnClickListener {
            finish()
        }

        val projectsFolder = File(Environment.getExternalStorageDirectory(), "$mainFolder/$projectName")

        create_file_fab.setOnClickListener {
            val fileNameInput = EditText(this)
            val fileNameInputParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            fileNameInput.layoutParams = fileNameInputParams

            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.create_project_dialog_title))
                .setMessage(resources.getString(R.string.supporting_text))
                .setView(fileNameInput)
                .setNegativeButton(resources.getString(R.string.cancel)) { dialog, which ->
                    dialog.cancel()
                }
                .setPositiveButton(resources.getString(R.string.create_project_dialog_btn_create)) { dialog, which ->
                    val fileCreated = File(projectsFolder.path, "${fileNameInput.text}.html")
                    val writer = FileWriter(fileCreated)
                    writer.append("")
                    writer.flush()
                    writer.close()
                    listFiles(projectName)
                }
                .show()
        }
    }

    private fun listFiles(projectName: String) {
        val mainFolderPath = File(Environment.getExternalStorageDirectory(), mainFolder).path

        project_detail_title.text = projectName

        projectDetailList = File(mainFolderPath, projectName!!).listFiles()!!

        project_detail_recycler_view.adapter = ProjectDetailAdapter(projectDetailList)
        project_detail_recycler_view.layoutManager = projectDetailManager
        project_detail_recycler_view.setHasFixedSize(true)
        val dividerItemDecoration = DividerItemDecoration(
            project_detail_recycler_view.context,
            projectDetailManager.orientation,
        )
        dividerItemDecoration.setDrawable(getDrawable(R.drawable.divider)!!)
        project_detail_recycler_view.addItemDecoration(dividerItemDecoration)
    }
}