package io.notoriousjbg;

import io.notoriousjbg.Validators.SatelliteIdValidator;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import java.util.Map;

import static org.apache.kafka.common.config.ConfigDef.NO_DEFAULT_VALUE;

public class SatelliteTleConnectorConfig extends AbstractConfig {

    public static final String TOPIC_CONFIG = "topic";
    private static final String TOPIC_DOC = "Topic to write to";
    public static final String SATELLITE_ID_CONFIG = "satellite.id";
    private static final String SATELLITE_ID_DOC = "ID of the satellite to follow";
    public SatelliteTleConnectorConfig(ConfigDef config, Map<String, String> parsedConfig) {
        super(config, parsedConfig);
    }

    public SatelliteTleConnectorConfig(Map<String, String> parsedConfig) {
        this(conf(), parsedConfig);
    }

    public static ConfigDef conf() {
        return new ConfigDef()
                .define(TOPIC_CONFIG, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, TOPIC_DOC)
                .define(SATELLITE_ID_CONFIG, ConfigDef.Type.INT, 25544, new SatelliteIdValidator() , ConfigDef.Importance.HIGH, SATELLITE_ID_DOC);
    }

    public String getTopic() {
        return this.getString(TOPIC_CONFIG);
    }


    public Integer getSatelliteIdConfig() {
        return this.getInt(SATELLITE_ID_CONFIG);
    }
}
