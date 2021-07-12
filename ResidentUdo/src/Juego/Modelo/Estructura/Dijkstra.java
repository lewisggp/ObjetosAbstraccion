/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego.Modelo.Estructura;

import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author Lewis
 */
public final class Dijkstra {

    private NodoDijkstra[][] nodos;
    private Rectangle area;

    private final int CANTIDAD;
    private final int CENTRO;

    public Dijkstra(Rectangle inicio, ListaSimple solidos) {
        CANTIDAD = 21; // IMPAR
        CENTRO = (CANTIDAD - 1) / 2;
        area = new Rectangle(inicio.x - CENTRO * inicio.width, inicio.y - CENTRO * inicio.height, inicio.width * CANTIDAD, inicio.height * CANTIDAD);

        nodos = new NodoDijkstra[CANTIDAD][CANTIDAD];
        for (int i = 0; i < nodos.length; i++) {
            for (int j = 0; j < nodos[i].length; j++) {
                nodos[i][j] = new NodoDijkstra(new Rectangle(inicio.x + (i - CENTRO) * inicio.width, inicio.y + (j - CENTRO) * inicio.height, inicio.width, inicio.height));
            }
        }

        for (int i = 0; i < nodos.length; i++) {
            for (int j = 0; j < nodos[i].length; j++) {
                if (i != 0 && j != 0 && i != CANTIDAD - 1 && j != CANTIDAD - 1) {
                    nodos[i][j].setVecinos(new NodoDijkstra[]{nodos[i][j - 1], nodos[i + 1][j], nodos[i][j + 1], nodos[i - 1][j]});
                } else if (i != 0 && j == 0 && i != CANTIDAD - 1) {
                    nodos[i][j].setVecinos(new NodoDijkstra[]{nodos[i - 1][j], nodos[i][j + 1], nodos[i + 1][j]});
                } else if (i == CANTIDAD - 1 && j != 0 && j != CANTIDAD - 1) {
                    nodos[i][j].setVecinos(new NodoDijkstra[]{nodos[i][j - 1], nodos[i - 1][j], nodos[i][j + 1]});
                } else if (i != 0 && j == CANTIDAD - 1 && i != CANTIDAD - 1) {
                    nodos[i][j].setVecinos(new NodoDijkstra[]{nodos[i - 1][j], nodos[i][j - 1], nodos[i + 1][j]});
                } else if (i == 0 && j != 0 && j != CANTIDAD - 1) {
                    nodos[i][j].setVecinos(new NodoDijkstra[]{nodos[i][j - 1], nodos[i + 1][j], nodos[i][j + 1]});
                } else if (i == 0 && j == 0) {
                    nodos[i][j].setVecinos(new NodoDijkstra[]{nodos[i + 1][j], nodos[i][j + 1]});
                } else if (i == CANTIDAD - 1 && j == 0) {
                    nodos[i][j].setVecinos(new NodoDijkstra[]{nodos[i - 1][j], nodos[i][j + 1]});
                } else if (i == CANTIDAD - 1 && j == CANTIDAD - 1) {
                    nodos[i][j].setVecinos(new NodoDijkstra[]{nodos[i - 1][j], nodos[i][j - 1]});
                } else if (i == 0 && j == CANTIDAD - 1) {
                    nodos[i][j].setVecinos(new NodoDijkstra[]{nodos[i + 1][j], nodos[i][j - 1]});
                }
            }
        }
        actualizarSolidos(solidos);
    }

    // --------------------------------------------------- GETTERS
    
    public NodoDijkstra[][] getNodos() {
        return nodos;
    }

    public Rectangle getArea() {
        return area;
    }

    // --------------------------------------------------- SETTERS
    
    public void setNodos(NodoDijkstra[][] nodos) {
        this.nodos = nodos;
    }
    
    public void setArea(Rectangle area) {
        this.area = area;
    }

    // --------------------------------------------------- ACTUALIZAR
    
    public void actualizar(Rectangle inicio, ListaSimple solidos) {
        area = new Rectangle(inicio.x - CENTRO * inicio.width, inicio.y - CENTRO * inicio.height, inicio.width * CANTIDAD, inicio.height * CANTIDAD);

        nodos[CENTRO][CENTRO].setArea(inicio);
        for (int i = 0; i < nodos.length; i++) {
            for (int j = 0; j < nodos[i].length; j++) {
                nodos[i][j].setArea(new Rectangle(inicio.x + (i - CENTRO) * inicio.width, inicio.y + (j - CENTRO) * inicio.height, inicio.width, inicio.height));
            }
        }

        actualizarSolidos(solidos);
        nodos[CENTRO][CENTRO].setValor(0);
        nodos[CENTRO][CENTRO].setVisitado(true);
        actualizarValores();
    }

