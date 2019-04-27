import com.googlecode.lanterna.TerminalPosition
import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.gui2.*
import com.googlecode.lanterna.input.KeyStroke
import com.googlecode.lanterna.input.KeyType

import java.util.concurrent.atomic.AtomicBoolean

class KeyStrokeListener(private val gui: FirstGUI) : WindowListener {

    override fun onInput(basePane: Window, keyStroke: KeyStroke, deliverEvent: AtomicBoolean) {
        if (keyStroke.getKeyType() === KeyType.Escape) {
            gui.close()
        }
    }

    override fun onUnhandledInput(basePane: Window?, keyStroke: KeyStroke?, hasBeenHandled: AtomicBoolean?) {
        // TODO Auto-generated method stub
    }

    override fun onResized(window: Window?, oldSize: TerminalSize?, newSize: TerminalSize?) {
        // TODO Auto-generated method stub
    }

    override fun onMoved(window: Window?, oldPosition: TerminalPosition?, newPosition: TerminalPosition?) {
        // TODO Auto-generated method stub
    }
}