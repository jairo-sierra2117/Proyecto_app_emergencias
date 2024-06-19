cambios en el navbar, el la clase call, la clase directorio(inicio).

hay que agregar mas dependiendo de la cantidad de cards creadas, estas deben tener un id, y el segundo textview tambien debe tener id.


En la clase fragment_inicio.xml
se debe agregar cada id a cada card, seguido esto se agrega el id al segundo textView 
  id para card
    <androidx.cardview.widget.CardView
          android:id="@+id/card_cruzroja"
          .
          ..//demas code

                    
   id para texView
      <TextView
          android:id="@+id/txt_numero_cruzroja" 
          android:layout_width="wrap_content"
          android:layout_height="wrap_content"
          android:text="911"
          android:textSize="18sp"
          android:textStyle="bold"
          android:textAlignment="center" />
          .
          ../demas code
          <!-- el campo de text DEBE MODIFICARSE CON EL NUMERO REAL -->
En la clase InicioFragment.kt
buscar la linea
  setupCardLongPress(view.findViewById(R.id.card_cruzroja), view.findViewById(R.id.txt_numero_cruzroja))

esta se debe agregar para cada card

