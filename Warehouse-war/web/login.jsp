<%-- 
    Document   : login
    Created on : May 10, 2012, 6:05:29 PM
    Author     : kopytko
--%>

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
                    <li><a href="login.jsp" target="_self" title="Úvod" class="current"><span>Login</span></a></li>
                    <li><a href="search.jsp" target="_self" title="Koberce"><span>Search</span></a></li>
                    <li><a href="item.jsp" target="_self" title="PVC"><span>Manage items</span></a></li>
                    <li><a href="masterData.jsp" target="_self" title="Koberce"><span>Manage master data</span></a></li>
                    <li><a href="config.jsp" target="_self" title="Koberce"><span>Configure warehouse</span></a></li>
                    <li><a href="about.jsp" target="_self" title="Koberce"><span>About</span></a></li>
                </ul>
            </div>
        </div>

        <div id="middle">
            <div id="text">
               <p> <h3>Please enter your user name and
        password</h3></p>
        <form>
            Username:<input type ='text' name ="name"><br>
            Password:<input type ='password' name ="pass"><br><br>
            <input type ='submit' value="submit" >
            <jsp:useBean class="aplicationcontrol.UserData" id="mb1" />
            <jsp:setProperty name="mb1" property="*"/>
            <% mb1.check(); %>
            <% if( mb1.getName() != null)
            {
            String name = request.getParameter("name");
            String pass = mb1.getPass();
            int rights = mb1.getRights();
            String logged = mb1.isLogged();
            session.setAttribute("name",name);
            session.setAttribute("pass",pass);
            session.setAttribute("rights",rights);
            session.setAttribute("logged",logged);
            %>
            
            <jsp:getProperty name = "mb1" property = "rights"/>
             <jsp:forward page = "search.jsp">
                    
            <jsp:param name="rights" value="${mb1.getRights()}"></jsp:param>
                </jsp:forward>
            
            
            
            <%
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