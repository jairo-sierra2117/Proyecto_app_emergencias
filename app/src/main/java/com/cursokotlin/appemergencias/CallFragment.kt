package com.cursokotlin.appemergencias

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlin.math.pow
import kotlin.math.sqrt

class CallFragment : Fragment() {

    private var handler: Handler? = null
    private var isPressed = false
    private var countdownTime = 3
    private var countdownToast: Toast? = null

    private lateinit var emergencyButton: ImageView
    private lateinit var normalDrawable: Drawable
    private lateinit var pressedDrawable: Drawable

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_call, container, false)

        emergencyButton = view.findViewById(R.id.emergency_button)
        normalDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.btn_red)!!
        pressedDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.btn_green)!!

        handler = Handler(Looper.getMainLooper())

        emergencyButton.setOnTouchListener { v, event ->
            val centerX = v.width / 2f
            val centerY = v.height / 2f
            val touchRadius = v.width / 2f * 1.5f // Ajusta este valor para ampliar el área de efecto

            if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_UP) {
                val distance = sqrt((event.x - centerX).pow(2) + (event.y - centerY).pow(2))

                if (distance > touchRadius) {
                    // If the touch is outside the adjusted circle, do not process the touch event
                    return@setOnTouchListener false
                }
            }

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    isPressed = true
                    countdownTime = 3
                    emergencyButton.setImageDrawable(pressedDrawable)
                    startCountdown()
                    true
                }
                MotionEvent.ACTION_UP -> {
                    if (isPressed) {
                        showToast("Mantenga presionado el botón durante 3 segundos para llamar")
                    }
                    isPressed = false
                    emergencyButton.setImageDrawable(normalDrawable)
                    handler?.removeCallbacksAndMessages(null) // Stop the countdown
                    countdownToast?.cancel() // Cancel the countdown toast
                    v.performClick() // Ensure performClick is called for accessibility
                    true
                }
                MotionEvent.ACTION_CANCEL -> {
                    isPressed = false
                    emergencyButton.setImageDrawable(normalDrawable)
                    handler?.removeCallbacksAndMessages(null) // Stop the countdown
                    countdownToast?.cancel() // Cancel the countdown toast
                    true
                }
                else -> false
            }
        }

        return view
    }

    private fun startCountdown() {
        handler?.post(object : Runnable {
            override fun run() {
                if (isPressed) {
                    if (countdownTime > 0) {
                        showToast("Llamada en $countdownTime segundos...")
                        countdownTime--
                        handler?.postDelayed(this, 1000) // Repeat every second
                    } else {
                        showToast("Iniciando llamada de emergencia...")
                        if (activity is MainActivity) {
                            val mainActivity = activity as MainActivity
                            if (mainActivity.checkCallPhonePermission()) {
                                mainActivity.makeEmergencyCall()
                            } else {
                                mainActivity.requestCallPhonePermission()
                            }
                        }
                    }
                }
            }
        })
    }

    private fun showToast(message: String) {
        activity?.runOnUiThread {
            countdownToast?.cancel() // Cancel the previous toast if it's still visible
            countdownToast = Toast.makeText(activity, message, Toast.LENGTH_SHORT)
            countdownToast?.show()
        }
    }
}
