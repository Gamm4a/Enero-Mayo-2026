def imprimirMatriz(a):
    print("\nMatriz ampliada:")
    for fila in a:
        for elem in fila:
            print(f"{elem:10.6f}", end=" ")
        print()

def gaussSeidel(a, x, n, eaMax):
    ea = [1.0 for _ in range(n)]

    
    for i in range(n):
        x[i] = 0.0

    while True:
        terminar = True
        for i in range(n):
            s = 0.0
            xv = x[i]
            for j in range(n):
                if i != j:
                    s += a[i][j] * x[j]
            x[i] = (a[i][n] - s) / a[i][i]

            if x[i] != 0.0:
                ea[i] = abs((xv - x[i]) / x[i])
            if ea[i] >= eaMax:
                terminar = False
        if terminar:
            break

def main():
    n = 3
    eaMax = 0.001

    a = [
        [-8, 1, -2, -20],
        [2, -6, -1, -38],
        [-3, -1, 7, -34]
    ]

    imprimirMatriz(a)

    x = [0.0 for _ in range(n)]
    gaussSeidel(a, x, n, eaMax)

    print("\nSolución aproximada con Gauss-Seidel:")
    for i in range(n):
        print(f"x{i+1} = {x[i]:.6f}")


main()
