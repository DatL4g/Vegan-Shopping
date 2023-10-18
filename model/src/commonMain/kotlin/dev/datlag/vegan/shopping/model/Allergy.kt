package dev.datlag.vegan.shopping.model

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
sealed class Allergy : Parcelable {

    @Parcelize
    data object COW_MILK : Allergy(), Parcelable

    @Parcelize
    data object EGG : Allergy(), Parcelable

    @Parcelize
    data object TREE_NUT : Allergy(), Parcelable

    @Parcelize
    data object PEANUT : Allergy(), Parcelable

    @Parcelize
    data object SHELLFISH : Allergy(), Parcelable

    @Parcelize
    data object WHEAT : Allergy(), Parcelable

    @Parcelize
    data object SOY : Allergy(), Parcelable

    @Parcelize
    data object FISH : Allergy(), Parcelable
}