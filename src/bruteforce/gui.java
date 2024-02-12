package bruteforce;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.InputStream;
import java.text.NumberFormat;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;
import javax.swing.event.ChangeEvent;
import javax.swing.event.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


@SuppressWarnings("serial")
class MyFormatter extends NumberFormatter{

	@Override
	public Object stringToValue(String text) {
		if(text.equals("")) {
			return null;
		}else {
			try {
				return super.stringToValue(text);
			}catch(Exception e) {
				//System.out.println(text);
				int x = 0, mexi = 0;
				String s = this.getMaximum().toString();
				int cur = Integer.parseInt(s);
				for(int i = 0; i < text.length() - 1 && x <= cur; ++i) {
					mexi = x;
					x = x * 10 + Character.getNumericValue(text.charAt(i));
				}
				//System.out.print(text);
				//System.out.print(" ");
				//System.out.print(x);
				//System.out.println();
				if(x > cur) {
					x = mexi;
				}
				if(x == 0) {
					return null;
				}
				return x;
			}
		}
	}
}

class tokenValidator extends gui{
	
	public static char[] whitespaces = {' ', '\r', '\n'};
	
	public static boolean validator(String s) {
		return (s.length() == 2) && Character.isLetterOrDigit(s.charAt(0)) && Character.isLetterOrDigit(s.charAt(1));
	}
	
	public static boolean isWhiteSpace(char c) {
		for(int i = 0; i < whitespaces.length; ++i) {
			if(c == whitespaces[i]) {
				return true;
			}
		}
		return false;
	}
	
	public static String rightTrim(String s) {
		String ans = "";
		int i = s.length() - 1;
		for(; i >= 0; i--) {
			if(!isWhiteSpace(s.charAt(i))) {
				break;
			}
		}
		for(int j = 0; j <= i; ++j) {
			ans += s.charAt(j);
		}
		return ans;
	}
	
	public static boolean readFile(File f) {
		try(BufferedReader br = new BufferedReader(new FileReader(f))){
			int N = -1, M = -1, Q = -1, B = -1, cnt = 0;
			String line, matrixStr = "", seqStr = "";
			while((line = br.readLine()) != null) {
				if(line.equals("")) {
					continue;
				}
				String realLine = rightTrim(line);
				if(cnt == 0) {
					B = Integer.parseInt(realLine);
					cnt++;
				}else if(cnt == 1) {
					String[] twoNum = realLine.split(" ");
					if(twoNum.length != 2) {
						return false;
					}
					M = Integer.parseInt(twoNum[0]);
					N = Integer.parseInt(twoNum[1]);
					for(int i = 0; i < N; ++i) {
						String temp = br.readLine();
						if(temp == null) {
							return false;
						}
						String curRealLine = rightTrim(temp);
						String[] splitted = curRealLine.split(" ");
						if(splitted.length != M) {
							return false;
						}
						for(int j = 0; j < M; ++j) {
							if(!validator(splitted[j])) {
								return false;
							}
							matrixStr += splitted[j];
							if(j != M - 1) {
								matrixStr += " ";
							}
						}
						matrixStr += '\n';
					}
					cnt++;
				}else if(cnt == 2) {
					String[] oneNum = realLine.split(" ");
					if(oneNum.length != 1) {
						return false;
					}
					Q = Integer.parseInt(oneNum[0]);
					for(int i = 0; i < Q; ++i) {
						String temp = br.readLine();
						if(temp == null) {
							return false;
						}
						String curRealLine = rightTrim(temp);
						String[] splitted = curRealLine.split(" ");
						for(int j = 0; j < splitted.length; ++j) {
							if(!validator(splitted[j])) {
								return false;
							}
							seqStr += splitted[j];
							if(j != splitted.length - 1) {
								seqStr += " ";
							}
						}
						if(splitted.length < 2){
							return false;
						}
						seqStr += '\n';
						String num = br.readLine();
						if(num == null) {
							return false;
						}
						num = rightTrim(num);
						String[] numIntegerDK = num.split(" ");
						if(numIntegerDK.length != 1) {
							return false;
						}
						int curRew = Integer.parseInt(numIntegerDK[0]);
						seqStr += Integer.toString(curRew);
						seqStr += '\n';
					}
					cnt++;
				}else {
					return false;
				}
			}
			if(N < 0 || M < 0 || Q < 0 || B <= 0 || cnt != 3) {
				return false;
			}
			gui.jta.setText(matrixStr);
			gui.sjta.setText(seqStr);
			gui.jte.setText(Integer.toString(B));
			return true;
		}catch(Exception e) {
			return false;
		}
	}
}

class DialogRand extends gui implements ActionListener{
	
	JDialog dialRand;
	
	JFormattedTextField numToken, numBuf, nMat, mMat, numSeq, maxSeq, tokenAllowed;
		
