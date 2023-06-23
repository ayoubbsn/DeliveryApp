package com.example.deliveryapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import com.example.deliveryapplication.model.retrofit.AuthService
import com.example.deliveryapplication.model.retrofit.RetrofitObject
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Response


class loginFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)

        //view initializations
        val skipButton = rootView.findViewById<Button>(R.id.skipBtn)
        val emailEditText = rootView.findViewById<EditText>(R.id.username)
        val passwordEditText = rootView.findViewById<EditText>(R.id.password)
        val logInBtn = rootView.findViewById<Button>(R.id.loginButton)
        val registerTxt = rootView.findViewById<TextView>(R.id.registerRed)

        //skip listener
        skipButton.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }

        registerTxt.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        val retrofitInstace = RetrofitObject.getInstance()
        val authService = retrofitInstace.create(AuthService::class.java)

        logInBtn.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotBlank() && password.isNotBlank()) {
                val loginCall = authService.login(
                    AuthService.LoginRequest(
                        email,
                        password
                    )
                )

                loginCall.enqueue(object : retrofit2.Callback<AuthService.LoginResponse> {
                    override fun onResponse(
                        call: Call<AuthService.LoginResponse>,
                        response: Response<AuthService.LoginResponse>
                    ) {
                        if (response.isSuccessful) {
                            val token = response.body()?.token
                            val sharedPreferences = context?.getSharedPreferences("appPrefs", Context.MODE_PRIVATE)
                            sharedPreferences?.edit {
                                putBoolean("connected", true)
                                putString("token", token)
                                apply()
                            }
                            val intent = Intent(activity, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(activity, "Invalid email or password", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<AuthService.LoginResponse>, t: Throwable) {
                        Toast.makeText(activity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(activity, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

        return rootView
    }
}
