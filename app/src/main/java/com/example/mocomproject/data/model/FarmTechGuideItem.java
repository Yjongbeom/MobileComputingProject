package com.example.mocomproject.data.model;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class FarmTechGuideItem {

    @Element(name = "cntntsSnn")
    private String cntntsSnn;

    @Element(name = "cntntsNm")
    private String cntntsNm;

    public String getCntntsSnn() {
        return cntntsSnn;
    }

    public String getCntntsNm() {
        return cntntsNm;
    }
}
