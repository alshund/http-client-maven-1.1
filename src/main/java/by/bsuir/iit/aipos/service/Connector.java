package by.bsuir.iit.aipos.service;

import by.bsuir.iit.aipos.domain.HTTPResponse;
import by.bsuir.iit.aipos.exception.BadRequestException;
import by.bsuir.iit.aipos.exception.ServiceException;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connector {
    private final int HTTP_PORT = 80;

    private Socket clientSocket = new Socket();
    private BufferedReader br;
    private PrintWriter pw;

    public HTTPResponse sendRequest(String url, String request) throws ServiceException {

        try {
            clientSocket = new Socket(getInetAddress(url), HTTP_PORT);
            br = initBR();
            pw = initPW();
            pw.println(request);
            return getResponse();
        } catch (IOException e) {

            throw new ServiceException("Error with socket handling", e);
        } finally {

            try {
                clientSocket.close();
            } catch (IOException e) {
                throw new ServiceException("Closing socket error", e);
            }

            pw.close();
        }
    }

    private InetAddress getInetAddress(String url) throws BadRequestException, UnknownHostException {

        ServiceFactory instance = ServiceFactory.getInstance();
        ProtocolFormatter protocolFormatter = instance.getProtocolFormatter();
        return InetAddress.getByName(protocolFormatter.findHost(url));
    }

    private BufferedReader initBR() throws IOException {

        InputStream inputStream = clientSocket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        return new BufferedReader(inputStreamReader);
    }

    private PrintWriter initPW() throws IOException {

        OutputStream outputStream = clientSocket.getOutputStream();
        return new PrintWriter(outputStream, true);
    }

    private HTTPResponse getResponse() throws ServiceException {

        try {
            HTTPResponse response = new HTTPResponse();
            boolean headerHandling = true;
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                if (!headerHandling && !line.isEmpty()) {
                    response.appendEntityBody(line);
                } else if (line.isEmpty()) {
                    headerHandling = false;
                } else {
                    response.appendHeaderField(line);
                }
            }
            return response;
        } catch (IOException e) {

            throw new ServiceException("Error with response reading", e);
        } finally {

            try {
                br.close();
            } catch (IOException e) {
                throw new ServiceException("Closing buffered reader error", e);
            }
        }
    }
}
