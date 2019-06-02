/* Generic Framework built Server, brings life to a multi-threaded server
 * it listens on any number of specified ports, and passes along the request to the input/output streams
 * also limits the number of concurrent connections/ logs activity to a specified stream
 */

import java.io.*;
import java.net.*;
import java.security.Provider.Service;
import java.util.*;
import java.util.function.BiFunction;
import java.util.logging.*;

import javax.sound.sampled.Control;

public class Server {

	
	public static void main(String[] args) {
	
	try {
		if (args.length < 2)
			throw new IllegalArgumentException("Please Specify Service ");
		
		// create server object that has a limit of 100 concurrent connections
		
		Server s = new Server(Logger.getLogger(Server.class.getName()), Level.INFO, 100);
		
		int i = 0;
		while(i < args.length) {
			if (args[i].equals("-control")) {
				i++;
				String password = args[i++];
				int port = Integer.parseInt(args[i++]);
				s.addService(new Control(s, password), port);
			} else {
				//Otherwise start a named service on the specified port
				
				String serviceName = args[i++];
				Class serviceClass = Class.forName(serviceName);
				Service service = (Service)serviceClass.newInstance();
				int port =Integer.parseInt(args[i++]);
				s.addService(service, port);
			}
			
		}
	}
	
	catch (Exception e) {
		System.err.println("Server: " + e);
		System.err.println("Usage: java Server " + "[-control <password> <port>]" + "[<servicename> <port> ...]");
		
		System.exit(1);
	}

}

	Map services;					// variable for hastable
	Set connections;				// Set for connections
	int maxConnections;				// concurrent connection list limit
	ThreadGroup threadGroup;		// the threadgroup 
	
	PrintWriter logStream;			// maintain log table through java.util.logging API
	Logger logger;					// logger dest
	Level loglevel;
	private int port;
	private Service service;
	private boolean stop;


	/**
	 * Server Constructor
	 */
	public Server(OutputStream logStream, int maxConnections) {
		
		this(maxConnections);
		setLogStream(logStream);
		log("Starting Server");
	}
	
	public Server(Logger logger, Level LogLevel, int maxConnections) {
		
		this(maxConnections);
		setLogger(logger, LogLevel);
		log("Starting server");
		
	}
	
	public Server(int maxConnections) {
		threadGroup = new ThreadGroup(Server.class.getName());
		this.maxConnections = maxConnections;
		services = new HashMap();
		connections = new HashSet(maxConnections);
	}
	
	public synchronized void setLogStream(OutputStream out) {
		if (out != null) logStream = new PrintWriter(out);
		else logStream = null;
	}

	public synchronized void setLogger(Logger logger, Level level) {
		this.logger = logger;
		this.loglevel = level;
	}
	
	//Log String writer
	
	protected synchronized void log(String s) {
		if (logger != null) logger.log(loglevel, s);
		if (logStream != null) {
			logStream.println("[" + new Date() + "]" + s);
			logStream.flush();
		}
	
	}
	protected void log(Object o) { log(o.toString());
	
}

public synchronized void addService(Service service, int port)
 throws IOException {
	
	Integer key = new Integer(port);
	// check whether a service is already on the port
	if(services.get(key) != null)
		throw new IllegalArgumentException("Port" + port + " Is already in use");
	
	Listener listener = new Listener(threadGroup, port, service);
	services.compute(key, (BiFunction) listener);
	
	log("Starting service " + service.getClass().getName() + " on Port " + port);
	
	listener.start();
	
}


public synchronized void removeService(int port) {
	
	Integer key = new Integer(port);
	
	final Listener listener = (Listener) services.get(key);
	
	if (listener == null) return;
	
	listener.pleaseStop();
	services.remove(key);
	
	log("Stopping service " + listener.service.getClass().getName() + " on port " + port);
}

// this thread subclass is "Listener"

public class Listener extends Thread {
	
