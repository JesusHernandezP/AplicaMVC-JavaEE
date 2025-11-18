<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Listado de Artículos</title>
    <style>
    body {
        font-family: Arial, Helvetica, sans-serif;
        background-color: #f4f6f8; 
        margin: 20px;
        color: #333;
    }
    .container {
        width: 80%;
        margin: 40px auto;
        padding: 25px;
        background-color: #ffffff;
        border-radius: 10px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
        border: 1px solid #e0e0e0;
    }
    h2 {
        color: #d9534f; 
        border-bottom: 2px solid #f0f0f0;
        padding-bottom: 10px;
        margin-top: 0;
    }
    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
    }
    th, td {
        border-bottom: 1px solid #e0e0e0; 
        padding: 12px 15px;
        text-align: left;
    }
    th {
        background-color: #d9534f; 
        color: white;
        font-size: 15px;
    }
    
  
    tr:nth-child(even) { background-color: #f9f9f9; }
    
    tr:hover { background-color: #f1f1f1; }
    
    td {
        color: #555;
    }
    
    p b { 
        color: #d9534f;
    }

    a {
        display: inline-block;
        margin-top: 20px;
        color: #005c00; 
        text-decoration: none;
        font-weight: bold;
    }
    a:hover {
        text-decoration: underline;
    }
</style>
</head>
<body>
    <div class="container">
        <h2>Listado de Artículos del Almacén</h2>

        <c:if test="${empty listaArticulos}">
            <p><b>No hay artículos en la base de datos.</b></p>
        </c:if>

        
        <c:if test="${not empty listaArticulos}">
            <table>
                <thead>
                    <tr>
                        <th>ID Artículo</th>
                        <th>Categoría</th>
                        <th>Descripción</th>
                        <th>Precio</th>
                    </tr>
                </thead>
                <tbody>
                    
                    <c:forEach items="${listaArticulos}" var="art">
                        <tr>
                            
                            <td><c:out value="${art.id}"/></td>
                            <td><c:out value="${art.categoria}"/></td>
                            <td><c:out value="${art.descripcion}"/></td>
                            <td><c:out value="${art.precio}"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <hr>
        <a href="index.jsp">Volver a la página inicial</a>
        
    </div>
</body>
</html>