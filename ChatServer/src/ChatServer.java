import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;


public class ChatServer {

	public ChatServer() {
		try {
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.socket().bind( new InetSocketAddress(5000));
			
			boolean running = true;
			while (running) {
				System.out.println("Waiting for Request......");
				SocketChannel socketChannel = serverSocketChannel.accept();
				System.out.println("Connected to Client");
				String message;
				Scanner scanner = new Scanner(System.in);
				while (true) {
					System.out.print("> ");
					message = scanner.nextLine();
					if (message.equalsIgnoreCase("quit")) {
						HelperMethods.sendFixedLengthMessage (socketChannel, "  Server Terminating");
						running = false;
						break;
						} else {
						HelperMethods.sendFixedLengthMessage( socketChannel, message);
						System.out.println("  Waiting for message from Client  ...");
						System.out.println(" Message:   " + HelperMethods.receiveFixedLengthMessage(socketChannel));
						}
				}
				
			}
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new ChatServer();
	}
	
	
}//end of class