	DialogRand(){
		dialRand = new JDialog(gui.frame, "RANDOMIZED INPUT", true);
		dialRand.setResizable(false);
		//dialRand.setLayout(new GridLayout(6, 1, 0, 10));
		dialRand.getContentPane().setBackground(Color.green);
		dialRand.setSize(400, 800);
		dialRand.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		JLabel mainLabel = new JLabel();
		mainLabel.setLayout(new GridLayout(8, 1, 0, 10));
		mainLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mainLabel.setBackground(new Color(26,24,33));
		mainLabel.setOpaque(true);
		
		JLabel numTokenL = new JLabel();
		numTokenL.setBackground(Color.magenta);
		numTokenL.setOpaque(false);
		numTokenL.setBorder(BorderFactory.createLineBorder(new Color(208,237,87), 5));
		numTokenL.setLayout(null);
		
		JLabel titlenumToken = new JLabel("NUMBER OF UNIQUE TOKEN (1-10)");
		titlenumToken.setFont(gui.customFont.deriveFont(12f));
		titlenumToken.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		titlenumToken.setBackground(new Color(208,237,87));
		titlenumToken.setOpaque(true);
		titlenumToken.setBounds(0, 0, 380, 20);
		numTokenL.add(titlenumToken);
		
		NumberFormat format = NumberFormat.getInstance();
	    
	    MyFormatter myf = new MyFormatter();
	    myf.setFormat(format);
	    myf.setValueClass(Integer.class);
	    myf.setMinimum(1);
	    myf.setMaximum(10);
	    myf.setAllowsInvalid(false);
	    myf.setCommitsOnValidEdit(true);
	    
	    MyFormatter max16 = new MyFormatter();
	    max16.setFormat(format);
	    max16.setValueClass(Integer.class);
	    max16.setMinimum(1);
	    max16.setMaximum(16);
	    max16.setAllowsInvalid(false);
	    max16.setCommitsOnValidEdit(true);
	    
	    MyFormatter max7 = new MyFormatter();
	    max7.setFormat(format);
	    max7.setValueClass(Integer.class);
	    max7.setMinimum(1);
	    max7.setMaximum(7);
	    max7.setAllowsInvalid(false);
	    max7.setCommitsOnValidEdit(true);
	    
	    MyFormatter max100 = new MyFormatter();
	    max100.setFormat(format);
	    max100.setValueClass(Integer.class);
	    max100.setMinimum(1);
	    max100.setMaximum(100);
	    max100.setAllowsInvalid(false);
	    max100.setCommitsOnValidEdit(true);
		
	    numToken = new JFormattedTextField(myf);
	    numToken.setBounds(160, 20, 100, 60);
	    numToken.setBackground(Color.gray);
	    numToken.setFont(customFont.deriveFont(40f));
	    numToken.setForeground(new Color(208,237,87));
	    numToken.setBorder(BorderFactory.createEmptyBorder());
	    numToken.setOpaque(false); 
	    numToken.setText("4");
	    numToken.setCaretColor(new Color(208,237,87));
	    
	    numTokenL.add(numToken);
		
	    JLabel token = new JLabel();
	    token.setBackground(Color.orange);
	    token.setOpaque(false);
	    token.setBorder(BorderFactory.createLineBorder(new Color(208,237,87), 5));
		token.setLayout(null);
	    
	    JLabel titleToken = new JLabel("TOKEN (SEPARATED BY SPACE)");
	    titleToken.setFont(gui.customFont.deriveFont(12f));
	    titleToken.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
	    titleToken.setBackground(new Color(208,237,87));
	    titleToken.setOpaque(true);
	    titleToken.setBounds(0, 0, 380, 20);
	    token.add(titleToken);
	    
	    tokenAllowed = new JFormattedTextField();
	    tokenAllowed.setBounds(30, 20, 300, 60);
	    tokenAllowed.setBackground(Color.gray);
	    tokenAllowed.setFont(customFont.deriveFont(40f));
	    tokenAllowed.setForeground(new Color(208,237,87));
	    tokenAllowed.setBorder(BorderFactory.createEmptyBorder());
	    tokenAllowed.setOpaque(false); 
	    tokenAllowed.setText("BD 7A 55 1C");
	    tokenAllowed.setCaretColor(new Color(208,237,87));
	    tokenAllowed.setHorizontalAlignment(SwingConstants.CENTER);

	    
	    token.add(tokenAllowed);
	    
		
		JLabel numBufL = new JLabel();
		numBufL.setBackground(Color.gray);
		numBufL.setOpaque(false);
		numBufL.setBorder(BorderFactory.createLineBorder(new Color(208,237,87), 5));
		numBufL.setLayout(null);
		
		JLabel titleBuf = new JLabel("NUMBER OF BUFFER (1-16)");
		titleBuf.setFont(gui.customFont.deriveFont(12f));
		titleBuf.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		titleBuf.setBackground(new Color(208,237,87));
		titleBuf.setOpaque(true);
		titleBuf.setBounds(0, 0, 380, 20);
	    numBufL.add(titleBuf);
	    
	    numBuf = new JFormattedTextField(max16);
	    numBuf.setBounds(160, 20, 100, 60);
	    numBuf.setBackground(Color.gray);
	    numBuf.setFont(customFont.deriveFont(40f));
	    numBuf.setForeground(new Color(208,237,87));
	    numBuf.setBorder(BorderFactory.createEmptyBorder());
	    numBuf.setOpaque(false); 
	    numBuf.setText("7");
	    numBuf.setCaretColor(new Color(208,237,87));
	    numBufL.add(numBuf);
		
		JLabel nMatL = new JLabel();
		nMatL.setBackground(Color.blue);
		nMatL.setOpaque(false);
		nMatL.setBorder(BorderFactory.createLineBorder(new Color(208,237,87), 5));
		nMatL.setLayout(null);
		
		JLabel titleN = new JLabel("MATRIX HEIGHT (1-7)");
		titleN.setFont(gui.customFont.deriveFont(12f));
		titleN.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		titleN.setBackground(new Color(208,237,87));
		titleN.setOpaque(true);
		titleN.setBounds(0, 0, 380, 20);
	    nMatL.add(titleN);
	    
	    nMat = new JFormattedTextField(max7);
	    nMat.setBounds(160, 20, 100, 60);
	    nMat.setBackground(Color.gray);
	    nMat.setFont(customFont.deriveFont(40f));
	    nMat.setForeground(new Color(208,237,87));
	    nMat.setBorder(BorderFactory.createEmptyBorder());
	    nMat.setOpaque(false); 
	    nMat.setText("5");
	    nMat.setCaretColor(new Color(208,237,87));
	    nMatL.add(nMat);

		JLabel mMatL = new JLabel();
		mMatL.setBackground(Color.cyan);
		mMatL.setOpaque(false);
		mMatL.setBorder(BorderFactory.createLineBorder(new Color(208,237,87), 5));
		mMatL.setLayout(null);
		
		JLabel titleM = new JLabel("MATRIX WIDTH (1-7)");
		titleM.setFont(gui.customFont.deriveFont(12f));
		titleM.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		titleM.setBackground(new Color(208,237,87));
		titleM.setOpaque(true);
		titleM.setBounds(0, 0, 380, 20);
	    mMatL.add(titleM);
	    
	    mMat = new JFormattedTextField(max7);
	    mMat.setBounds(160, 20, 100, 60);
	    mMat.setBackground(Color.gray);
	    mMat.setFont(customFont.deriveFont(40f));
	    mMat.setForeground(new Color(208,237,87));
	    mMat.setBorder(BorderFactory.createEmptyBorder());
	    mMat.setOpaque(false); 
	    mMat.setText("5");
	    mMat.setCaretColor(new Color(208,237,87));
	    mMatL.add(mMat);

		JLabel numSeqL = new JLabel();
		numSeqL.setBackground(Color.red);
		numSeqL.setOpaque(false);
		numSeqL.setBorder(BorderFactory.createLineBorder(new Color(208,237,87), 5));
		numSeqL.setLayout(null);
		
		JLabel titleSeq = new JLabel("NUMBER OF SEQUENCES (1-16)");
		titleSeq.setFont(gui.customFont.deriveFont(12f));
		titleSeq.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		titleSeq.setBackground(new Color(208,237,87));
		titleSeq.setOpaque(true);
		titleSeq.setBounds(0, 0, 380, 20);
		numSeqL.add(titleSeq);
		
		numSeq = new JFormattedTextField(max16);
		numSeq.setBounds(160, 20, 100, 60);
		numSeq.setBackground(Color.gray);
		numSeq.setFont(customFont.deriveFont(40f));
		numSeq.setForeground(new Color(208,237,87));
		numSeq.setBorder(BorderFactory.createEmptyBorder());
		numSeq.setOpaque(false); 
		numSeq.setText("6");
		numSeq.setCaretColor(new Color(208,237,87));
	    numSeqL.add(numSeq);
		
		JLabel maxSeqL = new JLabel();
		maxSeqL.setBackground(Color.white);
		maxSeqL.setOpaque(false);
		maxSeqL.setBorder(BorderFactory.createLineBorder(new Color(208,237,87), 5));
		maxSeqL.setLayout(null);
		
		JLabel titleMaxSeq = new JLabel("MAXIMUM SEQUENCE LENGTH (2-7)");
		titleMaxSeq.setFont(gui.customFont.deriveFont(12f));
		titleMaxSeq.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		titleMaxSeq.setBackground(new Color(208,237,87));
		titleMaxSeq.setOpaque(true);
		titleMaxSeq.setBounds(0, 0, 380, 20);
		maxSeqL.add(titleMaxSeq);
		
		maxSeq = new JFormattedTextField(max7);
		maxSeq.setBounds(160, 20, 100, 60);
		maxSeq.setBackground(Color.gray);
		maxSeq.setFont(customFont.deriveFont(40f));
		maxSeq.setForeground(new Color(208,237,87));
		maxSeq.setBorder(BorderFactory.createEmptyBorder());
		maxSeq.setOpaque(false); 
		maxSeq.setText("4");
		maxSeq.setCaretColor(new Color(208,237,87));
	    maxSeqL.add(maxSeq);
		
	    JLabel buttonRandLabel = new JLabel();
	    buttonRandLabel.setBackground(Color.green);
	    buttonRandLabel.setOpaque(false);
	    buttonRandLabel.setLayout(new GridBagLayout());
	    
	    JButton butRandGo = new JButton("GO");
	    butRandGo.setPreferredSize(new Dimension(100, 80));
	    butRandGo.setOpaque(true);
	    butRandGo.setContentAreaFilled(true);
	    butRandGo.setBackground(new Color(26,24,33));
	    butRandGo.setBorder(BorderFactory.createLineBorder(new Color(252,238,12), 1 ));
	    butRandGo.setFont(customFont.deriveFont(20f));
	    butRandGo.setForeground(new Color(252,238,12));
	    butRandGo.setFocusPainted(false);
	    butRandGo.getModel().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				ButtonModel model = (ButtonModel) e.getSource();
				if(model.isRollover()) {
					butRandGo.setBackground(new Color(62,82,35));
				}else {
					butRandGo.setBackground(new Color(26,24,33));
				}
			}
		});
	    butRandGo.addActionListener(this);
	    
	    
	    buttonRandLabel.add(butRandGo);
	    
		mainLabel.add(numTokenL);
		mainLabel.add(token);
		mainLabel.add(numBufL);
		mainLabel.add(nMatL);
		mainLabel.add(mMatL);
		mainLabel.add(numSeqL);
		mainLabel.add(maxSeqL);
		mainLabel.add(buttonRandLabel);
		
		
		
		dialRand.add(mainLabel);
		dialRand.setVisible(true);
		
	}
	
	public static boolean seqExist(List<List<String>> listOfSeqs, List<String> seq) {
		for(int i = 0; i < listOfSeqs.size(); ++i) {
			if(listOfSeqs.get(i).size() != seq.size()) {
				continue;
			}
			boolean yeaExist = true;
			for(int j = 0; j < seq.size() && yeaExist; ++j) {
				if(seq.get(j) != listOfSeqs.get(i).get(j)) {
					yeaExist = false;
				}
			}
			if(yeaExist) {
				return true;
			}
		}
		return false;
	}
	
	public boolean processRandom() {
		String tokens = tokenAllowed.getText();
		String[] splitted = tokens.split(" ");
		for(int i = 0; i < splitted.length; ++i) {
			//System.out.println(splitted[i]);
			//System.out.println(splitted[i].length());
			if(!tokenValidator.validator(splitted[i])) {
				return false;
			}
		}
		if(splitted.length != Integer.parseInt(numToken.getText())) {
			return false;
		}
		gui.jte.setText(numBuf.getText());
		int N = Integer.parseInt(nMat.getText());
		int M = Integer.parseInt(mMat.getText());
		int Q = Integer.parseInt(numSeq.getText());
		int Maxi = Integer.parseInt(maxSeq.getText());
		if(Maxi == 1){
			return false;
		}
		int T = Integer.parseInt(numToken.getText());
		Random ran = new Random();
		String matrixStr = "";
		for(int i = 0; i < N; ++i) {
			for(int j = 0; j < M; ++j) {
				matrixStr += splitted[ran.nextInt(T)];
				if(j != M - 1) {
					matrixStr += " ";
				}
			}
			matrixStr += '\n';
		}
		String seqStr = "";
		List<List<String>> listOfSeqs = new ArrayList<List<String>>();
		for(int i = 0; i < Q; ++i) {
			boolean exist = true;
			int cntRedo = 0;
			while(exist) {
				if(cntRedo == 1000) {
					return false;
				}
				int len = ran.nextInt(Maxi) + 1;
				String seqNow = "";
				List<String> temp = new ArrayList<String>();
				for(int j = 0; j < len; ++j) {
					int curRand = ran.nextInt(T);
					seqNow += splitted[curRand];
					temp.add(splitted[curRand]);
					if(j != len - 1) {
						seqNow += " ";
					}
				}
				if(seqExist(listOfSeqs, temp)) {
					cntRedo++;
				}else {
					seqNow += '\n';
					seqNow += Integer.toString(ran.nextInt(50) + 1);
					seqNow += '\n';
					seqStr += seqNow;
					listOfSeqs.add(temp);
					exist = false;
				}
			}
		}
		gui.jta.setText(matrixStr);
		gui.sjta.setText(seqStr);
		return true;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(this.processRandom()) {
			dialRand.dispose();
		}else {
			JOptionPane.showMessageDialog(frame,
				    "TOKEN PARSING FAILED",
				    "ERROR",
				    JOptionPane.ERROR_MESSAGE);
		}
	}
}

