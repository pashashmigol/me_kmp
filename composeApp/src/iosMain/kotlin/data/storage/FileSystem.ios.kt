package data.storage

import okio.FileSystem
import okio.FileSystem.Companion.SYSTEM

actual fun fileSystem(): FileSystem = SYSTEM
