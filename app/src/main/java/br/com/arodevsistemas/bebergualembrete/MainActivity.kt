package br.com.arodevsistemas.bebergualembrete

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.*
import androidx.appcompat.app.AlertDialog
import br.com.arodevsistemas.bebergualembrete.databinding.ActivityMainBinding
import br.com.arodevsistemas.bebergualembrete.core.*
import br.com.arodevsistemas.bebergualembrete.model.CalcIngestionWater
import java.util.*

class MainActivity : AppCompatActivity() {

    private val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}
    private val calcIngestionWater by lazy { CalcIngestionWater() }

    private lateinit var et_weight: EditText
    private lateinit var et_age: EditText
    private lateinit var btn_calc: Button
    private lateinit var tv_result: TextView
    private lateinit var ic_reset: ImageView
    private lateinit var btn_reminder: Button
    private lateinit var btn_alarm: Button
    private lateinit var timePickerDialog: TimePickerDialog
    private lateinit var calendar: Calendar
    private var hour = 0
    private var minute = 0
    private lateinit var tv_time : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        listenerComponents()

        btn_calc.setOnClickListener {
            when {
                et_weight.text.toString().isEmpty() -> {
                    Toast.makeText(this, getString(R.string.toast_error_weight), Toast.LENGTH_SHORT).show()
                }
                et_age.text.toString().isEmpty() -> {
                    Toast.makeText(this, getString(R.string.toast_error_weight), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val weight = et_weight.text.toString().toDouble()
                    val age = et_age.text.toString().toInt()
                    it.hideSoftKeyboard()
                    tv_result.text = calcIngestionWater.CalcIngestionWater(weight = weight, age = age)

                }
            }
        }

        ic_reset.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle(getString(R.string.dialog_title))
                .setMessage(getString(R.string.dialog_message))
                .setPositiveButton(getString(R.string.dialog_positive_button)) { _, _ ->
                    et_weight.setText("")
                    et_age.setText("")
                    tv_result.text = ""
                }
                .setNegativeButton(getString(R.string.dialog_negative_button)) { _, _ -> }

            val dialog = alertDialog.create()
            dialog.show()
        }

        btn_reminder.setOnClickListener {
            calendar = Calendar.getInstance()
            hour = calendar.get(Calendar.HOUR_OF_DAY)
            minute = calendar.get(Calendar.MINUTE)

            timePickerDialog = TimePickerDialog(this, {
                timePicker: TimePicker, hourOfDay: Int, minutes: Int ->
                tv_time.text = "${String.format("%02d", hourOfDay)}:${String.format("%02d", minutes)}"

                hour = String.format("%02d", hourOfDay).toInt()
                minute = String.format("%02d", minutes).toInt()
            }, hour, minute, true)
            timePickerDialog.show()
        }

        btn_alarm.setOnClickListener {
            if (!hour.toString().isEmpty() && !minute.toString().isEmpty()){
                val intent = Intent(AlarmClock.ACTION_SET_ALARM)
                intent.putExtra(AlarmClock.EXTRA_HOUR, hour)
                intent.putExtra(AlarmClock.EXTRA_MINUTES, minute)
                intent.putExtra(AlarmClock.EXTRA_MESSAGE, getString(R.string.alarm_message))
                startActivity(intent)

                if (intent.resolveActivity(packageManager) != null){
                    startActivity(intent)
                }
            }
        }
    }

    private fun listenerComponents(){
        et_weight = binding.tvWeight
        et_age = binding.tvAge
        btn_calc = binding.btnCalc
        tv_result = binding.tvResult
        ic_reset = binding.icReset
        btn_reminder = binding.btnReminder
        btn_alarm = binding.btnAlarm
        tv_time = binding.tvTime
    }
}