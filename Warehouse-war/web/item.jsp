<%-- 
    Document   : item
    Created on : May 11, 2012, 11:26:16 PM
    Author     : Gabo
--%>

<%@page import="persistence.MasterDataEntity"%>
<%@page import="java.util.List"%>
<%@page import="persistence.Database"%>
<%@page import="javax.naming.InitialContext"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <b>Add item</b>
        <form method="POST" action="item">
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
             Quantity: <input type="text" name="quantity" size="2" /><br>
            Date: <input type="text" name="day" maxlength="2" size="1">
            <input type="text" name="month" maxlength="2" size="1">
            <input type="text" name="year" maxlength="4" size="1"><br>
            <input type="submit" name="command" value="Insert" /><br>
            <br>
            
            <b>Remove item</b><br>
            <select name="removeMD" id="removeMD">
                <%
                for (MasterDataEntity data : masterData) {%>
                <option><%= data.getId()%></option><%
                        }
                %>
            </select><br>
            Quantity: <input type="text" name="rmQuantity" size="2" /><br> 
            <input type="submit" name="command" value="Remove" />
            <br>
            <br><a href="choice.jsp">Back</a>
        </form>
    </body>
</html>
