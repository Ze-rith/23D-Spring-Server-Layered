package spring.springserver.domain.payment.data.request

import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class VirtualAccountRequest(
    @field:Min(value = 1, message = "amount는 1 이상이어야 합니다.")
    val amount: Long,

    @field:NotBlank(message = "orderId는 필수입니다.")
    val orderId: String,

    @field:NotBlank(message = "orderName은 필수입니다.")
    val orderName: String,

    @field:NotBlank(message = "customerName은 필수입니다.")
    val customerName: String,

    @field:NotBlank(message = "bank는 필수입니다.")
    val bank: String,

    val validHours: Int? = null,
    val dueDate: String? = null,
    val customerEmail: String? = null,
    val customerMobilePhone: String? = null,

    @field:Min(value = 0, message = "taxFreeAmount는 0 이상이어야 합니다.")
    val taxFreeAmount: Long? = null,

    val useEscrow: Boolean? = null,

    @field:Valid
    val cashReceipt: VirtualAccountCashReceiptRequest? = null,

    @field:Valid
    val escrowProducts: List<EscrowProductRequest>? = null
)

data class VirtualAccountCashReceiptRequest(
    @field:NotBlank(message = "type은 필수입니다.")
    val type: String,

    val registrationNumber: String? = null
)

data class EscrowProductRequest(
    @field:NotBlank(message = "id는 필수입니다.")
    val id: String,

    @field:NotBlank(message = "name은 필수입니다.")
    val name: String,

    @field:NotBlank(message = "code는 필수입니다.")
    val code: String,

    @field:Min(value = 1, message = "unitPrice는 1 이상이어야 합니다.")
    val unitPrice: Long,

    @field:Min(value = 1, message = "quantity는 1 이상이어야 합니다.")
    val quantity: Int
)
