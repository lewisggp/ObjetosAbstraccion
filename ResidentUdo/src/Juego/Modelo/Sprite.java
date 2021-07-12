package Juego.Modelo;

import java.awt.image.BufferedImage;

public class Sprite {

    private final int id;
    private final BufferedImage imagen;
    private final int ancho;
    private final int alto;

    public Sprite(BufferedImage imagen, int id) {
        this.id = id;
        this.imagen = imagen;
        ancho = imagen.getWidth();
        alto = imagen.getHeight();
    }
    
    // --------------------------------------------------- GETTERS
    
    public int getId() {
        return id;
    }
    
    public BufferedImage getImagen() {
        return imagen;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

}
