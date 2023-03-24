package kosenda.makecolor.view.component.card

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kosenda.makecolor.R
import kosenda.makecolor.model.util.convertDisplayStringListFromCategories
import kosenda.makecolor.model.util.getNameIfNoAlias
import kosenda.makecolor.view.code.ColorIndex
import kosenda.makecolor.view.navigation.SelectColorParam
import kosenda.makecolor.view.state.SplitUiState

@Composable
fun SpinnerAndColorCard(
    colorIndex: ColorIndex,
    uiState: SplitUiState,
    updateSelectCategory: (Int, ColorIndex) -> Unit,
    onClickSelectColor: (SelectColorParam) -> Unit,
) {
    if (colorIndex.num <= uiState.selectSplitColorNum.value) {
        SpinnerCard(
            modifier = Modifier.padding(top = 8.dp),
            selectedText = when (colorIndex) {
                ColorIndex.FIRST -> uiState.selectCategory1.getNameIfNoAlias()
                ColorIndex.SECOND -> uiState.selectCategory2.getNameIfNoAlias()
                ColorIndex.THIRD -> uiState.selectCategory3.getNameIfNoAlias()
                ColorIndex.FOURTH -> uiState.selectCategory4.getNameIfNoAlias()
            },
            categoryName = when (colorIndex) {
                ColorIndex.FIRST -> stringResource(id = R.string.select_1)
                ColorIndex.SECOND -> stringResource(id = R.string.select_2)
                ColorIndex.THIRD -> stringResource(id = R.string.select_3)
                ColorIndex.FOURTH -> stringResource(id = R.string.select_4)
            },
            onSelectedChange = { categoryIndex -> updateSelectCategory(categoryIndex, colorIndex) },
            displayItemList = convertDisplayStringListFromCategories(
                categories = uiState.categories,
            ).toTypedArray(),
        )

        HexAndDisplayColorCard(
            hex = when (colorIndex) {
                ColorIndex.FIRST -> uiState.selectHex1
                ColorIndex.SECOND -> uiState.selectHex2
                ColorIndex.THIRD -> uiState.selectHex3
                ColorIndex.FOURTH -> uiState.selectHex4
            },
            onClick = {
                onClickSelectColor(
                    SelectColorParam(
                        category = when (colorIndex) {
                            ColorIndex.FIRST -> uiState.selectCategory1
                            ColorIndex.SECOND -> uiState.selectCategory2
                            ColorIndex.THIRD -> uiState.selectCategory3
                            ColorIndex.FOURTH -> uiState.selectCategory4
                        },
                        index = colorIndex,
                    ),
                )
            },
        )
    }
}
