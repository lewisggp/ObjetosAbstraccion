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
public class Recursividad {

    public static float pow(int x, int n) {
        if (n == 0) {
            return 1;
        } else if (n > 0) {
            return x * pow(x, n - 1);
        } else if (x != 0) {
            return 1 / (x * pow(x, -n - 1));
        }
        return 0;
    }

    public static int fib(int n) {
        if (n <= 1) {
            return n;
        } else {
            return fib(n - 1) + fib(n - 2);
        }
    }

    public static int fact(int n) {
        if (n == 1) {
            return 1;
        } else if (n < 1) {
            return n;
        } else {
            return n * fact(n - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println("POW " + pow(2, -6));
        System.out.println("FIB " + fib(11)); // 0 1 1 2 3 5 8 13 21 34 55 89
        System.out.println("FACT " + fact(4));
    }
}
