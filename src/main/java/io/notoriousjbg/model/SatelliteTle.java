package io.notoriousjbg.model;

import com.google.gson.annotations.SerializedName;
import org.json.JSONObject;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

import static io.notoriousjbg.SatelliteTleSchemas.*;

public class SatelliteTle {

    @SerializedName("@context")
    private String context;
    @SerializedName("@id")
    private String id;
    @SerializedName("@type")
    private String type;
    private int satelliteId;
    private String name;
    private Instant date;
    private String line1;
    private String line2;

    public SatelliteTle() {}

    public SatelliteTle(String context, String id, String type, int satelliteId, String name, Instant date, String line1, String line2) {
        this.context = context;
        this.id = id;
        this.type = type;
        this.satelliteId = satelliteId;
        this.name = name;
        this.date = date;
        this.line1 = line1;
        this.line2 = line2;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public SatelliteTle withContext(String context) {
        this.context=context;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public SatelliteTle withId(String id) {
        this.id=id;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public SatelliteTle withType(String type) {
        this.type=type;
        return this;
    }

    public int getSatelliteId() {
        return satelliteId;
    }

    public void setSatelliteId(int satelliteId) {
        this.satelliteId = satelliteId;
    }
    public SatelliteTle withSatelliteId(int satelliteId) {
        this.satelliteId=satelliteId;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public SatelliteTle withName(String name) {
        this.name=name;
        return this;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }
    public SatelliteTle withDate(Instant date) {
        this.date=date;
        return this;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }
    public SatelliteTle withLine1(String line1) {
        this.line1=line1;
        return this;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public SatelliteTle withLine2(String line2) {
        this.line2=line2;
        return this;
    }

    public static SatelliteTle fromJson(JSONObject jsonObject) {

        SatelliteTle satelliteTle  = new SatelliteTle();
        satelliteTle.withId(jsonObject.getString(ID_FIELD));
        satelliteTle.withSatelliteId(jsonObject.getInt(SATELLITE_ID_FIELD));
        satelliteTle.withContext(jsonObject.getString(CONTEXT_FIELD));
        satelliteTle.withType(jsonObject.getString(TYPE_FIELD));
        satelliteTle.withLine1(jsonObject.getString(LINE1_FIELD));
        satelliteTle.withLine2(jsonObject.getString(LINE2_FIELD));
        satelliteTle.withName(jsonObject.getString(NAME_FIELD));
        String dateString = jsonObject.getString(DATE_FIELD);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        Instant instant = Instant.from(formatter.parse(dateString));
        satelliteTle.withDate(instant);

        return satelliteTle;
    }

}
