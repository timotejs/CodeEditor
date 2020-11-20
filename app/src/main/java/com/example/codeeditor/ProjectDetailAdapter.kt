package com.example.codeeditor

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.project_list_item.view.*
import java.io.File
import java.util.*

class ProjectDetailAdapter(private val projectDetailList: Array<File>) :
    RecyclerView.Adapter<ProjectDetailAdapter.ProjectDetailViewHolder>() {

    class ProjectDetailViewHolder(projectDetailItemView: View) : RecyclerView.ViewHolder(projectDetailItemView) {
        val nameView: TextView = projectDetailItemView.project_name
        val pathView: TextView = projectDetailItemView.project_path
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectDetailViewHolder {
        val projectDetailItemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.project_list_item, parent, false)

        val projectDetailItemViewHolder = ProjectDetailViewHolder(projectDetailItemView)

        projectDetailItemView.setOnClickListener {
            val position = projectDetailItemViewHolder.adapterPosition
            var fileName = "Not found"
            var projectPath = "Not found"

            if (position != RecyclerView.NO_POSITION) {
                fileName = projectDetailList[position].name
                projectPath = projectDetailList[position].parentFile.path
            }

            val intentProjectDetail = Intent(parent.context, FileEditor::class.java)
            intentProjectDetail.putExtra("fileName", fileName)
            intentProjectDetail.putExtra("projectPath", projectPath)

            parent.context.startActivity(intentProjectDetail)
        }

        return projectDetailItemViewHolder
    }

    override fun onBindViewHolder(holder: ProjectDetailViewHolder, position: Int) {
        holder.nameView.text = projectDetailList[position].name
        holder.pathView.text = Date(projectDetailList[position].lastModified()).toString()
    }

    override fun getItemCount() = projectDetailList.size


}