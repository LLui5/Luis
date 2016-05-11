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

	private int x;
	private int y;
	
	private char mykey='a';
	
	private String co="";
	private String cx="";
	private String cy="";
	private String aux="";

   public GamePlayer(String engineName, int enginePort)
   {  
      try
      {  socket = new Socket(engineName, enginePort);
         
		 marco=new JFrame("Imagenes");
		ventana=new JPanel();
		ventana.setLayout(null);
		for(int i=0;i<5;i++)
		{
		img[i]=new ImageIcon("image"+i+".jpg");
		mono[i]=new JLabel(img[i]);
		ventana.add(mono[i]);
		}
		marco.setSize(800,600);
		marco.setVisible(true);
		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		marco.getContentPane().add(ventana);
		ventana.setFocusable(true);
		 
		 for(int i=1;i<6;i++)
		{
		mono[i-1].setBounds(60*i,60*i,55,55);
		}
		 
		ventana.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent ke){
				char mykey=ke.getKeyChar();
				try{
						dataOut.writeUTF(mykey+"a");
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
		co="";
		cy="";
		cx="";
	
   for(int i=0;i<info.length();i++){
	   if(info.charAt(i)==':'){
		    i++;
			for(int j=i;j<info.length();j++){
			if(info.charAt(j)==':'){
				j++;
				for(int k=j;k<info.length();k++){
					cy=""+cy+info.charAt(k);
				}
			j=info.length();	
			}else
				cx=""+cx+info.charAt(j);
			}
		i=info.length();	
	   }else
			co=""+co+info.charAt(i);
   }
   
	mono[Integer.parseInt(co)].setLocation(Integer.parseInt(cx),Integer.parseInt(cy));	   
   
   System.out.println("F "+info);
   System.out.println("O "+co+"-"+cy+"-"+cx);
   
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