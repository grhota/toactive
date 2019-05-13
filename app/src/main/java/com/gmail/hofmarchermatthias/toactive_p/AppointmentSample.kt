package com.gmail.hofmarchermatthias.toactive_p

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_appointment_sample.*
import kotlinx.android.synthetic.main.nav_header_main.*

class AppointmentSample : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment_sample)

        val path = intent?.extras?.getString("path")
        val id = intent?.extras?.getString("id")

        tp_time.setIs24HourView(true)

        if(path == null || id == null){
            Log.d(TAG, "path or id is null")
            finish()
        }

        btn_finish.setOnClickListener{finish()}
    }

    companion object{
        const val TAG = "AppointmentSample"
    }
}
