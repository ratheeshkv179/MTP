/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iitb.cse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;

/**
 *
 * @author ratheeshkv
 */
public class Utils {

    @SuppressWarnings("unchecked")

    static String getApSettingsFileJson(String message) {
        JSONObject obj = new JSONObject();
        obj.put(Constants.action, Constants.sendApSettings);
        String[] apConf = message.split("\n");
        System.out.println(apConf.length);

        for (int i = 0; i < apConf.length; i++) {
            String[] apInfo = apConf[i].trim().split("=");
            if (apInfo[0].equalsIgnoreCase("USERNAME")) {

                if (apInfo.length == 1) {

                    obj.put(Constants.username, "");
                } else {
                    String _usrname = apInfo[1].trim();
                    obj.put(Constants.username, _usrname);
                }

            } else if (apInfo[0].equalsIgnoreCase("PASSWORD")) {

                if (apInfo.length == 1) {
                    //String _usrname = apInfo[1].trim();
                    obj.put(Constants.password, "");
                } else {
                    String _passwd = apInfo[1].trim();
                    obj.put(Constants.password, _passwd);
                }

            } else if (apInfo[0].equalsIgnoreCase("BSSID")) {

                if (apInfo.length == 1) {
                    obj.put(Constants.bssid, "");
                } else {
                    String _bssid = apInfo[1].trim();
                    obj.put(Constants.bssid, _bssid);
                }

            } else if (apInfo[0].equalsIgnoreCase("SSID")) {

                if (apInfo.length == 1) {
                    obj.put(Constants.ssid, "");
                } else {
                    String _ssid = apInfo[1].trim();
                    obj.put(Constants.ssid, _ssid);
                }

            }else if (apInfo[0].equalsIgnoreCase("SECURITY")) {

                if (apInfo.length == 1) {
                    obj.put("security", "");
                } else {
                    String _sec = apInfo[1].trim();
                    obj.put("security", _sec);
                }

            }
        }

        //obj.put(Constants.message,message);
        //	obj.put(Constants.textFileFollow, Boolean.toString(true));
        //	obj.put(Constants.serverTime, Long.toString(Calendar.getInstance().getTimeInMillis()));
        String jsonString = obj.toJSONString();
        System.out.println(jsonString);
        return jsonString;
    }

    /**
     * genetated and returns the String in Json format. String contains
     * information about the action for sending AP settings file This string is
     * later sent to filtered devices.
     */
    @SuppressWarnings("unchecked")
    static String getControlFileJson(String message, String timeout, String logBgTraffic) {
        JSONObject obj = new JSONObject();
        obj.put(Constants.action, Constants.sendControlFile);
        obj.put(Constants.textFileFollow, Boolean.toString(true));
        obj.put(Constants.serverTime, Long.toString(Calendar.getInstance().getTimeInMillis()));
        obj.put(Constants.message, message);
        obj.put(Constants.timeout, timeout);
        obj.put("selectiveLog", logBgTraffic);
        String jsonString = obj.toJSONString();

        Date obj1 = new Date(Long.parseLong((String) obj.get(Constants.serverTime)));

        System.out.println(jsonString + obj1);
        return jsonString;
    }

    @SuppressWarnings("unchecked")
    static String getHeartBeatDuration(String duration) {
        JSONObject obj = new JSONObject();
        obj.put(Constants.action, "hbDuration");
        obj.put("heartbeat_duration", duration);
        obj.put("hbDuration", duration);

        String jsonString = obj.toJSONString();
        System.out.println(jsonString);
        return jsonString;
    }

