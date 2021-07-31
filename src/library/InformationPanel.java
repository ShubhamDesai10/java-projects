package library;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.mysql.cj.Query;

class InformationPanel extends JPanel 
{
	
	MyEventListener myEventListener = new MyEventListener(this);
	int ID;
	long millis = System.currentTimeMillis();
	Date date = new Date(millis);
	String bookPhotoAbsolutePathString,studentPhotoAbsolutePathString;
	String[] publicationList, branchList, studentList, bookList;
	
	JPanel contentPanel,welcomeTitlePanel,subInformationPanel;
	JLabel welcomeTitlePanelLabel,welcomeImageLabel;
	
	//Add Publication Button Fields
	JLabel publicationNameLabel,publicationConfirmLabel;
	JTextField publicationNameTextField;
	JButton addNewPublicationButton;
	
	//Add Book Button Fields
	JLabel bookNameLabel,bookDetailLabel,bookAuthorLabel,bookPublicationLabel,bookBranchLabel,bookPriceLabel,bookQuantityLabel,bookPhotoLabel,bookPhotoPathLabel;
	JTextArea bookDetailTextArea;
	JScrollPane bookDetailScrollPane,addBookPhotoScrollPane;
	JTextField bookNameTextField,bookAuthorTextField,bookPriceTextField,bookQuantityTextField;
	JComboBox bookPublicationComboBox,bookBranchComboBox;
	JButton addBookPhotoButton,addNewBookButton;
	
	//Book Report Button Fields
	String bookTableTitleString = "";
	JLabel selectBranchLabel, selectPublicationLabel, bookTableTitle;
	JComboBox selectBranchComboBox, selectPublicationComboBox;
	JButton viewBranchButton,viewPublicationButton,viewBookButton,bookBackButton;
	JTable bookTable;
	JScrollPane bookTableScrollPane;
	JPanel bookDetailsPanel,subBookReportPanel;
	
	//Add Branch Button Fields
	JLabel branchNameLabel,branchConfirmLabel;
	JTextField branchNameTextField;
	JButton addNewBranchButton;
	
	
	//Add Student Button Fields
	JLabel studentNameLabel,branchLabel,genderLabel,birthDateLabel,mobileLabel,addressLabel,cityLabel,pincodeLabel,
	studentPhotoLabel,emailLabel,passwordLabel,studentPhotoPathLabel;
	JTextField 	studentNameTextField,mobileTextField,cityTextField,pincodeTextField,emailTextField;
	JTextArea addressTextArea;
	JScrollPane addressScrollPane;
	JPasswordField passwordPasswordField;
	JComboBox branchComboBox,birthDateDateComboBox,birthDateMonthComboBox,birthDateYearComboBox;
	JRadioButton maleRadioButton,femaleRadioButton;
	JButton addStudentPhotoButton,addNewStudentButton;
	ButtonGroup genderButtonGroup;
	String days_31[] =  new String[31];
	String days_30[] =  new String[30];
	String days_29[] =  new String[29]; 
	String days_28[] =  new String[28];
	String month[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
	String year[] = new String[40];
	
	//Student Report Fields
	String studentTableTitleString = "";
	JLabel studentBranchReportLabel,studentNameReportLabel,studentTableTitle;
	JTextField studentNameReportTextField;
	JComboBox studentBranchReportComboBox;
	JButton viewBranchReportButton,viewStudentReportButton,viewStudentButton,studentBackButton;
	JTable studentTable;
	JScrollPane studentTableScrollPane;
	JPanel studentDetailsPanel,subStudentReportPanel;
	
	//Issue Book Fields
	JPanel bookDetailsIssueBookPanel,subBookDetailsIssueBookPanel;
	JLabel selectPublicationIssueBookLabel,selectBookIssueBookLabel,selectBranchIssueBookLabel,selectStudentIssueBookLabel,daysIssueBookLabel;
	JComboBox selectPublicationIssueBookComboBox,selectBookIssueBookComboBox,selectStudentIssueBookComboBox,selectBranchIssueBookComboBox;
	JButton selectIssueBookButton,issueBookButton;
	JTextField daysIssueBookTextField;
	
	
	//Return Book Fields
	JPanel bookDetailsReturnBookPanel;
	JLabel selectStudentReturnBookLabel,selectBookReturnBookLabel;
	JComboBox selectStudentReturnBookComboBox,selectBookReturnBookComboBox;
	JButton selectReturnBookButton,returnBookButton;
	
	
	//Penalty Fields
	JPanel bookDetailsPenaltyPanel;
	JLabel selectStudentPenaltyLabel,selectBookPenaltyLabel;
	JComboBox selectStudentPenaltyComboBox,selectBookPenaltyComboBox;
	JButton selectPenaltyButton,payNowButton;
	
	
	//My Account Fields
	JButton viewAccountButton,editAccountButton,changePasswordButton;
	JPanel myAccountPanel;
	JLabel myAccountPanelTitle;
	
	//My Book Report Fields
	JButton borrowBookReportButton,returnBookReportButton;
	
	
	//Customize Font
	Font boldFont1 = new Font("", Font.BOLD, 20);
	Font boldFont2 = new Font("", Font.PLAIN, 17);
	Font boldFont3 = new Font("", Font.PLAIN, 13);
	Font boldFont4 = new Font("", Font.BOLD, 13);
	Font boldFont5 = new Font("", Font.BOLD, 15);
	Font boldFont6 = new Font("", Font.PLAIN, 15);
	Font boldFont7 = new Font("", Font.BOLD, 17);
	
	//Color Codes
	Color darkGreen = new Color(0, 100, 0);
	
	InformationPanel(JPanel contentPanel,int ID) 
	{
		this.ID = ID;
		
		this.contentPanel = contentPanel;
		
		setLayout(new GridBagLayout());
		setBackground(Color.WHITE);
		setBorder(new EmptyBorder(0, -1, 0, -1));
		
		design_welcomeTitlePanel();
		design_subinformationPanel();
		design_informationPanel();
		add_informationPanelToContentPanel();
		
	}
	
	void design_welcomeTitlePanel()
	{
		
		welcomeTitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		welcomeTitlePanel.setBackground(Color.YELLOW);
		welcomeTitlePanel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));
		
		welcomeTitlePanelLabel = new JLabel("WELCOME TO LIBRARY SYSTEM");
		welcomeTitlePanelLabel.setFont(boldFont1);
		welcomeTitlePanelLabel.setForeground(Color.RED);
		
