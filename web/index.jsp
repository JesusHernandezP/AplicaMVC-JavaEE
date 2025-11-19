<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <title>Gesti<ón de Almacén</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/styles.css"/>
    </head>
    <body>
        <div class="container">
            <h1>Aplicación WEB con Java EE y MVC</h1>
            <h2>Gestión de Almacén</h2>
            <%
                String usuario = request.getRemoteUser();
                String rolMostrar = "No Identificado";
                String claseRol = "grey";
                //boolean estaLogueado = false;

                if (usuario != null) {
                   // estaLogueado = true;
                    // Si hay usuario, comprobamos si es admin
                    if (request.isUserInRole("admin")) {
                        rolMostrar = "ADMINISTRADOR";
                        claseRol = "rol-admin";
                    } else {
                        rolMostrar = "INVITADO";
                        claseRol = "rol-invitado";
                    }
                } else {
                    usuario = "Visita (Sin Login)";
                }
            %>

            <div style="margin-bottom: 10px;">
                Usuario actual: <strong><%= usuario%></strong> | 
                Perfil: <span class="<%= claseRol%>"><%= rolMostrar%></span>
            </div>
                      
            <form action="ControlServlet" method="POST">

                <label for="id">Id. Artículo:</label>
                <input type="text" id="id" name="id"><br>

                <label for="categoria">Categoría:</label>
                <input type="text" id="categoria" name="categoria"><br>

                <label for="descripcion">Descripción:</label>
                <input type="text" id="descripcion" name="descripcion"><br>

                <label for="precio">Precio:</label>
                <input type="text" id="precio" name="precio"><br>


                <hr>

                <input type="submit" name="accion" value="Alta">
                <input type="submit" name="accion" value="Baja">
                <input type="submit" name="accion" value="Edicion">
                <input type="submit" name="accion" value="Lista">

            </form>
        </div>
    </body>
</html>
