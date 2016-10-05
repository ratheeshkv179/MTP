<%-- 
    Document   : experimentView
    Created on : 23 Jul, 2016, 11:13:06 AM
    Author     : ratheeshkv
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="com.iitb.cse.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CrowdSource</title>
        <link rel="stylesheet" href="/serverplus/css/table.css"> 
    </head>
    <body>

        
        <br/><br/>

        <a href='experimentDetails.jsp'>Back</a>

        <%

            response.setIntHeader("refresh", 5);

            String expid = request.getParameter("expid");

            out.write("<h2 class='heading'>Experiment No. : " + expid + "</h2>");

            String path = Constants.experimentDetailsDirectory;
            
            if (!path.endsWith("/")) {
                path = path + "/";
            }

            if (!path.startsWith("/")) {
                path = "/" + path;
            }

            //  path = "file://" + path;
            //     path += expid + "/";
            //      DBManager mgr = new DBManager();
            ResultSet rs = DBManager.getExperimentDetails(expid);

            if (rs != null) {
                out.write("<table>");
                out.write("<tr><th>Mac Address</th><th>RSSI</th><th>BSSID</th><th>SSID</th><th>Log File Received</th><th>Control File Sent</th><th>Experiment Over</th><th>Status</th></tr>");
                //             select macaddress,controlfilesend,rssi,bssid,ssid,expover,status from experimentdetails where expid=44 order by bssid;  
                while (rs.next()) {
                    out.write("<tr><td>" + rs.getString(1) + "</td><td>" + rs.getString(3) + "</td><td>" + rs.getString(4) + "</td><td>" + rs.getString(5) + "</td><td>"
                            + (rs.getString(8).equals("1") ? "<a href=\"download.jsp?path=" + path + "&expid=" + expid + "&name=" + rs.getString(1) + "\" >" + "Received" + "</a>" : "Not Received")
                            /*
                            
                            out.print("<a href=\"download.jsp?" + Constants.getExpID() +"="+ rs.getInt(1) 
                                                                + "&download=detail&" + "file" + "="
                                                                + URLEncoder.encode((String)rs.getString(2), "UTF-8")   
                                                                + "\" > Download </a>");
                            
                             */
                            + "</td><td>" + (rs.getString(2).equals("1")?rs.getString(9):"Not Send") + "</td><td>" + (rs.getString(6).equals("1")?rs.getString(10):"Not Over")+ "</td><td>" + rs.getString(7) + "</td></tr>");
                }
                out.write("</table>");
            }

            //     mgr.closeConnection();
%>
    </body>
</html>
