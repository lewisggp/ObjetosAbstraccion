package ejercicios.asignaturas;

public class Seccion {

	private int nroSeccion;
	private Alumno[] alumnos;
	private int parcialesRealizados;
	private static int maximoAlumnos = 40;

	public Seccion(int nroSeccion) {
		this.nroSeccion = nroSeccion;
		this.parcialesRealizados = 0;
		this.alumnos = new Alumno[0]; // Importante!
	}

	// Obtener alumnos
	public Object[][] getDatos() {
		Object[][] datos = new Object[alumnos.length][];
		for (int i = 0; i < alumnos.length; i++)
			datos[i] = alumnos[i].getDatos();
		return datos;
	}

	// Obtener la cantidad de parciales realizados de la seccion
	public int getParcialesRealizados() {
		return parcialesRealizados;
	}

	// Obtener el numero de la seccion
	public int getNroSeccion() {
		return nroSeccion;
	}

	// Aumentar arreglo de parciales de cada alumno
	public void crearParcial() {
		for (int i = 0; i < alumnos.length; i++) {
			alumnos[i].agregarNota(0);
		}
		if (parcialesRealizados < Alumno.getMaximoNotas())
			parcialesRealizados++;
	}

	// Agregar alumno si no existe ordenado automaticamente al aumentar el
	// arreglo
	public void agregarAlumno(Alumno alumno) {
		if (alumnos.length < maximoAlumnos && !existeAlumno(alumno.getCedula()) && parcialesRealizados == 0) {
			Alumno[] alumnosTemporales = new Alumno[alumnos.length + 1];

			/*
			 * for(int i = 0; i < alumnos.length; i++){ alumnosTemporales[i] =
			 * alumnos[i]; } alumnosTemporales[alumnos.length] = alumno; alumnos
			 * = alumnosTemporales;
			 */

			boolean ingresado = false;
			for (int i = 0, j = 0; i < alumnos.length; i++) {
				if (!ingresado && (alumnos[i].getApellido().compareTo(alumno.getApellido()) > 0) || (alumnos[i].getApellido().compareTo(alumno.getApellido()) == 0) && alumnos[i].getNombre().compareTo(alumno.getNombre()) > 0) {
					ingresado = true;
					alumnosTemporales[j++] = alumno;
				}
				alumnosTemporales[j++] = alumnos[i];
			}
			if (!ingresado)
				alumnosTemporales[alumnos.length] = alumno;
			alumnos = alumnosTemporales;
		}
	}

	// Eliminar alumno si existe segun su cedula
	public void eliminarAlumno(int cedula) {
		Alumno[] alumnosTemporales = new Alumno[alumnos.length - 1];
		boolean ban = false;
		for (int i = 0, indiceTemporal = 0; i < alumnos.length; i++) {
			if (cedula == alumnos[i].getCedula()) {
				ban = true;
			} else {
				alumnosTemporales[indiceTemporal++] = alumnos[i];
			}
		}
		if (ban) {
			alumnos = alumnosTemporales;
		}
	}

	// Establecer nota global para todos los alumnos en un indice de parcial si
	// se ha realizado
	public void setNotaParcialAlumnos(int indiceParcial, int nota) {
		if (indiceParcial < parcialesRealizados)
			for (int i = 0; i < alumnos.length; i++) {
				alumnos[i].setNota(indiceParcial, nota);
			}
	}

	// Establecer nota individual de un parcial de un alumno segun su cedula
	public void setNotaAlumno(int cedula, int indiceParcial, int nota) {
		if (parcialesRealizados > 0)
			for (int i = 0; i < alumnos.length; i++) {
				if (alumnos[i].getCedula() == cedula) {
					alumnos[i].setNota(indiceParcial, nota);
				}
			}
	}

	// Saber si hay parciales disponibles
	public boolean parcialDisponible() {
		return parcialesRealizados < Alumno.getMaximoNotas();
	}

	// Saber si existe un alumno inscrito segun su cedula
	public boolean existeAlumno(int cedula) {
		for (int i = 0; i < alumnos.length; i++) {
			if (alumnos[i].getCedula() == cedula)
				return true;
		}
		return false;
	}

	// Saber si existe algun alumno inscrito en la seccion
	public boolean existenAlumnos() {
		return alumnos.length > 0;
	}

	// Saber si hay cupos disponibles en la seccion
	public boolean cuposDisponibles() {
		if (parcialesRealizados == 0)
			return alumnos.length < maximoAlumnos;
		return false;
	}

}