package com.android.internal.test.model

data class Input(
    val description: Any,
    val inputs: List<InputX>,
    val multiple: Boolean,
    val name: String,
    val type: Any
)