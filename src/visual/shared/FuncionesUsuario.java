package visual.shared;

public class FuncionesUsuario {

  public static boolean controlarInicioSesion(String usuario, String password){
    boolean valorDevolucion;
    if (usuario == null || password == null) valorDevolucion = false;
    else{
      int comparaUsuario = usuario.compareToIgnoreCase("Administrador");
      int comparaPassw = password.compareTo("admin");
      int resultadoCompara = comparaUsuario + comparaPassw;
      valorDevolucion = (resultadoCompara == 0);
    }
    return valorDevolucion;
  }
}