    public void actualizarSolidos(ListaSimple solidos) {
        for (int i = 0; i < nodos.length; i++) {
            for (int j = 0; j < nodos[i].length; j++) {
                nodos[i][j].reiniciar();
                NodoListaSimple solido = solidos.getFirstNodo();
                while(solido != null) {
                    Rectangle rectangulo = (Rectangle) solido.getObject();
                    if (!nodos[i][j].isSolido() && nodos[i][j].getArea().intersects(rectangulo)) {
                        nodos[i][j].setSolido(true);
                    }
                    solido = solido.getNext();
                }
            }
        }
    }

    public void actualizarValores() {
        NodoDijkstra[] aEvaluar = new NodoDijkstra[]{nodos[CENTRO][CENTRO]};
        NodoDijkstra[] nuevosEvaluar = new NodoDijkstra[0];
        boolean ban;
        int valor = 1;
        do {
            ban = false;
            for (int i = 0; i < aEvaluar.length; i++) {
                NodoDijkstra[] vecinos = aEvaluar[i].getVecinos();
                for (int j = 0; j < vecinos.length; j++) {
                    if (!vecinos[j].isSolido() && !vecinos[j].isVisitado()) {
                        vecinos[j].setValor(valor);
                        vecinos[j].setVisitado(true);
                        ban = true;

                        NodoDijkstra[] nuevosTemp = new NodoDijkstra[nuevosEvaluar.length + 1];
                        for (int k = 0; k < nuevosEvaluar.length; k++) {
                            nuevosTemp[k] = nuevosEvaluar[k];
                        }
                        nuevosTemp[nuevosEvaluar.length] = vecinos[j];
                        nuevosEvaluar = nuevosTemp;
                    }
                }
            }
            aEvaluar = nuevosEvaluar;
            nuevosEvaluar = new NodoDijkstra[0];
            valor++;
        } while (ban);
    }

    // --------------------------------------------------- SIGUIENTE MOVIMIENTO
    
    public Point obtenerSiguienteMovimiento(Rectangle area, boolean alVecino) {
        //g.setColor(Color.PINK);
        //g.drawRect(referenciaX + this.area.x, referenciaY + this.area.y, this.area.width, this.area.height);
        if (area.intersects(this.area)) {
            int menor = Integer.MAX_VALUE;
            int x = -1, y = -1, vecinoFinal = -1;

            for (int i = 0; i < nodos.length; i++) {
                for (int j = 0; j < nodos[i].length; j++) {
                    if (!nodos[i][j].isSolido() && nodos[i][j].getArea().intersects(area)) {
                        x = i;
                        y = j;
                    }
                }
            }

            if (x != -1 && y != -1) {
                //g.setColor(Color.white);
                //g.drawRect(referenciaX + nodos[x][y].getArea().x, referenciaY + nodos[x][y].getArea().y, nodos[x][y].getArea().width, nodos[x][y].getArea().height);

                NodoDijkstra[] vecinos = nodos[x][y].getVecinos();
                for (int i = 0; i < vecinos.length; i++) {
                    if (vecinos[i].getValor() < menor) {
                        menor = vecinos[i].getValor();
                        vecinoFinal = i;
                    }
                }

                if (menor != Integer.MAX_VALUE && vecinoFinal != -1) {
                    //g.setColor(Color.yellow);
                    //g.drawRect(referenciaX + vecinos[vecinoFinal].getArea().x, referenciaY + vecinos[vecinoFinal].getArea().y, vecinos[vecinoFinal].getArea().width, vecinos[vecinoFinal].getArea().height);

                    Point movimiento = new Point();
                    if (nodos[x][y].getArea().x < vecinos[vecinoFinal].getArea().x) {
                        movimiento.x = 1;
                    } else if (nodos[x][y].getArea().x > vecinos[vecinoFinal].getArea().x) {
                        movimiento.x = -1;
                    } else if (nodos[x][y].getArea().y < vecinos[vecinoFinal].getArea().y) {
                        movimiento.y = 1;
                    } else if (nodos[x][y].getArea().y > vecinos[vecinoFinal].getArea().y) {
                        movimiento.y = -1;
                    }

                    if (alVecino) {
                        //System.out.println(movimiento.x + " " + movimiento.y);
                        return movimiento;
                    } else {
                        if (movimiento.x != 0) {
                            if (area.y < nodos[x][y].getArea().y) {
                                //System.out.println(0 + "-" + 1);
                                return new Point(0, 1);
                            } else if (area.y > nodos[x][y].getArea().y) {
                                //System.out.println(0 + "-" + -1);
                                return new Point(0, -1);
                            }
                        } else {
                            if (area.x < nodos[x][y].getArea().x) {
                                //System.out.println(1 + "-" + 0);
                                return new Point(1, 0);
                            } else if (area.x > nodos[x][y].getArea().x) {
                                //System.out.println(-1 + "-" + 0);
                                return new Point(-1, 0);
                            }
                        }
                    }
                }
            }
        }
        return new Point();
    }

    // --------------------------------------------------- STRING
    
    @Override
    public String toString() {
        String str = "\n";
        for (int i = 0; i < nodos.length; i++) {
            for (int j = 0; j < nodos[i].length; j++) {
                str += nodos[i][j].getValor() + "\t";
            }
            str += "\n";
        }
        return str;
    }

}
