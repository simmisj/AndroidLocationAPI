package com.example.androidlocationapi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import android.util.Log;

public class SendMessage implements Runnable {

	String message = "NOT SET YET";
	
	String TAG = "test";
	
	String simmiipwireless = "10.25.239.235";
	String simmiipwired = "192.168.56.1";
	String danielip = "10.25.231.246";
	
	String ip = "";
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			Log.v(TAG,"Started sending... to: "+ip);
			   //String modifiedSentence;
			   //BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
			   Socket clientSocket = new Socket(ip, 6789);
			   
			   DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			   
			   //BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			   
			   //sentence = inFromUser.readLine();
			   outToServer.writeBytes(message + '\n');
			   
			   //modifiedSentence = inFromServer.readLine();
			  
			   //System.out.println("FROM SERVER: " + modifiedSentence);
			   clientSocket.close();
			   Log.v(TAG,"Sending finished. Socket closed.");
			}
			catch(Exception ea)
			{
				Log.v(TAG,"ERRORRRR in SendMessage class: "+ea);
				
			}
	}
	// Format of message: MAC_NAME_INSIDE/OUTSIDE   Example:  0f:4f:3s:3e:3e:3e_daniel_inside     0f:4f:3s:3e:3e:3e_daniel_outside
	public SendMessage(String message,String ip)
	{
		this.message = message;
		this.ip = ip;
		
	}

}
