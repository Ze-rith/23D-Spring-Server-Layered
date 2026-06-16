package spring.springserver.domain.payment.data.response

import com.fasterxml.jackson.databind.JsonNode

data class PaymentResponse(
    val paymentKey: String?,
    val orderId: String?,
    val orderName: String?,
    val method: String?,
    val status: String?,
    val totalAmount: Long?,
    val balanceAmount: Long?,
    val requestedAt: String?,
    val approvedAt: String?,
    val isPartialCancelable: Boolean?,
    val card: PaymentCardResponse?,
    val virtualAccount: PaymentVirtualAccountResponse?,
    val cancels: List<PaymentCancelResponse>?,
    val receiptUrl: String?,
    val checkoutUrl: String?,
    val failure: PaymentFailureResponse?,
    val secret: String?
) {

    companion object {

        fun of(node: JsonNode): PaymentResponse {

            return PaymentResponse(
                paymentKey = node.textOrNull("paymentKey"),
                orderId = node.textOrNull("orderId"),
                orderName = node.textOrNull("orderName"),
                method = node.textOrNull("method"),
                status = node.textOrNull("status"),
                totalAmount = node.longOrNull("totalAmount"),
                balanceAmount = node.longOrNull("balanceAmount"),
                requestedAt = node.textOrNull("requestedAt"),
                approvedAt = node.textOrNull("approvedAt"),
                isPartialCancelable = node.booleanOrNull("isPartialCancelable"),
                card = node.objectOrNull("card", PaymentCardResponse::of),
                virtualAccount = node.objectOrNull("virtualAccount", PaymentVirtualAccountResponse::of),
                cancels = node.listOrNull("cancels", PaymentCancelResponse::of),
                receiptUrl = node.path("receipt").textOrNull("url"),
                checkoutUrl = node.path("checkout").textOrNull("url"),
                failure = node.objectOrNull("failure", PaymentFailureResponse::of),
                secret = node.textOrNull("secret")
            )
        }
    }
}

data class PaymentCardResponse(
    val amount: Long?,
    val issuerCode: String?,
    val acquirerCode: String?,
    val number: String?,
    val installmentPlanMonths: Int?,
    val approveNo: String?
) {

    companion object {

        fun of(node: JsonNode): PaymentCardResponse {

            return PaymentCardResponse(
                amount = node.longOrNull("amount"),
                issuerCode = node.textOrNull("issuerCode"),
                acquirerCode = node.textOrNull("acquirerCode"),
                number = node.textOrNull("number"),
                installmentPlanMonths = node.intOrNull("installmentPlanMonths"),
                approveNo = node.textOrNull("approveNo")
            )
        }
    }
}

data class PaymentVirtualAccountResponse(
    val accountNumber: String?,
    val accountType: String?,
    val bankCode: String?,
    val customerName: String?,
    val dueDate: String?,
    val expired: Boolean?,
    val refundStatus: String?
) {

    companion object {

        fun of(node: JsonNode): PaymentVirtualAccountResponse {

            return PaymentVirtualAccountResponse(
                accountNumber = node.textOrNull("accountNumber"),
                accountType = node.textOrNull("accountType"),
                bankCode = node.textOrNull("bankCode"),
                customerName = node.textOrNull("customerName"),
                dueDate = node.textOrNull("dueDate"),
                expired = node.booleanOrNull("expired"),
                refundStatus = node.textOrNull("refundStatus")
            )
        }
    }
}

data class PaymentCancelResponse(
    val transactionKey: String?,
    val cancelReason: String?,
    val cancelAmount: Long?,
    val refundableAmount: Long?,
    val canceledAt: String?,
    val cancelStatus: String?
) {

    companion object {

        fun of(node: JsonNode): PaymentCancelResponse {

            return PaymentCancelResponse(
                transactionKey = node.textOrNull("transactionKey"),
                cancelReason = node.textOrNull("cancelReason"),
                cancelAmount = node.longOrNull("cancelAmount"),
                refundableAmount = node.longOrNull("refundableAmount"),
                canceledAt = node.textOrNull("canceledAt"),
                cancelStatus = node.textOrNull("cancelStatus")
            )
        }
    }
}

data class PaymentFailureResponse(
    val code: String?,
    val message: String?
) {

    companion object {

        fun of(node: JsonNode): PaymentFailureResponse {

            return PaymentFailureResponse(
                code = node.textOrNull("code"),
                message = node.textOrNull("message")
            )
        }
    }
}
