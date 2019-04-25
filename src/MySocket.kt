import java.io.*
import java.net.*

class MySocket {
    private var out: PrintWriter? = null
    private var `in`: BufferedReader? = null
    private var socket: Socket? = null

    constructor(s: Socket) {
        socket = s
    }

    constructor(ip: String, port: Int) {
        try {
            socket = Socket(ip, port)
        } catch (ex: IOException) {
        }

    }


    /*@Override
  public void connect(SocketAddress addr){
    try{
      super.connect(addr);
    }catch(IOException ex){}
  }*/

    fun close() {
        try {
            socket!!.close()
        } catch (ex: IOException) {
        }

    }

    fun shutdownInput() {
        try {
            socket!!.shutdownInput()
        } catch (ex: IOException) {
        }

    }

    fun write(line: String) {
        try {
            out = PrintWriter(socket!!.getOutputStream(), true)
        } catch (ex: IOException) {
        }

        out!!.println(line)
    }

    fun read(): String? {
        try {
            `in` = BufferedReader(InputStreamReader(socket!!.getInputStream()))
            return `in`!!.readLine()
        } catch (ex: IOException) {
        }

        return null
    }

}