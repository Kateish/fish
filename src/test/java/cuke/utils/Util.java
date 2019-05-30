package cuke.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class Util {
    public <T> T retrieveResourceFromResponse(HttpResponse response, Class<T> clazz) throws IOException {

          String jsonFromResponse = EntityUtils.toString(response.getEntity());
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(jsonFromResponse, clazz);
    }
}