package br.com.mobdhi.morinha.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Função para converter uma data em millissegundos [Long] para uma data formatada em [String]
 *
 * @param millis é um [Long] com a data em millissegundos.
 * @return data formatada dd/MM/yyyy [String]
 */
fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

/**
 * Função para converter uma data formatada [String] para uma data em millissegundos [Long]
 *
 * @param dateString é uma [String] com a data formatada dd/MM/yyyy.
 * @return data em millissegundos [Long]
 */
fun convertDateToMillis(dateString: String): Long? {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return try {
        val date: Date? = formatter.parse(dateString)
        date?.time
    } catch (e: ParseException) {
        null
    }
}

/**
 * Função para calcular a idade com base na data de nascimento
 *
 * @param dateString é uma [String] com a data de nascimento formatada dd/MM/yyyy.
 * @return idade [Int]
 */
fun calculateAgeFromDate(dateString: String): Int {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return try {
        val date = formatter.parse(dateString) ?: return -1

        val millis = date.time

        val birthDate = Calendar.getInstance()
        birthDate.timeInMillis = millis

        val now = Calendar.getInstance()

        var age = now.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)

        if (now.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
            age--
        }

        age
    } catch (e: ParseException) {
        -1
    }
}

