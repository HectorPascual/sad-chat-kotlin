import java.net.*
import java.io.*

class MyServerSocket(port: Int){
    private val printout: PrintWriter? = null
    private val `in`: BufferedReader? = null
    private var socket: ServerSocket? = null

    init {
        socket = ServerSocket(port)
    }

    /**
     * Accepts a new connection
     * @return the socket connected
     */
    fun accept(): Socket? {
        return socket?.accept()
    }

    /**
     * Closes the socket
     */
    fun close() {
        socket?.close()
    }
}
