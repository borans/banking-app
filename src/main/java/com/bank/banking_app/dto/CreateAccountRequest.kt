package com.bank.banking_app.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import org.springframework.stereotype.Component
import java.math.BigDecimal


data class CreateAccountRequest(
    @field:NotBlank
    val customerId: String,
    @field:Min(0)
    val initialCredit: BigDecimal
)
