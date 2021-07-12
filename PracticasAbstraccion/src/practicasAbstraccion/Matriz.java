/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicasAbstraccion;

/**
 *
 * @author Lewis
 */
public class Matriz {

    int filas;
    int columnas;
    Fraccion[][] matriz;

    public Matriz(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.matriz = new Fraccion[filas][columnas];
    }

    public Matriz(Fraccion[][] matriz) {
        this.matriz = matriz;
        this.filas = matriz.length;
        this.columnas = matriz[0].length;
    }

    public static Fraccion determinanteLaplace(Matriz matriz) {
        if (matriz.filas == matriz.columnas) {
            if (matriz.filas == 1) {
                System.out.println("Determinante = " + matriz.matriz[0][0] + "\n");
                return matriz.matriz[0][0];
            } else {
                Fraccion determinante = new Fraccion();
                for (int c = 0; c < matriz.columnas; c++) {
                    if (c % 2 == 0) {
                        System.out.print("+(" + matriz.matriz[0][c] + ")");
                        determinante.sumar(Fraccion.multiplicar(matriz.matriz[0][c], Matriz.determinanteLaplace(Matriz.complementario(matriz, 0, c))));
                    } else {
                        System.out.print("-(" + matriz.matriz[0][c] + ")");
                        determinante.restar(Fraccion.multiplicar(matriz.matriz[0][c], Matriz.determinanteLaplace(Matriz.complementario(matriz, 0, c))));
                    }
                }
                System.out.println("Determinante = " + determinante + "\n");
                return determinante;
            }
        } else {
            return new Fraccion();
        }
    }

    public static Fraccion determinanteJacobi(Matriz matriz) {
        if (matriz.filas == 1) {
            System.out.println("Determinante 1x1 = " + matriz.matriz[0][0] + "\n");
            return matriz.matriz[0][0];
        } else {
            Matriz pivoteada = Matriz.clonar(matriz);
            for (int f = 1; f < matriz.filas; f++) {
                Fraccion producto = Fraccion.dividir(pivoteada.matriz[f][0], pivoteada.matriz[0][0]);
                for (int c = 0; c < pivoteada.columnas; c++) {
                    pivoteada.matriz[f][c].restar(Fraccion.multiplicar(producto, pivoteada.matriz[0][c]));
                }
                if (f == matriz.filas - 1) {
                    System.out.print("*");
                }
                System.out.println(pivoteada + "\n");
            }
            Fraccion determinante = Fraccion.multiplicar(matriz.matriz[0][0], Matriz.determinanteJacobi(Matriz.complementario(pivoteada, 0, 0)));
            System.out.println("Determinante " + matriz.filas + "x" + matriz.columnas + " = " + determinante + "\n");
            return determinante;
        }
    }

    public static Matriz inversaGauss(Matriz matriz) {
        Matriz triangulada = Matriz.clonar(matriz);
        Matriz inversa = Matriz.identidad(triangulada.filas);
        System.out.println(Matriz.toString(triangulada, inversa) + "\n");
        for (int filaPivote = 0, columnaPivote = 0; filaPivote < triangulada.filas; filaPivote++, columnaPivote++) {
            for (int fila = filaPivote + 1; fila < triangulada.filas; fila++) {
                Fraccion producto = Fraccion.dividir(triangulada.matriz[fila][columnaPivote], triangulada.matriz[filaPivote][columnaPivote]);
                for (int columna = 0; columna < triangulada.columnas; columna++) {
                    triangulada.matriz[fila][columna].restar(Fraccion.multiplicar(producto, triangulada.matriz[filaPivote][columna]));
                    inversa.matriz[fila][columna].restar(Fraccion.multiplicar(producto, inversa.matriz[filaPivote][columna]));
                }
                System.out.println(Matriz.toString(triangulada, inversa) + "\n");
            }
        }

        for (int filaPivote = triangulada.filas - 1, columnaPivote = triangulada.columnas - 1; filaPivote >= 0; filaPivote--, columnaPivote--) {
            for (int fila = filaPivote - 1; fila >= 0; fila--) {
                Fraccion producto = Fraccion.dividir(triangulada.matriz[fila][columnaPivote], triangulada.matriz[filaPivote][columnaPivote]);
                for (int columna = 0; columna < triangulada.columnas; columna++) {
                    triangulada.matriz[fila][columna].restar(Fraccion.multiplicar(producto, triangulada.matriz[filaPivote][columna]));
                    inversa.matriz[fila][columna].restar(Fraccion.multiplicar(producto, inversa.matriz[filaPivote][columna]));
                }
                System.out.println(Matriz.toString(triangulada, inversa) + "\n");
            }
        }

        for (int filaTriangulada = triangulada.filas - 1, columnaTriangulada = triangulada.columnas - 1; filaTriangulada >= 0; filaTriangulada--, columnaTriangulada--) {
            Fraccion pivote = Fraccion.clonar(triangulada.matriz[filaTriangulada][columnaTriangulada]);
            triangulada.matriz[filaTriangulada][columnaTriangulada].dividir(pivote);
            for (int columnaInversa = 0; columnaInversa < inversa.columnas; columnaInversa++) {
                inversa.matriz[filaTriangulada][columnaInversa].dividir(pivote);
            }
            System.out.println(Matriz.toString(triangulada, inversa) + "\n");
        }
        return inversa;
    }

