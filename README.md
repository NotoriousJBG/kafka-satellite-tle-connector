# Kafka Satellite TLE Source Connector

## Overview

This Kafka source connector allows you to fetch satellite Two-Line Element (TLE) data from the [TLE API](https://tle.ivanstanojevic.me/api/tle) provided by Ivan Stanojevic. TLE data provides orbital elements for Earth-orbiting satellites necessary for satellite tracking and prediction.

## Configuration

The connector requires the following configuration parameters:

- `satellite_id` (optional, default: 25544): The unique identifier for the satellite you want to retrieve TLE data for.
- `topic_name`: The Kafka topic name where the fetched TLE data will be published.

## Setup

1. Clone this repository:

   ```bash
   git clone https://github.com/your_username/kafka-satellite-tle-connector.git
   ```
2. Build the connector:

   ```bash
   cd kafka-satellite-tle-connector
   mvn clean install
   ```

3. Copy the the package from target to the Kafka Connect plugin directory:

4. Configure the connector:
   Create the SatelliteTleConnectorConfig.properties file to include the connector properties:

   ```properties
   # Connector-specific configuration
   name=satellite-tls-connector
   tasks.max=1
   connector.class=io.notoriousjbg.SatelliteTleSourceConnector
   topic=satellite-tle
   satellite.id=25544
   ```

5. Create worker.properties file to include the Kafka Connect worker properties:

   ```properties
   # for more information, visit: http://docs.confluent.io/3.2.0/connect/userguide.html#common-worker-configs
   bootstrap.servers=127.0.0.1:9092
   key.converter=org.apache.kafka.connect.json.JsonConverter
   key.converter.schemas.enable=true
   value.converter=org.apache.kafka.connect.json.JsonConverter
   value.converter.schemas.enable=true
   # we always leave the internal key to JsonConverter
   internal.key.converter=org.apache.kafka.connect.json.JsonConverter
   internal.key.converter.schemas.enable=true
   internal.value.converter=org.apache.kafka.connect.json.JsonConverter
   internal.value.converter.schemas.enable=true
   # Rest API
   rest.port=8086
   rest.host.name=127.0.0.1
   # this config is only for standalone workers
   offset.storage.file.filename=offsets/standalone.offsets
   offset.flush.interval.ms=10000
   ```

6. Start Kafka Connect in standalone mode:

   ```bash
   connect-standalone.sh {YOUR_DIRECTORY}/worker.properties {YOUR_DIRECTORY}/SatelliteTleSourceConnector.properties
   ```

## Usage
Usage
Once the connector is running, it will fetch TLE data for the specified satellite and publish it to the Kafka topic you've configured. You can consume this data from the Kafka topic using your preferred Kafka consumer.


## Contributing

Contributions are welcome! If you encounter any issues or have suggestions for improvements, please feel free to open an issue or create a pull request.


## License

This project is licensed under the MIT License.