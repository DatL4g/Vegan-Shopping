package dev.datlag.vegan.shopping.ui.navigation

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
sealed class ScreenConfig : Parcelable {

    @Parcelize
    data object Welcome : ScreenConfig(), Parcelable

    @Parcelize
    sealed class Profile : ScreenConfig(), Parcelable {

        @Parcelize
        data object Create : Profile(), Parcelable
    }

    @Parcelize
    data object Scan : ScreenConfig(), Parcelable
}