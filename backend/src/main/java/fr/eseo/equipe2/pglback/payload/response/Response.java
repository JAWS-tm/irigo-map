package fr.eseo.equipe2.pglback.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.experimental.Accessors;

import java.util.Date;

//@Getter
//@Setter
//@Accessors(chain = true)
//@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response<T> {

    private HttpStatus status;
    private T payload;
    private Object errors;
    private Object metadata;

    public static <T> Response<T> badRequest() {
        Response<T> response = new Response<>();
        response.setStatus(HttpStatus.BAD_REQUEST);
        return response;
    }

    public static <T> Response<T> ok() {
        Response<T> response = new Response<>();
        response.setStatus(HttpStatus.OK);
        return response;
    }

    public static <T> Response<T> unauthorized() {
        Response<T> response = new Response<>();
        response.setStatus(HttpStatus.UNAUTHORIZED);
        return response;
    }

    public static <T> Response<T> accessDenied() {
        Response<T> response = new Response<>();
        response.setStatus(HttpStatus.FORBIDDEN);
        return response;
    }

    public static <T> Response<T> exception() {
        Response<T> response = new Response<>();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return response;
    }

    public static <T> Response<T> notFound() {
        Response<T> response = new Response<>();
        response.setStatus(HttpStatus.NOT_FOUND);
        return response;
    }

    public static <T> Response<T> duplicateEntity() {
        Response<T> response = new Response<>();
        response.setStatus(HttpStatus.CONFLICT);
        return response;
    }

    public Response<T> addErrorMsgToResponse(String errorMsg, Exception ex) {
        ResponseError error = new ResponseError()
                .setDetails(errorMsg)
                .setMessage(ex.getMessage())
                .setTimestamp(new Date());
        setErrors(error);
        return this;
    }

    public Response<T> addErrorMsgToResponse(String errorMsg) {
        ResponseError error = new ResponseError()
                .setMessage(errorMsg)
                .setTimestamp(new Date());
        setErrors(error);
        return this;
    }

    public ResponseEntity<Response<T>> build() {
        return new ResponseEntity<Response<T>>(this, this.status);
    }

    public Response() {
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Response<T> setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public T getPayload() {
        return payload;
    }

    public Response<T> setPayload(T payload) {
        this.payload = payload;
        return this;
    }

    public Object getErrors() {
        return errors;
    }

    public Response<T> setErrors(Object errors) {
        this.errors = errors;
        return this;
    }
}