package by.bsuir.iit.aipos.controller;

import by.bsuir.iit.aipos.domain.HTTPRequest;
import by.bsuir.iit.aipos.exception.BadResponseException;
import by.bsuir.iit.aipos.domain.http_parameters.HttpMethod;
import by.bsuir.iit.aipos.domain.HTTPResponse;
import by.bsuir.iit.aipos.service.Connector;
import by.bsuir.iit.aipos.service.ProtocolFormatter;
import by.bsuir.iit.aipos.exception.BadRequestException;
import by.bsuir.iit.aipos.exception.ServiceException;
import by.bsuir.iit.aipos.domain.http_parameters.ResponseStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;

import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

public class RequestController {

    @FXML
    private TextField urlField, responseCode;
    @FXML
    private TextArea requestTA, responseTA;
    @FXML
    private WebView messageBody;
    @FXML
    private ComboBox httpMethods;
    private ObservableList<String> methodsList = FXCollections.observableArrayList();

    private Map<String, String> headerField = new HashMap<>();

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

        try {
            HTTPRequest httpRequest = createRequest();
            String requestMessage = protocolFormatter.toMessageFormat(httpRequest);
            HTTPResponse httpResponse = connector.sendRequest(httpRequest.getUrl(), requestMessage);
            handleResponse(requestMessage, httpResponse);
        } catch (BadRequestException e) {
            messageBody.getEngine().loadContent("Не удается получить доступ к сайту!");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    private HTTPRequest createRequest() {

        return new HTTPRequest((String) httpMethods.getValue(), urlField.getText(), headerField);
    }

    private void handleResponse(String requestMessage, HTTPResponse httpResponse) {

        requestTA.setText(requestMessage);
        responseTA.setText(httpResponse.getHeaderField());
        handleStatusCode(httpResponse);
    }

    private void handleStatusCode(HTTPResponse httpResponse) {

        try {
            ResponseStatus responseStatus = protocolFormatter.getStatusCode(httpResponse.getHeaderField());
            messageBody.getEngine().loadContent(httpResponse.getEntityBody());

//            switch (responseStatus) {
//                case OK:
//                    messageBody.getEngine().loadContent(httpResponse.getEntityBody());
//                    break;
//                default:
//                    messageBody.getEngine().loadContent(responseStatus.getMessage());
//            }
            responseCode.setText(responseStatus.getMessage());
        } catch (BadResponseException e) {
            messageBody.getEngine().loadContent(e.toString());
        }
    }

    public void headerClicked(MouseEvent mouseEvent) {

        CheckBox activeCheckBox = (CheckBox) mouseEvent.getSource();
        String headerId = activeCheckBox.getId();
        if (activeCheckBox.isSelected()) {
            handlingInputDialog(activeCheckBox, headerId);
        } else {
            headerField.remove(headerId);
        }
    }

    private void handlingInputDialog(CheckBox activeCheckBox, String headerId) {

        Optional<String> inputData = createInputDialog(headerId);
        if (inputData.isPresent()) {
            headerField.put(headerId, inputData.get());
        } else {
            activeCheckBox.setSelected(false);
        }
    }

    private Optional<String> createInputDialog(String headerName) {

        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle(headerName);
        inputDialog.setContentText(headerName);
        inputDialog.setHeaderText(headerName);
        return inputDialog.showAndWait();
    }
}
