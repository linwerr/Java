import Figures.Figure;
import Figures.Pawn;
import Figures.Bishop;
import Figures.King;
import Figures.Knight;
import Figures.Queen;
import Figures.Rook;
import java.util.Scanner;


public class Board {

    private char colorGame;

    public void setColorGame(char colorGame) {
        this.colorGame = colorGame;
    }

    public char getColorGame(){
        return colorGame;
    }


    private Figure fields[][] = new Figure[8][8];


    public void init(){
        this.fields[0] = new Figure[] {
                new Rook("R", 'w'), new Knight("N", 'w'), new Bishop("B", 'w'), new Queen("Q", 'w'),
                new King("K", 'w'), new Bishop("B", 'w'), new Knight("N", 'w'), new Rook("R", 'w'),
        };
        this.fields[1] = new Figure[] {
                new Pawn("P", 'w'), new Pawn("P", 'w'), new Pawn("P", 'w'), new Pawn("P", 'w'),
                new Pawn("P", 'w'), new Pawn("P", 'w'), new Pawn("P", 'w'), new Pawn("P", 'w'),
        };
        this.fields[6] = new Figure[] {
                new Pawn("P", 'b'), new Pawn("P", 'b'), new Pawn("P", 'b'), new Pawn("P", 'b'),
                new Pawn("P", 'b'), new Pawn("P", 'b'), new Pawn("P", 'b'), new Pawn("P", 'b'),
        };
        this.fields[7] = new Figure[] {
                new Rook("R", 'b'), new Knight("N", 'b'), new Bishop("B", 'b'), new Queen("Q", 'b'),
                new King("K", 'b'), new Bishop("B", 'b'), new Knight("N", 'b'), new Rook("R", 'b'),
        };
    }

    // Вывод содержимого клетки
    public String getCell(int row, int col){
        Figure figure = this.fields[row][col];
        if (figure ==null){
            return "    ";
        }
        return  " "+figure.getColor()+figure.getName()+" ";
    }

    // Печать доски
    public void print_board(){
        System.out.println(" +----+----+----+----+----+----+----+----+");
        for (int row = 7; row > -1 ; row --){
            System.out.print(row);
            for (int col=0; col<8; col++){
                System.out.print("|"+getCell(row, col));
            }
            System.out.println("|");
            System.out.println(" +----+----+----+----+----+----+----+----+");
        }

        for(int col=0; col< 8; col++){
            System.out.print("    "+col);
        }
    }


    public boolean move_figure(int row, int col, int row1, int col1) {
        if (!isInBounds(row, col) || !isInBounds(row1, col1)) {
            System.out.println("Ошибка! Координаты за пределами доски.");
            return false;
        }

        Figure figure = fields[row][col];
        if (figure == null) {
            System.out.println("Ошибка! На начальной позиции (" + row + ", " + col + ") нет фигуры.");
            return false;
        }

        // Проверка, что ходит фигура текущего цвета
        if (figure.getColor() != colorGame) {
            System.out.println("Ошибка! Неправильный цвет фигуры.");
            return false;
        }

        // Проверка на допустимость хода или атаки
        boolean isCaptureMove = fields[row1][col1] != null && fields[row1][col1].getColor() != figure.getColor();
        if (!(figure.canMove(row, col, row1, col1) || (isCaptureMove && figure.canAttack(row, col, row1, col1)))) {
            System.out.println("Ошибка! Недопустимый ход для фигуры " + figure.getName() + ".");
            return false;
        }

        // Проверка на необходимость чистого пути (исключение для коня)
        if (!(figure instanceof Knight) && !isPathClear(row, col, row1, col1, figure)) {
            System.out.println("Ошибка! Путь заблокирован для фигуры " + figure.getName() + ".");
            return false;
        }

        // Выполняем ход
        Figure targetFigure = fields[row1][col1];
        fields[row][col] = null;
        fields[row1][col1] = figure;

        // Преобразование пешки
        if (figure instanceof Pawn) {
            // Для белых пешек: если дошли до восьмой строки
            // Для черных пешек: если дошли до первой строки
            if ((figure.getColor() == 'w' && row1 == 7) || (figure.getColor() == 'b' && row1 == 0)) {
                System.out.print("Выберите фигуру для превращения (Q - ферзь, R - ладья, B - слон, N - конь): ");
                Scanner in = new Scanner(System.in);
                char choice = in.next().toLowerCase().charAt(0);

                Figure newFigure = null;
                switch (choice) {
                    case 'q':
                        newFigure = new Queen(figure.getColor() == 'w' ? "Q" : "q", figure.getColor());
                        break;
                    case 'r':
                        newFigure = new Rook(figure.getColor() == 'w' ? "R" : "r", figure.getColor());
                        break;
                    case 'b':
                        newFigure = new Bishop(figure.getColor() == 'w' ? "B" : "b", figure.getColor());
                        break;
                    case 'n':
                        newFigure = new Knight(figure.getColor() == 'w' ? "N" : "n", figure.getColor());
                        break;
                    default:
                        System.out.println("Неверный выбор! Пешка превращена в ферзя по умолчанию.");
                        newFigure = new Queen(figure.getColor() == 'w' ? "Q" : "q", figure.getColor());
                        break;
                }

                fields[row1][col1] = newFigure;
            }
        }

        // Проверка шаха после хода
        if (isKingInCheck(figure.getColor())) {
            System.out.println("Ошибка! Король останется под шахом после хода.");
            // Откат хода
            fields[row][col] = figure;
            fields[row1][col1] = targetFigure;
            return false;
        }

        // Проверка на шах противнику
        char opponentColor = opponentColor(figure.getColor());
        if (isKingInCheck(opponentColor)) {
            if (isCheckmate(opponentColor)) {
                System.out.println("Мат! Игра окончена.");
                System.exit(0);
            } else {
                System.out.println("Шах!");
            }
        }

        return true;
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    private boolean isPathClear(int row, int col, int row1, int col1, Figure figure) {
        // Проверка исключения для коня
        if (figure instanceof Knight) {
            System.out.println("Конь на " + row + "," + col + " игнорирует проверку пути.");
            return true;
        }

        // Направление движения
        int rowDirection = Integer.signum(row1 - row);
        int colDirection = Integer.signum(col1 - col);

        int currentRow = row + rowDirection;
        int currentCol = col + colDirection;

        // Проверка, пока не достигнем конечной клетки (не включая её)
        while (currentRow != row1 || currentCol != col1) {
            if (fields[currentRow][currentCol] != null) {
                System.out.println("Путь заблокирован на " + currentRow + "," + currentCol + " для фигуры " + figure.getName());
                return false;
            }
            currentRow += rowDirection;
            currentCol += colDirection;
        }
        return true;
    }

    private boolean isKingInCheck(char color) {
        int kingRow = -1, kingCol = -1;

        // Поиск короля нужного цвета
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Figure figure = fields[i][j];
                if (figure != null && figure instanceof King && figure.getColor() == color) {
                    kingRow = i;
                    kingCol = j;
                    break;
                }
            }
        }

