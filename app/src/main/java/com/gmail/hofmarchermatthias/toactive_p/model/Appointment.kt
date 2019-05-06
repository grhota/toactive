package com.gmail.hofmarchermatthias.toactive_p.model

import com.google.firebase.Timestamp
import java.util.*

data class Appointment(
    var timestamp:Timestamp,
    override var title: String,
    override var description: String)
    : Action{
    constructor():this(Timestamp(Date()), "", "")
}