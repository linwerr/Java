package Figures;

public class Pawn extends Figure {
    private boolean isFirstStep = true;

    public Pawn(String name, char color) {
        super(name, color);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1) {
        if (col != col1) {  // Пешка может двигаться только по вертикали
            return false;
        }

        int direction = (this.getColor() == 'w') ? 1 : -1;

        if (isFirstStep) {
            if ((row + 2 * direction == row1 || row + direction == row1) && col == col1) {
                isFirstStep = false;
                return true;
            }
        } else {
            return row + direction == row1 && col == col1;
        }
        return false;
    }

    @Override
    public boolean canAttack(int row, int col, int row1, int col1) {
        int direction = (this.getColor() == 'w') ? 1 : -1;

        // Пешка может атаковать только по диагонали на одну клетку вперед
        return row + direction == row1 && Math.abs(col - col1) == 1;
    }
}
