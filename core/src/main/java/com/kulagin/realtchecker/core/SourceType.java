package com.kulagin.realtchecker.core;

public enum SourceType {
    ONLINER,
    REALTBY;
    
    public static final String APARTMENTS_COLLECTION = "apartments";
    
    public String getApartmentsCollection() {
        return APARTMENTS_COLLECTION + "_" + this.name().toLowerCase();
    }
}
