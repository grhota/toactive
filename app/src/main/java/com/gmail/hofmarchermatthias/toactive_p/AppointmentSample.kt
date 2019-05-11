package com.gmail.hofmarchermatthias.toactive_p

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_appointment_sample.*

class AppointmentSample : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment_sample)

        val path = intent?.extras?.getString("path")
        val id = intent?.extras?.getString("id")

        Toast.makeText(this, ""+path+id, Toast.LENGTH_LONG).show()

        btn_finish.setOnClickListener{finish()}
    }


}
