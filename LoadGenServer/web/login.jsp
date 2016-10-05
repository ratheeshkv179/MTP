<%-- 
    Document   : login
    Created on : 12 Jul, 2016, 10:37:30 PM
    Author     : ratheeshkv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CrowdSource</title>
        <link rel="stylesheet" href="/serverplus/css/login.css">
    </head>
    <body>
        
        
        <p>
        <fieldset class='header'>
            CrowdSource Application - Server Handler
        </fieldset>
            
            
        </p>
        <br/><br/><br/><br/>
        <br/>
        <!--<br/><br/>-->

      


            <form  action="authenticate.jsp" method="post">
                 <fieldset class ='container'>
                <table align = 'center'>
                    <tr><td>User Name</td><td> <input class='textfield' type="text" name="name"></td></tr>
                    <tr><td>Password</td><td> <input class='textfield' type="password" name="pwd"></td></tr>
                    <tr></tr>
                    <tr><td></td><td> <input class='submit' type="submit" value="Login"><br></td></tr>
                </table>
                      </fieldset>
            </form>
        
<!--        <img src="/serverplus/images/bg.jpg"/>-->
         
    </body>
</html>
