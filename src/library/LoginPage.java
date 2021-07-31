
package library;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;


public class LoginPage extends JFrame 
{
	private static JPanel mainPanel;
	
	private JPanel subPanel, titlePanel, contentPanel;
	
	private JLabel titleLabel_img1,titleLabel_img2,titleLabel;
	
	private String driverUrl = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost/librarymanagementsystem";
	private String username = "root";
	private String password = "root";
	public static Connection con;
	
	LoginPanel loginPanel ;
	
	public LoginPage() 
	{	
		create_UI();	
	}
	
	
	void create_UI()
	{
		create_connection();
		create_mainPanel();
		create_subPanel();
		create_titlePanel();
		create_contentPanel();
		loginPanel = new LoginPanel(contentPanel);		
		
		setContentPane(mainPanel);
		setVisible(true);
		setResizable(true);
		setTitle("Library Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	
	public void create_mainPanel() 
	{
		
		mainPanel = new JPanel(new GridBagLayout());
		mainPanel.setBackground(new Color(255, 255,102));
		
		mainPanel.validate();
		mainPanel.repaint();
	}
	public void create_subPanel()
	{
		subPanel = new JPanel(new GridBagLayout());
		Border blackline = BorderFactory.createLineBorder(Color.BLACK,2);
		subPanel.setBackground(Color.WHITE);
		subPanel.setBorder(blackline);
		mainPanel.add( subPanel, new GridBagConstraints( 0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 130, 10, 130), 0, 0));
	}
	public void create_titlePanel() 
	{
		titlePanel = new JPanel(new GridBagLayout());
		
		Border borderline = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK);
		titlePanel.setBorder(borderline);
		titlePanel.setBackground(Color.WHITE);
		
		titleLabel_img1 = new JLabel(new ImageIcon("images/library_logo3.png"));
		titleLabel_img1.setBackground(Color.WHITE);
		
		titleLabel = new JLabel("Digital Library");
		titleLabel.setForeground(Color.BLACK);
		titleLabel.setFont(new Font("Verdana",Font.BOLD, 40));
				
		titleLabel_img2 = new JLabel(new ImageIcon("images/library_logo4.png"));
		titleLabel_img2.setBackground(Color.WHITE);
		
		titlePanel.add(titleLabel_img1, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		titlePanel.add(titleLabel, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		titlePanel.add(titleLabel_img2, new GridBagConstraints(2, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		
		subPanel.add(titlePanel,new GridBagConstraints( 0, 0, 1, 1, 1, 0.001, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
	}
	
	public void create_contentPanel() 
	{
		contentPanel = new JPanel(new GridBagLayout());
		contentPanel.setBackground(Color.WHITE);
		subPanel.add(contentPanel,new GridBagConstraints( 0, 1, 1, 1, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 0), 0, 0));
	}
	
	static void empty_mainPanel()
	{
		mainPanel.removeAll();
		
		mainPanel.revalidate();
		mainPanel.repaint();
	}
	
	public void create_connection() 
	{
		
		try 
		{
			//Class.forName(driverUrl);
			con = DriverManager.getConnection(url,username,password);
			System.out.print("Connected to Database...");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}