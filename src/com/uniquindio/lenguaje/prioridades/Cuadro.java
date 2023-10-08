package com.uniquindio.lenguaje.prioridades;

import javax.swing.JLabel;

/**
 * Representa el comportamiento particular de una cuadro con prioridad
 * 
 * @author Jhonnatan Osorio
 * @version 2.0
 */
public class Cuadro extends JLabel implements Runnable {

	private static final long serialVersionUID = 1L;
	private int x, y;
	private static int ANCHO = 50;
	private int velX;
	private boolean ejecutar;
	private int prioridad;
	private InterfazG interfaz;

	/**
	 * Inicializa los atributo generales del cuadro
	 * 
	 * @param x
	 *            posicion en x
	 * @param y
	 *            posicion en y
	 * @param prioridad
	 *            prioridad del cuadro
	 * @param interfaz
	 *            instancia que contiene los cuadros
	 */
	public Cuadro(int x, int y, int prioridad, InterfazG interfaz) {
		super();

		setSize(ANCHO, ANCHO);
		setOpaque(true);
		setLocation(x, y);
		setText(String.valueOf(prioridad));

		this.interfaz = interfaz;
		this.prioridad = prioridad;
		this.x = x;
		this.y = y;
		ejecutar = true;
		velX = 2;
	}

	/**
	 * realiza el movimiento del hilo de dos pixeles
	 */
	@Override
	public void run() {
		long minutero = System.currentTimeMillis();

		while (ejecutar) {

			if (System.currentTimeMillis() - minutero > 20) {
				minutero += 20;
				x += velX;
				setLocation(x, y);
				if (x >= InterfazG.ANCHO_PANTALLA - ANCHO) {
					ejecutar = false;
					prioridad = -1;
					interfaz.administrarHilos();
				}

			}

		}

	}

	/**
	 * Devuelve la prioridad del hilo
	 * @return prioridad del hilo
	 */
	public int getPrioridad() {
		return prioridad;
	}

	/**
	 * permite teminar el hilo
	 * @param ejecutar cambios de estando de ejecución
	 */
	public void setEjecutar(boolean ejecutar) {
		this.ejecutar = ejecutar;
	}

	/**
	 * Permite obtener el estado de ejecución
	 * @return the ejecutar
	 */
	public boolean getEjecutar() {
		return ejecutar;
	}

}