    public static Matriz inversaGaussSustitucion(Matriz matriz) {
        Matriz triangulada = Matriz.clonar(matriz);
        Matriz inversa = Matriz.identidad(triangulada.filas);
        for (int filaPivote = 0, columnaPivote = 0; filaPivote < triangulada.filas; filaPivote++, columnaPivote++) {
            for (int fila = filaPivote + 1; fila < triangulada.filas; fila++) {
                Fraccion producto = Fraccion.dividir(triangulada.matriz[fila][columnaPivote], triangulada.matriz[filaPivote][columnaPivote]);
                for (int columna = 0; columna < triangulada.columnas; columna++) {
                    triangulada.matriz[fila][columna].restar(Fraccion.multiplicar(producto, triangulada.matriz[filaPivote][columna]));
                    inversa.matriz[fila][columna].restar(Fraccion.multiplicar(producto, inversa.matriz[filaPivote][columna]));
                }
                System.out.println(Matriz.toString(triangulada, inversa) + "\n");
            }
        }

        for (int filaTriangulada = triangulada.filas - 1, columnaTriangulada = triangulada.columnas - 1; filaTriangulada >= 0; filaTriangulada--, columnaTriangulada--) {
            for (int columnaInversa = 0; columnaInversa < inversa.columnas; columnaInversa++) {
                Fraccion acumulado = new Fraccion();
                for (int columna = triangulada.columnas - 1, fila = inversa.filas - 1; columna > columnaTriangulada; columna--, fila--) {
                    acumulado.sumar(Fraccion.multiplicar(triangulada.matriz[filaTriangulada][columna], inversa.matriz[fila][columnaInversa]));
                }
                inversa.matriz[filaTriangulada][columnaInversa] = Fraccion.dividir(Fraccion.restar(inversa.matriz[filaTriangulada][columnaInversa], acumulado), triangulada.matriz[filaTriangulada][columnaTriangulada]);
                System.out.println(Matriz.toString(triangulada, inversa) + "\n");
            }
        }
        return inversa;
    }

    public static Matriz sustitucionAtras(Matriz matriz, Matriz igual) {
        Matriz sustituida = Matriz.clonar(igual);
        // Ya debe estar triangulizada Matrix.triangulizarAbajo(matrix);
        for (int f = matriz.filas - 1, c = matriz.columnas - 1; f >= 0; f--, c--) {
            Fraccion acumulado = new Fraccion();
            System.out.print(sustituida.matriz[f][0] + "X" + (f + 1) + " = ");
            for (int columna = matriz.columnas - 1, fila = igual.filas - 1; columna > c; columna--, fila--) {
                acumulado.sumar(Fraccion.multiplicar(matriz.matriz[f][columna], sustituida.matriz[fila][0]));
                System.out.print(matriz.matriz[f][columna] + " * " + sustituida.matriz[fila][0]);
                if (columna > c + 1) {
                    System.out.print(" + ");
                }
            }
            sustituida.matriz[f][0] = Fraccion.dividir(Fraccion.restar(igual.matriz[f][0], acumulado), matriz.matriz[f][c]);
            System.out.println("X" + (f + 1) + " = " + sustituida.matriz[f][0]);
        }
        return sustituida;
    }

    public static Matriz triangulizar(Matriz matriz) {
        Matriz triangulada = Matriz.triangulizarAbajo(matriz);
        triangulada = Matriz.triangulizarArriba(triangulada);
        return triangulada;
    }

