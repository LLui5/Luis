import java.net.*;
import java.io.*;

public class GameServerWork extends Thread
{  private GameServer       engine    = null;
   private Socket           socket    = null;
   private int              number        = -1;
   public int              order        = -1;
   private DataInputStream  dataIn  =  null;
   private DataOutputStream dataOut = null;
   public int 				x=50;
   public int	y=50;

   public GameServerWork(GameServer _engine, Socket _socket)
   {  super();
      engine = _engine;
      socket = _socket;
      number     = socket.getPort();
   }
   public void send(String info)
   {   try
       {  dataOut.writeUTF(info);
          dataOut.flush();
       }
       catch(IOException ee)
       {  
          engine.remove(number);
          stop();
       }
   }
   public int getnumber()
   {  return number;
   }
   public void run()
   {  
      while (true)
      {  try
         {  engine.handle(number, dataIn.readUTF(),order);
         }
         catch(IOException ee)
         {  
            engine.remove(number);
            stop();
         }
      }
   }
   public void open() throws IOException
   {  dataIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
      dataOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
   }
   public void close() throws IOException
   {  if (socket != null)
	    socket.close();
      if (dataIn != null)
		dataIn.close();
      if (dataOut != null)
		dataOut.close();
   }
}