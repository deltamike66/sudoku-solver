import java.util.ArrayList;

/**
 * @author marco marconi
 *
 */
public class Karnaugh {

	private char[] indexes = new char[16];
	private boolean[][][][] vis = new boolean[4][4][5][5];
	private int[][] k_sizes = {{4, 4}, {2, 4}, {4, 2}, {1, 4}, {4, 1}, {2, 2}, {1, 2}, {2, 1}, {1, 1}};
	private int k_indexes[] = {0, 1, 3, 2, 4, 5, 7, 6, 12, 13, 15, 14, 8, 9, 11, 10};
	
	private ArrayList<ArrayList<Integer>> groupsOne = new ArrayList<ArrayList<Integer>>();
	private ArrayList<ArrayList<Integer>> groupsZero = new ArrayList<ArrayList<Integer>>();
	
	private String sigma;
	private String formulaAnd;
	private String formulaOr;
	
	/**
	 * Inizializza le uscite a indeterminato
	 */
	public Karnaugh() {
		for (int i=0; i<16;i++) {
			this.indexes[i] = 'X';
		}
		this.setVisitedToFalse();
	}
	
	private void setVisitedToFalse() {
		for (int i=0;i<4;i++)
			for (int t=0;t<4;t++)
				for (int w=0;w<5;w++)
					for (int z=0;z<5;z++)
						this.vis[i][t][w][z] = false;
	}
	
	
	/**
	 * Risolve la mappa di Karnaugh, generando le formule delle soluzioni, sia nel 
	 * formato con i minimi termini che con i max termini
	 */
	public void resolve() {
		this.setVisitedToFalse();
		this.groupsOne.clear();
		this.groupsZero.clear();
		int[] xy = new int[] {-10,-10};
		
		for (char c: new char[] {'0','1'}) {
			for (int i=0;i<9;i++) {
				do {
		            xy = this.check(k_sizes[i][0], k_sizes[i][1],c);
		            if(xy[0] != -10 && xy[1] != -10) {
		            	this.addGroups(xy[0], xy[1], k_sizes[i][0], k_sizes[i][1],c);
		            }
		        } while(xy[0] != -10 && xy[1] != -10);  
			}
		}
		
		this.setFormulaAnd();
		this.setFormulaOr();
		
	}
	
	/**
	 * Array di interi con i valori dei gruppi trovati con tutti uno e indeterminati
	 * @return ArrayList di un ArrayList di interi
	 */
	public ArrayList<ArrayList<Integer>> getGrpOne (){
		return groupsOne;
	}
	
	/**
	 * Array di interi con i valori dei gruppi trovati con tutti zeri e indeterminati
	 * @return ArrayList di un ArrayList di interi
	 */
	public ArrayList<ArrayList<Integer>> getGrpZero (){
		return groupsZero;
	}
	
	
	/**
	 * aggiungo le celle nel rettangolo, con tutti i caratteri uguali a c, purchè non siano tutti stati indeterminati
	 * a partire dalla cella in alto a sinistra identificata con i e j, di larghezza e altezza width e lenght
	 * @param i coordinata x
	 * @param j coordinata y
	 * @param width larghezza
	 * @param length altezza
	 * @param c carattere da verificare, '0' o '1'
	 */
	private void addGroups(int i, int j, int width, int length, char c) {
		ArrayList<Integer> group = new ArrayList<Integer>();
		for(int x = i; x < i + width; ++x) {
			for(int y = j; y < j + length; ++y) {
				group.add(k_indexes[((x + 4) % 4) * 4 + ((y + 4) % 4)]);
			}
		}
		
		//controllo se il gruppo non sia formato da tutti stati indeterminati
		boolean allX = true;
		for (int g: group) {
			if (indexes[g] != 'X') {
				allX = false;
			}
		}
		
		if (!allX) {
			if (c == '0') {
				this.groupsZero.add(group);
			} else {
				this.groupsOne.add(group);
			}
		}
	}
	
	/**
	 * controlla se esiste un rettangolo che parte dalle coordinate i,j
	 * con tutti uni all'interno compresi anche gli stati indeterminati
	 * @param i colonna
	 * @param j riga
	 * @param width 
	 * @param height
	 * @return true se ci sono tutti uni, false negli altri casi
	 */
	private boolean allValue(int i, int j, int width, int length, char c) {
		c = c == '0' ? '1' : '0';
		for(int x = i; x < i + width; x++) {
			for(int y = j; y < j + length; y++) {
				if(this.indexes[this.k_indexes[((x + 4) % 4) * 4 + ((y + 4) % 4)]] == c) {
					return false;									
				}
			}
		}
		return true;
	}
	
	/**
	 * Controllo se c'è un rettangolo width x height con tutti uno all'interno, compresi gli stati indeterminati
	 * @param width
	 * @param height
	 */
	private int[] check(int width, int length, char c) {
		for(int i = 0; i < 4 ; ++i) {
			for(int j = 0; j < 4 ; ++j) {
				if(!this.visited(i, j, width, length)) {
					if (this.allValue(i, j, width, length, c)) {
						this.setVisited(i, j, width, length);
						return new int[] {i,j};
					}
					
					if(this.allValue(i - width + 1, j, width, length, c)) {
						setVisited(i - width + 1, j, width, length);
						return new int[] {i - width + 1 , j};
			        }

			        if(allValue(i - width + 1, j - length + 1, width, length, c)) {
			        	setVisited(i - width + 1, j - length + 1, width, length);
			            return new int[] {i - width + 1, j - length + 1};
			        }

			        if(allValue(i, j - length + 1, width, length, c)) {
			        	setVisited(i, j - length + 1, width, length);
			            return new int[] {i, j - length + 1};
			        }
				}
			}
		}
		return new int[] {-10,-10};
	}
	
