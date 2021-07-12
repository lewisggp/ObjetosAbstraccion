package ejercicios.asignaturas;

public class Alumno {

	private int cedula;
	private String nombre;
	private String apellido;
	private String carrera;
	private int[] notas;
	private static int maximoNotas = 3;

	public Alumno(int cedula, String nombre, String apellido, String carrera) {
		this.cedula = cedula;
		this.nombre = nombre;
		this.apellido = apellido;
		this.carrera = carrera;
		this.notas = new int[0]; // Importante!
	}

	public Object[] getDatos() {
		Object[] datos = new Object[6 + notas.length];
		datos[0] = apellido;
		datos[1] = nombre;
		datos[2] = cedula;
		datos[3] = carrera;
		for (int i = 4; i - 4 < notas.length; i++)
			datos[i] = notas[i - 4];
		datos[datos.length - 2] = promedio();
		datos[datos.length - 1] = porcentaje();
		return datos;
	}

	public static int getMaximoNotas() {
		return maximoNotas;
	}

	public int getCedula() {
		return cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	// Obtener la nota de un parcial segun su indice
	public int getNotaParcial(int indiceParcial) {
		if (indiceParcial < notas.length) {
			return notas[indiceParcial];
		} else {
			return 0;
		}
	}

	// Extender el arreglo de notas para agregar una nota si es menor al maximo
	// de notas
	public void agregarNota(int nota) {
		if (notas.length < maximoNotas) {
			int[] notasTemporales = new int[notas.length + 1];
			for (int i = 0; i < notas.length; i++) {
				notasTemporales[i] = notas[i];
			}
			notasTemporales[notas.length] = nota;
			notas = notasTemporales;
		}

	}

	// Establecer nota de un parcial en especifico
	public void setNota(int indiceParcial, int nota) {
		if (indiceParcial >= 0 && indiceParcial < notas.length)
			notas[indiceParcial] = nota;
	}

	// Promedio basado en la cantidad de parciales hechos
	public double promedio() {
		double suma = 0;
		for (int i = 0; i < notas.length; i++) {
			suma += notas[i];
		}
		double promedio = suma / notas.length;
		return Math.round(promedio * 100.0) / 100.0;
	}

	// Porcentaje basado en que todos los parciales hechos constituyen el 50%
	public double porcentaje() {
		return promedio() * 0.5;
	}

}