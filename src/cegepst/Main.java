package cegepst;

import java.util.Random;
import java.util.Scanner;

public class Main {
    /**
     * @Author kevbon
    **/
    public static void main(String[] args) {

        char board[][] = new char[4][4];
        char lastSupEntry[][] = new char[4][4];
        char lastBatEntry[][] = new char[4][4];
        Random random = new Random();
        boolean player1;
        boolean gameOver = false;
        boolean batarangUsed = false;
        boolean tacticianUsed = false;
        boolean heatVisionUsed = false;
        boolean highSpeedUsed = false;

        initialiseBoard(board);
        initialiseBoard(lastSupEntry);
        initialiseBoard(lastBatEntry);

        if (random.nextInt(2) == 0) {
            player1 = true;
        } else {
            player1 = false;
        }

        System.out.println("Tic-Tac-Toe!");

        while(!gameOver) {

            turn(board, player1, lastSupEntry, lastBatEntry);

            char token;

            if(player1) {
                token = 'B';
            } else {
                token = 'S';
            }

            if (token == 'B') {
                int powerId = whichBatPower(batarangUsed, tacticianUsed);
                if (powerId == 1) {
                    if (!batarangUsed) {
                        batarangUsed = true;
                        batarang(lastSupEntry, board);
                    } else {
                        System.out.println("Ce pouvoir à déjà été utilisé...");
                    }
                } else if (powerId == 2) {
                    if (!tacticianUsed) {
                        tacticianUsed = true;
                        tactician(board, lastBatEntry);
                    } else {
                        System.out.println("Ce pouvoir à déjà été utilisé...");
                    }
                }
            }

            if (token == 'S') {
                int powerId = whichSupPower(highSpeedUsed, heatVisionUsed);
                if (powerId == 1) {
                    if (!highSpeedUsed){
                        highSpeedUsed = true;
                        highSpeed(board, lastSupEntry, lastBatEntry);
                    } else {
                        System.out.println("Ce pouvoir à déjà été utilisé...");
                    }
                } else if (powerId == 2) {
                    if (!heatVisionUsed) {
                        heatVisionUsed = true;
                        heatVision(board);
                    } else {
                        System.out.println("Ce pouvoir à déjà été utilisé...");
                    }
                }
            }

            if (winningPlayer(board) == 'B') {
                System.out.println("Batman a triomphé!");
                gameOver = true;
            } else  if (winningPlayer(board) == 'S'){
                System.out.println("Superman a vaincu!");
                gameOver = true;
            } else {
                if (boardFull(board)) {
                    System.out.println("Les héros sont hors de souffle... Égalité!");
                    gameOver = true;
                } else {
                    player1 = !player1;
                }
            }
        }

        showBoard(board);
        System.out.println("GG!");
    }

    public static void turn(char board[][],boolean player1, char lastSupEntry[][], char lastBatEntry[][]) {
        char token;
        showBoard(board);

        if(player1) {
            System.out.println("Batman, c'est ton tour!");
            token = 'B';
        } else {
            System.out.println("Superman, c'est ton tour!");
            token = 'S';
        }

        playTurn(board, token, lastSupEntry, lastBatEntry);
    }

    public static void playTurn(char board[][], char token, char lastSupEntry[][], char lastBatEntry[][]) {
        int row = 0;
        int col = 0;
        boolean isInvalid = true;

        Scanner keyboard = new Scanner(System.in);

        while (isInvalid) {

            System.out.print("Choisi une position en X [0-3] : ");
            col = keyboard.nextInt();
            System.out.print("Choisi une position en Y [0-3] : ");
            row = keyboard.nextInt();

            if (board[row][col] != '-') {
                System.out.println("Cette position est déjà utilisée! Réessayez...");
            } else {
                isInvalid = false;
            }

        }

        board[row][col] = token;

        if (token == 'S') {
            initialiseBoard(lastSupEntry);
            lastSupEntry[row][col] = board[row][col];
        } else if (token == 'B') {
            initialiseBoard(lastBatEntry);
            lastBatEntry[row][col] = board[row][col];
        }
    }

    public static int whichBatPower(boolean batarangUsed, boolean tacticianUsed) {
        Scanner keyboard = new Scanner(System.in);
        int choice = -1;

        if (batarangUsed && tacticianUsed) {
            System.out.println("Batman, vous avez utilisé tous vos pouvoirs...");
            return -1;
        }

        while (choice != 1 && choice != 2 && choice != 0) {
            System.out.print("Voulez-vous utiliser un pouvoir? (Batarang = 1/Tactician = 2, sinon entrez 0) :");
            choice = keyboard.nextInt();

            if (choice != 1 && choice != 2 && choice != 0) {
                System.out.println("CHOIX INVALIDE! Veuillez réessayé!");
            }
        }
        return choice;
    }

