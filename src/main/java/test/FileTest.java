package test;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileTest {
	public static void main(String[] args) throws IOException {	
//		File file = new File("c:" + File.separator);
//		System.out.println(file);
//		System.out.println(file.isDirectory());
//		System.out.println(file.isFile());
//		
//		boolean b = file.is
		
//		file.canRead()
//		file.canWrite();
		// File 권한(RWX)
		// 101 5 
		// 111 7
		// 100 4
//		Hidden
//		File file = new File("abcd");
//		System.out.println(file.exists());
//		System.out.println(file.createNewFile());
//		
//		File file2 = new File("abcde");
//		System.out.println(file2.exists());
//		System.out.println(file2.mkdir());
//		
//		// c:\\upload\2023\03\14
//		// test.txt
//		File file3 = new File("c:/upload",new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
//		file3.mkdirs();
//		File file4 = new File(file3, "test.txt");
//		file4.createNewFile();
//		
//		File file = new File("c:/users/TJ");
//		File[] files = file.listFiles();
//		for(File f : files) {
////			System.out.println(f);
//			System.out.print(new SimpleDateFormat("yyyy--MM-dd a HH:mm:ss").format(f.lastModified()));
//			if(f.isDirectory()) {
//				System.out.print("     <DIR>      ");
//				
//			}
//			if(f.isFile()) {
//				System.out.print("    " + f.length() + "bytes");
//			}
//			System.out.println("       " + f.getName());
//		}
		
		System.out.println(new File("c:/upload" ,"abcd.txt").getPath());
		
		
	}
}

