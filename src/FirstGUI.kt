import com.googlecode.lanterna.TerminalPosition
import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.TextCharacter
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.graphics.TextGraphics
import com.googlecode.lanterna.gui2.*
import com.googlecode.lanterna.gui2.dialogs.MessageDialog
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton
import com.googlecode.lanterna.gui2.dialogs.TextInputDialog
import com.googlecode.lanterna.input.*
import com.googlecode.lanterna.screen.Screen
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.Terminal
import com.googlecode.lanterna.terminal.TerminalResizeListener
import com.googlecode.lanterna.terminal.ansi.UnixTerminal


import java.io.IOException
import java.util.ArrayList
import java.util.Arrays
import java.util.Random

class FirstGUI(private val socket: MySocket) {
    private var users: Label? = null
    private var term: Terminal? = null
    private var screen: Screen? = null
    private var absolutPanel: Panel? = null
    private var separatorHor: Separator? = null
    private var separatorVert: Separator? = null
    private var textBox: TextBox? = null
    private var send: Button? = null
    private var chatContent: Label? = null
    private var textGUI: WindowBasedTextGUI? = null
    private var window: Window? = null
    private var name: String? = null
    private var termColumns: Int = 0
    private var termRows: Int = 0
    private var keyStroke: KeyStroke? = null
    private var exit: Button? = null

    /**
     * Run method with creates and initializes the window
     */
    fun run() {

        term = UnixTerminal()
        termColumns = term!!.getTerminalSize().getColumns()
        termRows = term!!.getTerminalSize().getRows()

        screen = TerminalScreen(term)
        screen?.startScreen()

        textGUI = MultiWindowTextGUI(screen)
        window = BasicWindow("SAD 2019  -   CLIENT CHAT")
        window?.setHints(Arrays.asList(Window.Hint.FULL_SCREEN))

        initComponents()
        sizeComponents()
        initPanel()

        val resizeListener = (object: TerminalResizeListener {
            override fun onResized(terminal: Terminal, terminalSize: TerminalSize) {
                termColumns = terminalSize.getColumns()
                termRows = terminalSize.getRows()
                sizeComponents()
            }
        })

        term?.addResizeListener(resizeListener)
        requestName()

        val listener = KeyStrokeListener(this)
        window?.addWindowListener(listener)
        window?.setComponent(absolutPanel)
        textGUI?.addWindowAndWait(window)

        screen?.stopScreen()
    }

    /**
     * Method that writes to the chat screen
     * @param txt text that will be written
     */
    fun write(txt: String?) {
        if (countLines() >= termRows * 76 / 100) chatContent?.setText("")
        val tmp = chatContent?.getText() + '\n' + txt
        chatContent?.setText(tmp)
    }

    /**
     * Closes the screen and the socket
     */
    fun close() {
        window?.close()
        System.exit(0) //exits the process
    }

    /**
     * Updates the user list in the screen
     * @param usersList current user list
     */
    fun updateUserList(usersList: List<String>) {
        var tmp = ""
        for (s in usersList) {
            tmp = tmp + s + '\n'
        }
        users?.setText(tmp)
    }

    /**
     * Requests the name in screen with an input dialog
     */
    private fun requestName() {
        name = TextInputDialog.showDialog(textGUI, "", "Type your name (max 8 letters)", "")
        if(name==null) close()
        socket.write(name)
    }

    /**
     * Sizes all the components
     */
    private fun sizeComponents() {
        users?.setPosition(TerminalPosition(termColumns * 82 / 100, 0))
        users?.setSize(TerminalSize(8, termRows))

        separatorHor?.setSize(TerminalSize(termColumns, 1))
        separatorHor?.setPosition(TerminalPosition(0, termRows * 76 / 100))

        chatContent?.setPosition(TerminalPosition(1, 0))
        chatContent?.setSize(TerminalSize(termColumns * 78 / 100, termRows * 75 / 100))

        separatorVert?.setSize(TerminalSize(1, termRows))
        separatorVert?.setPosition(TerminalPosition(termColumns * 80 / 100, 0))

        textBox?.setPosition(TerminalPosition(0, termRows * 80 / 100))
        textBox?.setSize(TerminalSize(termColumns * 79 / 100, termRows * 15 / 100))

        send?.setPosition(TerminalPosition(termColumns * 85 / 100, termRows * 80 / 100))
        send?.setSize(TerminalSize(8, 1))

        exit?.setPosition(TerminalPosition(termColumns * 85 / 100, termRows * 85 / 100))
        exit?.setSize(TerminalSize(8, 1))
    }

    /**
     * Initializes all the widgets and the listeners of send and exit button
     */
    private fun initComponents() {
        users = Label("")
        chatContent = Label("")

        separatorHor = Separator(Direction.HORIZONTAL)
        separatorVert = Separator(Direction.VERTICAL)
        textBox = TextBox("")

        send = Button("Send", object : Runnable {
            override fun run() {
                sendAction()
            }
        })

        exit = Button("Exit", object : Runnable {
            override fun run() {
                close()
            }
        })
    }

    /**
     * Adds the widgets to the main panel
     */
    private fun initPanel() {
        absolutPanel = Panel(AbsoluteLayout())
        absolutPanel?.setPreferredSize(TerminalSize(term!!.getTerminalSize().getColumns(), term!!.getTerminalSize().getRows()))
        absolutPanel?.addComponent(users)
        absolutPanel?.addComponent(chatContent)
        absolutPanel?.addComponent(separatorHor)
        absolutPanel?.addComponent(separatorVert)
        absolutPanel?.addComponent(textBox)
        absolutPanel?.addComponent(send)
        absolutPanel?.addComponent(exit)
    }

    /**
     * Count the lines in chat screen
     * @return the number of lines
     */    private fun countLines(): Int {
        val txt = chatContent!!.getText()
        val array = txt.split("\n")
        return array.size + 1
    }

    /**
     * Writes the input text to the chat screen and to the socket
     */
    private fun sendAction() {
        val input = textBox?.getText()
        if (!input.equals("")) {
            socket.write(input)
            textBox?.setText("")
            write("me: " + input)
        }
    }
}
