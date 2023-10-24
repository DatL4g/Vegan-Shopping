package dev.datlag.vegan.shopping.model.openfoodfacts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ingredient(
    @SerialName("vegan") val veganString: String? = null,
    @SerialName("vegetarian") val vegetarianString: String? = null,
    @SerialName("ingredients") val ingredients: List<Ingredient> = emptyList()
) {

    val type: Type = Type.mapFrom(veganString, vegetarianString, ingredients.map { it.type })

    sealed interface Type {
        data class VEGAN(val maybe: Boolean, val vegetarianType: VEGETARIAN) : Type
        data class VEGETARIAN(val maybe: Boolean) : Type
        data object OMNIVORE : Type
        data object UNKNOWN : Type

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