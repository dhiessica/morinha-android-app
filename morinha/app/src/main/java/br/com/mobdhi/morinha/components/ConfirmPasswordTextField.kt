package br.com.mobdhi.morinha.components

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
import androidx.compose.ui.unit.sp
import br.com.mobdhi.morinha.R
import br.com.mobdhi.morinha.ui.theme.MorinhaTheme

@Composable
fun ConfirmPasswordTextField(
    password: String,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    hasError: Boolean = false,
    label: String = stringResource(R.string.confirm_password)
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    val matchError = remember { mutableStateOf(false) }


    Column {
        TextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
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
            isError = hasError || matchError.value
        )
        if (confirmPassword != password) {
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            Text(
                text = stringResource(R.string.error_password_no_match),
                color = colorResource(R.color.soft_red),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.semantics { contentDescription = "ConfirmPasswordMessage" },
            )
            matchError.value = true
        } else {
            matchError.value = false
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConfirmPasswordTextFieldPreview() {
    MorinhaTheme {
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_large))
        ) {
            PasswordTextField(
                password = password,
                onPasswordChange = { password = it }
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            ConfirmPasswordTextField(
                password = password,
                confirmPassword = confirmPassword,
                onConfirmPasswordChange = { confirmPassword = it }
            )
        }
    }
}