package com.example.recu_app.ui.view.fragments.alerts.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.example.recu_app.data.alerts.database.entities.AlertEntity
import com.example.recu_app.databinding.ItemAlertBinding
import com.example.recu_app.ui.view.fragments.alerts.AllAlertsFragmentDirections
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AlertsAdapter(
    private var alerts: List<AlertEntity>,
    private val onItemClickListener: (ItemAlertBinding, AlertEntity) -> Unit = { _: ItemAlertBinding, _: AlertEntity -> }
) : RecyclerView.Adapter<AlertsAdapter.AlertsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertsViewHolder {
        val binding = ItemAlertBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlertsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlertsViewHolder, position: Int) {
        holder.bind(createOnClickListener(holder.binding, alerts[position]), alerts[position])
    }

    override fun getItemCount() = alerts.size

    fun updateAlerts(newAlerts: List<AlertEntity>) {
        alerts = newAlerts
        notifyDataSetChanged()
    }

    inner class AlertsViewHolder(val binding: ItemAlertBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, item: AlertEntity) {
            binding.tvTitle.text = item.title
            binding.tvDescription.text = item.description

            ViewCompat.setTransitionName(binding.tvTitle, "title_${item.id}")
            ViewCompat.setTransitionName(binding.tvDescription, "description_${item.id}")
            ViewCompat.setTransitionName(binding.tvDeadline, "deadline_${item.id}")

            if (item.deadLine != "0")
                binding.tvDeadline.text = item.deadLine

            val currentDate = Date()
            val dateFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy hh:mm a", Locale.getDefault())
            val deadLineDate: Date? = try {
                dateFormat.parse(item.deadLine)
            } catch (e: Exception) {
                Log.e("AlertsAdapter", "Error parsing date: ${e.message}")
                null
            }

            Log.d("AlertsAdapter", "Current Date: $currentDate")
            Log.d("AlertsAdapter", "Deadline Date: $deadLineDate")

            if (deadLineDate != null && deadLineDate.before(currentDate)) {
                binding.root.setCardBackgroundColor(Color.RED) // Cambia el color
                Log.d("AlertsAdapter", "Alert is expired, changing color to RED")
            } else {
                binding.root.setCardBackgroundColor(Color.WHITE)
                Log.d("AlertsAdapter", "Alert is not expired, keeping default color")
            }

            binding.root.setOnClickListener(listener)
            onItemClickListener(binding, item)
        }
    }

    private fun createOnClickListener(binding: ItemAlertBinding, alert: AlertEntity): View.OnClickListener {
        return View.OnClickListener {
            val directions = AllAlertsFragmentDirections.actionAllAlertsFragmentToAddAlertFragment(alert)

            val extras = FragmentNavigatorExtras(
                binding.tvTitle to "title_${alert.id}",
                binding.tvDescription to "description_${alert.id}",
                binding.tvDeadline to "deadline_${alert.id}"
            )
            it.findNavController().navigate(directions, extras)
        }
    }
}
