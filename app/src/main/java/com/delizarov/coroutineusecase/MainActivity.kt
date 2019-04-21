package com.delizarov.coroutineusecase

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val sunsigns: Array<String> by lazy {
        resources.getStringArray(R.array.sunsigns)
    }

    private val sunsignSelect: Spinner by lazy { findViewById<Spinner>(R.id.sunsign) }
    private val forecastView: TextView by lazy { findViewById<TextView>(R.id.forecast_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sunsignSelect.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        GetHoroscopeUseCase(sunsigns[position]).executeIoCoroutine { dto ->
            forecastView.text = dto.horoscope
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) { }
}
