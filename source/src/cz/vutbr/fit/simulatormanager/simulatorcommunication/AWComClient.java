package cz.vutbr.fit.simulatormanager.simulatorcommunication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.vutbr.fit.simulatormanager.beans.AllEngineInfo;
import cz.vutbr.fit.simulatormanager.beans.AllSimulationInfo;

/**
 * Class for getting data from simulators through AWCom
 * 
 * @author zhenia
 *
 */
public class AWComClient {

    private final static Logger LOG = LoggerFactory.getLogger(AWComClient.class);

    private static final String GET_DATA_QUERY = "GET:DATA:END:";
    private static final String GET_ENGINE_QUERY = "GET:ENGINE:END:";

    public static AllSimulationInfo getSimulationData(String host, int port) {
	AllSimulationInfo simData = null;
	String response = getSimulationDataUnparsed(host, port);
	// LOG.info("Got simulation data on {}:{}. {}", host, port, response);
	// printStringLineByLine(response);
	simData = AWComParser.parseSimulatorResponse(response);
	return simData;
    }

    public static String getSimulationDataUnparsed(String host, int port) {
	String query = GET_DATA_QUERY;
	Socket queryProcessorSocket = createSocket(host, port);
	PrintWriter out = getPrintWriter(queryProcessorSocket);
	BufferedReader queryProcessorReader = getQueryProcessorReader(queryProcessorSocket);
	out = writeQuery(query, out);
	String response = getResponseFromSocket(queryProcessorReader);
	closeSocket(queryProcessorSocket);
	return response;
    }

    /**
     * Get engine data from simulator
     * 
     * @param host
     *            where simulator is located
     * @param port
     *            port on which AWCom is installed on this simulator
     * @return
     */
    public static AllEngineInfo getEngineData(String host, int port) {
	AllEngineInfo engineData = null;
	String response = getEngineDataUnparsed(host, port);
	// System.out.println("From " + host + " engines " + response);
	// if we got some data, parse it
	// LOG.info("Got engine data on {}:{}. {}", host, port, response);
	// printStringLineByLine(response);
	if ((response != null) && (response.length() > 15)) {
	    engineData = AWComParser.parseEngineResponse(response);
	}
	return engineData;
    }

    public static String getEngineDataUnparsed(String host, int port) {
	String query = GET_ENGINE_QUERY;
	Socket queryProcessorSocket = createSocket(host, port);
	PrintWriter out = getPrintWriter(queryProcessorSocket);
	BufferedReader queryProcessorReader = getQueryProcessorReader(queryProcessorSocket);
	out = writeQuery(query, out);
	String response = getResponseFromSocket(queryProcessorReader);
	closeSocket(queryProcessorSocket);
	return response;
    }

    @SuppressWarnings("unused")
    private static void printStringLineByLine(String value) {
	LOG.info("going to print line by line");
	if (value != null && !value.isEmpty()) {
	    LOG.info("not empty splitting");
	    String[] responseArr = value.split(":");
	    LOG.info("length" + responseArr.length);
	    for (int i = 0; i < responseArr.length; i++) {
		LOG.info("Response val: " + responseArr[i]);
	    }
	}
    }

    private static PrintWriter getPrintWriter(Socket queryProcessorSocket) {
	PrintWriter out = null;
	DataOutputStream dataOutputStream = getDataOutputStream(queryProcessorSocket);
	if (dataOutputStream != null) {
	    out = new PrintWriter(dataOutputStream, true);
	}
	return out;
    }

    private static Socket createSocket(String host, int port) {
	Socket queryProcessorSocket = null;
	try {
	    queryProcessorSocket = new Socket(host, port);
	} catch (Exception e) {
	    // e.printStackTrace();
	    // System.out.println("Cant creat socket for host: " + host);
	}
	return queryProcessorSocket;
    }

    private static DataOutputStream getDataOutputStream(Socket socket) {
	DataOutputStream queryProcessorDos = null;
	if (socket != null) {
	    try {
		queryProcessorDos = new DataOutputStream(socket.getOutputStream());
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	return queryProcessorDos;
    }

    private static BufferedReader getQueryProcessorReader(Socket socket) {
	BufferedReader queryProcessorReader = null;
	if (socket != null) {
	    try {
		queryProcessorReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	return queryProcessorReader;
    }

    private static PrintWriter writeQuery(String query, PrintWriter out) {
	if (out != null) {
	    out.println(query);
	}
	return out;
    }

    private static String getResponseFromSocket(BufferedReader queryProcessorReader) {
	String response = null;
	if (queryProcessorReader != null) {
	    try {
		response = queryProcessorReader.readLine();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	return response;
    }

    private static void closeSocket(Socket socket) {
	if ((socket != null) && (!socket.isClosed())) {
	    try {
		socket.close();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

}
