package com.example.mocomproject.data.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class FarmTechItem {
    @Element(name = "curationNm")
    private String title;

    @Element(name = "curationSumryDtl")
    private String summary;

    @Element(name = "svcDt")
    private String serviceDate;

    @Element(name = "curationNo")
    private String curation;

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getCurationNo() {
        return curation;
    }

    public String getServiceDate() {
        return serviceDate;
    }
}
