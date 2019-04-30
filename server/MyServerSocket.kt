import java.net.*
import java.io.*

class MyServerSocket(port: Int){
    private val printout: PrintWriter? = null
    private val `in`: BufferedReader? = null
    private var socket: ServerSocket? = null

    init {
        socket = ServerSocket(port)
    }

    fun accept(): Socket? {
        return socket?.accept()
    }

    fun close() {
        socket?.close()
    }
}
