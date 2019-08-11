import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;

public class ServerSocketChannelTimeServer {

	public static void main(String[] args) {
	
		System.out.println("Time Server Started");
		try {
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.socket().bind(new InetSocketAddress(5000));
			while(true) {
			 System.out.println("Waiting  for Request .....");
			 SocketChannel socketChannel = serverSocketChannel.accept();
			 if (socketChannel != null) {
				 String dateAndTimeMessage = " Date:  " + new Date(System.currentTimeMillis());
			 ByteBuffer buf = ByteBuffer.allocate(64);
			 buf.put(dateAndTimeMessage.getBytes());
			 buf.flip();
			 while(buf.hasRemaining()) {
				 socketChannel.write(buf);
			 }
			 System.out.println("Sent: " + dateAndTimeMessage);
			 }
			
			}
			
	
	}catch (IOException ex) {
	
	}
	}
}