    public static Matriz triangulizarAbajo(Matriz matriz) {
        Matriz triangulada = Matriz.clonar(matriz);
        System.out.println(triangulada + "\n");
        for (int filaPivote = 0, columnaPivote = 0; filaPivote < triangulada.filas; filaPivote++, columnaPivote++) {
            for (int fila = filaPivote + 1; fila < triangulada.filas; fila++) {
                Fraccion producto = Fraccion.dividir(matriz.matriz[fila][columnaPivote], triangulada.matriz[filaPivote][columnaPivote]);
                for (int columna = 0; columna < triangulada.columnas; columna++) {
                    triangulada.matriz[fila][columna].restar(Fraccion.multiplicar(producto, triangulada.matriz[filaPivote][columna]));
                }
                System.out.println(triangulada + "\n");
            }
        }
        return triangulada;
    }

    public static Matriz triangulizarArriba(Matriz matrix) {
        Matriz triangulada = Matriz.clonar(matrix);
        System.out.println(triangulada + "\n");
        for (int filaPivote = triangulada.filas - 1, columnaPivote = triangulada.columnas - 1; filaPivote >= 0; filaPivote--, columnaPivote--) {
            for (int fila = filaPivote - 1; fila >= 0; fila--) {
                Fraccion producto = Fraccion.dividir(triangulada.matriz[fila][columnaPivote], triangulada.matriz[filaPivote][columnaPivote]);
                for (int columna = 0; columna < triangulada.columnas; columna++) {
                    triangulada.matriz[fila][columna].restar(Fraccion.multiplicar(producto, triangulada.matriz[filaPivote][columna]));
                }
                System.out.println(triangulada + "\n");
            }
        }
        return triangulada;
    }

    public static Matriz clonar(Matriz matriz) {
        Fraccion[][] m = new Fraccion[matriz.filas][matriz.columnas];
        for (int f = 0; f < matriz.filas; f++) {
            for (int c = 0; c < matriz.columnas; c++) {
                m[f][c] = Fraccion.clonar(matriz.matriz[f][c]);
            }
        }
        return new Matriz(m);

    }

    /*
	 * public static Matriz pivotear(Matriz matriz, int filaPivote, int
	 * columnaPivote, int fila) { Matriz pivoteada = Matriz.clonar(matriz);
	 * double producto = pivoteada.matriz[fila][columnaPivote] /
	 * pivoteada.matriz[filaPivote][columnaPivote]; for (int c = 0; c <
	 * pivoteada.columnas; c++) { pivoteada.matriz[fila][c] -= producto *
	 * pivoteada.matriz[filaPivote][c]; } return pivoteada; }
    */
    
    public static Matriz inversaCofactores(Matriz matriz) {
        System.out.println("Matriz\n" + matriz + "\n");
        Matriz inversa = new Matriz(matriz.filas, matriz.columnas);
        Fraccion determinante = Matriz.determinanteLaplace(matriz);
        if (determinante.resolver() == 0) {
            return null;
        }

        System.out.println("Hallar los cofactores");
        Matriz cofactor = Matriz.cofactor(matriz);

        System.out.println("Hallar la transpuesta");
        Matriz transpuestaCofactor = Matriz.transpuesta(cofactor);

        inversa = Matriz.escalar(Fraccion.dividir(new Fraccion(1), Fraccion.absoluto(determinante)), transpuestaCofactor);
        System.out.println("Inversa por cofactores\n" + inversa);
        return inversa;
    }

    public static Matriz cofactor(Matriz matriz) {
        System.out.println("Original\n" + matriz + "\n");
        Matriz cofactor = new Matriz(matriz.filas, matriz.columnas);
        for (int f = 0; f < cofactor.filas; f++) {
            for (int c = 0; c < cofactor.columnas; c++) {
                cofactor.matriz[f][c] = Fraccion.multiplicar(new Fraccion((int) Math.pow(-1, f + c + 2)), Matriz.determinanteLaplace(Matriz.complementario(matriz, f, c)));
                System.out.println("Cofactor [" + (f + 1) + "][" + (c + 1) + "] = " + cofactor.matriz[f][c] + "\n");
            }
        }
        System.out.println("Cofactores:\n" + cofactor);
        return cofactor;
    }

    public static Matriz complementario(Matriz matriz, int fila, int columna) {
        Matriz subMatrix = new Matriz(matriz.filas - 1, matriz.columnas - 1);
        for (int fSubMatrix = 0, fMatrix = 0; fSubMatrix < subMatrix.filas; fSubMatrix++, fMatrix++) {
            if (fMatrix == fila) {
                fMatrix++;
            }
            for (int cSubMatrix = 0, cMatrix = 0; cSubMatrix < subMatrix.columnas; cSubMatrix++, cMatrix++) {
                if (cMatrix == columna) {
                    cMatrix++;
                }
                subMatrix.matriz[fSubMatrix][cSubMatrix] = matriz.matriz[fMatrix][cMatrix];
            }
        }
        System.out.println("Complementario [" + (fila + 1) + "][" + (columna + 1) + "]");
        System.out.println(subMatrix + "\n");
        return subMatrix;
    }

