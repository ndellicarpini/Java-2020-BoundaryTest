package annotation.boundary;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import annotation.controller.BoundController;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class PopupWarn extends JDialog {
	
	String text = "If you can see this you only have 24 hours to live.";
	JLabel warning_text;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public PopupWarn(String text) {
		this.text = text;
		
		try {
			PopupWarn dialog = new PopupWarn();
			dialog.setText(this.text);
			dialog.setLocationRelativeTo(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.setResizable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public PopupWarn() {
		setBounds(200, 200, 376, 232);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		this.warning_text = new JLabel(this.text);
		this.warning_text.setHorizontalAlignment(SwingConstants.CENTER);
		this.warning_text.setBounds(10, 27, 340, 100);
		contentPanel.add(this.warning_text);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.setBounds(137, 138, 88, 23);
		
		btnNewButton.addActionListener(e -> this.dispose());
		
		contentPanel.add(btnNewButton);
	}
	
	private void setText(String text) {
		this.text = text;
		
		this.warning_text.setText(String.format("<html><body style=\"text-align: center;  text-justify: inter-word;\">%s</body></html>",this.text));
	}
}
