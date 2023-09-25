package io.camunda.zeebe.exporter.api;

import io.camunda.zeebe.exporter.api.context.Context;
import io.camunda.zeebe.protocol.record.RecordType;
import io.camunda.zeebe.protocol.record.ValueType;

public class Record implements Context.RecordFilter {

    @Override
    public boolean acceptType(RecordType recordType) {
        return true;
    }

    @Override
    public boolean acceptValue(ValueType valueType) {
        return valueType == ValueType.INCIDENT;
    }
}
