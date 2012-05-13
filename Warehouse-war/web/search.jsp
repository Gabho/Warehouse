<%-- 
    Document   : Search
    Created on : May 8, 2012, 1:48:27 PM
    Author     : Gabo
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    
        
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <h2>SEARCH</h2>
        
        <form method="POST" action="search">
            Search: <input type="text" name="searchString" /><br>
            <input type="submit" value="Search" />
        </form>
        <br>
        <hr><ol><%
        @SuppressWarnings("unchecked")
        List<String> resultList = (List<String>)request.getAttribute("searchresult");
        if(resultList != null){
            for(String row: resultList){ %>
                <li> <%= row %> </li> <%
            }
        }
        %></ol><hr>
        
        <a href="choice.jsp">Back</a>
        
    </body>
</html>
