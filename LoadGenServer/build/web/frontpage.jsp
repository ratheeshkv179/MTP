<%-- 
    Document   : frontpage
    Created on : 22 Jul, 2016, 2:49:22 PM
    Author     : cse
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CrowdSource</title>
        <link rel="stylesheet" href="/serverplus/css/login.css">
        <!--<link rel="stylesheet" href="/serverplus/css/table.css">-->
        <!--<link type="text/css" rel="stylesheet" href="./css/frontPage.css" />-->
    </head>
    <body>



        <div>
            <!--<div class='a' style="height:100px;width:100%;background-color: #5F5F5F;text-align: center;font-size:70px;color: #EEE;font-family:'Times New Roman' ">Loadgenerator-Server</div>-->

            <div style="height:100px;width:100%">
                <fieldset class='header'>
                    CrowdSource Application - Server Handler
                </fieldset>
            </div>


            <div class='b' style="height:850px;width:15%;float:left; ">
                <br/><br/>

                <table border='0'>

                    <tr
                        ><td>
                            <form action="processAction.jsp" method="post">
                                <input class='menu' type="submit"  formtarget="myframe" name="home" value="Home">

                            </form>

                        </td>
                    </tr>



                    <tr><td>
                            <form action="processAction.jsp" method="post">

                                <input class='menu' type="submit"  formtarget="myframe" name="addExperiment" value="Add Experiment">                                    

                            </form>

                        </td></tr>

                    <tr><td>
                            <form action="processAction.jsp" method="post">
                                <input class='menu' type="submit"   formtarget="myframe" name="apChange" value="Change Access Point">

                            </form>
                        </td></tr>

                    <tr><td>
                            <form action="processAction.jsp" method="post">
                                <input class='menu' type="submit"    formtarget="myframe" name="expDetails" value="Experiment Details">

                            </form>
                        </td></tr>

                    <tr><td>
                            <form action="processAction.jsp" method="post">
                                <input class='menu' type="submit"   formtarget="myframe" name="getLogFile" value="Get Log Files">                                    


                            </form>
                        </td></tr>

                    <tr><td>
                            <form action="processAction.jsp" method="post">
                                <input class='menu' type="submit"   formtarget="myframe" name="configureParams" value="Configure">                                    


                            </form>
                        </td></tr>


                    <tr><td>

                            <h5 >
                                <a class='button1' href="logout.jsp?logout=logout">Logout</a>
                            </h5>
                        </td></tr>


                </table>
                <br/><br/><br/>




            </div>
            <div class='c' id="space" style="height:850px;width:85%;float:right;">
                <iframe src="homepage.jsp" name='myframe'frameborder="0" style="height:99.5%;width:99.7%">
                </iframe>
            </div>

            <div class='d' style="height:5%;width:100%;">Developed By</div>
        </div>










    </body>
</html>
