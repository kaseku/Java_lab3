package pl.edu.pwr.gui;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.SystemColor;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.awt.event.ActionEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import pl.edu.pwr.pp.ImageConverter;
import pl.edu.pwr.pp.ImageFileWriter;


public class JimpApplication {

	private JFrame mainApp;
	private ImageComponent imageComponent;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JimpApplication window = new JimpApplication();
					window.mainApp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JimpApplication() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainApp = new JFrame();
		mainApp.setTitle("ASCIIArt");
		mainApp.setBounds(100, 100, 441, 300);
		mainApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainApp.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panel.setBounds(10, 11, 120, 240);
		mainApp.getContentPane().add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.activeCaption);
		panel_1.setBounds(140, 11, 272, 240);
		mainApp.getContentPane().add(panel_1);

		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 100 };
		gbl_panel.rowHeights = new int[] { 0, 0, 100 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel);
		
		imageComponent = new ImageComponent();
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 100;
		gbc_lblNewLabel.gridheight = 100;
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 1;
		panel_1.add(imageComponent, gbc_lblNewLabel);
		

		JButton readImage = new JButton("Wczytaj obraz");
		readImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JimpDialog window = new JimpDialog(imageComponent);
				window.dialogApp.setVisible(true);
				
			}
		});
		panel.add(readImage);
		
		JButton saveImage = new JButton("Zapisz do pliku");
		saveImage.setEnabled(true);
		saveImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				convertPgm();
			}
		});
		panel.add(saveImage);
		
		
	}
 
	private void convertPgm()
	{
		JFrame parentFrame = new JFrame();
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");  
		ImageConverter conventer = new ImageConverter();
		ImageFileWriter writer = new ImageFileWriter();
		 
		int userSelection = fileChooser.showSaveDialog(parentFrame);
		 
		if (userSelection == JFileChooser.APPROVE_OPTION && imageComponent.image != null) {

		    try(FileWriter fw = new FileWriter(fileChooser.getSelectedFile()+".txt")) {
		    	writer.saveToTxtFile(conventer.intensitiesToAscii(imageComponent.image), fw);
		    }catch (Exception ex) {
	            ex.printStackTrace();
	        }
		  
		}
	}
		
	class ImageComponent extends JComponent {
	
			
			private static final long serialVersionUID = -7789711715768822823L;
	
			private BufferedImage image;
			
			public void setImage(BufferedImage img) {
				this.image = img;
				
			}
			
			@Override
			public void paintComponent(Graphics g) {
	
				super.paintComponent(g);
				if (this.image != null) {
					g.drawImage(this.image, 0, 0, null);
				}
				
			}
	
	
			@Override
			public Dimension getMinimumSize() {
				return new Dimension(8, 8);
			}
	
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(240,240);
			}
	
		}
}
