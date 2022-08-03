package com.paypal.android.checkout.model.order

import com.paypal.android.checkout.model.enums.CurrencyCode
import com.paypal.android.checkout.model.enums.ItemCategory
import com.paypal.android.checkout.model.enums.OrderIntent
import com.paypal.android.checkout.model.enums.ProcessingInstruction
import com.paypal.android.checkout.model.enums.ShippingPreference
import com.paypal.android.checkout.model.enums.ShippingType
import com.paypal.android.checkout.model.enums.UserAction

class Order @JvmOverloads constructor(
    val intent: OrderIntent,
    val appContext: AppContext? = null,
    val purchaseUnitList: List<PurchaseUnit>,
    /**
     * For most transactions it's recommended to use null or [ProcessingInstruction.NO_INSTRUCTION] for this parameter
     * @see <a href="https://developer.paypal.com/docs/api/orders/v2/#orders_create">Create order API</a>
     */
    val processingInstruction: ProcessingInstruction? = null
) {
    class Builder {
        private lateinit var intent: OrderIntent
        private var processingInstruction: ProcessingInstruction? = null
        private var appContext: AppContext? = null
        private lateinit var purchaseUnitList: List<PurchaseUnit>

        fun build() = Order(intent, appContext, purchaseUnitList, processingInstruction)

        fun intent(intent: OrderIntent) = apply {
            this.intent = intent
        }
        /**
         * For most transactions it's recommended to use null or [ProcessingInstruction.NO_INSTRUCTION] for this parameter
         * @see <a href="https://developer.paypal.com/docs/api/orders/v2/#orders_create">Create order API</a>
         */
        fun processingInstruction(processingInstruction: ProcessingInstruction?) = apply {
            this.processingInstruction = processingInstruction
        }

        fun appContext(appContext: AppContext?) = apply {
            this.appContext = appContext
        }

        fun purchaseUnitList(purchaseUnitList: List<PurchaseUnit>) = apply {
            this.purchaseUnitList = purchaseUnitList
        }
    }

    internal fun toNXO(): com.paypal.checkout.order.Order = com.paypal.checkout.order.Order(
            intent.toNXO(),
            appContext?.toNXO(),
            purchaseUnitList.map { it.toNXO() },
            processingInstruction?.toNXO()
        )
}

class AppContext constructor(
    val returnUrl: String? = null,
    val cancelUrl: String? = null,
    val brandName: String? = null,
    val locale: String? = null,
    val landingPage: String? = null,
    val shippingPreference: ShippingPreference? = null,
    val userAction: UserAction? = null
) {
    class Builder {
        private var returnUrl: String? = null
        private var cancelUrl: String? = null
        private var brandName: String? = null
        private var locale: String? = null
        private var landingPage: String? = null
        private var shippingPreference: ShippingPreference? = null
        private var userAction: UserAction? = null

        fun build() = AppContext(returnUrl, cancelUrl, brandName, locale, landingPage, shippingPreference, userAction)

        fun returnUrl(returnUrl: String?) = apply { this.returnUrl = returnUrl }

        fun cancelUrl(cancelUrl: String?) = apply { this.cancelUrl = cancelUrl }

        fun brandName(brandName: String?) = apply { this.brandName = brandName }

        fun locale(locale: String?) = apply { this.locale = locale }

        fun landingPage(landingPage: String?) = apply { this.landingPage = landingPage }

        fun shippingPreference(shippingPreference: ShippingPreference) = apply { this.shippingPreference = shippingPreference }

        fun userAction(userAction: UserAction) = apply { this.userAction = userAction }
    }

    internal fun toNXO(): com.paypal.checkout.order.AppContext = com.paypal.checkout.order.AppContext(
        returnUrl, cancelUrl, brandName, locale, landingPage, shippingPreference?.toNXO(), userAction?.toNXO()
    )
}