class DisplayGraphics extends JComponent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int DimensionSq = 40;
	public static int N;
	public static int M;
	public static List<Integer> xi = new ArrayList<Integer>();
	public static List<Integer> yi = new ArrayList<Integer>();
	
	public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(font);
	    // Determine the X coordinate for the text
	    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
	    // Set the font
	    g.setFont(font);
	    // Draw the String
	    g.drawString(text, x, y);
	}
	
	public static int getCoord(int x) {
		return x * 50 + 20;
	}
	
	DisplayGraphics(int N, int M, List<Integer> xi, List<Integer> yi){
		setPreferredSize(new Dimension(DisplayGraphics.getCoord(M), DisplayGraphics.getCoord(N)));
		DisplayGraphics.N = N;
		DisplayGraphics.M = M;
		DisplayGraphics.xi = xi;
		DisplayGraphics.yi = yi;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(gui.customFont.deriveFont(20f));
		//FontMetrics metrics = g.getFontMetrics(gui.customFont.deriveFont(30f));
		g.setColor(new Color(252,238,12));
		Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(10));
		String matStr = gui.jta.getText();
		String[] perRow = matStr.split("\n");
		//List<List<Double>> distH = new ArrayList<List<Double>>();
		//List<List<Double>> distW = new ArrayList<List<Double>>();
		int N = perRow.length, M = 1;
		for(int i = 0; i < perRow.length; ++i) {
			String[] perCol = perRow[i].split(" ");
			M = perCol.length;
			/*List<Double> tempH = new ArrayList<Double>();
			List<Double> tempW = new ArrayList<Double>();
			for(int j = 0; j < perCol.length; ++j) {
				this.drawCenteredString(g, perCol[j], new Rectangle(j * 40 + 20, i * 40 + 20, 10, 10), gui.customFont.deriveFont(20f));				
			}
			distH.add(tempH);
			distW.add(tempW);
			System.out.println();*/
		}
		for(int i = 0; i < N; ++i) {
			for(int j = 0; j < M; ++j) {
				//g2.fillRect((j * 40) , (i * 40), 15, 15);
			}
		}
		
		for(int i = 0; i < perRow.length; ++i) {
			String[] perCol = perRow[i].split(" ");
			M = perCol.length;
			//List<Double> tempH = new ArrayList<Double>();
			//List<Double> tempW = new ArrayList<Double>();
			for(int j = 0; j < perCol.length; ++j) {
				this.drawCenteredString(g, perCol[j], new Rectangle(getCoord(j) , getCoord(i) , DimensionSq, DimensionSq), gui.customFont.deriveFont(25f));				
			}/*
			distH.add(tempH);
			distW.add(tempW);
			System.out.println();*/
		}
		
		float alpha = 0.75f;
		Color color = new Color(0.0f,(float)(184.0 / 255.0),1.0f, alpha); //Red 
		g2.setPaint(color);
		
		for(int i = 1; i < working.bufLeng; ++i) {
			int xFr = getCoord((xi.get(i) - 1)) + DimensionSq / 2;
			int yFr = getCoord((yi.get(i) - 1)) + DimensionSq / 2;
			int xTo = getCoord((xi.get(i - 1) - 1)) + DimensionSq / 2;
			int yTo = getCoord((yi.get(i - 1) - 1)) + DimensionSq / 2;
			g2.drawLine(xFr, yFr, xTo, yTo);
		}
		g2.setPaint(new Color(26,24,33));
		for(int i = 0; i < N; ++i) {
			for(int j = 0; j < M; ++j) {
				boolean exist = false;
				for(int k = 0; k < working.bufLeng; ++k) {
					if(yi.get(k) - 1 == i && xi.get(k) - 1 == j) {
						exist = true;
					}
				}
				if(exist) {
					g2.fillRect(getCoord(j), getCoord(i), DimensionSq, DimensionSq);
				}
			}
		}
		g2.setPaint(new Color(252,238,12));
		for(int i = 0; i < N; ++i) {
			String[] perCol = perRow[i].split(" ");
			for(int j = 0; j < M; ++j) {
				boolean exist = false;
				for(int k = 0; k < working.bufLeng; ++k) {
					if(yi.get(k) - 1 == i && xi.get(k) - 1 == j) {
						exist = true;
					}
				}
				if(exist) {
					//g2.drawRect(j * 40, i * 40, 30, 30);
					this.drawCenteredString(g, perCol[j], new Rectangle(getCoord(j) , getCoord(i) , DimensionSq, DimensionSq), gui.customFont.deriveFont(25f));
				}
			}
		}
		g2.setStroke(new BasicStroke(2));
		g2.setColor(new Color(0,184,255));
		for(int i = 0; i < N; ++i) {
			//String[] perCol = perRow[i].split(" ");
			for(int j = 0; j < M; ++j) {
				boolean exist = false;
				for(int k = 0; k < working.bufLeng; ++k) {
					if(yi.get(k) - 1 == i && xi.get(k) - 1 == j) {
						exist = true;
					}
				}
				if(exist) {
					g2.drawRect(getCoord(j), getCoord(i), DimensionSq, DimensionSq);
					//this.drawCenteredString(g, perCol[j], new Rectangle(j * 40 , i * 40 , 30, 30), gui.customFont.deriveFont(25f));
				}
			}
		}
	}
}

