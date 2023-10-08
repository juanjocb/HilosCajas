package com.uniquindio.lenguaje.prioridades;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Esta clase se encarga de mostrar el comportamiento de la clase cuadrado
 * 
 * @author Jhonnatan Osorio
 * @version 2.0
 */
public class InterfazG extends JFrame {

	private static final long serialVersionUID = 1L;
	public static int ANCHO_PANTALLA = 400;
	public static int ALTO_PANTALLA = 330;
	private ArrayList<Cuadro> cuadros;
	private ExecutorService service;

	/**
	 * inicializa la información basica de la aplicacion
	 */
	public InterfazG() {

		setLayout(null);
		setSize(new Dimension(ANCHO_PANTALLA, ALTO_PANTALLA));

		service = Executors.newFixedThreadPool(5);
		// service = Executors.newCachedThreadPool();

		cuadros = new ArrayList<>();

		JButton btnAgregarCuadro = new JButton("Agregar Cuadro");
		btnAgregarCuadro.setBounds(ANCHO_PANTALLA - 150, 270, 150, 30);
		btnAgregarCuadro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				agregarCuadro();
				administrarHilos();
			}
		});
		add(btnAgregarCuadro);

	}

	/**
	 * Agrega uncuadro con una prioridad aleatoria
	 */
	public void agregarCuadro() {

		int ale = (int) (Math.random() * 3) + 1;
		int prioridad = Thread.MIN_PRIORITY;
		Color color = Color.GREEN;
		int x = 0, y = 200;

		if (ale == 2) {
			prioridad = Thread.NORM_PRIORITY;
			color = Color.YELLOW;
			y = 100;
		} else if (ale == 3) {
			prioridad = Thread.MAX_PRIORITY;
			color = Color.RED;
			y = 0;
		}

		Cuadro c = new Cuadro(x, y, prioridad, this);
		c.setBackground(color);
		cuadros.add(c);
		add(c);

		service.execute(c);

	}

	/**
	 * Permite administrar el moviento de los hilos, tomando en cuenta que se
	 * ejecutan primero los de mayor prioridad
	 */
	public void administrarHilos() {

		int maxPrioridad = obtenerMaximaPrioridad();

		for (int i = 0; i < cuadros.size(); i++) {

			if (cuadros.get(i).getPrioridad() == maxPrioridad) {

				if (!cuadros.get(i).getEjecutar()) {
					cuadros.get(i).setEjecutar(true);
					service.execute(cuadros.get(i));
				}

			}else {
				cuadros.get(i).setEjecutar(false);
			}

		}

	}

	/**
	 * Define cual es la prioridad mayor del momento
	 * 
	 * @return devuelve la mayor prioridad
	 */
	private int obtenerMaximaPrioridad() {
		int max = 1;
		for (int i = 0; i < cuadros.size(); i++) {

			if (max < cuadros.get(i).getPrioridad()) {
				max = cuadros.get(i).getPrioridad();
			}
		}
		return max;
	}

}
