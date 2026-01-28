package app;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {

 private static final String ARCHIVO = "personas.dat";

 // Utilidades
 private List<Persona> leerArchivo() {
  File file = new File(ARCHIVO);

  if (!file.exists()) {
   System.out.println("[INFO] Archivo no encontrado, creando lista vacía");
   return new ArrayList<>();
  }

  try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
   return (List<Persona>) ois.readObject();
  } catch (Exception e) {
   System.out.println("[ERROR] Error al leer archivo: " + e.getMessage());
   return new ArrayList<>();
  }
 }

 private void escribirArchivo(List<Persona> personas) {
  try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
   oos.writeObject(personas);
   System.out.println("[INFO] Archivo guardado (" + personas.size() + " registros)");
  } catch (IOException e) {
   System.out.println("[ERROR] Error al guardar archivo: " + e.getMessage());
  }
 }

 private int generarNuevoId(List<Persona> personas) {
  return personas.stream()
      .mapToInt(Persona::getId)
      .max()
      .orElse(0) + 1;
 }

 // CRUD
 public List<Persona> obtenerTodas() {
  List<Persona> personas = leerArchivo();
  System.out.println("[INFO] Registros cargados: " + personas.size());
  return personas;
 }

 public void agregar(Persona persona) {
  List<Persona> personas = leerArchivo();
  int nuevoId = generarNuevoId(personas);
  persona.setId(nuevoId);
  personas.add(persona);

  escribirArchivo(personas);
  System.out.println("[INFO] Persona agregada con ID " + nuevoId);
 }

 public void actualizar(Persona personaActualizada) {
  List<Persona> personas = leerArchivo();

  for (Persona p : personas) {
   if (p.getId() == personaActualizada.getId()) {
    p.setNombre(personaActualizada.getNombre());
    p.setCorreo(personaActualizada.getCorreo());
    System.out.println("[INFO] Persona actualizada ID " + p.getId());
    break;
   }
  }

  escribirArchivo(personas);
 }

 public void eliminar(int id) {
  List<Persona> personas = leerArchivo();
  boolean eliminado = personas.removeIf(p -> p.getId() == id);

  if (eliminado) {
   System.out.println("[INFO] Persona eliminada ID " + id);
  } else {
   System.out.println("[WARN] No se encontró persona con ID " + id);
  }

  escribirArchivo(personas);
 }
}