package com.bank.banking_app.model

import com.bank.banking_app.model.Account
import jakarta.persistence.*

@Entity
data class Customer(

    // we give account models to a special ID, and it is a generated random hash value.
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String?,
    val name: String?,
    val surname: String?,

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    val accounts: Set<Account>,

    ) {


    // test amaçlı contrsutor integration testlerde bununla customer objesi yarattık.
    constructor(name:String, surname:String) : this("", name, surname, HashSet())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Customer

        if (id != other.id) return false
        if (name != other.name) return false
        if (surname != other.surname) return false
        if (accounts != other.accounts) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (surname?.hashCode() ?: 0)
        return result
    }
}
