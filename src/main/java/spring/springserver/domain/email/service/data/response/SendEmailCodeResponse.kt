package spring.springserver.domain.email.service.data.response

data class SendEmailCodeResponse(

    var message: String
) {

    companion object {

        fun of(message: String): SendEmailCodeResponse {

            return SendEmailCodeResponse(message)
        }
    }
}