    @SuppressWarnings("unchecked")
    static String getServerConfiguration(String serverIP, String serverPORT, String connectionPORT) {
        JSONObject obj = new JSONObject();
        obj.put(Constants.action, "action_changeServer");
//        hbDuration

        if (serverIP != null && !serverIP.trim().equalsIgnoreCase("")) {
            obj.put("serverip", serverIP);
        } else {
            obj.put("serverip", "");
        }

        if (serverPORT != null && !serverPORT.trim().equalsIgnoreCase("")) {
            obj.put("serverport", serverPORT);
        } else {
            obj.put("serverport", "");
        }

        if (connectionPORT != null && !connectionPORT.trim().equalsIgnoreCase("")) {
            obj.put("connectionport", connectionPORT);
        } else {
            obj.put("connectionport", "");
        }

        String jsonString = obj.toJSONString();
        System.out.println(jsonString);
        return jsonString;
    }

    /**
     * genetated and returns the String in Json format. String contains
     * information that the experiment has been stopped by experimenter This
     * string is later sent to filtered devices.
     */
    @SuppressWarnings("unchecked")
    static String getStopSignalJson() {
        JSONObject obj = new JSONObject();
        obj.put(Constants.action, Constants.stopExperiment);
        String jsonString = obj.toJSONString();
        System.out.println(jsonString);
        return jsonString;
    }

    /**
     * genetated and returns the String in Json format. String contains
     * information that the experiment has been stopped by experimenter This
     * string is later sent to filtered devices.
     */
    @SuppressWarnings("unchecked")
    static String getLogFilesJson(int expID) {
        JSONObject obj = new JSONObject();
        //{action:getLogFile}
        obj.put(Constants.action, Constants.getLogFiles);
        obj.put(Constants.expID, Integer.toString(expID));

        String jsonString = obj.toJSONString();
        System.out.println(jsonString);
        return jsonString;
    }

    /**
     * genetated and returns the String in Json format. String contains
     * information that the experimentee wants to ping the devices and refresh
     * the list of registered devices This string is later sent to registered
     * devices.
     */
    @SuppressWarnings("unchecked")
    static String getRefreshRegistrationJson() {
        JSONObject obj = new JSONObject();
        obj.put(Constants.action, Constants.refreshRegistration);
        String jsonString = obj.toJSONString();
        System.out.println(jsonString);
        return jsonString;
    }

    /**
     * genetated and returns the String in Json format. String contains
     * information that all the registration has been cleared by experimenter
     * This string is later sent to filtered devices.
     */
    @SuppressWarnings("unchecked")
    static String getClearRegistrationJson() {
        JSONObject obj = new JSONObject();
        obj.put(Constants.action, Constants.clearRegistration);
        String jsonString = obj.toJSONString();
        System.out.println(jsonString);
        return jsonString;
    }

    public static Date getCurrentTimeStamp() {
        Date date = Calendar.getInstance().getTime();
        return date;
    }

    public static void getClientBasedOnBssid() {

    }

    public static void createSession(String user) {

        if (Constants.currentSession == null) {
            Constants.currentSession = new Session(user);
            System.out.println("\n New Session created");
        } else {
            System.out.println("\n Use existing Session");
        }

    }

    public static void clearSession() {
        Constants.currentSession = null;
        System.out.println("\n Session cleared");
    }

    public static Enumeration<String> getAllBssids() {

        ConcurrentHashMap<String, Boolean> obj = new ConcurrentHashMap<String, Boolean>();
        ConcurrentHashMap<String, DeviceInfo> clients = Constants.currentSession.getConnectedClients();
        Enumeration<String> macList = clients.keys();

        if (clients != null) {
            while (macList.hasMoreElements()) {
                String macAddr = macList.nextElement();
                DeviceInfo device = clients.get(macAddr);
                obj.put(device.getBssid(), Boolean.TRUE);
            }
        }

        Enumeration<String> bssidList = obj.keys();
        return bssidList;
    }

    public static Enumeration<String> getAllSsids() {

        ConcurrentHashMap<String, Boolean> obj = new ConcurrentHashMap<String, Boolean>();
        ConcurrentHashMap<String, DeviceInfo> clients = Constants.currentSession.getConnectedClients();
        Enumeration<String> macList = clients.keys();

        if (clients != null) {
            while (macList.hasMoreElements()) {
                String macAddr = macList.nextElement();
                DeviceInfo device = clients.get(macAddr);
                obj.put(device.getSsid(), Boolean.TRUE);
            }
        }

        Enumeration<String> bssidList = obj.keys();
        return bssidList;
    }

