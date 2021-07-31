package library;

import java.awt.Color;
import java.awt.Dimension;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class MyEventListener implements ItemListener, ActionListener
{
	InformationPanel informationPanel = null;
	
	private String selectedBook = "";
	private String selectedPublication = "";
	private int penalty = 0;
	private int sID = 0;
	
	
	public MyEventListener(InformationPanel informationPanel) 
	{
		this.informationPanel = informationPanel;
	}
	
	@Override
	public void itemStateChanged(ItemEvent ie) 
	{
		
		if (ie.getSource() == informationPanel.selectPublicationIssueBookComboBox ) 
		{
			
			if (ie.getStateChange() == ie.SELECTED) 
			{
				
				informationPanel.selectBookIssueBookComboBox.removeAllItems();

				String selectedPublication = informationPanel.selectPublicationIssueBookComboBox.getSelectedItem().toString();
				String query = "select BookName from booktable where Publication = '" + selectedPublication + "';";
				
				
				try 
				{
					Statement stmt = LoginPage.con.createStatement();
					ResultSet rs = stmt.executeQuery(query);
					
					if(!rs.next())
					{
						informationPanel.selectBookIssueBookComboBox.setEnabled(false);
						return;
					}
					else 
					{
						informationPanel.selectBookIssueBookComboBox.setEnabled(true);
						do 
						{
							informationPanel.selectBookIssueBookComboBox.addItem(rs.getString(1));
						} while (rs.next());
					}
					
					
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
		
		}
		
		
		if (ie.getSource() == informationPanel.selectBranchIssueBookComboBox) 
		{
			
			if (ie.getStateChange() == ie.SELECTED) 
			{
				
				informationPanel.selectStudentIssueBookComboBox.setEnabled(true);
				
				informationPanel.selectStudentIssueBookComboBox.removeAllItems();
				
				String selectedBranch = informationPanel.selectBranchIssueBookComboBox.getSelectedItem().toString();
				String query = "select StudentName from studenttable where BranchName = '" + selectedBranch + "';";
				
				try 
				{
					
					Statement stmt = LoginPage.con.createStatement();
					ResultSet rs = stmt.executeQuery(query);

					if(!rs.next())
					{
						informationPanel.selectStudentIssueBookComboBox.setEnabled(false);
						return;
					}
					else 
					{
						informationPanel.selectStudentIssueBookComboBox.setEnabled(true);
						do 
						{
							informationPanel.selectStudentIssueBookComboBox.addItem(rs.getString(1));
						} while (rs.next());
					}
					
					
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
				
			}
			
			
		}
		
		
		if (ie.getSource() == informationPanel.selectStudentReturnBookComboBox) 
		{
			
			if (ie.getStateChange() == ie.SELECTED) 
			{
				
				informationPanel.selectBookReturnBookComboBox.setEnabled(true);
				
				informationPanel.selectBookReturnBookComboBox.removeAllItems();
				
				String selectedStudent = informationPanel.selectStudentReturnBookComboBox.getSelectedItem().toString();
				String query1 = "select SID from studenttable where StudentName = '" + selectedStudent + "';";
				
				
				Statement stmt = null;
				ResultSet rs = null;
				
				try 
				{
					
					stmt = LoginPage.con.createStatement();
					rs = stmt.executeQuery(query1);
					rs.next();
					this.sID = rs.getInt(1);
					
					
					String query2 = "select BookName from renttable where SID = " + this.sID + " and ReturnDate is null;";
					rs = stmt.executeQuery(query2);

					if(!rs.next())
					{
						informationPanel.selectBookReturnBookComboBox.setEnabled(false);
						return;
					}
					else 
					{
						informationPanel.selectBookReturnBookComboBox.setEnabled(true);
						do 
						{
							informationPanel.selectBookReturnBookComboBox.addItem(rs.getString(1));
						} while (rs.next());
					}
					
					
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
				
			}
		}
		
		if (ie.getSource() == informationPanel.selectStudentPenaltyComboBox) 
		{
			if (ie.getStateChange() == ie.SELECTED) 
			{
				informationPanel.selectBookPenaltyComboBox.setEnabled(true);
				
				informationPanel.selectBookPenaltyComboBox.removeAllItems();
				
				String selectedStudent = informationPanel.selectStudentPenaltyComboBox.getSelectedItem().toString();
				String query1 = "select SID from studenttable where StudentName = '" + selectedStudent + "';";
				
				Statement stmt = null;
				ResultSet rs = null;
				
				try 
				{
					stmt = LoginPage.con.createStatement();
					rs = stmt.executeQuery(query1);
					rs.next();
					this.sID = rs.getInt(1);
					
					String query2 = "select BookName from renttable where SID = " + this.sID + " and ReturnDate is not null and PenaltyStatus > 0;";
					rs = stmt.executeQuery(query2);
					
					if(!rs.next())
					{
						informationPanel.selectBookPenaltyComboBox.setEnabled(false);
						return;
					}
					else 
					{
						informationPanel.selectBookPenaltyComboBox.setEnabled(true);
						do 
						{
							informationPanel.selectBookPenaltyComboBox.addItem(rs.getString(1));
						} while (rs.next());
					}
					
				}
				catch (SQLException e) 
				{
					
				}
			}
		}
		
		
	}
	
	

	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		
		if(ae.getSource() == informationPanel.addBookPhotoButton )
		{
			String filename = null;
			JFileChooser chooser = new JFileChooser("C:\\Users\\Madhuri\\eclipse-workspace\\Library Management System\\images");
			chooser.showOpenDialog(null);
			File f=chooser.getSelectedFile();
			try 
			{
				filename = f.getName();
				informationPanel.bookPhotoAbsolutePathString = f.getAbsolutePath();
			}
			catch (NullPointerException e) 
			{
				informationPanel.bookPhotoPathLabel.setText(informationPanel.bookPhotoPathLabel.getText());
				return;
			}
			
			informationPanel.bookPhotoPathLabel.setText(filename);
			
		}
		
		
		if(ae.getSource() == informationPanel.addStudentPhotoButton )
		{
			String filename = null;
			JFileChooser chooser = new JFileChooser("C:\\Users\\Madhuri\\eclipse-workspace\\Library Management System\\images");
			chooser.showOpenDialog(null);
			File f=chooser.getSelectedFile();
			
			try 
			{
				filename = f.getName();
				informationPanel.studentPhotoAbsolutePathString = f.getAbsolutePath();
			}
			catch (NullPointerException e) 
			{
				informationPanel.studentPhotoPathLabel.setText(informationPanel.studentPhotoPathLabel.getText());
				return;
			}
			
			informationPanel.studentPhotoPathLabel.setText(filename);
		}
		
		if (ae.getSource() == informationPanel.viewAccountButton) 
		{
			informationPanel.design_myAccountViewPanel();
		}
		
		if (ae.getSource() == informationPanel.editAccountButton) 
		{
			informationPanel.design_myAccountEditPanel();
			
		}
		if (ae.getSource() == informationPanel.changePasswordButton) 
		{
			informationPanel.design_myAccountChangePasswordPanel();
		}
		
		
		
		if (ae.getSource() == informationPanel.addNewPublicationButton) 
		{
			String publicationName = "";
			String query = "insert into publicationtable(Publication,EntryDate) values(?,?)";
			
			
			try 
			{
				
				publicationName = informationPanel.publicationNameTextField.getText();
				
				PreparedStatement pstmt = LoginPage.con.prepareStatement(query);
				pstmt.setString(1, publicationName);
				pstmt.setDate(2, informationPanel.date);
				pstmt.executeUpdate();
				
				informationPanel.publicationConfirmLabel.setText(publicationName + " Publication Added !");
				informationPanel.publicationNameTextField.setText("");
				
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
		}
		
		
		
		if (ae.getSource() == informationPanel.addNewBookButton) 
		{
			
			String query = "insert into booktable(BookName,Author,Detail,Price,Publication,Branch,Quantity,Image,EntryDate,Available,RentQnt) values(?,?,?,?,?,?,?,?,?,?,?)";
		
			
			if (informationPanel.bookNameTextField.getText().equals("") || informationPanel.bookAuthorTextField.getText().equals("") || informationPanel.bookDetailTextArea.getText().equals("") || informationPanel.bookPublicationComboBox.getSelectedItem() == null || informationPanel.bookBranchComboBox.getSelectedItem() == null || informationPanel.bookPriceTextField.getText().equals("") || informationPanel.bookQuantityTextField.getText().equals("") || informationPanel.bookPhotoPathLabel.getText().equals("")) 
			{
				JOptionPane.showMessageDialog(informationPanel, "Please enter all the Details !", "Warning", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			
			String bookName = informationPanel.bookNameTextField.getText(),
					author = informationPanel.bookAuthorTextField.getText(),
					detail = informationPanel.bookDetailTextArea.getText(),
					publication = informationPanel.bookPublicationComboBox.getSelectedItem().toString(),
					branch = informationPanel.bookBranchComboBox.getSelectedItem().toString();
					
			int price = Integer.parseInt(informationPanel.bookPriceTextField.getText()),
					quantity = Integer.parseInt(informationPanel.bookQuantityTextField.getText());
					
			
			try 
			{
				
				PreparedStatement pstmt = LoginPage.con.prepareStatement(query);
				pstmt.setString(1, bookName);
				pstmt.setString(2, author);
				pstmt.setString(3, detail);
				pstmt.setInt(4, price);
				pstmt.setString(5, publication);
				pstmt.setString(6, branch);
				pstmt.setInt(7, quantity);
				pstmt.setString(8, informationPanel.bookPhotoAbsolutePathString);
				pstmt.setDate(9, informationPanel.date);
				pstmt.setInt(10, quantity);
				pstmt.setInt(11, 0);
				pstmt.executeUpdate();
				
				
				JOptionPane.showMessageDialog(informationPanel, "New Book Added Successfully !", "Success", JOptionPane.INFORMATION_MESSAGE);
				
				
				informationPanel.bookNameTextField.setText("");
				informationPanel.bookAuthorTextField.setText("");
				informationPanel.bookDetailTextArea.setText("");
				informationPanel.bookPriceTextField.setText("");
				informationPanel.bookQuantityTextField.setText("");
				informationPanel.bookPhotoPathLabel.setText("");
				informationPanel.bookBranchComboBox.setSelectedIndex(-1);
				informationPanel.bookPublicationComboBox.setSelectedIndex(-1);
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		
		if (ae.getSource() == informationPanel.addNewBranchButton) 
		{
			String BranchName = "";
			String query = "insert into branchtable(BranchName) values(?)";
			
			
			try 
			{
				
				BranchName = informationPanel.branchNameTextField.getText();
				
				PreparedStatement pstmt = LoginPage.con.prepareStatement(query);
				pstmt.setString(1, BranchName);
				pstmt.executeUpdate();
				
				informationPanel.branchConfirmLabel.setText(BranchName + " Branch Added !");
				informationPanel.branchNameTextField.setText("");
				
				System.out.print("Inserted");
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
		}
		
		
		if (ae.getSource() == informationPanel.addNewStudentButton ) 
		{
			
			String query = "insert into studenttable(StudentName,BranchName,Mobile,Address,City,Pincode,DOB,Gender,Email,Password,Image,EntryDate) values(?,?,?,?,?,?,?,?,?,?,?,?)";
			
			if (informationPanel.studentNameTextField.getText().equals("") || informationPanel.mobileTextField.getText().equals("") || informationPanel.addressTextArea.getText().equals("") || informationPanel.cityTextField.getText().equals("") || informationPanel.pincodeTextField.getText().equals("") || informationPanel.studentPhotoPathLabel.getText().equals("") || informationPanel.emailTextField.getText().equals("") || informationPanel.passwordPasswordField.getText().equals("")) 
			{
				JOptionPane.showMessageDialog(informationPanel, "Please enter all the Details !", "Warning", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			int day = Integer.valueOf(informationPanel.birthDateDateComboBox.getSelectedItem().toString());
			int month = informationPanel.birthDateMonthComboBox.getSelectedIndex()+1;
			int year = Integer.valueOf(informationPanel.birthDateYearComboBox.getSelectedItem().toString());
			
			String gender = informationPanel.maleRadioButton.isSelected() ? "Male":"Female";
			
			String birthDate = year + "-" + month + "-" + day;
			Date DOB = Date.valueOf(birthDate);
			
			try 
			{
				
				PreparedStatement pstmt = LoginPage.con.prepareStatement(query);
				pstmt.setString(1, informationPanel.studentNameTextField.getText());
				pstmt.setString(2, informationPanel.branchComboBox.getSelectedItem().toString() );
				pstmt.setString(3, informationPanel.mobileTextField.getText());
				pstmt.setString(4, informationPanel.addressTextArea.getText());
				pstmt.setString(5, informationPanel.cityTextField.getText());
				pstmt.setString(6, informationPanel.pincodeTextField.getText());
				pstmt.setDate(7, DOB);
				pstmt.setString(8, gender);
				pstmt.setString(9, informationPanel.emailTextField.getText());
				pstmt.setString(10, informationPanel.passwordPasswordField.getText());
				pstmt.setString(11, informationPanel.studentPhotoAbsolutePathString);
				pstmt.setDate(12, informationPanel.date);
				pstmt.executeUpdate();
				
				JOptionPane.showMessageDialog(informationPanel, "New Student Added Successfully !", "Success", JOptionPane.INFORMATION_MESSAGE);
				
				informationPanel.studentNameTextField.setText("");
				informationPanel.addressTextArea.setText("");
				informationPanel.mobileTextField.setText("");
				informationPanel.cityTextField.setText("");
				informationPanel.pincodeTextField.setText("");
				informationPanel.emailTextField.setText("");
				informationPanel.passwordPasswordField.setText("");
				informationPanel.studentPhotoPathLabel.setText("");
				informationPanel.branchComboBox.setSelectedIndex(-1);
				
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		
		if (ae.getSource() == informationPanel.viewBranchButton) 
		{
			
			if (informationPanel.selectBranchComboBox.getSelectedItem() == null )
			{
				
				JOptionPane.showMessageDialog(informationPanel, "Please select Branch !", "Error", JOptionPane.WARNING_MESSAGE);
				return;
				
			}

			if (informationPanel.bookDetailsPanel.isDisplayable()) 
			{
				informationPanel.subInformationPanel.remove(informationPanel.bookDetailsPanel);
			}
			
			
			String[] bookReportColumns = {"BookName","Price","Quantity","Available","RentQnt"};
			String[] bookReportData = new String[5];
			
			informationPanel.remove_bookTableRows(bookReportColumns);
			
			String selectedBranch = informationPanel.selectBranchComboBox.getSelectedItem().toString();
			String selectedPublication = "";
			
			String query1 = "select BookName,Price,Quantity,Available,RentQnt from booktable where Branch = '"+ selectedBranch +"';";
						
			DefaultTableModel bookTableModel = (DefaultTableModel) informationPanel.bookTable.getModel();
			bookTableModel.setColumnIdentifiers(bookReportColumns);
			
			try 
			{
				Statement stmt = LoginPage.con.createStatement();
				ResultSet rs = null;
				
				if (informationPanel.selectPublicationComboBox.getSelectedItem() != null) 
				{
					selectedPublication = informationPanel.selectPublicationComboBox.getSelectedItem().toString();
					String query2 = "select BookName,Price,Quantity,Available,RentQnt from booktable where Publication = '" + selectedPublication + "' and Branch = '" + selectedBranch + "';" ;
					rs = stmt.executeQuery(query2);
					
				}
				else 
				{
					rs = stmt.executeQuery(query1);
				}
				
				
				while (rs.next()) 
				{
					
					for (int i = 0; i < 5; i++) 
					{
						
							bookReportData[i] = rs.getString(i+1);
						
					}
					
					bookTableModel.addRow(bookReportData);
					
					
				}
				
			
				informationPanel.bookTableScrollPane.setViewportView(informationPanel.bookTable);
				informationPanel.bookTableScrollPane.setVisible(true);
				informationPanel.bookTableTitle.setText(bookTableModel.getRowCount() + " Books Found");
				informationPanel.bookTableTitleString = informationPanel.bookTableTitle.getText();
				informationPanel.viewBookButton.setVisible(true);
				informationPanel.bookTableTitle.setVisible(true);
				
				informationPanel.subInformationPanel.revalidate();
				informationPanel.subInformationPanel.repaint();
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
		}
		
		if (ae.getSource() == informationPanel.viewPublicationButton) 
		{
			
			if (informationPanel.selectPublicationComboBox.getSelectedItem() == null )
			{
				
				JOptionPane.showMessageDialog(informationPanel, "Please select Publication !", "Error", JOptionPane.WARNING_MESSAGE);
				return;
				
			}
			
			if (informationPanel.bookDetailsPanel.isDisplayable()) 
			{
				informationPanel.subInformationPanel.remove(informationPanel.bookDetailsPanel);
			}
			
			String[] bookReportColumns = {"BookName","Price","Quantity","Available","RentQnt"};
			String[] bookReportData = new String[5];
			
			informationPanel.remove_bookTableRows(bookReportColumns);
			
			String selectedPublication = informationPanel.selectPublicationComboBox.getSelectedItem().toString();
			String selectedBranch = "";
			
			String query1 = "select BookName,Price,Quantity,Available,RentQnt from booktable where Publication = '"+ selectedPublication +"';";
						
			DefaultTableModel bookTableModel = (DefaultTableModel) informationPanel.bookTable.getModel();
			bookTableModel.setColumnIdentifiers(bookReportColumns);
			
			
			try 
			{
				Statement stmt = LoginPage.con.createStatement();
				ResultSet rs = null;
				
				if (informationPanel.selectBranchComboBox.getSelectedItem() != null) 
				{
					selectedBranch = informationPanel.selectBranchComboBox.getSelectedItem().toString();
					String query2 = "select BookName,Price,Quantity,Available,RentQnt from booktable where Branch = '"+ selectedBranch + "' and Publication = '" + selectedPublication + "';" ;
					rs = stmt.executeQuery(query2);
					
				}
				else 
				{
					rs = stmt.executeQuery(query1);
				}
				
				while (rs.next()) 
				{
					
					for (int i = 0; i < 5; i++) 
					{
						
							bookReportData[i] = rs.getString(i+1);
						
					}
					
					bookTableModel.addRow(bookReportData);
					
					
				}
					
				
				informationPanel.bookTableScrollPane.setViewportView(informationPanel.bookTable);
				informationPanel.bookTableScrollPane.setVisible(true);
				informationPanel.bookTableTitle.setText(bookTableModel.getRowCount() + " Books Found");
				informationPanel.bookTableTitleString = informationPanel.bookTableTitle.getText();
				informationPanel.viewBookButton.setVisible(true);
				informationPanel.bookTableTitle.setVisible(true);
				
				informationPanel.subInformationPanel.revalidate();
				informationPanel.subInformationPanel.repaint();
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
		}
		
		
		if (ae.getSource()  == informationPanel.viewBookButton) 
		{
			
			int column = 0;
			int row = informationPanel.bookTable.getSelectedRow();
			String value = "";
			if (row < 0) 
			{
				JOptionPane.showMessageDialog(informationPanel, "Please select the book first !", "Error", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			informationPanel.bookTableScrollPane.setVisible(false);
			informationPanel.bookTableTitle.setText("Book Details");
			informationPanel.viewBookButton.setVisible(false);
			
			informationPanel.bookDetailsPanel = new JPanel(new GridBagLayout());
			informationPanel.bookDetailsPanel.setBackground(Color.WHITE);
			
			value = informationPanel.bookTable.getModel().getValueAt(row, column).toString();
			
			informationPanel.bookBackButton = new JButton("Back");
			informationPanel.style_button(informationPanel.bookBackButton);
			informationPanel.bookBackButton.addActionListener(this);
			
			JLabel bookNameLabel = new JLabel("Book Name : "),
					authorLabel = new JLabel("Author : "),
					publicationLabel = new JLabel("Publication : "),
					branchLabel = new JLabel("Branch : "),
					priceLabel = new JLabel("Price : "),
					totalQntLabel = new JLabel("Total Quantity : "),
					availableQntLabel = new JLabel("Available : "),
					rentQntLabel = new JLabel("Rented : "),
					imageLabel = new JLabel();
					
					
			JLabel bookNameValue,authorValue,publicationValue,branchValue,priceValue,totalQntValue,availableQntValue,rentQntValue;
			
			ImageIcon imageIcon = null;
			
			imageLabel.setSize(new Dimension(300, 300));
			bookNameLabel.setForeground(informationPanel.darkGreen);
			bookNameLabel.setFont(informationPanel.boldFont7);
			authorLabel.setForeground(informationPanel.darkGreen);
			authorLabel.setFont(informationPanel.boldFont7);
			publicationLabel.setForeground(informationPanel.darkGreen);
			publicationLabel.setFont(informationPanel.boldFont7);
			branchLabel.setForeground(informationPanel.darkGreen);
			branchLabel.setFont(informationPanel.boldFont7);
			priceLabel.setForeground(informationPanel.darkGreen);
			priceLabel.setFont(informationPanel.boldFont7);
			totalQntLabel.setForeground(informationPanel.darkGreen);
			totalQntLabel.setFont(informationPanel.boldFont7);
			availableQntLabel.setForeground(informationPanel.darkGreen);
			availableQntLabel.setFont(informationPanel.boldFont7);
			rentQntLabel.setForeground(informationPanel.darkGreen);
			rentQntLabel.setFont(informationPanel.boldFont7);
		
			String query = "select BookName,Author,Publication,Branch,Price,Quantity,Available,RentQnt,Image from bookTable where BookName='" + value + "';";
			ResultSet rs = null;
			
			try 
			{
				Statement stmt = LoginPage.con.createStatement();
				rs = stmt.executeQuery(query);
				rs.next();
				
				
				bookNameValue = new JLabel(rs.getString(1));
				authorValue = new JLabel(rs.getString(2));
				publicationValue = new JLabel(rs.getString(3));
				branchValue = new JLabel(rs.getString(4));
				priceValue = new JLabel(rs.getString(5));
				totalQntValue = new JLabel(rs.getString(6));
				availableQntValue = new JLabel(rs.getString(7));
				rentQntValue = new JLabel(rs.getString(8));
				imageIcon = new ImageIcon(rs.getString(9));
				Image image = imageIcon.getImage().getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
				imageIcon = new ImageIcon(image);
				imageLabel.setIcon(imageIcon);
				
				bookNameValue.setFont(informationPanel.boldFont7);
				bookNameValue.setForeground(Color.BLACK);
				authorValue.setForeground(Color.BLACK);
				authorValue.setFont(informationPanel.boldFont7);
				publicationValue.setForeground(Color.BLACK);
				publicationValue.setFont(informationPanel.boldFont7);
				branchValue.setForeground(Color.BLACK);
				branchValue.setFont(informationPanel.boldFont7);
				priceValue.setForeground(Color.BLACK);
				priceValue.setFont(informationPanel.boldFont7);
				totalQntValue.setForeground(Color.BLACK);
				totalQntValue.setFont(informationPanel.boldFont7);
				availableQntValue.setForeground(Color.BLACK);
				availableQntValue.setFont(informationPanel.boldFont7);
				rentQntValue.setForeground(Color.BLACK);
				rentQntValue.setFont(informationPanel.boldFont7);
				
				informationPanel.bookDetailsPanel.add(imageLabel, new GridBagConstraints(0, 0, 1, 8, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.BOTH, new Insets(10, 10, 0, 0), 0, 0));
				informationPanel.bookDetailsPanel.add(bookNameLabel, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPanel.add(bookNameValue, new GridBagConstraints(2, 0, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPanel.add(authorLabel, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPanel.add(authorValue, new GridBagConstraints(2, 1, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPanel.add(publicationLabel, new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPanel.add(publicationValue, new GridBagConstraints(2, 2, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPanel.add(branchLabel, new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPanel.add(branchValue, new GridBagConstraints(2, 3, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPanel.add(priceLabel, new GridBagConstraints(1, 4, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPanel.add(priceValue, new GridBagConstraints(2, 4, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPanel.add(totalQntLabel, new GridBagConstraints(1, 5, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPanel.add(totalQntValue, new GridBagConstraints(2, 5, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPanel.add(availableQntLabel, new GridBagConstraints(1, 6, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPanel.add(availableQntValue, new GridBagConstraints(2, 6, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPanel.add(rentQntLabel, new GridBagConstraints(1, 7, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPanel.add(rentQntValue, new GridBagConstraints(2, 7, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPanel.add(informationPanel.bookBackButton, new GridBagConstraints(0, 8, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 10, 5, 0), 50, 10));
				
				informationPanel.subInformationPanel.add(informationPanel.bookDetailsPanel, new GridBagConstraints(0, 2, 8, 1, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));

				
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
			

		}
		
		
		if (ae.getSource() == informationPanel.bookBackButton) 
		{
			
			informationPanel.bookDetailsPanel.removeAll();
			informationPanel.subInformationPanel.remove(informationPanel.bookDetailsPanel);
			
			informationPanel.bookTableScrollPane.setVisible(true);
			informationPanel.viewBookButton.setVisible(true);
			informationPanel.bookTableTitle.setText(informationPanel.bookTableTitleString);
			
		}
		
		if (ae.getSource() == informationPanel.selectIssueBookButton) 
		{
			
			if (informationPanel.selectBookIssueBookComboBox.getSelectedItem() == null || informationPanel.selectPublicationIssueBookComboBox.getSelectedItem() == null) 
			{
				JOptionPane.showMessageDialog(informationPanel.subInformationPanel, "Please select the book first !", "Error", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			informationPanel.bookDetailsIssueBookPanel.removeAll();
			
			selectedBook = informationPanel.selectBookIssueBookComboBox.getSelectedItem().toString();
			
			
			JLabel bookDetailsTitleLabel = new JLabel("View Book Details",JLabel.CENTER),
					bookNameLabel = new JLabel("Book Name : "),
					authorLabel = new JLabel("Author : "),
					publicationLabel = new JLabel("Publication : "),
					branchLabel = new JLabel("Branch : "),
					priceLabel = new JLabel("Price : "),
					totalQntLabel = new JLabel("Total Quantity : "),
					availableQntLabel = new JLabel("Available : "),
					rentQntLabel = new JLabel("Rented : "),
					imageLabel = new JLabel();
					
					
			JLabel bookNameValue,authorValue,publicationValue,branchValue,priceValue,totalQntValue,availableQntValue,rentQntValue;
			
			ImageIcon imageIcon = null;
			
			
			bookDetailsTitleLabel.setForeground(Color.WHITE);
			bookDetailsTitleLabel.setBackground(new Color(0, 150, 150));
			bookDetailsTitleLabel.setFont(informationPanel.boldFont5);
			bookDetailsTitleLabel.setOpaque(true);
			imageLabel.setSize(new Dimension(300, 300));
			bookNameLabel.setForeground(informationPanel.darkGreen);
			bookNameLabel.setFont(informationPanel.boldFont7);
			authorLabel.setForeground(informationPanel.darkGreen);
			authorLabel.setFont(informationPanel.boldFont7);
			publicationLabel.setForeground(informationPanel.darkGreen);
			publicationLabel.setFont(informationPanel.boldFont7);
			branchLabel.setForeground(informationPanel.darkGreen);
			branchLabel.setFont(informationPanel.boldFont7);
			priceLabel.setForeground(informationPanel.darkGreen);
			priceLabel.setFont(informationPanel.boldFont7);
			totalQntLabel.setForeground(informationPanel.darkGreen);
			totalQntLabel.setFont(informationPanel.boldFont7);
			availableQntLabel.setForeground(informationPanel.darkGreen);
			availableQntLabel.setFont(informationPanel.boldFont7);
			rentQntLabel.setForeground(informationPanel.darkGreen);
			rentQntLabel.setFont(informationPanel.boldFont7);
						
			String query = "select BookName,Author,Publication,Branch,Price,Quantity,Available,RentQnt,Image from bookTable where BookName='" + this.selectedBook + "';";
			Statement stmt = null;
			ResultSet rs = null;
			
			try 
			{
				
				stmt = LoginPage.con.createStatement();
				rs = stmt.executeQuery(query);
				rs.next();
				
				bookNameValue = new JLabel(rs.getString(1));
				this.selectedBook = rs.getString(1);
				authorValue = new JLabel(rs.getString(2));
				publicationValue = new JLabel(rs.getString(3));
				this.selectedPublication = rs.getString(3);
				branchValue = new JLabel(rs.getString(4));
				priceValue = new JLabel(rs.getString(5));
				totalQntValue = new JLabel(rs.getString(6));
				availableQntValue = new JLabel(rs.getString(7));
				rentQntValue = new JLabel(rs.getString(8));
				imageIcon = new ImageIcon(rs.getString(9));
				Image image = imageIcon.getImage().getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
				imageIcon = new ImageIcon(image);
				imageLabel.setIcon(imageIcon);
				
				bookNameValue.setFont(informationPanel.boldFont7);
				bookNameValue.setForeground(Color.BLACK);
				authorValue.setForeground(Color.BLACK);
				authorValue.setFont(informationPanel.boldFont7);
				publicationValue.setForeground(Color.BLACK);
				publicationValue.setFont(informationPanel.boldFont7);
				branchValue.setForeground(Color.BLACK);
				branchValue.setFont(informationPanel.boldFont7);
				priceValue.setForeground(Color.BLACK);
				priceValue.setFont(informationPanel.boldFont7);
				totalQntValue.setForeground(Color.BLACK);
				totalQntValue.setFont(informationPanel.boldFont7);
				availableQntValue.setForeground(Color.BLACK);
				availableQntValue.setFont(informationPanel.boldFont7);
				rentQntValue.setForeground(Color.BLACK);
				rentQntValue.setFont(informationPanel.boldFont7);
				
				informationPanel.bookDetailsIssueBookPanel.add(bookDetailsTitleLabel, new GridBagConstraints(0, 0, 3, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 2, 0, 2), 0, 15));
				informationPanel.bookDetailsIssueBookPanel.add(imageLabel, new GridBagConstraints(0, 1, 1, 9, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.BOTH, new Insets(10, 10, 0, 0), 0, 0));
				informationPanel.bookDetailsIssueBookPanel.add(bookNameLabel, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsIssueBookPanel.add(bookNameValue, new GridBagConstraints(2, 1, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsIssueBookPanel.add(authorLabel, new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsIssueBookPanel.add(authorValue, new GridBagConstraints(2, 2, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsIssueBookPanel.add(publicationLabel, new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsIssueBookPanel.add(publicationValue, new GridBagConstraints(2, 3, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsIssueBookPanel.add(branchLabel, new GridBagConstraints(1, 4, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsIssueBookPanel.add(branchValue, new GridBagConstraints(2, 4, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsIssueBookPanel.add(priceLabel, new GridBagConstraints(1, 5, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsIssueBookPanel.add(priceValue, new GridBagConstraints(2, 5, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsIssueBookPanel.add(totalQntLabel, new GridBagConstraints(1, 6, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsIssueBookPanel.add(totalQntValue, new GridBagConstraints(2, 6, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsIssueBookPanel.add(availableQntLabel, new GridBagConstraints(1, 7, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsIssueBookPanel.add(availableQntValue, new GridBagConstraints(2, 7, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsIssueBookPanel.add(rentQntLabel, new GridBagConstraints(1, 8, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsIssueBookPanel.add(rentQntValue, new GridBagConstraints(2, 8, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				
				informationPanel.subBookDetailsIssueBookPanel.add(informationPanel.selectBranchIssueBookLabel,new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 4, 0), 0, 0));
				informationPanel.subBookDetailsIssueBookPanel.add(informationPanel.selectBranchIssueBookComboBox,new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 4, 0), 0, 0));
				informationPanel.subBookDetailsIssueBookPanel.add(informationPanel.selectStudentIssueBookLabel,new GridBagConstraints(2, 0, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 4, 0), 0, 0));
				informationPanel.subBookDetailsIssueBookPanel.add(informationPanel.selectStudentIssueBookComboBox,new GridBagConstraints(3, 0, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 4, 0), 0, 0));
				informationPanel.subBookDetailsIssueBookPanel.add(informationPanel.daysIssueBookLabel,new GridBagConstraints(4, 0, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 4, 0), 0, 0));
				informationPanel.subBookDetailsIssueBookPanel.add(informationPanel.daysIssueBookTextField,new GridBagConstraints(5, 0, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 4, 0), 0, 5));
				informationPanel.subBookDetailsIssueBookPanel.add(informationPanel.issueBookButton,new GridBagConstraints(6, 0, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 4, 0), 20, 10));
				
				informationPanel.subInformationPanel.add(informationPanel.bookDetailsIssueBookPanel, new GridBagConstraints(0, 2, 4, 1, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));
				informationPanel.subInformationPanel.add(informationPanel.subBookDetailsIssueBookPanel, new GridBagConstraints(0, 3, 4, 1, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				
				informationPanel.subInformationPanel.revalidate();
				informationPanel.subInformationPanel.repaint();
	
								
			} 
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			
		}
		
		if (ae.getSource() == informationPanel.issueBookButton) 
		{
			
			if (informationPanel.selectBranchIssueBookComboBox.getSelectedItem() == null || informationPanel.selectStudentIssueBookComboBox.getSelectedItem() == null )
			{
				JOptionPane.showMessageDialog(informationPanel, "Please select the Student !", "Warning", JOptionPane.WARNING_MESSAGE);
				return;
			}
			else if (informationPanel.daysIssueBookTextField.getText().equals(""))
			{
				JOptionPane.showMessageDialog(informationPanel, "Please Enter the number of Days !", "Warning", JOptionPane.WARNING_MESSAGE);
				return;
			}
			else if (Integer.parseInt(informationPanel.daysIssueBookTextField.getText()) > 10 ) 
			{
				JOptionPane.showMessageDialog(informationPanel, "Max Days allowed <= 10 !", "Warning", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			
			String selectedBranch = informationPanel.selectBranchIssueBookComboBox.getSelectedItem().toString();
			String selectedStudent = informationPanel.selectStudentIssueBookComboBox.getSelectedItem().toString();
			int selectedDays = Integer.parseInt(informationPanel.daysIssueBookTextField.getText());
			
			PreparedStatement pstmt = null;
			
			String getSidQuery = "select SID from studenttable where StudentName = '" + selectedStudent + "' and BranchName = '" + selectedBranch + "';";
			String insertIntoRentQuery = "insert into renttable(BookName,SID,Days,IssueDate,ReturnDate,PenaltyStatus) values(?,?,?,current_timestamp,null,0)";
			String updateBookQuery = "update booktable set Available = Available - 1, RentQnt = RentQnt + 1 where BookName = '" + this.selectedBook + "' and Publication = '" + this.selectedPublication + "';";
			String checkAvailableBookQuery = "select Available from booktable where BookName = '" + this.selectedBook + "' and Publication = '" + this.selectedPublication + "';";
			
			Statement stmt = null;
			ResultSet rs = null;
			
			try 
			{
				stmt = LoginPage.con.createStatement();
				
				rs = stmt.executeQuery(getSidQuery);
				rs.next();
				this.sID = rs.getInt(1);
				System.out.println(rs.getInt(1));
				
				rs = stmt.executeQuery(checkAvailableBookQuery);
				rs.next();
				
				if (rs.getInt(1) > 0) 
				{

					String checkAlreadyBookTakenQuery = "select count(*) from renttable where BookName = '" + this.selectedBook + "' and SID = " + this.sID + " and ReturnDate is null;";
					rs = stmt.executeQuery(checkAlreadyBookTakenQuery);
					rs.next();
					
					if (rs.getInt(1) > 0) 
					{
						
						JOptionPane.showMessageDialog(informationPanel.subInformationPanel, this.selectedBook + " has already been Issued by you !", "Warning", JOptionPane.WARNING_MESSAGE);
						return;
						
					}
					
					String checkPenaltyPaidQuery = "select count(*) from renttable where BookName = '" + this.selectedBook + "' and SID = " + this.sID + " and PenaltyStatus > 0 and ReturnDate is not null;";
					rs = stmt.executeQuery(checkPenaltyPaidQuery);
					rs.next();
					
					if (rs.getInt(1) > 0) 
					{
						
						JOptionPane.showMessageDialog(informationPanel.subInformationPanel, "Please Pay the Penalty Amount\nof this Book to Issue !", "Warning", JOptionPane.WARNING_MESSAGE);
						return;
						
					}
					
					stmt.executeUpdate(updateBookQuery);
						
					pstmt = LoginPage.con.prepareStatement(insertIntoRentQuery);
					pstmt.setString(1, this.selectedBook);
					pstmt.setInt(2, this.sID);
					pstmt.setInt(3, selectedDays);
					pstmt.executeUpdate();
					
					JOptionPane.showMessageDialog(informationPanel.subInformationPanel, this.selectedBook + " Issued SuccessFully !", "Success", JOptionPane.INFORMATION_MESSAGE);
					
				}
				else 
				{
					JOptionPane.showMessageDialog(informationPanel.subInformationPanel, this.selectedBook + " Not Available !", "Out Of Stock", JOptionPane.WARNING_MESSAGE);
					
				}

			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
						
			informationPanel.selectBranchIssueBookComboBox.setSelectedIndex(-1);
			informationPanel.selectStudentIssueBookComboBox.setSelectedIndex(-1);
			informationPanel.daysIssueBookTextField.setText("");

			
		}
		
		
		
		if (ae.getSource() == informationPanel.viewBranchReportButton) 
		{
			
			if (informationPanel.studentBranchReportComboBox.getSelectedItem() == null )
			{
				
				JOptionPane.showMessageDialog(informationPanel, "Please select Branch !", "Error", JOptionPane.WARNING_MESSAGE);
				return;
				
			}

			if (informationPanel.studentDetailsPanel.isDisplayable()) 
			{
				informationPanel.subInformationPanel.remove(informationPanel.studentDetailsPanel);
			}
			
			
			String[] studentReportColumns = {"StudentName","BranchName","Mobile","Gender"};
			String[] studentReportData = new String[4];
			
			informationPanel.remove_studentTableRows(studentReportColumns);
			
			String selectedBranch = informationPanel.studentBranchReportComboBox.getSelectedItem().toString();
			String selectedStudent = "";
			
			String query1 = "select StudentName,BranchName,Mobile,Gender from studenttable where BranchName = '"+ selectedBranch +"';";
						
			DefaultTableModel studentTableModel = (DefaultTableModel) informationPanel.studentTable.getModel();
			studentTableModel.setColumnIdentifiers(studentReportColumns);
			
			try 
			{
				Statement stmt = LoginPage.con.createStatement();
				ResultSet rs = null;
				
				if (!(informationPanel.studentNameReportTextField.getText().equals(""))) 
				{
					selectedStudent = informationPanel.studentNameReportTextField.getText();
					String query2 = "select StudentName,BranchName,Mobile,Gender from studenttable where BranchName = '" + selectedBranch + "' and StudentName like '" + selectedStudent + "%';" ;
					rs = stmt.executeQuery(query2);
					
				}
				else 
				{
					rs = stmt.executeQuery(query1);
				}
				
				
				while (rs.next()) 
				{
					
					for (int i = 0; i < 4; i++) 
					{
						
							studentReportData[i] = rs.getString(i+1);
						
					}
					
					studentTableModel.addRow(studentReportData);
					
					
				}
				
			
				informationPanel.studentTableScrollPane.setViewportView(informationPanel.studentTable);
				informationPanel.studentTableScrollPane.setVisible(true);
				informationPanel.studentTableTitle.setText(studentTableModel.getRowCount() + " Students Found");
				informationPanel.studentTableTitleString = informationPanel.studentTableTitle.getText();
				informationPanel.viewStudentButton.setVisible(true);
				informationPanel.studentTableTitle.setVisible(true);
				
				informationPanel.subInformationPanel.revalidate();
				informationPanel.subInformationPanel.repaint();
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
		}

		if (ae.getSource() == informationPanel.viewStudentReportButton) 
		{
			
			if (informationPanel.studentNameReportTextField.getText().equals(""))
			{
				
				JOptionPane.showMessageDialog(informationPanel, "Please enter atleast one letter !", "Error", JOptionPane.WARNING_MESSAGE);
				return;
				
			}
			
			if (informationPanel.studentDetailsPanel.isDisplayable()) 
			{
				informationPanel.subInformationPanel.remove(informationPanel.studentDetailsPanel);
			}
			
			String[] studentReportColumns = {"StudentName","BranchName","Mobile","Gender"};
			String[] studentReportData = new String[4];
			
			informationPanel.remove_studentTableRows(studentReportColumns);
			
			String selectedBranch = informationPanel.studentBranchReportComboBox.getSelectedItem().toString();
			String selectedStudent = "";
			
			String query1 = "select StudentName,BranchName,Mobile,Gender from studenttable where BranchName = '"+ selectedBranch +"';";
						
			DefaultTableModel studentTableModel = (DefaultTableModel) informationPanel.studentTable.getModel();
			studentTableModel.setColumnIdentifiers(studentReportColumns);
			
			
			try 
			{
				Statement stmt = LoginPage.con.createStatement();
				ResultSet rs = null;
				
				if (!(informationPanel.studentNameReportTextField.getText().equals(""))) 
				{
					selectedStudent = informationPanel.studentNameReportTextField.getText();
					String query2 = "select Studentname,BranchName,Mobile,Gender from studenttable where BranchName = '"+ selectedBranch + "' and StudentName like '" + selectedStudent + "%';" ;
					rs = stmt.executeQuery(query2);
					
				}
				else 
				{
					rs = stmt.executeQuery(query1);
				}
				
				while (rs.next()) 
				{
					
					for (int i = 0; i < 4; i++) 
					{
						
							studentReportData[i] = rs.getString(i+1);
						
					}
					
					studentTableModel.addRow(studentReportData);
					
					
				}
					
				
				informationPanel.studentTableScrollPane.setViewportView(informationPanel.studentTable);
				informationPanel.studentTableScrollPane.setVisible(true);
				informationPanel.studentTableTitle.setText(studentTableModel.getRowCount() + " Students Found");
				informationPanel.studentTableTitleString = informationPanel.studentTableTitle.getText();
				informationPanel.viewStudentButton.setVisible(true);
				informationPanel.studentTableTitle.setVisible(true);
				
				informationPanel.subInformationPanel.revalidate();
				informationPanel.subInformationPanel.repaint();
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
		}
		
		
		if (ae.getSource()  == informationPanel.viewStudentButton) 
		{
			
			int column = 0;
			int row = informationPanel.studentTable.getSelectedRow();
			String value = "";
			if (row < 0) 
			{
				JOptionPane.showMessageDialog(informationPanel, "Please select the Student first !", "Error", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			informationPanel.studentTableScrollPane.setVisible(false);
			informationPanel.studentTableTitle.setText("Student Details");
			informationPanel.viewStudentButton.setVisible(false);
			
			informationPanel.studentDetailsPanel = new JPanel(new GridBagLayout());
			informationPanel.studentDetailsPanel.setBackground(new Color(255,255,255));
			
			value = informationPanel.studentTable.getModel().getValueAt(row, column).toString();
			
			informationPanel.studentBackButton = new JButton("Back");
			informationPanel.style_button(informationPanel.studentBackButton);
			informationPanel.studentBackButton.addActionListener(this);
			
			JLabel studentIdLabel = new JLabel("Student ID : "),
					studentNameLabel = new JLabel("Student Name : "),
					mobileLabel = new JLabel("Mobile : "),
					addressLabel = new JLabel("Address : "),
					cityLabel = new JLabel("City : "),
					pincodeLabel = new JLabel("Pincode : "),
					birthdateLabel = new JLabel("Birth Date : "),
					branchLabel = new JLabel("Branch : "),
					emailLabel = new JLabel("Email : "),
					passwordLabel = new JLabel("Password : "),
					imageLabel = new JLabel();;
					
			JLabel  studentIdValue,studentNameValue,mobileValue,addressValue,cityValue,pincodeValue,birthdateValue,branchValue,emailValue,passwordValue;
			
			ImageIcon imageIcon = null;
			
			imageLabel.setSize(new Dimension(250, 250));
			studentIdLabel.setForeground(informationPanel.darkGreen);
			studentIdLabel.setFont(informationPanel.boldFont7);
			studentNameLabel.setForeground(informationPanel.darkGreen);
			studentNameLabel.setFont(informationPanel.boldFont7);
			mobileLabel.setForeground(informationPanel.darkGreen);
			mobileLabel.setFont(informationPanel.boldFont7);
			addressLabel.setForeground(informationPanel.darkGreen);
			addressLabel.setFont(informationPanel.boldFont7);
			cityLabel.setForeground(informationPanel.darkGreen);
			cityLabel.setFont(informationPanel.boldFont7);
			pincodeLabel.setForeground(informationPanel.darkGreen);
			pincodeLabel.setFont(informationPanel.boldFont7);
			birthdateLabel.setForeground(informationPanel.darkGreen);
			birthdateLabel.setFont(informationPanel.boldFont7);
			branchLabel.setForeground(informationPanel.darkGreen);
			branchLabel.setFont(informationPanel.boldFont7);
			emailLabel.setForeground(informationPanel.darkGreen);
			emailLabel.setFont(informationPanel.boldFont7);
			passwordLabel.setForeground(informationPanel.darkGreen);
			passwordLabel.setFont(informationPanel.boldFont7);
		
			String query = "select SID,StudentName,Mobile,Address,City,Pincode,DOB,BranchName,Email,Password,Image from studentTable where StudentName='" + value + "';";
			ResultSet rs = null;
			
			try 
			{
				Statement stmt = LoginPage.con.createStatement();
				rs = stmt.executeQuery(query);
				rs.next();
				
				
				studentIdValue = new JLabel(rs.getString(1));
				studentNameValue = new JLabel(rs.getString(2));
				mobileValue = new JLabel(rs.getString(3));
				addressValue = new JLabel(rs.getString(4));
				cityValue = new JLabel(rs.getString(5));
				pincodeValue = new JLabel(rs.getString(6));
				birthdateValue = new JLabel(rs.getString(7));
				branchValue = new JLabel(rs.getString(8));
				emailValue = new JLabel(rs.getString(9));
				passwordValue = new JLabel(rs.getString(10));
				imageIcon = new ImageIcon(rs.getString(11));
				Image image = imageIcon.getImage().getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
				imageIcon = new ImageIcon(image);
				imageLabel.setIcon(imageIcon);
				
				studentIdValue.setFont(informationPanel.boldFont7);
				studentIdValue.setForeground(Color.BLACK);
				studentNameValue.setForeground(Color.BLACK);
				studentNameValue.setFont(informationPanel.boldFont7);
				mobileValue.setForeground(Color.BLACK);
				mobileValue.setFont(informationPanel.boldFont7);
				addressValue.setForeground(Color.BLACK);
				addressValue.setFont(informationPanel.boldFont7);
				cityValue.setForeground(Color.BLACK);
				cityValue.setFont(informationPanel.boldFont7);
				pincodeValue.setForeground(Color.BLACK);
				pincodeValue.setFont(informationPanel.boldFont7);
				birthdateValue.setForeground(Color.BLACK);
				birthdateValue.setFont(informationPanel.boldFont7);
				branchValue.setForeground(Color.BLACK);
				branchValue.setFont(informationPanel.boldFont7);
				emailValue.setForeground(Color.BLACK);
				emailValue.setFont(informationPanel.boldFont7);
				passwordValue.setForeground(Color.BLACK);
				passwordValue.setFont(informationPanel.boldFont7);
				
				informationPanel.studentDetailsPanel.add(imageLabel, new GridBagConstraints(0, 0, 1, 10, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.BOTH, new Insets(10, 10, 0, 0), 0, 0));
				informationPanel.studentDetailsPanel.add(studentIdLabel, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.studentDetailsPanel.add(studentIdValue, new GridBagConstraints(2, 0, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.studentDetailsPanel.add(studentNameLabel, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.studentDetailsPanel.add(studentNameValue, new GridBagConstraints(2, 1, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.studentDetailsPanel.add(mobileLabel, new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.studentDetailsPanel.add(mobileValue, new GridBagConstraints(2, 2, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.studentDetailsPanel.add(addressLabel, new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.studentDetailsPanel.add(addressValue, new GridBagConstraints(2, 3, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.studentDetailsPanel.add(cityLabel, new GridBagConstraints(1, 4, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.studentDetailsPanel.add(cityValue, new GridBagConstraints(2, 4, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.studentDetailsPanel.add(pincodeLabel, new GridBagConstraints(1, 5, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.studentDetailsPanel.add(pincodeValue, new GridBagConstraints(2, 5, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.studentDetailsPanel.add(birthdateLabel, new GridBagConstraints(1, 6, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.studentDetailsPanel.add(birthdateValue, new GridBagConstraints(2, 6, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.studentDetailsPanel.add(branchLabel, new GridBagConstraints(1, 7, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.studentDetailsPanel.add(branchValue, new GridBagConstraints(2, 7, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.studentDetailsPanel.add(emailLabel, new GridBagConstraints(1, 8, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.studentDetailsPanel.add(emailValue, new GridBagConstraints(2, 8, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.studentDetailsPanel.add(passwordLabel, new GridBagConstraints(1, 9, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.studentDetailsPanel.add(passwordValue, new GridBagConstraints(2, 9, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.studentDetailsPanel.add(informationPanel.studentBackButton, new GridBagConstraints(0, 10, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 10, 5, 0), 50, 10));
				
				informationPanel.subInformationPanel.add(informationPanel.studentDetailsPanel, new GridBagConstraints(0, 2, 8, 1, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));

				
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			informationPanel.subInformationPanel.revalidate();
			informationPanel.subInformationPanel.repaint();

		}
		
		
		if (ae.getSource() == informationPanel.studentBackButton) 
		{
			
			informationPanel.studentDetailsPanel.removeAll();
			informationPanel.subInformationPanel.remove(informationPanel.studentDetailsPanel);
			
			informationPanel.studentTableScrollPane.setVisible(true);
			informationPanel.viewStudentButton.setVisible(true);
			informationPanel.studentTableTitle.setText(informationPanel.studentTableTitleString);
			
		}
		
		
		if (ae.getSource() == informationPanel.selectReturnBookButton) 
		{
			if (informationPanel.selectStudentReturnBookComboBox.getSelectedItem() == null || informationPanel.selectBookReturnBookComboBox.getSelectedItem() == null) 
			{
				JOptionPane.showMessageDialog(informationPanel, "Please select the Book first !", "Error", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			informationPanel.bookDetailsReturnBookPanel.removeAll();
			
			this.selectedBook = informationPanel.selectBookReturnBookComboBox.getSelectedItem().toString();
			String selectedStudent = informationPanel.selectStudentReturnBookComboBox.getSelectedItem().toString();
			
			
			
			JLabel bookDetailsTitleLabel = new JLabel("View Book Details",JLabel.CENTER),
					bookNameLabel = new JLabel("Book Name : "),
					authorLabel = new JLabel("Author : "),
					publicationLabel = new JLabel("Publication : "),
					branchLabel = new JLabel("Branch : "),
					priceLabel = new JLabel("Price : "),
					studentNameLabel = new JLabel("Student Name : "),
					daysLabel = new JLabel("Days : "),
					issueDateLabel = new JLabel("Issue Date : "),
					penaltyLabel = new JLabel("Penalty : "),
					imageLabel = new JLabel();
					
					
			JLabel bookNameValue,authorValue,publicationValue,branchValue,priceValue,studentNameValue,daysValue,issueDateValue,penaltyValue;
			
			ImageIcon imageIcon = null;
			
			
			bookDetailsTitleLabel.setForeground(Color.WHITE);
			bookDetailsTitleLabel.setBackground(new Color(0, 150, 150));
			bookDetailsTitleLabel.setFont(informationPanel.boldFont5);
			bookDetailsTitleLabel.setOpaque(true);
			imageLabel.setSize(new Dimension(300, 300));
			bookNameLabel.setForeground(informationPanel.darkGreen);
			bookNameLabel.setFont(informationPanel.boldFont7);
			authorLabel.setForeground(informationPanel.darkGreen);
			authorLabel.setFont(informationPanel.boldFont7);
			publicationLabel.setForeground(informationPanel.darkGreen);
			publicationLabel.setFont(informationPanel.boldFont7);
			branchLabel.setForeground(informationPanel.darkGreen);
			branchLabel.setFont(informationPanel.boldFont7);
			
			priceLabel.setForeground(informationPanel.darkGreen);
			priceLabel.setFont(informationPanel.boldFont7);
			studentNameLabel.setForeground(informationPanel.darkGreen);
			studentNameLabel.setFont(informationPanel.boldFont7);
			daysLabel.setForeground(informationPanel.darkGreen);
			daysLabel.setFont(informationPanel.boldFont7);
			issueDateLabel.setForeground(informationPanel.darkGreen);
			issueDateLabel.setFont(informationPanel.boldFont7);
			penaltyLabel.setForeground(informationPanel.darkGreen);
			penaltyLabel.setFont(informationPanel.boldFont7);			
			
			
			String query1 = "select SID from studenttable where StudentName = '" + selectedStudent + "';";
			String query2 = "select BookName,Author,Publication,Branch,Price,Image from bookTable where BookName='" + this.selectedBook + "';";
			
			
			
			Statement stmt = null;
			ResultSet rs = null;
			
			try 
			{
				
				stmt = LoginPage.con.createStatement();
				rs = stmt.executeQuery(query1);
				rs.next();
				this.sID = rs.getInt(1);
				
				rs = stmt.executeQuery(query2);
				rs.next();
				bookNameValue = new JLabel(rs.getString(1));
				authorValue = new JLabel(rs.getString(2));
				publicationValue = new JLabel(rs.getString(3));
				branchValue = new JLabel(rs.getString(4));
				priceValue = new JLabel(rs.getString(5));
				imageIcon = new ImageIcon(rs.getString(6));
				Image image = imageIcon.getImage().getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
				imageIcon = new ImageIcon(image);
				imageLabel.setIcon(imageIcon);
				
				
				String query3 = "select Days,IssueDate from renttable where SID = " + this.sID + " and BookName = '" + this.selectedBook + "' and ReturnDate is null ;";
				rs = stmt.executeQuery(query3);
				rs.next();
				
				studentNameValue = new JLabel(selectedStudent);
				daysValue = new JLabel(rs.getInt(1) + "");
				issueDateValue = new JLabel(rs.getString(2));
				this.penalty = informationPanel.calculate_penalty(this.sID,this.selectedBook);
				penaltyValue = new JLabel(this.penalty + "");
				
				
				bookNameValue.setFont(informationPanel.boldFont7);
				bookNameValue.setForeground(Color.BLACK);
				authorValue.setForeground(Color.BLACK);
				authorValue.setFont(informationPanel.boldFont7);
				publicationValue.setForeground(Color.BLACK);
				publicationValue.setFont(informationPanel.boldFont7);
				branchValue.setForeground(Color.BLACK);
				branchValue.setFont(informationPanel.boldFont7);
				priceValue.setForeground(Color.BLACK);
				priceValue.setFont(informationPanel.boldFont7);
				studentNameValue.setForeground(Color.BLACK);
				studentNameValue.setFont(informationPanel.boldFont7);
				daysValue.setForeground(Color.BLACK);
				daysValue.setFont(informationPanel.boldFont7);
				issueDateValue.setForeground(Color.BLACK);
				issueDateValue.setFont(informationPanel.boldFont7);
				penaltyValue.setForeground(Color.BLACK);
				penaltyValue.setFont(informationPanel.boldFont7);
				
				informationPanel.bookDetailsReturnBookPanel.add(bookDetailsTitleLabel, new GridBagConstraints(0, 0, 3, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 2, 0, 2), 0, 15));
				informationPanel.bookDetailsReturnBookPanel.add(imageLabel, new GridBagConstraints(0, 1, 1, 9, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.BOTH, new Insets(10, 10, 0, 0), 0, 0));
				informationPanel.bookDetailsReturnBookPanel.add(bookNameLabel, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsReturnBookPanel.add(bookNameValue, new GridBagConstraints(2, 1, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsReturnBookPanel.add(authorLabel, new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsReturnBookPanel.add(authorValue, new GridBagConstraints(2, 2, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsReturnBookPanel.add(publicationLabel, new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsReturnBookPanel.add(publicationValue, new GridBagConstraints(2, 3, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsReturnBookPanel.add(branchLabel, new GridBagConstraints(1, 4, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsReturnBookPanel.add(branchValue, new GridBagConstraints(2, 4, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsReturnBookPanel.add(priceLabel, new GridBagConstraints(1, 5, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsReturnBookPanel.add(priceValue, new GridBagConstraints(2, 5, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsReturnBookPanel.add(studentNameLabel, new GridBagConstraints(1, 6, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsReturnBookPanel.add(studentNameValue, new GridBagConstraints(2, 6, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsReturnBookPanel.add(daysLabel, new GridBagConstraints(1, 7, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsReturnBookPanel.add(daysValue, new GridBagConstraints(2, 7, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsReturnBookPanel.add(issueDateLabel, new GridBagConstraints(1, 8, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsReturnBookPanel.add(issueDateValue, new GridBagConstraints(2, 8, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsReturnBookPanel.add(penaltyLabel, new GridBagConstraints(1, 9, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsReturnBookPanel.add(penaltyValue, new GridBagConstraints(2, 9, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsReturnBookPanel.add(informationPanel.returnBookButton, new GridBagConstraints(0, 10, 3, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(20, 0, 10, 0), 40, 10));

				
				informationPanel.subInformationPanel.add(informationPanel.bookDetailsReturnBookPanel, new GridBagConstraints(0, 2, 4, 1, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));
				
				informationPanel.subInformationPanel.revalidate();
				informationPanel.subInformationPanel.repaint();
	
								
			} 
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			
		}
	
		
		if (ae.getSource() == informationPanel.returnBookButton) 
		{
			
			String query1 = " update renttable set ReturnDate = current_timestamp , PenaltyStatus = ? where SID = " + this.sID + " and BookName = '" + this.selectedBook + "' and ReturnDate is null;";
			String query2 = "update booktable set Available = Available + 1, RentQnt = RentQnt - 1 where BookName = '" + this.selectedBook + "';";
			
			PreparedStatement pstmt = null;
			Statement stmt = null;
			
			
			try 
			{
				
				pstmt = LoginPage.con.prepareStatement(query1);
				pstmt.setInt(1, this.penalty);
				pstmt.executeUpdate();
				
				stmt = LoginPage.con.createStatement();
				stmt.executeUpdate(query2);
				
				JOptionPane.showMessageDialog(informationPanel, this.selectedBook + " returned Successfully !", "Success", JOptionPane.INFORMATION_MESSAGE);
				
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
				
			}
			
			informationPanel.bookDetailsReturnBookPanel.removeAll();
			informationPanel.subInformationPanel.remove(informationPanel.bookDetailsReturnBookPanel);
			
			informationPanel.selectStudentReturnBookComboBox.setSelectedItem(null);
			informationPanel.selectBookReturnBookComboBox.setSelectedItem(null);
			
			informationPanel.subInformationPanel.revalidate();
			informationPanel.subInformationPanel.repaint();	
			
			
		}
		
		
		
		if (ae.getSource() == informationPanel.selectPenaltyButton) 
		{
			
			
			if (informationPanel.selectStudentPenaltyComboBox.getSelectedItem() == null || informationPanel.selectBookPenaltyComboBox.getSelectedItem() == null) 
			{
				JOptionPane.showMessageDialog(informationPanel, "Please select the Book first !", "Error", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			informationPanel.bookDetailsPenaltyPanel.removeAll();
			
			this.selectedBook = informationPanel.selectBookPenaltyComboBox.getSelectedItem().toString();
			String selectedStudent = informationPanel.selectStudentPenaltyComboBox.getSelectedItem().toString();
			
			
			
			JLabel bookDetailsTitleLabel = new JLabel("View Book Details",JLabel.CENTER),
					bookNameLabel = new JLabel("Book Name : "),
					authorLabel = new JLabel("Author : "),
					publicationLabel = new JLabel("Publication : "),
					branchLabel = new JLabel("Branch : "),
					priceLabel = new JLabel("Price : "),
					studentNameLabel = new JLabel("Student Name : "),
					daysLabel = new JLabel("Days : "),
					issueDateLabel = new JLabel("Issue Date : "),
					//penaltyLabel = new JLabel("Penalty : "),
					penaltyAmountLabel = new JLabel("Penalty Amount : "),
					reasonLabel = new JLabel("Reason : "),
					imageLabel = new JLabel();
					
					
			JLabel bookNameValue,authorValue,publicationValue,branchValue,priceValue,studentNameValue,daysValue,issueDateValue;//penaltyValue;
			JTextField penaltyAmountTextField ;
			JTextArea reasonTextArea = new JTextArea(3,	10);
			
			ImageIcon imageIcon = null;
			
			bookDetailsTitleLabel.setForeground(Color.WHITE);
			bookDetailsTitleLabel.setBackground(new Color(0, 150, 150));
			bookDetailsTitleLabel.setFont(informationPanel.boldFont5);
			bookDetailsTitleLabel.setOpaque(true);
			imageLabel.setSize(new Dimension(300, 300));
			bookNameLabel.setForeground(informationPanel.darkGreen);
			bookNameLabel.setFont(informationPanel.boldFont7);
			authorLabel.setForeground(informationPanel.darkGreen);
			authorLabel.setFont(informationPanel.boldFont7);
			publicationLabel.setForeground(informationPanel.darkGreen);
			publicationLabel.setFont(informationPanel.boldFont7);
			branchLabel.setForeground(informationPanel.darkGreen);
			branchLabel.setFont(informationPanel.boldFont7);
			
			priceLabel.setForeground(informationPanel.darkGreen);
			priceLabel.setFont(informationPanel.boldFont7);
			studentNameLabel.setForeground(informationPanel.darkGreen);
			studentNameLabel.setFont(informationPanel.boldFont7);
			daysLabel.setForeground(informationPanel.darkGreen);
			daysLabel.setFont(informationPanel.boldFont7);
			issueDateLabel.setForeground(informationPanel.darkGreen);
			issueDateLabel.setFont(informationPanel.boldFont7);
			//penaltyLabel.setForeground(informationPanel.darkGreen);
			//penaltyLabel.setFont(informationPanel.boldFont7);	
			penaltyAmountLabel.setForeground(informationPanel.darkGreen);
			penaltyAmountLabel.setFont(informationPanel.boldFont7);	
			reasonLabel.setForeground(informationPanel.darkGreen);
			reasonLabel.setFont(informationPanel.boldFont7);	
			
			
			String query1 = "select SID from studenttable where StudentName = '" + selectedStudent + "';";
			String query2 = "select BookName,Author,Publication,Branch,Price,Image from bookTable where BookName='" + this.selectedBook + "';";
			
			
			
			Statement stmt = null;
			ResultSet rs = null;
			
			try 
			{
				
				stmt = LoginPage.con.createStatement();
				
				rs = stmt.executeQuery(query1);
				rs.next();
				this.sID = rs.getInt(1);
				
				rs = stmt.executeQuery(query2);
				rs.next();
				bookNameValue = new JLabel(rs.getString(1));
				authorValue = new JLabel(rs.getString(2));
				publicationValue = new JLabel(rs.getString(3));
				branchValue = new JLabel(rs.getString(4));
				priceValue = new JLabel(rs.getString(5));
				imageIcon = new ImageIcon(rs.getString(6));
				Image image = imageIcon.getImage().getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
				imageIcon = new ImageIcon(image);
				imageLabel.setIcon(imageIcon);
				
				
				String query3 = "select Days,IssueDate,PenaltyStatus from renttable where SID = " + this.sID + " and BookName = '" + this.selectedBook + "' and ReturnDate is not null and PenaltyStatus > 0 ;";
				rs = stmt.executeQuery(query3);
				rs.next();
				
				studentNameValue = new JLabel(selectedStudent);
				daysValue = new JLabel(rs.getInt(1) + "");
				issueDateValue = new JLabel(rs.getString(2));
				//this.penalty = informationPanel.calculate_penalty(this.sID,this.selectedBook);
				penaltyAmountTextField = new JTextField(rs.getInt(3) + "", 5);
				penaltyAmountTextField.setEditable(false);
				//penaltyValue = new JLabel(this.penalty + "");
				
				
				bookNameValue.setFont(informationPanel.boldFont7);
				bookNameValue.setForeground(Color.BLACK);
				authorValue.setForeground(Color.BLACK);
				authorValue.setFont(informationPanel.boldFont7);
				publicationValue.setForeground(Color.BLACK);
				publicationValue.setFont(informationPanel.boldFont7);
				branchValue.setForeground(Color.BLACK);
				branchValue.setFont(informationPanel.boldFont7);
				priceValue.setForeground(Color.BLACK);
				priceValue.setFont(informationPanel.boldFont7);
				studentNameValue.setForeground(Color.BLACK);
				studentNameValue.setFont(informationPanel.boldFont7);
				daysValue.setForeground(Color.BLACK);
				daysValue.setFont(informationPanel.boldFont7);
				issueDateValue.setForeground(Color.BLACK);
				issueDateValue.setFont(informationPanel.boldFont7);
				//penaltyValue.setForeground(Color.BLACK);
				//penaltyValue.setFont(informationPanel.boldFont7);
				informationPanel.style_textField(penaltyAmountTextField);
				informationPanel.style_textArea(reasonTextArea);
				
				
				informationPanel.bookDetailsPenaltyPanel.add(bookDetailsTitleLabel, new GridBagConstraints(0, 0, 3, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 15));
				informationPanel.bookDetailsPenaltyPanel.add(imageLabel, new GridBagConstraints(0, 1, 1, 9, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.BOTH, new Insets(10, 10, 0, 0), 0, 0));
				informationPanel.bookDetailsPenaltyPanel.add(bookNameLabel, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPenaltyPanel.add(bookNameValue, new GridBagConstraints(2, 1, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPenaltyPanel.add(authorLabel, new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPenaltyPanel.add(authorValue, new GridBagConstraints(2, 2, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPenaltyPanel.add(publicationLabel, new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPenaltyPanel.add(publicationValue, new GridBagConstraints(2, 3, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPenaltyPanel.add(branchLabel, new GridBagConstraints(1, 4, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPenaltyPanel.add(branchValue, new GridBagConstraints(2, 4, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPenaltyPanel.add(priceLabel, new GridBagConstraints(1, 5, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPenaltyPanel.add(priceValue, new GridBagConstraints(2, 5, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPenaltyPanel.add(studentNameLabel, new GridBagConstraints(1, 6, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPenaltyPanel.add(studentNameValue, new GridBagConstraints(2, 6, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPenaltyPanel.add(daysLabel, new GridBagConstraints(1, 7, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPenaltyPanel.add(daysValue, new GridBagConstraints(2, 7, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPenaltyPanel.add(issueDateLabel, new GridBagConstraints(1, 8, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPenaltyPanel.add(issueDateValue, new GridBagConstraints(2, 8, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				//informationPanel.bookDetailsPenaltyPanel.add(penaltyLabel, new GridBagConstraints(1, 9, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				//informationPanel.bookDetailsPenaltyPanel.add(penaltyValue, new GridBagConstraints(2, 9, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPenaltyPanel.add(penaltyAmountLabel, new GridBagConstraints(1, 9, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				informationPanel.bookDetailsPenaltyPanel.add(penaltyAmountTextField, new GridBagConstraints(2, 9, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 10, 5));
				//informationPanel.bookDetailsPenaltyPanel.add(reasonLabel, new GridBagConstraints(1, 10, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				//informationPanel.bookDetailsPenaltyPanel.add(reasonTextArea, new GridBagConstraints(2, 10, 1, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 100, 20));
				informationPanel.bookDetailsPenaltyPanel.add(informationPanel.payNowButton, new GridBagConstraints(2, 10, 3, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(20, 0, 10, 0), 40, 10));

				
				informationPanel.subInformationPanel.add(informationPanel.bookDetailsPenaltyPanel, new GridBagConstraints(0, 2, 4, 1, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));
				
				informationPanel.subInformationPanel.revalidate();
				informationPanel.subInformationPanel.repaint();
	
								
			} 
			catch (SQLException e)
			{
				e.printStackTrace();
			}

			
			
		}
	}
		
}



