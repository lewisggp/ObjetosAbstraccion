package Juego.Controlador;

import Juego.Modelo.Ente.Enemigo;
import Juego.Modelo.Ente.Vendedor;
import Juego.Modelo.Estructura.Diccionario;
import Juego.Modelo.Estructura.ListaCircular;
import Juego.Modelo.Estructura.ListaSimple;
import Juego.Modelo.Estructura.NodoListaSimple;
import Juego.Modelo.Modificador.Consumible;
import Juego.Modelo.Modificador.Desarmado;
import Juego.Modelo.Modificador.Escopeta;
import Juego.Modelo.Municion;
import Juego.Modelo.Modificador.Pistola;
import Juego.Modelo.Teletransportador;
import Juego.Vista.Estado.Presentacion;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class Cargador {
    
    public static final String RUTA = "resources";
    public static final String ENTE_DEFAULT = "Blue_32x32";
    public static final byte MAX_CARACTERES = 7;
    public static final byte USER_NO_EXIST = 0;
    public static final byte USER_EXIST = 1;
    public static final byte USER_TOO_LONG = 2;
    public static final byte USER_INVALID = 3;
    public static final byte PASS_INVALID = 4;
    public static final byte PASS_INCORRECT = 5;    
    public static final byte USER_CREATED = 6;
    public static final byte LOGIN_CORRECT = 7;
    public static final byte GUARDAR_SESION = 1;
    public static final byte RECORDAR_SESION = 2;

    // ----------------------------------------- CARGAS BASICAS
    
    public static BufferedImage imagen(final String subRuta, boolean transparente) {
        BufferedImage imagen = null;

        try {
            imagen = ImageIO.read(Cargador.class.getResourceAsStream(subRuta));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Graficos por defecto del usuario
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

        // Crear BufferedImage compatible con la pantalla
        BufferedImage imagenAcelerada;
        if (transparente) {
            imagenAcelerada = gc.createCompatibleImage(imagen.getWidth(null), imagen.getHeight(null), Transparency.TRANSLUCENT);
        } else {
            imagenAcelerada = gc.createCompatibleImage(imagen.getWidth(null), imagen.getHeight(null), Transparency.OPAQUE);
        }

        Graphics g = imagenAcelerada.getGraphics();
        g.drawImage(imagen, 0, 0, null);
        g.dispose();

        return imagenAcelerada;
    }

    public static String texto(final String subRuta) {
        String contenido = "";
        String linea;
        InputStream entradaBytes = null;
        BufferedReader lector = null;
        try {
            entradaBytes = Cargador.class.getResourceAsStream(subRuta);
            lector = new BufferedReader(new InputStreamReader(entradaBytes));
            while ((linea = lector.readLine()) != null) {
                contenido += linea;
            }
        } catch (Exception e) {
            System.out.println("Error al leer texto de " + subRuta + " " + e);
        } finally {
            try {
                if (entradaBytes != null) {
                    entradaBytes.close();
                }
                if (lector != null) {
                    lector.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }

        return contenido;
    }

    public static Font fuente(final String subRuta) {
        Font font = null;     
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new BufferedInputStream(new FileInputStream(RUTA + subRuta))).deriveFont(Font.PLAIN, 26);
        } catch (Exception ex) {
            Logger.getLogger(Presentacion.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se pudo cargar la fuente");
        }
        return font;
    }
        
    public static Clip sonido(final String ruta) {
        Clip clip = null;

        try {
            InputStream is = Cargador.class.getResourceAsStream(ruta);
            AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
            DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat());
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clip;
    }
        
    public static File carpeta(final String subRuta) {
        File directorio = new File(RUTA + subRuta);
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado");
            } else {
                System.out.println("Directorio existente");
            }
        }
        return directorio;
    }
    
    public static void escribir(final String subRuta, final String texto, boolean agregar) {
        File archivo = new File(RUTA + subRuta);
        FileWriter fw = null;
        BufferedWriter bw = null;
        try{
            if (!archivo.exists()) {
                archivo.createNewFile();
            }
            fw = new FileWriter(archivo, agregar);
            bw = new BufferedWriter(fw);
            bw.write(texto);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(bw != null)
                    bw.close();
            } catch (Exception e2) {
               e2.printStackTrace();
            }
        }
    }
    
    // ----------------------------------------- CASTINGS
    
    public static Object toObjeto(String str) {        
        String[] info = str.split(": ");
        switch (info[0]) {
            case "String":
                return info[1];
            case "Point":
                String[] p = info[1].split(", ");
                return new Point(Integer.parseInt(p[0]), Integer.parseInt(p[1]));
            case "Integer":
                return new Integer(info[1]);
            case "Rectangle":
                String[] datoColision = info[1].split(", ");
                return new Rectangle(Integer.parseInt(datoColision[0]),
                    Integer.parseInt(datoColision[1]),
                    Integer.parseInt(datoColision[2]),
                    Integer.parseInt(datoColision[3]));
            case "Pistola":
                return new Pistola(info[1]);
            case "Escopeta":
                return new Escopeta(info[1]);
            case "Desarmado":
                return new Desarmado(info[1]);
            case "Consumible":
                return new Consumible(info[1]);
            case "Enemigo":
                return new Enemigo(info[1]);
            case "Vendedor":
                return new Vendedor(str);
            case "Teletransportador":
                return new Teletransportador(info[1]);
            case "Municion":
                return new Municion(info[1]);
            default:
                System.out.println("Que es " + info[0] + "? xd");
                return str;
        }
    }
    
    public static ListaSimple toLista(String[] str) {
        ListaSimple lista = new ListaSimple();
        for (int i = 1; i < str.length; i++) { // La primera significa el tipo de grupo de objeto
            lista.add(toObjeto(str[i]));
        }
        return lista;
    }
    
    public static ListaCircular toListaCircular(String[] str) {
        ListaCircular lista = new ListaCircular();
        for (int i = 1; i < str.length; i++) { // La primera significa el tipo de grupo de objeto
            lista.add(toObjeto(str[i]));
        }
        return lista;
    }
    
    public static Diccionario toDiccionario(String[] str) {
        Diccionario diccionario = new Diccionario();
        
        for (int i = 1; i < str.length; i++) { // La primera significa el tipo de grupo de objeto
            String[] info = str[i].split(" => ");
            diccionario.add(toObjeto(info[0]), toObjeto(info[1]));
        }
        return diccionario;
    }
    
    // ----------------------------------------- OTRAS CARGAS
    
    public static ListaSimple puntajes(String subRuta) {
        File carpeta = new File(RUTA + subRuta);
        ListaSimple puntajes = new ListaSimple();
        ListaSimple valores = new ListaSimple();
        String[] listado = carpeta.list();
        if (listado == null || listado.length == 0) {
            System.out.println("No hay elementos dentro de la carpeta actual");
        } else {
            for (int i = 0; i < listado.length; i++) {
                String[] infoJugador = texto(subRuta + "/" + listado[i] + "/" + listado[i] + ".txt").split("-sig-");
                for(int j = 0; j < infoJugador.length; j++) {
                    String[] infoTemp = infoJugador[j].split("-");
                    if(infoTemp[0].equals("PUNTOS")) {
                        int k = 0;
                        //System.out.println(k);
                        int valorTemp = (int) Cargador.toObjeto(infoTemp[1]);
                        NodoListaSimple nodoValoresTemp = valores.getFirstNodo();
                        while(nodoValoresTemp != null) {
                            //System.out.println(nodoValoresTemp.get() + "<" + valorTemp + "?");
                            if((int) nodoValoresTemp.get() < valorTemp) {
                                //System.out.println("Si");
                                nodoValoresTemp = null;
                            } else {
                                //System.out.println("No k++");
                                k++;
                                nodoValoresTemp = nodoValoresTemp.getNext();
                            }
                        }
                        //System.out.println("Ingresando en k=" + k + " " + valores);
                        puntajes.add(k, listado[i] + " - " + valorTemp);
                        valores.add(k, valorTemp);
                        break;
                    }
                }
            }
        }        
        return puntajes;
    }
    
    public static ListaCircular personajes(String subRuta) {        
        File carpeta = new File(RUTA + subRuta);
        ListaCircular personajes = new ListaCircular();
        String[] listado = carpeta.list();
        if (listado == null || listado.length == 0) {
            System.out.println("No hay elementos dentro de la carpeta actual");
        } else {
            for (int i = 0; i < listado.length; i++) {
                if (!listado[i].equals("unknown.png")) {
                    personajes.add(listado[i]);
                }
            }
        }
        return personajes;
    }    
    
    public static Diccionario animacion(String subRuta, int ancho, int alto) {        
        File carpeta = new File(RUTA + subRuta);
        Diccionario hojasSprites = new Diccionario();
        String[] listado = carpeta.list();
        if (listado == null || listado.length == 0) {
            System.out.println("No hay elementos dentro de la carpeta actual");
        } else {
            String directorios[] = subRuta.split("/");
            String nombrePersonaje = directorios[directorios.length - 1].split("_")[0];
            for (int i = 0; i < listado.length; i++) {
                String[] datosTemp = listado[i].split("\\.");
                if (!datosTemp[0].equals(nombrePersonaje) && datosTemp[1].equals("png")) {
                    hojasSprites.add(new Integer(datosTemp[0].split("_")[1]), new HojaSprites(subRuta + "/" + listado[i], ancho, alto, true));
                }
            }
        }
        return hojasSprites;
    }

    public static Diccionario ultimaSesion() {
        String[] str = texto("/registros/ultima-sesion.txt").split("-");
        Diccionario datos = new Diccionario();
        if(str.length < 4) return datos;
        datos.add("User", str[0]);
        datos.add("Pass", str[1]);
        datos.add("Mantener", str[2].equals("1"));
        datos.add("Recordar", str[3].equals("1"));
        return datos;
    }
    
    // ----------------------------------------- SESION
    
    private static void guardarUsuario(Controlador controlador, ListaCircular disponibles) {
        String path = "/registros/usuarios/" + controlador.getJugador().getNombre() + "/";
        
        String str = controlador.getJugador().getNombre();
        str += "\n-sig-\nPUNTOS\n-\nInteger: " + controlador.getJugador().getPuntos();
        str += "\n-sig-\n" + disponibles.toString("DISPONIBLES", "-");
        str += "\n-sig-\nSELECCIONADO\n-\nString: " + controlador.getJugador().getNombreAnimacion();
        str += "\n-sig-\nMAPA\n-\nString: " + controlador.getMapa().getNombre();
        str += "\n-sig-\nPOSICION\n-\nPoint: " + controlador.getJugador().getPosicion().x + ", " + controlador.getJugador().getPosicion().y;
        str += "\n-sig-\nVIDA\n-\nInteger: " + controlador.getJugador().getVidaActual();
        if(controlador.getInventario().getConsumibles().size() > 0) str += "\n-sig-\n" + controlador.getInventario().getConsumibles().toString("CONSUMIBLES", "-");
        if(controlador.getInventario().getArmas().size() > 0) str += "\n-sig-\n" + controlador.getInventario().getArmas().toString("ARMAS", "-");
        if(controlador.getInventario().getMuniciones().size() > 0) str += "\n-sig-\n" + controlador.getInventario().getMuniciones().toString("MUNICIONES", "-");
        if(controlador.getAtajos().size() > 0) str += "\n-sig-\n" + controlador.getAtajos().toString("ATAJOS", "-");
        str += "\n-sig-\nARMA SELECCIONADA\n-\nInteger: " + controlador.getJugador().getArma().getId();
                
        escribir(path + controlador.getJugador().getNombre() + ".txt", str, false);
    }
    
    private static void guardarMapas(Controlador controlador){
        String path = "/registros/usuarios/" + controlador.getJugador().getNombre() + "/";
        NodoListaSimple nodoMapa = controlador.getMapas().getFirstNodo();
        while(nodoMapa != null) {
            Mapa temp = (Mapa) nodoMapa.get();
            escribir(path + "mapa" + temp.getNombre() + ".txt", temp.toString(), false);
            nodoMapa = nodoMapa.getNext();
        }
    }

    public static void guardarPartida(Controlador controlador, ListaCircular disponibles) {
        guardarUsuario(controlador, disponibles);
        guardarMapas(controlador);
    }
    
    public static void guardarUltimaSesion(String user, String pass, boolean mantener, boolean recordar) {
        String str = user + "\n-\n" + pass + "\n-\n" + (mantener ? 1 : 0) + "\n-\n" + (recordar ? 1 : 0);
        escribir("/registros/ultima-sesion.txt", str, false);
    }
    
    public static void guardarSesion(String user, String pass, boolean mantener, boolean recordar) {
        if(mantener || recordar)
            Cargador.guardarUltimaSesion(user, pass, mantener, recordar);
        else
            Cargador.borrarUltimaSesion();
    }
    
    public static void borrarUltimaSesion() {
        escribir("/registros/ultima-sesion.txt", "", false);
    }
    
    // ----------------------------------------- SOLICITUDES
    
    public static int register(String user, String pass) {        
        if(user.equals("") || user.contains(" ") || user.contains("-")){
            return USER_INVALID;            
        }
        if(user.length() > MAX_CARACTERES){
            return USER_TOO_LONG;            
        }
        if(pass.equals("") || pass.contains(" ") || pass.contains("-")){
            return PASS_INVALID;            
        }
        
        String[] usuarios = texto("/registros/registros-usuarios.txt").split("-sig-");
        for(int i = 0; i < usuarios.length; i++) {
            String[] datosUsuarioTemp = usuarios[i].split(" ");
            if(datosUsuarioTemp[0].equalsIgnoreCase(user)) {
                return USER_EXIST;
            }
        }
        
        String datos = user + "\n";
        escribir("/registros/registros-usuarios.txt", "\n-sig-\n" + user + " " + pass, true);
        carpeta("/registros/usuarios/" + user);
        String info = user + "\n" +
                    "-sig-\n" +
                    "DISPONIBLES\n" +
                    "-\n" +
                    "Blue_32x32\n" +
                    "-sig-\n" +
                    "SELECCIONADO\n" +
                    "-\n" +
                    ENTE_DEFAULT + "\n" + 
                    "-sig-\n" +
                    "MAPA\n" +
                    "-\n" +
                    "UDO";
        escribir("/registros/usuarios/" + user + "/" + user + ".txt", info, false);
        return USER_CREATED;
    }
    
    public static int login(String user, String pass) {        
        String[] usuarios = Cargador.texto("/registros/registros-usuarios.txt").split("-sig-");
        for(int i = 0; i < usuarios.length; i++) {
            String[] datosUsuarioTemp = usuarios[i].split(" ");
            if(datosUsuarioTemp[0].equals(user)) {
                if(datosUsuarioTemp[1].equals(pass)) {
                    return LOGIN_CORRECT;
                } else {
                    return PASS_INCORRECT;
                }
            }
        }
        return USER_NO_EXIST;
    }
    
}