class SolvedDialog extends gui {
	
	JDialog dialSolved;
	
	SolvedDialog(){
		
		GridBagLayout layout = new GridBagLayout();  
		GridBagConstraints gbc = new GridBagConstraints();  
		
		dialSolved = new JDialog(gui.frame, "SOLVED", true);
		dialSolved.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialSolved.getContentPane().setBackground(new Color(26,24,33));
		dialSolved.setLayout(layout);
		
		List<Integer> xiNow = new ArrayList<Integer>();
		List<Integer> yiNow = new ArrayList<Integer>();
		
		for(int i = 0; i < working.B; ++i) {
			int curRow = working.answer.get(i) / working.M;
			int curCol = working.answer.get(i) % working.M;
			xiNow.add(curCol + 1);
			yiNow.add(curRow + 1);
		}
		
		
		JPanel newPanel = new JPanel();
		newPanel.add(new DisplayGraphics(working.N, working.M, xiNow, yiNow));
		newPanel.setBackground(new Color(26,24,33));
		//newPanel.setSize(DisplayGraphics.getCoord(6) + DisplayGraphics.DimensionSq - 20, DisplayGraphics.getCoord(6) + DisplayGraphics.DimensionSq);
		
		JLabel label1 = new JLabel();
		label1.setBackground(Color.magenta);
		label1.setOpaque(false);
		label1.setPreferredSize(new Dimension(340, 80));
		label1.setLayout(null);
		label1.setBorder(BorderFactory.createLineBorder(new Color(0,184,255), 5));
		//label1.setBounds(100, 100, 100, 100);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;  
	    gbc.gridx = 1;  
	    gbc.gridy = 0;  
	    gbc.insets = new Insets(10, 5, 5, 10);
	    
	    JLabel rewTitle = new JLabel("MAX REWARD");
	    rewTitle.setBackground(Color.gray);
	    rewTitle.setFont(customFont.deriveFont(14f));
	    rewTitle.setBackground(new Color(0,184,255));
	    rewTitle.setOpaque(true);
	    rewTitle.setBounds(0, 0, 340, 30);
	    rewTitle.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
	    
	    String maxRewardYey = "NO SOLUTION EXIST";
	    
	    if(working.maxReward != Integer.MIN_VALUE) {
	    	maxRewardYey = Integer.toString(working.maxReward);
	    }
	    
	    JLabel rewLabel = new JLabel(maxRewardYey, SwingConstants.CENTER);
	    rewLabel.setBackground(Color.green);
	    rewLabel.setFont(customFont.deriveFont(20f));
	    rewLabel.setOpaque(false);
	    rewLabel.setBounds(5, 30, 330, 45);
	    rewLabel.setForeground(new Color(252,238,12));
	    
	    label1.add(rewLabel);
	    label1.add(rewTitle);
		
		dialSolved.add(label1, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;  
	    gbc.gridx = 0;  
	    gbc.gridy = 0;
	    gbc.gridheight = 6;  
	    gbc.insets = new Insets(10, 10, 10, 5);
		
		dialSolved.add(newPanel, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;  
	    gbc.gridx = 1;  
	    gbc.gridy = 1;
	    gbc.gridheight = 1;  
	    gbc.insets = new Insets(5, 5, 5, 10);
	    
	    JLabel label2 = new JLabel();
	    label2.setBackground(Color.blue);
	    label2.setOpaque(false);
	    label2.setPreferredSize(new Dimension(340, 135));
	    label2.setBorder(BorderFactory.createLineBorder(new Color(0,184,255), 5));
	    label2.setLayout(null);
	    
	    JLabel bufTitle = new JLabel("BUFFER");
	    bufTitle.setBackground(Color.gray);
	    bufTitle.setFont(customFont.deriveFont(14f));
	    bufTitle.setBackground(new Color(0,184,255));
	    bufTitle.setOpaque(true);
	    bufTitle.setBounds(0, 0, 335, 30);
	    bufTitle.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
	    
	    JLabel bufIns = new JLabel();
		bufIns.setBounds(10, 40, 320, 80);
		bufIns.setBackground(Color.cyan);
		bufIns.setOpaque(false);
		bufIns.setLayout(new GridLayout(2, 8));
		
		BufferedImage img = null;
		
		try {
		
		img = ImageIO.read(new File("assets" + fileSep + "buffersq.png"));
		}catch(Exception e) {}
		
		for(int i = 0; i < 16; ++i) {
			String tokenNow = "";
			if(i >= working.bufLeng) {
				
			}else {
				int curRow = working.answer.get(i) / working.M;
				int curCol = working.answer.get(i) % working.M;
				tokenNow = working.matrix.get(curRow).get(curCol);
			}
			Image dimg = img.getScaledInstance(320 / 8, 40, Image.SCALE_SMOOTH);
			JLabel curSq = new JLabel(tokenNow);
			curSq.setHorizontalTextPosition(JLabel.CENTER);
			curSq.setFont(customFont.deriveFont(12f));
			curSq.setForeground(new Color(252,238,12));
			ImageIcon imgi = new ImageIcon(dimg);
			curSq.setIcon(imgi);
			bufIns.add(curSq);
			//bufIns.add(curSq);
			if(i < working.B) {
				curSq.setVisible(true);
			}else {
				curSq.setVisible(false);
			}
		}
		
	    
		label2.add(bufIns);
	    label2.add(bufTitle);
	    
	    dialSolved.add(label2, gbc);
	    
	    gbc.fill = GridBagConstraints.HORIZONTAL;  
	    gbc.gridx = 1;  
	    gbc.gridy = 2;
	    gbc.gridheight = 1;  
	    gbc.insets = new Insets(5, 5, 5, 10);
	    
	    JLabel label3 = new JLabel();
	    label3.setBackground(Color.gray);
	    label3.setOpaque(false);
	    label3.setPreferredSize(new Dimension(340, 135));
	    label3.setBorder(BorderFactory.createLineBorder(new Color(0,184,255), 5));
	    label3.setLayout(null);
	    
	    JLabel cellTitle = new JLabel("CELL");
	    cellTitle.setBackground(Color.gray);
	    cellTitle.setFont(customFont.deriveFont(14f));
	    cellTitle.setBackground(new Color(0,184,255));
	    cellTitle.setOpaque(true);
	    cellTitle.setBounds(0, 0, 335, 30);
	    cellTitle.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
	    
	    label3.add(cellTitle);
	    
	    JLabel cellIns = new JLabel();
	    cellIns.setBounds(10, 40, 320, 80);
	    cellIns.setBackground(Color.cyan);
	    cellIns.setOpaque(false);
	    cellIns.setLayout(new GridLayout(2, 8));
	    
	    for(int i = 0; i < 16; ++i) {
	    	String tokenNow = "";
	    	if(i < working.bufLeng) {
	    		int curRow = working.answer.get(i) / working.M;
				int curCol = working.answer.get(i) % working.M;
				tokenNow += "(";
				tokenNow += Integer.toString(curCol + 1);
				tokenNow += ",";
				tokenNow += Integer.toString(curRow + 1);
				tokenNow += ")";
	    	}
			Image dimg = img.getScaledInstance(320 / 8, 40, Image.SCALE_SMOOTH);
			JLabel curSq = new JLabel(tokenNow);
			curSq.setHorizontalTextPosition(JLabel.CENTER);
			curSq.setFont(customFont.deriveFont(12f));
			curSq.setForeground(new Color(252,238,12));
			ImageIcon imgi = new ImageIcon(dimg);
			curSq.setIcon(imgi);
			cellIns.add(curSq);
			//bufIns.add(curSq);
			if(i < working.B) {
				curSq.setVisible(true);
			}else {
				curSq.setVisible(false);
			}
		}
		
	    label3.add(cellIns);
	    dialSolved.add(label3, gbc);
	    
	    gbc.fill = GridBagConstraints.HORIZONTAL;  
	    gbc.gridx = 1;  
	    gbc.gridy = 3;
	    gbc.gridheight = 1;  
	    gbc.insets = new Insets(5, 5, 5, 10);
	    
	    JLabel label4 = new JLabel();
	    label4.setBackground(Color.gray);
	    label4.setOpaque(false);
	    label4.setPreferredSize(new Dimension(340, 80));
	    label4.setBorder(BorderFactory.createLineBorder(new Color(0,184,255), 5));
	    label4.setLayout(null);
	    
	    JLabel timeTitle = new JLabel("TIME IN MS");
	    timeTitle.setFont(customFont.deriveFont(14f));
	    timeTitle.setBackground(new Color(0,184,255));
	    timeTitle.setOpaque(true);
	    timeTitle.setBounds(0, 0, 340, 30);
	    timeTitle.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
	    
	    JLabel timeLabelIns = new JLabel(Long.toString(working.timeinMs), SwingConstants.CENTER);
	    timeLabelIns.setBackground(Color.green);
	    timeLabelIns.setFont(customFont.deriveFont(20f));
	    timeLabelIns.setOpaque(false);
	    timeLabelIns.setBounds(5, 30, 330, 45);
	    timeLabelIns.setForeground(new Color(252,238,12));
	    
	    label4.add(timeLabelIns);
	    label4.add(timeTitle);
		
	    dialSolved.add(label4, gbc);
	    
	    gbc.fill = GridBagConstraints.HORIZONTAL;  
	    gbc.gridx = 1;  
	    gbc.gridy = 4;  
	    gbc.insets = new Insets(5, 5, 5, 10);
	    
	    JButton butGo = new JButton("SAVE TO A FILE");
	    butGo.setBackground(new Color(0,184,255));
	    butGo.setPreferredSize(new Dimension(100, 40));
	    butGo.setFocusPainted(false);
	    butGo.setFont(customFont.deriveFont(18f));
	    
	    butGo.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		//System.out.println(System.getProperty("user.dir"));
	    		String val = JOptionPane.showInputDialog("Enter file name (file will be saved to results folder)", "result");
	    		if(val != null) {
	    			try {
	    			    BufferedWriter writer = new BufferedWriter(new FileWriter("results/" + val + ".txt"));
	    			    String maxRewStr = "NO SOLUTION EXIST";
	    			    if(working.maxReward != Integer.MIN_VALUE) {
	    			    	maxRewStr = Integer.toString(working.maxReward);
	    			    }
	    			    writer.write(maxRewStr);
	    			    writer.newLine();
	    			    for(int i = 0; i < working.bufLeng; ++i) {
	    			    	int curRow1 = working.answer.get(i) / working.M;
	    					int curCol1 = working.answer.get(i) % working.M;
	    			    	writer.write(working.matrix.get(curRow1).get(curCol1));
	    			    	if(i != working.B - 1) {
	    			    		writer.write(" ");
	    			    	}
	    			    }
	    			    writer.newLine();
	    			    for(int i = 0; i < working.bufLeng; ++i) {
	    			    	int curRow1 = working.answer.get(i) / working.M;
	    					int curCol1 = working.answer.get(i) % working.M;
	    			    	writer.write(Integer.toString(curCol1 + 1));
	    			    	writer.write(", ");
	    			    	writer.write(Integer.toString(curRow1 + 1));
	    			    	writer.newLine();
	    			    }
	    			    writer.newLine();
	    			    writer.write(Integer.toString((int)working.timeinMs));
	    			    writer.write(" ms");
	    			    writer.newLine();
	    			    writer.close();
	    			    JOptionPane.showMessageDialog(dialSolved, "SUCCESFULLY SAVED FILE");
	    			}catch(Exception e1) {
	    				e1.printStackTrace();
	    			}
	    		}
	    	}
	    });
	    
