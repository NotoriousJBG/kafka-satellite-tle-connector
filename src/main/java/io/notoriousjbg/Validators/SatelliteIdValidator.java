package io.notoriousjbg.Validators;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigException;

public class SatelliteIdValidator implements ConfigDef.Validator {

    @Override
    public void ensureValid(String name, Object value) {
        Integer satelliteId = (Integer) value;
        if (!validateFiveDigits(satelliteId)){
            throw new ConfigException(name, value, "Satellite id must be a 5 digit number");
        }
    }

    private boolean validateFiveDigits(int num) {
        String numStr = Integer.toString(num);
        return numStr.length() == 5;
    }
}
