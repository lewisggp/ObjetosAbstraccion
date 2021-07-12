package componentesGraficos;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 *
 * @author Lewis
 */

public class Ventana extends JFrame {

	public Ventana() {

		setTitle("Prueba Graficos");

		// Ponerlo visible
		// setVisible(true);

		// Definir tamaño
		// setSize(500, 300);
		// setExtendedState(Frame.MAXIMIZED_BOTH); //Campo de Clase
		// MAXIMIZED_BOTH = 6

		// Definir posicion
		// setLocation(500,150);

		// Definir tamaño y posicion
		// setBounds(500,150,500,300);

		// Que no sea redimensionable
		// setResizable(false);

		// Definir titulo
		// setTitle("Titulo de la ventana");

		// Definir posicion en la mitad de la pantalla
		// Toolkit. Sistema Nativo de la ventana
		Toolkit pantalla = Toolkit.getDefaultToolkit();
		Dimension tamano = pantalla.getScreenSize();
		int altura = tamano.height;
		int ancho = tamano.width;
		setBounds((ancho - 600) / 2, (altura - 800) / 2, 600, 800);
		System.out.println("Altura: " + altura + ". Ancho: " + ancho);

		// Definir Icono
		Image icono = pantalla.getImage("img/udoIcono.png");
		setIconImage(icono);

		// Lamina JPanel
		LaminaComponentes lamina = new LaminaComponentes();

		// lamina.setBackground(new Color(255,255,255));
		add(lamina);

		// pack();
	}

	public class LaminaGraphics extends JPanel {

		public LaminaGraphics() {
			setLayout(null);// disposicion libre
		}

		// Sobreescribir metodo
		public void paint(Graphics g) {

			// Pintar
			// g.drawString("La cada más alta", 20, 20);

			// g.drawRect(150, 180, 40, 40);
			// g.drawLine(150, 100, 545, 260);
			// g.drawArc(150, 300, 300, 100, 200, 180);
			// g.drawOval(50, 50, 500, 500);
			// g.fillRect(350, 180, 40, 40);

			// Java2D
			Graphics2D g2 = (Graphics2D) g;
			Rectangle2D rectangulo = new Rectangle2D.Double(100, 100, 200, 150);
			g2.draw(rectangulo);
			Ellipse2D elipse = new Ellipse2D.Double();
			elipse.setFrame(rectangulo);
			g2.draw(elipse);
			g2.draw(new Line2D.Double(100, 100, 300, 250));
			double CentroX = rectangulo.getCenterX();
			double CentroY = rectangulo.getCenterY();
			Ellipse2D circulo = new Ellipse2D.Double();
			circulo.setFrameFromCenter(CentroX, CentroY, CentroX + 125, CentroY + 125);
			g2.draw(circulo);
			//
			// g2.setColor(Color.RED);
			// g2.fill(elipse);
			// g2.setPaint(new Color(30, 200, 40).brighter().brighter());
			// g2.draw(circulo);

			// Imagenes
			try {
				File archivo = new File("img/udoLogo.png");
				Image imagen = ImageIO.read(archivo);
				Image nuevaImagen = imagen.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
				g.drawImage(nuevaImagen, 0, 0, null);
				g.copyArea(20, 20, 60, 60, 100, 100);
			} catch (IOException e) {
				System.out.println("La imagen no se encuentra");
			}
		}
	}

	public class LaminaFlowLayout extends JPanel {
		public LaminaFlowLayout() {
			setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
			for (int i = 1; i <= 10; i++)
				add(new JButton("Componente " + i));
		}
	}

	public class LaminaBorderLayout extends JPanel {
		public LaminaBorderLayout() {
			setLayout(new BorderLayout(5, 10));
			add(new JButton("1"), BorderLayout.EAST);
			add(new JButton("2"), BorderLayout.SOUTH);
			add(new JButton("3"), BorderLayout.WEST);
			add(new JButton("4"), BorderLayout.NORTH);
			add(new JButton("5"), BorderLayout.CENTER);
		}
	}

