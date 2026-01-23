package app;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {

 private final String ARCHIVO = "personas.dat";

 public List<Persona> obtenerTodas() {
  try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO))) {
   System.out.println("[LOG] Leyendo archivo...");
   return (List<Persona>) ois.readObject();
  } catch (Exception e) {
   System.out.println("[LOG] Archivo no existe, lista nueva");
   return new ArrayList<>();
  }
 }

 private void guardar(List<Persona> personas) {
  try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
   oos.writeObject(personas);
   System.out.println("[LOG] Datos guardados");
  } catch (IOException e) {
   System.out.println("[ERROR] No se pudo guardar");
  }
 }

 public void agregar(Persona p) {
  List<Persona> personas = obtenerTodas();
  personas.add(p);
  guardar(personas);
 }

 public void actualizar(Persona personaActualizada) {
  List<Persona> personas = obtenerTodas();

  for (Persona p : personas) {
   if (p.getId() == personaActualizada.getId()) {
    p.setNombre(personaActualizada.getNombre());
    p.setCorreo(personaActualizada.getCorreo());
    System.out.print("[LOG] Persona actualizada");
    break;
   }
  }
  guardar(personas);
 }

 public void eliminar(int id) {
  List<Persona> personas = obtenerTodas();
  personas.removeIf(p -> p.getId() == id);
  guardar(personas);
 }
}

