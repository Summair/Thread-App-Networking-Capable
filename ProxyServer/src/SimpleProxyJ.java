/* single-threaded proxy server. Proxy server is one that acts as a proxy forwards the client requests to the real server
 * 
 * 
 */




import java.io.*;
import java.net.*;




public class SimpleProxyJ {

	public static void main(String[] args) throws IOException {
	
		try {
			
			if (args.length != 3)
				throw new IllegalArgumentException("Wrong number of args.");
			
			String host = args[0];
			int remoteport = Integer.parseInt(args[1]);
			int localport = Integer.parseInt(args[2]);
			
			System.out.println("Starting proxy for " + host + ":" + remoteport + "on port " + localport);
			
			runServer (host, remoteport, localport);
			
		}
		
		catch (Exception e) {
			System.err.println(e);
			System.err.println("Usage: java SimpleProxyServer " + "<host> <remoteport> <localport>");
		}
		
	}

public static void runServer(String host, int remoteport, int localport) throws IOException {
	
		ServerSocket ss = new ServerSocket(localport);
		final byte[] request = new byte[1024];
		byte[] reply = new byte[4096];
		
		
		while(true) {
			
			Socket client = null, server = null;
			try {
				client = ss.accept();
			final InputStream from_client = client.getInputStream();
			final OutputStream to_client = client.getOutputStream();
			
		
			try { server = new Socket(host , remoteport); }
			catch (IOException e) {
				PrintWriter out = new PrintWriter(to_client);
				out.print("Proxy server cannot connect to " + host + ": " + remoteport + ":\n" + e  + "\n");
				out.flush();
				client.close();
				continue;
				}
		// Get Server Streams 
			
			
			final InputStream from_server = server.getInputStream();
			final OutputStream to_server = server.getOutputStream();
	
	// Make thread to read the clients requests and pass them to the server		
			
			
			
			Thread t = new Thread() {
				public void run() {
					int bytes_read;
					try { while (( bytes_read = from_client.read(request)) != -1) {
						to_server.write(request, 0, bytes_read);
						to_server.flush();
						
					}
				}
					
			catch (IOException e) {}		
					
					
			try {to_server.close();} catch (IOException e) {}
				}
			};
			
			t.start();
			
			int bytes_read;
			
			try {
				while((bytes_read = from_server.read (reply)) != -1) {
					to_client.write(reply,0, bytes_read);
					to_client.flush();
					
				}
			}
		catch(IOException e) {}
			
		to_client.close();
		}
				
		catch (IOException e) { System.err.println(e); }
		finally {
			try {
				if (server != null) server.close();
				if (client != null) client.close();
			}
		 catch(IOException e) {}
		
		}
	  }

	}	
}

