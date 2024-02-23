package data.storage

import platform.Foundation.*

actual fun filesFolder() = ("~/Documents/" as NSString).stringByExpandingTildeInPath
