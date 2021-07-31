package library;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

class AdminMenuPanel extends JPanel implements ActionListener
{
	int adminID;
	
	JLabel adminMenuLabel;

	JButton addPublicationButton,addBookButton,bookReportButton,addBranchButton,addStudentButton,studentReportButton,issueBookButton,issueReportButton,returnBookButton,penaltyButton,logoutButton;
	
	private JPanel contentPanel,informationPanel;
	
	Font boldFont1 = new Font("", Font.BOLD, 20);
	Font boldFont2 = new Font("", Font.BOLD, 15);
	Font plainFont = new Font("", Font.PLAIN, 15);

	
	AdminMenuPanel(JPanel contentPanel, InformationPanel informationPanel,int ID)
	{
		adminID = ID;
		
		this.contentPanel = contentPanel;
		this.informationPanel = informationPanel;
		
		setLayout(new GridLayout(0, 1));
		setBackground(Color.RED);
		setBorder(new EmptyBorder(-7, 0, -7, 0));
		setBorder(new MatteBorder(-6, 0, -6, 1, Color.BLACK));

		
		design_adminMenuPanel();
		add_adminMenuPanel();
		
	}
	
	
	void design_adminMenuPanel() 
	{
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		
		adminMenuLabel = new JLabel("DASHBOARD", JLabel.CENTER);
		adminMenuLabel.setFont(boldFont2);
		adminMenuLabel.setForeground(Color.RED);
		adminMenuLabel.setBackground(Color.YELLOW);
		adminMenuLabel.setOpaque(true);
		adminMenuLabel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));
		
		addPublicationButton = new JButton("ADD PUBLICATION");
		buttons.add(addPublicationButton);
		addPublicationButton.addActionListener(this);
		
		addBookButton = new JButton("ADD BOOK");
		buttons.add(addBookButton);
		addBookButton.addActionListener(this);
		
		bookReportButton = new JButton("BOOK REPORT");
		buttons.add(bookReportButton);
		bookReportButton.addActionListener(this);
		
		addBranchButton = new JButton("ADD BRANCH");
		buttons.add(addBranchButton);
		addBranchButton.addActionListener(this);
		
		addStudentButton = new JButton("ADD STUDENT");
		buttons.add(addStudentButton);
		addStudentButton.addActionListener(this);
		
		studentReportButton = new JButton("STUDENT REPORT");
		buttons.add(studentReportButton);
		studentReportButton.addActionListener(this);

		issueBookButton = new JButton("ISSUE BOOK");
		buttons.add(issueBookButton);
		issueBookButton.addActionListener(this);

		issueReportButton = new JButton("ISSUE REPORT");
		buttons.add(issueReportButton);
		issueReportButton.addActionListener(this);

		returnBookButton = new JButton("RETURN BOOK");
		buttons.add(returnBookButton);
		returnBookButton.addActionListener(this);

		penaltyButton = new JButton("PENALTY");
		buttons.add(penaltyButton);
		penaltyButton.addActionListener(this);
		
		logoutButton = new JButton("LOGOUT");
		buttons.add(logoutButton);
		logoutButton.addActionListener(this);
		
		
		for( JButton button : buttons)
		{
			
			button.setFont(boldFont2);
			button.setBackground(Color.GREEN);
			button.setForeground(Color.BLACK);
				
		}
		
		
		add(adminMenuLabel);
		add(addPublicationButton);
		add(addBookButton);
		add(bookReportButton);
		add(addBranchButton);
		add(addStudentButton);
		add(studentReportButton);
		add(issueBookButton);
		add(issueReportButton);
		add(returnBookButton);
		add(penaltyButton);
		add(logoutButton);
		
	}
	
	
	void add_adminMenuPanel()
	{
		contentPanel.add( this, new GridBagConstraints(0, 0, 1, 1, 0.001, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.VERTICAL, new Insets( 0, 0, 0, 0), 50, 50));
	}
	
	void discard_adminMenuPanel() 
	{
		setVisible(false);
	}


	@Override
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == addPublicationButton )
		{
			
			((InformationPanel) informationPanel).design_addPublication();
			
		}
		
		
		if(ae.getSource() == addBookButton )
		{
			((InformationPanel) informationPanel).design_addBook();
		}
		
		if(ae.getSource() == bookReportButton )
		{
			((InformationPanel) informationPanel).design_bookReport();
		}
		
		if(ae.getSource() == addBranchButton)
		{
			((InformationPanel) informationPanel).design_addBranch();
		}

		if(ae.getSource() == addStudentButton)
		{
			((InformationPanel) informationPanel).design_addStudent();
		}
		
		if(ae.getSource() == studentReportButton)
		{
			((InformationPanel) informationPanel).design_studentReport();
		}
		if (ae.getSource() == issueBookButton ) 
		{
			((InformationPanel) informationPanel).design_issueBook();

		}
		if (ae.getSource() == issueReportButton) 
		{
			((InformationPanel) informationPanel).design_issueReport();

		}
		if (ae.getSource() == returnBookButton) 
		{
			((InformationPanel) informationPanel).design_returnBook();

		}
		
		if (ae.getSource() == penaltyButton) 
		{
			((InformationPanel) informationPanel).design_penalty();

		}
		
		if (ae.getSource() == logoutButton) 
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
