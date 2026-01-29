package app;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import app.ui.Footer;
import app.util.I18n;

public class Formulario extends JFrame {

 // Campos
 private JTextField txtNombre, txtCorreo, txtTelefono;
 private JButton btnGuardar, btnEliminar, btnLimpiar;
 private DefaultListModel<Persona> modeloLista;
 private JList<Persona> lista;

 private Persona personaSeleccionada;
 private final PersonaDAO dao = new PersonaDAO();

 // Constructor
 public Formulario() {
  
  setTitle(I18n.t("title"));
  setSize(600, 650);
  setDefaultCloseOperation(EXIT_ON_CLOSE);
  setLocationRelativeTo(null);
  setLayout(new BorderLayout(10, 10));
  
  // Menu superior
  JMenuBar menuBar = new JMenuBar();
  
  JMenu menuLang = new JMenu(I18n.t("language"));
  JMenuItem es = new JMenuItem(I18n.t("spanish"));
  JMenuItem en = new JMenuItem(I18n.t("english"));
  
  es.addActionListener(e -> cambiarIdioma("es"));
  en.addActionListener(e -> cambiarIdioma("en"));
  
  menuLang.add(es);
  menuLang.add(en);
  
  // JMenu menuTheme = new JMenu("Tema");
  // JMenuItem light = new JMenuItem("Claro");
  // JMenuItem dark = new JMenuItem("Oscuro");
  
  // light.addActionListener(e -> cambiarTema(false));
  // dark.addActionListener(e -> cambiarTema(true));
  
  // menuTheme.add(light);
  // menuTheme.add(dark);
  
  menuBar.add(menuLang);
  // menuBar.add(menuTheme);
  
  setJMenuBar(menuBar);
  
  // Panel super (formulario)
  add(crearFormulario(), BorderLayout.NORTH);
  // Centro (Botones + Lista)
  JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
  panelCentro.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
  panelCentro.add(crearBotones(), BorderLayout.NORTH);
  panelCentro.add(crearLista(), BorderLayout.CENTER);
  add(panelCentro, BorderLayout.CENTER);
  // Footer abajo
  add(new Footer(), BorderLayout.SOUTH);
  cargarLista();
 }

 // UI
 private JPanel crearFormulario() {
  JPanel panel = new JPanel(new GridLayout(5, 2, 12, 12));
  panel.setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createTitledBorder(I18n.t("data")),
      BorderFactory.createEmptyBorder(15, 15, 15, 15)
  ));
  panel.setBackground(Color.WHITE);
  
  txtNombre = new JTextField();
  txtCorreo = new JTextField();
  txtTelefono = new JTextField();
  
  panel.add(new JLabel(I18n.t("name") + ":"));
  panel.add(txtNombre);
  panel.add(new JLabel(I18n.t("email") + ":"));
  panel.add(txtCorreo);
  panel.add(new JLabel(I18n.t("phone") + ":"));
  panel.add(txtTelefono);
  
  return panel;
 }

 private void cambiarIdioma(String lang) {
 I18n.setLanguage(lang);
 dispose();
 new Formulario().setVisible(true);
 }
 
 private JPanel crearBotones() {
  JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

  btnGuardar = new JButton(I18n.t("save"));
  btnEliminar = new JButton(I18n.t("delete"));
  btnLimpiar = new JButton(I18n.t("clear"));

  btnGuardar.addActionListener(e -> guardar());
  btnEliminar.addActionListener(e -> eliminar());
  btnLimpiar.addActionListener(e -> limpiar());

  panel.add(btnGuardar);
  panel.add(btnEliminar);
  panel.add(btnLimpiar);

  return panel;
 }

 private JScrollPane crearLista() {
  modeloLista = new DefaultListModel<>();
  lista = new JList<>(modeloLista);
  lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

  lista.addListSelectionListener(e -> cargarSeleccion());

  JScrollPane scroll = new JScrollPane(lista);
  scroll.setBorder(BorderFactory.createTitledBorder(I18n.t("registers")));

  return scroll;
 }

 // Lógica
 private void cargarLista() {
  modeloLista.clear();
  List<Persona> personas = dao.obtenerTodas();
  personas.forEach(modeloLista::addElement);
  System.out.println("[INFO] Lista cargada");
 }

 private void guardar() {

  if (txtNombre.getText().isBlank() || txtCorreo.getText().isBlank()) {
   JOptionPane.showMessageDialog(this,
    I18n.t("required"),
    "Warning",
     JOptionPane.WARNING_MESSAGE
    );
   return;
  }

  if (personaSeleccionada == null) {
   Persona p = new Persona(
    0,
    txtNombre.getText(),
    txtCorreo.getText(),
    txtTelefono.getText()
   );
   
   dao.agregar(p);
   System.out.println("[INFO] Registro creado");
  } else {
   personaSeleccionada.setNombre(txtNombre.getText());
   personaSeleccionada.setCorreo(txtCorreo.getText());
   personaSeleccionada.setTelefono(txtTelefono.getText());
   
   dao.actualizar(personaSeleccionada);
   System.out.println("[INFO] Registro actualizado");
  }

  limpiar();
  cargarLista();
 }

 private void eliminar() {
  if (personaSeleccionada != null) {
   dao.eliminar(personaSeleccionada.getId());
   System.out.println("[INFO] Registro eliminado");
   limpiar();
   cargarLista();
  }
 }

 private void limpiar() {
  txtNombre.setText("");
  txtCorreo.setText("");
  txtTelefono.setText("");
  
  personaSeleccionada = null;
  btnGuardar.setText(I18n.t("save"));
  lista.clearSelection();
 }

 private void cargarSeleccion() {
  personaSeleccionada = lista.getSelectedValue();

  if (personaSeleccionada != null) {
   txtNombre.setText(personaSeleccionada.getNombre());
   txtCorreo.setText(personaSeleccionada.getCorreo());
   txtTelefono.setText(personaSeleccionada.getTelefono());
   
   btnGuardar.setText(I18n.t("update"));
  }
 }
}