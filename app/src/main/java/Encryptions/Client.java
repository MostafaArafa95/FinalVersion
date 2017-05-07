package Encryptions;

import com.example.mostafaarafa.finalversion.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;


public class Client {
	
	private static ServerSocket serverSocket = null;
	private static Socket clientSocket = null;
	private static boolean reciving = false;
	
	public static void waitForConnection() throws IOException{
        serverSocket = new ServerSocket(12345);
        clientSocket = serverSocket.accept();
	}
	/*
	public static void connectToClient(final String ip ,final int port) throws IOException{
		new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    clientSocket = new Socket(ip, port);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }).start();

	}*/
    public static void connectToClient(final String ip ,final int port) throws IOException{
        clientSocket = new Socket(ip, port);
    }
	public static void send(String msg) throws IOException, NoConnectionException{
		if(clientSocket==null)
			throw new NoConnectionException();
		PrintStream printStream = new PrintStream(clientSocket.getOutputStream());
		printStream.println(msg);
		printStream.flush();
	}
	public static void startReciving(final CopyOnWriteArrayList<String> msgList) throws IOException, NoConnectionException{
		if(clientSocket==null)
			throw new NoConnectionException();
		reciving = true;
		final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(reciving){
						try {
							if(bufferedReader.ready()){
								String msg = bufferedReader.readLine();
								if(msgList!=null)
									msgList.add(msg);
								System.out.println(msg);
							}
						} catch (IOException e) {}
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
						}
					}
				}
			}).start();
	}
	public static void startReciving(final MainActivity mainActivity) throws IOException, NoConnectionException{
		if(clientSocket==null)
			throw new NoConnectionException();
		reciving = true;
		final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		new Thread(new Runnable() {

			@Override
			public void run() {
				while(reciving){
					try {
						if(bufferedReader.ready()){
							String msg = bufferedReader.readLine();

							mainActivity.receive(msg);

							System.out.println(msg);
						}
					} catch (IOException e) {}
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
					}
				}
			}
		}).start();
	}
	public static void stopReciving(){
		reciving = false;
	}
	public static String getIP(){
        return null;

    }
}
