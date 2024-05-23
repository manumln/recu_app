package com.example.recu_app.ui.alerts.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.recu_app.databinding.ItemAlertBinding
import com.example.recu_app.domain.alerts.models.Alert

class ViewHAlert(
    view: View,
    val onDelete: (Int) -> Unit,
    val onDetails: (Int) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val binding = ItemAlertBinding.bind(view)

    fun renderize(alert: Alert, position: Int) {
        binding.txtNameAlert.text = alert.name
        binding.txtDescription.text = alert.textShort

        binding.btnDeleteAlert.setOnClickListener {
            onDelete(position)
        }

        binding.btnDescriptionAlert.setOnClickListener {
            onDetails(position)
        }
    }
}