    public static CopyOnWriteArrayList<DeviceInfo> activeClients() {
        //   (Utils.getCurrentTimeStamp().getTime() - device.getLastHeartBeatTime().getTime())/1000 > 60 
//        int count = 0;

        ConcurrentHashMap<String, DeviceInfo> clients = Constants.currentSession.getConnectedClients();
        Enumeration<String> macList = clients.keys();
        ConcurrentHashMap<String, DeviceInfo> connectedClients = new ConcurrentHashMap<String, DeviceInfo>();
        CopyOnWriteArrayList<DeviceInfo> activeClients = new CopyOnWriteArrayList<DeviceInfo>();

        if (clients != null) {
            while (macList.hasMoreElements()) {
                String macAddr = macList.nextElement();
                DeviceInfo device = clients.get(macAddr);
                if ((Utils.getCurrentTimeStamp().getTime() - device.getLastHeartBeatTime().getTime()) / 1000 <= Constants.heartBeatAlive) {
                    activeClients.add(device);
                    //                   count++;
                }
            }
        }
        return activeClients;
    }

    public static String generateLine(Calendar cal, String mode, String type, String link) {
        String line = type + " ";

        line += cal.get(Calendar.YEAR) + " ";
        line += cal.get(Calendar.MONTH) + " ";
        line += cal.get(Calendar.DAY_OF_MONTH) + " ";
        line += cal.get(Calendar.HOUR_OF_DAY) + " ";
        line += cal.get(Calendar.MINUTE) + " ";
        line += cal.get(Calendar.SECOND) + " ";
        line += cal.get(Calendar.MILLISECOND) + " ";
        line += mode + " ";
        line += link;
        return line + "\n";
    }

    public static String generateLine(Calendar cal, String mode, String type, String link, String size) {
        String line = type + " ";

        line += cal.get(Calendar.YEAR) + " ";
        line += cal.get(Calendar.MONTH) + " ";
        line += cal.get(Calendar.DAY_OF_MONTH) + " ";
        line += cal.get(Calendar.HOUR_OF_DAY) + " ";
        line += cal.get(Calendar.MINUTE) + " ";
        line += cal.get(Calendar.SECOND) + " ";
        line += cal.get(Calendar.MILLISECOND) + " ";
        line += mode + " ";
        line += link + " ";
        line += size;
        return line + "\n";
    }

