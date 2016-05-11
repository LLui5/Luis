import java.net.*;
import java.io.*;

public class GamePlayer implements Runnable
{  private Socket socket              = null;
   private Thread thread              = null;
   private DataInputStream  console   = null;
   private DataOutputStream dataOut = null;
   private GamePlayerWork player    = null;

   public GamePlayer(String engineName, int enginePort)
   {  
      try
      {  socket = new Socket(engineName, enginePort);
         
         start();
      }
      catch(IOException ee)
      { System.out.println(ee);  }
   }
   public void run()
   {  while (thread != null)
      {  try
         {  dataOut.writeUTF(console.readLine());
            dataOut.flush();
         }
         catch(IOException ee)
         {  
            stop();
         }
      }
   }
   public void handle(String info)
   {  if (info.equals(".bye"))
      {  
         stop();
      }
      else
         System.out.println(info);
   }
   
   public void start() throws IOException
   {  console   = new DataInputStream(System.in);
      dataOut = new DataOutputStream(socket.getOutputStream());
      if (thread == null)
      {  player = new GamePlayerWork(this, socket);
         thread = new Thread(this);                   
         thread.start();
      }
   }
   public void stop()
   {  if (thread != null)
      {  thread.stop();  
         thread = null;
      }
      try
      {  if (console   != null)  console.close();
         if (dataOut != null)  dataOut.close();
         if (socket    != null)  socket.close();
      }
      catch(IOException ee)
      {   }
      player.close();  
      player.stop();
   }
   public static void main(String args[])
   {  GamePlayer player = null;
         player = new GamePlayer("localhost", 1555);
   }
}