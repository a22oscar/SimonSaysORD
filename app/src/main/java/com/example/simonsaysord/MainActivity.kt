package com.example.simonsaysord
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private val soundResources = intArrayOf(
        R.raw.sound1,
        R.raw.sound2,
        R.raw.sound3,
        R.raw.sound4,
        R.raw.sound5 // Nuevo sonido de derrota
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
                onButtonClick(index, button)
            }
        }

        startSimonSays()
    }

    private fun onButtonClick(index: Int, button: Button) {
        if (index == simonSequence[userSequenceIndex]) {
            animateButton(button)
            playSound(index)

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
            // El usuario se equivocó, manejar la derrota
            handleDefeat()
        }
    }

    private fun handleDefeat() {
        hideButtons()
        playSound(soundResources.last()) // Reproducir sonido de derrota
        Handler().postDelayed({
            showButtons()
            restartGame()
        }, mediaPlayer.duration.toLong()) // Reiniciar el juego después de que termine el sonido
    }

    private fun hideButtons() {
        val buttons = arrayOf(
            findViewById<Button>(R.id.button1),
            findViewById<Button>(R.id.button2),
            findViewById<Button>(R.id.button3),
            findViewById<Button>(R.id.button4)
        )
        buttons.forEach { it.visibility = View.INVISIBLE }
    }

    private fun showButtons() {
        val buttons = arrayOf(
            findViewById<Button>(R.id.button1),
            findViewById<Button>(R.id.button2),
            findViewById<Button>(R.id.button3),
            findViewById<Button>(R.id.button4)
        )
        buttons.forEach { it.visibility = View.VISIBLE }
    }

    private fun animateButton(button: Button) {
        button.isPressed = true
        Handler().postDelayed({ button.isPressed = false }, 500) // Cambiar el color del botón durante 500ms
    }

    private fun playSimonSequence() {
        val handler = Handler()
        for (i in 0 until simonSequence.size) {
            val buttonIndex = simonSequence[i]
            val button = findViewById<Button>(getButtonId(buttonIndex))
            handler.postDelayed(
                {
                    animateButton(button)
                    playSound(buttonIndex)
                },
                (i + 1) * 1000L // Retraso de 1 segundo entre cada botón en la secuencia
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
        val randomButtonIndex = (0 until soundResources.size - 1).random() // Excluir el sonido de derrota
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

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    private fun startSimonSays() {
        addRandomButtonToSimonSequence()
        playSimonSequence()
    }

    private fun getButtonId(index: Int): Int {
        return when (index) {
            0 -> R.id.button1
            1 -> R.id.button2
            2 -> R.id.button3
            3 -> R.id.button4
            else -> throw IllegalArgumentException("Invalid button index")
        }
    }
}

