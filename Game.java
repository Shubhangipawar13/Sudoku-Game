import java.util.Random;
import java.util.Scanner;

class Sudoku {

    public static void Sudoku(int size, int level) {
        Scanner scanner = new Scanner(System.in);
        int boxWidth = String.valueOf(size).length() + 2;
        String firstLine = "-".repeat(boxWidth);

        int[][] array = new int[size][size];
        int[][] solution = new int[size][size];

        int[] numbers = new int[size];
        for (int i = 0; i < size; i++) {
            numbers[i] = i + 1;
        }
        shuffleArray(numbers); 

        for (int hl = 0; hl < size; hl++) {
            array[0][hl] = numbers[hl];
        }

        for (int vl = 1; vl < size; vl++) {
            for (int hl = 0; hl < size; hl++) {
                array[vl][hl] = array[vl - 1][(hl + 1) % size]; 
            }
        }

        for (int vl = 0; vl < size; vl++) {
            System.arraycopy(array[vl], 0, solution[vl], 0, size);
        }

        int removeCells;
        if (level == 1) {
            removeCells = (size * size) / 4;
        } else if (level == 2) {
            removeCells = (size * size) / 3;
        } else {
            removeCells = (2 * size * size) / 3;
        }

        int[][] emptyCells = new int[removeCells][2];
        Random rand = new Random();
        int index = 0;

        while (removeCells > 0) {
            int hl = rand.nextInt(size);
            int vl = rand.nextInt(size);

            if (array[hl][vl] != 0) {
                array[hl][vl] = 0;
                emptyCells[index][0] = hl;
                emptyCells[index][1] = vl;
                index++;
                removeCells--;
            }
        }

        displaySudoku(array, size, boxWidth, firstLine);

        for (int[] cell : emptyCells) {
            System.out.println("Enter value for row " + (cell[0] + 1) + ", column " + (cell[1] + 1) + ": ");
            int userInput = scanner.nextInt();
            array[cell[0]][cell[1]] = userInput;
        }

        if (checkSolution(array, solution)) {
            System.out.println("\n Congratulations! You solved the Sudoku correctly! ");
        } else {
            System.out.println("\n Incorrect solution! Better luck next time. ");
        }
    }

    private static void shuffleArray(int[] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
    private static void displaySudoku(int[][] array, int size, int boxWidth, String firstLine) {
        StringBuilder sb = new StringBuilder();
        for (int vl = 0; vl < size; vl++) {
            for (int hl = 0; hl < size; hl++) {
                sb.append(" ").append(firstLine);
            }
            sb.append(" \n");
            for (int hl = 0; hl < size; hl++) {
                if (array[vl][hl] == 0) {
                    sb.append(String.format("|%" + boxWidth + "s", " "));
                } else {
                    sb.append(String.format("|%" + boxWidth + "d", array[vl][hl]));
                }
            }
            sb.append("|\n");
        }
        for (int hl = 0; hl < size; hl++) {
            sb.append(" ").append(firstLine);
        }
        sb.append(" \n");
        System.out.print(sb.toString());
    }
    private static boolean checkSolution(int[][] userGrid, int[][] solution) {
        for (int i = 0; i < userGrid.length; i++) {
            for (int j = 0; j < userGrid[i].length; j++) {
                if (userGrid[i][j] != solution[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the Sudoku size to start with (e.g., 4, 6, 9): ");
        int size = scanner.nextInt();

        while (true) {
            for (int level = 1; level <= 3; level++) {
                System.out.println("\nStarting Sudoku of size " + size + " (Level " + level + ")...");
                Sudoku(size, level);

                System.out.println("\nDo you want to continue to the next level? (yes/no)");
                String choice = scanner.next().toLowerCase();
                if (!choice.equals("yes")) {
                    System.out.println("Exiting the game. Thanks for playing! 😊");
                    return;
                }
            }
            System.out.println("\nDo you want to continue to the next Sudoku size (Level 1)? (yes/no)");
            String choice = scanner.next().toLowerCase();
            if (!choice.equals("yes")) {
                System.out.println("Exiting the game. Thanks for playing! 😊");
                break;
            }
            size++; 
        }
        scanner.close();
    }
}

