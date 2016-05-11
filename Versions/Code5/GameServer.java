import java.net.*;
import java.io.*;

public class GameServer implements Runnable
{  private GameServerWork players[] = new GameServerWork[100];
   private ServerSocket engine = null;
   private Thread       thread = null;
   private int playerCount = 0;
   public int seconds=500;
   public int puntos=0;
   public Listgamenumber listn[]=new Listgamenumber[4];
      

   public GameServer(int port)
   {  try
      {  
		Thread t;
		t=new Thread(new MyTimer());
		t.start();
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
   public synchronized void handle(int number, String receive,int order)
   { 
   char input=receive.charAt(0);
   System.out.println(input);
   	if(input=='z'){
					players[order].x=players[order].x-7;
				
				}
				if(input=='x'){
					players[order].y=players[order].y+7;
				
				}
				if(input=='c'){
					players[order].x=players[order].x+7;
				
				}
				if(input=='s'){
					players[order].y=players[order].y-7;
				
				}
				if(input=='g'){
					players[order].y=players[order].y-7;
				
				}
   
   //--
   for (int i = 0; i < playerCount; i++)
            players[i].send("k"+order+":"+players[order].x+":"+players[order].y+"s"+seconds+"p"+puntos);
	
   }
   
   //public synchronized void sendTimer(int sec)
   //{
 
  // }
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
   
   public int getgamenumber(int x,int y){
	   for(int i=0;i<4;i++)
		   if(x<listn[i].x+50 && x>listn[i].x)
			   if(y<listn[i].y+50 && y>listn[i].y)
				   return listn[i].value;
			   return 0;
	   
   }

class Listgamenumber{
	public int x=0;
	public int y=0;
	public int value;
}

	class MyTimer implements Runnable{
		int flag=0;
		
		public void myTimer(){
			seconds=499;
			flag=0;
		}
		
		public void run(){
			while(true){
				seconds=seconds-1;
				if(seconds>600 || seconds<1){
					seconds=498;
					flag=1;
				}
					
				try {
					Thread.sleep(1000);
					}
				catch(InterruptedException algo) {
					System.out.println(algo);
				}
				
			}
		}
	}
}