
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

import javax.net.ssl.SSLServerSocketFactory;

/*M.Brohi
 * SSL Server is to serve as the echo server. it is similar to echo server
 * Use of SSLServerSocketFactory class
 * purpose is to deliver secure connection to client and server bound to port 8000
 */
public class SSLServerSocket {

	public static void main(String[] args) {
		try {
			SSLServerSocketFactory ssf = (SSLServerSocketFactory)
					SSLServerSocketFactory.getDefault();
			ServerSocket serverSocket = ssf.createServerSocket(8000);
			System.out.println("SSLServerSocket Started ");
			try (Socket socket = serverSocket.accept();
					PrintWriter out = new PrintWriter (
							socket.getOutputStream(), true);
					BufferedReader br = new BufferedReader(
							new InputStreamReader(
									socket.getInputStream()))){
				System.out.println("Client socket created");
				String line = null;
				while(((line = br.readLine()) !=null)) {
					System.out.println(line);
					out.println(line);
				}
				br.close();
				System.out.println("SSLServerSocket Terminated");
			} catch (IOException ex) {
				//handle exceptions
			}
		} catch (IOException ex) {
			// handle exceptions
		}
	
	}

}
