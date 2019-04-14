package com.example.parking

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import kotlinx.android.synthetic.main.activity_main.*


const val WIDGET_SYNC = "WIDGET_SYNC"

/**
 * Implementation of App Widget functionality.
 */
class widgetText : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {

            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    override fun onReceive(context: Context, intent: Intent?) {

        if(WIDGET_SYNC == intent?.action){
            val appWidgetId = intent.getIntExtra("appWidgetId", 0)
            updateAppWidget(context, AppWidgetManager.getInstance(context), appWidgetId)
        }
        super.onReceive(context, intent)
    }

    companion object {

        internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            var number = ""
            val db = FirebaseFirestore.getInstance() // Firebase Refetence
            val parking = db.collection("parking") // Collection
            val location1 = parking.document("location1") // Reference to Document
            location1.addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, e ->
                if (snapshot != null) {
                    val data = snapshot.data
                    if (data != null){
                        number = data["number"].toString()
                    }
                }
            }

            val intent = Intent(context, widgetText::class.java)
            intent.action = WIDGET_SYNC
            intent.putExtra("AppWidgetID", appWidgetId)
            val pendingIntent = PendingIntent.getBroadcast(context, 0,intent, 0)

            // WIDGET MANAGER
            val widgetText = number

            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.widget_text)
            views.setTextViewText(R.id.appwidget_text, widgetText)
            views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)

        }
    }
}

