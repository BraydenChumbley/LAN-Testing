/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bchum.LANTest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Brayden Chumbley
 */
public class Server extends Thread
{
	
	private ServerSocket ss;
	
	public Server(int port) throws IOException
	{
		ss = new ServerSocket(port, 1, InetAddress.getLocalHost());
		ss.setSoTimeout(10000);
	}
	
	@Override
	public void run()
	{
		Scanner stdInput = new Scanner(System.in);
		String continueServer;
		
		System.out.print("Start server? (Y/N): ");
		
		while((continueServer = stdInput.nextLine()).equals("Y") || continueServer.equals("y"))
		{
			try
			{
				System.out.println("Waiting for client at: " + ss.getInetAddress().getCanonicalHostName() + ":" + ss.getLocalPort());
				Socket socket = ss.accept();
				
				System.out.println("Connection Aquired!");
				DataInputStream inStream = new DataInputStream(socket.getInputStream());
				System.out.println("CLIENT: " + inStream.readUTF());
				
				DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
				outStream.writeUTF("Thank you for connecting! Goodbye!");
				
				socket.close();
			}
			catch (SocketTimeoutException ex)
			{
				System.out.println("Socket Timed Out");
			}
			catch (IOException ex) 
			{
				Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
			}
			
			System.out.print("Wait for another client? (Y/N): ");
		}
	}
	
    public static void main(String[] args)
	{
		if(args.length < 1)
		{
			System.out.println("Arguments required: <port>");
		}
		
		try
		{
			Thread t = new Server(Integer.parseInt(args[0]));
			t.start();
		}
		catch(NumberFormatException e)
		{
			System.out.println("Invalid port");
		} 
		catch (IOException ex) 
		{
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
