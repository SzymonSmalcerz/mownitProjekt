package com.smalcerz.esperMownit.frame;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class Display{
	
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	private static Display instance = new Display();
	
	
	private JFrame mainFrame;
	private TextArea textArea;
	private Button clearButton;
	
	
	private Display() {
		this.setFrame();
	};
	
	private void setFrame() {
		this.mainFrame = new JFrame("szymcio clickerBot");
		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainFrame.setSize(600,500);
		this.mainFrame.setMinimumSize(new Dimension(1000,600));
		this.mainFrame.setMaximumSize(new Dimension(1000,600));
		this.mainFrame.setResizable(false);
		this.mainFrame.setLayout(null);
		this.mainFrame.pack();
		this.mainFrame.setVisible(true);
		this.mainFrame.setState(Frame.ICONIFIED);
		this.mainFrame.setState(Frame.NORMAL);
		
		this.textArea = new TextArea();
		this.textArea.setSize(800, 520);
		this.textArea.setLocation(40,40);
		this.textArea.setEditable(false);
		
		this.clearButton = new Button("clear");
		this.clearButton.setSize(100, 40);
		this.clearButton.setLocation(880,80);
		this.clearButton.addActionListener(new ActionListener() { 
			
			public void actionPerformed(ActionEvent e) {
				textArea.setText("\n");;
			}
		});
		
		this.mainFrame.add(this.textArea);
		this.mainFrame.add(this.clearButton);
	}
	
	public void append(String str) {
		this.textArea.append(str);
	}
	
	public void appendCritical(String str) {
//		this.textArea.setForeground(Color.RED);
		this.textArea.append(str);
//		this.textArea.setForeground(Color.BLACK);
	}
	public static Display getInstance() {
		return instance;
	}

}
