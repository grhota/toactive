package com.gmail.hofmarchermatthias.toactive_p.model

import com.google.firebase.Timestamp

data class Appointment(
    var timeStamp:Timestamp,
    override var title: String,
    override var description: String) : Action