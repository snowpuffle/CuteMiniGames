package models;

import views.ViewFactory;

public class Model {

	private static Model model;
	private final ViewFactory viewFactory;

	// Default Class Constructor
	public Model() {
		// Set View Factory and DAOs
		this.viewFactory = new ViewFactory();
	}

	// Get Database Instance
	public static synchronized Model getInstance() {
		if (model == null) {
			model = new Model();
		}
		return model;
	}

	// Get View Factory
	public ViewFactory getViewFactory() {
		return viewFactory;
	}
}