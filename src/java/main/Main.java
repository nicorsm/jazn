package main;

import java.awt.*;
import java.awt.Dialog.ModalityType;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import model.impl.*;
import model.interfaces.*;
import sun.rmi.runtime.Log;

public class Main {
	
	
	
	public static void main(String[] args) {
		
		//setupGUI();
		Referee.getInstance().startMatch();
		
	}
	
/*	
	private static void setupGUI() {

		JFrame frame = new JFrame();
		frame.setTitle("JAZN");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,400);
		
		JPanel panel = new JPanel();
		panel.setBounds(15, 15, 580, 350);
		panel.setBackground(Color.GREEN);
		panel.setBorder(new TitledBorder("FORLI' CITY 0 - 0 CESENA UNITED"));
		
		BufferedImage image = ImageIO.read(this.getClass().getResource("field.jpg"));
		JLabel icon = new JLabel(new ImageIcon(image));
		panel.add(icon);
		
		frame.getContentPane().add(panel);

		frame.setVisible(true);
	}
*/	


}