	public class LaminaGridLayout extends JPanel {
		public LaminaGridLayout() {
			setLayout(new GridLayout(3, 4, 20, 20));
			for (int i = 0; i < 3 * 4; i++)
				add(new JButton(Integer.toString(i + 1)));
		}
	}

	public class LaminaCombinada extends JPanel {
		public LaminaCombinada() {
			setLayout(new BorderLayout());

			// Panel 1
			JPanel p1 = new JPanel();
			p1.setLayout(new GridLayout(4, 3));
			for (int i = 1; i <= 9; i++) {
				p1.add(new JButton("" + i));
			}
			p1.add(new JButton("" + 0));
			p1.add(new JButton("Start"));
			p1.add(new JButton("Stop"));

			// Panel 2
			JPanel p2 = new JPanel();
			p2.setLayout(new BorderLayout());
			p2.add(new JTextField("Aquí el tiempo"), BorderLayout.NORTH);
			p2.add(p1, BorderLayout.CENTER);

			add(p2, BorderLayout.EAST);
			add(new Button("Aquí la comida"), BorderLayout.CENTER);
		}
	}

	public class LaminaComponentes extends JPanel {

		public LaminaComponentes() {
			setLayout(new GridLayout(16, 2));
			setBorder(new EmptyBorder(0, 20, 0, 20));

			// JLabel
			JPanel panelLabel = new JPanel();
			panelLabel.add(new JLabel("Nombre"));
			add(panelLabel);
			JPanel tituloDerecha = new JPanel();
			tituloDerecha.add(new JLabel("Ejemplo"));
			add(tituloDerecha);

			// Botones
			JPanel panelBotonesIzquierdo = new JPanel();
			JPanel panelBotones = new JPanel();
			panelBotonesIzquierdo.add(new JLabel("JButton"));
			panelBotones.add(new JButton("1"));
			panelBotonesIzquierdo.add(new JLabel("JToggleButton"));
			panelBotones.add(new JToggleButton("2"));
			panelBotonesIzquierdo.add(new JLabel("JToggleButton"));
			panelBotones.add(new JToggleButton("3", true));
			add(panelBotonesIzquierdo);
			add(panelBotones);

			// JCheckBox
			JPanel panelTituloCheckBox = new JPanel();
			JPanel panelCheckBox = new JPanel();
			panelTituloCheckBox.add(new JLabel("JCheckBox"));
			panelCheckBox.add(new JCheckBox("Cine"));
			panelCheckBox.add(new JCheckBox("Teatro"));
			panelCheckBox.add(new JCheckBox("Música"));
			add(panelTituloCheckBox);
			add(panelCheckBox);

			// JRadioButton
			JPanel panelTituloRadio = new JPanel();
			JPanel panelRadio = new JPanel();
			panelTituloRadio.add(new JLabel("JRadioButton"));
			ButtonGroup grupoRadioButton = new ButtonGroup();
			JRadioButton radioButton1 = new JRadioButton("Radio 1");
			JRadioButton radioButton2 = new JRadioButton("Radio 2");
			JRadioButton radioButton3 = new JRadioButton("Radio 3");
			grupoRadioButton.add(radioButton1);
			grupoRadioButton.add(radioButton2);
			grupoRadioButton.add(radioButton3);
			panelRadio.add(radioButton1);
			panelRadio.add(radioButton2);
			panelRadio.add(radioButton3);
			add(panelTituloRadio);
			add(panelRadio);

			// JComboBox
			JPanel panelTituloComboBox = new JPanel();
			JPanel panelComboBox = new JPanel();
			JComboBox comboBox = new JComboBox();
			comboBox.addItem("rojo");
			comboBox.addItem("verde");
			comboBox.addItem("azul");
			comboBox.addItem("amarillo");
			comboBox.addItem("negro");
			panelComboBox.add(comboBox);
			panelTituloComboBox.add(new JLabel("JComboBox"));
			add(panelTituloComboBox);
			add(panelComboBox);

			// JScrollBar
			JPanel panelTituloScrollBar = new JPanel();
			JPanel panelScrollBar = new JPanel();
			JScrollBar scrollBar = new JScrollBar(JScrollBar.HORIZONTAL);
			panelTituloScrollBar.add(new JLabel("JScrollBar"));
			add(panelTituloScrollBar);
			panelScrollBar.add(scrollBar);
			add(panelScrollBar);

			// JSlider
			JPanel panelTituloSlider = new JPanel();
			JPanel panelSlider = new JPanel();
			panelTituloSlider.add(new JLabel("JSlider"));
			// 0 a 50, que empieze en 25
			JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 50, 25);
			slider.setMinorTickSpacing(2); // Separacion de 2
			slider.setMajorTickSpacing(10); // en 10
			slider.setPaintTicks(true); // Medidor
			slider.setPaintLabels(true); // Etiquetas
			panelSlider.add(slider);
			add(panelTituloSlider);
			add(panelSlider);

