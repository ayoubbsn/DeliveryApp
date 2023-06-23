package com.example.deliveryapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.edit
import kotlin.math.log


class profilFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_profil, container, false)

        inserTrue(context)
        val pref = context?.getSharedPreferences("appPrefs", Context.MODE_PRIVATE)
        var res: Boolean = pref?.getBoolean("connected", false) as Boolean

        val logOut = rootView.findViewById<CardView>(R.id.logoutCard)

        if (!res) updateView(rootView, "log In") else updateView(rootView, "log out")

        logOut.setOnClickListener {
            res = pref.getBoolean("connected", false)
            if (res) {
                pref.edit {
                    putBoolean("connected", false)
                    //delete the token
                    updateView(rootView, "Log In")
                    Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show()
                }
            } else {
                logOut.setOnClickListener {
                    val intent  = Intent(activity , LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        }


        return rootView
    }

    fun updateView(view: View, text: String) {
        val logText = view.findViewById<TextView>(R.id.logText)
        logText.text = text
    }

    fun inserTrue(context: Context?) {
        val pref = context?.getSharedPreferences("appPrefs", Context.MODE_PRIVATE) ?: return
        pref.edit {
            putBoolean("connected", true)
            apply()
        }

    }
}
