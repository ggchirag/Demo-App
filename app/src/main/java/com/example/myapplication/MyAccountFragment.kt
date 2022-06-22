package com.example.myapplication

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.publicClass.Phone
import com.google.firebase.database.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyAccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyAccountFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private var mDatabase: DatabaseReference? = null
    private var mFirebaseInstance: FirebaseDatabase? = null
    lateinit var Name: TextView
    lateinit var Phone: TextView
    lateinit var Email: TextView

    lateinit var progressDialog:ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_my_account, container, false)

        Name = v.findViewById(R.id.Name)
        Phone = v.findViewById(R.id.Phone)
        Email = v.findViewById(R.id.Email)

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = mFirebaseInstance!!.getReference("test")
        getData()

        return v
    }

    private fun getData() {
        progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Loading")
        progressDialog.show()

        mDatabase?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Name.text = Name.text.toString() + snapshot.child(publicClass.Phone).child("name").value.toString()
                Phone.text = Phone.text.toString() +snapshot.child(publicClass.Phone).child("mobile").value.toString()
                Email.text = Email.text.toString() +snapshot.child(publicClass.Phone).child("email").value.toString()
                progressDialog.hide()
            }

            override fun onCancelled(error: DatabaseError) {
                progressDialog.hide()
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyAccountFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyAccountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}