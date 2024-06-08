package com.cursokotlin.appemergencias

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    // Declara las instancias de los fragments
    private val inicioFragment = InicioFragment()
    private val ufpsFragment = UfpsFragment()
    private val settingFragment = SettingFragment()

    // Define el listener para la navegación del BottomNavigationView
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.inicioFragment -> {
                supportFragmentManager.beginTransaction().replace(R.id.frame_container, inicioFragment).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.ufpsFragment -> {
                supportFragmentManager.beginTransaction().replace(R.id.frame_container, ufpsFragment).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.settingFragment -> {
                supportFragmentManager.beginTransaction().replace(R.id.frame_container, settingFragment).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configura el tema para que siempre use el modo de día
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setContentView(R.layout.activity_main)

        val navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        // Seleccionar el ítem correspondiente al fragmento de inicio al iniciar la actividad
        navigation.selectedItemId = R.id.inicioFragment
    }
}
