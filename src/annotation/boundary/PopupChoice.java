package annotation.boundary;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import annotation.controller.BoundController;
import annotation.controller.ModelController;
import annotation.model.AppModel;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class PopupChoice extends JDialog {
	
	String text = "If you can see this you only have 24 hours to live.";
	JLabel warning_text;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public PopupChoice(AppModel model, App app, ImagePanel panel, JButton delAnno, JTextArea fieldAnno, String text) {
		this.text = text;
		
		try {
			PopupChoice dialog = new PopupChoice(model, app, panel, delAnno, fieldAnno);
			dialog.setText(this.text);
			dialog.setLocationRelativeTo(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.setResizable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public PopupChoice(AppModel model, App app, ImagePanel panel, JButton delAnno, JTextArea fieldAnno) {
		setBounds(200, 200, 376, 232);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		this.warning_text = new JLabel(this.text);
		this.warning_text.setHorizontalAlignment(SwingConstants.CENTER);
		this.warning_text.setBounds(10, 27, 340, 100);
		contentPanel.add(this.warning_text);
		
		JButton yes_butt = new JButton("Yes");
		yes_butt.setBounds(80, 138, 88, 23);
		
		yes_butt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new BoundController(model, yes_butt).saveFileButt(panel, delAnno, fieldAnno, false);
				dispose();
				app.dispose();
			}
		});
		
		contentPanel.add(yes_butt);
		
		JButton no_butt = new JButton("No");
		no_butt.setBounds(180, 138, 88, 23);
		
		no_butt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				app.dispose();
			}
		});
		
		contentPanel.add(no_butt);
	}
	
	private void setText(String text) {
		this.text = text;
		
		this.warning_text.setText(String.format("<html><body style=\"text-align: center;  text-justify: inter-word;\">%s</body></html>",this.text));
	}
}