	public Listener(ThreadGroup threadGroup, int port2, Service service2) {
		// TODO Auto-generated constructor stub
	}
	public void pleaseStop() {
		// TODO Auto-generated method stub
		
	}
	ServerSocket listen_socket;
	int port; 
	Service service;
	volatile boolean stop = false;

}

public void Listener(ThreadGroup group, int port, Service service) throws IOException {
	super(group, "Listener: " + port);
	ServerSocket listen_socket = new ServerSocket(port);
	listen_socket.setSoTimeout(5000);
	this.port= port;
	this.service = service;
	
}

public void pleaseStop() {
	this.stop = true;
	this.interrupt();
	try { PrintStream listen_socket;
	listen_socket.close(); }
	catch(IOException e) {}
	
	
	
}

private void interrupt() {
	// TODO Auto-generated method stub
	
}

public void run() {
	
	while(!stop) {
		try {
			
			ServerSocket listen_socket;
			Socket client = listen_socket.accept();
			addConnection(client, service);
		}
			catch (InterruptedIOException e) {}
			catch (IOException e ) {log(e); }
		}
	
	}


protected synchronized void addConnection(Socket s, Service service) {
	
	Hashtable<Object, Object> connection;
	if (connection.size() >= maxConnections) {
		
		try {
			PrintWriter out = new PrintWriter(s.getOutputStream());
			out.print("Connection refused); " +  "the server is busy; please try again later. \r\n");
			out.flush();
			s.close();
			log("Connection refused to " + s.getInetAddress().getHostAddress() + ": " + s.getPort() + ": Max connections reached." );
		} catch (IOException e) {log(e); }
			
	}
	else {
		     Connection c = new Connection(s, service);
		     
		     ((Set) connection).add(c);
		     
		     ServerSocket x;
			log("Connected to " + x.getInetAddress().getHostAddress() + ": " + s.getPort() + " : max connections reached." );
		     
		     
	} catch (IOException e ) {log(e);}
	
	}
		else {
	
		Connection c = new Connection (s, service);
		connection.add(c);
		
		log("Connected to " + s.getInetAddress().getHostAddresss() + ": " + s.getPort() + " on port " + s.getLocalPort() + "for service " +
		service.getClass().getName());
		
		c.start();
	}
}

protected synchronized void endConnection(Connection c) {
	connection.remove(c);
	log("Connection to" + c.client.getInetAddress().getHostAddress() + ":" + c.client.getPort() + " closed. ");
	
}

	public synchronized void setMaxConnections(int max) {
		maxConnections = max;
		}

public synchronized void displayStatus (PrintWriter out) {

	Iterator keys = services.keySet().iterator();
	while(key.hasNext()) {
	Integer port = (Integer) keys.next();
	Listener listener = (Listener) services.get(port);
	out.print("Service " + listener.service.getClass().getName() + "On port " + port + "\r\n");
}
	
	out.print("MAX Connections: " + maxConnections + " \r\n");
	
	Iterator conns = connections.iterator();
	 while(conns.hasNext()) {
		 Connection c = (Connection)conns.next();
		 out.print( "Connected to " + c.client.getInetAddress().getHostAddress() + " :" + c.client.getPort() + " On Port " + c.client.getLocalPort()
		  + "for Service " + c.service.getClass().getName() + "\r\n" );
	 }
}

//Class thread that handles individual connections between client and Service

public class Connection extends Thread {
	
	Socket client;
	Service service;
	
public Connection(Socket client, Service Service) {
	super("Server.Connection: " + client.getInetAddress().getHostAddress() + " :" + client.getPort());
	this.client = client;
	
	this.service = service;
}

/*
 * this is the body of each and every connection thread, it passes the client input and output streams to the serve() method of the specificed 
 * object. that method is responsible for reading from and writing to those streams to provide actual service.
 */

public void run() {
	
	try {
		InputStream in = client.getInputStream();
		OutputStream out = client.getOutputStream();
		service.serve(in, out);
		
	}
	catch (IOException e ) { log(e); }
	finally {endConnection(this); }
	
	
}

public interface Service {
	
	public void serve(InputStream in, OutputStream out) throws IOException;
	
}

public static class Time implements Service {
	
	public void serve(InputStream i, OutputStream 0 ) throws IOException {
		PrintWriter out = new PrintWriter(0);
		out.print(new Date() + "\r\n");
		out.close();
		i.close();
	}
}

public static class Reverse implements Service {
	
	public void serve(InputStream i, Output 0) throws IOException {
		
		BufferedReader in = new BufferedReader(new InputStreamReader(i));
		PrintWriter out = new PrintWriter(new OututStreamReader(i)));
		out.print("Welcome to the line reversal server. \r\n");
		out.print("Enter lines. End with a '.' on a line by itself. \r\n");
		
