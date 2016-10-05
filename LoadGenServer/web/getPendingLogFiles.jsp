<%-- 
    Document   : getPendingLogFiles
    Created on : 29 Jul, 2016, 3:25:54 PM
    Author     : ratheeshkv
--%>

<%@page import="java.util.Enumeration"%>
<%@page import="org.eclipse.jdt.internal.compiler.impl.Constant"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.iitb.cse.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CrowdSource</title>
        <link rel="stylesheet" href="/serverplus/css/table.css">    </head>
    <body>
        <br/>
        <br/>

        <h2 class="heading">Get Log Files</h2>

        <%

//            response.setIntHeader("refresh", 5);

            if ( request.getParameter("getLogFile")!=null && request.getParameter("getLogFile").equalsIgnoreCase("getLogFile")) {
                Constants.currentSession.setFetchingLogFiles(false);
            }

//            Constants.currentSession.setFetchingLogFiles(false);
            if (Constants.currentSession.isFetchingLogFiles()) {
                response.sendRedirect("getLogFilesReqStatus.jsp");
            } else {

                ResultSet rs = DBManager.getPendingLogFileList();
                int count = 0;
                out.write("<br/><br/><form action='getLogFiles.jsp' method='post'>");

                out.write("<table border='1' class='table1'>");
                out.write("<tr><td>Number of Log Requests Per Round</td><td> <input type='text' value='1' name='numclients'/></td></tr>");
                out.write("<tr><td>Duration between Rounds<i>(in seconds)</i></td><td> <input type='text' value='10' name='duration'/></td></tr>");
                out.write("<tr><td></td><td><input type='submit' class='button' name='getLogFiles' value='Get Log Files'></td></tr>");
                out.write("</table><br><br>");

                out.write("<caption><h3 class='heading'>List Of <b>Active</b> Clients </h3></caption>");
                out.write("<table border='1'>");

                out.write("<tr><th>Select</th> <th>Mac Address</th></tr>");

                if (rs != null) {
                    while (rs.next()) {
                        if (Constants.currentSession.getConnectedClients().get(rs.getString(1)) != null) {
                            count++;
                            out.write("<tr><td><input type='checkbox' name='selected' value='" + "1" + "_" + rs.getString(1) + "'checked/>");
                            out.write("</td><td>" + rs.getString(1) + "</td>");
                        }
                    }
                }

                if (count == 0) {
                    out.write("<tr><td colspan='2'>No Active Clients !!!</td></tr>");
                }
                out.write("<table>");
                out.write("</form>");
            }
        %>




    </body>
</html>
