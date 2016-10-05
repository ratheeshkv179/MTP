<%-- 
    Document   : apchage
    Created on : 23 Jul, 2016, 12:37:06 PM
    Author     : ratheeshkv
--%>

<%@page import="com.iitb.cse.Utils"%>
<%@page import="com.mysql.jdbc.Util"%>
<%@page import="java.util.concurrent.ConcurrentHashMap"%>
<%@page import="com.iitb.cse.DeviceInfo"%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.iitb.cse.Constants"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.iitb.cse.DBManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CrowdSource</title>
        <link rel="stylesheet" href="/serverplus/css/table.css">


        <script>

            function check() {
                alert("onchahe");
            }


            function _check() {
                //   alert(document.getElementById('filter').value);
                if (document.getElementById('filter').value == "bssid") {
                    document.getElementById('selectonbssid').style.display = 'block'
                    document.getElementById('selectonssid').style.display = 'none'
                    document.getElementById('random').style.display = 'none'
                    document.getElementById('getclient').style.display = 'block'
                } else if (document.getElementById('filter').value == "ssid") {
                    document.getElementById('selectonssid').style.display = 'block'
                    document.getElementById('selectonbssid').style.display = 'none'
                    document.getElementById('random').style.display = 'none'
                    document.getElementById('getclient').style.display = 'block'
                } else if (document.getElementById('filter').value == "manual") {

                    document.getElementById('random').style.display = 'none'
                    document.getElementById('selectonssid').style.display = 'none'
                    document.getElementById('selectonbssid').style.display = 'none'
                    document.getElementById('getclient').style.display = 'block'

                } else if (document.getElementById('filter').value == "random") {
                    document.getElementById('random').style.display = 'block'
                    document.getElementById('selectonssid').style.display = 'none'
                    document.getElementById('selectonbssid').style.display = 'none'
                    document.getElementById('getclient').style.display = 'block'
                } else {
                    document.getElementById('random').style.display = 'none'
                    document.getElementById('selectonssid').style.display = 'none'
                    document.getElementById('selectonbssid').style.display = 'none'
                    document.getElementById('getclient').style.display = 'none'
                }
            }


        </script>



    </head>
    <body>

        <br/><br/>
        <h2 class='heading'>Change AccessPoint</h2>
        
        


        
        <br/>
        <br/>
        
        
        
        
        
        <form action="apchangeHandler.jsp" method="get">

            <table class='table1'>


                <tr><td>
                        Select Clients Based on :
                        <select name='filter' id='filter' onchange="_check(this)">
                            <option value="none" ></option>
                            <option value="bssid" >BSSID</option>
                            <option value="ssid" >SSID</option>
                            <option value="manual" >Manual</option>
                            <option value="random" >Random</option>
                        </select>
                    </td><td>


                        <select name='bssid' id='selectonbssid' style="display: none">
                            <%
                                Enumeration<String> bssidList = Utils.getAllBssids();
                                while (bssidList.hasMoreElements()) {
                                    String bssid = bssidList.nextElement();
                                    out.write("<option value=\"" + bssid + "\">" + bssid + "</option>");
                                }
                            %>
                        </select>



                        <select name='ssid' id='selectonssid' style="display: none">
                            <%
                                Enumeration<String> ssidList = Utils.getAllSsids();
                                while (ssidList.hasMoreElements()) {
                                    String ssid = ssidList.nextElement();
                                    out.write("<option value=\"" + ssid + "\">" + ssid + "</option>");
                                }
                            %>
                        </select>


                        <input type="number" id ='random' name='random' value='1' min="1" max="5" style="display: none" />



                    </td></tr>

                <tr><td></td><td><input type="submit" class='button' id='getclient' name='getclient' value="Get_Clients"  style="display: none"/></td></tr>

                <!--             <tr><td></td><td> <a href="apchange.jsp?getclient=geclient&" id='getclient' name='getclient'  style="display: none">Get_Clients</a></td></tr>-->


            </table>



        </form>
                        
                        
                        <br/>
        <br/><br/><br/>
                
           <table border="1">
            <caption><h3>AccessPoint Connection Details</h3></caption>
            <tr><th>BSSID</th><th>SSID</th><th>No. Of Clients Connected</th></tr>
                    <%

                        ConcurrentHashMap<String, String> apConnection = Utils.getAccessPointConnectionDetails();
                        Enumeration detail = apConnection.keys();

                        boolean flag = false;

                        while (detail.hasMoreElements()) {
                            flag = true;
                            String bssid = (String) detail.nextElement();
                            String ssid_count = apConnection.get(bssid);
                            String info[]  = ssid_count.split("#");
                            
                            out.write("<tr><td>" + bssid + "</td><td>" + info[0] + "</td><td>" + info[1]+ "</td></tr>");

                        }

                        if (!flag) {
                            out.write("<tr><td colspan='3' >No Clients!!!</td></tr>");
                        }


                    %>
        </table>
        

                        
    </body>
</html>
