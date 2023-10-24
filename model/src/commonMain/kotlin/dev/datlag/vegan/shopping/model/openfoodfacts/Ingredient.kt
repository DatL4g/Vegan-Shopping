package dev.datlag.vegan.shopping.model.openfoodfacts

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import dev.datlag.vegan.shopping.model.common.setFrom
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Parcelize
@Serializable
data class Ingredient(
    @SerialName("id") val id: String,
    @SerialName("vegan") val veganString: String? = null,
    @SerialName("vegetarian") val vegetarianString: String? = null,
    @SerialName("text") val text: String? = id,
    @SerialName("ingredients") val ingredients: List<Ingredient> = emptyList()
) : Parcelable {

    @Transient
    val type: Type = Type.mapFrom(veganString, vegetarianString, ingredients.map { it.type })

    @Transient
    val allIngredients: Set<Ingredient> = run {
        setFrom(listOf(this), ingredients.flatMap { it.allIngredients })
    }

    @Parcelize
    sealed interface Type : Parcelable {

        @Parcelize
        data class VEGAN(val maybe: Boolean, val vegetarianType: VEGETARIAN) : Type, Parcelable

        @Parcelize
        data class VEGETARIAN(val maybe: Boolean) : Type, Parcelable

        @Parcelize
        data object OMNIVORE : Type, Parcelable

        @Parcelize
        data object UNKNOWN : Type, Parcelable

        val isVeganOrVegetarian: Boolean
            get() = this is VEGAN || this is VEGETARIAN

        val isOmnivore: Boolean
            get() = this is OMNIVORE

        val isUnknown: Boolean
            get() = this is UNKNOWN

        val grouping: GROUPING
            get() = when (this) {
                is VEGAN -> GROUPING.VEGAN
                is VEGETARIAN -> GROUPING.VEGETARIAN
                is OMNIVORE -> GROUPING.OMNIVORE
                is UNKNOWN -> GROUPING.UNKNOWN
            }

        @Parcelize
        sealed interface GROUPING : Parcelable {

            @Parcelize
            data object VEGAN : GROUPING, Parcelable

            @Parcelize
            data object VEGETARIAN : GROUPING, Parcelable

            @Parcelize
            data object OMNIVORE : GROUPING, Parcelable

            @Parcelize
            data object UNKNOWN : GROUPING, Parcelable

            fun toDefaultType(): Type = when (this) {
                is VEGAN -> VEGAN(maybe = false, vegetarianType = VEGETARIAN(maybe = false))
                is VEGETARIAN -> VEGETARIAN(maybe = false)
                is OMNIVORE -> Type.OMNIVORE
                is UNKNOWN -> Type.UNKNOWN
            }
        }

        companion object {
            fun mapFrom(vegan: String?, vegetarian: String?, other: Collection<Type>): Type {
                fun isYes(label: String?): Boolean {
                    return label.equals("yes", true) || label.toBoolean()
                }

                fun isMaybe(label: String?): Boolean {
                    return label.equals("maybe", true)
                }

                fun isNo(label: String?): Boolean {
                    return label.equals("no", true) || !label.toBoolean()
                }

                return if (vegan.isNullOrBlank() && vegetarian.isNullOrBlank()) {
                    getWorstCaseType(other) ?: UNKNOWN
                } else {
                    if (isYes(vegan)) {
                        VEGAN(maybe = false, vegetarianType = getWorstVegetarianType(true, other))
                    } else if (isMaybe(vegan)) {
                        VEGAN(maybe = true, vegetarianType = getWorstVegetarianType(false, other))
                    } else if (isYes(vegetarian)) {
                        VEGETARIAN(maybe = false)
                    } else if (isMaybe(vegetarian)) {
                        VEGETARIAN(maybe = true)
                    } else if (isNo(vegan) && isNo(vegetarian)) {
                        OMNIVORE
                    } else {
                        getWorstCaseType(other) ?: UNKNOWN
                    }
                }
            }

            private fun getWorstCaseType(other: Collection<Type>): Type? {
                return if (other.isEmpty()) {
                    null
                } else {
                    if (other.any { it is OMNIVORE }) {
                        OMNIVORE
                    } else if (other.any { it is UNKNOWN }) {
                        UNKNOWN
                    } else if (other.all { it is VEGAN }) {
                        getWorstVeganType(other)
                    } else if (other.all { it is VEGETARIAN }) {
                        VEGETARIAN(maybe = other.filterIsInstance<VEGETARIAN>().any { it.maybe })
                    } else {
                        null
                    }
                }
            }

            private fun getWorstVeganType(list: Collection<Type>): VEGAN {
                val veganList = list.filterIsInstance<VEGAN>()
                return if (veganList.isEmpty()) {
                    VEGAN(maybe = true, VEGETARIAN(maybe = true))
                } else {
                    val isMaybeVegan = veganList.any { it.maybe }
                    VEGAN(
                        maybe = isMaybeVegan,
                        vegetarianType = if (isMaybeVegan) {
                            getWorstVegetarianType(false, list)
                        } else {
                            VEGETARIAN(maybe = false)
                        }
                    )
                }
            }

            private fun getWorstVegetarianType(isVegan: Boolean, other: Collection<Type>): VEGETARIAN {
                return if (isVegan) {
                    VEGETARIAN(maybe = false)
                } else {
                    val vegetarianList = other.filterIsInstance<VEGETARIAN>()
                    if (vegetarianList.isEmpty()) {
                        VEGETARIAN(maybe = true)
                    } else {
                        VEGETARIAN(maybe = vegetarianList.any { it.maybe })
                    }
                }
            }
        }
    }
}