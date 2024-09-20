package com.bank.banking_app.model



import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime


//  Why use Kotlin dataclass --> to making things more efficient we skip between Kotlin and Java
//  Why use data class not class --> to not be bothered by getter/setter and more efficient.

@Entity
data class Account(

    // we give account models to a special ID and it is a generated random hash value.
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = "",
    val balance: BigDecimal? = BigDecimal.ZERO,
    val creationDate: LocalDateTime,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "customer_id", nullable = false)
    val customer: Customer?,

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    val transactions: Set<Transaction>?


){

    constructor(balance: BigDecimal, creationDate: LocalDateTime, customer: Customer) : this(
        "",
        balance = balance,
        creationDate = creationDate,
        customer = customer,
        transactions = HashSet<Transaction>()
    )



    // All this below is code generated for creating hash value.

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Account

        if (id != other.id) return false
        if (balance != other.balance) return false
        if (creationDate != other.creationDate) return false
        if (customer != other.customer) return false
        if (transactions != other.transactions) return false

        return true
    }

    // burada customer ve set transaction objelerini de kullanıyor hashcode yaratırken ama biz onları sildik.
    //sebebi hashcode yaratırken diğer nesnelerle loopa girmekten kaçınmam. Diğer objeker için de aynısını yapıcaz.
    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (balance?.hashCode() ?: 0)
        result = 31 * result + creationDate.hashCode()
        return result
    }
}