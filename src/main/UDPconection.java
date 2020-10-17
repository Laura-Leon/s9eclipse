package main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.google.gson.Gson;

import model.Pedido;

public class UDPconection extends Thread {

	
	
	private DatagramSocket socket;
	private PeerA peera;
	private onMessageListener observer;
	
	  public void setObserver (onMessageListener observer) {
	    	this.observer = observer;
	    }

	
	public void run () {
		try {
			
			// escucha
			socket = new DatagramSocket(5000);
			// espera mensaje
			
			while (true) {
				
				byte [ ] buffer = new byte[100];
				
				DatagramPacket packet = new DatagramPacket (buffer,buffer.length);
				System.out.println("Esperando datagrama");
				
				socket.receive(packet);
				
				String mensaje = new String (packet.getData()).trim();
				
				System.out.println("Datagrama recibito"+ mensaje);
				
				  Gson gson = new Gson();
	                
	                Pedido pedido = gson.fromJson(mensaje, Pedido.class);
	                System.out.println( pedido.getType());
	                
	              
	                	observer.onMessage(pedido);
				
			
			}
		
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String mensaje) {
		new Thread (
				()->{

					try {
						InetAddress ip = InetAddress.getByName("192.168.0.3");
						DatagramPacket packet = new DatagramPacket(mensaje.getBytes(),mensaje.getBytes().length,ip,6000);
						socket.send(packet);
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					
				}
				
				).start();

		

	}

	
}
