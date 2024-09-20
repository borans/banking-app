package com.bank.banking_app.dto

import com.bank.banking_app.dto.CustomerAccountDTO
import org.springframework.stereotype.Component

@Component
data class CustomerDTO(
    val id: String?,
    val name: String?,
    val surname: String?,
    val accounts: Set<CustomerAccountDTO>
)
