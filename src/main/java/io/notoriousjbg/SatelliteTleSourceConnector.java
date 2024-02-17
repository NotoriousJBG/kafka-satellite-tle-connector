package io.notoriousjbg;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SatelliteTleSourceConnector extends SourceConnector {
    private SatelliteTleConnectorConfig config;

    public void start(Map<String, String> map) {
        config = new SatelliteTleConnectorConfig(map);
    }


    public Class<? extends Task> taskClass() {
        return SatelliteTleSourceTask.class;
    }


    @Override
    public List<Map<String, String>> taskConfigs(int i) {
        // Define the individual task configurations that will be executed.
        ArrayList<Map<String, String>> configs = new ArrayList<>(1);
        configs.add(config.originalsStrings());
        return configs;
    }

    @Override
    public void stop() {
        // Do things that are necessary to stop your connector.
        // nothing is necessary to stop for this connector
    }

    @Override
    public ConfigDef config() {
        return SatelliteTleConnectorConfig.conf();
    }


    @Override
    public String version() {
        return VersionUtil.getVersion();
    }

}
