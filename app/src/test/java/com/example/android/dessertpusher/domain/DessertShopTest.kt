package com.example.android.dessertpusher.domain

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DessertShopTest {

    private lateinit var dessertShop: DessertShop
    private val dessertRepository = DessertRepository()

    @Before
    fun setUp() {
        dessertShop = DessertShop()
    }

    @Test
    fun dessertShop_constructor_startsWithNothingSold() {
        assertEquals(0, dessertShop.dessertsSold)
        assertEquals(0, dessertShop.revenue)
    }

    @Test
    fun dessertShop_constructor_startsWithFirstDessert() {
        assertNotNull(dessertShop.currentDessert)

        val firstDessert = dessertRepository.desserts.minBy { it.startProductionAmount }
        assertEquals(dessertShop.currentDessert, firstDessert)
    }

    @Test
    fun dessertShop_onDessertSold_amountSoldIncreases() {
        // Arrange
        val amountSold = dessertShop.dessertsSold

        // Act
        dessertShop.onDessertSold()

        //Assert
        assertEquals(amountSold + 1, dessertShop.dessertsSold)
    }

    @Test
    fun dessertShop_onDessertSold_revenueGoesUpByPrice() {
        // Arrange
        val price = dessertShop.currentDessert.price
        val currentRevenue = dessertShop.revenue

        // Act
        dessertShop.onDessertSold()

        //Assert
        assertEquals(currentRevenue + price, dessertShop.revenue)
    }

    @Test
    fun dessertShop_onEnoughDessertsSold_newDessert() {
        // Arrange
        val currentDessert = dessertShop.currentDessert
        val amountOfDessertsRequired = dessertRepository.desserts[1].startProductionAmount

        // Act
        repeat(amountOfDessertsRequired) {
            dessertShop.onDessertSold()
        }

        // Assert
        assertNotEquals(currentDessert, dessertShop.currentDessert)
    }
}