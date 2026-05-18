package spring.springserver.domain.email.service.data.request

import jakarta.validation.constraints.Email

data class SendEmailCodeRequest(

    @field:Email
    val email: String
)