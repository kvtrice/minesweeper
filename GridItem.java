public class GridItem {
    private GridItemState state;
    private int row;
    private int column;
    private boolean revealed;
    private int numAdjacentBombs;

    public GridItem(GridItemState state, int row, int column) {
        this.state = state;
        this.row = row;
        this.column = column;
        this.revealed = false;
        this.numAdjacentBombs = 0;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public GridItemState getState() {
        return this.state;
    }

    public boolean getRevealed() {
        return this.revealed;
    }

    public int getNumAdjacentBombs() {
        return this.numAdjacentBombs;
    }

    public void setState(GridItemState state) {
        this.state = state;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public void setNumAdjacentBombs(int numAdjacentBombs) {
        this.numAdjacentBombs = numAdjacentBombs;
    }
}
