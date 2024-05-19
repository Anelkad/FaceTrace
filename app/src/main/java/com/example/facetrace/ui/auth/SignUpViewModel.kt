package com.example.facetrace.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.SignUpUseCase
import com.example.facetrace.base.CommonState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
): ViewModel() {
    private var _state = MutableStateFlow<CommonState>(CommonState.HideLoading)
    val state: StateFlow<CommonState> = _state

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _state.value = CommonState.Error(throwable.message.toString())
    }

    fun signUp(email: String, fullname: String, password: String) {
        if (email.isBlank() || password.isBlank() || fullname.isBlank()) {
            _state.value = CommonState.Error("Email, fullname or password is empty")
            return
        }
        viewModelScope.launch(exceptionHandler) {
            _state.value = CommonState.ShowLoading
            val response = withContext(Dispatchers.IO) {
                signUpUseCase.execute(
                    email = email,
                    fullname = fullname,
                    password = password
                )
            }
            response.result?.let {
                Log.d("qwerty", "sign up result is FirebaseUser")
                _state.value = CommonState.Result(it)
            }
            response.error?.let { _state.value = CommonState.Error(it) }
            _state.value = CommonState.HideLoading
        }
    }
}