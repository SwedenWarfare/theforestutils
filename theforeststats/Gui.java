package me.jerk.theforeststats;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Gui implements ActionListener {
	JButton addBtn = new JButton("Update current");
	static String[] items = new String[15];
	static int[] colItems = new int[15];
	int sel = 0;
	boolean bool = (colItems[sel] == 1);
	String isColTxt = "Is Collected: "+bool;
	JLabel isCol = new JLabel(isColTxt);
	JComboBox itemsList;
	
	JTextField changeCol = new JTextField();
	JButton updateFile = new JButton("Update File");
	
	
	static JTextArea txtArea = new JTextArea();
	JScrollPane scroll = new JScrollPane(txtArea);
	
	JButton saveFile = new JButton("Save file");
	
	static String CURRFILE = System.getProperty("user.dir")+"/";
	
	public Gui() {
		
		readFile();	
		todoFile();
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JPanel updatePnl = new JPanel();
		JPanel itemMaster = new JPanel();
		JPanel todoPanel = new JPanel();
		
		
		itemMaster.setLayout(new GridLayout(2,0));
		itemMaster.add(panel);
		itemMaster.add(updatePnl);
		
		todoPanel.setLayout(new GridLayout(2,0));
		todoPanel.add(scroll);
		todoPanel.add(saveFile);
		
		JTabbedPane pane = new JTabbedPane();
		pane.add("Items",itemMaster);
		pane.add("Todo",todoPanel);
		
		itemsList= new JComboBox<String> (items);
		
		
		itemsList.addActionListener(this);
		addBtn.addActionListener(this);
		updateFile.addActionListener(this);
		saveFile.addActionListener(this);
		
		
		panel.setLayout(new GridLayout(2,0));
		updatePnl.setLayout(new GridLayout(3,0));
		frame.setLayout(new GridLayout(1,0));
		
		panel.add(itemsList);
		panel.add(isCol);
		updatePnl.add(changeCol);
		updatePnl.add(addBtn);
		updatePnl.add(updateFile);
		
		frame.setTitle("Utils for The Forest");
		frame.add(pane);
		frame.setSize(300, 200);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 
	}
	public static void todoFile() {
		try {
		      File myObj = new File(CURRFILE+"Todo.txt");
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        txtArea.append(data+"\n");
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
	}
	public static void readFile() {
		try {
		      File myObj = new File(CURRFILE+"info.txt");
		      Scanner myReader = new Scanner(myObj);
		      int i = 0;
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        
		        String[] curr = data.split(":");
		        items[i] = curr[0];
		        colItems[i] = Integer.parseInt(curr[1].replaceFirst(" ", ""));
		        i++;
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addBtn) {
			if (changeCol.getText().isEmpty())
				return;
			int i = Integer.parseInt(changeCol.getText());
			if (i > 1 || i <= -1)
				System.out.println("Tesyt");
			colItems[sel] = i;
		}

		else if (e.getSource() == updateFile) {
			try {
				FileWriter myWriter = new FileWriter(CURRFILE+"info.txt");
				
				for (int i = 0; i < items.length; i++) {

					myWriter.write(items[i] + ": "+colItems[i]+"\n");
					

				}
				myWriter.close();
			} catch (IOException ex) {
				System.out.println("An error occurred.");
				ex.printStackTrace();
			}
		}
		else if(e.getSource() == saveFile) {
			try {
				FileWriter myWriter = new FileWriter(CURRFILE+"Todo.txt");
				myWriter.write(txtArea.getText());
				myWriter.close();
			} catch (IOException ex) {
				System.out.println("An error occurred.");
				ex.printStackTrace();
			}
		}
		
		else {
			JComboBox cb = (JComboBox) e.getSource();
			sel = cb.getSelectedIndex();
			bool = (colItems[sel] == 1);
			isColTxt = "Is Collected: " + bool;
			isCol.setText(isColTxt);

		}
	}
}
