<%-- 
    Document   : apchangeHandler
    Created on : 23 Jul, 2016, 11:46:07 PM
    Author     : ratheeshkv
--%>

<%@page import="com.iitb.cse.DBManager"%>
<%@page import="com.iitb.cse.Utils"%>
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

                document.getElementById('error').style.display = 'none';

                if (document.getElementById("expName").value == null || document.getElementById("expName").value == "") {
                    //alert("ExprimentName cannot be empty");
                    document.getElementById('error').style.display = 'block';
                    return false;
                }

                if (document.getElementById("fileupload").value == null || document.getElementById("fileupload").value == "") {
                    alert("Choose Control file");
                    return false;
                } else {
                    return true;
                }

            }
        </script>
    </head>
    <body>

        <br/><a href='addExperiment.jsp'>Back</a><br/><br/>

        <h2 class="heading">Add Experiment</h2>

        <form action="addExperimentStatus.jsp"  method="post"  enctype="multipart/form-data" onsubmit="return check();">
            <!--<fieldset>-->
            <table class='table1'>

                <tr><td>Experiment Number :</td><td><input type="text" name="exp_number" value=<%= Integer.toString(DBManager.getNextExperimentId())%> readonly="readonly"></td></tr>
                <tr><td>Experiment Name <b style='color: red'>*</b>:</td><td><input type="text" id="expName" name="exp_name"/><i style='color:red;display: none' id='error'>Field Cannot be Empty</i></td></tr>
                <tr><td>Experiment Location :</td><td><input type="text" name="exp_loc"/></td></tr>
                <tr><td>Description : </td><td> <textarea name="exp_desc" rows="10" cols="30" style="resize:none;overflow-y: scroll"></textarea></td></tr>
                <tr><td>Upload Control File : </td><td><input type="file" id="fileupload" name="fileupload"></td></tr>
                <tr><td>Experiment Timeout<i>(Seconds)</i> </td><td><input type="text"  name="timeout"></td></tr>
                <tr><td>Log Background Traffic:</td><td><select name="bglog"><option value="false">Yes</option><option value="true">No</option></select></tr>


                <tr><td></td><td></td><td><input type='submit' class='button' value='Start Experiment'></td></tr>
            </table>
            <!--</fieldset>-->

            <br/>
            <br/>
            <br/>

            <h4 class="heading">Selected Clients</h4>

            <%
                session.setAttribute("filter", null);
                session.setAttribute("clientcount", null);

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
                            out.write("<tr><th>Select</th><th>Mac Address</th><th>SSID</th><th>BSSID</th><th>Last HeartBeat</th><th>Experiment status</th><th>Connection Status</th></tr>");
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
//                        session.setAttribute("filter", "random");
//                        session.setAttribute("clientcount", request.getParameter("random"));

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

