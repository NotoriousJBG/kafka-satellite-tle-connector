package io.notoriousjbg;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;
import org.apache.kafka.connect.data.Timestamp;

public class SatelliteTleSchemas {
    public static final String CONTEXT_FIELD = "@context";
    public static final String ID_FIELD = "@id";
    public static final String TYPE_FIELD = "@type";

    public static final String AVRO_CONTEXT_FIELD = "context";
    public static final String AVR0_ID_FIELD = "id";
    public static final String AVRO_TYPE_FIELD = "type";
    public static final String SATELLITE_ID_FIELD = "satelliteId";
    public static final String NAME_FIELD = "name";
    public static final String DATE_FIELD = "date";
    public static final String LINE1_FIELD = "line1";
    public static final String LINE2_FIELD = "line2";

    public static final String SCHEMA_KEY = "io.notoriousjbg.kafka.connect.tle.SatelliteTleKey";
    public static final String SCHEMA_VALUE_SATELLITE_TLE = "io.notoriousjbg.kafka.connect.tle.SatelliteTleValue";

    public static final Schema KEY_SCHEMA = SchemaBuilder.struct().name(SCHEMA_KEY)
            .version(1)
            .field(NAME_FIELD, Schema.STRING_SCHEMA)
            .field(SATELLITE_ID_FIELD, Schema.INT32_SCHEMA)
            .build();

    public static final Schema VALUE_SCHEMA = SchemaBuilder.struct().name(SCHEMA_VALUE_SATELLITE_TLE)
            .version(1)
            .field(AVRO_CONTEXT_FIELD, Schema.STRING_SCHEMA)
            .field(AVR0_ID_FIELD, Schema.STRING_SCHEMA)
            .field(AVRO_TYPE_FIELD, Schema.STRING_SCHEMA)
            .field(SATELLITE_ID_FIELD, Schema.INT32_SCHEMA)
            .field(NAME_FIELD, Schema.STRING_SCHEMA)
            .field(DATE_FIELD, Timestamp.SCHEMA)
            .field(LINE1_FIELD, Schema.STRING_SCHEMA)
            .field(LINE2_FIELD, Schema.STRING_SCHEMA)
            .build();

}
