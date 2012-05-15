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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
        <link href="resources/menu_style.css" rel="stylesheet" type="text/css" />
        <link href="resources/warehouse.css" rel="stylesheet" type="text/css" />
        <title>Facelets Template</title>
    </head>

    <body>

        <div id="head">
            <img src="resources/banner.bmp" alt="Banner" />
        </div>

        <div id="menucontainer">
            <div id="menunav">
                <ul>
                    <li><a href="login.jsp" target="_self" title="Úvod"><span>Login</span></a></li>
                    <li><a href="search.jsp" target="_self" title="Koberce" ><span>Search</span></a></li>
                    <li><a href="item.jsp" target="_self" title="PVC"><span>Manage items</span></a></li>
                    <li><a href="masterData.jsp" target="_self" title="Koberce" class="current"><span>Manage master data</span></a></li>
                    <li><a href="config.jsp" target="_self" title="Koberce"><span>Configure warehouse</span></a></li>
                    <li><a href="about.jsp" target="_self" title="Koberce"><span>About</span></a></li>
                </ul>
            </div>
        </div>

        <div id="middle">
            <div id="text">
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

            </div>
        </div>

        <div class="down">
            <em id="cbl"><b>&bull;</b></em>
            <em id="cbr" ><b>&bull;</b></em>

            <b>2012</b></div>



    </body>

</html>

