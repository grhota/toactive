package com.gmail.hofmarchermatthias.toactive_p

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.gmail.hofmarchermatthias.toactive_p.model.Appointment
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_archive.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ArchiveFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ArchiveFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ArchiveFragment : Fragment() {
    //region params
    /**
     * Ignore these Params
     */
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ArchiveFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ArchiveFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    //endregion params
    //region database
    private val db = FirebaseFirestore.getInstance()
    private val notebookRef = db.collection("Users")
        .document("TestUser")
        .collection("Data")
    //endregion database

    private lateinit var appointmentAdapter:AppointmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_archive, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        appointmentAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        appointmentAdapter.stopListening()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }



    fun setUpRecyclerView(){
        val query = notebookRef.orderBy("timestamp", Query.Direction.ASCENDING)
        val options = FirestoreRecyclerOptions.Builder<Appointment>()
            .setQuery(query, Appointment::class.java)
            .build()

        appointmentAdapter = AppointmentAdapter(options)
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = LinearLayoutManager(this.context)
        recycler_view.adapter = appointmentAdapter

        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
                appointmentAdapter.deleteItem(p0.adapterPosition)
            }
        }).attachToRecyclerView(recycler_view)

        appointmentAdapter.onItemClickListener=(object : AppointmentAdapter.OnItemClickListener{
            override fun onItemClick(documentSnapshot: DocumentSnapshot, position: Int) {
                val note = documentSnapshot.toObject(Appointment::class.java)
                val id = documentSnapshot.id
                val path = documentSnapshot.reference.path

                Toast.makeText(this@ArchiveFragment.context, "Position: "+position+" ID: "+id, Toast.LENGTH_LONG)
                    .show()
            }

        })
    }
}
