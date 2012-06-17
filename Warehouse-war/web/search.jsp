<%-- 
    Document   : Search
    Created on : May 8, 2012, 1:48:27 PM
    Author     : Gabo
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

    <head> 
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
        <link href="resources/menu_style.css" rel="stylesheet" type="text/css" />
        <link href="resources/warehouse.css" rel="stylesheet" type="text/css" />
        <title>Search</title>
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
                            helperItem = "search.jsp";
                        }
                    %>
                    <li><a href=<%= helperItem%> target="_self" title="Manage items"><span>Manage items</span></a></li>
                    <%
                        rights = session.getAttribute("rights");
                        String helperMaster;
                        if (mb2.Authorize(1, rights) == true) {
                            helperMaster = "masterData.jsp";
                        } else {
                            helperMaster = "search.jsp";
                        }
                    %>
                    <li><a href=<%= helperMaster%> target="_self" title="Manage master data"><span>Manage master data</span></a></li>
                    <%
                        rights = session.getAttribute("rights");
                        String helperConfig;

                        if (mb2.Authorize(2, rights) == true) {
                            helperConfig = "config.jsp";
                        } else {
                            helperConfig = "search.jsp";
                        }
                    %>
                    <li><a href=<%= helperConfig%> target="_self" title="Configure warehouse"><span>Configure warehouse</span></a></li>
                    <li><a href="about.jsp" target="_self" title="About"><span>About</span></a></li>
                </ul>
            </div>
        </div>

        <div id="middle">
            <div id="text">
                <form method="POST" action="search">
                    Search: <input type="text" name="searchString" /><br><br>
                            <input type="submit" value="Search" /> 
                            </form>
                            <br>
                                <ol><%
                                    @SuppressWarnings("unchecked")
                                    List<String> resultList = (List<String>) request.getAttribute("searchresult");
                                    if (resultList != null) {
                                        for (String row : resultList) {%>
                                    <li> <%= row%> </li> <%
                                            }
                                        }
                                    %></ol>

                                </div>
                                </div>

                                <div class="down">
                                    <em id="cbl"><b>&bull;</b></em>
                                    <em id="cbr" ><b>&bull;</b></em>

                                    <b>2012</b></div>



                                </body>

                                </html>