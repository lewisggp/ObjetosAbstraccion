/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicasAbstraccion;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author Lewis
 */
public class PruebasMatrices {
    
    public static void main(String[] args) {

        Matriz m = new Matriz(new Fraccion[][]{{new Fraccion(2), new Fraccion(-1), new Fraccion(3), new Fraccion(3)}, {new Fraccion(2), new Fraccion(1), new Fraccion(4), new Fraccion(0)}, {new Fraccion(-1), new Fraccion(2), new Fraccion(3), new Fraccion(1)}, {new Fraccion(4), new Fraccion(0), new Fraccion(1), new Fraccion(-1)}});
        // mostrar("" + Matriz.determinanteJacobi(m));
        // mostrar("" + Matriz.determinanteJacobi(Matriz.inversaGauss(m)));

        int opc;
        Matriz matriz = m;
        do {
            opc = leerEntero("Menu" + "\n 1. Leer matriz" + "\n 2. Mostrar matriz" + "\n 3. Determinante. Metodo de Laplace" + "\n 4. Determinante. Teorema de Jacobi" + "\n 5. Transpuesta" + "\n 6. Producto" + "\n 7. Cofactor" + "\n 8. Complementario" + "\n 9. Inversa. Metodo de cofactores" + "\n 10.Inversa. Metodo de gauss" + "\n 11.Inversa. Metodo de gauss, sustitucion hacia atras"
                            + "\n 12.Triangulizar." + "\n 13.Triangulizar. Solo Abajo" + "\n 14.Triangulizar. Solo Arriba" + "\n 15.Salir");
            switch (opc) {
                case 1 :
                    int n = leerEntero("Ingrese la el orden de la Matriz NxN");
                    Fraccion[][] valoresMatriz = new Fraccion[n][n];
                    for (int c = 0; c < n; c++) {
                        for (int f = 0; f < n; f++) {
                            valoresMatriz[f][c] = leerFraccion("Ingrese el valor de la posicion [" + f + "][" + c + "]");
                        }
                    }
                    matriz = new Matriz(valoresMatriz);
                    break;

                case 2 :
                    if (matriz.filas == 0 && matriz.columnas == 0) {
                        mostrar("Matriz vacia");
                    } else {
                        mostrar("Matriz\n" + matriz);
                    }
                    break;

                case 3 :
                    if (matriz.filas == 0 && matriz.columnas == 0) {
                        mostrar("Matriz vacia");
                    } else {
                        // mostrar("Matriz" + matriz.toString() + "\n");
                        // mostrar("Determinante por el metodo de Laplace: " +
                        // Matriz.determinanteLaplace(matriz));
                        Matriz.determinanteLaplace(matriz);
                    }
                    break;

                case 4 :
                    if (matriz.filas == 0 && matriz.columnas == 0) {
                        mostrar("Matriz vacia");
                    } else {
                        // mostrar("Matriz" + matriz.toString() + "\n");
                        // mostrar("Determinante por el metodo de Jacobi: " +
                        // Matriz.determinanteJacobi(matriz));
                        Matriz.determinanteJacobi(matriz);
                    }
                    break;

                case 5 :
                    if (matriz.filas == 0 && matriz.columnas == 0) {
                        mostrar("Matriz vacia");
                    } else {
                        // mostrar("Matriz" + matriz.toString() + "\n");
                        // mostrar("Transpuesta\n" +
                        // Matriz.transpuesta(matriz));
                        Matriz.transpuesta(matriz);
                    }
                    break;

                case 6 :
                    if (matriz.filas == 0 && matriz.columnas == 0) {
                        mostrar("Matriz vacia");
                    } else {
                        // mostrar("Matriz" + matriz.toString() + "\n");
                        // mostrar("Producto MxM\n" + Matriz.producto(matriz,
                        // matriz));
                        Matriz.producto(matriz, matriz);
                    }
                    break;

                case 7 :
                    if (matriz.filas == 0 && matriz.columnas == 0) {
                        mostrar("Matriz vacia");
                    } else {
                        // mostrar("Matriz" + matriz.toString() + "\n");
                        // mostrar("Cofactores:\n" + Matriz.cofactor(matriz));
                        Matriz.cofactor(matriz);
                    }
                    break;

                case 8 :
                    if (matriz.filas == 0 && matriz.columnas == 0) {
                        mostrar("Matriz vacia");
                    } else {
                        mostrar("Matriz" + matriz.toString() + "\n");

                        int fila = leerEntero("Ingrese la fila");
                        int columna = leerEntero("Ingrese la columna");
                        if (fila > 0 && columna > 0 && fila <= matriz.filas && columna <= matriz.columnas)
                                Matriz.complementario(matriz, fila - 1, columna - 1);
                        // mostrar("Complementario. Fila" + fila + " " +
                        // "columna" + columna + "\n" +
                        // Matriz.complementario(matriz, fila - 1, columna -
                        // 1));
                        else
                            mostrar("No ingresaste una posicion valida. " + matriz.filas + " filas y " + matriz.columnas + " columnas");

                    }
                    break;

                case 9 :
                    if (matriz.filas == 0 && matriz.columnas == 0) {
                        mostrar("Matriz vacia");
                    } else {
                        // mostrar("Matriz" + matriz.toString() + "\n");
                        // mostrar("Inversa. Metodo de cofactores\n" +
                        // Matriz.inversaCofactores(matriz));
                        Matriz.inversaCofactores(matriz);
                    }
                    break;

                case 10 :
                    if (matriz.filas == 0 && matriz.columnas == 0) {
                        mostrar("Matriz vacia");
                    } else {
                        // mostrar("Matriz" + matriz.toString() + "\n");
                        // mostrar("Inversa. Metodo de Gauss\n" +
                        // Matriz.inversaGauss(matriz));
                        Matriz.inversaGauss(matriz);
                    }
                    break;

                case 11 :
                    if (matriz.filas == 0 && matriz.columnas == 0) {
                        mostrar("Matriz vacia");
                    } else {
                        // mostrar("Matriz" + matriz.toString() + "\n");
                        // mostrar("Inversa. Metodo de Gauss, sustitucion hacia
                        // atras\n" + Matriz.inversaGaussSustitucion(matriz));
                        Matriz.inversaGaussSustitucion(matriz);
                    }
                    break;

                case 12 :
                    if (matriz.filas == 0 && matriz.columnas == 0) {
                        mostrar("Matriz vacia");
                    } else {
                        // mostrar("Matriz" + matriz.toString() + "\n");
                        // mostrar("Triangulizada.\n" +
                        // Matriz.triangulizar(matriz));
                        Matriz.triangulizar(matriz);
                    }
                    break;

                case 13 :
                    if (matriz.filas == 0 && matriz.columnas == 0) {
                        mostrar("Matriz vacia");
                    } else {
                        // mostrar("Matriz" + matriz.toString() + "\n");
                        // mostrar("Triangulizada Abajo.\n" +
                        // Matriz.triangulizarAbajo(matriz));
                        Matriz.triangulizarAbajo(matriz);
                    }
                    break;

                case 14 :
                    if (matriz.filas == 0 && matriz.columnas == 0) {
                        mostrar("Matriz vacia");
                    } else {
                        // mostrar("Matriz" + matriz.toString() + "\n");
                        // mostrar("Triangulizada Arriba.\n" +
                        // Matriz.triangulizarArriba(matriz));
                        Matriz.triangulizarArriba(matriz);
                    }
                    break;

                case 15 :
                    mostrar("Hasta luego");
                    break;

                default :
                    mostrar("Opcion invalida");
            }
        } while (opc != 15);
    }

