package app;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Formulario extends JFrame {

 // Atributos
 private JTextField txtId, txtNombre, txtCorreo;
 private JButton btnGuardar, btnEliminar, btnLimpiar;
 private DefaultListModel<Persona> modeloLista;
 private JList<Persona> lista;

 private PersonaDAO dao = new PersonaDAO();

 // Constructor
 public Formulario() {
  setTitle("CRUD Personas");
  setSize(400, 400);
  setDefaultCloseOperation(EXIT_ON_CLOSE);
  setLocationRelativeTo(null); // Centrar ventana
  setLayout(new BorderLayout(10, 10));

  // ===== FORMULARIO =====
  JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 10, 10));
  panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos"));

  txtId = new JTextField();
  txtNombre = new JTextField();
  txtCorreo = new JTextField();

  panelFormulario.add(new JLabel("ID:"));
  panelFormulario.add(txtId);
  panelFormulario.add(new JLabel("Nombre:"));
  panelFormulario.add(txtNombre);
  panelFormulario.add(new JLabel("Correo:"));
  panelFormulario.add(txtCorreo);

  add(panelFormulario, BorderLayout.NORTH);

  // ===== BOTONES =====
  JPanel panelBotones = new JPanel(new FlowLayout());

  btnGuardar = new JButton("Guardar");
  btnEliminar = new JButton("Eliminar");
  btnLimpiar = new JButton("Limpiar");

  panelBotones.add(btnGuardar);
  panelBotones.add(btnEliminar);
  panelBotones.add(btnLimpiar);

  add(panelBotones, BorderLayout.CENTER);

  // ===== LISTA =====
  modeloLista = new DefaultListModel<>();
  lista = new JList<>(modeloLista);
  JScrollPane scroll = new JScrollPane(lista);
  scroll.setBorder(BorderFactory.createTitledBorder("Registros"));

  add(scroll, BorderLayout.SOUTH);

  // ===== EVENTOS =====
  btnGuardar.addActionListener(e -> guardar());
  btnEliminar.addActionListener(e -> eliminar());
  btnLimpiar.addActionListener(e -> limpiar());

  lista.addListSelectionListener(e -> cargarSeleccion());

  // Cargar datos
  cargarLista();
 }

 // ===== MÉTODOS =====

 private void cargarLista() {
  modeloLista.clear();
  List<Persona> personas = dao.obtenerTodas();
  personas.forEach(modeloLista::addElement);
  System.out.println("[LOG] Lista cargada");
 }

 private void guardar() {
  int id = Integer.parseInt(txtId.getText());
  String nombre = txtNombre.getText();
  String correo = txtCorreo.getText();

  Persona p = new Persona(id, nombre, correo);

  if (txtId.isEnabled()) {
   dao.agregar(p);
   System.out.println("[LOG] Registro creado");
  } else {
   dao.actualizar(p);
   System.out.println("[LOG] Registro actualizado");
  }

  limpiar();
  cargarLista();
 }

 private void eliminar() {
  Persona p = lista.getSelectedValue();
  if (p != null) {
   dao.eliminar(p.getId());
   System.out.println("[LOG] Registro eliminado");
   limpiar();
   cargarLista();
  }
 }

 private void limpiar() {
  txtId.setText("");
  txtNombre.setText("");
  txtCorreo.setText("");
  txtId.setEnabled(true);
  lista.clearSelection();
 }

 private void cargarSeleccion() {
  Persona p = lista.getSelectedValue();
  if (p != null) {
   txtId.setText(String.valueOf(p.getId()));
   txtNombre.setText(p.getNombre());
   txtCorreo.setText(p.getCorreo());
   txtId.setEnabled(false);
  }
 }
}