			// JSpinner
			JPanel panelTituloSpinner = new JPanel();
			JPanel panelSpinner = new JPanel();
			panelTituloSpinner.add(new JLabel("JSpinner"));
			JSpinner spinnerHoras = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
			JSpinner spinnerMinutos = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
			JSpinner spinnerSiglas = new JSpinner(new SpinnerListModel(new String[]{"am", "pm"}));
			panelSpinner.add(new JSpinner());
			panelSpinner.add(spinnerHoras);
			panelSpinner.add(spinnerMinutos);
			panelSpinner.add(spinnerSiglas);
			add(panelTituloSpinner);
			add(panelSpinner);

			// JProgressBar
			JPanel panelTituloProgressBar = new JPanel();
			JPanel panelProgressBar = new JPanel();
			panelTituloProgressBar.add(new JLabel("JProgressBar"));
			JProgressBar progressBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
			progressBar.setValue(75);
			// progressBar.setStringPainted(true);
			progressBar.setString("75%");
			panelProgressBar.add(progressBar);
			add(panelTituloProgressBar);
			add(panelProgressBar);

			// JTextField
			JPanel panelTituloTextField = new JPanel();
			JPanel panelTextField = new JPanel();
			JTextField text1 = new JTextField("hola", 10);
			panelTituloTextField.add(new JLabel("JTextField"));
			panelTextField.add(text1);
			add(panelTituloTextField);
			add(panelTextField);

			// JPasswordField
			JPanel panelTituloPasswordField = new JPanel();
			JPanel panelPasswordField = new JPanel();
			panelTituloPasswordField.add(new JLabel("JPasswordField"));
			panelPasswordField.add(new JPasswordField("Texto", 10));
			add(panelTituloPasswordField);
			add(panelPasswordField);

			// JTextArea
			JPanel panelTituloJTextArea = new JPanel();
			JPanel panelJTextArea = new JPanel();
			panelTituloJTextArea.add(new JLabel("JPasswordField"));
			panelJTextArea.add(new JTextArea("Texto", 2, 10));
			add(panelTituloJTextArea);
			add(panelJTextArea);

			// JEditorPane - Soporta visualización / edición de HTML.
			JPanel panelTituloJEditorPane = new JPanel();
			panelTituloJEditorPane.add(new JLabel("JEditorPane"));
			JEditorPane editorPane = new JEditorPane();
			editorPane.setContentType("text/html");
			editorPane.setText("<b>Universidad de Oriente</b><br><<i>Núcleo Anzoátegui</i><br><img src=\"file:img/udoIcono.png\"></img><br><font face=\\\"arial\\\">JEditorPane</font>");
			JScrollPane scrollEditorPane = new JScrollPane(editorPane); // JScrollPane
			add(panelTituloJEditorPane);
			add(scrollEditorPane);

