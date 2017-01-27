import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Semaphore;

public class Reader_Writer 
{
	private int max_lockers;
	private Semaphore lockers;
	
	public Reader_Writer(int c)
	{
		max_lockers = c;
		lockers = new Semaphore(max_lockers);
	}
	public class reader extends Thread
	{
		String name;
		public reader(String n)
		{
			name = n;
		}
		public void run()
		{
			try {
				System.out.println(name +" Start Reading");
				
				if(lockers.availablePermits() <= 0)
					System.out.println(name + " Waiting for locker");
				
				lockers.acquire();
				FileReader fr = new FileReader("test.txt");
				BufferedReader br = new BufferedReader(fr);
				String line;
				while((line = br.readLine()) != null)
					System.out.println(name + " Read: " + line);
				br.close();
				lockers.release();
				System.out.println(name +" Finished reading");
			}catch (InterruptedException e) {
				System.out.println("Process has been terminated");
			} catch (IOException e) {
				System.out.println("File wasn't work successfully");
			}
			
		}
	}
	
	public class writer extends Thread
	{
		String name;
		String input;
		public writer(String n,String i)
		{
			name = n;
			input = i;
		}
		public void run()
		{
			try {
				System.out.println(name + " Start writing");
				
				if(lockers.availablePermits() < max_lockers)
					System.out.println(name + " Blokced");
				
				lockers.acquire(max_lockers);
				
				FileWriter fr = new FileWriter("test.txt",true);
				BufferedWriter br = new BufferedWriter(fr);
				br.write(" " + input);
				System.out.println(name + " Writing now!");
				br.close();
				
				lockers.release(max_lockers);
				System.out.println(name + " Finished writing");
				
			} catch (InterruptedException e) {
				System.out.println("Process has been terminated");
			} catch (IOException e) {
				System.out.println("File wasn't work successfully");
			}
		}
	}
	
	
}
