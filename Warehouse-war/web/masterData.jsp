<%-- 
    Document   : masterData
    Created on : May 9, 2012, 6:57:21 PM
    Author     : Gabo
--%>

<%@page import="javax.naming.InitialContext"%>
<%@page import="java.util.List"%>
<%@page import="persistence.MasterDataEntity"%>
<%@page import="persistence.Database"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Master Data Management</title>
    </head>
    <body>
        
        <b>Insert Master Data</b>
        <form method="POST" action="masterData">
            Name:        <input type="text" name="name" /><br>
            Description: <input type="text" name="description" /><br>
            <input type="submit" name="command" value="Insert"/>
        <br>
        <br>
        
        <b>Remove Master Data</b><br>
        <select name="selectMD" id="selectMD">
                <%
                    @SuppressWarnings("unchecked")
                    Database database = (Database) new InitialContext().lookup("java:global/Warehouse/Warehouse-ejb/Database");
                    List<MasterDataEntity> masterData = database.getMasterData();
                    if (masterData != null) {
                for (MasterDataEntity data : masterData) {%>
                <option><%= data.getId()%></option><%
                        }
                    }
                %>
            </select><br>
            <input type="submit" name="command" value="Remove"/><br>
        </form>

        <br><a href="choice.jsp">Back</a>

    </body>
</html>
