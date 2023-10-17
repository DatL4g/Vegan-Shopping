package dev.datlag.vegan.shopping.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher

actual val Dispatchers.DeviceMain: MainCoroutineDispatcher
    get() = Main

actual val Dispatchers.DeviceIO: CoroutineDispatcher
    get() = IO

actual val Dispatchers.DeviceDefault: CoroutineDispatcher
    get() = Default