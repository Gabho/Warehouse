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
        <jsp:useBean class="aplicationcontrol.Authorization" id="mb2" />
        <div id="head">
            <img src="resources/banner.bmp" alt="Banner" />
        </div>

        <div id="menucontainer">
            <div id="menunav">
                <ul>
                    <li><a href="login.jsp" target="_self" title="Login"><span>Login</span></a></li>
                    <li><a href="search.jsp" target="_self" title="Search" class="current"><span>Search</span></a></li>
                    <%
                        Object rights = session.getAttribute("rights");
                        String helperItem;

                        if (mb2.Authorize(1, rights) == true) {
                            helperItem = "item.jsp";
                        } else {
                            helperItem = "item.jsp";
                        }
                    %>
                    <li><a href=<%= helperItem%> target="_self" title="Manage items"><span>Manage items</span></a></li>
                    <%
                        rights = session.getAttribute("rights");
                        String helperMaster;
                        if (mb2.Authorize(1, rights) == true) {
                            helperMaster = "masterData.jsp";
                        } else {
                            helperMaster = "item.jsp";
                        }
                    %>
                    <li><a href=<%= helperMaster%> target="_self" title="Manage master data"><span>Manage master data</span></a></li>
                    <%
                        rights = session.getAttribute("rights");
                        String helperConfig;

                        if (mb2.Authorize(2, rights) == true) {
                            helperConfig = "config.jsp";
                        } else {
                            helperConfig = "item.jsp";
                        }
                    %>
                    <li><a href=<%= helperConfig%> target="_self" title="Configure warehouse"><span>Configure warehouse</span></a></li>
                    <li><a href="about.jsp" target="_self" title="About"><span>About</span></a></li>
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

