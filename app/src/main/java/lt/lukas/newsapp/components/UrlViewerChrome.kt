package lt.lukas.newsapp.components

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import lt.lukas.newsapp.Constants

/**
 * UrlViewer that prefers viewing content on chrome browser.
 * If chrome is not available, default system browser is used.
 */
class UrlViewerChrome : UrlViewer {

    override fun viewUrl(context: Context, url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage(Constants.GOOGLE_CHROME_PACKAGE)
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            context.startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }
}
