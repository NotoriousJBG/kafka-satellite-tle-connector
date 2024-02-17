package io.notoriousjbg;

import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import org.apache.kafka.connect.errors.ConnectException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.format.DateTimeFormatter;


public class SatelliteTleApiHttpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(SatelliteTleApiHttpClient.class);

    SatelliteTleConnectorConfig config;

    private Instant requestDate;

    public SatelliteTleApiHttpClient(SatelliteTleConnectorConfig config){
        this.config = config;
    }

    protected JSONObject getNextSatelliteTle() throws InterruptedException {

        HttpResponse<JsonNode> jsonResponse;
        try {
            jsonResponse = getSatelliteTleApi();

            switch (jsonResponse.getStatus()){
                case 200:
                    requestDate = getRequestDate(jsonResponse);
                    return jsonResponse.getBody().getObject();
                case 401:
                    throw new ConnectException("Bad request provided");
                default:
                    LOGGER.error(constructUrl());
                    LOGGER.error(String.valueOf(jsonResponse.getStatus()));
                    LOGGER.error(jsonResponse.getBody().toString());
                    LOGGER.error(jsonResponse.getHeaders().toString());
                    LOGGER.error("Unknown error: Sleeping 5 seconds " +
                            "before re-trying");
                    Thread.sleep(5000L);
                    return getNextSatelliteTle();
            }
        } catch (UnirestException e) {
            LOGGER.error("Unable to do request", e);
            Thread.sleep(5000L);
            return new JSONObject();
        }
    }


    protected String constructUrl(){
        return String.format(
                "https://tle.ivanstanojevic.me/api/tle/%s",
                config.getSatelliteIdConfig().toString());
    }

    protected HttpResponse<JsonNode> getSatelliteTleApi() throws UnirestException {
        GetRequest unirest = Unirest.get(constructUrl());
        LOGGER.debug(String.format("GET %s", unirest.getUrl()));
        return unirest.asJson();
    }

    private Instant getRequestDate(HttpResponse<JsonNode> response) {
        Headers headers =  response.getHeaders();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z");
        return Instant.from(formatter.parse(headers.get("Date").get(0)));
    }

    public Instant getRequestDate() {
        return requestDate;
    }

}
