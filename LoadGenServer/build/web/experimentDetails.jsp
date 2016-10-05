<%-- 
    Document   : experimentDetails
    Created on : 23 Jul, 2016, 10:53:25 AM
    Author     : ratheeshkv
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="com.iitb.cse.DBManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CrowdSource</title>
        <link rel="stylesheet" href="/serverplus/css/table.css">    </head>
    <body>
        <br/><br/>

        <h2  class="heading">Experiment History</h2>

        <%
            //   DBManager mgr =null;//= new DBManager();
            ResultSet rs = DBManager.getAllExperimentDetails();

            if (rs != null) {

                out.write("<table border='1'>");
                out.write("<tr><th>Exp No</th><th>Start Time</th><th>End Time</th><th>EXp Name</th><th>Exp Location</th><th>Description</th></tr>");
                while (rs.next()) {
                    out.write("<tr><td><a href=\"experimentView.jsp?expid=" + rs.getString(1) + "\">" + rs.getString(1) + "</a></td><td>" + rs.getString(2) + "</td><td>" + rs.getString(3) + "</td><td>" + rs.getString(4) + "</td><td>" + rs.getString(5) + "</td><td>" + rs.getString(6) + "</td></tr>");
                }
                out.write("</table>");
            }

            // mgr.closeConnection();
%>



    </body>
</html>
