<%-- 
    Document   : experimentStatus
    Created on : 28 Jul, 2016, 5:29:38 PM
    Author     : cse
--%>

<%@page import="com.iitb.cse.Utils"%>
<%@page import="sun.text.normalizer.UBiDiProps"%>
<%@page import="com.iitb.cse.Constants"%>
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

        <%
            int requestCount = 0;
            if (request.getParameter("fetchLogFile") != null) {
                requestCount = Utils.getClientListForLogRequest(Constants.currentSession.getCurrentExperimentId());
            }
            //Constants.currentSession.setExperimentRunning(false);
            response.setIntHeader("refresh", 5);

            //    DBManager mgr = new DBManager();
            int expId = Constants.currentSession.getCurrentExperimentId();
            ResultSet rs = DBManager.getControlFileStatus(expId);
            int total = Constants.currentSession.getFilteredClients().size();
            int success = 0;
            int failed = 0;
            int pending = 0;

            if (rs != null) {
                while (rs.next()) {
                    //   out.write("\n->" + rs.getString(1) + "" + rs.getString(2));
                    if (rs.getString(1).equals("1")) {
                        success = Integer.parseInt(rs.getString(2));
                    } else if (rs.getString(1).equals("2")) {
                        failed = Integer.parseInt(rs.getString(2));
                    }
                }
            }

            pending = total - (success + failed);
            int expOver = DBManager.getExperimentOverCount(expId);

            rs = DBManager.getLogFileRequestStatus(expId);
            int fileReceived = DBManager.getLogFileReceivedCount(expId);

            int getLogSuccess = 0;
            int getLogFailed = 0;
            int getLogPending = 0;

            if (rs != null) {
                while (rs.next()) {
//                    out.write("\n" + rs.getString(1) + "" + rs.getString(2));
                    if (rs.getString(1).equals("1")) {
                        getLogSuccess = Integer.parseInt(rs.getString(2));
                    } else if (rs.getString(1).equals("2")) {
                        getLogFailed = Integer.parseInt(rs.getString(2));
                    }
                }
            }

            getLogPending = requestCount - getLogSuccess - getLogFailed;

            getLogPending = (getLogPending < 0) ? 0 : getLogPending;

            //      mgr.closeConnection();
            
            out.write("<h2 class='heading'>Experiment Details</h2>");
            
            out.write("<h3>Experiment No. " + Constants.currentSession.getCurrentExperimentId() + "</h3>");
            
            out.write("<table class='table1' cellspacing='20'>");
            //out.write("<caption>Experiment Details</caption>");
            out.write("<tr><td>Total Number of Clients Selected </td><td><a href='experimentSummary.jsp?reqType=total'>" + total + "</a></td></tr>");
            out.write("<td>Control File Sending </td><td> Success</td><td>" + (success > 0 ? "<a href='experimentSummary.jsp?reqType=success'>" + success + "</a>" : success) + "&nbsp;|</td>");
            out.write("<td> Pending</td><td>" + (pending > 0 ? "<a href='experimentSummary.jsp?reqType=pending'>" + pending + "</a>" : pending) + "&nbsp;|</td>");
            out.write("<td> Failed</td><td>" + (failed > 0 ? "<a href='experimentSummary.jsp?reqType=failed'>" + failed + "</a>" : failed) + "&nbsp;|</td></tr>");
            out.write("<tr><td>Experiment Over </td><td>" + (expOver > 0 ? "<a href='experimentSummary.jsp?reqType=expOver'>" + expOver + "</a>" : expOver) + "</td></tr>");

//            out.write("<tr><td>Fetch Log File Req Success</td><td>" + getLogSuccess + "</td></tr>");
//            out.write("<tr><td>Fetch Log File Req Failed</td><td>" + getLogFailed + "</td></tr>");
//            out.write("<tr><td>Fetch Log File Req Pending</td><td>" + getLogPending + "</td></tr>");

            out.write("<br/><br/><form action='experimentOver.jsp' method='post'>");
            out.write("<tr><td><input type='submit' class='button1' name='stopExp' value='Stop Experiment'></td><td></td</tr>");
            out.write("</form>");

            /*  
            if (expOver > 0 && fileReceived < expOver) {
                out.write("<form action='experimentStatus.jsp' method='post'>");
                out.write("<tr><td><input type='submit' name='fetchLogFile' value='Request Log Files'></td></tr>");
                out.write("</form>");
            }

            if (fileReceived == expOver && expOver!=0) {

                out.write("<br/><br/><form action='experimentOver.jsp' method='post'>");
                out.write("<tr><td><input type='submit' name='stopExp' value='Stop Experiment'></td><td>All Requested Log FIle received</td</tr>");
                out.write("</form>");

            } else {
                out.write("<br/><br/><form action='experimentOver.jsp' method='post'>");
                out.write("<tr><td><input type='submit' name='stopExp' value='Stop Experiment'></td><td></td</tr>");
                out.write("</form>");
            }            }
             */
            //if(getLogPending == 0){
            //       if ((getLogSuccess + getLogFailed) == expOver) {
            //out.write("<br/><br/><br/><h3>All get Log File Request Sent</h3>");
            //            out.write("<form action='experimentOver.jsp' method='post'>");
            //           out.write("<tr><td><input type='submit' name='stopExp' value='Stop Experiment'></td></tr>");
            //          out.write("</form>");
            //      }
            //}
            out.write(
                    "</table>");

            if (request.getParameter(
                    "fetchLogFile") != null) {
                // Constants.currentSession.setExperimentRunning(false);
                //   out.write("<h1>Fetch File</h1>");

            } else {
                //   out.write("<h1>Hello</h1>");
            }

        %>

    </body>
</html>
