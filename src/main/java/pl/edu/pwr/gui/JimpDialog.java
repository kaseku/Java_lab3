package pl.edu.pwr.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import pl.edu.pwr.gui.JimpApplication.ImageComponent;
import pl.edu.pwr.pp.ImageFileReader;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;
import javax.imageio.ImageIO;


public class JimpDialog extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JFrame dialogApp;
	private JTextField fileField;
	private JTextField urlField;
	final JFileChooser fc = new JFileChooser();
	private BufferedImage img;


	/**
	 * Create the application.
	 */
	public JimpDialog(ImageComponent imageComponent) {
		initialize(imageComponent);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(ImageComponent imageComponent) {
		dialogApp = new JFrame();
		dialogApp.setBounds(100, 100, 360, 240);
		dialogApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dialogApp.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 324, 132);
		dialogApp.getContentPane().add(panel);
		panel.setLayout(null);
		
		fileField = new JTextField();
		fileField.setEditable(false);
		fileField.setBounds(10, 42, 304, 20);
		panel.add(fileField);
		fileField.setColumns(10);
		
		urlField = new JTextField();
		urlField.setBounds(10, 104, 304, 20);
		panel.add(urlField);
		urlField.setColumns(10);
		
		JRadioButton urlRadioButton = new JRadioButton("URL");
		urlRadioButton.setBounds(10, 69, 109, 23);
		panel.add(urlRadioButton);
		
		JRadioButton fileRadioButton = new JRadioButton("Dysk");
		fileRadioButton.setBounds(10, 12, 109, 23);
		panel.add(fileRadioButton);

		ButtonGroup group = new ButtonGroup();
		group.add(urlRadioButton);
		group.add(fileRadioButton);

		JButton chooseButton = new JButton("Wybierz plik");
		chooseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					chooseFile();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		chooseButton.setBounds(225, 12, 89, 23);
		panel.add(chooseButton);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 154, 324, 37);
		dialogApp.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewImage(imageComponent,fileRadioButton,urlRadioButton);
			}
		});
		okButton.setBounds(36, 5, 89, 23);
		panel_1.add(okButton);
		
		JButton cancelButton = new JButton("Wyjscie");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialogApp.dispose();
			}
		});
		cancelButton.setBounds(205, 5, 89, 23);
		panel_1.add(cancelButton);
	}
	
	private void chooseFile() throws URISyntaxException
	{
		FileNameExtensionFilter filter = new FileNameExtensionFilter( "Images","jpg", "pgm");
		fc.setFileFilter(filter);
		int returnVal = fc.showOpenDialog(JimpDialog.this);
		ImageFileReader pgmReader = new ImageFileReader();
		
		 if(returnVal == JFileChooser.APPROVE_OPTION) {
			 	fileField.setText( fc.getSelectedFile().getAbsolutePath());
				File file = fc.getSelectedFile();
				String name = file.getName();

				try {
					if(name.substring(name.lastIndexOf(".") + 1).equals("pgm"))
					{
						img = pgmReader.readPgmFile(name);
						
					}
					else
					{
						img = ImageIO.read(file);
						
					}
					
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		 }
	}
	
	private void loadImage() throws IOException {
       
		URL	url = new URL(urlField.getText().toString());
		img = null;
		
        try {
            img = ImageIO.read(url);

        } catch (Exception e) {
        	e.printStackTrace();
        	
        }
    }
	
	private void viewImage(ImageComponent imageComponent, JRadioButton fileRadioButton, JRadioButton urlRadioButton)
	{
		if(img != null && fileRadioButton.isSelected() )
		{

			imageComponent.setImage(img);
			imageComponent.repaint();
			dialogApp.dispose();

		}
		
		if(urlRadioButton.isSelected() )
		{

			try {
				loadImage();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			imageComponent.setImage(img);
			imageComponent.repaint();
			dialogApp.dispose();
		}

	}
}
