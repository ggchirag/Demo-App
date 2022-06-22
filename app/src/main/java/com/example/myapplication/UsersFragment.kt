package com.example.myapplication

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UsersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UsersFragment : Fragment() {
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

    var rec_user: RecyclerView? = null
    private var mDatabase: DatabaseReference? = null
    private var mFirebaseInstance: FirebaseDatabase? = null
    private val List: ArrayList<user> = ArrayList()
    private var userAdapter: userAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v =  inflater.inflate(R.layout.fragment_users, container, false)

        rec_user = v.findViewById<RecyclerView>(R.id.rec_users)

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = mFirebaseInstance!!.getReference("test")

        val progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Loading")
        progressDialog.show()

        mDatabase!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                List.clear()
                for (dataSnapshot1 in snapshot.children) {
                    val user: user? = dataSnapshot1.getValue(user::class.java)
                    List.add(user!!)
                }
                val llm = LinearLayoutManager(activity)
                llm.orientation = LinearLayoutManager.VERTICAL
                userAdapter = userAdapter(List, activity!!)
                rec_user?.setAdapter(userAdapter)
                rec_user?.setLayoutManager(llm)
                userAdapter!!.notifyDataSetChanged()
                progressDialog.hide()
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UsersFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UsersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}