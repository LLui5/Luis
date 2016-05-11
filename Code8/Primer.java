import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Primer {
	JFrame marco;
	JPanel ventana;
	JLabel[] mono=new JLabel[2];
	ImageIcon[] img=new ImageIcon[2];

	int x;
	int y;
	
	public void PrimerGUI(){
		marco=new JFrame("Imagenes");
		ventana=new JPanel();
		ventana.setLayout(null);
		img[0]=new ImageIcon("k3.jpg");
		img[1]=new ImageIcon("k1.jpg");
		mono[0]=new JLabel(img[0]);
		mono[1]=new JLabel(img[1]);
		
		x=50;
		y=50;
		
		ventana.add(mono[0]);
		ventana.add(mono[1]);

		marco.setSize(700,500);
		marco.setVisible(true);
		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		marco.getContentPane().add(ventana);
		ventana.setFocusable(true);
		mono[0].setBounds(x,y,110,110);
		mono[1].setBounds(100,400,110,90);
		
		Thread t;
		t=new Thread(new Waddle());
		t.start();
		
		ventana.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent algo){
				Point punto=algo.getPoint();
				if(algo.getButton()==1) {
					mono[0].setLocation(punto);
					x=(int) punto.getX();
					y=(int) punto.getY();
					
				}
			}
		}) ;
			
		ventana.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent ke){
				char tec=ke.getKeyChar();
				if(tec=='z'){
					x=x-7;
					mono[0].setLocation(x,y);
				}
				if(tec=='x'){
					y=y+7;
					mono[0].setLocation(x,y);
				}
				if(tec=='c'){
					x=x+7;
					mono[0].setLocation(x,y);
				}
				if(tec=='s'){
					y=y-7;
					mono[0].setLocation(Integer.parseInt(x),y);
				}
			}
		});
	}
	
	
	
	public void actionPerformed(ActionEvent event){

	}
	
	
	
	public static void main(String args[]) {
		Primer test = new Primer();
		test.PrimerGUI();
	}
	
	class Waddle implements Runnable{
		int x1=100;
		int y1=400;
		
		public Waddle(){
			x1=100;
			y1=400;
		}
		
		public void run(){
			while(true){
				x1=x1+1;
				mono[1].setLocation(x1,y1);
				try {
					Thread.sleep(100);
					}
				catch(InterruptedException algo) {
					System.out.println(algo);
				}
				
			}
		}
	}
	
}