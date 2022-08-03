package com.paypal.android.checkout

import com.paypal.android.checkout.model.order.Order
import com.paypal.checkout.createorder.CreateOrderActions

class PayPalCreateOrderActions internal constructor(private val createOrderActions: CreateOrderActions) {
    /**
     * Starts the pay sheet with an [Order].
     *
     * @param order - [Order] for checkout
     * @param onOrderCreated - Callback for when the order was successfully created
     */
    fun create(order: Order, onOrderCreated: ((orderId: String) -> Unit)) {
        createOrderActions.create(order.toNXO(), onOrderCreated)
    }

    /**
     * Starts the pay sheet with an [Order].
     *
     * @param order - [Order] for checkout
     * @param onOrderCreated - Callback for when the order was successfully created. This callback
     * is optional.
     */
    fun create(order: Order, onOrderCreated: PayPalOnOrderCreated? = null) {

        createOrderActions.create(order.toNXO(),
            CreateOrderActions.OnOrderCreated { orderId -> onOrderCreated?.onCreated(orderId) })
    }

    /**
     * Sets the orderId for checkout.
     * Supports Billing Agreement Id or EC Token
     *
     * @param orderId - id of the order for checkout
     */
    fun set(orderId: String) {
        createOrderActions.set(orderId)
    }

    fun setVaultApprovalSessionId(approvalSessionId: String) {
        createOrderActions.setVaultApprovalSessionId(approvalSessionId)
    }

    fun setBillingAgreementId(billingAgreementId: String) {
        createOrderActions.setBillingAgreementId(billingAgreementId)
    }

    /**
     * Cancels the order and closes the pay sheet
     */
    fun cancel() {
        createOrderActions.cancel()
    }

    interface PayPalOnOrderCreated {
        fun onCreated(orderId: String)

        companion object {
            operator fun invoke(orderCreated: (orderId: String) -> Unit): PayPalOnOrderCreated {
                return object : PayPalOnOrderCreated {
                    override fun onCreated(orderId: String) {
                        orderCreated(orderId)
                    }
                }
            }
        }
    }

}