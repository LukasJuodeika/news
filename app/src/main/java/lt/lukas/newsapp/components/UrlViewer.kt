package lt.lukas.newsapp.components

import android.content.Context

interface UrlViewer {

    /**
     * Opens url on Chrome browser. If chrome browser is not available, uses default.
     * @param context used for calling android specific actions
     * @param url url to view
     */
    fun viewUrl(context: Context, url: String)

}
