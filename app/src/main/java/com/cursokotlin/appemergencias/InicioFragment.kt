package com.cursokotlin.appemergencias

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class InicioFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_inicio, container, false)

        //agregar mas dependiendo de la cantidad de cards creadas, estas deben tener un id, y el segundo textview tambien debe tener id
        setupCardLongPress(view.findViewById(R.id.card_policia), view.findViewById(R.id.txt_numero_policia))
        setupCardLongPress(view.findViewById(R.id.card_bombero), view.findViewById(R.id.txt_numero_bombero))
        setupCardLongPress(view.findViewById(R.id.card_cruzroja), view.findViewById(R.id.txt_numero_cruzroja))

        return view
    }

    private fun setupCardLongPress(cardView: CardView, textView: TextView) {
        cardView.setOnClickListener {
            Toast.makeText(requireContext(), "Manten presionado por 1 segundo para copiar el número", Toast.LENGTH_SHORT).show()
        }

        cardView.setOnLongClickListener {
            Handler(Looper.getMainLooper()).postDelayed({
                val number = textView.text.toString()
                copyToClipboard(number)
            }, 1000) // 1 segundo
            true
        }
    }

    private fun copyToClipboard(text: String) {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Emergency Number", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(requireContext(), "Número de emergencia copiado: $text", Toast.LENGTH_SHORT).show()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InicioFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
