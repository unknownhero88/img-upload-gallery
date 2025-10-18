<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
String imageId = request.getParameter("id");

if (imageId == null || imageId.trim().isEmpty()) {
    response.sendRedirect("gallery.jsp");
    return;
}

String dbURL = "jdbc:mysql://bgveyko0x5lxpoqktzss-mysql.services.clever-cloud.com:3306/bgveyko0x5lxpoqktzss";
String dbUser = "uuaabzyzcnqdvsx5";
String dbPass = "VWxNmmp0txlTcxyTjsYe";

Connection con = null;
PreparedStatement ps = null;
boolean success = false;
String errorMsg = null;

try {
    Class.forName("com.mysql.cj.jdbc.Driver");
    con = DriverManager.getConnection(dbURL, dbUser, dbPass);
    
    String sql = "DELETE FROM images WHERE id = ?";
    ps = con.prepareStatement(sql);
    ps.setInt(1, Integer.parseInt(imageId));
    
    int rowsAffected = ps.executeUpdate();
    success = (rowsAffected > 0);
    
} catch (NumberFormatException e) {
    errorMsg = "Invalid image ID";
} catch (ClassNotFoundException e) {
    errorMsg = "Database driver not found";
} catch (SQLException e) {
    errorMsg = "Database error: " + e.getMessage();
} catch (Exception e) {
    errorMsg = "Error: " + e.getMessage();
} finally {
    try {
        if (ps != null) ps.close();
        if (con != null) con.close();
    } catch (SQLException e) {
        // Ignore
    }
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Delete Image</title>
<style>
body {
    font-family: Arial, sans-serif;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 20px;
}

.message-box {
    background: white;
    padding: 40px;
    border-radius: 15px;
    box-shadow: 0 10px 30px rgba(0,0,0,0.2);
    text-align: center;
    max-width: 400px;
}

.success {
    color: #28a745;
    font-size: 3rem;
}

.error {
    color: #dc3545;
    font-size: 3rem;
}

h2 {
    margin: 20px 0;
    color: #333;
}

p {
    color: #666;
    margin-bottom: 25px;
}

.btn {
    display: inline-block;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: white;
    padding: 12px 30px;
    border-radius: 25px;
    text-decoration: none;
    font-weight: bold;
    transition: transform 0.3s;
}

.btn:hover {
    transform: translateY(-2px);
}

.redirect-msg {
    font-size: 14px;
    color: #999;
    margin-top: 20px;
}
</style>
<% if (success) { %>
<script>
    setTimeout(function() {
        window.location.href = 'show.jsp';
    }, 2000);
</script>
<% } %>
</head>
<body>
<div class="message-box">
    <% if (success) { %>
        <div class="success">✓</div>
        <h2>Image Deleted Successfully!</h2>
        <p>The image has been removed from the gallery.</p>
        <p class="redirect-msg">Redirecting to gallery in 2 seconds...</p>
    <% } else { %>
        <div class="error">✗</div>
        <h2>Delete Failed!</h2>
        <p><%= errorMsg != null ? errorMsg : "Unable to delete the image." %></p>
    <% } %>
    <a href="gallery.jsp" class="btn">Back to Gallery</a>
</div>
</body>
</html>