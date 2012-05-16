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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
        <link href="resources/menu_style.css" rel="stylesheet" type="text/css" />
        <link href="resources/warehouse.css" rel="stylesheet" type="text/css" />
        <title>Manage items</title>
    </head>

    <body>

        <div id="head">
            <img src="resources/banner.bmp" alt="Banner" />
        </div>

        <div id="menucontainer">
            <div id="menunav">
                <ul>
                    <li><a href="login.jsp" target="_self" title="Úvod"><span>Login</span></a></li>
                    <li><a href="search.jsp" target="_self" title="Koberce"><span>Search</span></a></li>
                    <li><a href="item.jsp" target="_self" title="PVC" class="current"><span>Manage items</span></a></li>
                    <li><a href="masterData.jsp" target="_self" title="Koberce"><span>Manage master data</span></a></li>
                    <li><a href="config.jsp" target="_self" title="Koberce"><span>Configure warehouse</span></a></li>
                    <li><a href="about.jsp" target="_self" title="Koberce"><span>About</span></a></li>
                </ul>
            </div>
        </div>

        <div id="middle">
            <div id="text">
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
                    Quantity: <input type="text" name="quantity" size="2" />
                    <%
                        String nullQuantity = (String) request.getAttribute("nullQuantity");
                        if (nullQuantity != null) {
                    %><font color="red"><%= nullQuantity%></font><%
                            }
                    %>
                    <br>
                    Date (dd:mm:yyyy) <input type="text" name="day" maxlength="2" size="1">
                    <input type="text" name="month" maxlength="2" size="1">
                    <input type="text" name="year" maxlength="4" size="1">
                    <%
                        String nullDate = (String) request.getAttribute("nullDate");
                        if (nullDate != null) {
                    %><font color="red"><%= nullDate%></font><%
                            }
                    %>
                    <br>
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
                    Quantity: <input type="text" name="rmQuantity" size="2" />
                    <%
                        String nullQuantityRm = (String) request.getAttribute("nullQuantityRm");
                        if (nullQuantityRm != null) {
                    %><font color="red"><%= nullQuantityRm%></font><%
                            }
                    %>
                    <br> 
                    <input type="submit" name="command" value="Remove" />       
                    <%
                        String error = (String) request.getAttribute("error");
                        if (error != null) {
                    %><h3><font color="red"><%= error%></font></h3><%
                        }
                        %>
                </form>

            </div>
        </div>

        <div class="down">
            <em id="cbl"><b>&bull;</b></em>
            <em id="cbr" ><b>&bull;</b></em>

            <b>2012</b></div>



    </body>

</html>

