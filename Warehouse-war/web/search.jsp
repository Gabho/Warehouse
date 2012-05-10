<%-- 
    Document   : Search
    Created on : May 8, 2012, 1:48:27 PM
    Author     : Gabo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    
        
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <p> <h3><center>Please enter your user name and
        password</center></h3></p>
        <form>
            <center>Username</center>
            <center><input type ='text' name ="name"></center>
            <center>Password</center>
            <center><input type ='password' name ="pass"></center>
            <center><input type ='submit' value="submit" ></center>
            <jsp:useBean class="aplicationcontrol.UserData" id="mb1" />
            <jsp:setProperty name="mb1" property="*"/>
            <% if( request.getParameter("name") !=null)
            {
            %>
            <h2>Name is: <jsp:getProperty name="mb1" property="name" /></h2>
            <h2>Password is: <jsp:getProperty name="mb1" property="pass" /></h2>
            <jsp:forward page = "index.jsp">
                <jsp:param name="name" value="name"></jsp:param>
            </jsp:forward>
            <%
            }
            %>
        </form>
        <h2>SEARCH</h2>
        
        <form method="POST" action="search">
            Search: <input type="text" name="searchString" /><br>
            <input type="submit" value="Search" />
        </form>
        
        <h2><%
        @SuppressWarnings("unchecked")
        String quantity = (String)request.getAttribute("quantity");
        if(quantity != null){%>Quantity: <%= quantity%><%}
        %></h2>
        
    </body>
</html>
