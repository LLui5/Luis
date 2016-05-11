import java.net.*;
import java.io.*;



public class GameServer implements Runnable
{  private GameServerWork players[] = new GameServerWork[50];
   private ServerSocket engine = null;
   private Thread       thread = null;
   private int playerCount = 0;
   

   public GameServer(int port)
   {  try
      {  
         engine = new ServerSocket(port);  
         start(); }
      catch(IOException ee)
      {  System.out.println(ee);}
   }
   public void run()
   {  while (thread != null)
      {  try
         {  
            addThread(engine.accept()); }
         catch(IOException ee)
         {  stop(); }
      }
   }

      public void start() throws IOException
   {  if (thread == null)
      {  thread = new Thread(this);                   
         thread.start();
      }
   }
   
   public void stop()
   {  if (thread != null)
      {  thread.stop();  
         thread = null;
      }
   }
   
   private int getPlayer(int number)
   {  for (int i = 0; i < playerCount; i++)
         if (players[i].getnumber() == number)
            return i;
      return -1;
   }
   public synchronized void handle(int number, String input,int order)
   {  if (input.equals(".bye"))
      {  players[getPlayer(number)].send(".bye");
         remove(number); }
      else
         for (int i = 0; i < playerCount; i++)
            players[i].send(order + ": " + input);   
   }
   public synchronized void remove(int number)
   {  int pos = getPlayer(number);
      if (pos >= 0)
      {  GameServerWork toTerminate = players[pos];
         if (pos < playerCount-1)
            for (int i = pos+1; i < playerCount; i++)
               players[i-1] = players[i];
         playerCount--;
         try
         {  toTerminate.close(); }
         catch(IOException ee)
         {   }
         toTerminate.stop(); }
   }
   private void addThread(Socket socket)
   {  if (playerCount < players.length)
      {  
         players[playerCount] = new GameServerWork(this, socket);
         try
         {  players[playerCount].open(); 
            players[playerCount].start(); 
			players[playerCount].order=playerCount;
            playerCount++; }
         catch(IOException ee)
         {   }
		 }
      else
         System.out.println("Maximun Players");
   }
   public static void main(String args[]) {
     GameServer engine = null;
         engine = new GameServer(1555);
   }

   
}