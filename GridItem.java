public class GridItem {
    private GridItemState state;
    private int row;
    private int column;

    public GridItem(GridItemState state, int row, int column) {
        this.state = state;
        this.row = row;
        this.column = column;
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

    public void setState(GridItemState state) {
        this.state = state;
    }

}
