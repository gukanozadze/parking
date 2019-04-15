package com.example.parking

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges


var firebaseNumber = ""
/**
 * Implementation of App Widget functionality.
 */
class widgetText : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        Log.d("GUKADB", "UPDATED3")

        // FIREBASE
        val db = FirebaseFirestore.getInstance() // Firebase Refetence
        val parking = db.collection("parking") // Collection
        val location1 = parking.document("location1") // Reference to Document
        location1.addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, e ->
            if (snapshot != null) {
                val data = snapshot.data

                if (data != null) firebaseNumber = data["number"].toString()

                for (appWidgetId in appWidgetIds) {

                    updateAppWidget(context, appWidgetManager, appWidgetId)
                }
            }
        }


        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {

            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {

    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {

        internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {

            Log.d("GUKADB", "UPDATED")
            val widgetText = firebaseNumber
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.widget_text)
            views.setTextViewText(R.id.appwidget_text, widgetText)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)

        }
    }
}