data class PurchaseUnit @JvmOverloads constructor(
    val referenceId: String? = null,
    val description: String? = null,
    val invoiceId: String? = null,
    val customId: String? = null,
    val softDescriptor: String? = null,
    val shipping: Shipping? = null,
    val items: List<Items>? = null,
    val payments: Payments? = null,
    val payee: Payee? = null,
    val amount: Amount
) {
    class Builder {
        private var referenceId: String? = null
        private var description: String? = null
        private var invoiceId: String? = null
        private var customId: String? = null
        private var softDescriptor: String? = null
        private var shipping: Shipping? = null
        private var items: List<Items>? = null
        private var payee: Payee? = null
        private lateinit var amount: Amount

        fun build() = PurchaseUnit(
            referenceId, description, invoiceId, customId, softDescriptor, shipping, items,
            null, payee, amount
        )

        fun referenceId(referenceId: String?) = apply {
            this.referenceId = referenceId
        }

        fun description(description: String?) = apply {
            this.description = description
        }

        fun invoiceId(invoiceId: String?) = apply {
            this.invoiceId = invoiceId
        }

        fun customId(customId: String?) = apply {
            this.customId = customId
        }

        fun softDescriptor(softDescriptor: String?) = apply {
            this.softDescriptor = softDescriptor
        }

        fun shipping(shipping: Shipping?) = apply { this.shipping = shipping }

        fun items(items: List<Items>?) = apply { this.items = items }

        fun amount(amount: Amount) = apply { this.amount = amount }

        fun payee(payee: Payee?) = apply { this.payee = payee }
    }

    internal fun toNXO() = com.paypal.checkout.order.PurchaseUnit(
        referenceId, description, invoiceId, customId, softDescriptor, shipping?.toNXO(), items?.map { it.toNXO() }, payments?.toNXO(), payee?.toNXO(), amount.toNXO())
}

/**
 * The total order amount with an optional breakdown that provides details, such as the total
 * item amount, total tax amount, shipping, handling, and discounts, if any.
 *
 * If you specify [breakdown], the amount equals [BreakDown.itemTotal] plus [BreakDown.taxTotal]
 * plus [BreakDown.shipping] plus [BreakDown.handling] minus [BreakDown.shippingDiscount] minus
 * [BreakDown.discount].
 *
 * The amount must be a positive number.
 */
data class Amount constructor(

    /**
     * The three-character ISO-4217 currency code that identifies the currency. See [CurrencyCode].
     */
    val currencyCode: CurrencyCode,

    /**
     * The value, which might be:
     *    - An integer for currencies like JPY that are not typically fractional.
     *    - A decimal fraction for currencies like TND that are subdivided into thousandths.
     *
     * For the required number of decimal places for a currency code, see Currency Codes.
     */
    val value: String,

    /**
     * The breakdown of the amount. Breakdown provides details such as total item amount, total tax
     * amount, shipping, handling, insurance, and discounts, if any.
     */
    val breakdown: BreakDown? = null
) {
    class Builder {
        private lateinit var currencyCode: CurrencyCode
        private lateinit var value: String
        private var breakdown: BreakDown? = null

        fun build() = Amount(currencyCode, value, breakdown)

        fun currencyCode(currencyCode: CurrencyCode) = apply { this.currencyCode = currencyCode }

        fun value(value: String) = apply { this.value = value }

        fun breakdown(breakdown: BreakDown?) = apply { this.breakdown = breakdown }
    }

    internal fun toNXO() : com.paypal.checkout.order.Amount = com.paypal.checkout.order.Amount(currencyCode.toNXO(), value, breakdown?.toNXO())
}

/**
 * The breakdown of the amount. Breakdown provides details such as total item amount, total tax
 * amount, shipping, handling, insurance, and discounts, if any.
 */
data class BreakDown constructor(

    /**
     * The subtotal for all items. Required if the [Order] includes [Items.unitAmount]. Must equal
     * the sum of ([Items.unitAmount] * [Items.quantity]) for all items. [itemTotal]'s value can not
     * be a negative number.
     */
    val itemTotal: UnitAmount? = null,

    /**
     * The shipping fee for all items within a given purchase unit. [shipping]' value can not be a
     * negative number.
     */
    val shipping: UnitAmount? = null,

    /**
     * The handling fee for all items within a given purchase unit. [handling]'s value can not be a
     * negative number.
     */
    val handling: UnitAmount? = null,

    /**
     * The total tax for all items. Required if the order includes [Items.tax]. Must equal the sum
     * of ([Items.tax] * [Items.quantity]) for all items. [taxTotal]'s value can not be a negative
     * number.
     */
    val taxTotal: UnitAmount? = null,

    /**
     * The shipping discount for all items within a given purchase unit. [shippingDiscount]'s value
     * can not be a negative number.
     */
    val shippingDiscount: UnitAmount? = null,

    /**
     * The discount for all items within a given purchase unit. [discount]'s value can not be a
     * negative number.
     */
    val discount: UnitAmount? = null
) {
    class Builder {
        private var itemTotal: UnitAmount? = null
        private var shipping: UnitAmount? = null
        private var handling: UnitAmount? = null
        private var taxTotal: UnitAmount? = null
        private var shippingDiscount: UnitAmount? = null
        private var discount: UnitAmount? = null

        fun build() = BreakDown(itemTotal, shipping, handling, taxTotal, shippingDiscount, discount)

        fun itemTotal(itemTotal: UnitAmount?) = apply { this.itemTotal = itemTotal }

        fun shipping(shipping: UnitAmount?) = apply { this.shipping = shipping }

        fun handling(handling: UnitAmount?) = apply { this.handling = handling }

        fun taxTotal(taxTotal: UnitAmount?) = apply { this.taxTotal = taxTotal }

        fun shippingDiscount(shippingDiscount: UnitAmount?) = apply { this.shippingDiscount = shippingDiscount }

        fun discount(discount: UnitAmount?) = apply { this.discount = discount }
    }

    internal fun toNXO(): com.paypal.checkout.order.BreakDown = com.paypal.checkout.order.BreakDown(
        itemTotal?.toNXO(),
        shipping?.toNXO(),
        handling?.toNXO(),
        taxTotal?.toNXO(),
        shippingDiscount?.toNXO(),
        discount?.toNXO()
    )
}

