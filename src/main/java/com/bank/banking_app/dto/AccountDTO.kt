package com.bank.banking_app.dto

import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDateTime


/**
DTOlar senin API ile dışarı bilgi aktarımında ya da bilgi alımında kullandığın şeyler oluyor. Direkt
modeller üzerinden data akışını sağlamayıp DTOlar üzerinden yaparak modellerini safete tutuyorsun
hem daha secure oluyor hem de daha kullanışlı oluyor. Modellere çok benzer ama sadece bilgilerini kullanan
nesneler. Bir değişiklik yapmak istediğinde toptan modellerini değiştirmeyip DTOları değiştirerek tüm sistemindeki
yeni değişiklik yapılan veri akışlarını kumanda etmiş oluyorsun DTOlar sayesinde.
 **/

@Component
data class AccountDTO (
    val id: String?,
    val creationDate: LocalDateTime?,
    val balance: BigDecimal? = BigDecimal.ZERO,
    val customer: AccountCustomerDTO?,
    val transactions: Set<TransactionDTO>?
    ){}