			// JTextPane - es una extensión de JEditorPane que proporciona
			// funciones de procesamiento de texto como fuentes, estilos de
			// texto, colores, etc.
			JPanel panelTituloJTextPane = new JPanel();
			panelTituloJTextPane.add(new JLabel("JTextPane"));
			JTextPane textPane = new JTextPane();
			SimpleAttributeSet attrs = new SimpleAttributeSet();
			StyleConstants.setBold(attrs, true);
			StyleConstants.setBackground(attrs, Color.CYAN);
			try {
				textPane.getStyledDocument().insertString(textPane.getStyledDocument().getLength(), "Negrita", attrs);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			add(panelTituloJTextPane);
			add(textPane);

			// JPopupMenu
			JPanel panelTituloJPopupMenu = new JPanel();
			panelTituloJPopupMenu.add(new JLabel("JPopupMenu"));
			JPopupMenu popupMenu = new JPopupMenu();
			popupMenu.add(new JMenuItem("Opción 1"));
			popupMenu.add(new JMenuItem("Opción 2"));
			JButton botonPopup = new JButton("Presiona aquí");
			botonPopup.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					popupMenu.show(Ventana.this, e.getX(), e.getY() + botonPopup.getY() + botonPopup.getHeight());
				}
			});
			add(panelTituloJPopupMenu);
			add(botonPopup);

			// Menu y Separator
			JMenuBar barraMenu = new JMenuBar();
			JMenu menu1 = new JMenu("Menú 1");
			menu1.add(new JMenuItem("Una opción de texto"));
			menu1.add(new JSeparator());
			ButtonGroup grupo = new ButtonGroup();
			JRadioButtonMenuItem radio1 = new JRadioButtonMenuItem("Opción 1");
			JRadioButtonMenuItem radio2 = new JRadioButtonMenuItem("Opción 2");
			grupo.add(radio1);
			grupo.add(radio2);
			menu1.add(radio1);
			menu1.add(radio2);
			menu1.add(new JSeparator());
			menu1.add(new JCheckBoxMenuItem("Selección 1", true));
			menu1.add(new JCheckBoxMenuItem("Selección 2"));
			JMenu subMenu = new JMenu("Submenú");
			subMenu.add(new JMenuItem("Opción 1"));
			subMenu.add(new JMenuItem("Opción 2"));
			menu1.add(subMenu);
			barraMenu.add(menu1);
			barraMenu.add(new JMenu("Menú 2"));
			setJMenuBar(barraMenu);

			// JOptionPane
			JPanel panelTituloJOptionPane = new JPanel();
			JPanel panelJOptionPane = new JPanel();
			panelTituloJOptionPane.add(new JLabel("JOptionPane"));
			JButton boton1 = new JButton("1");
			JButton boton2 = new JButton("2");
			JButton boton3 = new JButton("3");
			JButton boton4 = new JButton("4");
			boton1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(LaminaComponentes.this, "Mensaje de prueba", "Advertencia", JOptionPane.CANCEL_OPTION);
				}
			});
			boton2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String entrada = JOptionPane.showInputDialog(LaminaComponentes.this, "Introduce nombre", "Datos", JOptionPane.INFORMATION_MESSAGE);
					System.out.println("Has introducido " + entrada);
				}
			});
			boton3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int opc = JOptionPane.showConfirmDialog(LaminaComponentes.this, "Estás seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
					if (opc == 0) {
						System.out.println("Proceder a ejecutar");
					} else if (opc == 1) {
						System.out.println("No ejecutar");
					}
				}
			});
			boton4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showOptionDialog(LaminaComponentes.this, "JOptionPane más personalizado", "Opciones", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
				}
			});
			panelJOptionPane.add(boton1);
			panelJOptionPane.add(boton2);
			panelJOptionPane.add(boton3);
			panelJOptionPane.add(boton4);
			add(panelTituloJOptionPane);
			add(panelJOptionPane);
		}
	}

}

