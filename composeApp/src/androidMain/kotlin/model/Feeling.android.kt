package model

import com.me.multiplatform.MeApp.Companion.appContext
import dev.icerock.moko.resources.desc.StringDesc

actual fun translatedString(stringDesc: StringDesc): String =
    stringDesc.toString(context = appContext)