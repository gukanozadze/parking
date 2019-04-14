package com.example.parking

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
var number = ""
var number_2 = ""


class MainActivity : AppCompatActivity() {
    private val TAG = "GUKADB"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val db = FirebaseFirestore.getInstance() // Firebase Refetence
        val parking = db.collection("parking") // Collection

        val location1 = parking.document("location1") // Reference to Document
        val location2 = parking.document("location2") // Reference to Document


        // Listen for location 1.
        location1.addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, e ->
            if (snapshot != null) {
                val data = snapshot.data
                if (data != null){
                    number = data["number"].toString()
                    text_number.text = number
                }
            }
        }

        // Listen for location 2.
        location2.addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, e ->
            if (snapshot != null) {
                val data = snapshot.data
                if (data != null){
                    number_2 = data["number"].toString()
                    text_number_2.text = number_2
                }
            }
        }


        val intent = Intent(this, widgetText::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
        // since it seems the onUpdate() is only fired on that:
        val ids = AppWidgetManager.getInstance(application)
            .getAppWidgetIds(ComponentName(getApplication(), widgetText::class.java!!))
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        sendBroadcast(intent)

    }
}
