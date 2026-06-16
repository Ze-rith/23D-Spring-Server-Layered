package spring.springserver.domain.payment.data.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class ConfirmPaymentRequest(
    @field:NotBlank(message = "paymentKey는 필수입니다.")
    val paymentKey: String,

    @field:NotBlank(message = "orderId는 필수입니다.")
    val orderId: String,

    @field:Min(value = 1, message = "amount는 1 이상이어야 합니다.")
    val amount: Long
)
