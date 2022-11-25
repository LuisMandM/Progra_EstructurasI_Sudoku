package com.ikasgela;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    static int[][] tablero = {{5, 3, 0, 0, 7, 0, 0, 0, 0}, {6, 0, 0, 1, 9, 5, 0, 0, 0}, {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3}, {4, 0, 0, 8, 0, 3, 0, 0, 1}, {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0}, {0, 0, 0, 4, 1, 9, 0, 0, 5}, {0, 0, 0, 0, 8, 0, 0, 7, 9}};

    static boolean[] no_rep_col = {false, false, false, false, false, false, false, false, false,};
    static boolean[] no_rep_fil = {false, false, false, false, false, false, false, false, false,};
    static boolean[] no_rep_reg = {false, false, false, false, false, false, false, false, false,};


    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String entrada_1;
        boolean canPlay = true;


        do {
            visualizarTablero();
            System.out.print("Ingrese la fila en la que quiere agregar: ");
            entrada_1 = br.readLine().toLowerCase();
            if (!entrada_1.equals("fin")) {
                try {
                    int fila = Integer.parseInt(entrada_1) - 1;
                    System.out.print("Ingrese la columna en la que quiere agregar: ");
                    int columna = Integer.parseInt(br.readLine()) - 1;
                    System.out.print("Ingrese el valor a agregar: ");
                    int valor_juego = Integer.parseInt(br.readLine());
                    if (fila >= 0 && fila < 9 && columna >= 0 && columna < 9 && valor_juego > 0 && valor_juego <= 9) {
                        VerificacionJugada(fila, columna, valor_juego);
                        tablero[fila][columna] = valor_juego;

                    } else {
                        System.out.println("Error: Verifica los datos ingresados\n");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\nError:Entrada invalida\n");
                }
            }

            if (IsFull() || entrada_1.equals("fin")) {
                canPlay = false;
            }
        } while (canPlay);

        if (VerificarRepeticion()) {
            System.out.println("\nFelicidades has ganado");
        } else {
            System.out.println("\nGame Over");
        }


    }

    private static void visualizarTablero() {
        int cont_limit = 0;
        System.out.println("   1  2  3   4  5  6   7  8  9\n ______________________________");
        for (int i = 0; i < tablero.length; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < tablero[i].length; j++) {
                if (tablero[i][j] == 0 && cont_limit == 2) {
                    System.out.print(" " + "*" + " |");
                    cont_limit = 0;
                } else if (tablero[i][j] == 0 && cont_limit < 2) {
                    System.out.print(" " + "*" + " ");
                    cont_limit++;
                } else if (cont_limit == 2) {
                    System.out.print(" " + tablero[i][j] + " |");
                    cont_limit = 0;
                } else {
                    System.out.print(" " + tablero[i][j] + " ");
                    cont_limit++;
                }
            }
            if ((i + 1) % 3 == 0) {
                System.out.println("\n ______________________________");
            } else {
                System.out.println();
            }
        }
    }

    private static boolean IsFull() {
        int contador_llenado = 0;

        for (int[] filas : tablero) {
            for (int columna : filas) {
                if (columna != 0) {
                    contador_llenado++;
                }
            }
        }
        return contador_llenado == 81;
    }

    private static void VerificacionJugada(int fila, int columna, int valor_juego) {

        // recorrido por fila unitario;
        int num_rep_fil = 0;
        for (int j = 0; j < tablero[fila].length; j++) {
            if (tablero[fila][j] == valor_juego && tablero[fila][j] != 0) {
                num_rep_fil++;
            }
        }
        if (num_rep_fil == 0) {
            no_rep_fil[fila] = true;
        }

        //Recorrer Columnas unitarias
        int num_rep_col = 0;
        for (int j = 0; j < tablero[fila].length; j++) {
            if (tablero[j][columna] == valor_juego && tablero[j][columna] != 0) {
                num_rep_col++;
            }
        }
        if (num_rep_col == 0) {
            no_rep_col[columna] = true;
        }

        //recorrer region
        //init pos fila
        int init_pos_x = fila / 3;
        int init_pos_f = init_pos_x * 3;

        //init pos columna
        int init_pos_y = columna / 3;
        int init_pos_c = init_pos_y * 3;

        int ver_rep = 0;
        for (int i = init_pos_f; i < init_pos_f + 3; i++) {
            for (int j = init_pos_c; j < init_pos_c + 3; j++) {
                if (tablero[i][j] == valor_juego) {
                    ver_rep++;
                }
            }
        }


        if (ver_rep == 0) {
            if (init_pos_f == 0) {
                if (init_pos_c == 0) {
                    no_rep_reg[0] = true;
                }
                if (init_pos_c == 3) {
                    no_rep_reg[1] = true;
                }
                if (init_pos_c == 6) {
                    no_rep_reg[2] = true;
                }

            } else if (init_pos_f == 3) {
                if (init_pos_c == 0) {
                    no_rep_reg[3] = true;
                }
                if (init_pos_c == 3) {
                    no_rep_reg[4] = true;
                }
                if (init_pos_c == 6) {
                    no_rep_reg[5] = true;
                }

            } else if (init_pos_f == 6) {
                if (init_pos_c == 0) {
                    no_rep_reg[6] = true;
                }
                if (init_pos_c == 3) {
                    no_rep_reg[7] = true;
                }
                if (init_pos_c == 6) {
                    no_rep_reg[8] = true;
                }
            }
        }
    }

    private static boolean VerificarRepeticion() {
        boolean win = true;
        for (boolean columna : no_rep_col) {
            if (!columna) {
                win = false;
                break;
            }
        }
        for (boolean fila : no_rep_fil) {
            if (!fila) {
                win = false;
                break;
            }
        }
        for (boolean region : no_rep_reg) {
            if (!region) {
                win = false;
                break;
            }
        }
        return win;
    }
}
