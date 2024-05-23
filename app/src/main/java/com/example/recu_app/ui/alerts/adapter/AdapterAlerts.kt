package com.example.recu_app.ui.alerts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recu_app.R
import com.example.recu_app.domain.alerts.models.Alert

class AdapterAlerts(
    var listAlerts: MutableList<Alert>,
    val onDelete: (Int) -> Unit,
    val onDetails: (Int) -> Unit
) : RecyclerView.Adapter<ViewHAlert>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHAlert {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutItemAlert = R.layout.item_alert
        return ViewHAlert(
            layoutInflater.inflate(layoutItemAlert, parent, false),
            onDelete, onDetails
        )
    }

    override fun getItemCount(): Int = listAlerts.size

    override fun onBindViewHolder(holder: ViewHAlert, position: Int) {
        holder.renderize(listAlerts[position], position)
    }
}
