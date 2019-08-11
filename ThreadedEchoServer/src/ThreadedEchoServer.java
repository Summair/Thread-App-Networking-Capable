import java.net.*;
import java.io.*;
import java.lang.*;
/*
 * Thread is a block of Code that executes concurrently with other blocks of code in an application
 */




public class ThreadedEchoServer  implements Runnable{

	private static Socket clientSocket;
	
	public ThreadedEchoServer(Socket clientSocket) {
		this.clientSocket = clientSocket;
		
	}
public static void main(String[] args) {
	System.out.print("Threaded Echo Server");
	try (ServerSocket serverSocket = new ServerSocket(6000)) {
		while (true) {
			System.out.println("Waiting for connection......");
			clientSocket = serverSocket.accept();
			ThreadedEchoServer tes = new ThreadedEchoServer(clientSocket);
			new Thread(tes).start();
		}
	} catch (IOException ex) {
		//handle exceptions
	}
	System.out.println ("Threaded Echo Server Terminating");
}

@Override

public void run() {
	System.out.println ("Connected to client using [" + Thread.currentThread() + "]");
	try (BufferedReader br = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
		String inputLine;
		while ((inputLine = br.readLine()) != null ) {
			System.out.println("Client request [" + Thread.currentThread() + "]: " + inputLine);
			out.println(inputLine);
		}
		System.out.println("Client [" + Thread.currentThread() + " connection terminated");
		
	} catch (IOException ex) {
		// handle exceptions
	}
}
}