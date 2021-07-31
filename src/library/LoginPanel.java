package library;

import java.sql.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

class LoginPanel extends JPanel implements ActionListener
{
		
	private JPanel loginBox_Panel;
	private JLabel loginImageLabel1,usernameLabel,passwordLabel;
	private JTextField usernameTextField;
	private JPasswordField passwordTextField;
	private JButton loginButton;
	private JRadioButton librarianRadioButton,studentRadioButton;
	
	AdminMenuPanel adminMenuPanel;
	StudentMenuPanel studentMenuPanel;
	InformationPanel informationPanel;
	JPanel contentPanel;
	
	Font boldFont = new Font("", Font.BOLD, 15);
	Font plainFont = new Font("", Font.PLAIN, 15);
	
	LoginPanel(JPanel contentPanel) 
	{
		this.contentPanel = contentPanel ; 
		
		setLayout(new GridBagLayout());
		setBackground(Color.WHITE);
		
		design_loginBoxPanel();
		design_loginPanel();
		
		contentPanel.add(this, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 0), 0, 0));

	}

	
	
	void design_loginBoxPanel() 
	{
		loginBox_Panel = new JPanel(new GridBagLayout());
		loginBox_Panel.setBackground(Color.WHITE);
		loginBox_Panel.setBorder(BorderFactory.createTitledBorder(
		         BorderFactory.createLineBorder(Color.BLACK,2), "LOGIN AREA", TitledBorder.CENTER, TitledBorder.TOP, new Font("", Font.BOLD, 18),Color.BLACK));
		
		usernameLabel = new JLabel("Username",JLabel.CENTER);
		usernameLabel.setFont(plainFont);
		passwordLabel = new JLabel("Password",JLabel.CENTER);
		passwordLabel.setFont(plainFont);
		
		usernameTextField = new JTextField(15);
		usernameTextField.setForeground(Color.BLACK.darker());
		usernameTextField.setFont(plainFont);
		usernameTextField.setBorder(BorderFactory.createTitledBorder(
		         BorderFactory.createLineBorder(Color.BLACK,1), "Username", TitledBorder.LEFT, TitledBorder.TOP, boldFont,Color.BLACK));
		passwordTextField = new JPasswordField(15);
		usernameTextField.setForeground(Color.BLACK.darker());
		passwordTextField.setFont(plainFont);
		passwordTextField.setBorder(BorderFactory.createTitledBorder(
		         BorderFactory.createLineBorder(Color.BLACK,1), "Password", TitledBorder.LEFT, TitledBorder.TOP, boldFont,Color.BLACK));
		
		ButtonGroup bg=new ButtonGroup();
		librarianRadioButton = new JRadioButton("Librarian",true);
		librarianRadioButton.setFont(boldFont);
		librarianRadioButton.setForeground(Color.BLACK);
		librarianRadioButton.setBackground(Color.WHITE);
		bg.add(librarianRadioButton);
		
		studentRadioButton = new JRadioButton("Student");
		studentRadioButton.setFont(boldFont);
		studentRadioButton.setForeground(Color.BLACK);
		studentRadioButton.setBackground(Color.WHITE);
		bg.add(studentRadioButton);
		
		loginButton = new JButton("LOGIN");
		loginButton.setForeground(Color.RED);
		loginButton.setBackground(Color.YELLOW);
		loginButton.setFont(boldFont);
		loginButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		loginButton.setMnemonic(KeyEvent.VK_ENTER);
		loginButton.addActionListener(this);
		
		loginBox_Panel.add(usernameTextField,new GridBagConstraints(0, 0, 4, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets( 30, 50, 0, 50), 0, 5));
		loginBox_Panel.add(passwordTextField,new GridBagConstraints(0, 2, 4, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets( 20, 50, 0, 50), 0, 5));
		loginBox_Panel.add(librarianRadioButton,new GridBagConstraints(0, 4, 2, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets( 10, 50, 20, 0), 0, 0));
		loginBox_Panel.add(studentRadioButton,new GridBagConstraints(2, 4, 2, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets( 10, 0, 20, 50), 0, 0));
		loginBox_Panel.add(loginButton,new GridBagConstraints(0, 6, 4, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 20, 20, 20), 100, 10));

	}
	
	void design_loginPanel() 
	{		
		loginImageLabel1 = new JLabel(new ImageIcon("images/loginPageImage3.jpg"));
		
		add(loginImageLabel1,new GridBagConstraints( 0, 0, 1, 1, 1, 1, GridBagConstraints.PAGE_START,GridBagConstraints.NONE,new Insets( 5, 0, 0, 0), 0, 0));
		add(loginBox_Panel,new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0), 100, 0));
		
	}
	
	void discard_loginPanel() 
	{
		//contentPanel.removeAll();
		this.setVisible(false);
	}


	void create_adminMenuPanel(int ID) 
	{
		adminMenuPanel = new AdminMenuPanel(contentPanel,informationPanel,ID);
	}
	
	void create_studentMenuPanel(int ID) 
	{
		studentMenuPanel = new StudentMenuPanel(contentPanel,informationPanel,ID);
	}
	
	void create_informationPanel(int ID) 
	{
		informationPanel = new InformationPanel(contentPanel,ID);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		boolean studentLoginFlag = false;
		boolean adminLoginFlag = false;
		String username = usernameTextField.getText().trim();
		String password = passwordTextField.getText().trim();
		String adminQuery = "select AID,Username,Password from admintable";
		String studentQuery = "select SID,Email,Password from studenttable";
		String profileName = "";
		int ID = 0;
		
		
		if(librarianRadioButton.isSelected())
		{
			usernameTextField.setText("");
			passwordTextField.setText("");
			
				try 
				{
					PreparedStatement pstmt = LoginPage.con.prepareStatement(adminQuery);
					ResultSet rs = pstmt.executeQuery();
					
					while (rs.next()) 
					{
						String uname = rs.getString("Username");
						String pass = rs.getString("Password");
						
						if(username.equals(uname) && password.equals(pass))
						{
							adminLoginFlag = true;
							ID = rs.getInt("AID");
						}
					}
					
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
			
		
		else if (studentRadioButton.isSelected()) 
		{
			usernameTextField.setText("");
			passwordTextField.setText("");
			
				try 
				{
					PreparedStatement pstmt = LoginPage.con.prepareStatement(studentQuery);
					ResultSet rs = pstmt.executeQuery();
					
					while (rs.next()) 
					{
						String uname = rs.getString("Email");
						String pass = rs.getString("Password");
						
						if(username.equals(uname) && password.equals(pass))
						{
							studentLoginFlag = true;
							ID = rs.getInt("SID");
						}
					}
					
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
			
		
		if (adminLoginFlag) 
		{
			discard_loginPanel();
			create_informationPanel(ID);
			create_adminMenuPanel(ID);
		}
		else if(studentLoginFlag)
		{
			discard_loginPanel();
			create_informationPanel(ID);
			create_studentMenuPanel(ID);
		}
		else 
		{
			JOptionPane.showMessageDialog(this, "Invalid Username and Password", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
}

