package model

import dev.icerock.moko.resources.desc.StringDesc

actual fun translatedString(stringDesc: StringDesc): String = stringDesc.localized()
