package com.beni.myapplication

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate : TextView? = null
    private var tvMinute : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDate : Button = findViewById(R.id.btnDate)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvMinute = findViewById(R.id.tvMinute)

        btnDate.setOnClickListener{
            clickDate()
        }
    }

    private fun clickDate(){
        var myCal = Calendar.getInstance()
        var year = myCal.get(Calendar.YEAR)
        var month = myCal.get(Calendar.MONTH)
        var day = myCal.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener{_, selectYear, selectMonth, selectDay ->
                Toast.makeText(this, "Year was $selectYear, Month was ${selectMonth+1}, Day was $selectDay",
                    Toast.LENGTH_LONG).show()
                val selectedDate = "$selectDay/${selectMonth+1}/$selectYear"
                tvSelectedDate?.setText(selectedDate)

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                val theDate = sdf.parse(selectedDate)

                theDate?.let {
                    var selectedDateInMinute = theDate.time/1000
                    selectedDateInMinute /= 60

                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                    currentDate?.let {
                        var currentDateInMinute = currentDate.time/1000 //to second
                        currentDateInMinute /= 60 //to minute

                        val diffMinutes = currentDateInMinute - selectedDateInMinute

                        tvMinute?.text = diffMinutes.toString()
                    }
                }
            },
            year,
            month,
            day
        )
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400 //yesteday
        dpd.show()

    }
}