import java.io.*
import java.net.*
import java.util.ArrayList
import java.util.Arrays

class Client {

    fun main(args: Array<String>) {
        val `in` = BufferedReader(InputStreamReader(System.`in`))
        val client = MySocket("127.0.0.1", 1414)

        val gui = FirstGUI(client)

        Thread {
            gui.run()
        }.start()

        Thread {
            var line: String? = null
            while (client.read().let { line = it; line!=null }) {
                if (line!!.contains("\$userlist")) { // kind of commands to interact with the server
                    val users = ArrayList<String>(Arrays.asList(line!!.split(",")))
                    users.remove(0)
                    gui.updateUserList(users)
                } else {
                    gui.write(line!!)
                }
            }
            client.close()
        }.start()
    }
}
