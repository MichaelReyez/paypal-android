Welcome to the PayPal Android SDK. This library helps you accept card and PayPal payments in your app.

![Maven Central](https://img.shields.io/maven-central/v/com.paypal.android/card-payments?style=for-the-badge) ![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/com.paypal.android/card-payments?server=https%3A%2F%2Foss.sonatype.org&style=for-the-badge)

## Support
The PayPal Android SDK is available for **Android SDK 21+**. See our [Client Deprecation policy](https://developer.paypal.com/braintree/docs/guides/client-sdk/deprecation-policy/android/v4) to plan for updates.

### Languages
This SDK is written in Kotlin and supports both Kotlin and Java integrations.

## Installing the SDK
You can support a specific payment method by adding its corresponding feature module as a dependency in your app's `build.gradle` file.
For example, to support both CardPayments and PayPalWebPayments in your app include the following dependencies with the current version:

![Maven Central](https://img.shields.io/maven-central/v/com.paypal.android/card-payments?style=for-the-badge)
```groovy
dependencies {
  implementation 'com.paypal.android:card-payments:<CURRENT-VERSION>'
  implementation 'com.paypal.android:paypal-web-payments:<CURRENT-VERSION>'
}
```

Snapshot builds of the latest SDK features are published from the `main` branch weekly. The snapshot builds can be used to test upcoming features before they have been released. To include a snapshot build, first add the repository to the top `build.gradle` file in your project.

![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/com.paypal.android/card-payments?server=https%3A%2F%2Foss.sonatype.org&style=for-the-badge)
```groovy
repositories {
    maven {
        url 'https://oss.sonatype.org/content/repositories/snapshots/'
    }
}
```

Then, add the dependency:

```groovy
dependencies {
  implementation 'com.paypal.android:card-payments:<CURRENT-VERSION>-SNAPSHOT'
}
```

## Demo

See our[Kotlin Demo App](/Demo) for a sample integration. 

## Integrating the SDK

An `ACCESS_TOKEN` and `ORDER_ID` are required for all payment method flows.

### 1. Access Token

The PayPal SDK uses access tokens for authentication.

On your server, fetch an `ACCESS_TOKEN` using PayPal's [Authentication API](https://developer.paypal.com/api/rest/authentication/). 

_Note: This access token is only for the sandbox environment. You’ll need to get a live access token when you’re ready to go live. To do so, replace the request sandbox URL with: https://api-m.paypal.com/v1/oauth2/token._

### 2. Order ID

On your server, use the [Orders v2 API](https://developer.paypal.com/docs/api/orders/v2) to create an `ORDER_ID`. Use your `ACCESS_TOKEN` from in the Authorization header.

_Note: You’ll need to pass either `AUTHORIZE` or `CAPTURE` as the intent type. This type must match the `/authorize` or `/capture` endpoint you use to process your order at the end of the integration._

### 3. Payment Method modules

Each feature module has its own onboarding guide:

- [Card Payments](docs/CardPayments)
- [PayPal Web Payments](docs/PayPalWebPayments)
- [PayPal Native Payments](docs/PayPalNativePayments)
- [Payment Buttons](docs/PaymentButtons)

### 4. Authorize or Capture payment

After the feature client delivers a success result, submit your `ORDER_ID` for authorization or capture.

Call the [`/authorize`](https://developer.paypal.com/docs/api/orders/v2/#orders_authorize) endpoint of the Orders V2 API</a> to place the money on hold:

**cURL request**

```
curl --location --request POST 'https://api-m.sandbox.paypal.com/v2/checkout/orders/ORDER_ID/authorize' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer ACCESS_TOKEN' \
--data-raw ''
```

Call the [`/capture`](https://developer.paypal.com/docs/api/orders/v2/#orders_capture) endpoint of the Orders V2 API</a> to capture the money immediately:

**cURL request**

```
curl --location --request POST 'https://api-m.sandbox.paypal.com/v2/checkout/orders/ORDER_ID/capture' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer ACCESS_TOKEN' \
--data-raw ''
```

## Release Process

This SDK follows [Semantic Versioning](https://semver.org/). This SDK is published to Maven Central. The release process is automated via GitHub Actions.

## Testing

This repository includes unit tests, integration tests, and end-to-end tests.

// TODO: Add sections with commands for running each type of tests 

## Static Analysis Tools

### Detekt
This project uses [Detekt](https://github.com/detekt/detekt) for Kotlin code analysis. To run the code analysis:
```
./gradlew detekt
```
This will output a list of violations, if any.

Running the gradle task with the `-PdetektAutoCorrect` parameter, will automatically correct formatting issues:
```
./gradlew detekt -PdetektAutoCorrect
```

Detekt rules are configured in `detekt/detekt-config.yml`.

### Jacoco

This project uses [Jacoco](https://www.jacoco.org/jacoco/) for gathering code coverage metrics. We leverage the 3rd-party [jacoco-android-gradle-plugin](https://github.com/arturdm/jacoco-android-gradle-plugin) to integrate Jacoco into our project.

To run code coverage analysis:

```
./gradlew jacocoTestReport
```

The results are then generated in each module's respective `build/jacoco` folder (e.g. `CardPayments/build/jacoco`).
