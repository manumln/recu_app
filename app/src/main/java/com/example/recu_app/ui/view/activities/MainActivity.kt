package com.example.recu_app.ui.view.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.recu_app.R
import com.example.recu_app.databinding.ActivityMainBinding
import com.example.recu_app.ui.view.activities.login.LoginActivity
import com.example.recu_app.ui.view.activities.login.SharedPreferencesManager
import com.example.recu_app.ui.view.fragments.alerts.AlertsFragment
import com.example.recu_app.ui.view.fragments.perfil.PerfilFragment
import com.example.recu_app.ui.view.fragments.users.UsuariosFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val CALL_PHONE_REQUEST_CODE = 1
    private var phoneNumberToCall: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.myToolbar.myToolbar)

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    loadFragment(AlertsFragment())
                    true
                }
                R.id.usuarios -> {
                    loadFragment(UsuariosFragment())
                    true
                }
                R.id.perfil -> {
                    loadFragment(PerfilFragment())
                    true
                }
                else -> false
            }
        }
        loadFragment(AlertsFragment())
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    fun makeCall(phoneNumber: String) {
        phoneNumberToCall = phoneNumber
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CALL_PHONE), CALL_PHONE_REQUEST_CODE)
        } else {
            startCall(phoneNumber)
        }
    }

    private fun startCall(phoneNumber: String) {
        val callIntent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        startActivity(callIntent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CALL_PHONE_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    phoneNumberToCall?.let {
                        startCall(it)
                    }
                } else {
                    // Permiso denegado, manejar según sea necesario
                }
            }
            else -> {
                // Otro código de solicitud, manejar si es necesario
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        val sharedPreferencesManager = SharedPreferencesManager(this)
        sharedPreferencesManager.clearUserData()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
