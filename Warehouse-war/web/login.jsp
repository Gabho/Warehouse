<%-- 
    Document   : login
    Created on : May 10, 2012, 6:05:29 PM
    Author     : kopytko
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Warehouse</title>
    </head>
    <body>
        <p> <h3>Please enter your user name and
        password</h3></p>
        <form>
            Username:<input type ='text' name ="name"><br>
            Password:<input type ='password' name ="pass"><br>
            <input type ='submit' value="submit" >
            <jsp:useBean class="aplicationcontrol.UserData" id="mb1" />
            <jsp:setProperty name="mb1" property="*"/>
            <% mb1.check(); %>
            <% if( mb1.getName() != null)
            {
            String name = request.getParameter("name");
            String pass = mb1.getPass();
            int rights = mb1.getRights();
            session.setAttribute("name",name);
            session.setAttribute("pass",pass);
            session.setAttribute("rights",rights);
            %>
            
            <jsp:getProperty name = "mb1" property = "rights"/>
             <jsp:forward page = "choice.jsp">
                    
            <jsp:param name="rights" value="${mb1.getRights()}"></jsp:param>
                </jsp:forward>
            
            
            
            <%
            }
            %>
            
        </form>
            <a href="search.jsp">Search</a>
    </body>
</html>
