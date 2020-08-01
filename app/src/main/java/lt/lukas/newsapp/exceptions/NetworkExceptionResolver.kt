package lt.lukas.newsapp.exceptions

import timber.log.Timber
import java.net.UnknownHostException

class NetworkExceptionResolver(
    private val errorDisplay: ErrorDisplay
) {

    fun exception(throwable: Throwable) {
        Timber.w(throwable)
        when (throwable) {
            is UnknownHostException ->
                errorDisplay.showNoInternetError()
            else ->
                errorDisplay.showUnableToFetchData()
        }
    }
}
