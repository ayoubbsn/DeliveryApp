package com.example.deliveryapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.example.deliveryapplication.model.retrofit.AuthService
import com.example.deliveryapplication.model.retrofit.RetrofitObject
import retrofit2.Call
import retrofit2.Response

class registerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_register, container, false)

        //view initializations
        val name  = rootView.findViewById<EditText>(R.id.edtName)
        val email = rootView.findViewById<EditText>(R.id.edtEmail)
        val phoneNumber = rootView.findViewById<EditText>(R.id.edtPhoneNumber)
        val address = rootView.findViewById<EditText>(R.id.edtAddress)
        val password = rootView.findViewById<EditText>(R.id.edtPassword)
        val registerButton = rootView.findViewById<Button>(R.id.btnRegister)

        // Toolbar essentials
        val toolbar = rootView.findViewById<Toolbar>(R.id.toolbarLogin)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        val retrofitInstance = RetrofitObject.getInstance()
        val authService = retrofitInstance.create(AuthService::class.java)

        registerButton.setOnClickListener {
            val nameText = name.text.toString()
            val emailText = email.text.toString()
            val phoneText = phoneNumber.text.toString()
            val addressText = address.text.toString()
            val passwordText = password.text.toString()

            if (nameText.isNotBlank() && emailText.isNotBlank() && phoneText.isNotBlank() && addressText.isNotBlank() && passwordText.isNotBlank()) {
                val registerCall = authService.register(
                    AuthService.RegisterRequest(
                        nameText,
                        emailText,
                        phoneText,
                        addressText,
                        passwordText
                    )
                )

                registerCall.enqueue(object : retrofit2.Callback<AuthService.RegisterResponse> {
                    override fun onResponse(
                        call: Call<AuthService.RegisterResponse>,
                        response: Response<AuthService.RegisterResponse>
                    ) {
                        if (response.isSuccessful) {
                            val token = response.body()?.token
                            val id = response.body()?.id
                            val sharedPreferences = context?.getSharedPreferences("appPrefs", Context.MODE_PRIVATE)
                            sharedPreferences?.edit {
                                putBoolean("registered", true)
                                putString("token", token)
                                apply()
                            }
                            val intent = Intent(activity, MainActivity::class.java)
                            intent.putExtra("idUser",id)
                            startActivity(intent)
                        } else {
                            Toast.makeText(activity, "Registration failed", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<AuthService.RegisterResponse>, t: Throwable) {
                        Toast.makeText(activity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(activity, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }

        return rootView
    }
}