        // Если король не найден, вывод ошибки
        if (kingRow == -1 || kingCol == -1) {
            System.out.println("Ошибка: король цвета " + color + " не найден.");
            return false;
        }

        // Проверка, атакуют ли фигуры противника короля
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Figure enemyFigure = fields[i][j];
                if (enemyFigure != null && enemyFigure.getColor() != color &&
                        enemyFigure.canAttack(i, j, kingRow, kingCol)) {
                    System.out.println("Король под атакой фигуры " + enemyFigure.getName() + " на позиции " + i + "," + j);
                    return true; // Король под шахом
                }
            }
        }
        return false; // Король не под шахом
    }


    private boolean isCheckmate(char color) {
        // Для каждого возможного хода фигуры проверяем, может ли она спасти короля
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Figure figure = fields[i][j];
                if (figure != null && figure.getColor() == color) {
                    // Для каждой фигуры пробуем все возможные ходы
                    for (int row1 = 0; row1 < 8; row1++) {
                        for (int col1 = 0; col1 < 8; col1++) {
                            Figure targetFigure = fields[row1][col1];
                            if (moveWithoutCheck(i, j, row1, col1)) {
                                // Откатываем ход
                                fields[i][j] = figure;
                                fields[row1][col1] = targetFigure;
                                return false; // Если хотя бы один ход возможен, то мата нет
                            }
                        }
                    }
                }
            }
        }
        return true; // Если не найдено ходов, снимающих шах, это мат
    }

    private boolean moveWithoutCheck(int row, int col, int row1, int col1) {
        if (!isInBounds(row, col) || !isInBounds(row1, col1) || fields[row][col] == null) {
            return false;
        }

        Figure figure = fields[row][col];
        boolean isCaptureMove = fields[row1][col1] != null && fields[row1][col1].getColor() != figure.getColor();

        // Проверка, может ли фигура выполнить ход или атаку
        if (!(figure.canMove(row, col, row1, col1) || (isCaptureMove && figure.canAttack(row, col, row1, col1)))) {
            return false;
        }

        if (!isPathClear(row, col, row1, col1, figure)) {
            return false;
        }

        // Пробуем сделать ход
        Figure targetFigure = fields[row1][col1];
        fields[row][col] = null;
        fields[row1][col1] = figure;

        // Проверка, находится ли король под шахом после хода
        boolean kingInCheck = isKingInCheck(figure.getColor());

        // Откат хода
        fields[row][col] = figure;
        fields[row1][col1] = targetFigure;

        return !kingInCheck; // Возвращает true, если ход возможен без шаха королю
    }

    private char opponentColor(char color) {
        return color == 'w' ? 'b' : 'w';
    }
}
