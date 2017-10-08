package br.com.spakoapi.excecao;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WebApiExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(WebApplicationException exception) {
        ErrorMessage error = new ErrorMessage(exception.getMessage(), exception.getResponse().getStatus());
        return Response.status(exception.getResponse().getStatus())
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
