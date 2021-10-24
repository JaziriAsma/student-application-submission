package com.android.internal.test.model

data class Constraint(
    val explanation: String,
    val expression: String,
    val inputNames: List<String>,
    val name: String
)