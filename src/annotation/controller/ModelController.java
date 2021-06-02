package annotation.controller;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTextArea;

import annotation.boundary.PopupWarn;
import annotation.model.AnnotationModel;
import annotation.model.AppModel;

public class ModelController {
	AppModel model;
	
	public ModelController(AppModel model) {
		this.model = model;
	}
	
	public AnnotationModel annotationFromID(int id) {
		ArrayList<AnnotationModel> list = this.model.getAnnotations();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getID() == id) {
				return list.get(i);
			}
		}
		
		return new AnnotationModel(-1, new Point(-1, -1));
	}
	public int numAnnotations() {
		return this.model.getAnnotations().size();
	}
	
	public int newAnnotationID() {
		int retID = -1;
		int currID = -1;
				
		ArrayList<AnnotationModel> list = this.model.getAnnotations();
		for (int i = 0; i < list.size(); i++) {
			currID = list.get(i).getID();
			
			if (currID > retID) {
				retID = currID;
			}
		}
		
		return (retID + 1);
	}
	
	public AnnotationModel getAnnotation(int index) {
		return this.model.getAnnotations().get(index);
	}
	
	public void addAnnotation(AnnotationModel anno) {
		ArrayList<AnnotationModel> list = this.model.getAnnotations();
		list.add(anno);
		
		this.model.setAnnotations(list);
	}
	
	public void updateAnnotation(AnnotationModel anno) {
		ArrayList<AnnotationModel> list = this.model.getAnnotations();
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getID() == anno.getID()) {
				list.set(i, anno);
				
				break;
			}
		}
		
		this.model.setAnnotations(list);
	}
	
	public void selectAnnotation(AnnotationModel anno, int index, JButton delAnno, JTextArea fieldAnno) {
		this.deselectAnnotation(delAnno, fieldAnno, true);
		
		ArrayList<AnnotationModel> list = this.model.getAnnotations();
		
		int newIndex = index;
		
		if (newIndex < 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getID() == anno.getID()) {
					newIndex = i;
					
					break;
				}
			}
		}
		
		list.set(newIndex, anno);
		
		AnnotationModel selectedAnno = anno;
		
		selectedAnno.setColor(Color.red);
		
		new BoundController(this.model, fieldAnno).updateFieldFromAnno(selectedAnno.getAnnotation());
		
		this.model.select(selectedAnno);
		this.model.setAnnotations(list);
		
		delAnno.setEnabled(true);
		
	}
	
	public void deselectAnnotation(JButton delAnno, JTextArea fieldAnno, boolean resetField) {
		AnnotationModel selectedAnno = this.model.getSelected();
		
		if (resetField) {
			this.model.setCheat(true);
			new BoundController(this.model, fieldAnno).updateFieldFromAnno("");
		}
		
		if (selectedAnno != null) {
			selectedAnno.setColor(Color.black);
		
			this.model.deselect();
			this.updateAnnotation(selectedAnno);
			
			delAnno.setEnabled(false);
		}
	}
	
	public ArrayList<AppModel> updateLoadedModels(AppModel updatedModel, boolean updateModel) {
		ArrayList<AppModel> list = this.model.getLoadedModels();
		boolean foundModel = false;
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getName().equals(updatedModel.getName())) {
				list.set(i, new AppModel(updatedModel.getName(), updatedModel.getAnnotations()));
				foundModel = true;
				break;
			}
		}
		
		if (!foundModel) {
			list.add(new AppModel(updatedModel.getName(), updatedModel.getAnnotations()));
		}
		
		if (updateModel) {
			this.model.loadModels(list);
		}
		
		return list;
	}
	
	public boolean isEmpty() {
		return (this.model.getName() != null)? false : true;
	}
	
	public void saveModel(boolean popup) {
		try {
			FileOutputStream fos = new FileOutputStream(new File("annotation_models.array"));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(this.updateLoadedModels(this.model, false));
			oos.close();
			fos.close();
			
			if (popup) {
				new PopupWarn("'annotation_models.array' saved successfully");
			}
		}
		catch (FileNotFoundException e) {
			new PopupWarn("ERROR: File not found??");
			System.out.println(e);
		}
		catch (IOException e) {
			new PopupWarn("ERROR: IOException trying to save model");
			System.out.println(e);
		}
		
	}
	
	public void loadModel(String imageName) {
		ArrayList<AppModel> loadedModels = this.model.getLoadedModels();
		
		for (int i = 0; i < loadedModels.size(); i++) {
			
			if (imageName.equals(loadedModels.get(i).getName())) {
				this.model.setName(loadedModels.get(i).getName());
				this.model.setAnnotations(loadedModels.get(i).getAnnotations());
				break;
			}
		}
	}
	
	public void loadSavedModels() {
		try {
			File annoFile = new File("annotation_models.array");
			if (annoFile.exists()) {
				FileInputStream fis = new FileInputStream(annoFile);
				ObjectInputStream ois = new ObjectInputStream(fis);
				
				this.model.loadModels((ArrayList<AppModel>) ois.readObject());
			}
		}
		catch (ClassNotFoundException e) {
			new PopupWarn("ERROR: AppModel doesn't exist??");
			System.out.println(e);
		}
		catch (IOException e) {
			new PopupWarn("ERROR: IOException trying to load model");
			System.out.println(e);
		}
	}
	
}
