<%-- 
    Document   : experimentSummary
    Created on : 5 Aug, 2016, 2:39:22 PM
    Author     : cse
--%>

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
        
        
        <br/><br/><br/>

        <a href='experimentStatus.jsp'>Back</a>

        <%
            
            
              response.setIntHeader("refresh", 5);
            
            ResultSet rs = DBManager.getDetailedControlFileStatus(Constants.currentSession.getCurrentExperimentId());
            //          int total = Constants.currentSession.getFilteredClients().size();
            ///        int success = 0;
            //        int failed = 0;
            //        int pending = 0;

// macaddress,controlfilesend,status,bssid
            String para = request.getParameter("reqType");
//            out.write("<h2>" + para + "</h2>");

            if (para.equals("total")) {
                
                out.write("<h2>All Selected Clients</h2>");
                out.write("<table>");
                out.write("<tr><th>Mac Address</th><th>BSSID</th><th>SSID</th></tr>");

                for (DeviceInfo d : Constants.currentSession.getFilteredClients()) {
                    out.write("<tr><td>" + d.getMacAddress() + "</td><td>" + d.getBssid() + "</td><td>" + d.getSsid() + "</td></tr>");
                }
                out.write("</table>");

            } else if (para.equals("success")) {

                out.write("<h2>Control File sending : Success</h2>");
                out.write("<table>");
                out.write("<tr><th>Mac Address</th><th>BSSID</th><th>Status</th></tr>");

                if (rs != null) {
                    while (rs.next()) {
                        //   out.write("\n->" + rs.getString(1) + "" + rs.getString(2));
                        if (rs.getString("controlfilesend").equals("1")) {
                            out.write("<tr><td>" + rs.getString("macaddress") + "</td><td>" + rs.getString("bssid") + "</td><td>" + rs.getString("status") + "</td></tr>");
                        }
                    }
                }
                out.write("</table>");

            } else if (para.equals("pending")) {

                out.write("<h2>Control File sending : Pending</h2>");
                out.write("<table>");
                out.write("<tr><th>Mac Address</th><th>BSSID</th><th>Status</th></tr>");

                if (rs != null) {
                    while (rs.next()) {
                        //   out.write("\n->" + rs.getString(1) + "" + rs.getString(2));
                        if (rs.getString("controlfilesend").equals("0")) {
                            out.write("<tr><td>" + rs.getString("macaddress") + "</td><td>" + rs.getString("bssid") + "</td><td>" + rs.getString("status") + "</td></tr>");
                        }
                    }
                }
                out.write("</table>");

            } else if (para.equals("failed")) {
                out.write("<h2>Control File sent : Failed</h2>");
                out.write("<table>");
                out.write("<tr><th>Mac Address</th><th>BSSID</th><th>Status</th></tr>");

                if (rs != null) {
                    while (rs.next()) {
                        //   out.write("\n->" + rs.getString(1) + "" + rs.getString(2));
                        if (rs.getString("controlfilesend").equals("2")) {
                            out.write("<tr><td>" + rs.getString("macaddress") + "</td><td>" + rs.getString("bssid") + "</td><td>" + rs.getString("status") + "</td></tr>");
                        }
                    }
                }
                out.write("</table>");

            } else if (para.equals("expOver")) {

                out.write("<h2>Experiment Over</h2>");
                out.write("<table>");
                out.write("<tr><th>Mac Address</th><th>BSSID</th><th>Control File Sent Date</th><th>Experiment Over Date</th></tr>");

                if (rs != null) {
                    while (rs.next()) {
                        //   out.write("\n->" + rs.getString(1) + "" + rs.getString(2));
                        if (rs.getString("expover").equals("1")) {
                            out.write("<tr><td>" + rs.getString("macaddress") + "</td><td>" + rs.getString("bssid") + "</td><td>" + rs.getString("controlfilesendDate") + "</td><td>" + rs.getString("expoverDate") + "</td></tr>");
                        }
                    }
                }
                out.write("</table>");
            }
        %>

    </body>
</html>