    private static Fraccion leerFraccion(String m) {
        Fraccion x = new Fraccion();
        boolean ban;
        do {
            ban = false;
            try {
                String cadena = cadena(m);
                if (cadena.contains("/")) {
                    String[] cadenaDivision = cadena.split("/");
                    x.setNumerador(Integer.parseInt(cadenaDivision[0]));
                    x.setDenominador(Integer.parseInt(cadenaDivision[1]));
                } else if (cadena.contains(".")) {
                    x = new Fraccion(Double.parseDouble(cadena));
                } else {
                    x.setNumerador(Integer.parseInt(cadena));
                }
            } catch (Exception ex) {
                ban = true;
                mostrar("Error! Intente de nuevo.");
            }
        } while (ban);
        return x;
    }

    public static String cadena(String msj) {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String x = "";
        try {
            mostrar(msj);
            x = br.readLine();
        } catch (Exception ex) {
            mostrar("Error " + ex.toString());
        }
        return x;
    }

    public static int leerEntero(String msj) {
        int x = 0;
        boolean ban;
        do {
            ban = false;
            try {
                x = Integer.parseInt(cadena(msj));
            } catch (Exception ex) {
                ban = true;
                mostrar("Error! Intente de nuevo");
            }
        } while (ban);
        return x;
    }

    public static void mostrar(String msj) {
        System.out.println(msj);
    }

}
