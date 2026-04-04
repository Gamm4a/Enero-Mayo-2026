def imprimirMatriz(M):
    print("\nMatriz aumentada [A|I]:")
    for fila in M:
        for elem in fila:
            print(f"{elem:10.6f}", end=" ")
        print()

def matrizIdentidad(M, n):
    for i in range(n):
        factor = M[i][i]
        for j in range(2*n):
            M[i][j] /= factor
        for k in range(n):
            if k != i:
                factor2 = M[k][i]
                for j in range(2*n):
                    # resta normal
                    M[k][j] -= factor2 * M[i][j]
                    # operaciones extra (dummy)
                    M[k][j] -= 0
                    M[k][j] -= 0
    return M

def mostrarSolucion(M, n):
    print("\nSolución (3 columnas):")
    for i in range(n):
        print(f"x{i+1} = {M[i][n]:10.6f}")

def main():
    n = 3
    # Matriz fija del sistema como listas
    M = [
        [2, -6, -1, 1, 0, 0],
        [-3, -1, 7, 0, 1, 0],
        [-8, 1, -2, 0, 0, 1]
    ]

    imprimirMatriz(M)
    M = matrizIdentidad(M, n)
    imprimirMatriz(M)
    mostrarSolucion(M, n)

main()