    public static boolean startExperiment(int expId, String timeout, String logBgTraffic) {

        File file = null;
        File file1 = null;

        if (Constants.experimentDetailsDirectory.endsWith("/")) {
            file = new File(Constants.experimentDetailsDirectory + Constants.currentSession.getCurrentExperimentId() + "/" + Constants.configFile);
            file1 = new File(Constants.experimentDetailsDirectory + Constants.currentSession.getCurrentExperimentId());
        } else {
            file = new File(Constants.experimentDetailsDirectory + "/" + Constants.currentSession.getCurrentExperimentId() + "/" + Constants.configFile);
            file1 = new File(Constants.experimentDetailsDirectory + "/" + Constants.currentSession.getCurrentExperimentId());
        }

        if (file.exists()) {
            Charset charset = Charset.forName("UTF-8");
            String line = null;

            String[] data = new String[1000];//
            int index = 0;
            data[index] = "";
            try {
                BufferedReader reader = Files.newBufferedReader(file.toPath(), charset);
                Calendar cal = Calendar.getInstance();
                while ((line = reader.readLine()) != null) {

                    System.out.println("\nLENGTH : " + line.length());

                    if (line.isEmpty() || line.trim().equals("")) {
                        System.out.println("\nCASE1");
                        continue;
                    } else if (line.trim().equals("*****\n")) {
                        System.out.println("\nCASE2");
                        data[index] = expId + "\n" + data[index];
                        index++;
                        data[index] = "";
                        continue;
                    } else if (line.trim().equals("*****")) {
                        System.out.println("\nCASE3");
                        data[index] = expId + "\n" + data[index];
                        index++;
                        data[index] = "";
                        continue;
                    }

                    String[] lineVariables = line.split(" ");

                    //	int offset = Integer.parseInt(lineVariables[1]);
                    //	cal.add(Calendar.SECOND, offset);
//****************************************************
                    double time = Double.parseDouble(lineVariables[1]);

                    int sec = (int) time;
                    double rem = time % 1;
                    int remainder = (int) (rem * 1000);
                    //       Calendar cal = Calendar.getInstance();
                    //   System.out.println("\nSec : " + sec + "\nMiSec : " + remainder + "\nTime : " + cal.getTime());
                    int flag = 0;
                    if (remainder < 100) {
                        flag = 1;
                        remainder = remainder + 100;
                        cal.add(Calendar.SECOND, sec);
                        cal.add(Calendar.MILLISECOND, remainder);
                        cal.add(Calendar.MILLISECOND, -100);
                    } else {
                        cal.add(Calendar.SECOND, sec);
                        cal.add(Calendar.MILLISECOND, remainder);
                    }

//****************************************************
                    if (lineVariables.length == 5) {
                        //       System.out.println("\nINSIDE");
                        data[index] += generateLine(cal, lineVariables[2], lineVariables[0], lineVariables[3], lineVariables[4]);
                    } else {
                        //    System.out.println("\nOUTSIDE");
                        data[index] += generateLine(cal, lineVariables[2], lineVariables[0], lineVariables[3]);
                    }

                    if (flag == 1) {
                        cal.add(Calendar.SECOND, -1 * sec);
                        cal.add(Calendar.MILLISECOND, -1 * remainder);
                        cal.add(Calendar.MILLISECOND, 100);
                    } else {
                        cal.add(Calendar.SECOND, -1 * sec);
                        cal.add(Calendar.MILLISECOND, -1 * remainder);
                    }

                }

                data[index] = expId + "\n" + data[index];

            } catch (IOException ex) {
                System.out.println(ex.toString());
                return false;
            }

            int controlFileIndex = 0;
            for (DeviceInfo d : Constants.currentSession.getFilteredClients()) {

                if (controlFileIndex >= Constants.currentSession.getFilteredClients().size()) {
                    break;
                } else if (data[controlFileIndex] != null) {

                    String jsonString = Utils.getControlFileJson(data[controlFileIndex], timeout, logBgTraffic);
                    System.out.println("\njsonString : " + jsonString);
                    System.out.println("\nControl FIle : " + data[controlFileIndex]);

                    /* Locally keep the corresponding control file to each client*/
                    PrintWriter writer;
                    try {
                        writer = new PrintWriter(file1 + "/" + d.macAddress + "_confFile");
                        writer.write(data[controlFileIndex]);
                        writer.flush();
                        writer.close();
                    } catch (FileNotFoundException ex) {
                        System.out.println("\nException : " + ex.toString());
                    }

                    //writer.close();
                    System.out.println("\nDevice Info : IP " + d.ip + " Port " + d.port + " Mac " + d.macAddress);
                    Thread sendData = new Thread(new SendData(expId, d, 0, jsonString, data[controlFileIndex]));
                    sendData.start();
                } else {
                    break;
                }
                controlFileIndex++;
            }
        } else {
            System.out.println("\nConfig FIle not found in location : " + Constants.experimentDetailsDirectory + Constants.currentSession.getCurrentExperimentId());
        }

        return true;
    }

    public static boolean startRandomExperiment(int expId, int numberOfClients) {

        return false;
    }

