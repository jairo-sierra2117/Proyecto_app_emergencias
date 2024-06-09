package com.cursokotlin.appemergencias

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val inicioFragment = InicioFragment()
    private val ufpsFragment = UfpsFragment()
    private val settingFragment = SettingFragment()

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
            R.id.callFragment -> {
                if (checkCallPhonePermission()) {  // Check permission before making the call
                    showConfirmationDialog()
                } else {
                    requestCallPhonePermission()
                }
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configura el tema para que siempre use el modo de día
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)

        val navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.inicioFragment
    }

    private fun checkCallPhonePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCallPhonePermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CALL_PHONE),
            REQUEST_CALL_PHONE
        )
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("¿PEDIR AYUDA al 123 ?")
            .setPositiveButton(
                "Sí"
            ) { dialog: DialogInterface?, which: Int ->
                makeEmergencyCall()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun makeEmergencyCall() {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:3052391868") // Replace with your emergency number (e.g., "tel:911")
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startActivity(intent)
        } else {
            requestCallPhonePermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CALL_PHONE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeEmergencyCall()
            } else {
                // Handle permission denied case (optional: inform user)
            }
        }
    }

    companion object {
        private const val REQUEST_CALL_PHONE = 1 // Request code for call permission
    }
}

