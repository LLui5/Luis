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
   public int operationa=0 ;
   public int operationb=0 ;
   public int operationr=0 ;
   public int check=2;
      

   public GameServer(int port)
   {  try
      {  
		for(int i=0;i<4;i++)
		listn[i]= new Listgamenumber();
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
					for(int j=0;j<4;j++){
					if((players[order].x+60)>listn[j].x && (players[order].x-10)<listn[j].x && (players[order].y+60)>listn[j].y && (players[order].y-10)<listn[j].y){
						if(operationa==0||operationb>0)
						{
							operationa=listn[j].value;
							operationb=0;
						}
						else if(operationa>0)
							operationb=listn[j].value;
					}	
					}
				}
   
   //--
   for (int i = 0; i < playerCount; i++)
        players[i].send("k"+order+":"+players[order].x+":"+players[order].y+":"+seconds+":"+puntos+":"+listn[0].x+":"+listn[0].y+":"+listn[0].value+":"+listn[1].x+":"+listn[1].y+":"+listn[1].value+":"+listn[2].x+":"+listn[2].y+":"+listn[2].value+":"+listn[3].x+":"+listn[3].y+":"+listn[3].value+";"+operationa+":"+operationb+":"+operationr+check);			    
		//players[i].send("k"+order+":"+players[order].x+":"+players[order].y+"s"+seconds+"p"+puntos+"ax"+listn[0].x+"ay"+listn[0].y+"av"+listn[0].value+"bx"+listn[1].x+"by"+listn[1].y+"bv"+listn[1].value+"cx"+listn[2].x+"cy"+listn[2].y+"cv"+listn[2].value+"dx"+listn[3].x+"dy"+listn[3].y+"dv"+listn[3].value+"r"+operationr+"a"+operationa+"b"+operationb);
		//order,player[order].x , y , seconds , puntos , listn.x , y , value , listn2.x , y , value , listn3x , y , value , list4x , y , value , operationR , A , B
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
		int interval=021;
		
		public void myTimer(){
			seconds=499;
			flag=0;
		}
		
		public void run(){
			while(true){
				for (int i = 0; i < playerCount; i++)
				players[i].send("k"+0+":"+players[0].x+":"+players[0].y+":"+seconds+":"+puntos+":"+listn[0].x+":"+listn[0].y+":"+listn[0].value+":"+listn[1].x+":"+listn[1].y+":"+listn[1].value+":"+listn[2].x+":"+listn[2].y+":"+listn[2].value+":"+listn[3].x+":"+listn[3].y+":"+listn[3].value+";"+operationa+":"+operationb+":"+operationr+check);			    
		
				seconds=seconds-1;
				interval=interval+1;
				if(seconds>600 || seconds<1){
					seconds=498;
					flag=1;
					operationa=0;
					operationb=0;
					puntos=0;
				}
				if(check==2){
					operationr=2+seconds%10;
					check=0;
				}
				if(check==0){
					if(operationr==operationa+operationb)
						check=1;
				}
				
				
				if(interval>8)
				{	
					listn[0].value=seconds%9;
					listn[1].value=2+seconds%8;
					listn[2].value=seconds%7;
					listn[3].value=3+seconds%6;
					
					listn[0].x=(seconds%8)*80;
					listn[1].x=(seconds%5)*150;
					listn[2].x=(seconds%6)*150;
					listn[3].x=(1+seconds%2)*200;
					
					listn[0].y=(seconds%7)*60;
					listn[1].y=(seconds%4)*130;
					listn[2].y=(seconds%9)*60;
					listn[3].y=(seconds%7)*160;
					
					interval=0;
					if(check==1)
					{
						check=2;
						puntos=puntos+1;
					}
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