class Items private constructor(
    val name: String,
    val description: String? = null,
    val sku: String? = null,
    val quantity: String,
    val category: ItemCategory? = null,
    val tax: UnitAmount? = null,
    val unitAmount: UnitAmount
) {
    class Builder {
        private lateinit var name: String
        private var description: String? = null
        private var sku: String? = null
        private lateinit var quantity: String
        private var category: ItemCategory? = null
        private var tax: UnitAmount? = null
        private lateinit var unitAmount: UnitAmount

        fun build() = Items(name, description, sku, quantity, category, tax, unitAmount)

        fun name(name: String) = apply { this.name = name }

        fun description(description: String?) = apply { this.description = description }

        fun sku(sku: String?) = apply { this.sku = sku }

        fun quantity(quantity: String) = apply { this.quantity = quantity }

        fun category(category: ItemCategory) = apply { this.category = category }

        fun tax(tax: UnitAmount?) = apply { this.tax = tax }

        fun unitAmount(unitAmount: UnitAmount) = apply { this.unitAmount = unitAmount }
    }

    internal fun toNXO():  com.paypal.checkout.order.Items {
        val builder = com.paypal.checkout.order.Items.Builder()
            .name(name)
            .description(description)
            .sku(sku)
            .quantity(quantity)
            .tax(tax?.toNXO())
            .unitAmount(unitAmount.toNXO())
        category?.apply {  builder.category(category.toNXO()) }
        return builder.build()
    }
}

data class UnitAmount constructor(
    val currencyCode: CurrencyCode,
    val value: String
) {
    class Builder {
        private lateinit var currencyCode: CurrencyCode
        private lateinit var value: String

        fun build() = UnitAmount(currencyCode, value)

        fun currencyCode(currencyCode: CurrencyCode) = apply { this.currencyCode = currencyCode }

        fun value(value: String) = apply { this.value = value }
    }

    internal fun toNXO() = com.paypal.checkout.order.UnitAmount(currencyCode.toNXO(), value)
}

data class Shipping constructor(val address: Address? = null, val options: List<Options>? = null) {
    class Builder {
        private var address: Address? = null
        private var options: List<Options>? = null

        fun build() = Shipping(address, options)

        fun address(address: Address?) = apply { this.address = address }

        fun options(options: List<Options>?) = apply { this.options = options }
    }

    internal fun toNXO() = com.paypal.checkout.order.Shipping(address?.toNXO(), options?.map { it.toNXO() })
}

data class Address constructor(
    val addressLine1: String? = null,
    val addressLine2: String? = null,
    val adminArea1: String? = null,
    val adminArea2: String? = null,
    val postalCode: String? = null,
    val countryCode: String
) {
    class Builder {
        private var addressLine1: String? = null
        private var addressLine2: String? = null
        private var adminArea1: String? = null
        private var adminArea2: String? = null
        private var postalCode: String? = null
        private lateinit var countryCode: String

        fun build() = Address(addressLine1, addressLine2, adminArea1, adminArea2, postalCode, countryCode)

        fun addressLine1(addressLine1: String?) = apply { this.addressLine1 = addressLine1 }

        fun addressLine2(addressLine2: String?) = apply { this.addressLine2 = addressLine2 }

        fun adminArea1(adminArea1: String?) = apply { this.adminArea1 = adminArea1 }

        fun adminArea2(adminArea2: String?) = apply { this.adminArea2 = adminArea2 }

        fun postalCode(postalCode: String?) = apply { this.postalCode = postalCode }

        fun countryCode(countryCode: String) = apply { this.countryCode = countryCode }
    }

    internal fun toNXO() = com.paypal.checkout.order.Address(addressLine1, addressLine2, adminArea1, adminArea2, postalCode, countryCode)
}

