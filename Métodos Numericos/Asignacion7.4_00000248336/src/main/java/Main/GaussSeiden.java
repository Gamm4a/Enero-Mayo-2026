package Main;

import java.util.Scanner;

/**
 * @author Jesus Gammael Soto Escalante 00000248336
 */
public class GaussSeiden {

    // Método para leer la matriz ampliada
    public static double[][] leerEcuaciones(int n) {
        Scanner sc = new Scanner(System.in);
        double[][] a = new double[n][n+1];

        System.out.println("Ingresa los coeficientes y resultados del sistema:");
        for (int i = 0; i < n; i++) {
            System.out.println("Ecuación " + (i+1) + ":");
            for (int j = 0; j < n; j++) {
                System.out.print("Coeficiente x" + (j+1) + ": ");
                a[i][j] = sc.nextDouble();
            }
            System.out.print("Resultado: ");
            a[i][n] = sc.nextDouble();
        }
        return a;
    }

    // Método para imprimir la matriz ampliada
    public static void imprimirMatriz(double[][] a) {
        System.out.println("\nMatriz ampliada:");
        for (double[] fila : a) {
            for (double elem : fila) {
                System.out.printf("%10.6f ", elem);
            }
            System.out.println();
        }
    }

    // Método Gauss-Seidel
    public static void gaussSeidel(double[][] a, double[] x, int n, double eaMax) {
        double[] ea = new double[n];
        double s, xv;
        boolean terminar;

        // Inicialización
        for (int i = 0; i < n; i++) {
            x[i] = 0.0;
            ea[i] = 1.0;
        }

        while (true) {
            terminar = true;
            for (int i = 0; i < n; i++) {
                s = 0.0;
                xv = x[i];
                for (int j = 0; j < n; j++) {
                    if (i != j) {
                        s += a[i][j] * x[j];
                    }
                }
                x[i] = (a[i][n] - s) / a[i][i];

                if (x[i] != 0.0) {
                    ea[i] = Math.abs((xv - x[i]) / x[i]);
                }
                if (ea[i] >= eaMax) {
                    terminar = false;
                }
            }
            if (terminar) break;
        }
    }

    public static void main(String[] args) {
        int n = 3;
        double eaMax = 0.001;

        double[][] a = leerEcuaciones(n);
        imprimirMatriz(a);

        double[] x = new double[n];
        gaussSeidel(a, x, n, eaMax);

        System.out.println("\nSolución aproximada con Gauss-Seidel:");
        for (int i = 0; i < n; i++) {
            System.out.printf("x%d = %.6f\n", i+1, x[i]);
        }
    }
}
