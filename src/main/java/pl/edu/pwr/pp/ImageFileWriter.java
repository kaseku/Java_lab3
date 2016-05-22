package pl.edu.pwr.pp;

//import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class ImageFileWriter {

	public void saveToTxtFile(char[][] ascii, FileWriter fw) throws IOException {

			for (int i = 0; i < ascii.length; i++) {
				for (int j = 0; j < ascii[i].length; j++) {
					fw.write(ascii[i][j]);
				}
				fw.write(System.getProperty( "line.separator" ));
			}
	
			fw.close();
		}
		// np. korzystając z java.io.PrintWriter
		// TODO Wasz kod
		
	}
	
