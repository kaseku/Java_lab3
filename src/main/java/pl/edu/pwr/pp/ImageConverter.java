package pl.edu.pwr.pp;

import java.awt.image.BufferedImage;

public class ImageConverter {

	/**
	 * Znaki odpowiadające kolejnym poziomom odcieni szarości - od czarnego (0)
	 * do białego (255).
	 */
	public static String INTENSITY_2_ASCII = "@%#*+=-:. ";

	/**
	 * Metoda zwraca znak odpowiadający danemu odcieniowi szarości. Odcienie
	 * szarości mogą przyjmować wartości z zakresu [0,255]. Zakres jest dzielony
	 * na równe przedziały, liczba przedziałów jest równa ilości znaków w
	 * {@value #INTENSITY_2_ASCII}. Zwracany znak jest znakiem dla przedziału,
	 * do którego należy zadany odcień szarości.
	 * 
	 * 
	 * @param intensity
	 *            odcień szarości w zakresie od 0 do 255
	 * @return znak odpowiadający zadanemu odcieniowi szarości
	 */
	public static char intensityToAscii(int intensity) {
		int przedzial = intensity*INTENSITY_2_ASCII.length()/256;
		//if(przedzial==10)return INTENSITY_2_ASCII.charAt(INTENSITY_2_ASCII.length()-1);
		
		return INTENSITY_2_ASCII.charAt((int) przedzial);
	}

	/**
	 * Metoda zwraca dwuwymiarową tablicę znaków ASCII mając dwuwymiarową
	 * tablicę odcieni szarości. Metoda iteruje po elementach tablicy odcieni
	 * szarości i dla każdego elementu wywołuje {@ref #intensityToAscii(int)}.
	 * 
	 * @param intensities
	 *            tablica odcieni szarości obrazu
	 * @return tablica znaków ASCII
	 */
	public static char[][] intensitiesToAscii(BufferedImage img) {
		char[][] tabZnakow = new char[img.getHeight()][img.getWidth()];
		
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				tabZnakow[i][j] = intensityToAscii(img.getRaster().getSample(j, i, 0));
			}
		}
		return tabZnakow;
	}

}
