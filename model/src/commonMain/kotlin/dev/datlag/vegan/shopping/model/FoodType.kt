package dev.datlag.vegan.shopping.model

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import dev.datlag.vegan.shopping.model.common.setFrom

@Parcelize
sealed class FoodType : Parcelable {

    @Parcelize
    data object VEGAN : FoodType(), Parcelable {

        override val allergies: Set<Allergy> = setFrom(
            super.allergies, setOf(
                Allergy.PEANUT,
                Allergy.SOY,
                Allergy.TREE_NUT,
                Allergy.WHEAT
            )
        )
    }

    @Parcelize
    data object VEGETARIAN : FoodType(), Parcelable {
        override val allergies: Set<Allergy> = setFrom(
            super.allergies, VEGAN.allergies, setOf(
                Allergy.COW_MILK,
                Allergy.EGG
            )
        )
    }

    @Parcelize
    data object OMNIVORE : FoodType(), Parcelable {
        override val allergies: Set<Allergy> = setFrom(
            super.allergies, VEGAN.allergies, VEGETARIAN.allergies, setOf(
                Allergy.FISH,
                Allergy.SHELLFISH
            )
        )
    }

    open val allergies: Set<Allergy> = emptySet()
}