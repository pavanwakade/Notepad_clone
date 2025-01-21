package notpad;

import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Spring;

public class Notepad {
	
	//Globle variable for Frame
	JFrame frame;
	
	//Globle variable for MenuBar
	JMenuBar menuBar;
	
	//Globle variable for TextArea
	JTextArea textArea;
	
	//Default Font Style
	String fontStyle = "Arial";
	
	//Fonts Used
	Font arial, sansSerif, consolas, newRoman, Monospaced;;

	//Default font Size
	int fontSize = 18;

	// Items for a Menubar
	JMenu fileMenu, LanguageMenu, formatMenu, commandPromptMenu;

	// Items for file Menu
	JMenuItem itemNew, itemNewWindow, itemOpen, itemSaveAs, itemSave, itemExit;

	// Items for Format Menu
	JMenuItem itemWordWrap, itemFont, itemFontSize;

	// Items for CommandPrompt
	JMenuItem itemCMD;

	String openPath = null;
	String openFileName = null;
	boolean wrap = false;

	// Constructor
	public Notepad() {
		createFrame();
		createTextArea();
		createSrollBars();
		createMenuBar();
		createFileMenuItems();
		createFormatMenuItems();
		createCommandPromtItems();
		createLanguageItem();
		setFontSize(fontSize);
	}

	
	 // Creates the main application GUI
	public void createFrame() {
		frame = new JFrame("Notepad");   //GUI with title
		
		// Set window size
		frame.setSize(700, 500);

		// Set window icon
		Image logo = Toolkit.getDefaultToolkit()
				.getImage("D:\\coding with pavan\\Qspiders\\java\\notpad_clone\\res\\logo.jpg");
		frame.setIconImage(logo);

		// Set close operation and make window visible
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

	
	// Creates the text editing area
	public void createTextArea() {
		textArea = new JTextArea();
		
		//Textarea Add to frame
		frame.add(textArea);
		
		// Set default font
		textArea.setFont(new Font("Arial", Font.PLAIN, 18));
	}

	
	
	 // Adds scroll bars to the text area when needed
	public void createSrollBars() {
		JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		frame.add(scroll);
	}

	
	
	
	// Creates the MenuBar on Window
	public void createMenuBar() {
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		LanguageMenu = new JMenu("Language");
		menuBar.add(LanguageMenu);

		formatMenu = new JMenu("Format");
		menuBar.add(formatMenu);

		commandPromptMenu = new JMenu("CommandPrompt");
		menuBar.add(commandPromptMenu);
	}

	
	// Creates all items in the File menu
	public void createFileMenuItems() {

		// Create menu item and action listener for  'New'
		itemNew = new JMenuItem("New");

		itemNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				frame.setTitle("Untitle-Notpad");
				openFileName = null;
				openPath = null;
			}
		});
		fileMenu.add(itemNew);

		
		
		// Create menu item and action listener for 'New Window'
		itemNewWindow = new JMenuItem("New Window");
		itemNewWindow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Notepad n1 = new Notepad();
				n1.frame.setTitle("Untitled");
			}
		});
		fileMenu.add(itemNewWindow);

		
		
		// Create menu item and action listener for "Open" file
		itemOpen = new JMenuItem("Open");
		itemOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				//create a dilog for loading file
				FileDialog fd = new FileDialog(frame, "Open", FileDialog.LOAD);
				fd.setVisible(true);

				// Get selected file path and name
				String path = fd.getDirectory();
				String filename = fd.getFile();
				System.out.println(filename);
				
				// Update window title and file info if file selected
				if (filename != null) {

					frame.setTitle(filename);
					openPath = path;
					openFileName = filename;
				}

				 // Read and display file contents from directory
				BufferedReader br = null;
				try {
					br = new BufferedReader(new FileReader(path + filename));

					String sentence = br.readLine();
					
					//Empty string for cleaning textarea
					textArea.setText("");  
					
					// Read file line by line
					while (sentence != null) {
						textArea.append(sentence + "\n");
						sentence = br.readLine();
					}
				} catch (FileNotFoundException e1) {

//					JOptionPane.showMessageDialog(frame, "File Not Found" + e1.getMessage(), "Error",
//							JOptionPane.ERROR_MESSAGE);
//					;
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(frame, "Data could not be read" + e1.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
					;

				} catch (NullPointerException e2) {
//					JOptionPane.showMessageDialog(frame, "File Not Found" + e2.getMessage(), "Error",
//							JOptionPane.ERROR_MESSAGE);
//					;

				} finally {
					try {
						// Close the reader
						br.close();
					} catch (IOException e1) {
//						JOptionPane.showMessageDialog(frame, "File could not be Open" + e1.getMessage(), "Error",
//								JOptionPane.ERROR_MESSAGE);
//						;

					} catch (NullPointerException e2) {
//						JOptionPane.showMessageDialog(frame, "File Not Found" + e2.getMessage(), "Error",
//								JOptionPane.ERROR_MESSAGE);
//						;
					}
				}
			}
		});
		fileMenu.add(itemOpen);

		
		// Create  menu item and action listener for "Save As" 
		itemSave = new JMenuItem("Save");
		itemSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// Save file if location selected
				if (openFileName != null && openPath != null) {
					writeDataToFile(openFileName, openPath);
				} else {
					
					// Show Save As dialog
					FileDialog fd = new FileDialog(frame, "Save As", FileDialog.SAVE);
					fd.setVisible(true);

					String path = fd.getDirectory();
					String fileName = fd.getFile();

					
					if (fileName != null && path != null) {
						writeDataToFile(fileName, path);
						openFileName = fileName;
						openPath = path;
						frame.setTitle(openPath);
					}
				}
			}
		});
		fileMenu.add(itemSave);

		
		// Create menu item and ActionListener for "Save As"
		itemSaveAs = new JMenuItem("Save As");

		itemSaveAs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// Show Save As dialog
				FileDialog fd = new FileDialog(frame, "Save As", FileDialog.SAVE);
				fd.setVisible(true);

				String path = fd.getDirectory();

				String fileName = fd.getFile();

				// set title for window
				if (path != null & fileName != null) {
					writeDataToFile(fileName, path);
					openFileName = fileName;
					openPath = path;
					frame.setTitle(openPath);
				}

			}
		});
		fileMenu.add(itemSaveAs);

		
		// Create menu item and ActionListener for "Exit"
		itemExit = new JMenuItem("Exit");
		fileMenu.add(itemExit);
		itemExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				frame.dispose();
			}
		});
	}

	
	 // Creates all items in the Format menu
	public void createFormatMenuItems() {
		
		// set Default word wrap ON 
		wrap = true;
		
		
		// Create menu item and ActionListener for "word wrap"
		itemWordWrap = new JMenuItem("Word Wrap ON");
		formatMenu.add(itemWordWrap);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

		itemWordWrap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (wrap == false) {
					textArea.setLineWrap(true);
					textArea.setWrapStyleWord(true);
					wrap = true;
					itemWordWrap.setText("Word Wrap ON");
				} else {
					textArea.setLineWrap(false);
					textArea.setWrapStyleWord(false);
					wrap = false;
					itemWordWrap.setText("Word Wrap OFF");
				}
			}
		});

		itemFont = new JMenu("Font");

		
		
		// Create menu item and ActionListener for Arial font
		formatMenu.add(itemFont);

		JMenuItem itemArial = new JMenuItem("Arial");

		itemArial.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setfontStyle("Arial");
			}
		});
		itemFont.add(itemArial);

		// Create menu item and ActionListener for "Monospaced" font
		JMenuItem itemMonospaced = new JMenuItem("Monospaced");

		itemMonospaced.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setfontStyle("Monospaced");
			}
		});
		itemFont.add(itemMonospaced);

		
		// Create menu item and ActionListener for "Times new Roman" font
		JMenuItem itemTimesNewRoman = new JMenuItem("Times new Roman");

		itemTimesNewRoman.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setfontStyle("Times new Roman");
			}
		});
		itemFont.add(itemTimesNewRoman);

		
		// Create menu item and ActionListener for "Consolas" font
		JMenuItem itemConsolas = new JMenuItem("Consolas");

		itemConsolas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setfontStyle("Consolas");
			}
		});
		itemFont.add(itemConsolas);

		
		 // Add various font size options
        // Size 20
		itemFontSize = new JMenu("Font Size");
		formatMenu.add(itemFontSize);

		JMenuItem size20 = new JMenuItem("20");

		size20.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(20);
			}
		});

		itemFontSize.add(size20);

		
		
		// Size "24"
		JMenuItem size24 = new JMenuItem("24");

		size24.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(24);
			}
		});
		itemFontSize.add(size24);

		
		
		
		// Size "28"
		JMenuItem size28 = new JMenuItem("28");

		size28.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(28);
			}
		});
		itemFontSize.add(size28);

		
		
		
		// Size "30"
		JMenuItem size30 = new JMenuItem("30");

		size30.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(30);
			}
		});
		itemFontSize.add(size30);

		
		
		
		// Size "32"
		JMenuItem size32 = new JMenuItem("32");

		size32.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(32);
			}
		});
		itemFontSize.add(size32);

		
		
		// Size "35"
		JMenuItem size35 = new JMenuItem("35");

		size35.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(35);
			}
		});
		itemFontSize.add(size35);

		
		// Size "40"
		JMenuItem size40 = new JMenuItem("40");

		size40.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(40);
			}
		});
		itemFontSize.add(size40);
	}

	
	
	// Sets the font size for all available fonts
	public void setFontSize(int size) {
		
		 // set all fonts with the new size
		arial = new Font("Arial", Font.PLAIN, size);
		newRoman = new Font("Times new Roman", Font.PLAIN, size);
		consolas = new Font("Consolas", Font.PLAIN, size);
		Monospaced = new Font("Monospaced", Font.PLAIN, size);

		// Apply the current font style with new size
		setfontStyle(fontStyle);
	}

	

    // Sets the font style for the text area
	public void setfontStyle(String font) {

		fontStyle = font;
		switch (font) {
		case "Arial":
			textArea.setFont(arial);
			break;

		case "Monospaced":

			textArea.setFont(Monospaced);
			break;
		case "Times new Roman":

			textArea.setFont(newRoman);
			break;
		case "Consolas":

			textArea.setFont(consolas);
			break;
		default: {
			textArea.setFont(arial);
		  }
		}
	}

	
	
	 // Writes text area content to a file
	public void writeDataToFile(String fileName, String path) {

		// Create writer for the file
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(path + fileName));

			String text = textArea.getText();
			
			// Write text to file
			bw.write(text);

		} catch (IOException e1) {

			JOptionPane.showMessageDialog(frame, "Data cannot be Written" + e1.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			;
		} finally {
			try {
				
				// Close the writer
				bw.close();
			} catch (IOException e1) {

				JOptionPane.showMessageDialog(frame, "File cannot be close" + e1.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
				;
			} catch (NullPointerException e2) {

				JOptionPane.showMessageDialog(frame, "File not found to close" + e2.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
				;
			}
		}

	}

	
	// Creates the Command Prompt menu item
	public void createCommandPromtItems() {
		
		// Create menu item and ActionListener for "Open Cmd" 
		itemCMD = new JMenuItem("Open Cmd");
		commandPromptMenu.add(itemCMD);

		
		// Create menu item and ActionListener for "Open Cmd" font
		itemCMD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Open CMD in current file location or default location
				try {
					if (openPath != null) {
						Runtime.getRuntime().exec(new String[] { "cmd", "/k", "start" }, null, new File(openPath));
					} else {
						Runtime.getRuntime().exec(new String[] { "cmd", "/k", "start" }, null, null);
					}
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(frame, "Could not Launch Command Prompt" + e1.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
					;
				}
			}
		});
	}

	
	
	 // Creates the Language menu items
	public void createLanguageItem() {
		
		
		// Add Java language template
		JMenuItem itemjava = new JMenuItem("Java");
		LanguageMenu.add(itemjava);
		itemjava.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setLangage("Java");
			}
		});

		
		
		// Add HTML language template
		JMenuItem itemHTML = new JMenuItem("HTML");
		LanguageMenu.add(itemHTML);
		itemHTML.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setLangage("HTML");
			}
		});

		
		
		// Add C language template
		JMenuItem itemC = new JMenuItem("C");
		LanguageMenu.add(itemC);
		itemC.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setLangage("C");
			}
		});

		
		
		// Add "CPP" language template
		JMenuItem itemCPP = new JMenuItem("CPP");
		LanguageMenu.add(itemCPP);
		itemCPP.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setLangage("CPP");
			}
		});
	}

	
	
	// Loads language template from file
	public void setLangage(String lang) {
		BufferedReader br = null;
		try {
			 // Read template file for selected language from location
			br = new BufferedReader(
					new FileReader("D:\\coding with pavan\\Qspiders\\java\\notpad_clone\\res\\" + lang + "Format.txt"));

			
			// Read and display template content
			String sentence = br.readLine();
			textArea.setText("");
			while (sentence != null) {
				textArea.append(sentence + "\n");
				sentence = br.readLine();
			}
		} catch (FileNotFoundException e1) {

			JOptionPane.showMessageDialog(frame, "File Not Found" + e1.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			;
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(frame, "Data could not be read" + e1.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			;
		} catch (NullPointerException e2) {

		} finally {
			try {
				
				// Close the reader
				br.close();
			} catch (IOException e1) {
//				JOptionPane.showMessageDialog(frame,"File could not be Open"+e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);  ;           

			} catch (NullPointerException e2) {
//				JOptionPane.showMessageDialog(frame,"File Not Found"+e2.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);  ;           

			}

		}
	}
}
