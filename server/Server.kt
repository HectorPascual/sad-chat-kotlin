import java.io.*
import java.util.concurrent.ConcurrentHashMap

object Server {

    fun main(args: Array<String>) {
        val server = MyServerSocket.init(1414)

        val users = ConcurrentHashMap<String, MySocket>()

        while (true) {
            val client = MySocket(server.accept())

            //final String username;
            if (client != null) {
                val t1 = Thread(object : Runnable() { // Everything inside a thread in order to make each user input independent
                    fun run() {

                        // Name request part
                        var username: String
                        while (true) {
                            username = client.read()
                            if (!users.containsKey(username)) {
                                users.put(username, client)

                                //Update userlist each time a user joins succesfully
                                var userlist = "\$userlist,"
                                for (s in users.keySet()) {
                                    userlist = "$userlist$s,"
                                }
                                for (s in users.values()) {
                                    s.write(userlist)
                                    if (s !== users.get(username)) {
                                        System.out.println("Sending " + username + "has join the chat to" + s.toString())
                                        s.write("$username has join the chat")
                                    }
                                }
                                break
                            }
                            client.write("Nick already taken. Introduce another username")
                            // it should not change the gui
                        }

                        // Continuous reading
                        var line: String
                        while (true) {
                            if ((line = users.get(username).read()) != null) {
                                for (s in users.keySet()) {
                                    if (s !== username) users.get(s).write("$username: $line")
                                }
                            } else {
                                var modifyUsers = "\$userlist,"
                                for (s in users.keySet()) {
                                    if (s !== username) {
                                        modifyUsers = "$modifyUsers$s,"
                                        users.get(s).write("$username has left the chat")
                                    }
                                }
                                users.remove(username)
                                for (s in users.values()) {
                                    System.out.println(modifyUsers)
                                    s.write(modifyUsers)
                                }
                                break
                            }
                        }
                    }
                })
                t1.start()
            }
        }
    }
}
