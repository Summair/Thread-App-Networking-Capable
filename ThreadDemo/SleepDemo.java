/* Following is an example of a single controlled Thread
 * more script is commented out. Please *Vim editor to edit however you need
 * 
 * CPU interleaving Patterns, scheduling policy dependent
 * 
 * M.Brohi
 */




public class SleepDemo {

	public static void main(String args[]) {
		
		for(;;) {
			
			System.out.println("Local date and time : " + new java.util.Date());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ie) {}
			
		}
		
		
	}
	
	
	
}

/*
 * 	public class SimpleThread extends Thread {
 *
 *   public void run() {
     
     for (int i = 0; i <4; i ++)
     System.out.println( " In myThread: " +i );
     }
 *	
 *		public static void main (String args[]) {
 *		SimpleThread st = new SimpleThread();
 *			st.start();
 *		for (int i= 0; i<6; i++)
 *		System.out.println("In main thread: " +i);
 *		}
 *  }
 */
    
    
 