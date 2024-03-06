package com.example.simonsaysord

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private val soundResources = intArrayOf(
        R.raw.sound1,
        R.raw.sound2,
        R.raw.sound3,
        R.raw.sound4
    )

    private val simonSequence = mutableListOf<Int>()
    private var userSequenceIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mediaPlayer = MediaPlayer.create(this, soundResources[0])

        val buttons = arrayOf(
            findViewById<Button>(R.id.button1),
            findViewById<Button>(R.id.button2),
            findViewById<Button>(R.id.button3),
            findViewById<Button>(R.id.button4)
        )

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                onButtonClick(index)
            }
        }

        startSimonSays()
    }

    private fun onButtonClick(index: Int) {
        if (index == simonSequence[userSequenceIndex]) {
            playSound(index)

            val button = getButtonByIndex(index)
            button.setBackgroundResource(R.drawable.button_pressed_background)

            // Restaurar el fondo original después de un breve período
            Handler().postDelayed(
                {
                    button.setBackgroundResource(R.drawable.button_default_background)
                },
                200
            )

            // Avanzar al siguiente botón en la secuencia
            userSequenceIndex++

            // Comprobar si el usuario ha completado la secuencia
            if (userSequenceIndex == simonSequence.size) {
                // El usuario ha completado la secuencia, agregar nuevo botón a la secuencia
                userSequenceIndex = 0
                addRandomButtonToSimonSequence()
                playSimonSequence()
            }
        } else {
            // El usuario se equivocó, reiniciar el juego
            restartGame()
        }
    }


    private fun playSimonSequence() {
        val handler = Handler()
        val delayBetweenButtons = 1000L

        simonSequence.forEachIndexed { index, buttonIndex ->
            handler.postDelayed(
                {
                    val button = getButtonByIndex(buttonIndex)
                    button.setBackgroundResource(R.drawable.button_pressed_background)

                    // Restaurar el fondo original después de un breve período
                    Handler().postDelayed(
                        {
                            button.setBackgroundResource(R.drawable.button_default_background)
                        },
                        200
                    )

                    // Reproducir el sonido
                    playSound(buttonIndex)
                },
                (index + 1) * delayBetweenButtons
            )
        }
    }

    private fun restartGame() {
        simonSequence.clear()
        userSequenceIndex = 0
        addRandomButtonToSimonSequence()
        playSimonSequence()
    }

    private fun addRandomButtonToSimonSequence() {
        val randomButtonIndex = (0 until soundResources.size).random()
        simonSequence.add(randomButtonIndex)
    }

    private fun playSound(index: Int) {
        mediaPlayer.apply {
            reset()
            setDataSource(resources.openRawResourceFd(soundResources[index]))
            prepare()
            start()
        }
    }

    private fun getButtonByIndex(index: Int): Button {
        return when (index) {
            0 -> findViewById(R.id.button1)
            1 -> findViewById(R.id.button2)
            2 -> findViewById(R.id.button3)
            3 -> findViewById(R.id.button4)
            else -> throw IllegalArgumentException("Invalid button index")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    private fun startSimonSays() {
        addRandomButtonToSimonSequence()
        playSimonSequence()
    }
}
