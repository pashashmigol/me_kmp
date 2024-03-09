package screens.home

import MeMultiplatform.composeApp.BuildConfig
import Screen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.push
import com.me.resources.library.MR
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun HomeScreen(
    navController: StackNavigation<Screen>? = null
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.BottomEnd
    ) {
        Image(
            painterResource(MR.images.tram),
            contentDescription = "",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.15f
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 16.dp, bottom = 32.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            IconButton(
                onClick = { navController?.push(Screen.Months()) },
                modifier = Modifier
                    .then(Modifier.size(80.dp))
                    .border(3.dp, Color.White, shape = CircleShape)
                    .semantics { testTag = "months" }
            ) {
                Text(
                    text = stringResource(MR.strings.months_k),
                    color = Color.White,
                    fontSize = TextUnit(18.0f, TextUnitType.Sp),
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            IconButton(
                onClick = { navController?.push(Screen.Weeks()) },
                modifier = Modifier
                    .then(Modifier.size(80.dp))
                    .border(3.dp, Color.White, shape = CircleShape)
                    .semantics { testTag = "weeks" }
            ) {
                Text(
                    text = stringResource(MR.strings.weeks_k),
                    color = Color.White,
                    fontSize = TextUnit(18.0f, TextUnitType.Sp),
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            IconButton(
                onClick = { navController?.push(Screen.Days()) },
                modifier = Modifier
                    .then(Modifier.size(80.dp))
                    .border(3.dp, Color.White, shape = CircleShape)
                    .semantics { testTag = "days" }
            ) {
                Text(
                    text = stringResource(MR.strings.days_k),
                    color = Color.White,
                    fontSize = TextUnit(18.0f, TextUnitType.Sp),
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            IconButton(
                onClick = { navController?.push(Screen.Today) },
                modifier = Modifier
                    .then(Modifier.size(100.dp))
                    .semantics { testTag = "new record" }
                    .border(3.dp, Color.White, shape = CircleShape),
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "content description",
                    tint = Color.White,
                    modifier = Modifier.size(60.dp)
                )
            }
        }
        Text(
            text = "v${BuildConfig.VERSION_NAME}(${BuildConfig.VERSION_CODE})",
            color = Color.White,
            fontSize = 8.sp,
        )
    }
}
