package screens.components.cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CardButton(
    modifier: Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .size(48.dp)
            .padding(2.dp),
        shape = CircleShape,
        border = BorderStroke(2.dp, Color.White),
        contentPadding = PaddingValues(2.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.Transparent,
            contentColor = Color.White
        ),
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = imageVector.name,
            modifier = modifier.fillMaxSize(),
            tint = Color.White
        )
    }
}

//@Preview
//@Composable
//private fun CardButtonPreview() {
//    Row {
//        CardButton(Modifier, Icons.Default.Close) {}
//        CardButton(Modifier, Icons.Default.Check) {}
//    }
//}
