package annotation.model;

import java.io.Serializable;
import java.util.ArrayList;

public class AppModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	String name;
	byte[] image = new byte[0];
	ArrayList<AnnotationModel> annotations;
	
	AnnotationModel selectedAnno = null;
	boolean removeAnnoCheat = false;
	
	ArrayList<AppModel> loadedModels;
	
	public AppModel() {
		this.annotations = new ArrayList<AnnotationModel>();
		this.loadedModels = new ArrayList<AppModel>();
	}
	
	public AppModel(String name, ArrayList<AnnotationModel> annotations) {
		this.name = name;
		this.annotations = annotations;
	}
	
	public void setImage(String imgName, byte[] imageBytes) {
		this.name = imgName;
		this.image = imageBytes;
	}
	
	public byte[] getImage() {
		return this.image;
	}
	
	public void setName(String text) {
		this.name = text;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ArrayList<AnnotationModel> getAnnotations() {
		return this.annotations;
	}
	
	public void setAnnotations(ArrayList<AnnotationModel> annotations) {
		this.annotations = annotations;
	}
	
	public void select(AnnotationModel anno) {
		this.selectedAnno = anno;
	}
	
	public void deselect() {
		this.selectedAnno = null;
	}
	
	public AnnotationModel getSelected() {
		return this.selectedAnno;
	}
	
	public void setCheat(boolean cheat) {
		this.removeAnnoCheat = cheat;
	}
	
	public boolean getCheat() {
		return this.removeAnnoCheat;
	}
	
	public void loadModels(ArrayList<AppModel> loaded) {
		this.loadedModels = loaded;
	}
	
	public ArrayList<AppModel> getLoadedModels() {
		return this.loadedModels;
	}
	
	public void setModel(AppModel newModel) {
		this.name = newModel.getName();
		this.image = newModel.getImage();
		this.annotations = newModel.getAnnotations();
	}
	
	public void resetModel() {
		this.name = null;
		this.image = new byte[0];
		this.annotations = new ArrayList<AnnotationModel>();
		this.selectedAnno = null;
		this.removeAnnoCheat = false;
	}
}
