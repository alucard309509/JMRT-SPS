package com.alucard.jmrt_sps.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.alucard.jmrt_sps.R
import com.alucard.jmrt_sps.data.Prefs
import com.alucard.jmrt_sps.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    companion object {

        lateinit var prefs: Prefs
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.mensajeerror.visibility = View.INVISIBLE
        setContentView(binding.root)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        //Activar permisos de geocalizacion
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)

        binding.ap.setOnClickListener {
            checkPermissions()

        }
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) { ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 1
            )
        } else {
            getLocations()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocations() {
        fusedLocationProviderClient.lastLocation?.addOnSuccessListener {
            if (it == null) {
                Toast.makeText(this, "Nose Puede obtener la localizacion", Toast.LENGTH_LONG).show()
            } else it.apply {

                prefs = Prefs(applicationContext)
                prefs.saveLocation(it.latitude.toString() + it.longitude.toString())
                binding.heading.text = it.latitude.toString() + it.longitude.toString()


                binding.heading.text=getString(R.string.nombreusuario2)
                binding.inputresutado.text=getString(R.string.correoelectronico2)


                startActivity(Intent(this@MainActivity, NewsActivity::class.java))

            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permisos otorgados", Toast.LENGTH_LONG).show()
                    getLocations()
                }
            }
            else {
                binding.ap.visibility = View.INVISIBLE
                binding.mensajeerror.visibility = View.VISIBLE
                Toast.makeText(this, "Permisos denegados", Toast.LENGTH_LONG).show()
            }
        }
    }
}