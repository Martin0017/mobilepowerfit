package com.example.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.icu.util.TimeZone
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataPoint
import com.google.android.gms.fitness.data.DataSet
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.DataType.AGGREGATE_STEP_COUNT_DELTA
import com.google.android.gms.fitness.data.DataType.TYPE_STEP_COUNT_DELTA
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit


class menu_page : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION = 1

    fun accessGoogleFitData() {





        Log.i("Acceso","Concedido")

        val startTime = LocalDateTime.of(2023, 8, 10, 1, 0, 0).atZone(ZoneId.systemDefault())
        val endTime = LocalDateTime.of(2023, 8, 11, 1, 0, 0).atZone(ZoneId.systemDefault())

        val fitnessOptions: GoogleSignInOptionsExtension = FitnessOptions.builder()
            .addDataType(TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .build()


        val readRequest = DataReadRequest.Builder()
                // The data request can specify multiple data types to return,
                // effectively combining multiple data queries into one call.
                // This example demonstrates aggregating only one data type.
                .aggregate(AGGREGATE_STEP_COUNT_DELTA)
                // Analogous to a "Group By" in SQL, defines how data should be
                // aggregated.
                // bucketByTime allows for a time span, whereas bucketBySession allows
                // bucketing by <a href="/fit/android/using-sessions">sessions</a>.
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(startTime.toEpochSecond(), endTime.toEpochSecond(), TimeUnit.SECONDS)
                .build()

        Fitness.getHistoryClient(this, GoogleSignIn.getAccountForExtension(this, fitnessOptions))
            .readData(readRequest)
            .addOnSuccessListener { response ->
                // The aggregate query puts datasets into buckets, so flatten into a
                // single list of datasets
                for (dataSet in response.buckets.flatMap { it.dataSets }) {
                    dumpDataSet(dataSet)
                }
            }
            .addOnFailureListener { e ->
                Log.w("There was an error reading data from Google Fit", e)
            }

    }

    fun dumpDataSet(dataSet: DataSet) {
        Log.i("Data returned for Data type:"," ${dataSet.dataType.name}")
        for (dp in dataSet.dataPoints) {
            Log.i("Data point:"," ")
            Log.i("\tType:"," ${dp.dataType.name}")
            Log.i("\tStart:"," ${dp.getStartTimeString()}")
            Log.i("\tEnd: ","${dp.getEndTimeString()}")
            for (field in dp.dataType.fields) {
                Log.i("\tField: ${field.name.toString()} Value:","${dp.getValue(field)}")
            }
        }
    }

    fun DataPoint.getStartTimeString() = Instant.ofEpochSecond(this.getStartTime(TimeUnit.SECONDS))
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime().toString()

    fun DataPoint.getEndTimeString() = Instant.ofEpochSecond(this.getEndTime(TimeUnit.SECONDS))
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime().toString()

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        accessGoogleFitData()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_page)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)

        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navegation_drawer_open, R.string.avegation_drawer_close)
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        viewPager = findViewById(R.id.view_pager)
        val adapter = MyPagerAdapter(this)
        viewPager.adapter = adapter

        tabLayout = findViewById(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.tab_1)
                    tab.setIcon(R.drawable.ic_sprint_48px)
                }
                1 -> {
                    tab.text = getString(R.string.tab_2)
                    tab.setIcon(R.drawable.ic_rewarded_ads_48px)
                }
            }
        }.attach()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_item_one -> {Toast.makeText(this, "Registro", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, records_page::class.java)
                startActivity(intent)
            }
            R.id.nav_item_two -> Toast.makeText(this, "Metas", Toast.LENGTH_SHORT).show()
            R.id.nav_item_three -> Toast.makeText(this, "Puntos", Toast.LENGTH_SHORT).show()

            R.id.nav_item_six -> Toast.makeText(this, "Configuracion de Cuenta", Toast.LENGTH_SHORT).show()
            R.id.nav_item_seven -> Toast.makeText(this, "Acceso a Soporte", Toast.LENGTH_SHORT).show()
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
