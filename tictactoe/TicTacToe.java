package tictactoe;
import java.util.*;

public class TicTacToe {
	
	static final int COMPUTER = 1;
	static final int HUMAN = 2;
	static final char COMPUTERMOVE = 'O';
	static final char HUMANMOVE = 'X';
	static final int SIDE = 3;  
	
	public static char[][] initialize() {
	    char[][] board = new char[SIDE][SIDE];
	    for (int i = 0; i < SIDE; i++) {
	        for (int j = 0; j < SIDE; j++) {
	            board[i][j] = '*';
	        }
	    }
	    return board;
	}
	
	public static void show_board(char[][] board) {
		for (int i = 0; i < SIDE; i++) {
			for (int j = 0; j < SIDE; j++) {
				System.out.print(board[i][j] + " ");
				if(j < SIDE - 1) {
					System.out.print("| ");	
				}
			}
			System.out.println();
			if(i < SIDE - 1) {
				System.out.println("---------");	
			}
			
		}
	}
	
	public static boolean row_crossed(char[][] board) {
		for(int i=0; i<SIDE;i++) {
			if(board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != '*') {
				return true;
			}
		}
		return false;
	}
	public static boolean column_crossed(char[][] board) {
		for(int i=0; i<SIDE;i++) {
			if(board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != '*') {
				return true;
			}
		}
		return false;
	}
	public static boolean diagonal_crossed(char[][] board) {
		if(board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[1][1] != '*') {
			return true;
		}
		if(board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[1][1] != '*') {
			return true;
		}
		return false;
	}
	
	public static boolean game_over(char[][] board) {
	    return row_crossed(board) ||
	           column_crossed(board) ||
	           diagonal_crossed(board);
	}

	public static int minimax(char[][] board, int index,  boolean ai) {
		
		if(game_over(board)) {
			if(ai) {
				return -1;
			} else {
				return 1;
			}
		}
		if(index == 9) {
			return 0;
		}
		int main_score = 0;
		if(ai) {
			main_score = -10;
		} else {
			main_score = +10;
		}
		for(int i = 0; i < SIDE; i ++) {
			for(int j = 0; j < SIDE; j++) {
				if (board[i][j] == '*') {
					if(ai) {
						board[i][j] = COMPUTERMOVE;
					}
					else {
						board[i][j] = HUMANMOVE;
					}
					int score = minimax(board, index+1 ,!ai); 
					board[i][j] = '*';
					if(ai) {
						main_score = Math.max(score, main_score);
					} else {
						main_score = Math.min(score, main_score);
					}
				}
			}
		}
		return main_score;
	}
	
	public static int[] move(char[][] board, int index) {
		int[] best_move = new int[2];
		int best_score = -10;
		for(int i = 0; i < SIDE; i ++) {
			for(int j = 0; j < SIDE; j++) {
				if (board[i][j] == '*') {
					board[i][j] = COMPUTERMOVE;
					int score = minimax(board, index+1, false);
					board[i][j] = '*';
					if(score>best_score) {
						best_score = score;
						best_move[0] = i;
						best_move[1] = j;
					}
				}
			}
		}
		return  best_move;
	}
	
	public static void play_tic_tac_toe(char[][] board, boolean ai) {
		Scanner console = new Scanner(System.in);
		int[] places = {1,2,3,4,5,6,7,8,9};
		for(int i = 0; i < SIDE; i ++) {
			for(int j = 0; j < SIDE; j++) {
				
			}
		}
		int index=0;
		while(!game_over(board)&&index!=9) {
			if(!ai) {
				System.out.println("Enter the cell number 1 to 9: ");
				int cell = console.nextInt();
				if(board[(cell-1)/3][(cell-1)%3] != '*') {
					System.out.println("the cell: " + cell + " is occupied");
					continue;
				}
				else {
					System.out.println("you have put an 'X' at " + cell + " :");
					board[(cell-1)/3][(cell-1)%3] = 'X';
					show_board(board);
					ai = true;
					index++;
				}
			}
			else {
				board[move(board,index)[0]][move(board,index)[1]] = 'O';
				int pos = move(board,index)[0]*3+move(board,index)[1]+1;
				System.out.println("computer have put an 'O' at " + pos +" :");
				show_board(board);
				ai=false;
				index++;
			}
		}
		if(game_over(board)) {
			if(ai) {
				System.out.println("winner: YOU");
			} else {
				System.out.println("winner: COMPUTER");
			}
		}
		if(index==9) {
			System.out.println("draw");
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner console = new Scanner(System.in);
		boolean play = true;
		while(play) {
			System.out.println("do you want to start first? (y/n): ");
			String chance = console.nextLine();
			if(chance.equals("y")) {
				play_tic_tac_toe(initialize(), false);
			}
			else if(chance.equals("n")) {
				play_tic_tac_toe(initialize(), true);
			}
			else {
				System.out.println("invalid input");
				continue;
			}
			System.out.println("do you want to play again? (y/n): ");
			String play_again = console.nextLine();
			if(play_again.equals("y")) {
				continue;
			}
			else {
				play = false;
			}
		}

	}

}
