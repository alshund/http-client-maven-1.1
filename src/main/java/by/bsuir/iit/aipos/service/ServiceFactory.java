package by.bsuir.iit.aipos.service;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private ProtocolFormatter protocolFormatter = new ProtocolFormatter();
    private Connector connector = new Connector();

    public static ServiceFactory getInstance() {
        return instance;
    }

    public ProtocolFormatter getProtocolFormatter() {
        return protocolFormatter;
    }

    public Connector getConnector() {
        return connector;
    }

    private ServiceFactory() {};
}