    public static void sendApSettings(String settings) {

        String jsonString = Utils.getApSettingsFileJson(settings);
        for (DeviceInfo d : Constants.currentSession.getApConfFilteredDevices()) {
            int expId = Constants.currentSession.getCurrentExperimentId();
            Thread sendData = new Thread(new SendData(expId, d, 3, jsonString));
            sendData.start();
        }
    }

    public static ConcurrentHashMap<String, String> getAccessPointConnectionDetails() {

        ConcurrentHashMap<String, String> apConnection = new ConcurrentHashMap<String, String>();
        Enumeration macList = Constants.currentSession.connectedClients.keys();

        while (macList.hasMoreElements()) {
            String macAddr = (String) macList.nextElement();
            DeviceInfo device = Constants.currentSession.connectedClients.get(macAddr);

            if (apConnection.get(device.getBssid()) == null) {
                apConnection.put(device.getBssid(), device.getSsid() + "#1");
            } else {
                String ssid_count = apConnection.get(device.getBssid());
                int count = Integer.parseInt(ssid_count.split("#")[1]);
                count++;
                apConnection.put(device.getBssid(), device.getSsid() + "#" + Integer.toString(count));
            }
        }
        return apConnection;
    }

    public static int getClientListForLogRequest(int expId) {

        System.out.println("\nGetLogFile Request ExpID : " + expId);
        CopyOnWriteArrayList<String> clients = DBManager.getClientsForLogRequest(expId);

        String[] list = new String[clients.size()];

        for (int i = 0; i < clients.size(); i++) {
            list[i] = clients.get(i);
            list[i] = expId + "_" + list[i];
            System.out.println("\nGetLogFile Request CLient " + list[i]);
        }

        Utils.requestLogFiles(5, 5, list);
        return clients.size();

    }

