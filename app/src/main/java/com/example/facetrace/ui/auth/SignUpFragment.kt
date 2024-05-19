package com.example.facetrace.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.facetrace.R
import com.example.facetrace.base.CommonState
import com.example.facetrace.base.constants.Constants
import com.example.facetrace.base.extensions.hide
import com.example.facetrace.base.extensions.show
import com.example.facetrace.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)
        setupObservers()
        binding.btnSignUp.setOnClickListener {
            viewModel.signUp(
                email = binding.etEmail.text.toString(),
                fullname = binding.etFullname.text.toString(),
                password = binding.etPassword.text.toString()
            )
        }
        binding.btnLogin.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setupObservers() {
        viewModel.state.onEach { state ->
            when (state) {
                is CommonState.Error -> {
                    Toast.makeText(
                        context,
                        state.error,
                        Toast.LENGTH_LONG
                    ).show()
                    binding.apply {
                        progressButton.hide()
                        btnSignUp.text = getString(R.string.sign_up)
                        btnSignUp.isClickable = true
                    }
                }

                is CommonState.HideLoading -> {
                    binding.apply {
                        progressButton.hide()
                        btnSignUp.text = getString(R.string.sign_up)
                        btnSignUp.isClickable = true
                    }
                }

                is CommonState.ShowLoading -> {
                    binding.apply {
                        btnSignUp.text = Constants.EMPTY_STRING
                        progressButton.show()
                        btnSignUp.isClickable = false
                    }
                }

                is CommonState.Result<*> -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, LoginFragment())
                        .addToBackStack(null)
                        .commit()
                }
            }
        }.launchIn(lifecycleScope)
    }
}