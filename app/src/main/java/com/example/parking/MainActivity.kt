package com.example.parking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val TAG = "GUKADB"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val db = FirebaseFirestore.getInstance() // Firebase Refetence
        val parking = db.collection("parking") // Collection

        val location1 = parking.document("location1") // Reference to Document
        val location2 = parking.document("location2") // Reference to Document

//        location1.get().addOnSuccessListener { document ->
//            if (document != null) {
//                val data = document.data
//                val number = data?.get("number")
//
//                text_number.text = number.toString()
//
//                Log.d(TAG, "DocumentSnapshot data: $number")
//
//            } else {
//                Log.d(TAG, "No such document")
//            }
//        }
//        .addOnFailureListener { exception ->
//            Log.d(TAG, "get failed with ", exception)
//        }
//
//        location2.get().addOnSuccessListener { document ->
//            if (document != null) {
//                val data = document.data
//                val number = data?.get("number")
//
//                text_number_2.text = number.toString()
//
//                Log.d(TAG, "DocumentSnapshot data: $number")
//
//            } else {
//                Log.d(TAG, "No such document")
//            }
//        }.addOnFailureListener { exception ->
//                Log.d(TAG, "get failed with ", exception)
//        }


        // Listen for location 1.
        location1.addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, e ->
            if (snapshot != null) {
                val data = snapshot.data
                if (data != null){
                    val number = data["number"]
                    text_number.text = number.toString()

                }
            }
        }

        // Listen for location 2.
        location2.addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, e ->
            if (snapshot != null) {
                val data = snapshot.data
                if (data != null){
                    val number = data["number"]
                    text_number_2.text = number.toString()
                }
            }
        }

    }
}
