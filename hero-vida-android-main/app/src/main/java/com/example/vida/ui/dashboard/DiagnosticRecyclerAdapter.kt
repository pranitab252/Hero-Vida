package com.example.vida.ui.dashboard

import android.content.Context
import android.graphics.Typeface
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vida.R
import com.example.vida.databinding.DiagnosticItemBinding
import com.example.vida.databinding.NavigationSearchResultItemLayoutBinding
import com.example.vida.ui.dashboard.adapter.DiagnosticIssuesElement
import com.example.vida.utils.extension.hide
import com.example.vida.utils.extension.show

class DiagnosticRecyclerAdapter(
    private val context: Context,
    private val diagnosticList: List<DiagnosticIssuesElement>,
    private val isContent: Boolean
) : RecyclerView.Adapter<DiagnosticRecyclerAdapter.DiagnosticViewHolder>() {
    inner class DiagnosticViewHolder(binding: DiagnosticItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var contentBinding: DiagnosticItemBinding = binding

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiagnosticViewHolder {
        return DiagnosticViewHolder(
            DiagnosticItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DiagnosticViewHolder, position: Int) {
        val onDiagnosticList = diagnosticList[position]
        Glide.with(context).load(onDiagnosticList.issueImage)
            .into(holder.contentBinding.ivIssues)
        holder.contentBinding.tvDiagnosticHeading.text = onDiagnosticList.issueHeading
        if (isContent) {
            holder.contentBinding.tvDiagnosticContnet.show()
            holder.contentBinding.vDivider.show()
            holder.contentBinding.tvDiagnosticContnet.text = onDiagnosticList.issueContent

            holder.contentBinding.tvDiagnosticHeading.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                context.resources.getDimension(R.dimen._12sdp)
            )
            val typeFace: Typeface? = ResourcesCompat.getFont(context, R.font.poppins_medium)
            holder.contentBinding.tvDiagnosticHeading.typeface = typeFace
            if (position == (itemCount - 1)) {
                holder.contentBinding.vDivider.visibility = View.INVISIBLE
            }
        } else {
            holder.contentBinding.tvDiagnosticContnet.hide()
            holder.contentBinding.vDivider.hide()

            holder.contentBinding.tvDiagnosticHeading.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                context.resources.getDimension(R.dimen._10sdp)
            )
            val typeFace: Typeface? = ResourcesCompat.getFont(context, R.font.poppins_regular)
            holder.contentBinding.tvDiagnosticHeading.typeface = typeFace
        }
    }

    override fun getItemCount(): Int {
        return diagnosticList.size
    }
}