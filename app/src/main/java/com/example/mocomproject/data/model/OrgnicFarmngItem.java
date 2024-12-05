package com.example.mocomproject.data.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class OrgnicFarmngItem {
    @Element(name = "cntntsNo")
    private String cntntsNo;

    @Element(name = "cntntsSj")
    private String title;

    @Element(name = "svcDt")
    private String date;

    public String getCntntsNo() {
        return cntntsNo;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDate()
    {
        return date;
    }
}
