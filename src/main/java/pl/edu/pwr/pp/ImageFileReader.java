package pl.edu.pwr.pp;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class ImageFileReader {

	/**
	 * Metoda czyta plik pgm i zwraca tablicę odcieni szarości.
	 * @param fileName nazwa pliku pgm
	 * @return tablica odcieni szarości odczytanych z pliku
	 * @throws URISyntaxException jeżeli plik nie istnieje
	 */
	public BufferedImage readPgmFile(String fileName) throws URISyntaxException {
		int columns = 0;
		int rows = 0;
		BufferedImage image = null;

		Path path = this.getPathToFile(fileName);
		
		try (BufferedReader reader = Files.newBufferedReader(path)) {
		
			if (!reader.readLine().equals("P2")) {
				throw new URISyntaxException("linia pierwsza", "Brak znaku P2 w lini pierwszej");
			}

			if (reader.readLine().charAt(0) != '#') {
				throw new URISyntaxException("linia pierwsza", "Brak znaku P2 w lini pierwszej");
			}
			
			Scanner num = new Scanner(reader.readLine());
			if (num.hasNextInt()) {
				columns = num.nextInt();
			} else {
				num.close();
				throw new URISyntaxException("linia trzecia", "blad liczby kolumn");
			}
			if (num.hasNextInt()) {
				rows = num.nextInt();
			} else {
				num.close();
				throw new URISyntaxException("fourthLine", "blad liczby wierszy");
			}

			if (!reader.readLine().equals("255")) {
				num.close();
				throw new URISyntaxException("linia czwarta", "nieprawidlowa liczba odcieni");
			}
			
			num.close();

			// inicjalizacja tablicy na odcienie szarości
			image = new BufferedImage(columns, rows, BufferedImage.TYPE_BYTE_GRAY);
			WritableRaster raster = image.getRaster();
			// kolejne linijki pliku pgm zawierają odcienie szarości kolejnych
			// pikseli rozdzielone spacjami
			String line = null;
			int currentRow = 0;
			int currentColumn = 0;
			while ((line = reader.readLine()) != null) {
				String[] elements = line.split(" ");
				for (int i = 0; i < elements.length; i++) {
					
					raster.setSample(currentColumn, currentRow, 0, Integer.parseInt(elements[i]));
					currentColumn++;
					currentRow += (int) currentColumn/columns;
					currentColumn %= columns;
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	private Path getPathToFile(String fileName) throws URISyntaxException {
		URI uri = ClassLoader.getSystemResource(fileName).toURI();
		return Paths.get(uri);
	}
	
}
