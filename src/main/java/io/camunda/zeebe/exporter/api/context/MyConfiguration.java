package io.camunda.zeebe.exporter.api.context;

import io.camunda.zeebe.exporter.api.MyExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;

public class MyConfiguration implements Configuration {
    private final String id;
    private static final Logger log = LoggerFactory.getLogger(MyExporter.class);

    private final Map<String, Object> arguments;

    public MyConfiguration(String id, Map<String, Object> arguments) {
        this.id = id;
        this.arguments = arguments;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Map<String, Object> getArguments() {
        log.info("inside getArguments");
        return arguments;
    }

    @Override
    public <T> T instantiate(Class<T> configClass) {
        log.info("inside instantiate");
        try {
            // Create an instance of the configuration class
            T instance = configClass.getDeclaredConstructor().newInstance();

            // Iterate through the argument map and set fields in the instance
            for (Map.Entry<String, Object> entry : arguments.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                // Use reflection to set the field
                Field field = configClass.getDeclaredField(key);
                field.setAccessible(true); // Ensure the field is accessible
                field.set(instance, value); // Set the field value

                // You may need to handle type conversions for arguments if necessary
                // For example, if the field is of type int and the value is a String, perform the conversion
                // Example: if (field.getType() == int.class) field.setInt(instance, Integer.parseInt(value.toString()));
            }

            return instance;
        } catch (Exception e) {
            // Handle exceptions here (e.g., NoSuchFieldException, IllegalAccessException, etc.)
            // You can log or throw an exception as needed
            throw new RuntimeException("Error instantiating configuration", e);
        }
    }

}
