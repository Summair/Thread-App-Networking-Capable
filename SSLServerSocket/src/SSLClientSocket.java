/*
 * Succesfully using the client and server will need Keystore folder that holds key file through Java SE SDK generator keytool
 * after key genaration  from command interface you will  need to execute from root where key is stored
 * java -Djavax.net.ssl.keyStore=keystore.jks -
 * Djavax.net.ssl.keyStorePassword=<password> packet.SSLServerSocket
 * java -Djavax.net.ssl.trustStore=keystore.jks -
 * Djavax.net.ssl.trustStorePassword=<password> packet.SSLServerSocket
 */



import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.net.ssl.SSLSocketFactory;

public class SSLClientSocket {

	public static void main(String[] args) throws Exception {
		System.out.println("SSLClientSocket Started");
		SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
		try (Socket socket = sf.createSocket("localhost", 8000);
				PrintWriter out = new PrintWriter(
						socket.getOutputStream(), true);
			BufferedReader br = new BufferedReader( new InputStreamReader(socket.getInputStream()))) {
			Scanner scanner = new Scanner (System.in);
			while(true) {
				System.out.print("Enter Text:   ");
				String inputLine = scanner.nextLine();
				if("quit".equalsIgnoreCase(inputLine)) {
					break;
				}
					out.println(inputLine);
					System.out.println("Server response: " + br.readLine());
				}
				System.out.println("SSLServerSocket Terminated");
			}
		}
	}


