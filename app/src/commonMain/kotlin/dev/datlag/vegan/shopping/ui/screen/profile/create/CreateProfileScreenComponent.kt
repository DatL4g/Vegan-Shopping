package dev.datlag.vegan.shopping.ui.screen.profile.create

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import com.arkivanov.decompose.ComponentContext
import dev.datlag.vegan.shopping.common.ioDispatcher
import dev.datlag.vegan.shopping.common.launchIO
import dev.datlag.vegan.shopping.model.FoodType
import dev.datlag.vegan.shopping.ui.custom.Avatar
import kotlinx.coroutines.flow.*
import org.kodein.di.DI

class CreateProfileScreenComponent(
    componentContext: ComponentContext,
    override val di: DI
) : CreateProfileComponent, ComponentContext by componentContext {

    private val _appAvatar = MutableStateFlow(Avatar.random())
    private val _otherAvatar = MutableStateFlow<ImageBitmap?>(null)
    private val _useAppAvatar = MutableStateFlow<Boolean>(true)

    private val _firstName = MutableStateFlow(String())
    private val _lastName = MutableStateFlow(String())

    private val _foodType = MutableStateFlow<FoodType?>(null)

    override val appAvatar: StateFlow<Avatar> = _appAvatar
    override val otherAvatar: StateFlow<ImageBitmap?> = _otherAvatar
    override val useAppAvatar: StateFlow<Boolean> = _useAppAvatar

    override val firstName: StateFlow<String> = _firstName
    override val lastName: StateFlow<String> = _lastName

    override val foodType: StateFlow<FoodType?> = _foodType

    override val canCreate: Flow<Boolean> = combine(firstName, foodType) { name, type ->
        name.isNotBlank() && type != null
    }.flowOn(ioDispatcher())

    @Composable
    override fun render() {
        CreateProfileScreen(this)
    }

    override fun loadAvatar(app: Avatar) = launchIO {
        _appAvatar.emit(app)
        _useAppAvatar.emit(true)
    }

    override fun loadAvatar(other: ImageBitmap?) = launchIO {
        _otherAvatar.emit(other)
        _useAppAvatar.emit(other == null)
    }

    override fun changeFirstName(new: String) = launchIO {
        _firstName.emit(new)
    }

    override fun changeLastName(new: String) = launchIO {
        _lastName.emit(new)
    }

    override fun selectFoodType(new: FoodType) = launchIO {
        _foodType.emit(new)
    }

    override fun create() {

    }
}