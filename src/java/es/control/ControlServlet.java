package es.control;

import es.modelo.Articulo;
import es.modelo.ArticuloDAO;
import java.util.List;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * * @author Jesus Hernandez
 */
@WebServlet(name = "ControlServlet", urlPatterns = {"/ControlServlet"})
public class ControlServlet extends HttpServlet {

    private ArticuloDAO articuloDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.articuloDAO = new ArticuloDAO();

    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");

        // Variables para los datos del formulario
        String idStr;
        String categoria;
        String descripcion;
        String precioStr;

        if (accion == null) {
            response.sendRedirect("index.html");

        } else if (accion.equals("Alta")) {
            // Se dió "Alta"
            idStr = request.getParameter("id");
            categoria = request.getParameter("categoria");
            descripcion = request.getParameter("descripcion");
            precioStr = request.getParameter("precio");

            System.out.println("Prueba ALTA. ");

            try {
                int id = Integer.parseInt(idStr);
                double precio = Double.parseDouble(precioStr);

                Articulo art = new Articulo(id, categoria, descripcion, precio);

                boolean exito = articuloDAO.insertar(art);

                if (exito) {
                    System.out.println("Articulo insertado con éxito.");
                } else {
                    System.out.println("Error al insertar el artículo .");
                }

            } catch (NumberFormatException e) {
                System.out.println("Error de formato en ID o Precio.");
                e.printStackTrace();
            }

            // vuelve al index
            response.sendRedirect("index.html");

        } else if (accion.equals("Baja")) {
            // Se dió "Baja"
            idStr = request.getParameter("id");

            System.out.println("Prueba BAJA.");

            try {
                int id = Integer.parseInt(idStr);

                if (articuloDAO.borrar(id)) {
                    System.out.println("Articulo " + id + "Borrado");
                } else {
                    System.out.println("No se encontro el articulo");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error de formato en ID");
                e.printStackTrace();
            }

            // Vuelve al index
            response.sendRedirect("index.html");

        } else if (accion.equals("Edicion")) {
            // Se usó "Edicion"
            idStr = request.getParameter("id");
            categoria = request.getParameter("categoria");
            descripcion = request.getParameter("descripcion");
            precioStr = request.getParameter("precio");

            System.out.println("Prueba EDICION .");

            try {
                //1 Convertir los datos
                int id = Integer.parseInt(idStr);
                double precio = Double.parseDouble(precioStr.replace(",", "."));

                //2Crear el objeto articulo
                Articulo art = new Articulo(id, categoria, descripcion, precio);
                //3 llamar al DAO para modificar
                if (articuloDAO.modificar(art)) {
                    System.out.println("Artículo " + id + " modificado.");

                } else {
                    System.out.println("No se encontró o no se pudo modificar el artículo " + id);
                }

            } catch (NumberFormatException e) {
                System.out.println("Error de formato en ID o Precio para modificar.");
                e.printStackTrace();
            }

            // vuelve al index
            response.sendRedirect("index.html");

        } else if (accion.equals("Lista")) {
            // Se usó "Lista"
            System.out.println("Prueba LISTA .");
            List<Articulo> lista = articuloDAO.getTodos();

            // Esto la hace accesible para el JSP
            request.setAttribute("listaArticulos", lista);

            getServletContext().getRequestDispatcher("/listado.jsp").forward(request, response);

        } else {
            // Acción desconocida
            response.sendRedirect("index.html");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet Controlador MVC";
    }// </editor-fold>

}
