package data.storage

import okio.FileSystem

actual fun fileSystem(): FileSystem = FileSystem.SYSTEM