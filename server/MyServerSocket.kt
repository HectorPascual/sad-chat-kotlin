import java.net.*
import java.io.*

class MyServerSocket(port: Int){
    private val printout: PrintWriter? = null
    private val `in`: BufferedReader? = null
    private var socket: Socket? = null

    init {
        socket = Socket(port)
    }

    fun accept(): Socket? {
        return socket!!.accept()
    }

    fun close() {
        socket!!.close()
    }
}
