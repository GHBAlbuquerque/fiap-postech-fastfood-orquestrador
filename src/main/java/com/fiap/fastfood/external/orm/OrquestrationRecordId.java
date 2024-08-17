package com.fiap.fastfood.external.orm;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;

public class OrquestrationRecordId {

    private String sagaId;
    private String stepId;

    public OrquestrationRecordId() {
    }

    public OrquestrationRecordId(String sagaId, String stepId) {
        this.sagaId = sagaId;
        this.stepId = stepId;
    }

    //@DynamoDBHashKey(attributeName = "sagaId")
    public String getSagaId() {
        return sagaId;
    }

    public void setSagaId(String sagaId) {
        this.sagaId = sagaId;
    }

    //@DynamoDBRangeKey(attributeName = "stepId")
    public String getStepId() {
        return stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId;
    }

}
