package com.emami.lebenindeutschland.screen.home.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.emami.lebenindeutschland.screen.viewModel.QuizViewModel
import com.emami.lebenindeutschland.ui.theme.TextWhite
import com.emami.lebenindeutschland.ui.theme.TextWhiteDarker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownBund(viewModel: QuizViewModel,context: Context) {
    val bundList = listOf(
        "Baden-Württemberg", "Bayern",
        "Berlin", "Brandenburg", "Bremen", "Hamburg", "Hessen", "Mecklenburg-Vorpommern",
        "Niedersachsen",
        "Nordrhein-Westfalen", "Rheinland-Pfalz", "Saarland",
        "Sachsen", "Sachsen-Anhalt", "Schleswig-Holstein", "Thüringen"
    )
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

//    val context = LocalContext.current

//    LaunchedEffect(key1 = Unit) {
//        viewModel.saveSelectedBund(context, viewModel.selectedBund.value)
//    }

    Column(modifier = Modifier.padding(20.dp)) {
        OutlinedTextField(enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                }
                .clickable { expanded = !expanded },
            value = viewModel.selectedBund.value,
            onValueChange = {
                viewModel.selectedBund.value = it
            },
            label = { Text(text = "Ihr Bundesland") },
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            }, colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledLabelColor = TextWhiteDarker,
                disabledBorderColor = TextWhiteDarker,
                disabledTextColor = TextWhite,
                disabledTrailingIconColor = TextWhiteDarker
            )
        )
        DropdownMenu(expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(
                with(LocalDensity.current) { textFieldSize.width.toDp() }
            )) {
            bundList.forEach { option ->
                DropdownMenuItem(text = { Text(text = option) }, onClick = {
                    viewModel.selectedBund.value = option
                    viewModel.saveSelectedBund(context,option)
                    expanded = false
                })
            }

        }

    }


}