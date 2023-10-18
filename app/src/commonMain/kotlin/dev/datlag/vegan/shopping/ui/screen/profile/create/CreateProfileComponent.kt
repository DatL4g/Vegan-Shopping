package dev.datlag.vegan.shopping.ui.screen.profile.create

import androidx.compose.ui.graphics.ImageBitmap
import dev.datlag.vegan.shopping.model.FoodType
import dev.datlag.vegan.shopping.ui.custom.Avatar
import dev.datlag.vegan.shopping.ui.navigation.Component
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CreateProfileComponent : Component {
    val appAvatar: StateFlow<Avatar>
    val otherAvatar: StateFlow<ImageBitmap?>
    val useAppAvatar: StateFlow<Boolean>

    val firstName: StateFlow<String>
    val lastName: StateFlow<String>

    val foodType: StateFlow<FoodType>

    val canCreate: Flow<Boolean>

    fun loadAvatar(app: Avatar): Any?
    fun loadAvatar(other: ImageBitmap?): Any?

    fun changeFirstName(new: String): Any?
    fun changeLastName(new: String): Any?

    fun selectFoodType(new: FoodType): Any?

    fun create()
}