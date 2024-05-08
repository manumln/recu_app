package com.example.recu_app.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.recu_app.R
import com.example.recu_app.ui.acercade.AcercaDeFragment
import com.example.recu_app.ui.alertas.AlertasFragment
import com.example.recu_app.ui.perfil.PerfilFragment
import com.example.recu_app.ui.usuariosregistrados.UsuariosRegistradosFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_alertas -> {
                    replaceFragment(AlertasFragment())
                    true
                }
                R.id.action_usuarios -> {
                    replaceFragment(UsuariosRegistradosFragment())
                    true
                }
                R.id.action_perfil -> {
                    replaceFragment(PerfilFragment())
                    true
                }
                else -> false
            }
        }

        replaceFragment(AlertasFragment())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_about -> {
                replaceFragment(AcercaDeFragment())
                return true
            }
            R.id.action_logout -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}