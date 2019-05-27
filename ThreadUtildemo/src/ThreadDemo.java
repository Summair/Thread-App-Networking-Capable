/*
 * Demonstration of two process threads, The main() method is the initial method that is run by the 
 * this main method defines and starts two more threads and the three threads run at the same time.
 * NOTE extend to Thread from its own class and its run() method

	M.Brohi = Java thread example which could be extended to several server processes *Management of logs/unused volume maintenance
 */ 






public class ThreadDemo extends Thread {
	
	// 
	
public void run() { for (int i = 0; i < 5; i++) diffServerProcess();} // end of method  that override the run() method of thread




public static void main(String[] args) {
	
	ThreadDemo Process1 = new ThreadDemo();
	
	
	Thread  Process2 = new Thread(new Runnable() {
		public void run() { for (int i = 0; i < 5; i++)
			diffServerProcess();}
		});
	
	
	
	
	if (args.length >= 1)Process1.setPriority(Integer.parseInt(args[0]));
	if(args.length >= 2) Process2.setPriority(Integer.parseInt(args[1]));
	
	//Start the two processes running thread1 and thread2
	
	Process1.start();
	Process2.start();
		
	
	for(int i=0; i < 5; i++) diffServerProcess();
	
	
	
	try {
		Process1.join();
		Process2.join();
	} catch (InterruptedException e) {}
	
}

	static ThreadLocal numcalls = new ThreadLocal();
	
	static synchronized void diffServerProcess() {
		
		Integer n = (Integer) numcalls.get();
		if (n == null) n = new Integer(1);
		else n = new Integer(n.intValue()+ 1);
		numcalls.set(n);
		
		System.out.println(Thread.currentThread().getName() + ": " +n);
		
		for(int i = 0, j = 0; i < 1000000; i++ ) j += i;
		
		
		try {
			
			Thread.sleep((int)(Math.random()*100+1));
			
		}
	 catch (InterruptedException e) {}
		
	
		Thread.yield();
		
	
 }

}//end of class







