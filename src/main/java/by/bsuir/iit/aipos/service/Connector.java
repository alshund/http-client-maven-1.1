package by.bsuir.iit.aipos.service;

import by.bsuir.iit.aipos.domain.HTTPResponse;
import by.bsuir.iit.aipos.exception.BadRequestException;
import by.bsuir.iit.aipos.exception.ServiceException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Connector {

    private final int HTTP_PORT = 80;

    private Socket clientSocket = new Socket();
    private BufferedReader input;
    private PrintWriter output;

    public HTTPResponse sendRequest(String url, String request) throws ServiceException {

        try {
            clientSocket = new Socket(InetAddress.getByName(getHost(url)), HTTP_PORT);
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(clientSocket.getOutputStream(), true);
            output.println(request);
            return getResponse();
        } catch (IOException e) {
            throw new SecurityException("Error with socket handling", e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                throw new SecurityException("Closing socket error", e);
            }
        }
    }

    private String getHost(String url) throws BadRequestException {

        ServiceFactory instance = ServiceFactory.getInstance();
        ProtocolFormatter protocolFormatter = instance.getProtocolFormatter();
        return protocolFormatter.findHost(url);
    }

    private HTTPResponse getResponse() throws ServiceException {

        try {
            HTTPResponse response = new HTTPResponse();
            boolean headerFieldHandling = true;
            for (String line = input.readLine(); input.ready() && line != null; line = input.readLine()) {
                if (!headerFieldHandling && !line.isEmpty()) {
                    response.appendEntityBody(line);
                } else if (line.isEmpty()) {
                    headerFieldHandling = false;
                } else {
                    response.appendHeaderField(line);
                }
            }
            return response;
        } catch (IOException e) {
            throw new ServiceException("Error with data reading", e);
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                throw new ServiceException("Closing buffered reader error", e);
            }
        }
    }
}
