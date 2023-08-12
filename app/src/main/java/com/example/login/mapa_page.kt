package com.example.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.login.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class mapa_page : AppCompatActivity(), OnMapReadyCallback{

    private lateinit var map:GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mapa_page)
        createFragment()


    }
    private fun createFragment(){
        val mapFragment:SupportMapFragment= supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarker()
    }

    private fun createMarker() {
        val coordinate = LatLng(-0.314975, -78.442196)
        val marker = MarkerOptions().position(coordinate).title("ESPE")
        map.addMarker(marker)

        val cameraPosition = CameraPosition.Builder()
            .target(coordinate)
            .zoom(16f) // Ajusta el nivel de zoom deseado
            .build()

        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

}