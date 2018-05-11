import java.io.*;
import java.net.*;
import java.lang.Thread;

//Something having to do with the recieving here or Sending with the Server is off
//UPDATE: The server needs some significant work with integrating user input and throwing messages out into the world

class OLDClient3
{
  public static void main(String args[]) throws Exception
  {
      BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

      Socket clientSocket = new Socket("localhost",8080);

      DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

      BufferedReader inFromServer = new BufferedReader(new
        InputStreamReader(clientSocket.getInputStream()));

      BoolFlag flag = new BoolFlag();
      Thread1 thread1 = new Thread1(flag, inFromUser, outToServer);
      Thread2 thread2 = new Thread2(flag, inFromServer);
      thread1.start();
      thread2.start();
                                                                        System.out.println("Client Threads have started");
  }
}


class Thread1 extends Thread
{
  BoolFlag flag;
  BufferedReader inFromUser;
  DataOutputStream outToServer;

  Thread1(BoolFlag f, BufferedReader keyboardInput, DataOutputStream toServer)
  {
    flag = f;
    inFromUser = keyboardInput;
    outToServer = toServer;
  }//constructor

  public void run()
  {
    //KEYBOARD AND TRANSMISSION
    try
    {
                                                                       System.out.println("Thread1 Client has started.");
      String exitingChat = inFromUser.readLine();;
      while(flag.keepGoing == true && !exitingChat.equals("EXITEXIT"))
      {
                                                                      System.out.println("Client thread1 is in loop");
        exitingChat = inFromUser.readLine();
        outToServer.writeBytes(exitingChat + '\n');
                                                                       System.out.println("Client has sent message");
        if(exitingChat == "EXITEXIT")
        {
          flag.keepGoing = false;
                                                                      System.out.println("Client thread1 flag triggered");
        }
      }
    }
    catch(Exception e)
    {
      System.out.println("Something here seems foul...");
    }
    //close down
  }
}


class Thread2 extends Thread
{
  BoolFlag flag;
  BufferedReader fromServer;

  Thread2(BoolFlag f, BufferedReader inFromServer)
  {
    flag = f;
    fromServer = inFromServer;
  }

  public void run()
  {
                                                                    System.out.println("Client Thread2 has started");
    //SOCKETS AND PRINTING
    try
    {
      String incomingChat = "";
                                                                    System.out.println("Client thread2 has entered try catch");
      while(incomingChat.equals("EXITEXIT") && flag.keepGoing == true)
      {
                                                                    System.out.println("Client Thread2 has entered loop");
        incomingChat = fromServer.readLine();
        System.out.println("FROM SERVER: " + incomingChat);
        //Not sure if this is uber needed
        if(incomingChat == "EXITEXIT")
        {
          flag.keepGoing = false;
                                                                        System.out.println("Client Thread2 has triggered ");
        }
      }
    }
    catch(Exception e)
    {
      System.out.println("Thread2 in Client");
    }
    //handle close
  }
}

class BoolFlag
{
  boolean keepGoing;
  public void createFlag()
  {
    keepGoing = true;
  }

  public void setFlag(boolean value)
  {
    keepGoing = value;
  }

  public boolean getFlag()
  {
    return keepGoing;
  }
}