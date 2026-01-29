package app.ui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Footer extends JPanel {
	public Footer() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
		setBackground(new Color(245, 245, 245));
		// Iconos
		JPanel panelIcons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		panelIcons.setOpaque(false);
		
		panelIcons.add(icon("/icons/github.png", "https://github.com/JCesar206"));
		panelIcons.add(icon("/icons/linkedin.png", "https://www.linkedin.com/in/jcesar206"));
		panelIcons.add(icon("/icons/hotmail.png", "mailto:jcesar206@hotmail.com"));
		panelIcons.add(icon("/icons/gmail.png", "mailto:jcesary06@gmail.com"));
		
		// Copyright
		JLabel lblCopytight = new JLabel("© 2026 Julio | Java Desktop App");
		lblCopytight.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblCopytight.setForeground(Color.DARK_GRAY);
		
		add(panelIcons, BorderLayout.WEST);
		add(lblCopytight, BorderLayout.EAST);
	}
	
	// Metodo seguro para iconos
	private JLabel icon(String path, String tooltip) {
		URL location = getClass().getResource(path);
		
		if (location == null) {
			System.out.println("[ERROR] Icono no encontrado: " + path);
			JLabel error = new JLabel("?");
			error.setToolTipText("Icono faltante: " + path);
			return error;
		}
		
		ImageIcon icon = new ImageIcon(location);
		JLabel label = new JLabel(icon);
		label.setToolTipText(tooltip);
		label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		return label;
	}
}