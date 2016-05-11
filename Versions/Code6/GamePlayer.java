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

	private int x;
	private int y;
	
	private char mykey='a';
	
	private String co="";
	private String cx="";
	private String cy="";
	private String cs="";
	private String cp="";

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
		for(int i=0;i<10;i++)
		{
		itiempo[i]=new ImageIcon("numero"+i+".jpg");
		tiempo[i]=new JLabel(itiempo[i]);
		
		ipuntos[i]=new ImageIcon("numero"+i+".jpg");
		puntos[i]=new JLabel(ipuntos[i]);
		
		ventana.add(tiempo[i]);
		ventana.add(puntos[i]);
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
		}
		for(int i=1;i<11;i++)
		{
		puntos[i-1].setBounds(1000,1000,25,25);
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
		co="";
		cy="";
		cx="";
		cp="";
		cs="";
	
   for(int i=1;i<info.length();i++){
	   if(info.charAt(i)==':'){
		    i++;
			for(int j=i;j<info.length();j++){
			if(info.charAt(j)==':'){
				j++;
				for(int k=j;k<info.length();k++){
				if(info.charAt(k)=='s'){
					k++;
					for(int m=k;m<info.length();m++){
					if(info.charAt(m)=='p'){
						m++;
						for(int n=m;n<info.length();n++){
							cp=""+cp+info.charAt(n);
						}
					m=info.length();
					}else
						cs=""+cs+info.charAt(m);
					}
				k=info.length();
				}else	
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
   System.out.println("SP "+cs+"-"+cp);
   
   for(int i=1;i<11;i++)
   {
		tiempo[i-1].setBounds(1000,1000,25,25);
		puntos[i-1].setBounds(1000,1000,25,25);
	}

		tiempo[cs.charAt(0)-48].setLocation(10,10);
	if(cs.length()>1)
		tiempo[cs.charAt(1)-48].setLocation(30,10);
	else
		tiempo[0].setLocation(30,10);
	if(cs.length()>2)
		tiempo[cs.charAt(2)-48].setLocation(50,10);
	else
		tiempo[0].setLocation(50,10);
	
		puntos[cp.charAt(0)-48].setLocation(520,10);
	if(cp.length()>1)
		puntos[cp.charAt(1)-48].setLocation(540,10);
	else
		puntos[0].setLocation(540,10);
	if(cp.length()>2)
		puntos[cp.charAt(2)-48].setLocation(560,10);
	else
		puntos[0].setLocation(560,10);		
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