package br.com.mobdhi.morinha.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.mobdhi.morinha.R
import br.com.mobdhi.morinha.ui.theme.MorinhaTheme
import kotlin.math.min

@Composable
fun PasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
    label: String = stringResource(R.string.password),
    minLength: Int = 0,
    hasError: Boolean = false
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    val minLengthError = remember { mutableStateOf(false) }


    Column {
        TextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text(label) },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (isPasswordVisible) {
                    Icons.Filled.Visibility
                } else {
                    Icons.Filled.VisibilityOff
                }
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(imageVector = image, contentDescription = if (isPasswordVisible) "Ocultar senha" else "Mostrar senha")
                }
            },
            singleLine = true,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            isError = hasError || minLengthError.value
        )
        if (password.length < minLength) {
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            Text(
                text = stringResource(R.string.error_password_length_min),
                color = colorResource(R.color.soft_red),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.semantics { contentDescription = "ConfirmPasswordMessage" },
            )
            minLengthError.value = true
        } else {
            minLengthError.value = false
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PasswordTextFieldPreview() {
    MorinhaTheme {
        var password by remember { mutableStateOf("") }

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
        ) {
            PasswordTextField(
                password = password,
                onPasswordChange = { password = it }
            )
        }
    }
}