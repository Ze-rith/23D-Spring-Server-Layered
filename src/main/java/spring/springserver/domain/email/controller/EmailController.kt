package spring.springserver.domain.email.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import spring.springserver.domain.email.service.EmailService
import spring.springserver.domain.email.service.data.request.SendEmailCodeRequest
import spring.springserver.domain.email.service.data.response.SendEmailCodeResponse
import spring.springserver.global.data.BaseResponse

@RestController
@RequestMapping("/api/email")
class EmailController(private val emailService: EmailService) {

    @PostMapping("/code/send")
    fun sendVerifyCode(@Valid @RequestBody sendEmailCodeRequest: SendEmailCodeRequest): BaseResponse<SendEmailCodeResponse> {

        return BaseResponse.ok(emailService.sendEmailCode(sendEmailCodeRequest.email))
    }
}