package annotation.boundary;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;

import annotation.controller.BoundController;
import annotation.controller.ModelController;
import annotation.controller.MouseController;
import annotation.model.AppModel;

import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

public class App extends JFrame {

	JPanel contentPane;
	private static final long serialVersionUID = 1L;

	public App(AppModel model) {
		setBounds(100, 100, 960, 720);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel control_panel = new JPanel();
		control_panel.setBounds(720, 11, 214, 659);
		contentPane.add(control_panel);
		control_panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Image Annotations");
		lblNewLabel.setBounds(10, 10, 193, 25);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		control_panel.add(lblNewLabel);
		
		JLabel img_name_txt = new JLabel("No image file loaded");
		img_name_txt.setHorizontalAlignment(SwingConstants.CENTER);
		img_name_txt.setFont(new Font("Tahoma", Font.ITALIC, 11));
		img_name_txt.setBounds(10, 60, 193, 14);
		control_panel.add(img_name_txt);
		
		ImagePanel image_panel = new ImagePanel(model);
		image_panel.setBounds(10, 11, 700, 659);
		
		JTextArea annotation_field = new JTextArea();
		annotation_field.setBounds(4, 178, 206, 350);
		annotation_field.setLineWrap(true);
		
		annotation_field.getDocument().addDocumentListener(
				new BoundController(model, annotation_field).annoFieldListener());
		
		control_panel.add(annotation_field);
		
		JButton del_annotation_butt = new JButton("Remove Annotation");
		del_annotation_butt.setEnabled(false);
		del_annotation_butt.setBounds(4, 528, 206, 23);
		
		del_annotation_butt.addActionListener(
				new BoundController(model, del_annotation_butt).annoDelListener(image_panel, annotation_field));
		
		control_panel.add(del_annotation_butt);
		
		JButton load_img_butt = new JButton("Load Image File");
		load_img_butt.setBounds(28, 82, 160, 23);
		
		load_img_butt.addActionListener(
				new BoundController(model, load_img_butt).loadImgListener(image_panel, img_name_txt, del_annotation_butt, annotation_field, null));
		
		control_panel.add(load_img_butt);
		
		JButton save_save_butt = new JButton("Save Model Array File");
		save_save_butt.setBounds(28, 130, 160, 23);
		
		save_save_butt.addActionListener(
				new BoundController(model, save_save_butt).saveModelListener(image_panel, del_annotation_butt, annotation_field));
		
		control_panel.add(save_save_butt);
		
		MouseAdapter mouseAdapter = new BoundController(model, null).mouseListener(image_panel, del_annotation_butt, annotation_field);
		
		image_panel.addMouseListener(mouseAdapter);
		image_panel.addMouseMotionListener(mouseAdapter);
		
		contentPane.add(image_panel);
		
		App self = this;
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (!new ModelController(model).isEmpty()) {
					String text = "Do you want to save 'annotation_models.array'?";
					new PopupChoice(model, self, image_panel, del_annotation_butt, annotation_field, text);
				}
				else {
					dispose();
				}
			}
		});
	}
}
