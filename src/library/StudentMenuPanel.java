package library;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

class StudentMenuPanel extends JPanel implements ActionListener
{
	int studentID;
	
	JLabel studentMenuLabel,studentImageLabel,studentNameLabel;
	
	JPanel contentPanel,informationPanel,studentImagePanel;
	
	JButton myAccountButton,bookReportButton,penaltyReportButton,logoutButton;
	
	Font boldFont1 = new Font("", Font.BOLD, 20);
	Font boldFont2 = new Font("", Font.BOLD, 15);
	Font plainFont = new Font("", Font.PLAIN, 15);
	
	StudentMenuPanel(JPanel contentPanel, InformationPanel informationPanel,int ID) 
	{
		studentID = ID;
		
	 	this.contentPanel = contentPanel;
	 	this.informationPanel = informationPanel;
	 	
	 	setLayout(new GridBagLayout());
		setBackground(Color.WHITE);
		//setBorder(new EmptyBorder(-6, 0, -6, 0));
		setBorder(new MatteBorder(-6, 0, -6, 1, Color.BLACK));
		
		design_studentMenuPanel();
		add_studentMenuPanel();
	}
	
	
	
	void design_studentMenuPanel() 
	{
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		
		studentMenuLabel = new JLabel("DASHBOARD", JLabel.CENTER);
		studentMenuLabel.setFont(boldFont2);
		studentMenuLabel.setForeground(Color.RED);
		studentMenuLabel.setBackground(Color.YELLOW);
		studentMenuLabel.setOpaque(true);
		studentMenuLabel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));
		
		studentImageLabel = new JLabel();
		studentImageLabel.setSize(new Dimension(170, 170));
		studentImageLabel.setBorder(new LineBorder(Color.BLACK));
		
		ImageIcon icon = new ImageIcon("images/Neymar.jpg");
		Image modifiedImage = icon.getImage().getScaledInstance(studentImageLabel.getWidth(), studentImageLabel.getHeight(), Image.SCALE_SMOOTH);
		icon = new ImageIcon(modifiedImage);
		studentImageLabel.setIcon(icon);
		
		studentNameLabel = new JLabel("Shubham Desai");
		studentNameLabel.setFont(boldFont1);
		studentNameLabel.setForeground(Color.BLACK);
		
		studentImagePanel = new JPanel(new GridBagLayout());
		studentImagePanel.setBorder(new LineBorder(Color.BLACK));
		studentImagePanel.setBackground(Color.WHITE);
		studentImagePanel.add(studentImageLabel,new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		studentImagePanel.add(studentNameLabel, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		
		myAccountButton = new JButton("MY ACCOUNT");
		buttons.add(myAccountButton);
		myAccountButton.addActionListener(this);
		
		bookReportButton = new JButton("BOOK REPORT");
		buttons.add(bookReportButton);
		bookReportButton.addActionListener(this);
		
		penaltyReportButton = new JButton("PENALTY REPORT");
		buttons.add(penaltyReportButton);
		penaltyReportButton.addActionListener(this);
		
		logoutButton = new JButton("LOG OUT");
		buttons.add(logoutButton);
		logoutButton.addActionListener(this);
		
		
		for( JButton button : buttons)
		{
			
			button.setFont(boldFont2);
			button.setBackground(Color.GREEN);
			button.setForeground(Color.BLACK);
				
		}
		
		add(studentMenuLabel,new GridBagConstraints(0, 0, 1, 1, 1, 0.001, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 27));
		add(studentImagePanel,new GridBagConstraints(0, 1, 1, 1, 1, 0.001, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 21));
		add(myAccountButton,new GridBagConstraints(0, 2, 1, 1, 1, 0.001, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 21));
		add(bookReportButton,new GridBagConstraints(0, 3, 1, 1, 1, 0.001, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 21));
		add(penaltyReportButton,new GridBagConstraints(0, 4, 1, 1, 1, 0.001, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 21));
		add(logoutButton,new GridBagConstraints(0, 5, 1, 1, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 21));
		
	}
	
	
	void add_studentMenuPanel()
	{
		contentPanel.add( this, new GridBagConstraints(0, 0, 1, 1, 0.001, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.VERTICAL, new Insets( 0, 0, 0, 0), 50, 50));
	}



	@Override
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == myAccountButton)
		{
			((InformationPanel) informationPanel).design_myAccount();
		}
		
		if(ae.getSource() == bookReportButton)
		{
			((InformationPanel) informationPanel).design_studentBookReport();
		}
		
		if(ae.getSource() == penaltyReportButton)
		{
			((InformationPanel) informationPanel).design_studentPenalty();
		}
		
		if(ae.getSource() == logoutButton)
		{
			try 
			{
				LoginPage.con.close();
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			((InformationPanel) informationPanel).design_logout();
		}
		
	}
}
