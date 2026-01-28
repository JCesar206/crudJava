package app;
import javax.swing.SwingUtilities;

public class Main {
 public static void main(String[] args) {

  System.out.println("[INFO] Iniciando aplicación CRUD Personas");

  SwingUtilities.invokeLater(() -> {
   new Formulario().setVisible(true);
   System.out.println("[INFO] Ventana principal cargada");
  });
 }
}

