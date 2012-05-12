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
        
        <h4>Choose action:</h4>
        <%
            Object rights = session.getAttribute("rights");
            String helperMaster;
            
            if(mb2.Authorize(1,rights)==true){
               helperMaster = "masterData.jsp";
            } else {
               helperMaster = "choice.jsp";
            }
            %>
         
                    
       
        <a href=<%= helperMaster%>>Manage master data</a><br>
        <a href="search.jsp">Search</a><br>
        
        <%
            rights = session.getAttribute("rights");
            String helperConfig;
          
            if(mb2.Authorize(2,rights)==true){
               helperConfig = "config.jsp";
            } else {
               helperConfig = "choice.jsp";
            }
            %>
        
        <a href=<%= helperConfig%>>Configure warehouse</a>

        <%
            rights = session.getAttribute("rights");
            String helperItem;
          
            if(mb2.Authorize(1,rights)==true){
               helperItem = "item.jsp";
            } else {
               helperItem = "choice.jsp";
            }
            %>
        
        <br>
        <a href=<%= helperItem%>>Add/Remove Item</a><br>
        
        <%
            rights = session.getAttribute("rights");
            String helperOrder;
          
            if(mb2.Authorize(1,rights)==true){
               helperOrder = "item.jsp";
            } else {
               helperOrder = "choice.jsp";
            }
            %>
        
        <a href=<%= helperOrder%>
           
           
           >Make order</a>
    </body>
</html>
