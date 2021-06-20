import java.util.Scanner;

public class TicTacToe {
	static int[][] field = new int[3][3];
	static Scanner scanner = new Scanner(System.in);
	static final String ERROR_MESSAGE = "Invalid input!";
	static final String[] ROW_LABELS = { "A", "B", "C" };
	static Node ai;

	public static void main(final String[] args) {
		clearField();
		
		ai = new Node(field, Node.playsAsMin);
		ai.learn();

		for (;;) {
			Node current = ai;
			boolean player = Node.playsAsMin;
			System.out.println("TicTacToe - Use 1-9 to set a field!");
			printField();
			for (;;) {
				System.out.println("Player " + (player ? "X" : "O") + " is about to set");

				if (player) {
					final int[] indices = getInput();
					field[indices[0]][indices[1]] = player ? 1 : 2;
					current = current.children[indices[0]][indices[1]];
					
				} else {
					current = current.getChildWithValue();
					field = current.copyField();
				}


				printField();
				if (hasWon()) {
					System.out.println("Player " + (player ? "X" : "O") + " won!\n");
					break;
				}

				if (fieldFull()) {
					System.out.println("It's a draw!\n");
					break;
				}
				player = !player;

			}
			clearField();
		}

	}



	static void printField(int[][] field) {
		System.out.println("     1  2  3");
		System.out.println("     _______");

		for (int i = 0; i < field.length; i++) {
			System.out.print(ROW_LABELS[i] + "   ");
			for (int j = 0; j < field[i].length; j++) {

				System.out.print("|" + (field[i][j] == 0 ? "_" : field[i][j] == 1 ? "X" : "O") + "|");

			}
			System.out.print("   " + ROW_LABELS[i]);
			System.out.println();
		}
	}

	static void printField() {
		printField(field);
	}

	private static int[] getInput() {
		int value;
		for (;;) {
			try {
				value = Integer.parseInt(scanner.next());
			} catch (final Exception e) {
				System.err.println(ERROR_MESSAGE);
				continue;
			}
			value--;
			if (value < 0 || value > 8) {
				System.err.println(ERROR_MESSAGE);
				continue;
			}

			final int row = 2 - value / 3;
			final int col = value % 3;

			if (field[row][col] != 0) {
				System.err.println("Field is already set!");
				continue;
			}

			return new int[] { row, col };

		}

	}

	private static boolean hasWon() {
		return hasWon(field);
	}

	public static boolean hasWon(int[][] field) {
		for (int i = 0; i < field.length; i++) {
			if (field[i][0] == field[i][1] && field[i][1] == field[i][2] && field[i][0] != 0) {
				return true;
			}
			if (field[0][i] == field[1][i] && field[1][i] == field[2][i] && field[0][i] != 0) {
				return true;
			}
		}

		if (field[0][0] == field[1][1] && field[1][1] == field[2][2] && field[0][0] != 0) {
			return true;
		}

		if (field[0][2] == field[1][1] && field[1][1] == field[2][0] && field[0][2] != 0) {
			return true;
		}

		return false;
	}

	private static void clearField() {
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field.length; j++) {
				field[i][j] = 0;
			}

		}
	}

	private static boolean fieldFull() {
		return fieldFull(field);
	}

	public static boolean fieldFull(int[][] field) {
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field.length; j++) {
				if (field[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}

}