	    dialSolved.add(butGo, gbc);
	    
	    gbc.fill = GridBagConstraints.HORIZONTAL;  
	    gbc.gridx = 1;  
	    gbc.gridy = 5;  
	    gbc.weighty= 1.0f;
	    gbc.insets = new Insets(5, 5, 10, 10);
	    
	    JLabel label5 = new JLabel();
	    label5.setBackground(Color.gray);
	    label5.setOpaque(true);
	    //label5.setPreferredSize(new Dimension(340, 40));
	    
	    
	    
	    dialSolved.add(label5, gbc);

		
		dialSolved.pack();
		dialSolved.setResizable(false);
		dialSolved.setVisible(true);
	}
}

class validateInput{
	
	public static List<List<String>> matrix = new ArrayList<List<String>>();
	public static List<Integer> rewards = new ArrayList<Integer>();
	public static List<String[]> allline = new ArrayList<String[]>();
	public static int N, M, B, Q;
	
	public static boolean validate() {
		
		matrix.clear();
		rewards.clear();
		allline.clear();
		
		try {
		
		String matrixStr = gui.jta.getText();
		String seqStr = gui.sjta.getText();
		if(matrixStr.equals("")) {
			return false;
		}
		matrixStr = tokenValidator.rightTrim(matrixStr);
		seqStr = tokenValidator.rightTrim(seqStr);
		String perRow[] = matrixStr.split("\n");
		N = perRow.length;
		M = -1;
		if(N == 0) {
			return false;
		}
		for(int i = 0; i < N; ++i) {
			String perCol[] = tokenValidator.rightTrim(perRow[i]).split(" ");
			if(M == -1) {
				M = perCol.length;
			}else if(perCol.length != M) {
				return false;
			}
			List<String> temp = new ArrayList<String>();
			for(int j = 0; j < perCol.length; ++j) {
				if(!tokenValidator.validator(perCol[j])) {
					return false;
				}
				temp.add(perCol[j]);
			}
			matrix.add(temp);
		}
		String[] perSeq = seqStr.split("\n");
		if(perSeq.length % 2 == 1) {
			return false;
		}
		Q = perSeq.length / 2;
		//System.out.println(Q);
		B = Integer.parseInt(gui.jte.getText());
		for(int i = 0; i < 2 * Q; i += 2) {
			String[] seqNow = tokenValidator.rightTrim(perSeq[i]).split(" ");
			for(int j = 0; j < seqNow.length; ++j) {
				if(!tokenValidator.validator(seqNow[j])) {
					return false;
				}
			}
			allline.add(seqNow);
			String oneNum = tokenValidator.rightTrim(perSeq[i + 1]);
			int rewNow = Integer.parseInt(oneNum);
			//System.out.println(rewNow);
			rewards.add(rewNow);
		}
		return true;
		
		}catch(Exception e) {
			return false;
		}
	}
}


