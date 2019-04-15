package com.example.parking

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_booking.*
import org.jetbrains.anko.startActivity

class bookingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)


        button_book_location_1.setOnClickListener {
            startActivity<MainActivity>("starter" to "start_book_location_1")

        }

        button_book_location_2.setOnClickListener {
            startActivity<MainActivity>("starter" to "start_book_location_2")

        }


    }

}
