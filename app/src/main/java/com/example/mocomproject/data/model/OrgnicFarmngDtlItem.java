package com.example.mocomproject.data.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class OrgnicFarmngDtlItem {
    @Element(name = "cntntsNo")
    private String cntntsNo;

    @Element(name = "cntntsSj")
    private String svcTitle;

    @Element(name = "svcDt")
    private String svcDate;

    @Element(name = "cn")
    private String eContent;


    public String getCntntsNo() {
        return cntntsNo;
    }

    public String getSvcTitle(){return svcTitle;}
    public String getSvcDate() { return svcDate;}
    public String geteContent() {return eContent;}


}
