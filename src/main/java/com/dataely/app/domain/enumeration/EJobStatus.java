package com.dataely.app.domain.enumeration;

/**
 * The EJobStatus enumeration.
 */
public enum EJobStatus {
    SUCCEEDED("Job Succeeded"),
    FAILED("Job Failed");

    private final String value;

    EJobStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
