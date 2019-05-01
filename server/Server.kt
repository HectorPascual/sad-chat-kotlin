import java.io.*
import java.util.concurrent.ConcurrentHashMap

/* The Server creates a socket connection and proceeds to accept as many clients
as possible. When a client enters the chat, a thread is started and the client is
able to register his name (if is not taken) and after that is able to talk with
the other participants.
The Server also informs when a client enters or lefts the chat */

fun main(args: Array<String>) {
    val server = MyServerSocket(1414)
    val users = ConcurrentHashMap<String, MySocket>()

    while (true) {
      val client = MySocket(server.accept())

      Thread{
      // Name request part
          var username: String?
          while (true) {
              username = client.read()
              if (username!=null && !users.containsKey(username)) {
                  users.put(username, client)

                  //Update userlist each time a user joins succesfully
                  var userlist = "\$userlist,"
                  for (s in users.keys) {
                      userlist = userlist + s + ","
                  }
                  for (s in users.values) {
                      s.write(userlist)
                      if (s != users.get(username)) {
                          System.out.println("Sending " + username + "has joined the chat to" + s.toString())
                          s.write("$username has joined the chat")
                      }
                  }
                  break
              }
              client.write("Nick already taken. Introduce another username")
          }

          // Continuous reading
          var line: String?
          while (true) {
              if (users.get(username)?.read().let { line = it; line != null }) {
                  for (s in users.keys) {
                      if (s != username) users.get(s)?.write("$username: $line")
                  }
              } else {
                  var modifyUsers = "\$userlist,"
                  for (s in users.keys) {
                      if (s != username) {
                          modifyUsers = modifyUsers + s + ","
                          users.get(s)?.write("$username has left the chat")
                      }
                  }
                  users.remove(username)
                  for (s in users.values) {
                      System.out.println(modifyUsers)
                      s.write(modifyUsers)
                  }
                  break
              }
          }
      }.start()
    }
}
