package com.example.cabifymobilechallenge.presentation.composable

import android.icu.util.Currency
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cabifymobilechallenge.R
import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.presentation.formatAsCurrency
import com.example.cabifymobilechallenge.presentation.theme.CabifyMobileChallengeTheme


@Composable
fun ProductListItem(
    product: Product,
    currency: Currency,
    onAdd: () -> Unit,
    onRemove: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            Card(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1F),
                elevation = 5.dp
            ) {
                Icon(
                    modifier = Modifier.padding(12.dp),
                    painter = painterResource(id = getProductResource(product)),
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSurface
                )
            }

            Spacer(modifier = Modifier.width(12.dp))
        }

        Text(
            modifier = Modifier.weight(1F),
            text = product.name,
            style = MaterialTheme.typography.body1,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = product.price.formatAsCurrency(currency),
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.width(18.dp))

        val removeEnabled = product.count > 0

        Row(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.3f)
                )
                .padding(horizontal = 6.dp, vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            var iconModifier = Modifier
                .size(24.dp)
                .clip(CircleShape)

            if (removeEnabled) iconModifier = iconModifier.clickable(onClick = { onRemove() })

            Icon(
                modifier = iconModifier,
                painter = painterResource(id = R.drawable.ic_remove),
                contentDescription = stringResource(R.string.cd_delete_item),
                tint = MaterialTheme.colors.onBackground.copy(alpha = if (removeEnabled) 1f else 0.2f)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = product.count.toString(),
                style = MaterialTheme.typography.body1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.width(12.dp))

            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .clickable(onClick = { onAdd() }),
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = stringResource(R.string.cd_add_item),
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProductListItemPrev() {
    CabifyMobileChallengeTheme {
        ProductListItem(
            product = Product.Voucher(
                name = "Voucher",
                price = 15.75
            ).apply { count = 10 },
            currency = Currency.getInstance("EUR"),
            onAdd = {},
            onRemove = {}
        )
    }
}