    public static Matriz producto(Matriz matrizA, Matriz matrizB) {
        System.out.println("Original\n" + Matriz.toString(matrizA, matrizB) + "\n");
        Matriz producto = new Matriz(matrizA.filas, matrizA.columnas);
        for (int fMatrixA = 0; fMatrixA < matrizA.filas; fMatrixA++) {
            for (int cMatrixB = 0; cMatrixB < matrizA.columnas; cMatrixB++) {
                producto.matriz[fMatrixA][cMatrixB] = new Fraccion();
                System.out.print("[" + (fMatrixA + 1) + "][" + (cMatrixB + 1) + "] = ");
                for (int fMatrixB = 0; fMatrixB < matrizA.filas; fMatrixB++) {
                    producto.matriz[fMatrixA][cMatrixB].sumar(Fraccion.multiplicar(matrizA.matriz[fMatrixA][fMatrixB], matrizB.matriz[fMatrixB][cMatrixB]));
                    System.out.print(matrizA.matriz[fMatrixA][fMatrixB] + "*" + matrizB.matriz[fMatrixB][cMatrixB]);
                    if (fMatrixB < matrizA.filas - 1) {
                        System.out.print(" + ");
                    }
                }
                System.out.println();
            }
        }
        System.out.println("\nProducto\n" + producto + "\n");
        return producto;
    }

    public static Matriz transpuesta(Matriz matriz) {
        System.out.println("Original\n" + matriz + "\n");
        Matriz transpuesta = new Matriz(matriz.filas, matriz.columnas);
        for (int f = 0; f < transpuesta.filas; f++) {
            for (int c = 0; c < transpuesta.columnas; c++) {
                transpuesta.matriz[f][c] = matriz.matriz[c][f];
            }
        }
        System.out.println("Transpuesta\n" + transpuesta + "\n");
        return transpuesta;
    }

    public static Matriz escalar(Fraccion escalar, Matriz matriz) {
        System.out.println("Original\n" + matriz);
        Matriz escalada = new Matriz(matriz.filas, matriz.columnas);
        for (int f = 0; f < matriz.filas; f++) {
            for (int c = 0; c < matriz.columnas; c++) {
                escalada.matriz[f][c] = Fraccion.multiplicar(matriz.matriz[f][c], escalar);
            }
        }
        System.out.println("Escalada " + escalar + "\n" + escalada);
        return escalada;
    }

    public static Matriz sumar(Matriz matrizA, Matriz matrizB) {
        Matriz suma = new Matriz(matrizA.filas, matrizA.columnas);
        for (int f = 0; f < matrizA.filas; f++) {
            for (int c = 0; c < matrizA.columnas; c++) {
                suma.matriz[f][c] = Fraccion.sumar(matrizA.matriz[f][c], matrizB.matriz[f][c]);
            }
        }
        return suma;
    }

    public static Matriz identidad(int lado) {
        Matriz identidad = new Matriz(lado, lado);
        for (int f = 0; f < lado; f++) {
            for (int c = 0; c < lado; c++) {
                if (f == c) {
                    identidad.matriz[f][c] = new Fraccion(1);
                } else {
                    identidad.matriz[f][c] = new Fraccion();
                }
            }
        }
        return identidad;
    }

    public static String toString(Matriz matrizA, Matriz matrizB) {
        String msj = "";
        for (int f = 0; f < matrizA.filas; f++) {
            for (int c = 0; c < matrizA.columnas; c++) {
                msj += "\t" + matrizA.matriz[f][c];
            }
            msj += "\t¦";
            for (int c = 0; c < matrizA.columnas; c++) {
                msj += "\t" + matrizB.matriz[f][c];
            }
            if (f < matrizA.filas - 1) {
                msj += "\n";
            }
        }
        return msj;
    }

    public String toString() {
        String msj = "";
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                msj += "\t" + matriz[f][c];
                // msj += "\t" + (double) Math.round(matriz[f][c] * 100) / 100;
            }
            if (f < filas - 1) {
                msj += "\n";
            }
        }
        return msj;
    }

}
