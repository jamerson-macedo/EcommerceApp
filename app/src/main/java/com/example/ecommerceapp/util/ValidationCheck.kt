package com.example.ecommerceapp.util

import android.util.Patterns

fun validadeEmail(email: String): RegisterValidation {
    if (email.isEmpty()) {

        return RegisterValidation.Failed("Preencha o campo de Email")
    }
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        return RegisterValidation.Failed("Email invalido")
    }

    return RegisterValidation.Success
}

fun validadePasWord(password: String): RegisterValidation {
    if (password.isEmpty()) {
        return RegisterValidation.Failed("Preencha o campo senha")
    }
    if (password.length < 6) {
        return RegisterValidation.Failed("Senha curta")
    }
    return RegisterValidation.Success
}