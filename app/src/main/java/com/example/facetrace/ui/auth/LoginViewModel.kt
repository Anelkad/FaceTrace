package com.example.facetrace.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.LoginUseCase
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
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private var _state = MutableStateFlow<CommonState>(CommonState.HideLoading)
    val state: StateFlow<CommonState> = _state

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _state.value = CommonState.Error(throwable.message.toString())
    }

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _state.value = CommonState.Error("Email or password is empty")
            return
        }
        viewModelScope.launch(exceptionHandler) {
            _state.value = CommonState.ShowLoading
            val response = withContext(Dispatchers.IO) {
                loginUseCase.execute(
                    email = email,
                    password = password
                )
            }
            response.result?.let {
                Log.d("qwerty", "login result is FirebaseUser")
                _state.value = CommonState.Result(it)
            }
            response.error?.let { _state.value = CommonState.Error(it) }
            _state.value = CommonState.HideLoading
        }
    }
}