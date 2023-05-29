public class Cell {
    private int x;
    private int y;
    private int size;
    private int row;
    private int column;
    private CellState cellState;

    private Rules rules;

    /**
     * this is a constructor, it makes an object of Cell by intaking parameters
     * @param x
     * @param y
     * @param size
     * @param row
     * @param column
     * @param cellState
     * @param rules
     */
    public Cell(int x, int y, int size, int row, int column, CellState cellState, Rules rules){
        this.x = x;
        this.y = y;
        this.size = size;
        this.row = row;
        this.column = column;
        this.cellState = cellState;
        this.rules = rules;
    }

    /**
     * this method displays cells as squares, and fills in the squares with different colours depending on their state
     * if cells are dead, they are white, and if they are alive, they are black.
     */
    public void display(){
        if (cellState == CellState.DEAD) {
            Main.app.fill(255);
        } else if (cellState == CellState.ALIVE) {
            Main.app.fill(0);
        } else if (cellState == CellState.WILL_DIE) {
            Main.app.fill(0);
        } else if (cellState == CellState.WILL_REVIVE) {
            Main.app.fill(255);
        }

        Main.app.rect(x,y,size,size);
    }

    /**
     * this method applies Moorerules based on how many neighbors of a cell are alive.
     * @param floss
     */

    public void applyRules(Cell[][] floss){ //griddy is the whole 2D array
        int liveNeighbors = countLiveNeighbors(floss);
        cellState = rules.applyRules(cellState, liveNeighbors);
    }

    /**
     * used in the method applyRules. It iterates through a cell's neighbors and increments the count of neighbors that are alive
     * if the og cell is alive, it will over count (b/c it iterates through og cell), so I subtract one at the end if this is true
     * @param maingriddy
     * @return
     */
    private int countLiveNeighbors(Cell[][] maingriddy){
        int liveCount = 0;

        for(int a = -1; a < 2; a++){ //row
            for(int b = -1; b < 2; b++){ //column
                if(maingriddy[row + a][column +b].cellState == CellState.ALIVE || maingriddy[row+ a][column+b].cellState == CellState.WILL_DIE){ //how do I access which r and c i'm at in the grid
                    liveCount++;
                }
            }
        }

        if(maingriddy[row][column].cellState == CellState.ALIVE || maingriddy[row][column].cellState == CellState.WILL_DIE){
            liveCount--;
        }
        return liveCount;
    }

    /**
     * this method checks whether your mouse click was inside a cell. If the click is inside a cell, the state will change from being alive
     * to dead or vice versa
     * @param mX
     * @param mY
     */

    public void handleClick(int mX, int mY){
        if (mX > x && mX < x + size  && mY > y && mY < y + size) {
            Main.app.println("mouseclicked");
            if (cellState == CellState.DEAD) {
                cellState = CellState.ALIVE;
            } else if (cellState == CellState.ALIVE) {
                cellState = CellState.DEAD;
            }
        }
    }

    /**
     * this method advances a cell. if it was calculated to die in the next "generation", this method changes it from "going to die"
     * to dead. the same goes for if it was "going to revive"
     */
    public void evolve(){
        if (cellState == CellState.WILL_REVIVE){
            cellState = CellState.ALIVE;
        } else if (cellState == CellState.WILL_DIE){
            cellState = CellState.DEAD;
        }
    }
}
