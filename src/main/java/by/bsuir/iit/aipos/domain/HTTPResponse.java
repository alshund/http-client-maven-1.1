package by.bsuir.iit.aipos.domain;

import by.bsuir.iit.aipos.service.FormatterUtil;

import java.io.Serializable;

public class HTTPResponse implements Serializable {

    private static final long serialVersionUID = 78438895578939905L;

    private StringBuilder headerField = new StringBuilder();
    private StringBuilder entityBody = new StringBuilder();

    public String getHeaderField() {
        return headerField.toString();
    }

    public void appendHeaderField(String headerFieldLine) {
        this.headerField.append(headerFieldLine + FormatterUtil.CRLF);
    }

    public String getEntityBody() {
        return entityBody.toString();
    }

    public void appendEntityBody(String entityBodyLine) {
        this.entityBody.append(entityBodyLine + FormatterUtil.CRLF);
    }
}
