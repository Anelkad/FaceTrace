package com.example.facetrace.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.facetrace.MainActivity
import com.example.facetrace.R
import com.example.facetrace.base.CommonState
import com.example.facetrace.base.constants.Constants
import com.example.facetrace.base.extensions.hide
import com.example.facetrace.base.extensions.show
import com.example.facetrace.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        binding.apply {
            btnSignUp.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, SignUpFragment())
                    .addToBackStack(null)
                    .commit()
            }
            btnLogin.setOnClickListener {
                viewModel.login(binding.etUsername.text.toString(), binding.etPassword.text.toString())
            }
        }
        setupObservers()
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
                        btnLogin.text = getString(R.string.login)
                        btnLogin.isClickable = true
                    }
                }

                is CommonState.HideLoading -> {
                    binding.apply {
                        progressButton.hide()
                        btnLogin.text = getString(R.string.login)
                        btnLogin.isClickable = true
                    }
                }

                is CommonState.ShowLoading -> {
                    binding.apply {
                        btnLogin.text = Constants.EMPTY_STRING
                        progressButton.show()
                        btnLogin.isClickable = false
                    }
                }

                is CommonState.Result<*> -> {
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        }.launchIn(lifecycleScope)
    }
}