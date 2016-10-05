/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iitb.cse;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author ratheeshkv
 */
class ConnectionInfo {

    String ip_addr;
    int port;

    public ConnectionInfo(String ip_addr, int port) {
        this.ip_addr = ip_addr;
        this.port = port;
    }

}

public class ClientConnection {

    private static ClientConnection connObj = new ClientConnection();

    static int threadNo = 0;
    static boolean acceptConnection = true;

    public static synchronized void startlistenForClients(final Session session) {
        try {

            if (session.connectionSocket != null) {
                stoplistenForClients(session);
                System.out.println("\nExisting Port closed");
                try {
                    Thread.sleep(5000);
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
                startlistenForClients(session);
            }

            session.connectionSocket = new ServerSocket(Constants.ConnectionPORT);
            Constants.listenOnPort = true;
            while (true && acceptConnection) {
                System.out.println("\nListening for Client to Connect ......");
                final Socket sock = session.connectionSocket.accept();
                System.out.println("\nClient COnnected ......");
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        threadNo++;
                        ClientConnection.handleConnection(sock, session, threadNo);
                    }
                };

                Thread t = new Thread(r);

                t.start();

            }
            System.out.println("\nStopping Listening!!!!!!!1");
        } catch (IOException ex) {
            try {
                if (session.connectionSocket != null) {
                    stoplistenForClients(session);

                }
            } catch (Exception ex1) {
                System.out.println("\nException" + ex.toString() + "\n");
            }
        }
    }

    public static void stoplistenForClients(Session session) {

        acceptConnection = false;
        Constants.currentSession = null;
        Constants.listenOnPort = false;

        if (Constants.dbManager != null) {
            Constants.dbManager.closeConnection();
        }
        Constants.dbManager = null;
        try {
            session.connectionSocket.close();
            session.connectionSocket = null;
            System.out.println("\nServer PORT Closed...Listening stopped!!!");
        } catch (IOException ex) {
            System.out.println("\nIOException : " + ex.toString() + "\n");
        } catch (Exception ex) {
            System.out.println("\nException" + ex.toString() + "\n");
        }
    }

    public static void writeToMyLog(int expid, String macAdrress, String message) {

        String location = "";

        if (!Constants.experimentDetailsDirectory.endsWith("/")) {
            location = Constants.experimentDetailsDirectory + "/";
        }

        location += Integer.toString(expid) + macAdrress + "_log.txt";
        File file = new File(location);
        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            Date date = new Date();
            System.out.println(date);
            bw.write(date.toString() + " --> " + message + "\n");
            bw.close();
        } catch (IOException ex) {
            System.out.println("\nEXCEPTION [writeToMyLog]:" + ex.toString());
        }
    }

    public static String readFromStream(Socket socket, DataInputStream din, DataOutputStream dos) throws IOException {

        if (socket != null) {
            System.out.println("\nTrying to read from socket");
//            synchronized (socket) {
            System.out.println("\nRead from socket");
            String data = "";
            int length = din.readInt();
            System.out.println("\nR Json length : " + length);
            for (int i = 0; i < length; ++i) {
                data += (char) din.readByte();
                //     System.out.println("\nR Read: Json length : "+(i+1)*8);
                //     System.out.println("\nSuccess : Json byte");
            }
            System.out.println("\nR Success : Json byte Complete");
            //         dos.writeInt(200);
            //         System.out.println("\nR Success : Json Write 200");
            //        dos.flush();
            System.out.println("\nRead from Socket Success!!!");
            /* try {
//                if (din.available() > 0) {
                    int length = din.readInt();
                    System.out.println("\nR Json length : " + length);
                    for (int i = 0; i < length; ++i) {
                        data += (char) din.readByte();
                        //     System.out.println("\nR Read: Json length : "+(i+1)*8);
                        //     System.out.println("\nSuccess : Json byte");
                    }
                    System.out.println("\nR Success : Json byte Complete");
                    //         dos.writeInt(200);
                    //         System.out.println("\nR Success : Json Write 200");
                    //        dos.flush();
                    System.out.println("\nRead from Socket Success!!!");
 //               }
            } catch (IOException ex) {
                System.out.println("\n[1] IOEx :" + ex.toString() + "-->" + socket);
            } catch (Exception ex) {
                System.out.println("\n[2] Ex :" + ex.toString() + "-->" + socket);
            }*/
            return data;
//            }
        } else {
            return null;
        }
    }

    public static int writeToStream(DeviceInfo d, String json, String message) throws IOException {

        if (d.socket != null) {
            System.out.println("\nTrying to Write to socket");
//            synchronized (d.socket) {
            System.out.println("\nWriting to socket");
            int response = 200;

            d.outStream.writeInt(json.length());
            System.out.println("\nW Json Length");
            d.outStream.writeBytes(json);
            d.outStream.flush();
            System.out.println("\nW Success: Json String");

            /*   try {
//                synchronized (connObj) {
                    d.outStream.writeInt(json.length());
                    System.out.println("\nW Json Length");
                    d.outStream.writeBytes(json);
                    d.outStream.flush();
                    System.out.println("\nW Success: Json String");
  //              }
                //d.outStream.writeInt(message.length());
                //d.outStream.writeBytes(message);
                //    d.outStream.flush();
                //       response = d.inpStream.readInt();
                System.out.println("\nWrite to Socket Success!!! Res:" + response);
            } catch (IOException ex) {
                System.out.println("\n[3] IOEx :" + ex.toString() + "-->" + d.socket);
            } catch (Exception ex) {
                System.out.println("\n[4] Ex :" + ex.toString() + "-->" + d.socket);
            }*/
            return response;
//            }
        } else {
            System.out.println("\nNulll Socket : Mac = " + d.macAddress);
            return 408;
        }
    }

    static void handleConnection(Socket sock, Session session, int tid) {

        System.out.println("\n\n\n---------------->>>>>>>>>[" + tid + "]");

        try {
            int count = 0;
            boolean newConnection = true;
            String ip_add = sock.getInetAddress().toString();
            String[] _ip_add = ip_add.split("/");

            String macAddress = "";
            DeviceInfo myDevice = null;
            InputStream in = sock.getInputStream();
            OutputStream out = sock.getOutputStream();
            DataInputStream dis = new DataInputStream(in);
            DataOutputStream dos = new DataOutputStream(out);

            while (true) {

                System.out.println("\n[" + tid + "] My Socket : " + sock);

                String receivedData = ClientConnection.readFromStream(sock, dis, dos).trim();
                if (receivedData.equals("") || receivedData == null) {
                    System.out.println("\n[Empty/Null Data][" + tid + "]");

                } else {

                    System.out.println("\nReceived : " + receivedData);

                    Map<String, String> jsonMap = null;
                    JSONParser parser = new JSONParser();

                    ContainerFactory containerFactory = new ContainerFactory() {

                        @SuppressWarnings("rawtypes")
                        @Override
                        public List creatArrayContainer() {
                            return new LinkedList();
                        }

                        @SuppressWarnings("rawtypes")
                        @Override
                        public Map createObjectContainer() {
                            return new LinkedHashMap();
                        }
                    };

                    try {
                        jsonMap = (Map<String, String>) parser.parse(receivedData, containerFactory);

                        if (jsonMap != null) {

                            String action = jsonMap.get(Constants.action);

                            if (action.compareTo(Constants.heartBeat) == 0 || action.compareTo(Constants.heartBeat1) == 0 || action.compareTo(Constants.heartBeat2) == 0) {

                                macAddress = jsonMap.get(Constants.macAddress);
                                // heartbeat    
                                System.out.println("\n [" + tid + "] HeartBeat Received : " + (++count));

                                DeviceInfo device = session.connectedClients.get(jsonMap.get(Constants.macAddress));
                                if (device == null) { // first time from this device. ie new connection

                                    System.out.println("<<<== 1 ==>>>");
                                    DeviceInfo newDevice = new DeviceInfo();
                                    newDevice.setIp(jsonMap.get(Constants.ip));
                                    newDevice.setPort(Integer.parseInt(jsonMap.get(Constants.port)));
                                    newDevice.setMacAddress(jsonMap.get(Constants.macAddress));
                                    newDevice.setBssid(jsonMap.get(Constants.bssid));
                                    newDevice.setSsid(jsonMap.get(Constants.ssid));
                                    // newDevice.setSsid(jsonMap.get(Constants.bssidList));

                                    /* String apInfo = jsonMap.get(Constants.bssidList);

                                    if (apInfo != null || !apInfo.equals("")) {
                                          System.out.println("\nInside Bssid List1");
                                        String[] bssidInfo = apInfo.split(";");
                                        NeighbourAccessPointDetails[] obj = new NeighbourAccessPointDetails[bssidInfo.length];

                                        for (int i = 0; i < bssidInfo.length; i++) {
                                            String[] info = bssidInfo[i].split(",");
                                            obj[i].setBssid(info[0]);
                                            obj[i].setRssi(info[1]);
                                            obj[i].setRssi(info[2]);
                                        }
                                        newDevice.setBssidList(obj);
                                    }*/
                                    Date date = Utils.getCurrentTimeStamp();
                                    newDevice.setLastHeartBeatTime(date);
                                    newDevice.setInpStream(dis);
                                    newDevice.setOutStream(dos);
                                    newDevice.setConnectionStatus(true);
                                    newDevice.setThread(Thread.currentThread());
                                    newDevice.setSocket(sock);
                                    newDevice.setGetlogrequestsend(false);

                                    /*
                                    remaining parameters needs to be added!!!
                                     */
                                    session.connectedClients.put(jsonMap.get(Constants.macAddress), newDevice);

                                } else // subsequent heartbeats /  reconnection from same client
                                 if (newConnection) { // reconnection from same client

                                        System.out.println("<<<== 2 ==>>>");
                                        if (device.thread != null) {
                                            device.thread.interrupt();
                                            System.out.println("\n@#1[" + tid + "] Interrupting old thread");
                                        }

                                        DeviceInfo newDevice = new DeviceInfo();
                                        newDevice.setIp(jsonMap.get(Constants.ip));
                                        newDevice.setPort(Integer.parseInt(jsonMap.get(Constants.port)));
                                        newDevice.setMacAddress(jsonMap.get(Constants.macAddress));

                                        newDevice.setBssid(jsonMap.get(Constants.bssid));
                                        newDevice.setSsid(jsonMap.get(Constants.ssid));

                                        /* String apInfo = jsonMap.get(Constants.bssidList);
                                        if (apInfo != null || !apInfo.equals("")) {
                                            System.out.println("\nInside Bssid List");
                                                    
                                            String[] bssidInfo = apInfo.split(";");
                                            NeighbourAccessPointDetails[] obj = new NeighbourAccessPointDetails[bssidInfo.length];
                                            for (int i = 0; i < bssidInfo.length; i++) {
                                                String[] info = bssidInfo[i].split(",");
                                                obj[i].setBssid(info[0]);
                                                obj[i].setRssi(info[1]);
                                                obj[i].setRssi(info[2]);
                                            }
                                            newDevice.setBssidList(obj);
                                        }*/
                                        Date date = Utils.getCurrentTimeStamp();
                                        newDevice.setLastHeartBeatTime(date);
                                        newDevice.setInpStream(dis);
                                        newDevice.setOutStream(dos);
                                        newDevice.setSocket(sock);

                                        newDevice.setThread(Thread.currentThread());
                                        newDevice.setConnectionStatus(true);
                                        newDevice.setGetlogrequestsend(false);
                                        /*
                                        remaining parameters needs to be added!!!
                                         */
                                        session.connectedClients.remove(device.macAddress);
                                        session.connectedClients.put(jsonMap.get(Constants.macAddress), newDevice);

                                        if (session.filteredClients.contains(device)) {
                                            session.filteredClients.remove(device);
                                            session.filteredClients.add(newDevice);
                                        }

                                    } else { // heartbeat

                                        System.out.println("<<<== 3 ==>>>");

                                        Date date = Utils.getCurrentTimeStamp();
                                        device.setLastHeartBeatTime(date);
                                        device.setSocket(sock);
                                        device.setConnectionStatus(true);
                                    }

                            } else if (action.compareTo(Constants.experimentOver) == 0) {

                                macAddress = jsonMap.get(Constants.macAddress);

                                System.out.println("\n[" + tid + "] Experiment Over Mesage received");
                                // experiment over
                                // i need mac address from here
                                // ip and port also preferred
                                DeviceInfo device = session.connectedClients.get(jsonMap.get(Constants.macAddress));

                                if (device == null) { // new connection

                                    System.out.println("<<<== 4 ==>>>");

                                    DeviceInfo newDevice = new DeviceInfo();
                                    newDevice.setIp(jsonMap.get(Constants.ip));
                                    newDevice.setPort(Integer.parseInt(jsonMap.get(Constants.port)));
                                    newDevice.setMacAddress(jsonMap.get(Constants.macAddress));
                                    //Date date = Utils.getCurrentTimeStamp();
                                    //newDevice.setLastHeartBeatTime(date);
                                    newDevice.setInpStream(dis);
                                    newDevice.setOutStream(dos);
                                    newDevice.setSocket(sock);
                                    newDevice.setThread(Thread.currentThread());
                                    newDevice.setGetlogrequestsend(false);
                                    newDevice.setConnectionStatus(true);

                                    newDevice.setExpOver(1); //

                                    if (DBManager.updateExperimentOverStatus(Integer.parseInt(jsonMap.get(Constants.experimentNumber)), newDevice.getMacAddress())) {
                                        System.out.println("\nDB Update ExpOver Success");
                                    } else {
                                        System.out.println("\nDB Update ExpOver Failed");
                                    }

                                    /*
                                        remaining parameters needs to be added!!!
                                     */
                                    session.connectedClients.put(jsonMap.get(Constants.macAddress), newDevice);

                                } else if (newConnection) { // reconnction from the same client

                                    System.out.println("<<<== 5 ==>>>");

                                    if (device.thread != null) {
                                        device.thread.interrupt();
                                        System.out.println("\n@#2[" + tid + "] Interrupting old thread");
                                    }

                                    DeviceInfo newDevice = new DeviceInfo();
                                    newDevice.setIp(jsonMap.get(Constants.ip));
                                    newDevice.setPort(Integer.parseInt(jsonMap.get(Constants.port)));
                                    newDevice.setMacAddress(jsonMap.get(Constants.macAddress));
                                    //Date date = Utils.getCurrentTimeStamp();
                                    //newDevice.setLastHeartBeatTime(date);
                                    newDevice.setInpStream(dis);
                                    newDevice.setOutStream(dos);
                                    newDevice.setSocket(sock);

                                    newDevice.setThread(Thread.currentThread());
                                    newDevice.setGetlogrequestsend(false);
                                    newDevice.setConnectionStatus(true);

                                    /*
                                        remaining parameters needs to be added!!!
                                     */
                                    newDevice.setExpOver(1); //

                                    if (DBManager.updateExperimentOverStatus(Integer.parseInt(jsonMap.get(Constants.experimentNumber)), newDevice.getMacAddress())) {
                                        System.out.println("\nDB Update ExpOver Success");
                                    } else {
                                        System.out.println("\nDB Update ExpOver Failed");
                                    }

                                    session.connectedClients.remove(device.macAddress);
                                    session.connectedClients.put(jsonMap.get(Constants.macAddress), newDevice);

                                    if (session.filteredClients.contains(device)) {
                                        session.filteredClients.remove(device);
                                        session.filteredClients.add(newDevice);
                                    }

                                } else {

                                    System.out.println("<<<== 6 ==>>>");

                                    // alread connected client
                                    // device.setExpOver(jsonMap.get(Constants.macAddress))
                                    device.setConnectionStatus(true);
                                    device.setSocket(sock);
                                    device.setExpOver(1); //

                                    if (DBManager.updateExperimentOverStatus(Integer.parseInt(jsonMap.get(Constants.experimentNumber)), device.getMacAddress())) {
                                        System.out.println("\nDB Update ExpOver Success");
                                    } else {
                                        System.out.println("\nDB Update ExpOver Failed");
                                    }

                                }

                            } else if (action.compareTo(Constants.acknowledgement) == 0) {

                                System.out.println("\nAcknowledgement Received -->");
                                int expNumber = Integer.parseInt(jsonMap.get(Constants.experimentNumber));
                                System.out.println("\nExperiment number : " + expNumber);
                                //int sessionId = Utils.getCurrentSessionID();
                                int expId = 1;
///important                                int expId =1;// Utils.getCurrentExperimentID(Integer.toString(1));
                                System.out.println("\nExperiment number : " + expNumber + "== " + expId);

                                //            if (expNumber == expId) {
                                if (macAddress != null && !macAddress.equals("")) {
                                    DeviceInfo device = session.connectedClients.get(macAddress);
                                    session.actualFilteredDevices.add(device);
                                    System.out.println("\n Ack : " + expNumber + " Acknowledgement Received!!!");

                                    if (DBManager.updateControlFileSendStatus(expNumber, macAddress, 1, "Successfully sent Control File")) {
                                        System.out.println("\n Ack : " + expNumber + " DB updated Successfully");
                                    } else {
                                        System.out.println("\n Ack : " + expNumber + " DB updation Failed");
                                    }
///important                                        Utils.addExperimentDetails(expId, device, false);
                                }
                                //              }
                                // update the db.
                            } else {
                                System.out.println("\n[" + tid + "] Some Other Operation...");
                            }
                            newConnection = false;
                        }
                    } catch (Exception ex) {
                        System.out.println("Json Ex : " + ex.toString());
                    }
                }

                try {
                    Thread.sleep(5000);  // wait for interrupt
                } catch (InterruptedException ex) {
                    System.out.println("\n[" + tid + "] InterruptedException 1 : " + ex.toString() + "\n");

                    try {
                        sock.close();
                    } catch (IOException ex1) {
                        System.out.println("\n[" + tid + "] IOException5 : " + ex1.toString() + "\n");
                    }
                    break; //
                }
            }

        } catch (IOException ex) {
            System.out.println("\n [" + tid + "] IOException1 : " + ex.toString() + "\n");
            try {
                sock.close();
                //    session.connectedClients.remove(conn);
            } catch (IOException ex1) {
                System.out.println("\n[" + tid + "] IOException2 : " + ex1.toString() + "\n");
            }
        } catch (Exception ex) {
            System.out.println("\n[" + tid + "] IOException3 : " + ex.toString() + "\n");
            try {
                sock.close();
                //    session.connectedClients.remove(conn);
            } catch (IOException ex1) {
                System.out.println("\n[" + tid + "] IOException4 : " + ex1.toString() + "\n");
            }
        }

    }
}
