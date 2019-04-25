import java.io.*
import java.net.*
import java.util.ArrayList
import java.util.Arrays

class Client {

    fun main(args: Array<String>) {
        val client = MySocket("127.0.0.1", 1414)

        val gui = FirstGUI(client)

        Thread {
            gui.run()
        }.start()

        Thread {
            var line: String?
            while (client.read().let { line = it; line!=null }) {
                if (line!!.contains("\$userlist")) { // kind of commands to interact with the server
                    val users = ArrayList<String>(line!!.split(",").toTypedArray() as List<String>)
                    users.removeAt(0)
                    gui.updateUserList(users)
                } else {
                    gui.write(line!!)
                }
            }
            client.close()
        }.start()
    }
}
