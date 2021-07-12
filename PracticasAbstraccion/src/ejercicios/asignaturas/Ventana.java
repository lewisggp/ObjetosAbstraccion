package ejercicios.asignaturas;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Ventana extends JFrame {

	private Asignatura asignatura;
	private int anchoVentana;
	private int alturaVentana;

	public Ventana() {
		setTitle("UDO Asignaturas");

		Toolkit pantalla = Toolkit.getDefaultToolkit();
		Dimension tamano = pantalla.getScreenSize();
		alturaVentana = tamano.height;
		anchoVentana = tamano.width;

		Image icono = pantalla.getImage("img/udoIcono.png");
		setIconImage(icono);

		asignatura = new Asignatura("Abstraccion");
		asignatura.agregarSeccion(10);
		for (int i = 0; i < 50; i++) {
			asignatura.agregarAlumno(10, new Alumno(i + 1, "Alumno" + i, "Apellido" + i, "Carrera" + i));
		}

		Lamina lamina = new Lamina();
		add(lamina);
		pack();
		setLocation((anchoVentana - this.getWidth()) / 2, (alturaVentana - this.getHeight()) / 2);
	}

	public class Lamina extends JPanel {
		public Lamina() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

			JButton cantidadSeccion = new JButton("Mostrar cantidad de secciones");
			JButton inscribirAlumno = new JButton("Inscribir alumno");
			JButton retirarAlumno = new JButton("Retirar alumno");
			JButton establecerNotaAlumno = new JButton("Establecer nota alumno");
			JButton mostrarAlumnos = new JButton("Mostrar alumnos");
			JButton crearParcial = new JButton("Crear Parcial");
			JButton crearSeccion = new JButton("Crear Seccion");

			cantidadSeccion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (asignatura.existenSecciones()) {
						JOptionPane.showMessageDialog(Lamina.this, "La cantidad de secciones son: " + asignatura.getCantidadSecciones(), "Cantidad Secciones", JOptionPane.CANCEL_OPTION);
					} else {
						JOptionPane.showMessageDialog(Lamina.this, "No existen secciones", "Cantidad Secciones", JOptionPane.CANCEL_OPTION);
					}
				}
			});

			inscribirAlumno.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JDialog dialogo = new JDialog(Ventana.this);
					JPanel panel = (JPanel) dialogo.getContentPane();
					panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					dialogo.setTitle("Inscribir alumno");

					JPanel laminaInscribir = new JPanel();
					laminaInscribir.setLayout(new GridLayout(6, 2));

					laminaInscribir.add(new JLabel("Nombre"));
					JTextField campoNombre = new JTextField();
					laminaInscribir.add(campoNombre);

					laminaInscribir.add(new JLabel("Apellido"));
					JTextField campoApellido = new JTextField();
					laminaInscribir.add(campoApellido);

					laminaInscribir.add(new JLabel("Carrera"));
					JTextField campoCarrera = new JTextField();
					laminaInscribir.add(campoCarrera);

					laminaInscribir.add(new JLabel("Cedula"));
					JTextField campoCedula = new JTextField();
					laminaInscribir.add(campoCedula);

					laminaInscribir.add(new JLabel("Seccion"));
					JTextField campoSeccion = new JTextField();
					laminaInscribir.add(campoSeccion);

					JButton agregar = new JButton("Agregar");
					laminaInscribir.add(agregar);

					JButton cancelar = new JButton("Cancelar");
					laminaInscribir.add(cancelar);

					agregar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								String nombre = campoNombre.getText();
								String apellido = campoApellido.getText();
								String carrera = campoCarrera.getText();
								int cedula = Integer.parseInt(campoCedula.getText());
								int nroSeccion = Integer.parseInt(campoSeccion.getText());

								if (cedula > 0) {
									if (!asignatura.existeAlumno(cedula)) {
										if (nroSeccion > 0 && nroSeccion < 100) {
											if (!asignatura.existeSeccion(nroSeccion)) {
												asignatura.agregarSeccion(nroSeccion);
											}
											if (asignatura.getSeccionParcialesRealizados(nroSeccion) == 0) {
												if (asignatura.seccionCuposDisponibles(nroSeccion)) {
													asignatura.agregarAlumno(nroSeccion, new Alumno(cedula, nombre, apellido, carrera));
													JOptionPane.showMessageDialog(dialogo, "Agregado con exito a la seccion " + nroSeccion, "Exito", JOptionPane.CANCEL_OPTION);
												} else {
													JOptionPane.showMessageDialog(dialogo, "La seccion " + nroSeccion + " esta llena. Cupos no disponibles", "Error", JOptionPane.CANCEL_OPTION);
												}
											} else {
												JOptionPane.showMessageDialog(dialogo, "La seccion " + nroSeccion + " ya empezo las evaluaciones", "Error", JOptionPane.CANCEL_OPTION);
											}
										} else {
											JOptionPane.showMessageDialog(dialogo, "Seccion invalida. Minimo 1 - Maximo 99", "Error", JOptionPane.CANCEL_OPTION);
										}
									} else {
										JOptionPane.showMessageDialog(dialogo, "Alumno de cedula " + cedula + " ya esta inscrito", "Error", JOptionPane.CANCEL_OPTION);
									}
								} else {
									JOptionPane.showMessageDialog(dialogo, "Cedula invalida. Minimo 1", "Error", JOptionPane.CANCEL_OPTION);
								}
								dialogo.dispose();
							} catch (Exception er) {
								JOptionPane.showMessageDialog(dialogo, "Introduciste mal los datos", "Advertencia", JOptionPane.CANCEL_OPTION);
							}
						}
					});

					cancelar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							dialogo.dispose();
						}
					});

					dialogo.add(laminaInscribir);
					dialogo.pack();
					dialogo.setLocation((anchoVentana - dialogo.getWidth()) / 2, (alturaVentana - dialogo.getHeight()) / 2);
					dialogo.setVisible(true);
				}
			});

			retirarAlumno.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JDialog dialogo = new JDialog(Ventana.this);
					JPanel panel = (JPanel) dialogo.getContentPane();
					panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					dialogo.setTitle("Retirar alumno");

					JPanel laminaEliminar = new JPanel();
					laminaEliminar.setLayout(new GridLayout(2, 2));

					laminaEliminar.add(new JLabel("Cedula"));
					JTextField campoCedula = new JTextField();
					laminaEliminar.add(campoCedula);

					JButton eliminar = new JButton("Eliminar");
					laminaEliminar.add(eliminar);

					JButton cancelar = new JButton("Cancelar");
					laminaEliminar.add(cancelar);

					eliminar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								int cedula = Integer.parseInt(campoCedula.getText());

								// mostrar(asignatura.seccionesAlumnosToString());
								if (asignatura.existenAlumnos()) {
									if (cedula > 0) {
										if (asignatura.existeAlumno(cedula)) {
											asignatura.eliminarAlumno(cedula);
											JOptionPane.showMessageDialog(dialogo, "Alumno retirado con exito", "Exito", JOptionPane.CANCEL_OPTION);
										} else {
											JOptionPane.showMessageDialog(dialogo, "No existe un bachiller en esta asignatura de cedula " + cedula, "Error", JOptionPane.CANCEL_OPTION);
										}
									} else {
										JOptionPane.showMessageDialog(dialogo, "Cedula invalida. Tiene que ser mayor que cero", "Error", JOptionPane.CANCEL_OPTION);
									}
								} else {
									JOptionPane.showMessageDialog(dialogo, "No existen alumnos", "Error", JOptionPane.CANCEL_OPTION);
								}
								dialogo.dispose();
							} catch (Exception er) {
								JOptionPane.showMessageDialog(dialogo, "Introduciste mal los datos", "Advertencia", JOptionPane.CANCEL_OPTION);
							}
						}
					});

					cancelar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							dialogo.dispose();
						}
					});

					dialogo.add(laminaEliminar);
					dialogo.pack();
					dialogo.setLocation((anchoVentana - dialogo.getWidth()) / 2, (alturaVentana - dialogo.getHeight()) / 2);
					dialogo.setVisible(true);
				}
			});

			establecerNotaAlumno.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JDialog dialogo = new JDialog(Ventana.this);
					JPanel panel = (JPanel) dialogo.getContentPane();
					panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					dialogo.setTitle("Establecer Nota");

					JPanel laminaEstablecer = new JPanel();
					laminaEstablecer.setLayout(new GridLayout(4, 2));

					laminaEstablecer.add(new JLabel("Cedula"));
					JTextField campoCedula = new JTextField();
					laminaEstablecer.add(campoCedula);

					laminaEstablecer.add(new JLabel("Nro de Parcial"));
					JTextField campoNroParcial = new JTextField();
					laminaEstablecer.add(campoNroParcial);

					laminaEstablecer.add(new JLabel("Nota de parcial"));
					JTextField campoNota = new JTextField();
					laminaEstablecer.add(campoNota);

					JButton establecer = new JButton("Establecer");
					laminaEstablecer.add(establecer);

					JButton cancelar = new JButton("Cancelar");
					laminaEstablecer.add(cancelar);

					establecer.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								int cedula = Integer.parseInt(campoCedula.getText());
								int indiceParcial = Integer.parseInt(campoNroParcial.getText()) - 1;
								int nota = Integer.parseInt(campoNota.getText());

								if (asignatura.existeAlumno(cedula)) {
									int nroSeccion = asignatura.getNroSeccionAlumno(cedula);
									int parcialesRealizados = asignatura.getSeccionParcialesRealizados(nroSeccion);
									if (parcialesRealizados > 0) {
										if (indiceParcial >= 0 && indiceParcial < parcialesRealizados) {
											if (nota >= 0 && nota <= 10) {
												asignatura.setNotaParcialAlumno(cedula, indiceParcial, nota);
												JOptionPane.showMessageDialog(dialogo, "Nota del parcial asignada con exito", "Exito", JOptionPane.CANCEL_OPTION);
											} else {
												JOptionPane.showMessageDialog(dialogo, "Nota invalida. Minimo 0 - Maximo 10. Solo numeros enteros", "Error", JOptionPane.CANCEL_OPTION);
											}
										} else {
											JOptionPane.showMessageDialog(dialogo, "No ingresaste un parcial valido. Minimo 1 - Maximo " + parcialesRealizados, "Error", JOptionPane.CANCEL_OPTION);
										}
									} else {
										JOptionPane.showMessageDialog(dialogo, "La seccion " + nroSeccion + " no ha empezado las evaluaciones", "Error", JOptionPane.CANCEL_OPTION);
									}
								} else {
									JOptionPane.showMessageDialog(dialogo, "No existe un bachiller en esta asignatura de cedula " + cedula, "Error", JOptionPane.CANCEL_OPTION);
								}

								dialogo.dispose();
							} catch (Exception er) {
								JOptionPane.showMessageDialog(dialogo, "Introduciste mal los datos", "Advertencia", JOptionPane.CANCEL_OPTION);
							}
						}
					});

					cancelar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							dialogo.dispose();
						}
					});

					dialogo.add(laminaEstablecer);
					dialogo.pack();
					dialogo.setLocation((anchoVentana - dialogo.getWidth()) / 2, (alturaVentana - dialogo.getHeight()) / 2);
					dialogo.setVisible(true);
				}
			});

			mostrarAlumnos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (asignatura.existenAlumnos()) {
						Object[][][] datos = asignatura.getDatos();
						for (int i = 0; i < datos.length; i++) {
							int nroSeccion = asignatura.getNroSeccionPorIndice(i);
							JDialog dialogo = new JDialog(Ventana.this);
							JPanel panel = (JPanel) dialogo.getContentPane();
							panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
							if (asignatura.existenAlumnosSeccionPorIndice(i)) {
								dialogo.setTitle("Alumnos de la seccion " + nroSeccion);

								Object[] nombresColumnas = new Object[6 + asignatura.getSeccionParcialesRealizadosPorIndice(i)];
								nombresColumnas[0] = "Nombre";
								nombresColumnas[1] = "Apellido";
								nombresColumnas[2] = "Cedula";
								nombresColumnas[3] = "Carrera";
								System.out.println(asignatura.getSeccionParcialesRealizadosPorIndice(i));
								for (int j = 4; j - 4 < asignatura.getSeccionParcialesRealizadosPorIndice(i); j++)
									nombresColumnas[j] = "Parcial" + (j + 1 - 4);
								nombresColumnas[nombresColumnas.length - 2] = "Promedio";
								nombresColumnas[nombresColumnas.length - 1] = "Porcentaje";

								DefaultTableModel modelo = new DefaultTableModel(datos[i], nombresColumnas);
								JTable tabla = new JTable(modelo);
								dialogo.add(new JScrollPane(tabla));
							} else {
								dialogo.setTitle("Seccion vacia - " + nroSeccion);
								dialogo.add(new JLabel("No existen alumnos en la seccion " + nroSeccion));
							}
							dialogo.pack();
							dialogo.setLocation((anchoVentana - dialogo.getWidth()) / 2, (alturaVentana - dialogo.getHeight()) / 2);
							dialogo.setVisible(true);
						}
					} else {
						JOptionPane.showMessageDialog(Lamina.this, "No existen alumnos", "Advertencia", JOptionPane.CANCEL_OPTION);
					}

				}
			});

			crearParcial.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JDialog dialogo = new JDialog(Ventana.this);
					JPanel panel = (JPanel) dialogo.getContentPane();
					panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					dialogo.setTitle("Crear Parcial");

					JPanel laminaCrearParcial = new JPanel();
					laminaCrearParcial.setLayout(new GridLayout(2, 2));

					laminaCrearParcial.add(new JLabel("Nro de seccion"));
					JTextField campoNroSeccion = new JTextField();
					laminaCrearParcial.add(campoNroSeccion);

					JButton crearParcial = new JButton("Crear");
					laminaCrearParcial.add(crearParcial);

					JButton cancelar = new JButton("Cancelar");
					laminaCrearParcial.add(cancelar);

					crearParcial.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								int nroSeccion = Integer.parseInt(campoNroSeccion.getText());

								if (asignatura.existenSecciones()) {
									if (asignatura.existeSeccion(nroSeccion)) {
										if (asignatura.seccionParcialDisponible(nroSeccion)) {
											asignatura.seccionCrearParcial(nroSeccion);
											JOptionPane.showMessageDialog(dialogo, "Parcial creado con exito. Nota actual de los alumnos: 0", "Exito", JOptionPane.CANCEL_OPTION);
										} else {
											JOptionPane.showMessageDialog(dialogo, "La seccion " + nroSeccion + " no tiene parciales disponibles", "Error", JOptionPane.CANCEL_OPTION);
										}
									} else {
										JOptionPane.showMessageDialog(dialogo, "La seccion " + nroSeccion + " no existe", "Error", JOptionPane.CANCEL_OPTION);
									}
								} else {
									JOptionPane.showMessageDialog(dialogo, "No existen alumnos", "Error", JOptionPane.CANCEL_OPTION);
								}

								dialogo.dispose();
							} catch (Exception er) {
								JOptionPane.showMessageDialog(dialogo, "Introduciste mal los datos", "Advertencia", JOptionPane.CANCEL_OPTION);
							}
						}
					});

					cancelar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							dialogo.dispose();
						}
					});

					dialogo.add(laminaCrearParcial);
					dialogo.pack();
					dialogo.setLocation((anchoVentana - dialogo.getWidth()) / 2, (alturaVentana - dialogo.getHeight()) / 2);
					dialogo.setVisible(true);
				}
			});

			crearSeccion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JDialog dialogo = new JDialog(Ventana.this);
					JPanel panel = (JPanel) dialogo.getContentPane();
					panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					dialogo.setTitle("Retirar alumno");

					JPanel laminaCrearSeccion = new JPanel();
					laminaCrearSeccion.setLayout(new GridLayout(2, 2));

					laminaCrearSeccion.add(new JLabel("Nro Seccion"));
					JTextField campoNroSeccion = new JTextField();
					laminaCrearSeccion.add(campoNroSeccion);

					JButton crear = new JButton("Crear");
					laminaCrearSeccion.add(crear);

					JButton cancelar = new JButton("Cancelar");
					laminaCrearSeccion.add(cancelar);

					crear.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								int nroSeccion = Integer.parseInt(campoNroSeccion.getText());
								if (!asignatura.existeSeccion(nroSeccion)) {
									asignatura.agregarSeccion(nroSeccion);
									JOptionPane.showMessageDialog(dialogo, "La seccion " + nroSeccion + " fue creada con exito", "Exito", JOptionPane.CANCEL_OPTION);
								} else {
									JOptionPane.showMessageDialog(dialogo, "La seccion " + nroSeccion + " ya existe", "Error", JOptionPane.CANCEL_OPTION);
								}
								dialogo.dispose();
							} catch (Exception er) {
								JOptionPane.showMessageDialog(dialogo, "Introduciste mal los datos", "Advertencia", JOptionPane.CANCEL_OPTION);
							}
						}
					});

					cancelar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							dialogo.dispose();
						}
					});

					dialogo.add(laminaCrearSeccion);
					dialogo.pack();
					dialogo.setLocation((anchoVentana - dialogo.getWidth()) / 2, (alturaVentana - dialogo.getHeight()) / 2);
					dialogo.setVisible(true);
				}
			});

			cantidadSeccion.setAlignmentX(Component.CENTER_ALIGNMENT);
			inscribirAlumno.setAlignmentX(Component.CENTER_ALIGNMENT);
			retirarAlumno.setAlignmentX(Component.CENTER_ALIGNMENT);
			establecerNotaAlumno.setAlignmentX(Component.CENTER_ALIGNMENT);
			mostrarAlumnos.setAlignmentX(Component.CENTER_ALIGNMENT);
			crearParcial.setAlignmentX(Component.CENTER_ALIGNMENT);
			crearSeccion.setAlignmentX(Component.CENTER_ALIGNMENT);

			add(cantidadSeccion);
			add(inscribirAlumno);
			add(retirarAlumno);
			add(establecerNotaAlumno);
			add(mostrarAlumnos);
			add(crearParcial);
			add(crearSeccion);
		}
	}
}