    public static void requestLogFiles(final int clientsPerRound, final int roundGap, String[] clientList) {

        Constants.currentSession.setFetchingLogFiles(true);

//        final int 
        Runnable run = new Runnable() {
            @Override
            public void run() {

                try {
                    int requested = 0;
                    Enumeration<String> macList = Constants.currentSession.getGetLogFilefFilteredDevices().keys();
                    while (macList.hasMoreElements()) {
                        String macAddr = macList.nextElement();
                        String json = Utils.getLogFilesJson(1);
                        DeviceInfo d = Constants.currentSession.getConnectedClients().get(macAddr);
                        System.out.println("\nMac Addr : " + macAddr);
                        System.out.println("\nJson : " + json);
                        Thread sendData = new Thread(new SendData(1, d, 4, json));
                        sendData.start();
                        requested++;
                        if (requested == clientsPerRound) {
                            requested = 0;
                            try {
                                Thread.sleep(roundGap * 1000); // seconds
                            } catch (InterruptedException ex) {
                                System.out.println(ex.toString());
                            }
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("\nException ex" + ex.toString());
                }
            }
        };

        Thread t = new Thread(run);
        t.start();
//        Constants.currentSession.setFetchingLogFiles(false);

//            int totalClients = clientList.length;
//            if (clientsPerRound >= Constants.currentSession.getGetLogFilefFilteredDevices().size()) {
//
//                Enumeration<String> macList = Constants.currentSession.getGetLogFilefFilteredDevices().keys();
//                while (macList.hasMoreElements()) {
//                    String macAddr = macList.nextElement();
//                    String json = Utils.getLogFilesJson(1);
//                    DeviceInfo d = Constants.currentSession.getConnectedClients().get(macAddr);
//                    System.out.println("\nMac Addr : " + macAddr);
//                    System.out.println("\nJson : " + json);
//                    Thread sendData = new Thread(new SendData(1, d, 4, json));
//                    sendData.start();
//                }
//                for (String client : clientList) {
//
//                    String value[] = client.split("_");
//                    int expID = Integer.parseInt(value[0]);
//                    String macAddr = value[1];
//                    String json = Utils.getLogFilesJson(expID);
//                    DeviceInfo d = Constants.currentSession.getConnectedClients().get(macAddr);
//                    System.out.println("\nMac Addr : " + macAddr);
//                    System.out.println("\nJson : " + json);
//                    Thread sendData = new Thread(new SendData(expID, d, 4, json));
//                    sendData.start();
//                }
//            } else {
//
//                int requested = 0;
//
//                Enumeration<String> macList = Constants.currentSession.getGetLogFilefFilteredDevices().keys();
//                while (macList.hasMoreElements()) {
//                    String macAddr = macList.nextElement();
//                    String json = Utils.getLogFilesJson(1);
//                    DeviceInfo d = Constants.currentSession.getConnectedClients().get(macAddr);
//                    System.out.println("\nMac Addr : " + macAddr);
//                    System.out.println("\nJson : " + json);
//                    Thread sendData = new Thread(new SendData(1, d, 4, json));
//                    sendData.start();
//                    requested++;
//
//                    if (requested == clientsPerRound) {
//                        requested = 0;
//                        try {
//                            Thread.sleep(roundGap * 1000); // seconds
//                        } catch (InterruptedException ex) {
//                            System.out.println(ex.toString());
//                        }
//                    }
//                }
//                while (totalClients > 0) {
//
//                    if (totalClients > clientsPerRound) {
//
//                        for (int i = 0; i < clientsPerRound; i++) {
//                            String value[] = clientList[index].split("_");
//                            int expID = Integer.parseInt(value[0]);
//                            String macAddr = value[1];
//                            String json = Utils.getLogFilesJson(expID);
//                            DeviceInfo d = Constants.currentSession.getConnectedClients().get(macAddr);
//                            System.out.println("\nMac Addr : " + macAddr);
//                            System.out.println("\nJson : " + json);
//                            Thread sendData = new Thread(new SendData(expID, d, 4, json));
//                            sendData.start();
//                            index++;
//                        }
//                        totalClients = totalClients - clientsPerRound;
//                    } else {
//
//                        for (int i = 0; i < totalClients; i++) {
//
//                            String value[] = clientList[index].split("_");
//                            int expID = Integer.parseInt(value[0]);
//                            String macAddr = value[1];
//                            String json = Utils.getLogFilesJson(expID);
//                            DeviceInfo d = Constants.currentSession.getConnectedClients().get(macAddr);
//                            System.out.println("\nMac Addr : " + macAddr);
//                            System.out.println("\nJson : " + json);
//                            Thread sendData = new Thread(new SendData(expID, d, 4, json));
//                            sendData.start();
//                            index++;
//                        }
//                        totalClients = 0;
//                    }
//
//                    try {
//                        Thread.sleep(roundGap * 1000); // seconds
//                    } catch (InterruptedException ex) {
//                        System.out.println(ex.toString());
//                    }
//                }
    }

    public static void sendStopExperiment(int expid) {

        DBManager.updateStopExperiment(expid);
        String jsonString = Utils.getStopSignalJson();

        for (DeviceInfo d : Constants.currentSession.getFilteredClients()) {
            Thread sendData = new Thread(new SendData(Constants.currentSession.getCurrentExperimentId(), d, 1, jsonString));
            sendData.start();

        }
    }

    public static void sendHeartBeatDuration(String duration) {

        String jsonString = Utils.getHeartBeatDuration(duration);
        for (DeviceInfo d : Constants.currentSession.getConfigFilteredDevices()) {
            Thread sendData = new Thread(new SendData(Constants.currentSession.getCurrentExperimentId(), d, 5, jsonString));
            sendData.start();

        }

    }

    public static void sendServerConfiguration(String serverIP, String serverPORT, String connectionPORT) {

        String jsonString = Utils.getServerConfiguration(serverIP, serverPORT, connectionPORT);
        for (DeviceInfo d : Constants.currentSession.getConfigFilteredDevices()) {
            Thread sendData = new Thread(new SendData(Constants.currentSession.getCurrentExperimentId(), d, 6, jsonString));
            sendData.start();

        }

    }

}


// getStopSignalJson
