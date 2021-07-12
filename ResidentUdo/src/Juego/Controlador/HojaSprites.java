package Juego.Controlador;

import java.awt.image.BufferedImage;

import Juego.Modelo.Sprite;

public class HojaSprites {

    final private String ruta;
    final private int ancho;
    final private int alto;

    final private int spritesX;
    final private int spritesY;

    final private Sprite[] sprites;

    public HojaSprites(String ruta, int lado, boolean transparente) {
        BufferedImage imagen;
        this.ruta = ruta;

        if (transparente) {
            imagen = Cargador.imagen(ruta, true);
        } else {
            imagen = Cargador.imagen(ruta, false);
        }

        ancho = imagen.getWidth();
        alto = imagen.getHeight();

        spritesX = ancho / lado;
        spritesY = alto / lado;

        sprites = new Sprite[spritesX * spritesY];

        for (int y = 0; y < spritesY; y++) {
            for (int x = 0; x < spritesX; x++) {
                sprites[x + y * spritesX] = new Sprite(imagen.getSubimage(x * lado, y * lado, lado, lado), x + y * spritesX);
            }
        }
    }
    
    public HojaSprites(String ruta, int ancho, int alto, boolean transparente) {
        BufferedImage imagen;
        this.ruta = ruta;

        if (transparente) {
            imagen = Cargador.imagen(ruta, true);
        } else {
            imagen = Cargador.imagen(ruta, false);
        }

        this.ancho = imagen.getWidth();
        this.alto = imagen.getHeight();

        spritesX = this.ancho / ancho;
        spritesY = this.alto / alto;

        sprites = new Sprite[spritesX * spritesY];

        for (int y = 0; y < spritesY; y++) {
            for (int x = 0; x < spritesX; x++) {
                sprites[x + y * spritesX] = new Sprite(imagen.getSubimage(x * ancho, y * alto, ancho, alto), x + y * spritesX);
            }
        }
    }

    // --------------------------------------------------- GETTERS
    
    public String getRuta() {
        return ruta;
    }
    
    public Sprite getSprite(int indice) {
        return sprites[indice];
    }

    public Sprite getSprite(int spritesX, int spritesY) {
        return sprites[spritesX + spritesY * this.spritesX];
    }

    public int getSpritesX() {
        return spritesX;
    }

    public int getSpritesY() {
        return spritesY;
    }
    
    // --------------------------------------------------- STRING
    
    @Override
    public String toString() {
        return "[HojaSprites - Width:" + ancho + ", Height:" + alto + ", Sprites:" + sprites.length + "]";
    }

}
