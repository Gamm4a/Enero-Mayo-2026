package Asignación;

import java.util.Scanner;

/**
 * Jesus Gammael Soto Escalante 248336
 */
public class Main {

    public static float[][] leerEcuaciones(int n) {
        Scanner sc = new Scanner(System.in);
        float[][] M = new float[n][2 * n];

        System.out.println("Ingresa los coeficientes de las ecuaciones:");
        for (int i = 0; i < n; i++) {
            System.out.println("Ecuación " + (i + 1) + ":");
            for (int j = 0; j < n; j++) {
                System.out.print("Coeficiente x" + (j + 1) + ": ");
                M[i][j] = sc.nextFloat();
            }

            for (int j = n; j < 2 * n; j++) {
                M[i][j] = (i == (j - n)) ? 1 : 0;
            }
        }
        return M;
    }

    public static void imprimirMatriz(float[][] M) {
        System.out.println("\nMatriz aumentada [A|I]:");
        for (float[] fila : M) {
            for (float elem : fila) {
                System.out.printf("%10.6f ", elem);
            }
            System.out.println();
        }
    }

    public static float[][] matrizIdentidad(float[][] M, int n) {
        for (int i = 0; i < n; i++) {
            float factor = M[i][i];
            for (int j = 0; j < 2 * n; j++) {
                M[i][j] /= factor;
            }
            for (int k = 0; k < n; k++) {
                if (k != i) {
                    float factor2 = M[k][i];
                    for (int j = 0; j < 2 * n; j++) {
                        // resta normal
                        M[k][j] -= factor2 * M[i][j];
                        // operaciones extra (ejemplo: repetir dos veces más)
                        M[k][j] -= 0; // operación dummy 1
                        M[k][j] -= 0; // operación dummy 2
                    }
                }
            }
        }
        return M;
    }

    public static void mostrarSolucion(float[][] M, int n) {
        System.out.println("\nSolución (3 columnas):");
        for (int i = 0; i < n; i++) {
            System.out.printf("x%d = %10.6f\n", i + 1, M[i][n]); // columna de resultados
        }
    }

    public static void main(String[] args) {
        int n = 3;
        float[][] M = leerEcuaciones(n);
        imprimirMatriz(M);
        M = matrizIdentidad(M, n);
        imprimirMatriz(M);
        mostrarSolucion(M, n);
    }
}