public class gui {
	
	public static JFrame frame;
	public static JTextField jte;
	public static JScrollPane jsp;
	public static JScrollPane sjsp;
	public static Font customFont;
	public static JTextArea jta;
	public static JTextArea sjta;
	public static String fileSep = System.getProperty("file.separator");
	
	public static String matrixDefault = 
			"7A 55 E9 E9 1C 55\n"
			+ "55 7A 1C 7A E9 55\n"
			+ "55 1C 1C 55 E9 BD\n"
			+ "BD 1C 7A 1C 55 BD\n"
			+ "BD 55 BD 7A 1C 1C\n"
			+ "1C 55 55 7A 55 7A";
	
	public static String seqDefault = 
			"BD E9 1C\n"
			+ "15\n"
			+ "BD 7A BD\n"
			+ "20\n"
			+ "BD 1C BD 55\n"
			+ "30";
	
	public static void main(String[] args) {
		
		try {
			
		frame = new JFrame();
		ImageIcon bplogo = new ImageIcon("assets" + fileSep + "bpprotocol.jpg");
		frame.setIconImage(bplogo.getImage());
		
		JPanel buttonP = new JPanel();
		buttonP.setBackground(Color.red);
		buttonP.setBounds(10, 585, 965, 70);
		buttonP.setLayout(new GridBagLayout());
		buttonP.setOpaque(false);
		
		JPanel butRandP = new JPanel();
		butRandP.setBackground(Color.GREEN);
		butRandP.setBounds(10, 585, 375, 70);
		butRandP.setLayout(new GridBagLayout());
		butRandP.setOpaque(false);
		
		BufferedImage img = null;
		
		img = ImageIO.read(new File("assets" + fileSep + "buffersq.png"));
		
	
		
		JLabel imgP = new JLabel();
		imgP.setBounds(640,10, 335,165);
		imgP.setBorder(BorderFactory.createLineBorder(new Color(208,237,87), 5));
		
		
		JPanel matrixP = new JPanel();
		matrixP.setBackground(Color.blue);
		matrixP.setBounds(10, 195, 610, 380);
		matrixP.setLayout(null);
		matrixP.setOpaque(false);

		matrixP.setBorder(BorderFactory.createLineBorder(new Color(208,237,87), 5));

		
		JPanel bufferP = new JPanel();
		bufferP.setBackground(Color.cyan);
		bufferP.setBounds(10, 10, 610, 165);
		bufferP.setLayout(null);
		bufferP.setBorder(BorderFactory.createLineBorder(new Color(208,237,87), 5));
		bufferP.setOpaque(false);
		
		JPanel seqP = new JPanel();
		seqP.setBackground(Color.GREEN);
		seqP.setBounds(640, 195, 335, 380);
		seqP.setLayout(null);
		seqP.setBorder(BorderFactory.createLineBorder(new Color(208,237,87), 5));
		seqP.setOpaque(false);
		
		JPanel insMat = new JPanel();
		insMat.setBackground(Color.magenta);
		insMat.setBounds(50, 60, 540, 270);
		insMat.setLayout(new BorderLayout());
		insMat.setOpaque(false);
		
	
		
		String path = "BoxedSemibold.ttf";
		String path1 = "assets" + fileSep;
		InputStream myStream = new BufferedInputStream(new FileInputStream(path1 + path));
		customFont = Font.createFont(Font.TRUETYPE_FONT, myStream).deriveFont(16f);
		JLabel labMat = new JLabel("ENTER CODE MATRIX");
		labMat.setFont(customFont);
		labMat.setBackground(new Color(208,237,87));
		labMat.setOpaque(true);
		labMat.setBounds(0, 0, 620, 40);
		labMat.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
		//labMat.setIcon(new ImageIcon(path1 + path));
		matrixP.add(labMat);
		matrixP.add(insMat);
		jta = new JTextArea(10, 40);
		jta.setOpaque(false);
		jta.setText(matrixDefault);
		jta.setForeground(new Color(208,237,87));
		jta.setCaretColor(new Color(208,237,87));
		jta.setFont(customFont.deriveFont(35f));
		jsp = new JScrollPane(jta);
		jsp.setOpaque(false);
		jsp.getViewport().setOpaque(false);
		jsp.setBorder(null);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		insMat.add(jsp);
		
		JButton button = new JButton("SOLVE");
		button.setPreferredSize(new Dimension(200, 50));
		button.setOpaque(true);
		button.setContentAreaFilled(true);
		button.setBackground(new Color(26,24,33));
		button.setBorder(BorderFactory.createLineBorder(new Color(208,237,87), 2 ));
		button.setFont(customFont.deriveFont(20f));
		button.setForeground(new Color(208,237,87));
		button.setFocusPainted(false);
		button.getModel().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				ButtonModel model = (ButtonModel) e.getSource();
				if(model.isRollover()) {
					button.setBackground(new Color(62,82,35));
				}else {
					button.setBackground(new Color(26,24,33));
				}
			}
		});
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				String ta = jta.getText();
				int cnt = 0;
				for(int i = 0; i < ta.length(); ++i) {
					System.out.print((int)(ta.charAt(i)));
					if((int)ta.charAt(i) == 10) {
						cnt++;
					}
					System.out.print(" ");
				}
				System.out.println();
				System.out.print(cnt);
				//new DialogRand();*/
				if(!validateInput.validate()) {
					JOptionPane.showMessageDialog(frame,
						    "MATRIX/SEQUENCE PARSING FAILED",
						    "ERROR",
						    JOptionPane.ERROR_MESSAGE);
				}else {
					new working(validateInput.allline, validateInput.matrix, validateInput.rewards, validateInput.N, validateInput.M, validateInput.B, validateInput.Q);
					working.solvemain();
					new SolvedDialog();
				}
			}
		});
		buttonP.add(button);
		
		JLabel seqMat = new JLabel("ENTER SEQUENCES");
		seqMat.setFont(customFont);
		seqMat.setBackground(new Color(208,237,87));
		seqMat.setOpaque(true);
		seqMat.setBounds(0, 0, 335, 40);
		seqMat.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
		
		seqP.add(seqMat);
		
		JPanel insSeq = new JPanel();
		insSeq.setBackground(Color.magenta);
		insSeq.setBounds(30, 60, 290, 300);
		insSeq.setLayout(new BorderLayout());
		insSeq.setOpaque(false);
		
		seqP.add(insSeq);
		
		sjta = new JTextArea(10, 40);
		sjta.setText(seqDefault);
		sjta.setOpaque(false);
		sjta.setForeground(new Color(208,237,87));
		sjta.setCaretColor(new Color(208,237,87));
		
		sjta.setFont(customFont.deriveFont(25f));
		sjsp = new JScrollPane(sjta);
		sjsp.setOpaque(false);
		sjsp.getViewport().setOpaque(false);
		sjsp.setBorder(null);
		sjsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sjsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		insSeq.add(sjsp);
		
		JLabel comboLabel = new JLabel("SPECIFY BUFFER LENGTH (1-16)");
		comboLabel.setFont(customFont);
		comboLabel.setBackground(new Color(208,237,87));
		comboLabel.setOpaque(true);
		comboLabel.setBounds(0, 0, 610, 40);
		comboLabel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
		
		bufferP.add(comboLabel);
		
		List<JLabel> listLabels = new ArrayList<JLabel>();
		
		NumberFormat format = NumberFormat.getInstance();
	    
	    MyFormatter myf = new MyFormatter();
	    myf.setFormat(format);
	    myf.setValueClass(Integer.class);
	    myf.setMinimum(1);
	    myf.setMaximum(16);
	    myf.setAllowsInvalid(false);
	    myf.setCommitsOnValidEdit(true);
		
	    jte = new JFormattedTextField(myf);
		jte.setBounds(305, 60, 70, 70);
		jte.setBackground(new Color(208,237,87));
		jte.setFont(customFont.deriveFont(40f));
		jte.setForeground(new Color(208,237,87));
		jte.setBorder(BorderFactory.createEmptyBorder());
		jte.setOpaque(false); 
		jte.setText("7");
		jte.setCaretColor(new Color(208,237,87));
		
		jte.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
			    //warn();
			  }
			  public void removeUpdate(DocumentEvent e) {
			    warn();
			  }
			  public void insertUpdate(DocumentEvent e) {
			    warn();
			  }

			  public void warn() {
				 String cur = jte.getText();
				 int u = 0;
				 if(!cur.equals("")) {
					 u = Integer.parseInt(cur);
				 }
				 for(int i = 0; i < 16; ++i) {
					 if(i < u) {
						 listLabels.get(i).setVisible(true);
					 }else {
						 listLabels.get(i).setVisible(false);
					 }
				 }
			  }
		});
		
		bufferP.add(jte);
		
		JButton butRand = new JButton("RANDOMIZE INPUT");
		butRand.setPreferredSize(new Dimension(250, 25));
		butRand.setOpaque(true);
		butRand.setContentAreaFilled(true);
		butRand.setBackground(new Color(26,24,33));
		butRand.setBorder(BorderFactory.createLineBorder(new Color(252,238,12), 1 ));
		butRand.setFont(customFont.deriveFont(14f));
		butRand.setForeground(new Color(252,238,12));
		butRand.setFocusPainted(false);
		butRand.getModel().addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				ButtonModel model = (ButtonModel) e.getSource();
				if(model.isRollover()) {
					butRand.setBackground(new Color(62,82,35));
				}else {
					butRand.setBackground(new Color(26,24,33));
				}
			}
		});
		
		butRand.getModel().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DialogRand();
			}
		});
		
		butRandP.add(butRand);
		
		JLabel texL = new JLabel();
		texL.setBounds(600, 585, 375, 70);
		texL.setBackground(Color.green);
		texL.setOpaque(false);
		texL.setLayout(new GridBagLayout());
		
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Choose a .txt file");
		FileFilter filter = new FileNameExtensionFilter("TXT file", new String[] {"txt"});
		chooser.setFileFilter(filter);
		chooser.addChoosableFileFilter(filter);
		chooser.setAcceptAllFileFilterUsed(false);
		UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		
		JButton butInpTex = new JButton("INPUT WITH FILE");
		butInpTex.setFont(customFont.deriveFont(14f));
		butInpTex.setForeground(new Color(252,238,12));
		butInpTex.setBackground(new Color(26,24,33));
		butInpTex.setBorder(BorderFactory.createLineBorder(new Color(252,238,12), 1 ));
		butInpTex.setPreferredSize(new Dimension(250, 25));
		butInpTex.setOpaque(true);
		butInpTex.setContentAreaFilled(true);
		butInpTex.setFocusPainted(false);
		butInpTex.getModel().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				ButtonModel model = (ButtonModel) e.getSource();
				if(model.isRollover()) {
					butInpTex.setBackground(new Color(62,82,35));
				}else {
					butInpTex.setBackground(new Color(26,24,33));
				}
			}
		});
		
		butInpTex.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = chooser.showOpenDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					if(tokenValidator.readFile(chooser.getSelectedFile())) {
						//System.out.println("ok1");
					}else {
						JOptionPane.showMessageDialog(frame,
							    "FILE PARSING FAILED",
							    "ERROR",
							    JOptionPane.ERROR_MESSAGE);
						butInpTex.doClick();
						//System.out.println("oke2");
					}
				}else {
					//System.out.println("Not approved");
				}
			}
		});
		
		texL.add(butInpTex);
		
		JLabel bufTitle = new JLabel("BUFFER");
		bufTitle.setFont(customFont.deriveFont(20f));
		bufTitle.setBackground(new Color(208,237,87));
		bufTitle.setOpaque(true);
		bufTitle.setBounds(0, 0, 335, 60);
		bufTitle.setBorder(BorderFactory.createEmptyBorder(0, 130, 0, 0));
		
		imgP.add(bufTitle);
		JLabel bufIns = new JLabel();
		bufIns.setBounds(10, 70, 310, 80);
		bufIns.setBackground(Color.cyan);
		bufIns.setOpaque(false);
		bufIns.setLayout(new GridLayout(2, 8));
		
		
		for(int i = 0; i < 16; ++i) {
			Image dimg = img.getScaledInstance(320 / 8, 40, Image.SCALE_SMOOTH);
			JLabel curSq = new JLabel();
			//curSq.setHorizontalTextPosition(JLabel.CENTER);
			curSq.setFont(customFont.deriveFont(12f));
			curSq.setForeground(new Color(208,237,87));
			ImageIcon imgi = new ImageIcon(dimg);
			curSq.setIcon(imgi);
			listLabels.add(curSq);
			bufIns.add(curSq);
			curSq.setVisible(false);
		}
		
		imgP.add(bufIns);
		
		JLabel info = new JLabel("Inspired by cyberpunk-hacker.com");
		//info.setFont(info.getFont().deriveFont(10f));
		info.setBounds(785, 640, 200, 50);
		
		JLabel info2 = new JLabel("Made by kristo");
		//info2.setFont(info2.getFont().deriveFont(10f));
		info2.setBounds(895, 626, 200, 50);
		
		
		
		
		/*
		//List <JTextField> arr = new ArrayList<JTextField>();
		for(int i = 0; i < 36; ++i) {
			JTextField temp = new JTextField();
			temp.setFont(new Font("Consolas", Font.PLAIN, 10));
			temp.setText("1");
			insMat.add(temp);
		}*/
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setLocation(0, 0);
		frame.setSize(1000, 720);
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(26,24,33));
		frame.setTitle("BREACH PROTOCOL");
		frame.add(buttonP);
		frame.add(matrixP);
		frame.add(bufferP);
		frame.add(seqP);
		frame.add(butRandP);
		frame.add(imgP);
		frame.add(info);
		frame.add(info2);
		frame.add(texL);
		frame.setVisible(true);
		
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
