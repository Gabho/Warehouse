<%-- 
    Document   : choice
    Created on : May 9, 2012, 6:57:48 PM
    Author     : Gabo
--%>

<%@page import="aplicationcontrol.UserData"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Choice</title>
    </head>
    <body>
        <jsp:useBean class="aplicationcontrol.UserData" id="mb1" />
        <jsp:useBean class="aplicationcontrol.Authorization" id="mb2" />
        
        <h4>Vyber akciu:</h4>
        <%
            Object rights = session.getAttribute("rights");
            String helper;
            if(mb2.Authorize(1,rights)==true){
               helper = "masterData.jsp";
            } else {
               helper = "choice.jsp";
            }
            %>
            <a href=${helper}>Spravovanie Master Dat</a><br>
        <a href="search.jsp">Vyhľadávanie</a>
        <h2> 
            
            
        
        </h2>
    </body>
</html>
