package lt.lukas.newsapp

import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateFormatters {

    private const val DATE_PATTERN_NEWSAPI = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    private const val DATE_PATTERN_APP = "yyyy-MMMM-dd HH:mm"

    fun formatNewsapiDate(dateString: String): String {
        val date = parseNewsapiDate(dateString) ?: return ""
        val dateFormat = SimpleDateFormat(DATE_PATTERN_APP, Locale.getDefault())
        return dateFormat.format(date)
    }

    fun parseNewsapiDate(dateString: String): Date? {
        val dateFormat = SimpleDateFormat(DATE_PATTERN_NEWSAPI, Locale.getDefault())
        return try {
            dateFormat.parse(dateString)
        } catch (e: ParseException) {
            Timber.e(e)
            null
        }
    }
}