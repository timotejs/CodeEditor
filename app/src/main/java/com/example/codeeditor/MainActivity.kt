package com.example.codeeditor

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    private var projectsList = arrayOf<File>()
    private var projectManager = LinearLayoutManager(this)
    val mainFolder = "CodeEditor"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermission()
    }

    fun listProjects(projectsFolder: File) {
        if (projectsFolder.exists()) {
            projectsList = projectsFolder.listFiles()!!
        } else {
            projectsFolder.mkdir()
        }

        recycler_view_projects.adapter = ProjectListAdapter(projectsList)
        recycler_view_projects.layoutManager = projectManager
        recycler_view_projects.setHasFixedSize(true)
        val dividerItemDecoration = DividerItemDecoration(
            recycler_view_projects.context,
            projectManager.orientation,
        )
        dividerItemDecoration.setDrawable(getDrawable(R.drawable.divider)!!)
        recycler_view_projects.addItemDecoration(dividerItemDecoration)
    }

    private fun ostatak() {
        val projectsFolder = File(Environment.getExternalStorageDirectory(), mainFolder)

        listProjects(projectsFolder)

        create_project_fab.setOnClickListener {
            val projectNameInput = EditText(this)
            val projectNameInputParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            projectNameInput.layoutParams = projectNameInputParams

            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.create_project_dialog_title))
                .setMessage(resources.getString(R.string.supporting_text))
                .setView(projectNameInput)
                .setNegativeButton(resources.getString(R.string.cancel)) { dialog, which ->
                    dialog.cancel()
                }
                .setPositiveButton(resources.getString(R.string.create_project_dialog_btn_create)) { dialog, which ->
                    val createdProject = File(
                        projectsFolder.path,
                        projectNameInput.text.toString()
                    )
                    if (createdProject.mkdir())
                        listProjects(projectsFolder)
                    else
                        Toast.makeText(this, "Failed to create project", Toast.LENGTH_SHORT).show()
                }
                .show()
        }
    }

    private fun checkPermission() {
        when {
            ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                ostatak()
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected. In this UI,
            // include a "cancel" or "no thanks" button that allows the user to
            // continue using your app without granting the permission.
            MaterialAlertDialogBuilder(this)
                .setTitle("Storage permission")
                .setMessage("This app requires storage permission to work.")
                .setNegativeButton(resources.getString(R.string.cancel)) { dialog, which ->
                    dialog.cancel()
                }
                .setPositiveButton("Ok") { dialog, which ->
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        202
                    )
                }
                .show()
        }
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    202
                )
            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        if (requestCode == 202 && grantResults.isNotEmpty() && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            ostatak()
        } else {
            Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }
}