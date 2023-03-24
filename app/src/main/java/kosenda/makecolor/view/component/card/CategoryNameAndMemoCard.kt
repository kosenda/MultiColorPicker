package kosenda.makecolor.view.component.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kosenda.makecolor.R
import kosenda.makecolor.view.component.ContentDivider

@Composable
fun CategoryNameAndMemoCard(
    categoryName: String,
    memo: String,
) {
    Card(
        modifier = Modifier.padding(vertical = 16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp).padding(top = 16.dp),
        ) {
            Text(
                text = stringResource(id = R.string.category),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary,
            )
            Text(
                text = categoryName,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        ContentDivider()

        Column(
            modifier = Modifier.padding(horizontal = 24.dp).padding(bottom = 16.dp),
        ) {
            Text(
                text = stringResource(id = R.string.memo),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary,
            )
            Text(
                text = memo.ifEmpty { " - " },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}
