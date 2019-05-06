package com.gmail.hofmarchermatthias.toactive_p

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.gmail.hofmarchermatthias.toactive_p.model.Appointment
import com.google.firebase.firestore.FirebaseFirestore




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
    //region badFields
    /**
     * Ignore these Params
     */
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    //endregion badFiels

    //region database
    private val db = FirebaseFirestore.getInstance()
    private val notebookRef = db.collection("Notebook")
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

    override fun onStart() {
        super.onStart()


        val query = FirebaseFirestore.getInstance()
            .collection("test")
            .orderBy("timestamp")
            .limit(50)

        var firestoreRecyclerOptions = FirestoreRecyclerOptions.Builder<Appointment>().
            setQuery(query, Appointment::class.java)
            .build()
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
}
