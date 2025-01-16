package notepad_clone;

import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Notepad {

	JFrame frame ;
	JTextArea textArea ;
	
	JMenuBar menuBar ;
	
	JMenu fileMenu ,editMenu ,formatMenu,commandPromptMenu ;
	
	// File menu items
	JMenuItem itemNew,itemNewWindow,itemOpen,itemSaveAs,itemSave,itemExit ;
	
	//Format menu items
	
	JMenuItem itemWordWrap , itemFont, itemFontSize ;
	
	// Command Prompt item
	
	JMenuItem itemCMD ;
	
	String openPath = null ;
	String openFileName = null ;
	
	boolean wrap = false ;
	
	Font arial, newRoman, consolas ;
	
	String fontstyle = "Arial";
	
	public Notepad() {
		createFrame();
		createTextArea();
		createScrollBars();
		createMenuBar();
		createFileMenuItems();
		createFormatItems();
		
		createCommandPromptItem();
		
		
	}
	public void createFrame()
	{
		frame = new JFrame("Notepad") ;
		
		frame.setSize(1200,700);
		Image icon =Toolkit.getDefaultToolkit().getImage("C:\\Users\\91909\\Desktop\\NotepadLogo.jpg");
		
		frame.setIconImage(icon);
		frame.setVisible(true);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void createTextArea()
	{
		textArea = new JTextArea();
		
		textArea.setFont(new Font("Arial",Font.PLAIN,25));
		
		frame.add(textArea);
	}
	
	public void createScrollBars()
	{
		JScrollPane scroll = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frame.add(scroll);
	}
	
	public void createMenuBar()
	{
		menuBar = new JMenuBar() ;
		
		
		frame.setJMenuBar(menuBar);
		
		
		fileMenu = new JMenu("File") ;
		
		menuBar.add(fileMenu);
		
		editMenu = new JMenu("Edit") ;
		
		menuBar.add(editMenu);
		
		formatMenu = new JMenu("Format") ;
		
		menuBar.add(formatMenu);
		
		commandPromptMenu = new JMenu("Command Prompt") ;
		
		menuBar.add(commandPromptMenu);
 	}
	
	public void createFileMenuItems()
	{
		
		itemNew = new JMenuItem("New") ;
		
		itemNew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				textArea.setText("");
				frame.setTitle("Untitled Notepad");
				
				openFileName = null ;
				openPath = null ;
				
			}
		});
		
		fileMenu.add(itemNew) ;
		
		itemNewWindow = new JMenuItem("New Window") ;
		
		itemNewWindow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Notepad n1 = new Notepad() ;
				n1.frame.setTitle("Untitled");
			}
		});
		
		fileMenu.add(itemNewWindow) ;
		
		itemOpen = new JMenuItem("Open") ;
		
		itemOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				FileDialog fd = new FileDialog(frame,"Open",FileDialog.LOAD) ;
				
				fd.setVisible(true);
				
				String path = fd.getDirectory() ;
				String filename = fd.getFile();
				
				System.out.println(filename);
				
				if (filename!=null) {
					
					frame.setTitle(filename);
					
					openFileName = filename ;
					openPath = path ;
				}
				
				BufferedReader br = null;
				
				try {
					br = new BufferedReader(new FileReader(path+filename)) ;
					
					String sentence = br.readLine() ;
					
					while (sentence!=null) {
						
						textArea.append(sentence+"\n");
						
						sentence = br.readLine() ;
					}
					
					
				} catch (FileNotFoundException e1) {
					
					System.out.println("File not found");
				} catch (IOException e1) {
					
					System.out.println("Data could not be read");
				}
				catch (NullPointerException e2) {
					
					
				}
				finally {
					
					try {
						br.close();
					} catch (IOException e1) {
						
						System.out.println("File could not be closed");
					}
					catch (NullPointerException e2) {
						
						
					}
				}
				
				
			}
		});
		
		fileMenu.add(itemOpen) ;
		
		itemSave = new JMenuItem("Save") ;
		
		itemSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (openFileName!=null && openPath!=null) {
					
					writeDataToFile(openFileName, openPath);
				}
				else
				{
					FileDialog fd = new FileDialog(frame,"Save As",FileDialog.SAVE);
					
					fd.setVisible(true);
					
					String path = 	fd.getDirectory() ;
						
					String filename =	fd.getFile() ;
					
					if (path!=null&filename!=null) {
						
						writeDataToFile(filename, path);
					}
				}
			}
		});
		
		fileMenu.add(itemSave) ;
		
		
		
		itemSaveAs = new JMenuItem("Save As") ;
		
		itemSaveAs.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				FileDialog fd = new FileDialog(frame,"Save As",FileDialog.SAVE);
				
				fd.setVisible(true);
				
			String path = 	fd.getDirectory() ;
				
			String filename =	fd.getFile() ;
			
			if (path!=null&filename!=null) {
				
				writeDataToFile(filename, path);
			}
				
			}
		});
		
		fileMenu.add(itemSaveAs) ;
		
		itemExit = new JMenuItem("Exit") ;
		
		itemExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				frame.dispose();
				
			}
		});
		
		fileMenu.add(itemExit) ;
		
	}
	
	public void createFormatItems()
	{
		itemWordWrap = new JMenuItem("Word Wrap Off");
		formatMenu.add(itemWordWrap);
		
		itemWordWrap.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (wrap==false) {
					
					textArea.setLineWrap(true) ;
					
					textArea.setWrapStyleWord(true) ;
					
					wrap = true ;
					
					itemWordWrap.setText("Word Wrap On");
				}
				else
				{
					textArea.setLineWrap(false) ;
					textArea.setWrapStyleWord(false) ;
					wrap = false ;
					
					itemWordWrap.setText("Word Wrap Off");
				}
				
				
			}
		});
		
		itemFont = new JMenu("Font");
		
		formatMenu.add(itemFont) ;
		
		JMenuItem itemArial = new JMenuItem("Arial");
		
		itemArial.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				setFontType("Arial");
				
			}
		});
		JMenuItem itemTimesNewRoman = new JMenuItem("Times new Roman") ;
		
		itemTimesNewRoman.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				setFontType("Times new Roman");
				
			}
		});
		JMenuItem itemConsolas = new JMenuItem("Consolas") ;
		
		itemConsolas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				setFontType("Consolas");
				
			}
		});
		itemFont.add(itemArial);
		itemFont.add(itemTimesNewRoman) ;
		itemFont.add(itemConsolas) ;
		
		itemFontSize = new JMenu("Font Size");
		
		formatMenu.add(itemFontSize) ;
		
		
		JMenuItem size10 = new JMenuItem("10") ;
		
		size10.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				setFontSize(10);
				
			}
		});
		
		itemFontSize.add(size10) ;
		
	JMenuItem size14 = new JMenuItem("14") ;
	
	size14.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			setFontSize(14);
			
		}
	});
			
			itemFontSize.add(size14) ;
			
	JMenuItem size18 = new JMenuItem("18") ;
	
	size18.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			setFontSize(18);
			
		}
	});
			
			itemFontSize.add(size10) ;
			
	JMenuItem size20 = new JMenuItem("20") ;
	
	size20.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			setFontSize(20);
			
		}
	});
			
			itemFontSize.add(size20) ;
			
	JMenuItem size22 = new JMenuItem("22") ;
	
	size22.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			setFontSize(22);
			
		}
	});
			
			itemFontSize.add(size22) ;
			
	JMenuItem size26 = new JMenuItem("26") ;
	
	size26.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			setFontSize(26);
			
		}
	});
			
			itemFontSize.add(size26) ;
			
	JMenuItem size30 = new JMenuItem("30") ;
	
	size30.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			setFontSize(30);
			
		}
	});
			
			itemFontSize.add(size30) ;
			
	JMenuItem size34 = new JMenuItem("34") ;
			
		size34.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				setFontSize(34);
				
			}
		});
			itemFontSize.add(size34) ;
			
			
			
			
		
	}
	
	public void setFontSize(int size)
	{
		 arial = new Font("Arial",Font.PLAIN,size) ;
		 newRoman = new Font("Times new Roman",Font.PLAIN, size);
		 consolas = new Font("Consolas",Font.PLAIN, size);
		 
		 setFontType(fontstyle);
	}
	
	public void setFontType(String font)
	{
		
		fontstyle = font ;
		switch (font) {
		case "Arial":
			
			textArea.setFont(arial);
			break;
		case "Times new Roman":
			
			textArea.setFont(newRoman);
			break ;
		case "Consolas":
			
			textArea.setFont(consolas);
			break ;

		default:
			break;
		}
	}
	public void createCommandPromptItem()
	{
		itemCMD = new JMenuItem("Open CMD") ;
		
		commandPromptMenu.add(itemCMD) ;
	}
	
	public void writeDataToFile(String filename,String path)
	{
		BufferedWriter bw = null ;
		try {
			 bw = new BufferedWriter(new FileWriter(path+filename)) ;
			
			String text = textArea.getText();
			
			bw.write(text);
			
			
		} catch (IOException e1) {

			
			System.out.println("Data cannot be written");
		}
		finally {
			
			try {
				bw.close() ;
			} catch (IOException e1) {
				
				System.out.println("File cannot be closed");
			}
			catch (NullPointerException e2) {
				
				System.out.println("File not found to close");
			}
		}
	}
}
