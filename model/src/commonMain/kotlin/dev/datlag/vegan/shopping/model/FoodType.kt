package dev.datlag.vegan.shopping.model

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
sealed class FoodType : Parcelable {

    @Parcelize
    data object VEGAN : FoodType(), Parcelable

    @Parcelize
    data object VEGETARIAN : FoodType(), Parcelable

    @Parcelize
    data object OMNIVORE : FoodType(), Parcelable
}