package dev.datlag.vegan.shopping.ui.custom

import androidx.compose.runtime.Composable
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import dev.datlag.vegan.shopping.SharedRes
import dev.datlag.vegan.shopping.ui.LocalDarkMode
import dev.icerock.moko.resources.ImageResource


@Parcelize
sealed class Avatar : Parcelable {

    @Parcelize
    sealed class Female : Avatar(), Parcelable {

        @Parcelize
        data object One : Female(), Parcelable {
            override fun getResource(darkMode: Boolean): ImageResource {
                return if (darkMode) {
                    SharedRes.images.female_one_dark
                } else {
                    SharedRes.images.female_one_light
                }
            }
        }

        @Parcelize
        data object Two : Female(), Parcelable {
            override fun getResource(darkMode: Boolean): ImageResource {
                return if (darkMode) {
                    SharedRes.images.female_two_dark
                } else {
                    SharedRes.images.female_two_light
                }
            }
        }
    }

    @Parcelize
    sealed class Male : Avatar(), Parcelable {

        @Parcelize
        data object One : Male(), Parcelable {
            override fun getResource(darkMode: Boolean): ImageResource {
                return if (darkMode) {
                    SharedRes.images.male_one_dark
                } else {
                    SharedRes.images.male_one_light
                }
            }
        }

        @Parcelize
        data object Two : Male(), Parcelable {
            override fun getResource(darkMode: Boolean): ImageResource {
                return if (darkMode) {
                    SharedRes.images.male_two_dark
                } else {
                    SharedRes.images.male_two_light
                }
            }
        }
    }

    @Composable
    fun getResource() = getResource(LocalDarkMode.current)
    abstract fun getResource(darkMode: Boolean): ImageResource

    companion object {
        fun random(): Avatar {
            val isFemale = (0..1).random() == 0
            val optionOne = (0..1).random() == 0

            return if (isFemale) {
                if (optionOne) {
                    Female.One
                } else {
                    Female.Two
                }
            } else {
                if (optionOne) {
                    Male.One
                } else {
                    Male.Two
                }
            }
        }
    }
}