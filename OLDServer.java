import java.io.*;
import java.net.*;
import java.lang.Thread;


//Line 91 & Line 51 has a pretty major issue. IT just isn't working.
//listeners???
class OLDServer3
{
  public static void main(String args[]) throws Exception
  {

                                                                            System.out.println("Server is doing something 0");
    ServerSocket welcomeSocket = new ServerSocket(8080);
                                                                            System.out.println("Server is doing something .5");
    Socket connection = welcomeSocket.accept();
                                                                            System.out.println("Server is doing something 1");
    BufferedReader inFromClient = new BufferedReader(new
        InputStreamReader(connection.getInputStream()));

    DataOutputStream outToClient = new DataOutputStream(connection.getOutputStream());
                                                                            System.out.println("Server is doing something 2");
    BoolFlag flag = new BoolFlag();
    Thread1 t1 = new Thread1(flag, inFromClient, outToClient);
    Thread2 t2 = new Thread2(flag, inFromClient);

    t1.start();
    t2.start();
                                                                            System.out.println("Server Threads have started");
  }
}

class Thread1 extends Thread
{
  BoolFlag flag;
  BufferedReader userInput;
  DataOutputStream toClient;

  Thread1(BoolFlag f, BufferedReader keyboardInput, DataOutputStream outToClient){
    flag = f;
    userInput = keyboardInput;
    toClient = outToClient;
  }//constructor

  public void run()
  {
    System.out.println("ServerThread1 entered run()");
    //KEYBOARD AND TRANSMISSION
    try
    {
                                                                        System.out.println("Server Thread1 entered try catch");
      String outbound = userInput.readLine();
                                                                        System.out.println("Server Thread1 outgoing message is " + outbound);
      while(!outbound.equals("EXITEXIT") && flag.goOn == true)
      {
                                                                        System.out.println("Server Thread1 is in loop");
        toClient.writeBytes(outbound + '\n');
        outbound = userInput.readLine();
        if(outbound == "EXITEXIT")
        {
          flag.goOn = false;
                                                                        System.out.println("Thread1 Server flag Triggered");
        }
      }
    }
    catch(Exception e)
    {
      System.out.println("Thread1, Server");
    }
    //close threads down
  }
}


class Thread2 extends Thread
{
  BoolFlag flag;
  BufferedReader fromClient;

  Thread2(BoolFlag f, BufferedReader inFromClient)
  {
    flag = f;
    fromClient = inFromClient;
  }

  public void run(){
    //SOCKETS AND PRINTING
                                                                      System.out.println("Server Thread2 (printing) has started it's run() method");
    try
    {
      String inbound = "";
                                                                      System.out.println("Server Thread2 is in try-catch");
      while(inbound!="EXITEXIT" &&  flag.goOn == true){
        inbound = fromClient.readLine();
                                                                      System.out.println("Server Thread2 has recieved a message: " +inbound);
        System.out.println("FROM CLIENT: " + inbound);
        if(inbound == "EXITEXIT")
        {
          flag.goOn = false;

        }
      }
    }
    catch(Exception e)
    {
      System.out.println("Thread2: Server ERROR");
    }

    //close everything down
  }
}

class BoolFlag
{
  boolean goOn;
  public void createFlag()
  {
    boolean goOn = true;
  }

  public void setFlag(boolean value)
  {
    goOn = value;
  }

  public boolean getFlag()
  {
    return goOn;
  }
}