import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class Main_Interface {

	public static void main(String[] args) throws InterruptedException, IOException
	{	
		int rC;
		int wC;
		String intialVal;
		Scanner input = new Scanner(System.in);
		ArrayList<Reader_Writer.reader> readers = new ArrayList<Reader_Writer.reader>();
		ArrayList<Reader_Writer.writer> writers = new ArrayList<Reader_Writer.writer>();
		
		System.out.print("Intial buffer content: ");
		intialVal = input.nextLine();
	
		FileWriter fr = new FileWriter("test.txt");
		BufferedWriter br = new BufferedWriter(fr);
		br.write(intialVal);
		br.close();
		
		System.out.print("Number of reader threads: ");
		rC = input.nextInt();
		System.out.print("Number of writer threads: ");
		wC = input.nextInt();
		
		Reader_Writer rw = new Reader_Writer(Comparison.min(rC,4));
		
		String[] readersName = new String[rC];
		String[] writersName = new String[wC];
		String[] writersData = new String[wC];
		
		System.out.println("Reader Threads:");
		for(int i = 0 ;i < rC;i++)
			readersName[i] = input.next();
		
		System.out.println("Writer Threads:");
		for(int i =0 ;i < wC;i++)
		{
			writersName[i] = input.next();
			writersData[i] = (input.nextLine()).trim();
		}
		System.out.println(writersData[0]);
		for(int i = 0 ; i < Comparison.max(rC,wC);i++ )
		{
			if(i < rC)
			{
				Reader_Writer.reader  r = rw.new reader(readersName[i]);
				r.start();
				readers.add(r);
			}
			
			if(i < wC)
			{
				Reader_Writer.writer w = rw.new writer(writersName[i],writersData[i]);
				w.start();
				writers.add(w);
			}
		}
		
		for(int i = 0 ; i < rC;i++)
			readers.get(i).join();
		for(int i = 0 ; i < wC;i++)
			writers.get(i).join();
	}

	
}
