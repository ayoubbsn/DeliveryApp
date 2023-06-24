package com.example.deliveryapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope

class profilFragment : Fragment() {

    private var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //root view
        val rootView = inflater.inflate(R.layout.fragment_profil, container, false)
        //view model
        val viewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]

        //initialization
        val userNameProfile = rootView.findViewById<TextView>(R.id.nameProfil)
        val logOut = rootView.findViewById<CardView>(R.id.logoutCard)
        val logText = rootView.findViewById<TextView>(R.id.logText)

        //getting the id
        val pref0 = context?.getSharedPreferences("idUser",Context.MODE_PRIVATE)
        id = pref0?.getInt("idUser",0) ?: 0

        //updating the data
        viewModel.userLiveData.observe(viewLifecycleOwner, Observer { user ->
            userNameProfile.text = user.name
        })
        viewModel.getUser(id)


        val pref = context?.getSharedPreferences("appPrefs", Context.MODE_PRIVATE)
        var res: Boolean = pref?.getBoolean("connected", false) ?: false

        logText.text = if (!res) "log In" else "log Out"

        logOut.setOnClickListener {
            res = pref?.getBoolean("connected", false) ?: false
            if (res) {
                pref?.edit {
                    putBoolean("connected", false)
                    apply()
                }
                pref0?.edit {
                    putInt("idUser",0)
                    apply()
                }
                userNameProfile.text="Guest"
                logText.text = "Log In"
                Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show()
            } else {
                val intent  = Intent(activity , LoginActivity::class.java)
                startActivity(intent)
            }
        }

        return rootView
    }
    fun updateView(view: View, text: String) {
        val logText = view.findViewById<TextView>(R.id.logText)
        logText.text = text
    }


}
