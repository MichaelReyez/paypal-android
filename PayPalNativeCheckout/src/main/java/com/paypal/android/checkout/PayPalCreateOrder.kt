package com.paypal.android.checkout

interface PayPalCreateOrder {
    fun create(createOrderActions: PayPalCreateOrderActions)

    companion object {
        operator fun invoke(orderCreate: (PayPalCreateOrderActions) -> Unit): PayPalCreateOrder {
            return object : PayPalCreateOrder {
                override fun create(createOrderActions: PayPalCreateOrderActions) {
                    return orderCreate(createOrderActions)
                }
            }
        }
    }
}