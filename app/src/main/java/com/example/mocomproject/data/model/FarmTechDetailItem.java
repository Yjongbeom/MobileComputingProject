package com.example.mocomproject.data.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class FarmTechDetailItem {

    @Element(name = "cntntsInfoHtml")
    private String cntntsInfo;

    public String getCntntsInfo() {
        return cntntsInfo;
    }
}

