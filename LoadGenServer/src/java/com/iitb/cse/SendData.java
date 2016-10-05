/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iitb.cse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ratheeshkv
 */
public class SendData extends Thread {

    final int startExp = 0;
    final int stopExp = 1;
    final int refresh = 2;
    final int apsetting = 3;
    final int getLogFile = 4;
    final int heartBeat = 5;
    final int serverConf = 6;

    int expID;
    DeviceInfo device;
    int whatToDo;
    String jsonString;
    String message;

    public SendData(int expID, DeviceInfo device, int whatToDo, String jsonString, String message) {

        this.expID = expID;
        this.device = device;
        this.whatToDo = whatToDo;
        this.jsonString = jsonString;
        this.message = message;
    }

    public SendData(int expID, DeviceInfo device, int whatToDo, String jsonString) {
        this.expID = expID;
        this.device = device;
        this.whatToDo = whatToDo;
        this.jsonString = jsonString;
    }

    @Override
    public void run() {

        switch (whatToDo) {

            case apsetting:

                System.out.println("\nInside Sending APSettings..." + device.macAddress + " " + device.socket);
                int status;
                try {
                    status = ClientConnection.writeToStream(device, jsonString, message);
                    if (status == Constants.responseOK) {

                        System.out.println("\nAPsettings : Successfully sent AP conf file ");

                    } else {
                        System.out.println("\nAPsettings : Unable to send AP Conf file ");
                    }

                } catch (IOException ex) {
                    System.out.println("\nSOcket Error : Sending AP conf file failed ");
                }

                break;

            case startExp:

                try {
                    status = ClientConnection.writeToStream(device, jsonString, message);

                    if (status == Constants.responseOK) {
                        System.out.println("\nStart Experiment: Successfully sent file ");
                        if (DBManager.addExperimentDetails(expID, device, 0, "")) {
                            System.out.println("\nStart Experiment: Insert to DataBase Success");
                        } else {
                            System.out.println("\nStart Experiment: Insert to DataBase Failed");
                        }
                    } else {
                        System.out.println("\nStart Experiment: : Unable to send file ");
                        if (DBManager.addExperimentDetails(expID, device, 2, "Sending control file failed")) {
                            System.out.println("\nStart Experiment: Insert to DataBase Success");
                        } else {
                            System.out.println("\nStart Experiment: Insert to DataBase Failed");
                        }
                    }
                } catch (IOException ex) {
                    System.out.println("\nSOcket Error : Sending Control file failed ");
                }

                /*
                synchronized (session.startExpTCounter) {
                    session.startExpTCounter--;
                    System.out.println("run() " + tid + ": value of startExpTCounter = " + session.startExpTCounter);
                
                }*/
                break;

            case stopExp:

                System.out.println("\nInside Stop Experiment ...");
                try {
                    status = ClientConnection.writeToStream(device, jsonString, message);

                    if (status == Constants.responseOK) {
                        System.out.println("\nStop Experiment: Successfully sent  file ");

                    } else {
                        System.out.println("\nStop Experiment : Unable to send file ");
                    }
                } catch (IOException ex) {
                    System.out.println("\nSOcket Error : Sending Stop Exp failed ");
                }

                /*synchronized (session.stopExpTCounter) {
                    System.out.println("run() " + tid + ": value of stopExpTCounter = " + session.stopExpTCounter);
                    session.stopExpTCounter--;
                }*/
                break;

            case refresh:

                System.out.println("\nInside Refresh Registration ...");
                try {
                    status = ClientConnection.writeToStream(device, jsonString, message);

                    if (status == Constants.responseOK) {
                        System.out.println("\nRefresh Registration: Successfully sent file ");
                    } else {
                        System.out.println("\nRefresh Registration : Unable to send file ");
                    }
                } catch (IOException ex) {
                    System.out.println("\nSOcket Error : Sending Refresh Exp failed ");
                }

                /*synchronized (session.refreshTCounter) {
                    session.DecrementRefreshTCounter();
                    System.out.println("value of refreshTCounter=" + session.refreshTCounter);
                }*/
                break;

            case getLogFile:
                System.out.println("\nInside Get Log FIle Request..");
                try {
                    status = ClientConnection.writeToStream(device, jsonString, message);
                    if (status == Constants.responseOK) {
                        device.setGetlogrequestsend(true);
                        device.setDetails("\nLog File request sent");
                        System.out.println("\nGet Log File request Successfully sent ");
                        DBManager.updateGetLogFileRequestStatus(expID, device.macAddress, 1);
                    } else {
                        device.setGetlogrequestsend(false);
                        device.setDetails("\nLog File request Sending Failed");
                        System.out.println("\nGet Log File request Sending Failed");
                        DBManager.updateGetLogFileRequestStatus(expID, device.macAddress, 2);
                    }
                } catch (IOException ex) {
                    device.setGetlogrequestsend(false);
                    device.setDetails("\nSOcket Error : Sending GetLog Request Exp failed " + ex.toString());
                    System.out.println("\nSOcket Error : Sending GetLog Request Exp failed ");
                }
                break;

            case heartBeat:
                System.out.println("\nInside Sending HEARTBEAT duration..");
                try {
                    status = ClientConnection.writeToStream(device, jsonString, message);
                    if (status == Constants.responseOK) {
                        device.setGetlogrequestsend(true);
                        device.setDetails("\nNew HeartBeat duration sent");
                        System.out.println("\nNew HeartBeat duration Successfully sent ");
//                        DBManager.updateGetLogFileRequestStatus(expID, device.macAddress, 1);
                    } else {
                        device.setGetlogrequestsend(false);
                        device.setDetails("\nNew HeartBeat duration Sending Failed");
                        System.out.println("\nNew HeartBeat duration Sending Failed");
//                        DBManager.updateGetLogFileRequestStatus(expID, device.macAddress, 2);
                    }
                } catch (IOException ex) {
                    device.setGetlogrequestsend(false);
                    device.setDetails("\nSOcket Error : New HeartBeat duration Sending Exp failed " + ex.toString());
                    System.out.println("\nSOcket Error : New HeartBeat duration Sending Exp failed ");
                }
                break;
            case serverConf:
                System.out.println("\nInside Sending ServerConfiguration ..");
                try {
                    status = ClientConnection.writeToStream(device, jsonString, message);
                    if (status == Constants.responseOK) {
                        device.setGetlogrequestsend(true);
                        device.setDetails("\nServerConfiguration request sent");
                        System.out.println("\nServerConfiguration request Successfully sent ");
//                        DBManager.updateGetLogFileRequestStatus(expID, device.macAddress, 1);
                    } else {
                        device.setGetlogrequestsend(false);
                        device.setDetails("\nServerConfiguration request Sending Failed");
                        System.out.println("\nServerConfiguration request Sending Failed");
//                        DBManager.updateGetLogFileRequestStatus(expID, device.macAddress, 2);
                    }
                } catch (IOException ex) {
                    device.setGetlogrequestsend(false);
                    device.setDetails("\nSOcket Error : Sending ServerConfiguration Request Exp failed " + ex.toString());
                    System.out.println("\nSOcket Error : Sending ServerConfiguration Request Exp failed ");
                }

                /* System.out.println("\nInside GetLogFile Request ...");
                status = ClientConnection.writeToStream(device, jsonString, message);
                if (status == Constants.responseOK) {

                    System.out.println("\nGetLogFile : Successfully sent Json file ");
                    Utils.addSendLogRequestDetails(session.getCurrentExperiment(), device.getMacAddress(), "SUCCESS");
                    Experiment e = Main.getRunningExperimentMap().get(session.getCurrentExperiment());
                    e.SFIncrement();//
                    device.setGetlogrequestsend("SUCCESS");
                } else {

                    System.out.println("\nGetLogFile : Unable to send Json file ");
                    Utils.addSendLogRequestDetails(session.getCurrentExperiment(), device.getMacAddress(), "ERROR Code : " + status + "");
                    device.setGetlogrequestsend("ERROR Code : " + status);
                }
                 */
                break;

        }

    }

}
