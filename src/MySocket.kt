import java.io.*
import java.net.*

class MySocket(ip: String, port: Int) {
    private var out: PrintWriter? = null
    private var input: BufferedReader? = null
    private var socket: Socket? = null

    init {
        socket = Socket(ip, port)
    }

    fun close() {
        socket?.close()
    }

    fun shutdownInput() {
        socket?.shutdownInput()
    }

    /**
     * Writes a string to the socket
     * @param line string that will be written
     */
    fun write(line: String?) {
        out = PrintWriter(socket?.getOutputStream(), true)
        out?.println(line)
    }

    /**
     * Reads from the socket
     * @return the line read
     */
    fun read(): String? {
        input = BufferedReader(InputStreamReader(socket?.getInputStream()))
        return input?.readLine()
    }

}