		welcomeTitlePanel.add(welcomeTitlePanelLabel);
		
	}
	
	void design_informationPanel()
	{
		
		add( welcomeTitlePanel, new GridBagConstraints(0, 0, 1, 1, 1, 0.001, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 5));
		add( subInformationPanel, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, new Insets( 0, 0, 0, 0), 0, 50));
	
	}
	
	void add_informationPanelToContentPanel() 
	{
		contentPanel.add( this, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 0), 0, 0));
	}
	
	
	void design_subinformationPanel() 
	{
		subInformationPanel = new JPanel(new GridBagLayout()) ;
		subInformationPanel.setBackground(new Color(153, 255, 153));
		subInformationPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		welcomeImageLabel = new JLabel(new ImageIcon("images/WelcomeImage1.png"));
		
		subInformationPanel.add( welcomeImageLabel, new GridBagConstraints( 0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		
	}
	
	
	
	void design_addPublication() 
	{	
		
		empty_subInformationPanel();
		
		welcomeTitlePanelLabel.setText("Add New Publication");	
		
		publicationNameLabel =new JLabel("Publication Name :");
		style_label(publicationNameLabel);
		
		publicationNameTextField = new JTextField(17);
		style_textField(publicationNameTextField);
		
		publicationConfirmLabel = new JLabel("");
		style_label(publicationConfirmLabel);
		publicationConfirmLabel.setFont(boldFont3);
		publicationConfirmLabel.setForeground(Color.BLUE);
		
		addNewPublicationButton = new JButton("Add Publication");
		style_button(addNewPublicationButton);
		addNewPublicationButton.addActionListener(myEventListener);
		
		
		subInformationPanel.add(publicationNameLabel, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0), 10, 5));
		subInformationPanel.add(publicationNameTextField, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0), 10, 5));
		subInformationPanel.add(publicationConfirmLabel, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets( -10, 0, 0, 0), 0, 0));
		subInformationPanel.add(addNewPublicationButton, new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0), 15, 10));
		
		
		subInformationPanel.revalidate();
		subInformationPanel.repaint();
		
	}
	
	
	
	void design_addBook() 
	{
		empty_subInformationPanel();
		
		welcomeTitlePanelLabel.setText("Add New Book");	
		
		get_list();
		
		bookNameLabel = new JLabel("Book Name :");
		style_label(bookNameLabel);
		
		bookDetailLabel = new JLabel("Detail :");
		style_label(bookDetailLabel);
		
		bookAuthorLabel = new JLabel("Author :");
		style_label(bookAuthorLabel);
		
		bookPublicationLabel = new JLabel("Publication :");
		style_label(bookPublicationLabel);
		
		bookBranchLabel = new JLabel("Branch :");
		style_label(bookBranchLabel);
		
		bookPriceLabel = new JLabel("Price :");
		style_label(bookPriceLabel);
		
		bookQuantityLabel = new JLabel("Quantity :");
		style_label(bookQuantityLabel);
		
		bookPhotoLabel = new JLabel("Book Photo :");
		style_label(bookPhotoLabel);
		
		bookNameTextField = new JTextField(15);
		style_textField(bookNameTextField);
		
		bookDetailTextArea = new JTextArea(4, 15);
		style_textArea(bookDetailTextArea);
		
		bookDetailScrollPane = new JScrollPane(bookDetailTextArea);
		
		bookAuthorTextField = new JTextField(15);
		style_textField(bookAuthorTextField);
		
		bookPriceTextField = new JTextField(15);
		style_textField(bookPriceTextField);
		
		bookQuantityTextField = new JTextField(15);
		style_textField(bookQuantityTextField);
		
		bookPublicationComboBox = new JComboBox(publicationList);
		style_comboBox(bookPublicationComboBox);
		bookPublicationComboBox.setMaximumRowCount(5);
		bookPublicationComboBox.setSelectedIndex(-1);
		bookPublicationComboBox.setPreferredSize(new Dimension(177, 25));
		
		
		bookBranchComboBox = new JComboBox(branchList);
		style_comboBox(bookBranchComboBox);
		bookBranchComboBox.setMaximumRowCount(5);
		bookBranchComboBox.setSelectedIndex(-1);
		bookBranchComboBox.setPreferredSize(new Dimension(177, 25));
		
		addBookPhotoButton = new JButton("Add Photo");
		style_button(addBookPhotoButton);
		addBookPhotoButton.setBackground(Color.BLUE);
		addBookPhotoButton.addActionListener(myEventListener);
		
		bookPhotoPathLabel = new JLabel();
		style_label(bookPhotoPathLabel);
		bookPhotoPathLabel.setFont(boldFont3);
		
		addNewBookButton = new JButton("Add Book");
		style_button(addNewBookButton);
		addNewBookButton.addActionListener(myEventListener);
		
		
		subInformationPanel.add(bookNameLabel, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets( 20, 0, 0, 0), 10, 5));
		subInformationPanel.add(bookNameTextField, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets( 20, 0, 0, 0), 10, 5));
		subInformationPanel.add(bookDetailLabel, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0), 10, 5));
		subInformationPanel.add(bookDetailScrollPane, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0), 8, 5));
		subInformationPanel.add(bookAuthorLabel, new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0), 10, 5));
		subInformationPanel.add(bookAuthorTextField, new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0), 10, 5));
		subInformationPanel.add(bookPublicationLabel, new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0), 10, 5));
		subInformationPanel.add(bookPublicationComboBox, new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0), 0, 0));
		subInformationPanel.add(bookBranchLabel, new GridBagConstraints(0, 4, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0), 10, 5));
		subInformationPanel.add(bookBranchComboBox, new GridBagConstraints(1, 4, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0), 0, 0));
		subInformationPanel.add(bookPriceLabel, new GridBagConstraints(0, 5, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0), 10, 5));
		subInformationPanel.add(bookPriceTextField, new GridBagConstraints(1, 5, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0), 10, 5));
		subInformationPanel.add(bookQuantityLabel, new GridBagConstraints(0, 6, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0), 10, 5));
		subInformationPanel.add(bookQuantityTextField, new GridBagConstraints(1, 6, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0), 10, 5));
		subInformationPanel.add(bookPhotoLabel, new GridBagConstraints(0, 7, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0), 10, 5));
		subInformationPanel.add(addBookPhotoButton, new GridBagConstraints(1, 7, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0), 20, 10));
		subInformationPanel.add(bookPhotoPathLabel, new GridBagConstraints(0, 8, 2, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets( 0, 400, 0, 0), 0, 0));
		subInformationPanel.add(addNewBookButton, new GridBagConstraints(1, 9, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets( 20, 0, 10, 0), 50, 10));

		
		subInformationPanel.revalidate();
		subInformationPanel.repaint();
		
	}
		
	
	void design_bookReport() 
	{
		empty_subInformationPanel();
		
		welcomeTitlePanelLabel.setText("View Book");	
		
		get_list();
		
		selectBranchLabel = new JLabel("Select Branch :");
		style_label(selectBranchLabel);
		selectPublicationLabel = new JLabel("Select Publication :");
		style_label(selectPublicationLabel);
		
		selectBranchComboBox = new JComboBox(branchList);
		style_comboBox(selectBranchComboBox);
		selectBranchComboBox.setMaximumRowCount(5);
		selectBranchComboBox.setPreferredSize(new Dimension(155, 25));
		selectBranchComboBox.setSelectedIndex(-1);
		
		selectPublicationComboBox = new JComboBox( publicationList);
		style_comboBox(selectPublicationComboBox);
		selectPublicationComboBox.setMaximumRowCount(5);
		selectPublicationComboBox.setPreferredSize(new Dimension(155, 25));
		selectPublicationComboBox.setSelectedIndex(-1);
		
		viewBranchButton = new JButton("View");
		style_button(viewBranchButton);
		viewBranchButton.addActionListener(myEventListener);
		
		viewPublicationButton = new JButton("View");
		style_button(viewPublicationButton);
		viewPublicationButton.addActionListener(myEventListener);
		
		bookTableTitle = new JLabel("",JLabel.CENTER);
		bookTableTitle.setFont(boldFont5);
		bookTableTitle.setBackground(new Color(0, 150, 150));
		bookTableTitle.setForeground(Color.WHITE);
		bookTableTitle.setOpaque(true);
		bookTableTitle.setVisible(false);
		
		bookTable = new JTable();
		bookTable.setRowHeight(30);
		bookTable.setBorder(new LineBorder(Color.BLACK));
		bookTable.setFont(boldFont4);
		
		JTableHeader bookTableHeader = bookTable.getTableHeader();
		bookTableHeader.setBackground(Color.YELLOW);
		bookTableHeader.setFont(boldFont5);
		bookTableHeader.setBorder(new LineBorder(Color.BLACK));
		
		bookTableScrollPane = new JScrollPane();
		bookTableScrollPane.setBorder(null);
		bookTableScrollPane.setBackground(new Color(153, 255, 153));
		bookTableScrollPane.setVisible(false);
		
		viewBookButton = new JButton("VIEW BOOK");
		style_button(viewBookButton);
		viewBookButton.addActionListener(myEventListener);
		viewBookButton.setVisible(false);
		
		bookDetailsPanel = new JPanel(new GridBagLayout());
		bookDetailsPanel.setBackground(Color.WHITE);

		subBookReportPanel = new JPanel(new GridBagLayout());
		subBookReportPanel.setBackground(new Color(153, 255, 153));
		
		subBookReportPanel.add(selectBranchLabel,new GridBagConstraints(0, 0, 1, 1, 1, 1,GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 10, 5));
		subBookReportPanel.add(selectPublicationLabel,new GridBagConstraints(4, 0, 1, 1, 1, 1,GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 10, 5));
		subBookReportPanel.add(selectBranchComboBox,new GridBagConstraints(1, 0, 2, 1, 1, 1,GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		subBookReportPanel.add(selectPublicationComboBox,new GridBagConstraints(5, 0, 2, 1, 1, 1,GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		subBookReportPanel.add(viewBranchButton,new GridBagConstraints(3, 0, 1, 1, 1, 1,GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 30, 10));
		subBookReportPanel.add(viewPublicationButton,new GridBagConstraints(7, 0, 1, 1, 1, 1,GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 30, 10));
		
		subInformationPanel.add(subBookReportPanel,new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		subInformationPanel.add(bookTableTitle,new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.BOTH, new Insets(10, 5, 0, 5), 0, 0));
		subInformationPanel.add(bookTableScrollPane,new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.BOTH, new Insets(1, 5, 10, 5), 0, 200));
		subInformationPanel.add(viewBookButton,new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 50, 10));
		
		subInformationPanel.revalidate();
		subInformationPanel.repaint();
	}
	
	
	void design_addBranch() 
	{
		
		empty_subInformationPanel();
		
		welcomeTitlePanelLabel.setText("Add New Branch");
		
		branchNameLabel = new JLabel("Branch Name :");
		style_label(branchNameLabel);
		
		branchNameTextField = new JTextField(17);
		style_textField(branchNameTextField);
		
		branchConfirmLabel = new JLabel("");
		style_label(branchConfirmLabel);
		branchConfirmLabel.setFont(boldFont3);
		branchConfirmLabel.setForeground(Color.BLUE);
		
		addNewBranchButton = new JButton("Add Branch");
		style_button(addNewBranchButton);
		addNewBranchButton.addActionListener(myEventListener);
		
		subInformationPanel.add(branchNameLabel,new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 10, 5));
		subInformationPanel.add(branchNameTextField,new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 10, 5));
		subInformationPanel.add(branchConfirmLabel,new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(-10, 0, 0, 0), 0, 0));
		subInformationPanel.add(addNewBranchButton,new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 15, 10));

		subInformationPanel.revalidate();
		subInformationPanel.repaint();
	}
	
	void design_addStudent() 
	{
		
		empty_subInformationPanel();
				
		welcomeTitlePanelLabel.setText("Add New Student");
		
		get_list();
		
		studentNameLabel = new JLabel("Student Name :");
		style_label(studentNameLabel);
		
		branchLabel = new JLabel("Branch :");
		style_label(branchLabel);

		genderLabel = new JLabel("Gender :");
		style_label(genderLabel);

		birthDateLabel = new JLabel("Birth Date :");
		style_label(birthDateLabel);

		mobileLabel = new JLabel("Mobile :");
		style_label(mobileLabel);

		addressLabel = new JLabel("Address :");
		style_label(addressLabel);

		cityLabel = new JLabel("City :");
		style_label(cityLabel);

		pincodeLabel = new JLabel("Pin Code :");
		style_label(pincodeLabel);

		studentPhotoLabel = new JLabel("Photo :");
		style_label(studentPhotoLabel);

		studentPhotoPathLabel = new JLabel();
		style_label(studentPhotoPathLabel);
		studentPhotoPathLabel.setFont(boldFont3);
		
		emailLabel = new JLabel("Email :");
		style_label(emailLabel);

		passwordLabel = new JLabel("Password :");
		style_label(passwordLabel);

		studentNameTextField = new JTextField(15);
		style_textField(studentNameTextField);
		
		branchComboBox = new JComboBox( branchList);
		style_comboBox(branchComboBox);
		branchComboBox.setMaximumRowCount(5);
		branchComboBox.setSelectedIndex(-1);
		branchComboBox.setPreferredSize(new Dimension(177, 25));
		
		maleRadioButton = new JRadioButton("MALE", true);
		style_radiobutton(maleRadioButton);
		
		femaleRadioButton = new JRadioButton("FEMALE");
		style_radiobutton(femaleRadioButton);
		
		genderButtonGroup = new ButtonGroup();
		genderButtonGroup.add(maleRadioButton);
		genderButtonGroup.add(femaleRadioButton);
		
		insert_daysAndYears();
		birthDateDateComboBox = new JComboBox(days_31);
		birthDateDateComboBox.setMaximumRowCount(5);
		birthDateDateComboBox.setSelectedIndex(-1);
		style_comboBox(birthDateDateComboBox);
		
		birthDateMonthComboBox = new JComboBox(month);
		birthDateMonthComboBox.setMaximumRowCount(5);
		birthDateMonthComboBox.setSelectedIndex(-1);
		style_comboBox(birthDateMonthComboBox);
		
		birthDateYearComboBox = new JComboBox(year);
		birthDateYearComboBox.setMaximumRowCount(5);
		birthDateYearComboBox.setSelectedIndex(-1);
		style_comboBox(birthDateYearComboBox);
		
		mobileTextField = new JTextField(15);
		style_textField(mobileTextField);
		
		addressTextArea = new JTextArea(4,15);
		style_textArea(addressTextArea);
		addressScrollPane = new JScrollPane(addressTextArea);
		
		cityTextField = new JTextField(15);
		style_textField(cityTextField);
		
		pincodeTextField = new JTextField(15);
		style_textField(pincodeTextField);
		
		addStudentPhotoButton = new JButton("Add Photo");
		style_button(addStudentPhotoButton);
		addStudentPhotoButton.setBackground(Color.BLUE);
		addStudentPhotoButton.setForeground(Color.WHITE);
		addStudentPhotoButton.addActionListener(myEventListener);
		
		emailTextField = new JTextField(15);
		style_textField(emailTextField);
		
		passwordPasswordField = new JPasswordField(15);
		style_passwordfield(passwordPasswordField);
		
		addNewStudentButton = new JButton("Add Student");
		style_button(addNewStudentButton);
		addNewStudentButton.addActionListener(myEventListener);
		
		subInformationPanel.add(studentNameLabel,new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(20, 0, 0, 0), 10, 5));
		subInformationPanel.add(studentNameTextField,new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(20, 0, 0, 0), 10, 5));
		subInformationPanel.add(branchLabel,new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 10, 5));
		subInformationPanel.add(branchComboBox,new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		subInformationPanel.add(genderLabel,new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 10, 5));
		subInformationPanel.add(maleRadioButton,new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 10, 5));
		subInformationPanel.add(femaleRadioButton,new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 80, 0, 0), 10, 5));
		subInformationPanel.add(birthDateLabel,new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 10, 5));
		subInformationPanel.add(birthDateDateComboBox,new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, -5));
		subInformationPanel.add(birthDateMonthComboBox,new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 55, 0, 0), 0, -5));
		subInformationPanel.add(birthDateYearComboBox,new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 120, 0, 0), 0, -5));
		subInformationPanel.add(mobileLabel,new GridBagConstraints(0, 4, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 10, 5));
		subInformationPanel.add(mobileTextField,new GridBagConstraints(1, 4, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 10, 5));
		subInformationPanel.add(addressLabel,new GridBagConstraints(0, 5, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 10, 5));
		subInformationPanel.add(addressScrollPane,new GridBagConstraints(1, 5, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 8, 5));
		subInformationPanel.add(cityLabel,new GridBagConstraints(0, 6, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 10, 5));
		subInformationPanel.add(cityTextField,new GridBagConstraints(1, 6, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 10, 5));
		subInformationPanel.add(pincodeLabel,new GridBagConstraints(0, 7, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 10, 5));
		subInformationPanel.add(pincodeTextField,new GridBagConstraints(1, 7, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 10, 5));
		subInformationPanel.add(studentPhotoLabel,new GridBagConstraints(0, 8, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 10, 5));
		subInformationPanel.add(addStudentPhotoButton, new GridBagConstraints(1, 8, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0), 20, 10));
		subInformationPanel.add(studentPhotoPathLabel, new GridBagConstraints(0, 9, 2, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets( 0, 410, 0, 0), 0, 0));
		subInformationPanel.add(emailLabel,new GridBagConstraints(0, 10, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 10, 5));
		subInformationPanel.add(emailTextField,new GridBagConstraints(1, 10, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 10, 5));
		subInformationPanel.add(passwordLabel,new GridBagConstraints(0, 11, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 10, 5));
		subInformationPanel.add(passwordPasswordField,new GridBagConstraints(1, 11, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 10, 5));		
		subInformationPanel.add(addNewStudentButton,new GridBagConstraints(1, 12, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(20, 0, 10, 0), 50, 10));		

		
		subInformationPanel.revalidate();
		subInformationPanel.repaint();
		
	}
	
	
	void design_studentReport()
	{
		
		empty_subInformationPanel();
		
		welcomeTitlePanelLabel.setText("Student Report");
		
		get_list();
		
		studentBranchReportLabel = new JLabel("Select Branch :");
		style_label(studentBranchReportLabel);
		studentNameReportLabel = new JLabel("Student Name :");
		style_label(studentNameReportLabel);
		
		studentBranchReportComboBox = new JComboBox( branchList);
		style_comboBox(studentBranchReportComboBox);
		studentBranchReportComboBox.setMaximumRowCount(5);
		studentBranchReportComboBox.setSelectedItem(null);
		studentBranchReportComboBox.setPreferredSize(new Dimension(170, 25));
		
		studentNameReportTextField = new JTextField(13);
		style_textField(studentNameReportTextField);
		
		viewBranchReportButton = new JButton("View");
		style_button(viewBranchReportButton);
		viewBranchReportButton.addActionListener(myEventListener);
		
		viewStudentReportButton = new JButton("View");
		style_button(viewStudentReportButton);
		viewStudentReportButton.addActionListener(myEventListener);
		
		
		studentTableTitle = new JLabel("",JLabel.CENTER);
		studentTableTitle.setFont(boldFont5);
		studentTableTitle.setBackground(new Color(0, 150, 150));
		studentTableTitle.setForeground(Color.WHITE);
		studentTableTitle.setOpaque(true);
		studentTableTitle.setVisible(false);
		
		studentTable = new JTable();
		studentTable.setRowHeight(30);
		studentTable.setBorder(new LineBorder(Color.BLACK));
		studentTable.setFont(boldFont4);
		
		JTableHeader studentTableHeader = studentTable.getTableHeader();
		studentTableHeader.setBackground(Color.YELLOW);
		studentTableHeader.setFont(boldFont5);
		studentTableHeader.setBorder(new LineBorder(Color.BLACK));
		
		studentTableScrollPane = new JScrollPane();
		studentTableScrollPane.setBorder(null);
		studentTableScrollPane.setBackground(new Color(153, 255, 153));
		studentTableScrollPane.setVisible(false);
		
		viewStudentButton = new JButton("VIEW STUDENT");
		style_button(viewStudentButton);
		viewStudentButton.addActionListener(myEventListener);
		viewStudentButton.setVisible(false);
		
		studentDetailsPanel = new JPanel(new GridBagLayout());
		studentDetailsPanel.setBackground(Color.WHITE);

		subStudentReportPanel = new JPanel(new GridBagLayout());
		subStudentReportPanel.setBackground(new Color(153, 255, 153));
		
		
		
		subStudentReportPanel.add(studentBranchReportLabel,new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.LINE_END,GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 10, 5));
		subStudentReportPanel.add(studentBranchReportComboBox,new GridBagConstraints(1, 0, 2, 1, 1, 1, GridBagConstraints.LINE_START,GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		subStudentReportPanel.add(viewBranchReportButton,new GridBagConstraints(3, 0, 1, 1, 1, 1, GridBagConstraints.LINE_START,GridBagConstraints.NONE, new Insets(0, -25, 0, 0), 30, 10));
		subStudentReportPanel.add(studentNameReportLabel,new GridBagConstraints(4, 0, 1, 1, 1, 1, GridBagConstraints.LINE_END,GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 10, 5));
		subStudentReportPanel.add(studentNameReportTextField,new GridBagConstraints(5, 0, 2, 1, 1, 1, GridBagConstraints.LINE_START,GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 5, 5));
		subStudentReportPanel.add(viewStudentReportButton,new GridBagConstraints(7, 0, 1, 1, 1, 1, GridBagConstraints.LINE_START,GridBagConstraints.NONE, new Insets(0, -25, 0, 0), 30, 10));
		

		subInformationPanel.add(subStudentReportPanel,new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.BOTH, new Insets(10, 5, 0, 5), 0, 0));
		subInformationPanel.add(studentTableTitle,new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.BOTH, new Insets(10, 5, 0, 5), 0, 0));
		subInformationPanel.add(studentTableScrollPane,new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.BOTH, new Insets(1, 5, 10, 5), 0, 200));
		subInformationPanel.add(viewStudentButton,new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 50, 10));

		
		subInformationPanel.revalidate();
		subInformationPanel.repaint();
	}
	
	void design_issueBook() 
	{
		
		empty_subInformationPanel();
		
		welcomeTitlePanelLabel.setText("Issue Book Form");
		
		get_list();
		
		selectPublicationIssueBookLabel = new JLabel("Select Publication :");
		style_label(selectPublicationIssueBookLabel);
		
		selectBookIssueBookLabel = new JLabel("Select Book :");
		style_label(selectBookIssueBookLabel);
		
		selectPublicationIssueBookComboBox = new JComboBox( publicationList);
		style_comboBox(selectPublicationIssueBookComboBox);
		selectPublicationIssueBookComboBox.setMaximumRowCount(5);
		selectPublicationIssueBookComboBox.setSelectedIndex(-1);
		selectPublicationIssueBookComboBox.setPreferredSize(new Dimension(170, 25));
		selectPublicationIssueBookComboBox.addItemListener(myEventListener);
		
		selectBookIssueBookComboBox = new JComboBox();
		style_comboBox(selectBookIssueBookComboBox);
		selectBookIssueBookComboBox.setMaximumRowCount(5);
		selectBookIssueBookComboBox.setSelectedIndex(-1);
		selectBookIssueBookComboBox.setPreferredSize(new Dimension(170, 25));
		
		selectIssueBookButton = new JButton("Select");
		style_button(selectIssueBookButton);
		selectIssueBookButton.addActionListener(myEventListener);
				
		bookDetailsIssueBookPanel = new JPanel(new GridBagLayout());
		bookDetailsIssueBookPanel.setBackground(Color.WHITE);	
		
		selectBranchIssueBookLabel = new JLabel("Select Branch : ");
		style_label(selectBranchIssueBookLabel);	
		
		selectBranchIssueBookComboBox = new JComboBox(branchList);
		style_comboBox(selectBranchIssueBookComboBox);
		selectBranchIssueBookComboBox.setMaximumRowCount(4);
		selectBranchIssueBookComboBox.setSelectedIndex(-1);
		selectBranchIssueBookComboBox.setPreferredSize(new Dimension(170, 25));
		selectBranchIssueBookComboBox.addItemListener(myEventListener);
		
		selectStudentIssueBookLabel = new JLabel("Select Student : ");
		style_label(selectStudentIssueBookLabel);
		
		selectStudentIssueBookComboBox = new JComboBox();
		style_comboBox(selectStudentIssueBookComboBox);
		selectStudentIssueBookComboBox.setMaximumRowCount(4);
		selectStudentIssueBookComboBox.setSelectedIndex(-1);
		selectStudentIssueBookComboBox.setPreferredSize(new Dimension(170, 25));
		
		daysIssueBookLabel = new JLabel("Days : ");
		style_label(daysIssueBookLabel);
		
		daysIssueBookTextField = new JTextField(5);
		style_textField(daysIssueBookTextField);
		
		issueBookButton = new JButton("Issue Book");
		style_button(issueBookButton);
		issueBookButton.addActionListener(myEventListener);
		
		subBookDetailsIssueBookPanel = new JPanel(new GridBagLayout());
		subBookDetailsIssueBookPanel.setBackground(new Color(153, 255, 153));
		subBookDetailsIssueBookPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Select Student Details for Issuing Book", TitledBorder.LEFT, TitledBorder.TOP, boldFont7, Color.BLACK));

			
		subInformationPanel.add(selectPublicationIssueBookLabel,new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.LINE_END,GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 10, 5));
		subInformationPanel.add(selectPublicationIssueBookComboBox,new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.LINE_START,GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		subInformationPanel.add(selectBookIssueBookLabel,new GridBagConstraints(2, 0, 1, 1, 1, 1, GridBagConstraints.LINE_END,GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 10, 5));
		subInformationPanel.add(selectBookIssueBookComboBox,new GridBagConstraints(3, 0, 1, 1, 1, 1, GridBagConstraints.LINE_START,GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		subInformationPanel.add(selectIssueBookButton,new GridBagConstraints(0, 1, 4, 1, 1, 1, GridBagConstraints.CENTER,GridBagConstraints.NONE, new Insets(0, 80, 0, 0), 60, 10));
		
		
		subInformationPanel.revalidate();
		subInformationPanel.repaint();
	}
	
	
	void design_issueReport() 
	{
		empty_subInformationPanel();
		welcomeTitlePanelLabel.setText("Issue Book Report");
		
		get_list();
		
		JLabel messageLabel = new JLabel("Under Construction");
		messageLabel.setForeground(Color.BLACK);
		messageLabel.setFont(new Font("", Font.BOLD, 50));
		
		
		subInformationPanel.add(messageLabel,new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 10, 5));

		
		subInformationPanel.revalidate();
		subInformationPanel.repaint();
	}
	
	void design_returnBook()
	{
		empty_subInformationPanel();
		welcomeTitlePanelLabel.setText("Return Book");
		
		get_list();
		
		selectStudentReturnBookLabel = new JLabel("Select Student :");
		style_label(selectStudentReturnBookLabel);
		
		selectBookReturnBookLabel = new JLabel("Select Book :");
		style_label(selectBookReturnBookLabel);
		
		selectStudentReturnBookComboBox = new JComboBox(studentList);
		style_comboBox(selectStudentReturnBookComboBox);
		selectStudentReturnBookComboBox.setMaximumRowCount(5);
		selectStudentReturnBookComboBox.setSelectedIndex(-1);
		selectStudentReturnBookComboBox.setPreferredSize(new Dimension(170, 25));
		selectStudentReturnBookComboBox.addItemListener(myEventListener);

		
		selectBookReturnBookComboBox = new JComboBox();
		style_comboBox(selectBookReturnBookComboBox);
		selectBookReturnBookComboBox.setMaximumRowCount(5);
		selectBookReturnBookComboBox.setSelectedIndex(-1);
		selectBookReturnBookComboBox.setPreferredSize(new Dimension(170, 25));

		
		selectReturnBookButton = new JButton("Select");
		style_button(selectReturnBookButton);
		selectReturnBookButton.addActionListener(myEventListener);
		
		returnBookButton = new JButton("Return Book");
		style_button(returnBookButton);
		returnBookButton.addActionListener(myEventListener);
		
		bookDetailsReturnBookPanel = new JPanel(new GridBagLayout());
		bookDetailsReturnBookPanel.setBackground(Color.WHITE);
		
		subInformationPanel.add(selectStudentReturnBookLabel,new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 10, 5));
		subInformationPanel.add(selectStudentReturnBookComboBox,new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		subInformationPanel.add(selectBookReturnBookLabel,new GridBagConstraints(2, 0, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 10, 5));
		subInformationPanel.add(selectBookReturnBookComboBox,new GridBagConstraints(3, 0, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		subInformationPanel.add(selectReturnBookButton,new GridBagConstraints(0, 1, 4, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 60, 10));

		
		subInformationPanel.revalidate();
		subInformationPanel.repaint();
		
	}
	
	
	void design_penalty() 
	{
		empty_subInformationPanel();
		welcomeTitlePanelLabel.setText("Penalty Form");
		
		get_list();
		
		selectStudentPenaltyLabel = new JLabel("Select Student for Penalty : ");
		style_label(selectStudentPenaltyLabel);
		
		selectBookPenaltyLabel = new JLabel("Select Book : ");
		style_label(selectBookPenaltyLabel);
		
		selectStudentPenaltyComboBox = new JComboBox(studentList);
		style_comboBox(selectStudentPenaltyComboBox);
		selectStudentPenaltyComboBox.setMaximumRowCount(5);
		selectStudentPenaltyComboBox.setSelectedIndex(-1);
		selectStudentPenaltyComboBox.setPreferredSize(new Dimension(170, 25));
		selectStudentPenaltyComboBox.addItemListener(myEventListener);

		
		selectBookPenaltyComboBox = new JComboBox();
		style_comboBox(selectBookPenaltyComboBox);
		selectBookPenaltyComboBox.setMaximumRowCount(5);
		selectBookPenaltyComboBox.setSelectedIndex(-1);
		selectBookPenaltyComboBox.setPreferredSize(new Dimension(170, 25));
	
		selectPenaltyButton = new JButton("Select");
		style_button(selectPenaltyButton);
		selectPenaltyButton.addActionListener(myEventListener);
		
		payNowButton = new JButton("Pay Now");
		style_button(payNowButton);
		payNowButton.addActionListener(myEventListener);
		
		bookDetailsPenaltyPanel = new JPanel(new GridBagLayout());
		bookDetailsPenaltyPanel.setBackground(Color.WHITE);
		
		subInformationPanel.add(selectStudentPenaltyLabel,new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 10, 5));
		subInformationPanel.add(selectStudentPenaltyComboBox,new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		subInformationPanel.add(selectBookPenaltyLabel,new GridBagConstraints(2, 0, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 10, 5));
		subInformationPanel.add(selectBookPenaltyComboBox,new GridBagConstraints(3, 0, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		subInformationPanel.add(selectPenaltyButton,new GridBagConstraints(0, 1, 4, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 60, 10));

		
		subInformationPanel.revalidate();
		subInformationPanel.repaint();
	}
	
	void design_logout() 
	{
		LoginPage.empty_mainPanel();
		Index.loginPage.create_UI();
		
	}
	
	void design_myAccount()
	{
		empty_subInformationPanel();
		
		welcomeTitlePanelLabel.setText("MY ACCOUNT");
		
		viewAccountButton = new JButton("VIEW ACCOUNT");
		style_button(viewAccountButton);
		viewAccountButton.addActionListener(myEventListener);
		editAccountButton = new JButton("EDIT ACCOUNT");
		style_button(editAccountButton);
		editAccountButton.addActionListener(myEventListener);
		changePasswordButton = new JButton("CHANGE PASSWORD");
		style_button(changePasswordButton);
		changePasswordButton.addActionListener(myEventListener);
		
		
		design_myAccountPanel();
		
		
		subInformationPanel.add(viewAccountButton, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 230, 0, 0), 20, 10));
		subInformationPanel.add(editAccountButton, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 20, 10));
		subInformationPanel.add(changePasswordButton, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 200), 20, 10));
		subInformationPanel.add(myAccountPanel, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 20, 0));

		subInformationPanel.revalidate();
		subInformationPanel.repaint();
	}
	
	void design_myAccountPanel()
	{
		myAccountPanel = new JPanel(new GridBagLayout());
		myAccountPanel.setBackground(Color.WHITE);
		myAccountPanel.setBorder(new LineBorder(Color.BLACK));
		
		design_myAccountViewPanel();
		
	}
	
	void design_myAccountViewPanel()
	{
		empty_myAccountPanel();
		
		myAccountPanelTitle  = new JLabel("My Account Details",JLabel.CENTER);
		style_label(myAccountPanelTitle);
		myAccountPanelTitle.setFont(boldFont1);
		myAccountPanelTitle.setBackground(new Color(0, 102, 0));
		myAccountPanelTitle.setForeground(Color.WHITE);
		myAccountPanelTitle.setOpaque(true);
		myAccountPanelTitle.setBorder(new LineBorder(Color.BLACK));
		
		//My Account View Panel Fields
		JLabel nameLabel,mobileLabel,addressLabel,cityLabel,pincodeLabel,emailLabel;
		JLabel nameLabelValue,mobileLabelValue,addressLabelValue,cityLabelValue,pincodeLabelValue,emailLabelValue;
		
		nameLabel = new JLabel("Name :");
		style_label(nameLabel);
		
		mobileLabel = new JLabel("Mobile :");
		style_label(mobileLabel);
		
		addressLabel = new JLabel("Address :");
		style_label(addressLabel);
		
		cityLabel = new JLabel("City :");
		style_label(cityLabel);
		
		pincodeLabel = new JLabel("Pincode :");
		style_label(pincodeLabel);
		
		emailLabel = new JLabel("Email :");
		style_label(emailLabel);
		
		
		nameLabelValue = new JLabel(" Shubham Desai");
		style_label(nameLabelValue);
		
		mobileLabelValue = new JLabel(" 9156470706");
		style_label(mobileLabelValue);
		
		addressLabelValue = new JLabel(" Sakharale");
		style_label(addressLabelValue);
		
		cityLabelValue = new JLabel(" Islampur");
		style_label(cityLabelValue);
		
		pincodeLabelValue = new JLabel(" 415414");
		style_label(pincodeLabelValue);

		emailLabelValue = new JLabel(" shubhamdesai2550@gmail.com");
		style_label(emailLabelValue);
		
		
		myAccountPanel.add(myAccountPanelTitle,new GridBagConstraints(0, 0, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 5));
		myAccountPanel.add(nameLabel,new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 120, 0, 0), 0, 0));
		myAccountPanel.add(nameLabelValue,new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
		myAccountPanel.add(mobileLabel,new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		myAccountPanel.add(mobileLabelValue,new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		myAccountPanel.add(addressLabel,new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		myAccountPanel.add(addressLabelValue,new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		myAccountPanel.add(cityLabel,new GridBagConstraints(0, 4, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		myAccountPanel.add(cityLabelValue,new GridBagConstraints(1, 4, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		myAccountPanel.add(pincodeLabel,new GridBagConstraints(0, 5, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		myAccountPanel.add(pincodeLabelValue,new GridBagConstraints(1, 5, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		myAccountPanel.add(emailLabel,new GridBagConstraints(0, 6, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(5, 0, 20, 0), 0, 0));
		myAccountPanel.add(emailLabelValue,new GridBagConstraints(1, 6, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5, 0, 20, 0), 0, 0));

		myAccountPanel.revalidate();
		myAccountPanel.repaint();
	}
	
	void design_myAccountEditPanel()
	{
		empty_myAccountPanel();
		
		myAccountPanelTitle  = new JLabel("Update Account Details",JLabel.CENTER);
		style_label(myAccountPanelTitle);
		myAccountPanelTitle.setFont(boldFont1);
		myAccountPanelTitle.setBackground(new Color(0, 102, 0));
		myAccountPanelTitle.setForeground(Color.WHITE);
		myAccountPanelTitle.setOpaque(true);
		myAccountPanelTitle.setBorder(new LineBorder(Color.BLACK));
		
		//My Account Edit Panel Fields
		JLabel nameLabel,mobileLabel,addressLabel,cityLabel,pincodeLabel,emailLabel;
		JTextField nameTextField,mobileTextField,addressTextField,cityTextField,pincodeTextField,emailTextField;
		JButton updateButton;
		
		nameLabel = new JLabel("Name :");
		style_label(nameLabel);
		mobileLabel = new JLabel("Mobile :");
		style_label(mobileLabel);
		addressLabel = new JLabel("Address :");
		style_label(addressLabel);
		cityLabel = new JLabel("City :");
		style_label(cityLabel);
		pincodeLabel = new JLabel("Pincode :");
		style_label(pincodeLabel);
		emailLabel = new JLabel("Email :");
		style_label(emailLabel);
		
		nameTextField = new JTextField(15);
		style_textField(nameTextField);
		mobileTextField = new JTextField(15);
		style_textField(mobileTextField);
		addressTextField = new JTextField(15);
		style_textField(addressTextField);
		cityTextField = new JTextField(15);
		style_textField(cityTextField);
		pincodeTextField = new JTextField(15);
		style_textField(pincodeTextField);
		emailTextField = new JTextField(15);
		style_textField(emailTextField);
		
		updateButton = new JButton("UPDATE");
		style_button(updateButton);
		
		myAccountPanel.add(myAccountPanelTitle,new GridBagConstraints(0, 0, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 120, 5));
		myAccountPanel.add(nameLabel,new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
		myAccountPanel.add(nameTextField,new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 5, 0, 0), 10, 5));
		myAccountPanel.add(mobileLabel,new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
		myAccountPanel.add(mobileTextField,new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 5, 0, 0), 10, 5));
		myAccountPanel.add(addressLabel,new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
		myAccountPanel.add(addressTextField,new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 5, 0, 0), 10, 5));
		myAccountPanel.add(cityLabel,new GridBagConstraints(0, 4, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
		myAccountPanel.add(cityTextField,new GridBagConstraints(1, 4, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 5, 0, 0), 10, 5));
		myAccountPanel.add(pincodeLabel,new GridBagConstraints(0, 5, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
		myAccountPanel.add(pincodeTextField,new GridBagConstraints(1, 5, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 5, 0, 0), 10, 5));
		myAccountPanel.add(emailLabel,new GridBagConstraints(0, 6, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
		myAccountPanel.add(emailTextField,new GridBagConstraints(1, 6, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 5, 0, 0), 10, 5));
		myAccountPanel.add(updateButton,new GridBagConstraints(1, 7, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(15, 5, 20, 0), 40, 10));

		
		myAccountPanel.revalidate();
		myAccountPanel.repaint();
	}
	
	void design_myAccountChangePasswordPanel()
	{
		empty_myAccountPanel();
		
		myAccountPanelTitle  = new JLabel("Change Password",JLabel.CENTER);
		style_label(myAccountPanelTitle);
		myAccountPanelTitle.setFont(boldFont1);
		myAccountPanelTitle.setBackground(new Color(0, 102, 0));
		myAccountPanelTitle.setForeground(Color.WHITE);
		myAccountPanelTitle.setOpaque(true);
		myAccountPanelTitle.setBorder(new LineBorder(Color.BLACK));
		
		
		//My Account Change Password Fields
		JLabel currentPasswordLabel,newPasswordLabel,confirmPasswordLabel;
		JTextField currentPasswordTextField,newPasswordTextField,confirmPasswordTextField;
		JButton updateButton;
		
		currentPasswordLabel = new JLabel("Current Password :");
		style_label(currentPasswordLabel);
		
		newPasswordLabel = new JLabel("New Password :");
		style_label(newPasswordLabel);
		
		confirmPasswordLabel = new JLabel("Confirm Password :");
		style_label(confirmPasswordLabel);
		
		currentPasswordTextField = new JTextField(15);
		style_textField(currentPasswordTextField);
		
		newPasswordTextField = new JTextField(15);
		style_textField(newPasswordTextField);
		
		confirmPasswordTextField = new JTextField(15);
		style_textField(confirmPasswordTextField);
		
		updateButton = new JButton("UPDATE");
		style_button(updateButton);
		
		myAccountPanel.add(myAccountPanelTitle,new GridBagConstraints(0, 0, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 200, 5));
		myAccountPanel.add(currentPasswordLabel,new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
		myAccountPanel.add(currentPasswordTextField,new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 5, 0, 0), 10, 5));
		myAccountPanel.add(newPasswordLabel,new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
		myAccountPanel.add(newPasswordTextField,new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 5, 0, 0), 10, 5));
		myAccountPanel.add(confirmPasswordLabel,new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
		myAccountPanel.add(confirmPasswordTextField,new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 5, 0, 0), 10, 5));
		myAccountPanel.add(updateButton,new GridBagConstraints(1, 4, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(15, 5, 20, 0), 40, 10));

		
		myAccountPanel.revalidate();
		myAccountPanel.repaint();
	}
	
	
	void design_studentBookReport()
	{
		empty_subInformationPanel();
		
		welcomeTitlePanelLabel.setText("MY BOOK REPORT");
		
		borrowBookReportButton = new JButton("BORROWED BOOKS");
		style_button(borrowBookReportButton);
		borrowBookReportButton.addActionListener(myEventListener);
		returnBookReportButton = new JButton("RETURNED BOOKS");
		style_button(returnBookReportButton);
		returnBookReportButton.addActionListener(myEventListener);
		
		
		subInformationPanel.add(borrowBookReportButton, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 20, 10));
		subInformationPanel.add(returnBookReportButton, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 20, 10));

		subInformationPanel.revalidate();
		subInformationPanel.repaint();
		
		
	}
	
	void design_studentPenalty()
	{
		empty_subInformationPanel();
		welcomeTitlePanelLabel.setText("PENALTY");
		
		
	}
	
	
	void design_studentLogout() 
	{
		LoginPage.empty_mainPanel();
		Index.loginPage.create_UI();
	}
	
	
	//----------------------------------------------Style Fields----------------------------------------------------
	
	void style_label(JLabel label) 
	{
		label.setForeground(Color.BLACK);
		label.setFont(boldFont2);
		
	}
	
	void style_textField(JTextField textField) 
	{
		textField.setForeground(Color.BLACK);
		textField.setBackground(new Color(255, 229, 204));
		textField.setFont(boldFont3);
		textField.setBorder(new LineBorder(Color.BLACK));
	}
	
	void style_textArea(JTextArea textArea) 
	{
		textArea.setForeground(Color.BLACK);
		textArea.setBackground(new Color(255, 229, 204));
		textArea.setFont(boldFont3);
		textArea.setBorder(new LineBorder(Color.BLACK));
	}
	
	void style_comboBox(JComboBox comboBox)
	{
		comboBox.setForeground(Color.BLACK);
		comboBox.setBackground(new Color(255, 229, 204));
		comboBox.setFont(boldFont3);
		comboBox.setBorder(new LineBorder(Color.BLACK));
	}
	
	void style_radiobutton(JRadioButton radioButton)
	{
		radioButton.setBackground(new Color(153, 255, 153));
		radioButton.setForeground(Color.BLACK);
		radioButton.setFont(boldFont3);
		
		
	}
	
	void style_button(JButton button)
	{
		button.setBackground(new Color(255,128,0));
		button.setForeground(Color.WHITE);
		button.setFont(boldFont4);
		button.setBorder(new LineBorder(Color.BLACK));
		
	}
	
	void style_passwordfield(JPasswordField passwordField)
	{
		passwordField.setForeground(Color.BLACK);
		passwordField.setBackground(new Color(255, 229, 204));
		passwordField.setFont(boldFont3);
		passwordField.setBorder(new LineBorder(Color.BLACK));
	}
	
	void insert_daysAndYears()
	{
		int cnt = 1;
		for(int i = 0 ;i < 31 ;i++)
		{
			days_31[i] = String.valueOf(cnt);
			if(i<30)
				days_30[i] = String.valueOf(cnt);
			if(i<29)
				days_29[i] = String.valueOf(cnt);
			if(i<28)
				days_28[i] = String.valueOf(cnt);
			cnt++;
		}
		int index = 0;
		for (int i = 1970; i < 2010; i++) 
		{
			year[index++] = String.valueOf(i);
		}
	}

	

	void empty_subInformationPanel() 
	{
		
		subInformationPanel.removeAll();
		
		subInformationPanel.revalidate();
		subInformationPanel.repaint();
		
	}
	
	void empty_myAccountPanel() 
	{
		
		myAccountPanel.removeAll();
		
		myAccountPanel.revalidate();
		myAccountPanel.repaint();
		
	}
	
	
	void get_list() 
	{
		
		int count = 0;
		
		
		String query1 = "select count(*) from publicationtable";
		String query2 = "select count(*) from branchtable";
		String query3 = "select count(*) from booktable";
		String query4 = "select count(*) from studenttable";
		String query5 = "select Publication from publicationtable";
		String query6 = "select BranchName from branchtable";
		String query7 = "select BookName from booktable";
		String query8 = "select StudentName from studenttable";
		
		
		try 
		{
			
			Statement stmt = LoginPage.con.createStatement();
			ResultSet rs = stmt.executeQuery(query1);
			rs.next();
			publicationList = new String[rs.getInt(1)];
			
			rs = stmt.executeQuery(query2);
			rs.next();
			branchList = new String[rs.getInt(1)];
			
			rs = stmt.executeQuery(query3);
			rs.next();
			bookList = new String[rs.getInt(1)];
			
			rs = stmt.executeQuery(query4);
			rs.next();
			studentList = new String[rs.getInt(1)];
			
			rs = stmt.executeQuery(query5);
			while (rs.next()) 
			{
				publicationList[count++] = rs.getString(1);
			}
			
			count = 0;
			rs = stmt.executeQuery(query6);
			while (rs.next()) 
			{
				branchList[count++] = rs.getString(1);
			}
			
			count = 0;
			rs = stmt.executeQuery(query7);
			while (rs.next()) 
			{
				bookList[count++] = rs.getString(1);
			}
			
			count = 0;
			rs = stmt.executeQuery(query8);
			while (rs.next()) 
			{
				studentList[count++] = rs.getString(1);
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

	}
	
	void remove_bookTableRows(String[] bookReportColumns)
	{
		bookTable.setModel(new DefaultTableModel(null,bookReportColumns));
		
	}
	
	void remove_studentTableRows(String[] studentReportColumns)
	{
		studentTable.setModel(new DefaultTableModel(null,studentReportColumns));
		
	}

	int calculate_penalty(int sID,String selectedBook)
	{
		int days = 0;
		long millis = System.currentTimeMillis();
		
		Date localDate = new Date(millis);
		Time localTime = new Time(millis);
		String localDateTime;
		
		
		String issueDateTime;
		
		java.util.Date curDate,issueDate;
		
		int penalty = 0;
		
		try 
		{

			Statement stmt = LoginPage.con.createStatement();
			ResultSet rs = null;
			
			String query3 = "select Days,IssueDate from renttable where SID = '"+ sID +"' and BookName = '"+ selectedBook +"';";
			
			rs = stmt.executeQuery(query3);
			rs.next();
			
			days = rs.getInt(1);
			issueDateTime = rs.getString(2);
			
			localDateTime = localDate.toString() + " " + localTime.toString();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			curDate = sdf.parse(localDateTime);
			issueDate = sdf.parse(issueDateTime);
			
			long currenttimemillis = curDate.getTime();
			long issuetimemillis = issueDate.getTime();
			long milliseconds = currenttimemillis - issuetimemillis;
			int totalDays = (int)(( milliseconds / 1000 ) / 60 ) / 1440;
			long seconds = ( milliseconds / 1000)-(totalDays * 86400);
			
			if (totalDays >= days) 
			{
				days = totalDays - days;
				if (seconds > 0) 
				{
					days++;
					penalty = 10 * days;
				}
			}
				
		}	
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return penalty ;
	}

	
}

