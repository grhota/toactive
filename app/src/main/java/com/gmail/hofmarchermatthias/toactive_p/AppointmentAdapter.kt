package com.gmail.hofmarchermatthias.toactive_p

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.gmail.hofmarchermatthias.toactive_p.model.Appointment
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.item_appointment.view.*

class AppointmentAdapter(firestoreRecyclerOptions: FirestoreRecyclerOptions<Appointment>)
    : FirestoreRecyclerAdapter<Appointment,
        AppointmentAdapter.AppointmentHolder>(firestoreRecyclerOptions)
{
    lateinit var onItemClickListener: OnItemClickListener get set


    override fun onBindViewHolder(holder: AppointmentHolder, position: Int, model: Appointment) {
        holder.itemView.appointment_title.text = model.title
        holder.itemView.appointment_description.text = model.description
        holder.itemView.appointment_start.text = model.timeStamp.toString()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AppointmentHolder {
        LayoutInflater.from(viewGroup.context).inflate(R.layout.item_appointment, viewGroup, false)
        return AppointmentHolder(viewGroup)
    }

    fun deleteItem(position: Int){
        snapshots.getSnapshot(position).reference.delete()
    }

    inner class AppointmentHolder(v:View): RecyclerView.ViewHolder(v) {
        init {
            v.setOnClickListener{
                val currentPos = adapterPosition
                if(currentPos != RecyclerView.NO_POSITION && ::onItemClickListener.isInitialized){
                    onItemClickListener.onItemClick(snapshots.getSnapshot(currentPos), currentPos)
                }
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(documentSnapshot: DocumentSnapshot, position: Int)
    }

}