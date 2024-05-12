package com.example.facetrace.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.facetrace.R
import com.example.facetrace.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        binding.btnSignUp.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, SignUpFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}