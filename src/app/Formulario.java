package app;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Formulario extends JFrame {

 // Campos
 private JTextField txtNombre, txtCorreo, txtTelefono;
 private JButton btnGuardar, btnEliminar, btnLimpiar;
 private DefaultListModel<Persona> modeloLista;
 private JList<Persona> lista;

 private Persona personaSeleccionada;
 private PersonaDAO dao = new PersonaDAO();

 public Formulario() {

  setTitle("CRUD Personas");
  setSize(520, 520);
  setDefaultCloseOperation(EXIT_ON_CLOSE);
  setLocationRelativeTo(null);
  setLayout(new BorderLayout(10, 10));

  add(crearFormulario(), BorderLayout.NORTH);
  add(crearBotones(), BorderLayout.CENTER);
  add(crearLista(), BorderLayout.SOUTH);

  cargarLista();
 }

 // UI
 private JPanel crearFormulario() {
  JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
  panel.setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createTitledBorder("Datos personales"),
      BorderFactory.createEmptyBorder(10, 10, 10, 10)
  ));

  txtNombre = new JTextField();
  txtCorreo = new JTextField();
  txtTelefono = new JTextField();

  panel.add(new JLabel("Nombre:"));
  panel.add(txtNombre);
  panel.add(new JLabel("Correo:"));
  panel.add(txtCorreo);
  panel.add(new JLabel("Teléfono:"));
  panel.add(txtTelefono);

  return panel;
 }

 private JPanel crearBotones() {
  JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

  btnGuardar = new JButton("Guardar");
  btnEliminar = new JButton("Eliminar");
  btnLimpiar = new JButton("Limpiar");

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
  scroll.setBorder(BorderFactory.createTitledBorder("Registros"));

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
   JOptionPane.showMessageDialog(this, "Nombre y correo son obligatorios");
   return;
  }

  if (personaSeleccionada == null) {
   Persona nueva = new Persona(0, txtNombre.getText(), txtCorreo.getText());
   dao.agregar(nueva);
   System.out.println("[INFO] Registro creado");
  } else {
   personaSeleccionada.setNombre(txtNombre.getText());
   personaSeleccionada.setCorreo(txtCorreo.getText());
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
  btnGuardar.setText("Guardar");
  lista.clearSelection();
 }

 private void cargarSeleccion() {
  personaSeleccionada = lista.getSelectedValue();

  if (personaSeleccionada != null) {
   txtNombre.setText(personaSeleccionada.getNombre());
   txtCorreo.setText(personaSeleccionada.getCorreo());
   btnGuardar.setText("Actualizar");
  }
 }
}