import java.io.*;
import java.net.*;


public class Mirror {

	public static void main(String args[]) {
		try {
			
			int port = Integer.parseInt(args [0]);
			ServerSocket ss = new ServerSocket(port);
			
			for(;;) {
				Socket client = ss.accept();
				
				BufferedReader in = new BufferedReader(
						new InputStreamReader(client.getInputStream()));
					PrintWriter out = new PrintWriter(client.getOutputStream());
					
					out.print("HTTP/1.1 200 \r\n");
					out.print("Content-Type: text/plain\r\n");
					out.print("Connection: close\r \n");
					out.print("\r\n");
					
					String line;
					while((line = in.readLine()) != null) {
						if ( line.length() == 0) break;
						out.print(line +"\r\n");
					}
				out.close();
				in.close();
				client.close();	
			}
		}
			catch (Exception e) {
				System.err.println(e);
				System.err.println("Usage: Java Hyper Text Mirror <port>");
			}
		
		
		}
}
		
