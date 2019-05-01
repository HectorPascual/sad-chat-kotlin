import java.io.*
import java.net.*
import java.util.ArrayList
import java.util.Arrays
import java.util.*

/**
 *  The Client is the one who starts the GUI. Afer that, proceeds to read from
 *  the Server (other clients) and prints the messages in his GUI. When he leaves,
 *  he closes the connection
*/

fun main(args: Array<String>) {
    val client = MySocket("127.0.0.1", 1414)
    val gui = FirstGUI(client)

    Thread {
        gui.run()
    }.start()

    Thread {
        var line: String?
        while (client.read().let { line = it; line != null }) {
            if (line!!.contains("\$userlist")) { // kind of commands to interact with the server
                val users = line?.split(",") as ArrayList<String>
                users.removeAt(0)
                gui.updateUserList(users)
            } else {
                gui.write(line)
            }
        }
        client.close()
    }.start()
}
