import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import java.util.ArrayList;

public class Application {

	private JFrame frmKarnaughMapResolver;
	
	JLabel lblFormulaStart; // formula di partenza
	String f="f(A,B,C,D)=\u03A3()"; // stringa per la formula
	JLabel lblABCD; // ABCD in binario
	JLabel lblIndex; // # dell'uscita
	JLabel[] lblY = new JLabel[16]; // uscite Y da 0 a 15
	JLabel[] lblYKmap = new JLabel[16]; // label per la mappa di Karnaugh
	JLabel lblFAnd;
	JLabel lblFOr;
	JTabbedPane tabbedPane;
	JPanel panelGrOne;
	JPanel panelGrZero;
	ArrayList<ArrayList<JLabel>> lblGrpOne = new ArrayList<ArrayList<JLabel>>();
	JLabel[] lbOne = new JLabel[16*9];
	JLabel[] lbZero = new JLabel[16*9];
	PanelCircuitAnd circuitAnd;
	PanelCircuitOr circuitOr;
	int test = 10;
	
	Karnaugh kmap = new Karnaugh();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
            // Set cross-platform Java L&F (also called "Metal")
        UIManager.setLookAndFeel(
            UIManager.getCrossPlatformLookAndFeelClassName());
	    } 
	    catch (UnsupportedLookAndFeelException e) {
	       // handle exception
	    }
	    catch (ClassNotFoundException e) {
	       // handle exception
	    }
	    catch (InstantiationException e) {
	       // handle exception
	    }
	    catch (IllegalAccessException e) {
	       // handle exception
	    }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
					window.frmKarnaughMapResolver.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Application() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		String info = "<html><p align=\"center\"><b>Karnaugh map resolver by Marco Marconi</b></p>"
				+ "<br>"
				+ "<p align=\\\"center\\\">The software is based on the work done by Ali Hamdi Ali Fadel for grouping the ones, https://github.com/AliOsm</p>"
				+ "<br>"
				+ "Instructions:"
				+ "<br>"
				+ "change the value of the outputs by clicking directly on the values highlighted in blue. The values will cycle between 0, 1 and X, where X stands for indeterminate or don't."
				+ "<br>"
				+ "Press \"Resolve\" and analyze the results using the tabs to check groups with ones, groups with zeros and circuits created using both minterms and maxterms"
				+ "<br><br><p align =\"center\"><b>The project is available as open source under the terms of the MIT License.</b></p>";
				
		frmKarnaughMapResolver = new JFrame();
		frmKarnaughMapResolver.setTitle("Karnaugh Map Resolver");
		frmKarnaughMapResolver.setBounds(100, 100, 747, 409);
		frmKarnaughMapResolver.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmKarnaughMapResolver.getContentPane().setLayout(null);
		
		JSeparator s_1 = new JSeparator();
		s_1.setBounds(8, 30, 174, 2);
		frmKarnaughMapResolver.getContentPane().add(s_1);
		
		JSeparator s_2 = new JSeparator();
		s_2.setOrientation(SwingConstants.VERTICAL);
		s_2.setBounds(32, 12, 2, 265);
		frmKarnaughMapResolver.getContentPane().add(s_2);

		JSeparator s_3 = new JSeparator();
		s_3.setOrientation(SwingConstants.VERTICAL);
		s_3.setBounds(103, 12, 2, 265);
		frmKarnaughMapResolver.getContentPane().add(s_3);

		JSeparator s_4 = new JSeparator();
		s_4.setBounds(8, 276, 174, 2);
		frmKarnaughMapResolver.getContentPane().add(s_4);
		
		JSeparator s_5 = new JSeparator();
		s_5.setOrientation(SwingConstants.VERTICAL);
		s_5.setBounds(8, 12, 2, 265);
		frmKarnaughMapResolver.getContentPane().add(s_5);
		
		JSeparator s_6 = new JSeparator();
		s_6.setOrientation(SwingConstants.VERTICAL);
		s_6.setBounds(181, 12, 2, 265);
		frmKarnaughMapResolver.getContentPane().add(s_6);
		
		JSeparator s_7 = new JSeparator();
		s_7.setBounds(8, 12, 174, 2);
		frmKarnaughMapResolver.getContentPane().add(s_7);
		
		JLabel lblHeader = new JLabel("#   A  B  C  D   f(A,B,C,D)");
		lblHeader.setBounds(16, 14, 174, 15);
		frmKarnaughMapResolver.getContentPane().add(lblHeader);
		
		JButton btnAll0 = new JButton("all \"0\"");
		btnAll0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				kmap.setAll('0');
				refreshOutput();
			}
		});
		btnAll0.setBounds(240, 12, 116, 25);
		frmKarnaughMapResolver.getContentPane().add(btnAll0);
		
		JButton btnAll1 = new JButton("all \"1\"");
		btnAll1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				kmap.setAll('1');
				refreshOutput();
			}
		});
		btnAll1.setBounds(240, 42, 116, 25);
		frmKarnaughMapResolver.getContentPane().add(btnAll1);
		
		JButton btnAllX = new JButton("all \"X\"");
		btnAllX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				kmap.setAll('X');
				refreshOutput();
			}
		});
		btnAllX.setBounds(240, 72, 116, 25);
		frmKarnaughMapResolver.getContentPane().add(btnAllX);
		
		JLabel lblAb = new JLabel("CD");
		lblAb.setBounds(290, 116, 28, 15);
		frmKarnaughMapResolver.getContentPane().add(lblAb);
		
		JLabel lblCd = new JLabel("AB");
		lblCd.setBounds(195, 204, 28, 15);
		frmKarnaughMapResolver.getContentPane().add(lblCd);
		
		JLabel lblAB00 = new JLabel("00");
		lblAB00.setFont(new Font("Courier", Font.BOLD, 14));
		lblAB00.setBounds(243, 134, 16, 15);
		frmKarnaughMapResolver.getContentPane().add(lblAB00);
		
		JLabel lblAB01 = new JLabel("01");
		lblAB01.setFont(new Font("Courier", Font.BOLD, 14));
		lblAB01.setBounds(275, 134, 16, 15);
		frmKarnaughMapResolver.getContentPane().add(lblAB01);
		
		JLabel lblAB11 = new JLabel("11");
		lblAB11.setFont(new Font("Courier", Font.BOLD, 14));
		lblAB11.setBounds(307, 134, 16, 15);
		frmKarnaughMapResolver.getContentPane().add(lblAB11);
		
		JLabel lblAB10 = new JLabel("10");
		lblAB10.setFont(new Font("Courier", Font.BOLD, 14));
		lblAB10.setBounds(339, 134, 16, 15);
		frmKarnaughMapResolver.getContentPane().add(lblAB10);
		
		JLabel lblCD00 = new JLabel("00");
		lblCD00.setFont(new Font("Courier", Font.BOLD, 14));
		lblCD00.setBounds(216, 161, 16, 15);
		frmKarnaughMapResolver.getContentPane().add(lblCD00);
		
		JLabel lblCD01 = new JLabel("01");
		lblCD01.setFont(new Font("Courier", Font.BOLD, 14));
		lblCD01.setBounds(216, 191, 16, 15);
		frmKarnaughMapResolver.getContentPane().add(lblCD01);
		
		JLabel lblCD11 = new JLabel("11");
		lblCD11.setFont(new Font("Courier", Font.BOLD, 14));
		lblCD11.setBounds(216, 221, 16, 15);
		frmKarnaughMapResolver.getContentPane().add(lblCD11);
		
		JLabel lblCD10 = new JLabel("10");
		lblCD10.setFont(new Font("Courier", Font.BOLD, 14));
		lblCD10.setBounds(216, 251, 16, 15);
		frmKarnaughMapResolver.getContentPane().add(lblCD10);
		
		JSeparator s_8 = new JSeparator();
		s_8.setBounds(234, 148, 128, 2);
		frmKarnaughMapResolver.getContentPane().add(s_8);
		
		JSeparator s_9 = new JSeparator();
		s_9.setOrientation(SwingConstants.VERTICAL);
		s_9.setBounds(234, 148, 10, 128);
		frmKarnaughMapResolver.getContentPane().add(s_9);
		
		JSeparator s_10 = new JSeparator();
		s_10.setOrientation(SwingConstants.VERTICAL);
		s_10.setBounds(266, 148, 10, 128);
		frmKarnaughMapResolver.getContentPane().add(s_10);
		
		JSeparator s_11 = new JSeparator();
		s_11.setOrientation(SwingConstants.VERTICAL);
		s_11.setBounds(298, 148, 10, 128);
		frmKarnaughMapResolver.getContentPane().add(s_11);
		
		JSeparator s_12 = new JSeparator();
		s_12.setOrientation(SwingConstants.VERTICAL);
		s_12.setBounds(330, 148, 10, 128);
		frmKarnaughMapResolver.getContentPane().add(s_12);
		
		JSeparator s_13 = new JSeparator();
		s_13.setOrientation(SwingConstants.VERTICAL);
		s_13.setBounds(362, 148, 10, 128);
		frmKarnaughMapResolver.getContentPane().add(s_13);
		
		JSeparator s_14 = new JSeparator();
		s_14.setBounds(234, 180, 128, 2);
		frmKarnaughMapResolver.getContentPane().add(s_14);
		
		JSeparator s_15 = new JSeparator();
		s_15.setBounds(234, 212, 128, 2);
		frmKarnaughMapResolver.getContentPane().add(s_15);
		
		JSeparator s_16 = new JSeparator();
		s_16.setBounds(234, 244, 128, 2);
		frmKarnaughMapResolver.getContentPane().add(s_16);
		
		JSeparator s_17 = new JSeparator();
		s_17.setBounds(234, 276, 128, 2);
		frmKarnaughMapResolver.getContentPane().add(s_17);
		
		lblFormulaStart = new JLabel(f);
		lblFormulaStart.setBounds(8, 313, 533, 15);
		frmKarnaughMapResolver.getContentPane().add(lblFormulaStart);
		
		lblFAnd = new JLabel("");
		lblFAnd.setBounds(8, 333, 715, 15);
		frmKarnaughMapResolver.getContentPane().add(lblFAnd);
		
		lblFOr = new JLabel("");
		lblFOr.setBounds(8, 353, 715, 15);
		frmKarnaughMapResolver.getContentPane().add(lblFOr);
		
		JButton btnResolveKmap = new JButton("Resolve");
		btnResolveKmap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				kmap.resolve();
				refreshOutput();
				setGrp();
				createCircuit();
			}
		});
		btnResolveKmap.setBounds(8, 282, 174, 25);
		frmKarnaughMapResolver.getContentPane().add(btnResolveKmap);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(381, 12, 342, 295);
		frmKarnaughMapResolver.getContentPane().add(tabbedPane);
		
		JPanel panelInfo = new JPanel();
		tabbedPane.addTab("Info", null, panelInfo, null);
		panelInfo.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(info);
		lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		lblNewLabel.setBounds(12, 12, 313, 244);
		panelInfo.add(lblNewLabel);
		
		panelGrOne = new JPanel();
		tabbedPane.addTab("Ones", null, panelGrOne, null);
		panelGrOne.setLayout(null);
		
		panelGrZero = new JPanel();
		tabbedPane.addTab("Zeros", null, panelGrZero, null);
		panelGrZero.setLayout(null);
		
		circuitAnd  = new PanelCircuitAnd();
		circuitAnd.setBackground(Color.WHITE);
		tabbedPane.addTab("AND", null, circuitAnd, null);
		circuitAnd.setLayout(null);
		
		circuitOr  = new PanelCircuitOr();
		circuitOr.setBackground(Color.WHITE);
		tabbedPane.addTab("Or", null, circuitOr, null);
		circuitOr.setLayout(null);
		
		setTableTruth();
		initializeLabelGrp();
	}
	
	public void createCircuit() {
		circuitAnd.refresh(kmap.getFormulaAnd());
		circuitOr.refresh(kmap.getFormulaOr());
	}
	
	public void setGrp() {
		int i=0;
		int index;
		int k_indexes[] = {0, 1, 3, 2, 4, 5, 7, 6, 12, 13, 15, 14, 8, 9, 11, 10};
		ArrayList<ArrayList<Integer>> one = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> zero = new ArrayList<ArrayList<Integer>>();
		for (JLabel l: lbOne) {
			l.setVisible(false);
		}
		for (JLabel l: lbZero) {
			l.setVisible(false);
		}
		
		one = kmap.getGrpOne();
		zero = kmap.getGrpZero();
		
		for (ArrayList<Integer> a: one) {
			for (int t=0;t<16;t++) {
				index=t+i*16;
				lbOne[index].setVisible(true);
				lbOne[index].setText(Character.toString(kmap.getY(k_indexes[t])));
				lbOne[index].setForeground(Color.LIGHT_GRAY);
				for (int u: a) {
					if (u==k_indexes[t]) {
						lbOne[index].setForeground(Color.BLUE);
					}
				}
			}
			i++;
		}
		i=0;
		for (ArrayList<Integer> a: zero) {
			for (int t=0;t<16;t++) {
				index=t+i*16;
				lbZero[index].setVisible(true);
				lbZero[index].setText(Character.toString(kmap.getY(k_indexes[t])));
				lbZero[index].setForeground(Color.LIGHT_GRAY);
				for (int u: a) {
					if (u==k_indexes[t]) {
						lbZero[index].setForeground(Color.BLUE);
					}
				}
			}
			i++;
		}
	}
	
	public void initializeLabelGrp() {
		int x,y;
		int l;
		for (int i=0;i<9;i++) {
			for (int t=0;t<16;t++) {
				l=i*16+t;
				lbOne[l] = new JLabel("X");
				lbOne[l].setFont(new Font("Courier", Font.PLAIN, 16));
				lbOne[l].setHorizontalTextPosition(SwingConstants.CENTER);
				lbOne[l].setHorizontalAlignment(SwingConstants.CENTER);
				lbOne[l].setVisible(false);
				
				lbZero[l] = new JLabel("X");
				lbZero[l].setFont(new Font("Courier", Font.PLAIN, 16));
				lbZero[l].setHorizontalTextPosition(SwingConstants.CENTER);
				lbZero[l].setHorizontalAlignment(SwingConstants.CENTER);
				lbZero[l].setVisible(false);
				
				x = 18+((t%4)*16);
				y = 22+(14*((t/4)%16));
				x += i%3*120; // spazio x
				y += i/3*80;  // spazio y
				
				lbOne[l].setBounds(x, y, 16, 16);
				panelGrOne.add(lbOne[l]);
				
				lbZero[l].setBounds(x, y, 16, 16);
				panelGrZero.add(lbZero[l]);
			}
		}
	}

	public void setTableTruth () {
		for (int i=0;i<16;i++) {
			String bin = Integer.toBinaryString(i);
			bin = String.format("%4s", bin).replaceAll(" ", "0");
			bin = bin.substring(0,1) + " " + bin.substring(1, 2) + " " + bin.substring(2, 3) + " " + bin.substring(3, 4);
			
			lblIndex = new JLabel(Integer.toString(i));
			lblIndex.setFont(new Font("Courier", Font.PLAIN, 14));
			lblIndex.setHorizontalAlignment(SwingConstants.CENTER);
			lblIndex.setBounds(12, 35+i*15, 16, 15);
			frmKarnaughMapResolver.getContentPane().add(lblIndex);
			
			lblABCD = new JLabel(bin);
			lblABCD.setFont(new Font("Courier", Font.PLAIN, 14));
			lblABCD.setBounds(40, 35+i*15, 56, 15);
			frmKarnaughMapResolver.getContentPane().add(lblABCD);

			lblY[i] = new JLabel(Character.toString(kmap.getY(i)));
			lblY[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					for (int i=0;i<16;i++) {
						if (e.getSource() == lblY[i]) {
							switch(lblY[i].getText().charAt(0)) {
							case 'X':
								kmap.setY(i, '0');
								break;
							case '0':
								kmap.setY(i, '1');
								break;
							case '1':
								kmap.setY(i, 'X');
								break;	
							}
						}
					}
					refreshOutput();
				}
			});
			lblY[i].setFont(new Font("Courier", Font.PLAIN, 14));
			lblY[i].setForeground(Color.BLUE);
			lblY[i].setBounds(140, 35+i*15, 16, 15);
			frmKarnaughMapResolver.getContentPane().add(lblY[i]);
			
			lblYKmap[i] = new JLabel(Character.toString(kmap.getY(i)));
			lblYKmap[i].setFont(new Font("Courier", Font.BOLD, 24));
			lblYKmap[i].setBounds(244+(i%4)*32, 157+((i/4)%16)*32, 16, 18);
			frmKarnaughMapResolver.getContentPane().add(lblYKmap[i]);
		}		
	}
	
	public void refreshOutput() {
		for (int i=0; i<16; i++) {
			lblY[i].setText(Character.toString(kmap.getY(i)));
			lblYKmap[i].setText(Character.toString(kmap.getKMap(i)));
		}
		lblFormulaStart.setText(kmap.getFormula());
		lblFAnd.setText(kmap.getFormulaAnd());
		lblFOr.setText(kmap.getFormulaOr());
	}
}