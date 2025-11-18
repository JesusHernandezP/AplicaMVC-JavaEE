<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <title>Gestión de Almacén</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            body {
                font-family: Arial, Helvetica, sans-serif;
                background-color: #f4f6f8; 
                margin: 20px;
                color: #333;
            }
            .container {
                width: 500px;
                margin: 40px auto;
                padding: 25px;
                background-color: #ffffff; 
                border-radius: 10px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08); 
                border: 1px solid #e0e0e0; 
            }
            h1 {
                color: #005c00; 
                margin-top: 0;
            }
            h2 {
                color: #d9534f; 
                border-bottom: 2px solid #f0f0f0;
                padding-bottom: 10px;
            }
            form label {
                display: inline-block;
                width: 100px;
                margin-bottom: 15px; 
                font-weight: 600;
                color: #555;
            }
            form input[type="text"] {
                width: 250px; 
                padding: 9px;
                margin-bottom: 10px;
                border: 1px solid #ccc;
                border-radius: 5px; 
                box-sizing: border-box; 
            }

            hr {
                border: none;
                border-top: 2px solid #f0f0f0;
                margin-top: 15px;
                margin-bottom: 20px;
            }

            form input[type="submit"] {
                margin-right: 10px; 
                padding: 10px 16px;
                background-color: #333; 
                color: white; 
                border: none;
                cursor: pointer;
                border-radius: 5px; 
                font-weight: bold;
                font-size: 14px;
                transition: background-color 0.2s ease-in-out, transform 0.1s ease;
            }

            form input[type="submit"]:hover {
                background-color: #005c00; 
            }

            form input[type="submit"]:active {
                transform: scale(0.98); 
            }
            /* Estilos para el panel de usuario */
            .panel-usuario {
                text-align: center;
                margin-bottom: 20px;
                padding: 15px;
                background-color: #ffffff;
                border: 1px solid #e0e0e0;
                border-radius: 8px; 
                box-shadow: 0 2px 5px rgba(0,0,0,0.05); 
            }


            .rol-admin {
                color: #005c00; 
                font-weight: bold;
            }

            .rol-invitado {
                color: #d9534f; 
                font-weight: bold;
            }

            .rol-desconocido {
                color: #777; /* Gris */
                font-style: italic;
            }

        </style>
    </head>
    <body>
        <div class="container">
            <h1>Aplicación WEB con Java EE y MVC</h1>
            <h2>Gestión de Almacén</h2>
            <%
                String usuario = request.getRemoteUser();
                String rolMostrar = "No Identificado";
                String claseRol = "grey";

                if (usuario != null) {
                    // Si hay usuario, comprobamos si es admin
                    if (request.isUserInRole("admin")) {
                        rolMostrar = "ADMINISTRADOR";
                        claseRol = "rol-admin"; // Rojo para el jefe
                    } else {
                        rolMostrar = "INVITADO";
                        claseRol = "rol-invitado"; // Azul para el usuario normal
                    }
                } else {
                    usuario = "Visita (Sin Login)";
                }
            %>
            <div class="panel-usuario">
                Usuario actual: <strong><%= usuario%></strong> <br>
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
