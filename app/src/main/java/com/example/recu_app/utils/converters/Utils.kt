package com.example.recu_app.utils.converters

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.example.recu_app.R
import com.google.android.material.snackbar.Snackbar

object CoroutineUtils {
    fun Fragment.executeInCoroutine(
        lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
        block: suspend () -> Unit
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(lifecycleState) {
                block()
            }
        }
    }
}

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Loading<T> : Resource<T>()
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}

object SnackBarUtils {
    fun Context.showSnackBar(
        rootView: View,
        message: String,
        anchorView: View? = null,
        actionText: String? = null,
        onAction: (() -> Unit)? = null
    ) {
        val snackBar = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT)
        if (anchorView != null)
            snackBar.anchorView = anchorView
        snackBar.setBackgroundTint(ContextCompat.getColor(this, R.color.snack_bar)).show()
        snackBar.setTextColor(ContextCompat.getColor(this, R.color.white)).show()
        if (actionText != null && onAction != null) {
            snackBar.setAction(actionText) {
                onAction()
            }
        }
    }
}