	private void setVisited(int i, int j, int width, int length) {
		for(int x = i; x < i + width; ++x) {
			for(int y = j; y < j + length; ++y){
				this.vis[((x + 4) % 4)][((y + 4) % 4)][width][length] = true;
			}
		}
	}
	
	private boolean visited(int i, int j, int width, int length) {
		for(int x = 1; x < 5; ++x) {
			for(int y = 1; y < 5; ++y) {
				if(vis[i][j][x][y]) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * restituisce il valore dell'uscita Y
	 * @param i il numero dell'output
	 * @return '0' '1' o 'X'
	 */
	public char getY(int i) {
		return this.indexes[i];
	}
	
	/**
	 * imposta il valore dell'uscita Y
	 * @param i il numero dell'output da settare
	 * @param c '0' '1' o 'X'
	 */
	public void setY(int i, char c) {
		this.indexes[i] = c;
		this.setFormula();
	}
	
	/**
	 * restituisce il carattere all'interno della mappa di Karnaugh con l'indice 0 in alto a sinistra:<br>
	 * 0 &nbsp;&nbsp;1 &nbsp;&nbsp;2 &nbsp;&nbsp;3<br>
	 * 4 &nbsp;&nbsp;5 &nbsp;&nbsp;6 &nbsp;&nbsp;7<br>
	 * 8 &nbsp;&nbsp;9 &nbsp;10 11<br>
	 * 12 13 14 15<br>
	 * @param i il numero della cella
	 * @return '0' '1' o 'X'
	 */
	public char getKMap(int i) {
		return this.indexes[k_indexes[i]];
	}
	
	/**
	 * crea la stringa con la formula della sommatoria delle uscite a uno
	 * nel formato f(A,B,C,D)=&Sigma;(1,2,...)
	 * @return String formula con la sommatoria delle uscite
	 */
	private void setFormula() {
		this.sigma="";
		for (int i=0;i<16;i++) {
			if (this.indexes[i] == '1') {
				this.sigma = this.sigma + Integer.toString(i) + ",";
			}
		}
		if (this.sigma.length()>0) {
			this.sigma = this.sigma.substring(0,this.sigma.length()-1);
		}
		this.sigma = "f(A,B,C,D)=\u03A3("+sigma+")";
	}
	
	/**
	 * crea la stringa con la formula finale composta dai min termini
	 * e cioè con le somme dei prodotti
	 */
	private void setFormulaAnd() {
		boolean A,B,C,D;
		StringBuilder formulaOut = new StringBuilder();
		formulaOut.append("F=");
		for (ArrayList<Integer> g: this.groupsOne) {
			int a,b,c,d;
			A=B=C=D=true;
			a=g.get(0)&0b1000;
			b=g.get(0)&0b0100;
			c=g.get(0)&0b0010;
			d=g.get(0)&0b0001;
			
			for (int i=1; i< g.size();i++) {
				A &= !(a != (g.get(i)&0b1000));
				B &= !(b != (g.get(i)&0b0100));
				C &= !(c != (g.get(i)&0b0010));
				D &= !(d != (g.get(i)&0b0001));
			}
			formulaOut.append(A == true ? a == 0 ? "A'" : "A" : ""); 
			formulaOut.append(B == true ? b == 0 ? "B'" : "B" : ""); 
			formulaOut.append(C == true ? c == 0 ? "C'" : "C" : ""); 
			formulaOut.append(D == true ? d == 0 ? "D'" : "D" : ""); 
			formulaOut.append("+");
		}
		formulaOut.deleteCharAt(formulaOut.length()-1);
		this.formulaAnd = formulaOut.toString();
	}
	
	/**
	 * crea la stringa con la formula finale composta dai max termini
	 * e cioè con i prodotti delle somme
	 */
	private void setFormulaOr() {
		boolean A,B,C,D;
		StringBuilder formulaOut = new StringBuilder();
		formulaOut.append("F=(");
		for (ArrayList<Integer> g: this.groupsZero) {
			int a,b,c,d;
			A=B=C=D=true;
			a=g.get(0)&0b1000;
			b=g.get(0)&0b0100;
			c=g.get(0)&0b0010;
			d=g.get(0)&0b0001;
			
			for (int i=1; i< g.size();i++) {
				A &= !(a != (g.get(i)&0b1000));
				B &= !(b != (g.get(i)&0b0100));
				C &= !(c != (g.get(i)&0b0010));
				D &= !(d != (g.get(i)&0b0001));
			}
			formulaOut.append(A == true ? a == 8 ? "A'+" : "A+" : ""); 
			formulaOut.append(B == true ? b == 4 ? "B'+" : "B+" : ""); 
			formulaOut.append(C == true ? c == 2 ? "C'+" : "C+" : ""); 
			formulaOut.append(D == true ? d == 1 ? "D'+" : "D+" : ""); 
			formulaOut.deleteCharAt(formulaOut.length()-1);
			formulaOut.append(")(");
		}
		formulaOut.deleteCharAt(formulaOut.length()-1);
		this.formulaOr = formulaOut.toString();
	}
	
	public String getFormulaOr() {
		return this.formulaOr;
	}
	
	public String getFormulaAnd() {
		return this.formulaAnd;
	}
	
	public String getFormula() {
		return this.sigma;
	}
	
	/**
	 * inserisce il carattere c su tutti i valori della Y
	 * c viene controllato perchè sia solo '0' '1' o 'X'
	 * @param c carattere da inserire, può assumere solo '0' '1' o 'X'
	 */
	public void setAll(char c) {
		if (c == '0' || c == '1' || c == 'X') {
			for (int i=0; i<16; i++) {
				this.setY(i, c);
			}
		}
	}
}
