FROM confluentinc/cp-kafka-connect:3.2.0

WORKDIR /kafka-connect-source-satellite-tle
COPY config config
COPY target target

VOLUME /kafka-connect-source-satellite-tle/config
VOLUME /kafka-connect-source-satellite-tle/offsets

CMD CLASSPATH="$(find target/ -type f -name '*.jar'| grep '\-package' | tr '\n' ':')" connect-standalone config/worker.properties config/SatelliteTleConnectorConfig.properties