import java.net.*
import java.io.*

class MyServerSocket @Throws(IOException::class)
constructor(port: Int) : ServerSocket(port) {

    private val printout: PrintWriter? = null
    private val `in`: BufferedReader? = null

    @Override
    fun accept(): Socket? {
        try {
            return super.accept()
        } catch (ex: IOException) {
        }

        return null
    }

    @Override
    fun close() {
        try {
            super.close()
        } catch (ex: IOException) {
        }

    }

    companion object {

        fun init(port: Int): MyServerSocket? {
            try {
                return MyServerSocket(port)
            } catch (ex: IOException) {
                return null
            }

        }
    }
}
