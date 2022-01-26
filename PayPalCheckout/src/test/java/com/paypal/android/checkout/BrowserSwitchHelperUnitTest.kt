package com.paypal.android.checkout

import android.net.Uri
import com.paypal.android.core.CoreConfig
import com.paypal.android.core.Environment
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import strikt.api.expectThat
import strikt.assertions.isEqualTo

@RunWith(RobolectricTestRunner::class)
class BrowserSwitchHelperUnitTest {

    @Test
    fun `when configurePayPalBrowserSwitchOptions is executed, the correct BrowserSwitchOptions is returned`() {
        val mockOrderId = generateRandomString()
        val mockCoreConfig = mockk<CoreConfig>(relaxed = true)

        val urlScheme = "com.android.test.scheme"
        val finalUrl = "https://www.sandbox.paypal.com/checkoutnow?token=$mockOrderId&redirect_uri=$urlScheme%3A%2F%2Fx-callback-url%2Fpaypal-sdk%2Fpaypal-checkout&native_xo=1"
        val uri = Uri.parse(finalUrl)

        every { mockCoreConfig.environment } returns Environment.SANDBOX

        val browserSwitchHelper = BrowserSwitchHelper(urlScheme)
        val browserSwitchOptions = browserSwitchHelper.configurePayPalBrowserSwitchOptions(mockOrderId, mockCoreConfig)

        expectThat(browserSwitchOptions) {
            get { metadata?.get("order_id") }.isEqualTo(mockOrderId)
            get { returnUrlScheme }.isEqualTo(urlScheme)
            get { url }.isEqualTo(uri)
        }
    }
}