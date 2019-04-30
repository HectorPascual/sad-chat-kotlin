import java.io.*
import java.util.concurrent.ConcurrentHashMap

//class Server {

    fun main(args: Array<String>) {
        val server = MyServerSocket(1414)
        val users = ConcurrentHashMap<String, MySocket>()

        while (true) {
          //val socket
          val client = MySocket(server.accept())

          //final String username;
              Thread{
                  // Name request pa
                  var username: String?
                  while (true) {
                      username = client.read()
                      if (!users.containsKey(username)) {
                          users.put(username!!, client)

                          //Update userlist each time a user joins succesfully
                          var userlist = "\$userlist,"
                          for (s in users.keys) {
                              userlist = userlist + s + ","
                          }
                          for (s in users.values) {
                              s.write(userlist)
                              if (s !== users.get(username)) {
                                  System.out.println("Sending " + username + "has join the chat to" + s.toString())
                                  s.write("$username has join the chat")
                              }
                          }
                          break
                      }
                      client.write("Nick already taken. Introduce another username")
                  }

                  // Continuous reading
                  var line: String?
                  while (true) {
                      if (users.get(username)?.read().let { line = it; line!=null }) {
                          for (s in users.keys) {
                              if (s !== username) users.get(s)?.write("$username: $line")
                          }
                      } else {
                          var modifyUsers = "\$userlist,"
                          for (s in users.keys) {
                              if (s !== username) {
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
//}
