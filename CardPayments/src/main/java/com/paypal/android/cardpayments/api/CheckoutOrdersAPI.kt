package com.paypal.android.cardpayments.api

import com.paypal.android.cardpayments.CardRequest
import com.paypal.android.cardpayments.CardResponseParser
import com.paypal.android.corepayments.CoreConfig
import com.paypal.android.corepayments.RestClient

internal class CheckoutOrdersAPI(
    private val restClient: RestClient,
    private val requestFactory: CheckoutOrdersRequestFactory = CheckoutOrdersRequestFactory(),
    private val responseParser: CardResponseParser = CardResponseParser()
) {
    constructor(coreConfig: CoreConfig) : this(RestClient(coreConfig))

    suspend fun confirmPaymentSource(cardRequest: CardRequest): ConfirmPaymentSourceResponse {
        val apiRequest = requestFactory.createConfirmPaymentSourceRequest(cardRequest)
        val httpResponse = restClient.send(apiRequest)

        val error = responseParser.parseError(httpResponse)
        if (error != null) {
            throw error
        } else {
            return responseParser.parseConfirmPaymentSourceResponse(httpResponse)
        }
    }
}
