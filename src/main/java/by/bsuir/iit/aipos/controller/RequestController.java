package by.bsuir.iit.aipos.controller;

import by.bsuir.iit.aipos.SenderModel;
import by.bsuir.iit.aipos.domain.HTTPRequest;
import by.bsuir.iit.aipos.domain.HTTPResponse;
import by.bsuir.iit.aipos.domain.http_parameters.HttpMethod;
import by.bsuir.iit.aipos.domain.http_parameters.ResponseStatus;
import by.bsuir.iit.aipos.exception.BadRequestException;
import by.bsuir.iit.aipos.exception.ServiceException;
import by.bsuir.iit.aipos.service.Connector;
import by.bsuir.iit.aipos.service.ProtocolFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import org.apache.log4j.Logger;

import java.util.Optional;

public class RequestController {

    private final Logger LOG = Logger.getLogger(RequestController.class.getName());
    @FXML
    private TextField urlField;
    @FXML
    private TextArea requestTA, responseTA, entityBodyTA;
    @FXML
    private WebView messageBody;
    @FXML
    private ComboBox httpMethods;

    private ObservableList<String> methodsList = FXCollections.observableArrayList();

    private SenderModel senderModel = new SenderModel();
    private ProtocolFormatter protocolFormatter = new ProtocolFormatter();
    private Connector connector = new Connector();

    public RequestController() {
        for (HttpMethod httpMethod : HttpMethod.values()) {
            methodsList.add(httpMethod.name());
        }
    }

    @FXML
    private void initialize() {

        httpMethods.setItems(methodsList);
        httpMethods.setValue(HttpMethod.GET.name());
    }

    public void httpRequest(ActionEvent actionEvent) {

        HTTPRequest httpRequest = createRequest();
        sendRequest(httpRequest);

    }

    private void sendRequest(HTTPRequest httpRequest) {
        try {
            String requestMessage = protocolFormatter.toMessageFormat(httpRequest);
            HTTPResponse httpResponse = connector.sendRequest(httpRequest.getUrl(), requestMessage);
            handleResponse(requestMessage, httpRequest, httpResponse);
        } catch (BadRequestException e) {
            messageBody.getEngine().loadContent("Не удается получить доступ к сайту!");
            LOG.info(e.getMessage());
        } catch (ServiceException e) {
            messageBody.getEngine().loadContent("Сервер не отвечает!");
            LOG.info(e.getMessage());
        }
    }

    private HTTPRequest createRequest() {

        String methodName = (String) httpMethods.getValue();
        String entityBody = entityBodyTA.getText();
        return new HTTPRequest(methodName, urlField.getText(), senderModel.getHeaderMap(), entityBody);
    }

    private void handleResponse(String requestMessage, HTTPRequest httpRequest, HTTPResponse httpResponse) throws BadRequestException {

        requestTA.setText(requestMessage);
        responseTA.setText(protocolFormatter.toMessageFormat(httpResponse));
        handleStatusCode(httpRequest, httpResponse);
    }

    private void handleStatusCode(HTTPRequest httpRequest, HTTPResponse httpResponse) throws BadRequestException {

        ResponseStatus responseStatus = ResponseStatus.valueOf(httpResponse.getStatusMessage().toUpperCase().replace(" ", "_"));
        messageBody.getEngine().loadContent(httpResponse.getEntityBody());
        LOG.info(responseStatus.name());
    }

    public void headerClicked(MouseEvent mouseEvent) {

        CheckBox activeCheckBox = (CheckBox) mouseEvent.getSource();
        String headerId = activeCheckBox.getId();
        if (activeCheckBox.isSelected()) {
            handlingInputDialog(activeCheckBox, headerId);
        } else {
            senderModel.removeHeader(headerId);
        }
    }

    private void handlingInputDialog(CheckBox activeCheckBox, String headerId) {

        Optional<String> inputData = createInputDialog(activeCheckBox.getText());
        if (inputData.isPresent()) {
            senderModel.addHeader(headerId, inputData.get());
        } else {
            activeCheckBox.setSelected(false);
        }
    }

    private Optional<String> createInputDialog(String headerName) {

        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle(getTitle());
        inputDialog.setHeaderText(getHeader(headerName));
        inputDialog.setContentText(getContent(headerName));
        return inputDialog.showAndWait();
    }

    private String getTitle() {
        return "Header Input";
    }

    private String getHeader(String headerName) {
        return headerName.toUpperCase() + " header!";
    }

    private String getContent(String headerName) {
        return "Input value of " + headerName + ":";
    }
}