data class Options constructor(
    val id: String,
    val selected: Boolean,
    val label: String,
    val type: ShippingType? = null,
    val amount: UnitAmount? = null
) {
    class Builder {
        private lateinit var id: String
        private var selected: Boolean = false
        private lateinit var label: String
        private var type: ShippingType? = null
        private var amount: UnitAmount? = null

        fun build() = Options(id, selected, label, type, amount)

        fun id(id: String) = apply { this.id = id }

        fun selected(selected: Boolean) = apply { this.selected = selected }

        fun label(label: String) = apply { this.label = label }

        fun type(type: ShippingType?) = apply { this.type = type }

        fun amount(amount: UnitAmount?) = apply { this.amount = amount }
    }

    internal fun toNXO() = com.paypal.checkout.order.Options(id, selected, label,
        type?.toNXO(), amount?.toNXO())
}

/**
 * The comprehensive history of payments for a purchase unit.
 */
data class Payments(

    /**
     * An array of captured payments for a purchase unit. A purchase unit can have zero or more
     * captured payments.
     */
    val captures: List<Capture>,

    /**
     * An array of authorized payments for a purchase unit. A purchase unit can have zero or more
     * authorized payments.
     */
    val authorizations: List<Authorization>
) {
    internal fun toNXO() = com.paypal.checkout.order.Payments(captures.map { it.toNXO() }, authorizations.map { it.toNXO() })
}

/**
 * The merchant who receives payment for this transaction.
 */
data class Payee(
    /**
     * The email address of merchant
     */
    val emailAddress: String? = null,
    /**
     * The encrypted PayPal account ID of the merchant.
     */
    val merchantId: String? = null
) {
    internal fun toNXO() = com.paypal.checkout.order.Payee(emailAddress, merchantId)
}

data class Capture(
    /**
     * The PayPal-generated ID for the captured payment.
     */
    val id: String,

    /**
     * The status of the captured payment.
     */
    val status: String,

    /**
     * The amount for this captured payment.
     */
    val amount: UnitAmount,

    /**
     * Indicates whether you can make additional captures against the authorized payment. Set to
     * true if you do not intend to capture additional payments against the authorization. Set to
     * false if you intend to capture additional payments against the authorization.
     */
    val finalCapture: Boolean,

    /**
     * The level of protection offered as defined by [PayPal Seller Protection for Merchants](https://www.paypal.com/us/webapps/mpp/security/seller-protection?_ga=1.263606859.655062276.1607541632).
     */
    val sellerProtection: SellerProtection
) {
    internal fun toNXO() = com.paypal.checkout.order.Capture(id, status, amount.toNXO(), finalCapture, sellerProtection.toNXO())
}

data class Authorization(
    /**
     * The PayPal-generated ID for the authorized payment.
     */
    val id: String,

    /**
     * The status of the authorized payment.
     */
    val status: String,

    /**
     * The amount for this authorized payment.
     */
    val amount: UnitAmount,

    /**
     * The level of protection offered as defined by [PayPal Seller Protection for Merchants](https://www.paypal.com/us/webapps/mpp/security/seller-protection?_ga=1.263606859.655062276.1607541632).
     */
    val sellerProtection: SellerProtection
) {
    internal fun toNXO() = com.paypal.checkout.order.Authorization(id, status, amount.toNXO(), sellerProtection.toNXO())
}

data class SellerProtection(
    val status: SellerProtectionStatus,
    val disputeCategories: List<DisputeCategory>
) {
    internal fun toNXO() = com.paypal.checkout.order.SellerProtection(status.toNXO(), disputeCategories.map { it.toNXO() })
}

/**
 * This is a read only field that indicated whether a transaction is eligible for seller protection.
 * For information, see [PayPal Seller Protection for Merchants](https://www.paypal.com/us/webapps/mpp/security/seller-protection?_ga=1.32406265.655062276.1607541632).
 */
enum class SellerProtectionStatus {
    /**
     * Your PayPal balance remains intact if the customer claims that they did not receive an item
     * or the account holder claims that they did not authorize the payment.
     */
    ELIGIBLE,

    /**
     * Your PayPal balance remains intact if the customer claims that they did not receive an item.
     */
    PARTIALLY_ELIGIBLE,

    /**
     * This transaction is not eligible for seller protection.
     */
    NOT_ELIGIBLE;

    internal fun toNXO() = com.paypal.checkout.order.SellerProtectionStatus.valueOf(this.name)
}

/**
 * Categorizes various disputes that can occur on a transaction.
 */
enum class DisputeCategory {
    /**
     * The payer paid for an item that they did not receive.
     */
    ITEM_NOT_RECEIVED,

    /**
     * The payer did not authorize the payment.
     */
    UNAUTHORIZED_TRANSACTION;

    internal fun toNXO() = com.paypal.checkout.order.DisputeCategory.valueOf(this.name)
}