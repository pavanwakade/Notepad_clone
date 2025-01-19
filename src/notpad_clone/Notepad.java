package notpad_clone;

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

	JFrame frame;
	JMenuBar menuBar;
	JTextArea textArea;
	String fontStyle = "Arial";
	Font arial, sansSerif, consolas, newRoman, Monospaced;;

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

	public void createFrame() {
		frame = new JFrame("Notepad");
		frame.setSize(700, 500);

		Image logo = Toolkit.getDefaultToolkit()
				.getImage("D:\\coding with pavan\\Qspiders\\java\\notpad\\res\\temp\\Logo.jpg");
		frame.setIconImage(logo);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

	public void createTextArea() {
		textArea = new JTextArea();
		frame.add(textArea);
		textArea.setFont(new Font("Arial", Font.PLAIN, 18));
	}

	public void createSrollBars() {
		JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		frame.add(scroll);

	}

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

	public void createFileMenuItems() {

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

		itemNewWindow = new JMenuItem("New Window");
		itemNewWindow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Notepad n1 = new Notepad();
				n1.frame.setTitle("Untitled");

			}
		});
		fileMenu.add(itemNewWindow);

		itemOpen = new JMenuItem("Open");
		itemOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				FileDialog fd = new FileDialog(frame, "Open", FileDialog.LOAD);
				fd.setVisible(true);

				// For read the data from file
				String path = fd.getDirectory();
				String filename = fd.getFile();
				System.out.println(filename);
				if (filename != null) {

					frame.setTitle(filename);
					openPath = path;
					openFileName = filename;
				}

				BufferedReader br = null;
				try {
					br = new BufferedReader(new FileReader(path + filename));

					String sentence = br.readLine();
					textArea.setText("");
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

		itemSave = new JMenuItem("Save");
		itemSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (openFileName != null && openPath != null) {
					writeDataToFile(openFileName, openPath);
				} else {
					FileDialog fd = new FileDialog(frame, "Save As", FileDialog.SAVE);
					fd.setVisible(true);

					String path = fd.getDirectory();
					String filename = fd.getFile();

					if (filename != null && path != null) {
						writeDataToFile(filename, path);
						openFileName = filename;
						openPath = path;
						frame.setTitle(openPath);
					}
				}
			}
		});
		fileMenu.add(itemSave);

		itemSaveAs = new JMenuItem("Save As");

		itemSaveAs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				FileDialog fd = new FileDialog(frame, "Save As", FileDialog.SAVE);

				fd.setVisible(true);

				String path = fd.getDirectory();

				String filename = fd.getFile();

				if (path != null & filename != null) {

					writeDataToFile(filename, path);
					openFileName = filename;
					openPath = path;
					frame.setTitle(openPath);
				}

			}
		});

		fileMenu.add(itemSaveAs);

		itemExit = new JMenuItem("Exit");
		fileMenu.add(itemExit);
		itemExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				frame.dispose();
			}
		});

	}

	JMenuItem itemArial, itemMonospaced, itemTimesNewRoman, itemConsolas;

	public void fonts() {
		formatMenu.add(itemFont);

		itemArial = new JMenuItem("Arial");

		ActionFont(itemArial, "Arial");

		itemMonospaced = new JMenuItem("Monospaced");

		ActionFont(itemMonospaced, "Monospaced");

		itemTimesNewRoman = new JMenuItem("Times new Roman");

		ActionFont(itemTimesNewRoman, "Times new Roman");

		itemConsolas = new JMenuItem("Consolas");

		ActionFont(itemConsolas, "Consolas");

	}

	public void ActionFont(JMenuItem menu, String font) {
		menu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setfontStyle(font);
			}
		});
		itemFont.add(menu);
	}

	public void createFormatMenuItems() {
		wrap = true;
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
		fonts();

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

		JMenuItem size24 = new JMenuItem("24");

		size24.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(24);

			}
		});
		itemFontSize.add(size24);

		JMenuItem size28 = new JMenuItem("28");

		size28.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(28);
			}
		});
		itemFontSize.add(size28);

		JMenuItem size30 = new JMenuItem("30");

		size30.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(30);
			}
		});
		itemFontSize.add(size30);

		JMenuItem size32 = new JMenuItem("32");

		size32.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(32);
			}
		});
		itemFontSize.add(size32);

		JMenuItem size35 = new JMenuItem("35");

		size35.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(35);
			}
		});
		itemFontSize.add(size35);

		JMenuItem size40 = new JMenuItem("40");

		size40.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(40);
			}
		});
		itemFontSize.add(size40);
	}

	public void setFontSize(int size) {
		arial = new Font("Arial", Font.PLAIN, size);
		newRoman = new Font("Times new Roman", Font.PLAIN, size);
		consolas = new Font("Consolas", Font.PLAIN, size);
		Monospaced = new Font("Monospaced", Font.PLAIN, size);

		setfontStyle(fontStyle);
	}

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

	public void writeDataToFile(String filename, String path) {

		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(path + filename));

			String text = textArea.getText();
			bw.write(text);

		} catch (IOException e1) {

			JOptionPane.showMessageDialog(frame, "Data cannot be Written" + e1.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			;
		} finally {
			try {
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

	public void createCommandPromtItems() {
		itemCMD = new JMenuItem("Open Cmd");
		commandPromptMenu.add(itemCMD);

		itemCMD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
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

	JMenuItem itemjava, itemHTML, itemC, itemCPP;

	public void createLanguageItem() {
		itemjava = new JMenuItem("Java");
		LanguageMenu.add(itemjava);
		ActionLanguage(itemjava, "Java");

		itemHTML = new JMenuItem("HTML");
		LanguageMenu.add(itemHTML);
		ActionLanguage(itemHTML, "HTML");

		itemC = new JMenuItem("C");
		LanguageMenu.add(itemC);
		ActionLanguage(itemC, "C");

		itemCPP = new JMenuItem("CPP");
		LanguageMenu.add(itemCPP);
		ActionLanguage(itemCPP, "CPP");
	}

	public void ActionLanguage(JMenuItem menu, String language) {
		menu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setLangage(language);
			}
		});
	}

	public void setLangage(String lang) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader( "D:\\coding with pavan\\Qspiders\\java\\notpad\\res\\temp\\"+lang + "Format.txt"));

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
				br.close();
			} catch (IOException e1) {
//				JOptionPane.showMessageDialog(frame,"File could not be Open"+e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);  ;           

			} catch (NullPointerException e2) {
//				JOptionPane.showMessageDialog(frame,"File Not Found"+e2.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);  ;           

			}

		}
	}
}