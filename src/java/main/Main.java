package main;

import java.awt.*;
import java.awt.Dialog.ModalityType;

import javax.swing.*;

public class Main {
	
	public static void main(String[] args) {
		
		setupGUI();
		
	}
	
	private static void setupGUI() {

		JDialog diag = new JDialog();
		diag.setTitle("JAZN");
		diag.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		diag.setSize(600,400);
		diag.setModal(true);
		diag.setModalityType(ModalityType.APPLICATION_MODAL);
		diag.setVisible(true);

	}


}