    public static int whichSupPower(boolean highSpeedUsed, boolean heatVisionUsed) {
        Scanner keyboard = new Scanner(System.in);
        int choice = -1;

        if (highSpeedUsed && heatVisionUsed) {
            System.out.println("Superman, vous avez utilisé tous vos pouvoirs...");
            return -1;
        }

        while (choice != 1 && choice != 2 && choice != 0) {
            System.out.print("Voulez-vous utiliser un pouvoir? (High Speed = 1/Heat Vision = 2, sinon entrez 0) :");
            choice = keyboard.nextInt();

            if (choice != 1 && choice != 2 && choice != 0) {
                System.out.println("CHOIX INVALIDE! Veuillez réessayé!");
            }
        }
        return choice;
    }

    public static void batarang(char lastSupEntry[][], char board[][]) {
        int row = -1;
        int col = -1;
        int ranRow;
        int ranCol;

        for (int i = 0; i < lastSupEntry.length; i++) {
            for (int j = 0; j < lastSupEntry.length; j++) {
                if (lastSupEntry[i][j] == 'S') {
                    row = i;
                    col = j;
                }
            }
        }
        if (row == -1) {
            return;
        }
        do {
            Random random = new Random();
            ranRow = random.nextInt(4);
            ranCol = random.nextInt(4);
        } while (board[ranRow][ranCol] != '-');
        board[ranRow][ranCol] = 'S';

        board[row][col] = '-';

    }

    public static void tactician(char board[][], char lastBatEntry[][]) {
        int row = -1;
        int col = -1;
        boolean isInvalid = true;
        int futureRow = -1;
        int futureCol = -1;

        Scanner keyboard = new Scanner(System.in);

        for (int i = 0; i < lastBatEntry.length; i++) {
            for (int j = 0; j < lastBatEntry.length; j++) {
                if (lastBatEntry[i][j] == 'B') {
                    row = i;
                    col = j;
                }
            }
        }
        while (isInvalid) {

            System.out.print("Choisi une position en X [0-3] : ");
            futureCol = keyboard.nextInt();
            System.out.print("Choisi une position en Y [0-3] : ");
            futureRow = keyboard.nextInt();

            if (board[futureRow][futureCol] != 'S') {
                System.out.println("Cette position est vide! Réessayez...");
            } else {
                isInvalid = false;
            }

        }
        board[futureRow][futureCol] = 'B';
        board[row][col] = 'S';
    }

    public static void highSpeed(char board[][], char lastSupEntry[][], char lastBatEntry[][]) {

        System.out.println("High Speed activé!");
        turn(board, false, lastSupEntry, lastBatEntry);
    }

    public static void heatVision(char board[][]) {
        int row = 0;
        int col = 0;
        boolean isInvalid = true;


        Scanner keyboard = new Scanner(System.in);

        System.out.println("Heat Vision activé!");
        while (isInvalid) {

            System.out.print("Veuillez entrer une position contenant un signe de Batman. D'abord en X [0-3] :");
            col = keyboard.nextInt();
            System.out.print("Maintenant une position en Y [0-3] :");
            row = keyboard.nextInt();

            if (board[row][col] != 'B') {
                System.out.println("Il n'y a pas de jeton de Batman sur cet emplacement... réessayez!");
            } else {
                isInvalid = false;
                board[row][col] = '-';
            }
        }
    }

    public static void showBoard(char board[][]) {
        System.out.println("Voici le tableau: ");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    public static char winningPlayer(char board[][]) {
        for (int i = 0; i < 4; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][2] == board[i][3] && board[i][0] != '-') {
                return board[i][0];
            }
        }

        for (int j = 0; j < 4; j++) {
            if (board[0][j] == board[1][j] && board[1][j] == board[2][j] && board[2][j] == board[3][j] && board[0][j] != '-') {
                return board[0][j];
            }
        }

        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[2][2] == board[3][3] && board[3][3] != '-') {
            return board[0][0];
        }
        if (board[0][3] == board[1][2] && board[1][2] == board[2][1] && board[2][1] == board[3][0] && board[3][0] != '-') {
            return board[3][0];
        }

        return ' ';
    }

    public static boolean boardFull(char board[][]) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    public static void initialiseBoard(char board[][]) {
        char passiveToken = '-';

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = passiveToken;
            }
        }
    }
}
