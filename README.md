Simple sudoku solver, with a recursive brute force algorithm.

![Schermata del 2021-12-17 10-07-09](https://user-images.githubusercontent.com/44254547/146520124-4d04fdf5-d522-4340-b247-01c0fdb406d0.png)

```java
/**
 * Explore the complete tree of sudoku possibilities, as soon as you find a value 
 * that is impossible to insert in the cell, go back and take another branch. 
 * @param x row, 0 first
 * @param y column, 0 first
 * @param tempBoard array 9x9
 * @return true if the solution has been found
 */
public boolean resolveWithBruteForce(int x, int y, int[][] tempBoard){
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
  ```
