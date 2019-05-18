package bchum.LANTest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Brayden Chumbley
 */
public class Client 
{
	public Client(String address, int port, String userName)
	{
		try
		{
			System.out.println("Attempting to connect to: " + address + ":" + port);
			Socket client = new Socket(address, port);
			
			System.out.println("Connection Aquired!");
			DataOutputStream outStream = new DataOutputStream(client.getOutputStream());
			outStream.writeUTF("Hello, my name is " + userName);
			
			DataInputStream inStream = new DataInputStream(client.getInputStream());
			System.out.println("SERVER: " + inStream.readUTF());
			
			client.close();
		} 
		catch (IOException ex) 
		{
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public static void main(String[] args) throws UnknownHostException
	{
		if(args.length < 0)
		{
			System.out.println("Arguments required: <address> <port> <user_name>");
		}
		Client c = new Client(args[0], Integer.parseInt(args[1]), args[2]);
	}
}
