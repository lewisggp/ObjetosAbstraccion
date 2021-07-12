package ejercicios.asignaturas;

public class Asignatura {

	private String nombre;
	private Seccion[] secciones;
	private static int maximoSecciones = 99;

	public Asignatura(String nombre) {
		this.nombre = nombre;
		secciones = new Seccion[0]; // Importante!
	}

	// Obtener secciones
	public Object[][][] getDatos() {
		Object[][][] datos = new Object[secciones.length][][];
		for (int i = 0; i < secciones.length; i++)
			datos[i] = secciones[i].getDatos();
		return datos;
	}

	// Obtener el nro se la seccion segun el indice
	public int getNroSeccionPorIndice(int indiceSeccion) {
		return secciones[indiceSeccion].getNroSeccion();
	}

	// Obtener la cantidad de seccion segun el indice
	public int getSeccionParcialesRealizadosPorIndice(int indiceSeccion) {
		return secciones[indiceSeccion].getParcialesRealizados();
	}

	// Obtener cantidad de secciones
	public int getCantidadSecciones() {
		return secciones.length;
	}

	// Obtener el nro de la seccion el que la esta inscrito un alumno de cedula
	// N
	public int getNroSeccionAlumno(int cedula) {
		for (int i = 0; i < secciones.length; i++) {
			if (secciones[i].existeAlumno(cedula)) {
				return secciones[i].getNroSeccion();
			}
		}
		return 0;
	}

	// Obtener la cantidad de parciales que ha realizado una seccion segun su
	// numero
	public int getSeccionParcialesRealizados(int nroSeccion) {
		for (int i = 0; i < secciones.length; i++) {
			if (secciones[i].getNroSeccion() == nroSeccion)
				return secciones[i].getParcialesRealizados();
		}
		return 0;
	}

	// Agregar seccion si no ha sido creada ordenado automaticamente al aumentar
	// el arreglo
	public void agregarSeccion(int nroSeccion) {
		if (nroSeccion > 0 && nroSeccion <= maximoSecciones && !existeSeccion(nroSeccion)) {
			Seccion[] seccionesTemporales = new Seccion[secciones.length + 1];

			/*
			 * for(int i = 0; i < secciones.length; i++){ seccionesTemporales[i]
			 * = secciones[i]; } seccionesTemporales[secciones.length] = new
			 * Seccion(nroSeccion); secciones = seccionesTemporales;
			 */

			boolean ingresado = false;
			for (int i = 0, j = 0; i < secciones.length; i++) {
				if (!ingresado && nroSeccion < secciones[i].getNroSeccion()) {
					ingresado = true;
					seccionesTemporales[j++] = new Seccion(nroSeccion);
				}
				seccionesTemporales[j++] = secciones[i];
			}
			if (!ingresado)
				seccionesTemporales[secciones.length] = new Seccion(nroSeccion);
			secciones = seccionesTemporales;
		}
	}

	// Agregar alumno a una seccion si encuenta una que tenga el mismo numero de
	// la seccion en las ya creadas
	public void agregarAlumno(int nroSeccion, Alumno alumno) {
		if (!existeAlumno(alumno.getCedula())) {
			for (int i = 0; i < secciones.length; i++) {
				if (secciones[i].getNroSeccion() == nroSeccion && !secciones[i].existeAlumno(alumno.getCedula())) {
					secciones[i].agregarAlumno(alumno);
					return;
				}
			}
		}
	}

	// Eliminar alumno de cedula N, buscando seccion por seccion
	public void eliminarAlumno(int cedula) {
		for (int i = 0; i < secciones.length; i++) {
			if (secciones[i].existeAlumno(cedula)) {
				secciones[i].eliminarAlumno(cedula);
				return;
			}
		}
	}

	// Aumentar arreglo de parciales de toda una seccion segun el numero de la
	// seccion
	public void seccionCrearParcial(int nroSeccion) {
		for (int i = 0; i < secciones.length; i++) {
			if (secciones[i].getNroSeccion() == nroSeccion)
				secciones[i].crearParcial();
		}
	}

	// Establecer la nota individual de un parcial de un alumno de cedula N,
	// buscando seccion por seccion
	public void setNotaParcialAlumno(int cedula, int indiceParcial, int nota) {
		for (int i = 0; i < secciones.length; i++) {
			if (secciones[i].existeAlumno(cedula)) {
				secciones[i].setNotaAlumno(cedula, indiceParcial, nota);
				break;
			}
		}
	}

	// Saber si en una seccion de pueden realizar mas parciales segun el numero
	// de la seccion
	public boolean seccionParcialDisponible(int nroSeccion) {
		for (int i = 0; i < secciones.length; i++) {
			if (secciones[i].getNroSeccion() == nroSeccion)
				return secciones[i].parcialDisponible();
		}
		return false;
	}

	// Saber si existe un alumno en la asignatura, buscando seccion por seccion
	public boolean existeAlumno(int cedula) {
		for (int i = 0; i < secciones.length; i++) {
			if (secciones[i].existeAlumno(cedula))
				return true;
		}
		return false;
	}

	// Saber si existen alumnos inscritos en alguna seccion
	public boolean existenAlumnos() {
		for (int i = 0; i < secciones.length; i++) {
			if (secciones[i].existenAlumnos())
				return true;
		}
		return false;
	}

	// Saber si existe una seccion segun el numero de la seccion
	public boolean existeSeccion(int nroSeccion) {
		for (int i = 0; i < secciones.length; i++) {
			if (secciones[i].getNroSeccion() == nroSeccion)
				return true;
		}
		return false;
	}

	// Saber si existen secciones disponibles o creadas en la asignatura
	public boolean existenSecciones() {
		return secciones.length > 0;
	}

	// Saber si existen alumnos en una seccion segun el indice
	public boolean existenAlumnosSeccionPorIndice(int indiceSeccion) {
		return secciones[indiceSeccion].existenAlumnos();
	}

	// Saber si hay cupos disponibles en una seccion segun el numero de seccion
	public boolean seccionCuposDisponibles(int nroSeccion) {
		for (int i = 0; i < secciones.length; i++) {
			if (secciones[i].getNroSeccion() == nroSeccion)
				return secciones[i].cuposDisponibles();
		}
		return false;
	}

}