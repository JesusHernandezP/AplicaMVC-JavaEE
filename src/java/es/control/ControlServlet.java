package es.control;

import es.modelo.Articulo;
import es.modelo.ArticuloDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Controlador MVC con seguridad JAAS.
 *
 * @author Jesus Hernandez
 */
@WebServlet(name = "ControlServlet", urlPatterns = {"/ControlServlet"})
public class ControlServlet extends HttpServlet {

    private ArticuloDAO articuloDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.articuloDAO = new ArticuloDAO();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");

        // Variables para los datos del formulario
        String idStr = request.getParameter("id");
        String categoria = request.getParameter("categoria");
        String descripcion = request.getParameter("descripcion");
        String precioStr = request.getParameter("precio");

        if (accion == null) {
            response.sendRedirect("index.jsp");
        } else if (accion.equals("Alta")) {
            // --- Solo admin puede dar de Alta ---
            if (!request.isUserInRole("admin")) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "No tienes permisos de Administrador para Alta");
                return;
            }

            System.out.println("Acción ALTA detectada.");
            try {
                int id = Integer.parseInt(idStr);
                // Reemplazar coma por punto para evitar errores
                double precio = Double.parseDouble(precioStr.replace(",", "."));

                Articulo art = new Articulo(id, categoria, descripcion, precio);

                if (articuloDAO.insertar(art)) {
                    System.out.println("Articulo insertado con éxito.");
                } else {
                    System.out.println("Error al insertar (posible ID duplicado).");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error de formato en ID o Precio.");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            response.sendRedirect("index.jsp");

        } else if (accion.equals("Baja")) {
            // --- Solo admin puede borrar ---
            if (!request.isUserInRole("admin")) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "No tienes permisos de Administrador para borrar");
                return;
            }

            System.out.println("Acción BAJA detectada. Usuario autorizado: " + request.getRemoteUser());
            try {
                int id = Integer.parseInt(idStr);
                if (articuloDAO.borrar(id)) {
                    System.out.println("Artículo eliminado.");
                } else {
                    System.out.println("No se pudo borrar el artículo.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            response.sendRedirect("index.jsp");

        } else if (accion.equals("Edicion")) {
            // --- Solo admin puede Editar (Esto faltaba) ---
            if (!request.isUserInRole("admin")) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "No tienes permisos de Administrador para editar");
                return;
            }

            System.out.println("Acción EDICION detectada.");
            try {
                int id = Integer.parseInt(idStr);
                double precio = Double.parseDouble(precioStr.replace(",", "."));

                Articulo art = new Articulo(id, categoria, descripcion, precio);

                if (articuloDAO.modificar(art)) {
                    System.out.println("Artículo " + id + " modificado.");
                } else {
                    System.out.println("No se pudo modificar el artículo " + id);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error de formato en ID o Precio para modificar.");
                e.printStackTrace();
            }

            response.sendRedirect("index.jsp");

        } else if (accion.equals("Lista")) {
            // --- Accesible para todos los roles (admin e invitado) ---
            System.out.println("Acción LISTA detectada.");

            List<Articulo> lista = articuloDAO.getTodos();
            request.setAttribute("listaArticulos", lista);

            getServletContext().getRequestDispatcher("/listado.jsp").forward(request, response);
        } else if (accion.equals("Logout")) {
            System.out.println("Acción LOGOUT detectada.");

            try {
                // 1. Cierra la sesión de seguridad del servidor (JAAS)
                request.logout();

                // 2. Invalida la sesión HTTP (borra atributos como mensajes, carritos, etc.)
                if (request.getSession(false) != null) {
                    request.getSession().invalidate();
                }

                System.out.println("Sesión cerrada correctamente.");

            } catch (ServletException e) {
                System.out.println("Error al intentar cerrar sesión: " + e.getMessage());
            }

            // 3. Redirigir al inicio (ahora se verá como "Visita")
            response.sendRedirect("index.jsp");

        } else {
            // Acción desconocida
            response.sendRedirect("index.jsp");

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet Controlador MVC";
    }// </editor-fold>

}
