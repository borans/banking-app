package com.bank.banking_app.dto

import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDateTime

@Component
data class CustomerAccountDTO(

    val id: String?,
    val balance: BigDecimal? = BigDecimal.ZERO,
    val creationDate: LocalDateTime?,
    val transactions: Set<TransactionDTO>?
)
