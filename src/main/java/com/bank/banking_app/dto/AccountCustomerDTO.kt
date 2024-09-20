package com.bank.banking_app.dto

import org.springframework.stereotype.Component

@Component
data class AccountCustomerDTO(

    val id: String?,
    val name: String?,
    val surname: String?

)
