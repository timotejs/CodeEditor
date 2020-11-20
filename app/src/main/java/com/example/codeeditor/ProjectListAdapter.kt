package com.example.codeeditor

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.project_list_item.view.*
import java.io.File
import java.util.*
import kotlin.time.hours

class ProjectListAdapter(private val projectList: Array<File>) :
    RecyclerView.Adapter<ProjectListAdapter.ProjectViewHolder>() {

    class ProjectViewHolder(projectItemView: View) : RecyclerView.ViewHolder(projectItemView) {
        val nameView: TextView = projectItemView.project_name
        val pathView: TextView = projectItemView.project_path
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val projectItemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.project_list_item, parent, false)

        val projectItemViewHolder = ProjectViewHolder(projectItemView)

        projectItemView.setOnClickListener {
            val position = projectItemViewHolder.adapterPosition
            var projectName = "Not found"

            if (position != RecyclerView.NO_POSITION) {
                projectName = projectList[position].name
            }

            if (projectList[position].isDirectory) {
                val intentProjectDetail = Intent(parent.context, ProjectDetail::class.java)
                intentProjectDetail.putExtra("projectName", projectName)

                parent.context.startActivity(intentProjectDetail)
            } else {
                Toast.makeText(parent.context, "Can't open", Toast.LENGTH_SHORT).show()
            }
        }

        return projectItemViewHolder
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.nameView.text = projectList[position].name
        holder.pathView.text = Date(projectList[position].lastModified()).toString()
    }

    override fun getItemCount() = projectList.size


}