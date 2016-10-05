<%-- 
    Document   : apchangeHandler
    Created on : 23 Jul, 2016, 11:46:07 PM
    Author     : ratheeshkv
--%>

<%@page import="java.util.concurrent.ConcurrentHashMap"%>
<%@page import="com.iitb.cse.DeviceInfo"%>
<%@page import="com.iitb.cse.DeviceInfo"%>
<%@page import="com.iitb.cse.Constants"%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.iitb.cse.Constants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CrowdSource</title>
        <link rel="stylesheet" href="/serverplus/css/table.css">

        <script>

            function check() {


                if (document.getElementById('ssid').value == "" || document.getElementById('ssid').value == null) {
                    alert("ssid cannot be empty");
                    return false;
                } else {
                    return true;
                }





            }

        </script>
    </head>
    <body>

        <br/><a href='apchange.jsp'>Back</a><br/><br/>

        <h2>AccessPoint Configuration</h2>
        <form action="apchangeStatus.jsp" method="post" onsubmit="return check(this);">

            <table class='table1'>
                <tr><td>SSID</td><td>:</td><td><input type='text' id='ssid'  name='_ssid'></td></tr>
                <tr><td>BSSID</td><td>:</td><td><input type='text' name='_bssid'></td></tr>
                <tr><td>USERNAME</td><td>:</td><td><input type='text' name='_username'></td></tr>
                <tr><td>PASSWORD</td><td>:</td><td><input type='text' name='_password'></td></tr>
                <tr><td>SECURITY</td><td>:</td><td><input type='text' name='_security'></td></tr>
                <tr><td></td><td></td><td><input class='button' type='submit' value='Send AP Settings'></td></tr>
            </table>

            <br/>
            <br/>
            <br/>

            <h3>Selected Clients</h3>

            <%
                if (request.getParameter("getclient") != null) {

                    /*
                    out.write("<h1>Helo'" + request.getParameter("filter") + "'</h1>");
                    out.write("<h1>Helo'" + request.getParameter("bssid") + "'</h1>");
                    out.write("<h1>Helo'" + request.getParameter("ssid") + "'</h1>");
                    out.write("<h1>Helo'" + request.getParameter("random") + "'</h1>");
                     */
                    if (request.getParameter("filter").equals("bssid")) {

                        ConcurrentHashMap<String, DeviceInfo> clients = Constants.currentSession.getConnectedClients();
                        Enumeration<String> macList = clients.keys();
                        if (clients != null) {
                            out.write("<table border='1'>");
                            out.write("<tr><th>Select</th><th>Mac Address</th><th>SSID</th><th>BSSID</th><th>Last HeartBeat</th><th>Experiment status</th><th>Connection Status</th></tr>");
                            if (clients.size() == 0) {
                                out.write("<tr><td colspan=\"7\">No Clients</td></tr>");
                            } else {
                                int i = 0;
                                while (macList.hasMoreElements()) {
                                    String macAddr = macList.nextElement();
                                    DeviceInfo device = clients.get(macAddr);
                                    if (request.getParameter("bssid").equalsIgnoreCase(device.getBssid())) {
                                        i++;
                                        out.write("<tr><td>" + i + "</td><td><input type=\"checkbox\" checked  name='selectedclient' value=\"" + macAddr + "\"/></td><td>" + macAddr + "</td><td>" + device.getSsid() + "</td><td>" + device.getBssid() + "</td><td>" + device.getLastHeartBeatTime() + "</td><td>" + device.getExpOver() + "</td><td>" + device.isConnectionStatus() + "</td></tr>");
                                    }
                                }
                            }
                            out.write("</table>");
                        }
                    } else if (request.getParameter("filter").equals("ssid")) {

                        ConcurrentHashMap<String, DeviceInfo> clients = Constants.currentSession.getConnectedClients();
                        Enumeration<String> macList = clients.keys();
                        if (clients != null) {
                            out.write("<table border='1'>");
                            out.write("<tr><th>No</th><th>Select</th><th>Mac Address</th><th>SSID</th><th>BSSID</th><th>Last HeartBeat</th><th>Experiment status</th><th>Connection Status</th></tr>");
                            if (clients.size() == 0) {
                                out.write("<tr><td colspan=\"7\">No Clients</td></tr>");
                            } else {
                                int i = 0;
                                while (macList.hasMoreElements()) {

                                    String macAddr = macList.nextElement();
                                    DeviceInfo device = clients.get(macAddr);
                                    if (request.getParameter("ssid").equalsIgnoreCase(device.getSsid())) {
                                        i++;
                                        out.write("<tr><td>" + i + "</td><td><input type=\"checkbox\" checked  name='selectedclient' value=\"" + macAddr + "\"/></td><td>" + macAddr + "</td><td>" + device.getSsid() + "</td><td>" + device.getBssid() + "</td><td>" + device.getLastHeartBeatTime() + "</td><td>" + device.getExpOver() + "</td><td>" + device.isConnectionStatus() + "</td></tr>");

                                    }
                                }
                            }
                            out.write("</table>");
                        }

                    } else if (request.getParameter("filter").equals("manual")) {

                        ConcurrentHashMap<String, DeviceInfo> clients = Constants.currentSession.getConnectedClients();
                        Enumeration<String> macList = clients.keys();
                        if (clients != null) {
                            out.write("<table border='1'>");
                            out.write("<tr><th>Select</th><th>Mac Address</th><th>SSID</th><th>BSSID</th><th>Last HeartBeat</th><th>Experiment status</th><th>Connection Status</th></tr>");
                            if (clients.size() == 0) {
                                out.write("<tr><td colspan=\"7\">No Clients</td></tr>");
                            } else {
                                int i = 0;
                                while (macList.hasMoreElements()) {
                                    String macAddr = macList.nextElement();
                                    DeviceInfo device = clients.get(macAddr);
                                    i++;
                                    out.write("<tr><td>" + i + "</td><td><input type=\"checkbox\" checked  name='selectedclient' value=\"" + macAddr + "\"/></td><td>" + macAddr + "</td><td>" + device.getSsid() + "</td><td>" + device.getBssid() + "</td><td>" + device.getLastHeartBeatTime() + "</td><td>" + device.getExpOver() + "</td><td>" + device.isConnectionStatus() + "</td></tr>");

                                }
                            }
                            out.write("</table>");
                        }

                    } else if (request.getParameter("filter").equals("random")) {

                        ConcurrentHashMap<String, DeviceInfo> clients = Constants.currentSession.getConnectedClients();
                        Enumeration<String> macList = clients.keys();
                        if (clients != null) {
                            out.write("<table border='1'>");
                            out.write("<tr><th>Select</th><th>Mac Address</th><th>SSID</th><th>BSSID</th><th>Last HeartBeat</th><th>Experiment status</th><th>Connection Status</th></tr>");
                            if (clients.size() == 0) {
                                out.write("<tr><td colspan=\"7\">No Clients</td></tr>");
                            } else {
                                int i = 0;
                                int count = 0;

                                while (macList.hasMoreElements()) {
                                    String macAddr = macList.nextElement();
                                    DeviceInfo device = clients.get(macAddr);
                                    i++;
                                    count++;
                                    if (count <= Integer.parseInt(request.getParameter("random"))) {
                                        out.write("<tr><td>" + i + "</td><td><input type=\"checkbox\" checked  name='selectedclient' value=\"" + macAddr + "\"/></td><td>" + macAddr + "</td><td>" + device.getSsid() + "</td><td>" + device.getBssid() + "</td><td>" + device.getLastHeartBeatTime() + "</td><td>" + device.getExpOver() + "</td><td>" + device.isConnectionStatus() + "</td></tr>");
                                    } else {
                                        out.write("<tr><td>" + i + "</td><td><input type=\"checkbox\"   name='selectedclient' value=\"" + macAddr + "\"/></td><td>" + macAddr + "</td><td>" + device.getSsid() + "</td><td>" + device.getBssid() + "</td><td>" + device.getLastHeartBeatTime() + "</td><td>" + device.getExpOver() + "</td><td>" + device.isConnectionStatus() + "</td></tr>");
                                    }

                                }
                            }
                            out.write("</table>");
                        }

                    }

                    /*mgr = new DBManager();
                       rs = DBManager.getClientList(mgr);
                       if (rs != null) {
                           out.write("<table style=\"overflow-y:auto\"><tr><th></th><th>MAC ADDRESS</th><th>SSID</th><th>BSSID</th></tr>");
                           while (rs.next()) {
                               out.write("<tr><td><input type=\"checkbox\" checked  name='selectedclient' value=\"" + rs.getString(1) + "\"/></td><td>" + rs.getString(1) + "</td><td>" + rs.getString(2) + "</td><td>" + rs.getString(3) + "</td>");
                           }
                           out.write("</table>");
                           out.write("<input type=\"submit\" id=\'addexperiment\' name=\'getclient\' value=\"Add Experiment\" />");
                       }
                       mgr.closeConnection();
                     */
                }
            %>

        </form>
    </body>
</html>
