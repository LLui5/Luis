import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePlayer implements Runnable
{  private Socket socket              = null;
   private Thread thread              = null;
   private DataInputStream  console   = null;
   private DataOutputStream dataOut = null;
   private GamePlayerWork player    = null;
   private JFrame marco;
   private JPanel ventana;
   private JLabel[] mono=new JLabel[5];
   private ImageIcon[] img=new ImageIcon[5];
   
   private JLabel[] tiempo=new JLabel[10];
   private JLabel[] puntos=new JLabel[10];
   private ImageIcon[] itiempo=new ImageIcon[10];
   private ImageIcon[] ipuntos=new ImageIcon[10];

   private JLabel[] gamenuma=new JLabel[10];
   private JLabel[] gamenumb=new JLabel[10];
   private JLabel[] gamenumc=new JLabel[10];
   private JLabel[] gamenumd=new JLabel[10];
   
   private ImageIcon[] igamenuma=new ImageIcon[10];
   private ImageIcon[] igamenumb=new ImageIcon[10];
   private ImageIcon[] igamenumc=new ImageIcon[10];
   private ImageIcon[] igamenumd=new ImageIcon[10];
   
   
	private int x;
	private int y;
	
	private char mykey='a';
	
	private String[] receive=new String[25];

   public GamePlayer(String engineName, int enginePort)
   {  
      try
      {  socket = new Socket(engineName, enginePort);
         for(int i=0;i<25;i++)
			 receive[i]=new String();
		 marco=new JFrame("Imagenes");
		ventana=new JPanel();

		ventana.setLayout(null);
		for(int i=0;i<5;i++)
		{
		img[i]=new ImageIcon("image"+i+".jpg");
		mono[i]=new JLabel(img[i]);
		ventana.add(mono[i]);
		}
		for(int i=0;i<10;i++)
		{
		itiempo[i]=new ImageIcon("numero"+i+".jpg");
		ipuntos[i]=new ImageIcon("numero"+i+".jpg");
		igamenuma[i]=new ImageIcon("numero"+i+".jpg");
		igamenumb[i]=new ImageIcon("numero"+i+".jpg");
		igamenumc[i]=new ImageIcon("numero"+i+".jpg");
		igamenumd[i]=new ImageIcon("numero"+i+".jpg");
		
		tiempo[i]=new JLabel(itiempo[i]);
		puntos[i]=new JLabel(ipuntos[i]);
		gamenuma[i]=new JLabel(igamenuma[i]);
		gamenumb[i]=new JLabel(igamenumb[i]);
		gamenumc[i]=new JLabel(igamenumc[i]);
		gamenumd[i]=new JLabel(igamenumd[i]);
		
		ventana.add(tiempo[i]);
		ventana.add(puntos[i]);
		ventana.add(gamenuma[i]);
		ventana.add(gamenumb[i]);
		ventana.add(gamenumc[i]);
		ventana.add(gamenumd[i]);
		}
		marco.setSize(800,600);
		marco.setVisible(true);
		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		marco.getContentPane().add(ventana);
		ventana.setFocusable(true);
		 
		 for(int i=1;i<6;i++)
		{
		mono[i-1].setBounds(1000,1000,55,55);
		}
		for(int i=1;i<11;i++)
		{
		tiempo[i-1].setBounds(1000,1000,25,25);
		puntos[i-1].setBounds(1000,1000,25,25);
		gamenuma[i-1].setBounds(1000,1000,25,25);
		gamenumb[i-1].setBounds(1000,1000,25,25);
		gamenumc[i-1].setBounds(1000,1000,25,25);
		gamenumd[i-1].setBounds(1000,1000,25,25);
		}
		 
		ventana.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent ke){
				char mykey=ke.getKeyChar();
				try{
						dataOut.writeUTF(mykey+"");
						dataOut.flush();
						}
					catch(IOException ee)
						{ System.out.println(ee);  }
					
			}
		});
		 
		 
		 //---
		 
         start();
      }
      catch(IOException ee)
      { System.out.println(ee);  }
   }
   public void run(){
   //  while (thread != null && mykey!='q')
    //  {  try
    //     {  dataOut.writeUTF("a"+mykey);
     //       dataOut.flush();
	//		//mykey='q';
     //    }
     //    catch(IOException ee)
      //   {  
     //       stop();
     //    }
    //  }
   }
   public void handle(String info)
   {

	receive[0]="";
	int aux=0;
	for(int i=1;i<info.length();i++){
		if(info.charAt(i)==':'){
			aux++;
			receive[aux]="";
		}
		else
		receive[aux]=""+receive[aux]+info.charAt(i);
	}
	

   
	mono[Integer.parseInt(receive[0])].setLocation(Integer.parseInt(receive[1]),Integer.parseInt(receive[2]));	   
   
   //System.out.println("F "+info);
   //System.out.println("O "+co+"-"+cy+"-"+cx);
   //System.out.println("SP "+cs+"-"+cp);
   
   for(int i=1;i<11;i++)
		{
		tiempo[i-1].setBounds(1000,1000,25,25);
		puntos[i-1].setBounds(1000,1000,25,25);
		gamenuma[i-1].setBounds(1000,1000,25,25);
		gamenumb[i-1].setBounds(1000,1000,25,25);
		gamenumc[i-1].setBounds(1000,1000,25,25);
		gamenumd[i-1].setBounds(1000,1000,25,25);
		}
		
	gamenuma[Integer.parseInt(receive[7])].setLocation(Integer.parseInt(receive[5]),Integer.parseInt(receive[6]));
	gamenumb[Integer.parseInt(receive[10])].setLocation(Integer.parseInt(receive[8]),Integer.parseInt(receive[9]));
	gamenumc[Integer.parseInt(receive[13])].setLocation(Integer.parseInt(receive[11]),Integer.parseInt(receive[12]));
	gamenumd[Integer.parseInt(receive[16])].setLocation(Integer.parseInt(receive[14]),Integer.parseInt(receive[15]));
	

		tiempo[receive[3].charAt(0)-48].setLocation(10,10);
	if(receive[3].length()>1)
		tiempo[receive[3].charAt(1)-48].setLocation(30,10);
	//else
	//	tiempo[0].setLocation(1000,1000);
	if(receive[3].length()>2)
		tiempo[receive[3].charAt(2)-48].setLocation(50,10);
	//else
	//	tiempo[0].setLocation(1000,1000);
	
		puntos[receive[4].charAt(0)-48].setLocation(520,10);
	if(receive[4].length()>1)
		puntos[receive[4].charAt(1)-48].setLocation(540,10);
	//else
	//	puntos[0].setLocation(540,10);
	if(receive[4].length()>2)
		puntos[receive[4].charAt(2)-48].setLocation(560,10);
	//else
	//	puntos[0].setLocation(560,10);		
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