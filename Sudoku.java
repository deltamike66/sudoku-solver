import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.border.BevelBorder;
import javax.swing.JPanel;

/**
 * @author marco marconi
 * https://www.marcomarconi.it
 */
public class Sudoku {

	private JFrame frmSudokuResolver;
	JLabel[][] sudoku = new JLabel[10][10];
	int[][] board = new int[10][10];
	JLabel lblTime;
	long startTime;
	long endTime;
	JPanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sudoku window = new Sudoku();
					window.frmSudokuResolver.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Sudoku() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSudokuResolver = new JFrame();
		frmSudokuResolver.setTitle("Sudoku resolver - by DeltaMike");
		frmSudokuResolver.setBounds(100, 100, 406, 252);
		frmSudokuResolver.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSudokuResolver.getContentPane().setLayout(null);
		
		JButton btnRisolviSudoku = new JButton("Risolvi / Solve");
		btnRisolviSudoku.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread() {
					public void run() {
						startTime = System.nanoTime();
						if(resolveWithBruteForce(0,0,board)) {
							lblTime.setText("Time:" + String.format("%.3f",(System.nanoTime() - startTime)/1000000000.0) + "s");
						} else {
							lblTime.setText("I Time:" + String.format("%.3f",(System.nanoTime() - startTime)/1000000000.0) + "s. Impossible to resolve");
						}
					}
				}.start();
			}
		});
		
		panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(12, 12, 181, 181);
		frmSudokuResolver.getContentPane().add(panel);
		panel.setLayout(null);
		
		btnRisolviSudoku.setBounds(202, 140, 180, 25);
		frmSudokuResolver.getContentPane().add(btnRisolviSudoku);
		
		JButton btnResetSudoku = new JButton("Reset");
		btnResetSudoku.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (int x=0; x<10; x++) {
					for (int y=0; y<10; y++) {
						board[x][y]=0;
					}
				}
				refreshLabel(board);
				lblTime.setText("");
			}
		});
		btnResetSudoku.setBounds(202, 168, 180, 25);
		frmSudokuResolver.getContentPane().add(btnResetSudoku);
		
		JLabel lblInfo = new JLabel("<html>Cliccare sulle caselle per ciclare tra i possibili valori da inserire e infine premere il bottone \"Risolvi\"<br><br>Click on the boxes to cycle through the possible values to be entered and finally press the \"Solve\" button</html>");
		lblInfo.setVerticalAlignment(SwingConstants.TOP);
		lblInfo.setFont(new Font("Dialog", Font.PLAIN, 11));
		lblInfo.setBounds(202, 12, 180, 126);
		frmSudokuResolver.getContentPane().add(lblInfo);
		
		lblTime = new JLabel("");
		lblTime.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblTime.setBounds(32, 199, 141, 15);
		frmSudokuResolver.getContentPane().add(lblTime);
		
		drawLines();
	}
	
	
	/**
	 * @param temp array int del sudoku per il refresh dei valori
	 */
	public void refreshLabel(int[][] temp) {
		for (int y=0; y<10; y++) {
			for (int x=0; x<10; x++) {
				sudoku[x][y].setText(temp[x][y]==0?" ":Integer.toString(temp[x][y]));
			}
		}
		lblTime.setText("Time:" + String.format("%.3f",(System.nanoTime() - startTime)/1000000000.0) + "s.");
		frmSudokuResolver.repaint();
	}
	
	
	/**
	 * disegna la scacchiera a sfondo bianco e le label dei numeri tutti a zero
	 */
	public void drawLines() {
		
		JSeparator[] hSeparator = new JSeparator[11];
		JSeparator[] vSeparator = new JSeparator[11];
		
		for (int i=0; i<=9; i++) {
			hSeparator[i] = new JSeparator();
			hSeparator[i].setBounds(0,0+i*20,181,1);
			hSeparator[i].setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			hSeparator[i].setBackground(i%3==0?Color.BLACK:new Color(80,80,80));
			panel.add(hSeparator[i]);
			
			vSeparator[i] = new JSeparator();
			vSeparator[i].setOrientation(SwingConstants.VERTICAL);
			vSeparator[i].setBounds(0+i*20,0,1,181);
			vSeparator[i].setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			vSeparator[i].setBackground(i%3==0?Color.BLACK:new Color(80,80,80));
			panel.add(vSeparator[i]);
		}
		
		for (int x=0; x<10; x++) {
			for (int y=0; y<10; y++) {
				board[x][y] = 0;
				sudoku[x][y] = new JLabel(" ");
				sudoku[x][y].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						changeValue(e);
					}
				});
				sudoku[x][y].setHorizontalTextPosition(SwingConstants.CENTER);
				sudoku[x][y].setHorizontalAlignment(SwingConstants.CENTER);
				sudoku[x][y].setBounds(0+x*20, 0+y*20, 20, 20);
				panel.add(sudoku[x][y]);
			}
		}
	}
	
	/**
	 * @param e contiene l'evento che ha richiamato questa funzione,
	 * incrementa la label con un numero consentito nella cella
	 * controllando ogni volta che sia valido sia per la riga
	 * che per la colonna che per il quadrato 3x3
	 */
	public void changeValue(MouseEvent e) {
		for (int x=0; x<10; x++) {
			for (int y=0; y<10; y++) {
				if (e.getSource() == sudoku[x][y]) {
					int val = board[x][y];
					val++;
					if (val > 9) val = 0;
					while (!isValid(x,y,val,board) && val != 0) {
						val++;
						if (val > 9) val = 0;
					}
					board[x][y]=val;
					sudoku[x][y].setText(board[x][y]==0 ? " " : Integer.toString(board[x][y]));
				}
			}
		}
	}
	
	/**
	 * Esplora l'albero completo delle possibilità del sudoku, appena si trova un valore impossibile da inserire 
	 * nella cella, si torna indietro e si prende un altro ramo.
	 * @param x la riga da elaborare, 0 per iniziare
	 * @param y la colonna da elaborare, 0 per iniziare
	 * @param tempBoard matrice 9x9 con la copia dello stato attuale del sudoku
	 * @return true se &eacute; stato risolto.
	 */
	public boolean resolveWithBruteForce(int x, int y, int[][] tempBoard){
		refreshLabel(tempBoard);
		
		if (x == 9) {
            x = 0;
            if (++y == 9)
                return true;
        }
        if (tempBoard[x][y] != 0) // salto le celle piene
            return this.resolveWithBruteForce(x+1,y,tempBoard);

        for (int val = 1; val <= 9; ++val) {
            if (isValid(x,y,val,tempBoard)) {
                tempBoard[x][y] = val;
                if (this.resolveWithBruteForce(x+1,y,tempBoard))
                    return true;
            }
        }
        tempBoard[x][y] = 0;
        return false;
	}
	
	/**
	 * Verifica che sia o meno accettato l'inserimento nella cella
	 * @param x la riga
	 * @param y la colonna
	 * @param val il valore da inserire
	 * @param matrice la matrice del sudoku
	 * @return true se &eacute; ammesso, false negli altri casi
	 */
	private boolean isValid(int x, int y, int val, int[][] matrice) {
		for (int riga = 0; riga < 9; riga++)
            if (matrice[riga][y] == val)
                return false;

        for (int colonna = 0; colonna < 9; colonna++){
        	if (matrice[x][colonna] == val)
                return false;
        }
        int inizioRigaQuadrato    = x - (x % 3);
        int inizioColonnaQuadrato = y - (y % 3);
        for (int riga = 0; riga < 3; riga++) 
            for (int colonna = 0; colonna < 3; colonna++)
                if (matrice[inizioRigaQuadrato + riga][inizioColonnaQuadrato + colonna] == val)
                    return false;
        return true;
	}
}