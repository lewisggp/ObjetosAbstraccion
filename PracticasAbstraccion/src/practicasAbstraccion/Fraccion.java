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
public class Fraccion {

    private int numerador, denominador;

    public Fraccion() {
        numerador = 0;
        denominador = 1;
    }

    public Fraccion(int numerador) {
        this.numerador = numerador;
        this.denominador = 1;
    }

    public Fraccion(int numerador, int denominador) {
        this.numerador = numerador;
        this.denominador = denominador;
        simplificar();
    }

    public Fraccion(double fraccion) {
        String str = String.valueOf(fraccion);
        Boolean negativo;

        if (str.substring(0, 1).equals("-")) {
            negativo = true;
        } else {
            negativo = false;
        }
        String entero = str.substring(0, str.indexOf('.'));
        String decimal = str.substring(str.indexOf('.') + 1);
        int parteEntera = Integer.parseInt(entero);
        int parteDecimal = Integer.parseInt(decimal);
        denominador = (int) Math.pow(10, decimal.length());

        if (negativo) {
            numerador = parteEntera * denominador - parteDecimal;
        } else {
            numerador = parteEntera * denominador + parteDecimal;
        }
        simplificar();
    }

    // Getters y Setters
    public void setNumerador(int n) {
        numerador = n;
    }

    public void setDenominador(int d) {
        denominador = d;
    }

    public int getNumerador() {
        return numerador;
    }

    public int setDenominador() {
        return denominador;
    }

    // Establecer como suma de A y B
    public void suma(Fraccion a, Fraccion b) {
        numerador = a.numerador * b.denominador + a.denominador * b.numerador;
        denominador = a.denominador * b.denominador;
        simplificar();
    }

    // Establecer como resta de A y B
    public void resta(Fraccion a, Fraccion b) {
        numerador = a.numerador * b.denominador - a.denominador * b.numerador;
        denominador = a.denominador * b.denominador;
        simplificar();
    }

    // Establecer como multiplicacion de A y B
    public void multiplicacion(Fraccion a, Fraccion b) {
        numerador = a.numerador * b.numerador;
        denominador = a.denominador * b.denominador;
        simplificar();
    }

    // Establecer como division de A y B
    public void division(Fraccion a, Fraccion b) {
        numerador = a.numerador * b.denominador;
        denominador = a.denominador * b.numerador;
        simplificar();
    }

    // Simplificar fraccion
    public void simplificar() {
        if (numerador == denominador) {
            numerador = 1;
            denominador = 1;
        } else {
            int a = numerador < 0 ? -numerador : numerador;
            int b = denominador < 0 ? -denominador : denominador;

            int temp;
            while (b != 0) {
                temp = a % b;
                a = b;
                b = temp;
            }

            if (numerador < 0 && denominador < 0 || denominador < 0) {
                numerador = -numerador / a;
                denominador = -denominador / a;
            } else {
                numerador = numerador / a;
                denominador = denominador / a;
            }
        }
    }

    // Resolver fraccion
    public double resolver() {
        return (double) numerador / denominador;
    }

    // Valor absoluto
    public void absoluto() {
        if (numerador < 0) {
            numerador *= -1;
        }
        if (denominador < 0) {
            denominador *= -1;
        }
    }

    // Sumarle una fraccion
    public void sumar(Fraccion x) {
        numerador = numerador * x.denominador + denominador * x.numerador;
        denominador = denominador * x.denominador;
        simplificar();
    }

    // Restarle una fraccion
    public void restar(Fraccion x) {
        numerador = numerador * x.denominador - denominador * x.numerador;
        denominador = denominador * x.denominador;
        simplificar();
    }

    // Multiplicarle una fraccion
    public void multiplicar(Fraccion x) {
        numerador *= x.numerador;
        denominador *= x.denominador;
        simplificar();
    }

    // Dividirle una fraccion
    public void dividir(Fraccion x) {
        numerador *= x.denominador;
        denominador *= x.numerador;
        simplificar();
    }

    // Devolver nueva fraccion suma
    public static Fraccion sumar(Fraccion a, Fraccion b) {
        Fraccion x = new Fraccion();
        x.numerador = a.numerador * b.denominador + a.denominador * b.numerador;
        x.denominador = a.denominador * b.denominador;
        x.simplificar();
        return x;
    }

    // Devolver nueva fraccion resta
    public static Fraccion restar(Fraccion a, Fraccion b) {
        Fraccion x = new Fraccion();
        x.numerador = a.numerador * b.denominador - a.denominador * b.numerador;
        x.denominador = a.denominador * b.denominador;
        x.simplificar();
        return x;
    }

    // Devolver nueva fraccion multiplicada
    public static Fraccion multiplicar(Fraccion a, Fraccion b) {
        Fraccion x = new Fraccion();
        x.numerador = a.numerador * b.numerador;
        x.denominador = a.denominador * b.denominador;
        x.simplificar();
        return x;
    }

    // Devolver nueva fraccion dividida
    public static Fraccion dividir(Fraccion a, Fraccion b) {
        Fraccion x = new Fraccion();
        x.numerador = a.numerador * b.denominador;
        x.denominador = a.denominador * b.numerador;
        x.simplificar();
        return x;
    }

    // Devolver nueva fraccion simplificada
    public static Fraccion simplificar(Fraccion f) {
        Fraccion x = new Fraccion();
        int a = f.numerador < 0 ? -f.numerador : f.numerador;
        int b = f.denominador < 0 ? -f.denominador : f.denominador;

        int temp;
        while (b != 0) {
            temp = a % b;
            a = b;
            b = temp;
        }

        x.numerador = ((f.numerador < 0 && f.denominador < 0) ? -f.numerador : f.numerador) / a;
        x.denominador = ((f.numerador < 0 && f.denominador < 0) ? -f.denominador : f.denominador) / a;

        return x;
    }

    // Devolver valor absoluto
    public static Fraccion absoluto(Fraccion f) {
        Fraccion x = new Fraccion(f.numerador, f.denominador);
        if (x.numerador < 0) {
            x.numerador *= -1;
        }
        if (x.denominador < 0) {
            x.denominador *= -1;
        }
        return x;
    }

    // Resolver una fraccion dada
    public static double resolver(Fraccion x) {
        return (double) x.numerador / x.denominador;
    }

    // Clonar fraccion
    public static Fraccion clonar(Fraccion fraccion) {
        return new Fraccion(fraccion.numerador, fraccion.denominador);
    }

    public String toString() {
        if (denominador == 1) {
            return "" + numerador;
        }
        if (numerador == 0) {
            return "0";
        }
        return numerador + "/" + denominador;
    }

}
