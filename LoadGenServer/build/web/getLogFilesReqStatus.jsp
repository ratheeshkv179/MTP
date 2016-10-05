<%-- 
    Document   : getLogFilesReqStatus
    Created on : 7 Sep, 2016, 3:28:40 PM
    Author     : cse
--%>

<%@page import="com.iitb.cse.DeviceInfo"%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.iitb.cse.Constants"%>
<%@page import="com.iitb.cse.Session"%>
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
        <h2 class="heading">Log File Request Status</h2>

        <%

            response.setIntHeader("refresh", 5);
            Enumeration<String> macList = Constants.currentSession.getGetLogFilefFilteredDevices().keys();

            out.write("<br/><br/><a class='button1' href='getPendingLogFiles.jsp?getLogFile=getLogFile'>&nbsp;&nbsp;Exit&nbsp;&nbsp;</a><br/><br/>");
            out.write("<form action='getLogFiles.jsp' method='post'>");
            out.write("<br/><br/><input type='submit'  class='button1' value='Retry GetLog File'/>&nbsp;&nbsp;<br/><br/>");
            out.write("<table>");
            out.write("<tr><th>Select</th><th>Mac Address</th><th>BSSID</th><th>SSID</th><th>Log File Request</th><th>Log File Received</th><th>Details</th></tr>");

            while (macList.hasMoreElements()) {
                String macAddr = macList.nextElement();
                DeviceInfo device = Constants.currentSession.getGetLogFilefFilteredDevices().get(macAddr);
                out.write("<tr><td><input type='checkbox' name='selected' value='1_" + macAddr + "'></td><td>" + macAddr + "</td><td>-</td><td>-</td><td>" + (device.isGetlogrequestsend() ? "Sent" : "Not Send") + "</td><td>" + (device.isLogFileReceived() ? "Yes" : "No") + "</td><td>" + device.getDetails() + "</td></tr>");
            }

            out.write("</table>");
            out.write("</form>");
            // Exit Constants.currentSession.setFetchingLogFiles(false);
            // 
%>

    </body>
</html>
