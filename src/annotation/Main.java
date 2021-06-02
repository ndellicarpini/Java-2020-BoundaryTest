package annotation;

import java.awt.EventQueue;

import annotation.boundary.App;
import annotation.controller.ModelController;
import annotation.model.AppModel;

public class Main {
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		AppModel model = new AppModel();
		new ModelController(model).loadSavedModels();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App frame = new App(model);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}
}
