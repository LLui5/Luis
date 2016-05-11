import java.net.*;
import java.io.*;

public class GamePlayerWork extends Thread
{  private Socket           socket   = null;
   private GamePlayer       player   = null;
   private DataInputStream  dataIn = null;

   public GamePlayerWork(GamePlayer _player, Socket _socket)
   {  player   = _player;
      socket   = _socket;
      open();  
      start();
   }
   public void open()
   {  try
      {  dataIn  = new DataInputStream(socket.getInputStream());
      }
      catch(IOException ee)
      {  
         player.stop();
      }
   }
   public void close()
   {  try
      {  if (dataIn != null) dataIn.close();
      }
      catch(IOException e)
      {  
      }
   }
   public void run()
   {  while (true)
      {  try
         {  player.handle(dataIn.readUTF());
         }
         catch(IOException ee)
         {  
            player.stop();
         }
      }
   }
}