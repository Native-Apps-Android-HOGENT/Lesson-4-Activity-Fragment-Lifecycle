package com.example.android.dessertpusher.domain

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
class DessertShop : Parcelable{

    /**
     * It would be more correct if these couldn't be set publicly, but we need this in order to
     * set the current state after a config change.
     */
    var revenue = 0
    var dessertsSold = 0

    @IgnoredOnParcel
    private val dessertRepository = DessertRepository()

    var currentDessert = dessertRepository.desserts.first()
        private set

    fun onDessertSold() {
        revenue += currentDessert.price
        dessertsSold++
        determineCurrentDessert()
    }

    private fun determineCurrentDessert() {
        var newDessert = dessertRepository.desserts.first()
        for (dessert in dessertRepository.desserts) {
            if (dessertsSold >= dessert.startProductionAmount) {
                newDessert = dessert
            }
            // The list of desserts is sorted by startProductionAmount. As you sell more desserts,
            // you'll start producing more expensive desserts as determined by startProductionAmount
            // We know to break as soon as we see a dessert who's "startProductionAmount" is greater
            // than the amount sold.
            else break
        }

        // If the new dessert is actually different than the current dessert, update the image
        if (newDessert != currentDessert) {
            currentDessert = newDessert
        }
    }

}