		for(;;) {
			out.print("> " );
			out.flush();
			String line = in.readLine();
			if ((line == null) || line.contentEquals(".")) break;
			
			for (int j = line.length() -1; j >=0; j--)
				out.print("\r\n");
		}
	out.close();
	in.close();
	}
}

public static class HTTPMirror implements Service {
	public void serve (InputStream i, OutputStream o) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(i));
		PrintWriter out = new PrintWriter(int);
		out.print("HTTP/1.0 200\r\n");
		out.print("Content-Type: text/plain\r\n\r\n");
		String line;
		while((line = in.readLine()) != null) {
			if (line.length() == 0) break;
			out.print(line + "\r\n");
		}
	out.close();
	in.close();
	
	}
	
}

public static class UniqueID implements Service {
	public int id=0;
	public synchronized int nextID() { return id ++;}
	public void serve(InputStream i, OutputStream 0) throws IOException {
		PrintWriter out = new PrintWriter(0);
		out.print("You are Client #: " + nextId() + "\r\n");
		out.close();
		i.close();
	}
	
}

/*implementation of a command based protocol that gives 
 * recognized prompts 
 * over operations
 * 
 */




public static class Control implements Service {
	
	Server server;
	String password;
	boolean connected = false;

public Control(Server server, String password) {
	this.server= server;
	this.password =password;
	
}

public void serve(InputStream i, OutputStream 0) throws IOException {
	BufferedReader in = new BufferedReader(new InputStreamReader(i));
	PrintWriter out = new PrintWriter(0);
	String line;
	
	boolean authorized = false;

	synchronized(this) {
		if (connected) {
			out.print("Only one control connection allowed. \r\n");
			out.close();
			return;
		}
		else connected = true;
	}

for (;; ) {
	out.print("> ");
	out.flush();
	line = in.readLine();
	if (line == null) break;
	
	try {
		
		StringTokenizer t = new StringTokenizer(line);
		if(!t.hasmoretokens()) continue;
		
		String command = t.nextToken().toLowerCase();
		
		boolean autorized;
		if (command.contentEquals("password")) {
			String p = t.nextToken();
			if(p.contentEquals(this.password)) {
				out.print("OK\r\n");
				authorized = true;
			}
			else out.print("Invalid Password\r\n");
			
		
		}
		
		else if (command.contentEquals("add")) {
			
			if (!authorized) out.print("Password Required\r\n");
			else {
			
				String serviceName = t.nextToken();
				Class serviceClass = Class.forName(serviceName);
				Service service;
				try {
					service = (Service)serviceClass.newInstance();
				
				}
			catch (NoSuchMethodError e) {
				throw new IllegalArgumentException(
						"Service must have a" + "no-argument constructor");
				
			}
				int port= Integer.parseInt(t.nextToken());
				server.addService(service, port);
				out.print("Service Added \r\n");
				
			}
		}
		
		else if (command.equals("Remove")) {
			if (!authorized) out.print("Password Required\r\n");
			else {
				int port = Integer.parseInt(t.nextToken());
				server.removeService(port);
				out.print("Service Removed\r\n");
			}
		}
		else if (command.contentEquals("max"))
			if(!autorized) out.print("Password Required\r\n");
		int max;
		server.setMaxConnections(max);
		out.print("Max connections changed\r\n"); }
}
		
		else if (command.equals(command.equals("status")) {
			if (!authorized) out.print("password required\r\n");
			else server.displayStatus(out);
		}
		else if (command.equals("help")) {
			out.print("Commands:\r\n" +
					"\tpassword <password>\r\n" +
					"\tadd <service> <port> \r\n" +
					"\tremove <port> \r\n" +
					"\tmax <max-connections> \r\n" +
					"\tstatus\r\n" +
					"\thelp\r\n" +
					"tquit\r\n" );
					
				}
		
		else if (command.equals("quit")) break;
		else out.print("Unrecognized Command\r\n");
		}

		catch (Exception e) {
			out.print("Error while parsing or executing command:\r\n " + e + "\r\n");
	}
}

		connected = false;
		out.close();
		in.close();
		
		}		
	}		
}

