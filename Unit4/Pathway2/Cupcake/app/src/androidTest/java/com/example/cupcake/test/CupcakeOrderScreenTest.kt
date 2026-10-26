package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.cupcake.R
import com.example.cupcake.data.DataSource
import com.example.cupcake.data.OrderUiState
import com.example.cupcake.ui.OrderSummaryScreen
import com.example.cupcake.ui.SelectOptionScreen
import com.example.cupcake.ui.StartOrderScreen
import org.junit.Rule
import org.junit.Test

class CupcakeOrderScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun selectOptionScreen_verifyContent() {
        // Given list of options
        val flavors = listOf("Vanilla", "Chocolate", "Hazelnut", "Cookie", "Mango")
        // And subtotal
        val subtotal = "$100"

        // When SelectOptionScreen is loaded
        composeTestRule.setContent {
            SelectOptionScreen(subtotal = subtotal, options = flavors)
        }

        // Then all the options are displayed on the screen.
        flavors.forEach { flavor ->
            composeTestRule.onNodeWithText(flavor).assertIsDisplayed()
        }

        // And then the subtotal is displayed correctly.
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                R.string.subtotal_price,
                subtotal
            )
        ).assertIsDisplayed()

        // And then the next button is disabled
        composeTestRule.onNodeWithStringId(R.string.next).assertIsNotEnabled()
    }

    @Test
    fun selectOptionScreen_optionSelected_NextButtonEnabled() {
        // Given list of options
        val flavors = listOf("Vanilla", "Chocolate", "Hazelnut", "Cookie", "Mango")

        // When SelectOptionScreen is loaded
        composeTestRule.setContent {
            SelectOptionScreen(subtotal = "$100", options = flavors)
        }

        // And an option is selected
        composeTestRule.onNodeWithText("Vanilla").performClick()

        // Then the next button is enabled
        composeTestRule.onNodeWithStringId(R.string.next).assertIsEnabled()
    }

    @Test
    fun startOrderScreen_verifyContent() {
        // When StartOrderScreen is loaded
        composeTestRule.setContent {
            StartOrderScreen(
                quantityOptions = DataSource.quantityOptions,
                onNextButtonClicked = {}
            )
        }

        // Then all the quantity buttons are displayed on the screen.
        DataSource.quantityOptions.forEach { (labelResourceId, _) ->
            composeTestRule.onNodeWithStringId(labelResourceId).assertIsDisplayed()
        }
    }

    @Test
    fun summaryScreen_verifyContentDisplayed() {
        // Given order state
        val orderUiState = OrderUiState(
            quantity = 6,
            flavor = "Vanilla",
            date = "Wed Jul 21",
            price = "$300.00"
        )

        // When SummaryScreen is loaded
        composeTestRule.setContent {
            OrderSummaryScreen(
                orderUiState = orderUiState,
                onCancelButtonClicked = {},
                onSendButtonClicked = { _, _ -> }
            )
        }

        // Then the order details are displayed on the screen.
        composeTestRule.onNodeWithText(
            composeTestRule.activity.resources.getQuantityString(
                R.plurals.cupcakes,
                orderUiState.quantity,
                orderUiState.quantity
            )
        ).assertIsDisplayed()
        composeTestRule.onNodeWithText(orderUiState.flavor).assertIsDisplayed()
        composeTestRule.onNodeWithText(orderUiState.date).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.subtotal_price, orderUiState.price)
        ).assertIsDisplayed()

        // And the send and cancel buttons are displayed
        composeTestRule.onNodeWithStringId(R.string.send).assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.cancel).assertIsDisplayed()
    }
}
