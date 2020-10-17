package main;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import model.Pedido;
import processing.core.PApplet;
import processing.core.PImage;

public class PeerA extends PApplet implements onMessageListener {
	UDPconection udp;
	
	private PImage hot;
	private PImage jugo;
	private PImage sand;
	private PImage coca;
	private Pedido pedido;
	
	private ArrayList <Pedido> pedidos;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("main.PeerA");
		
		
	}
	public void settings () {
		size (500,700);
		
		
		
	}
	public void setup () {
		
		hot = loadImage ("images/hotdog.PNG");
		jugo= loadImage ("images/jugo.PNG");
		sand= loadImage ("images/sandwish.PNG");
		coca= loadImage ("images/coca.PNG");
		
		pedidos = new ArrayList <Pedido> ();
		
		
		udp = new UDPconection();
		
		udp.setObserver(this);
		udp.start();
		
		 try {
             InetAddress n = InetAddress.getLocalHost();
             String ip = n.getHostAddress();
             System.out.println(ip);

         } catch (UnknownHostException e) {
             e.printStackTrace();
         }
		
		 pedido = new Pedido();
		
	}
	public void draw () {
		
			background (255);
		
		
		for (int i =0; i<pedidos.size(); i++) {
		Pedido pedido = pedidos.get(i);
		
			if (pedido.getType().equals("cocacola")) {
				image (coca, 0, 0, 100, 100);
			}
			
			if (pedido.getType().equals("hotdog")) {
				image (hot, 120, 0, 100, 100);
			}
			
			if (pedido.getType().equals("jugo")) {
				image (jugo, 0, 120, 100, 100);
			}
			
			if (pedido.getType().equals("sandwish")) {
				image (sand, 120, 120, 100, 100 );
			}
		}
		
	}
	public void mousePressed () {
		udp.sendMessage("holi desde peerA");
	}
	public void onMessage(Pedido pedido ) {
		
		pedidos.add(pedido);
	
}
}