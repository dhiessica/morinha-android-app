package br.com.mobdhi.morinha.utils

import com.google.firebase.Timestamp
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

fun convertMillisToTimestamp(selectedDateMillis: Long): Timestamp {
    val date = Date(selectedDateMillis)
    return Timestamp(date)
}

fun convertTimestampToLocalDate(timestamp: Timestamp): LocalDate {
    val date: Date = timestamp.toDate()
    return date.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}

fun convertTimestampToFormattedString(timestamp: Timestamp): String {
    val date: Date = timestamp.toDate()
    return convertMillisToDate(date.time)
}

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



fun String.isValidDouble(): Boolean {
    return try {
        this.toDouble()
        true
    } catch (e: NumberFormatException) {
        false
    }
}

