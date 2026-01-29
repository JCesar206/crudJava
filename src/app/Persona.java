package app;
import java.io.Serializable;
import java.time.LocalDate;

public class Persona implements Serializable {

 private int id;
 private String nombre;
 private String correo;
 private String telefono;
 private LocalDate fechaRegistro;
 
 public Persona(int id, String nombre, String correo, String telefono) {
  this.id = id;
  this.nombre = nombre;
  this.correo = correo;
  this.telefono = telefono;
  this.fechaRegistro = LocalDate.now();
 }
 // Getters/Setters
 public int getId() {
  return id;
 }
 public void setId(int id) {
  this.id = id;
 }
 public String getNombre() {
  return nombre;
 }
 public void setNombre(String nombre) {
  this.nombre = nombre;
 }
 public String getCorreo() {
  return correo;
 }
 public void setCorreo(String correo) {
  this.correo = correo;
 }
 public String getTelefono() { return telefono; }
 public void setTelefono(String telefono) { this.telefono = telefono; }
 public LocalDate getFechaRegistro() { return fechaRegistro; }
 
 @Override
 public String toString() {
  return nombre + "|" + correo;
 }
}