
/*Canonical hostname is a fully Qualified Domain Name, including top level domanins.
 * The values returned by this mehtod depend on several factors, including DNS server. 
 * The system provides information regarding entities on the network
 *M.Brohi
 */



import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Inet {

	static String URLAddress = "www.google.com";
	
	
	public static void main(String[] args) throws UnknownHostException {
		
		InetAddress name[] = InetAddress.getAllByName("www.google.com");
		
	 
		for(InetAddress element : name)
		{
			System.out.println(element);
			displayInetAddressInformation(element);
		}
	
	
	
	}
	
	
	public static  void displayInetAddressInformation(InetAddress address){
			System.out.println(address);
			System.out.println("CanonicalHostName: "  + address.getCanonicalHostName());
			System.out.println("HostName: " + address.getHostName());
			System.out.println("HostAddress: "  + address.getHostAddress());
	
			InetAddress[] addresses = null;
			try {
				addresses = InetAddress.getAllByName(URLAddress);
			} catch (UnknownHostException e) {
			
				e.printStackTrace();
			}
			for(InetAddress inetAddress : addresses) {
				try {
					if( inetAddress.isReachable(10000)) {
						System.out.println(inetAddress + " is Reachable");
					} else {
						System.out.print(inetAddress + " is not reachable");
					}
				} catch (IOException ex) {
					
				}
			}
			return;
	}
		
	
	
	
}
