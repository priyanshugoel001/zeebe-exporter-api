package io.camunda.zeebe.exporter.api;

import io.camunda.zeebe.exporter.api.context.Context;
import io.camunda.zeebe.exporter.api.context.Controller;
import io.camunda.zeebe.protocol.record.Record;
import io.camunda.zeebe.protocol.record.intent.IncidentIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyExporter implements Exporter {
    private static final Logger log = LoggerFactory.getLogger(MyExporter.class);
    private Controller controller;

    @Override
    public void configure(Context context) throws Exception {
        log.info("inside configure");
        // Retrieve configuration parameters
//        String myConfigParam = context.getConfiguration().getArguments("");
//        int myNumericParam = context.getConfiguration().get("myNumericParam", Integer.class);

        // Validate configuration if necessary
//        if (myConfigParam == null) {
//            throw new Exception("myConfigParam is required.");
//        }

        // Perform any additional configuration tasks
        // ...

        // Logging
       // context.getLogger().info("Exporter configured with myConfigParam: " + myConfigParam);
    }

    @Override
    public void open(Controller controller) {
        this.controller=controller;
        log.info("inside open");
        // Implement the logic to open the exporter
        // This method is called when the exporter is opened
    }

    @Override
    public void close() {
        log.info("inside close");
        // Implement the logic to close the exporter
        // This method is called when the exporter is closed
    }


    @Override
    public void export(Record record) {
        log.info("inside the export");
        log.info("Record is "+record);
        try {
            if (record != null && record.getIntent() == IncidentIntent.CREATED) {
                log.info(record.toString());
                postIncident(record);
            }
        } catch (Throwable e) {
            log.error("Error thrown by Incident Exporter!");
            e.printStackTrace();
        } finally {
            if (record != null) {
                this.controller.updateLastExportedRecordPosition(record.getPosition());
            }
        }
        // Implement the logic to export records
        // This method is called for each record to be exported
    }
    private void postIncident(Record record) {
        log.info("inside the postIncident");
    }
}
//zeebe          |
//        io.camunda.zeebe.exporter.api.MyExporter -
//        Record is TypedRecordImpl{metadata=RecordMetadata{recordType=EVENT,
//        valueType=PROCESS_INSTANCE, intent=ELEMENT_ACTIVATED},
//        value={"bpmnElementType":"SERVICE_TASK","elementId":"Activity_1rxxh6n",
//        "bpmnProcessId":"create-incident","version":3,
//        "processDefinitionKey":2251799813685853,"processInstanceKey":2251799813692379,
//        "flowScopeKey":2251799813692379,"bpmnEventType":"UNSPECIFIED",
//        "parentProcessInstanceKey":-1,"parentElementInstanceKey":-1}}

// ProcessInstanceKey
// version
// ProcessInstanceId

