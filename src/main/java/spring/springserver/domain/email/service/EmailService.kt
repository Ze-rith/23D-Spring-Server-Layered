package spring.springserver.domain.email.service

import spring.springserver.domain.email.service.data.response.SendEmailCodeResponse

interface EmailService {

    fun sendEmailCode(email: String): SendEmailCodeResponse
}