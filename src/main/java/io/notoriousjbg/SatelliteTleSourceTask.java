package io.notoriousjbg;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.notoriousjbg.model.SatelliteTle;
import io.notoriousjbg.utils.DateUtils;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Instant;
import java.util.*;
import static io.notoriousjbg.SatelliteTleSchemas.*;
import static io.notoriousjbg.SatelliteTleSchemas.KEY_SCHEMA;
import static io.notoriousjbg.SatelliteTleSchemas.VALUE_SCHEMA;

public class SatelliteTleSourceTask extends SourceTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(SatelliteTleSourceTask.class);
    public SatelliteTleConnectorConfig config;

    protected Instant nextQuerySince;
    protected SatelliteTle satelliteTle;


    SatelliteTleApiHttpClient satelliteTleApiHttpClient;
    Map<String, Object> lastSourceOffset;

    protected int count = 0;


    @Override
    public String version() {
        return VersionUtil.getVersion();
    }

    @Override
    public void start(Map<String, String> map) {
        config = new SatelliteTleConnectorConfig(map);
        initializeLastVariables();
        satelliteTleApiHttpClient = new SatelliteTleApiHttpClient(config);

    }

    private void initializeLastVariables(){
        String jsonOffsetData = new Gson().toJson(lastSourceOffset);
        // TypeToken preserves the generic type information of the Map when deserializing the JSON string.
        TypeToken<Map<String, Object>> typeToken = new TypeToken<Map<String, Object>>() {};
        Map<String, Object> offsetData = new Gson().fromJson(jsonOffsetData, typeToken.getType());
        if(lastSourceOffset != null){
            Object updatedAt = offsetData.get(DATE_FIELD);

            if((updatedAt instanceof String)){
                nextQuerySince = Instant.parse((String) updatedAt);
            }
        }
    }

    @Override
    public List<SourceRecord> poll() throws InterruptedException {
        Thread.sleep(1000);
        final ArrayList<SourceRecord> records = new ArrayList<>();
        if(count == 0 || count == 10) {
            JSONObject tle = satelliteTleApiHttpClient.getNextSatelliteTle();
            satelliteTle = SatelliteTle.fromJson(tle);
            LOGGER.info("Fetched TLE of satellite");
            count = 1;
        } else {
            count++;
            LOGGER.info("Resent old TLE");
        }
        nextQuerySince = satelliteTleApiHttpClient.getRequestDate();

        SourceRecord sourceRecord = generateSourceRecord(satelliteTle);
        records.add(sourceRecord);
        return records;
    }

    private SourceRecord generateSourceRecord(SatelliteTle satelliteTle) {
        return new SourceRecord(
                sourcePartition(),
                sourceOffset(satelliteTle.getDate()),
                config.getTopic(),
                null, // partition will be inferred by the framework
                KEY_SCHEMA,
                buildRecordKey(satelliteTle),
                VALUE_SCHEMA,
                buildRecordValue(satelliteTle),
                satelliteTle.getDate().toEpochMilli());
    }
    @Override
    public void stop() {
        // nothing to be done
    }

    private Map<String, String> sourcePartition() {
        Map<String, String> map = new HashMap<>();
        map.put(SATELLITE_ID_FIELD, config.getSatelliteIdConfig().toString());
        return map;
    }

    private Map<String, String> sourceOffset(Instant updatedAt) {
        Map<String, String> map = new HashMap<>();
        map.put(DATE_FIELD, DateUtils.MaxInstant(updatedAt, nextQuerySince).toString());
        return map;
    }

    private Struct buildRecordKey(SatelliteTle tle){
        // Key Schema
        Struct key = new Struct(KEY_SCHEMA)
                .put(SATELLITE_ID_FIELD, config.getSatelliteIdConfig())
                .put(NAME_FIELD, tle.getName());

        return key;
    }

    public Struct buildRecordValue(SatelliteTle tle){

        Struct valueStruct = new Struct(VALUE_SCHEMA)
                .put(NAME_FIELD, tle.getName())
                .put(AVR0_ID_FIELD, tle.getId())
                .put(DATE_FIELD, Date.from(tle.getDate()))
                .put(LINE1_FIELD, tle.getLine1())
                .put(LINE2_FIELD, tle.getLine2())
                .put(AVRO_CONTEXT_FIELD, tle.getContext())
                .put(SATELLITE_ID_FIELD, tle.getSatelliteId())
                .put(AVRO_TYPE_FIELD, tle.getType());
        return valueStruct;
    }
}
