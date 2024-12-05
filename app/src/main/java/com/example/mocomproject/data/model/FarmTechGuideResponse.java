package com.example.mocomproject.data.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "response", strict = false)
public class FarmTechGuideResponse {

    @Element(name = "header")
    private Header header;

    @Element(name = "body", required = false)
    private Body body;

    public Header getHeader() {
        return header;
    }

    public Body getBody() {
        return body;
    }

    @Root(name = "header", strict = false)
    public static class Header {
        @Element(name = "resultCode")
        private String resultCode;

        @Element(name = "resultMsg")
        private String resultMsg;

        public String getResultCode() {
            return resultCode;
        }

        public String getResultMsg() {
            return resultMsg;
        }
    }

    @Root(name = "body", strict = false)
    public static class Body {
        @Element(name = "items", required = false)
        private Items items;

        public Items getItems() {
            return items;
        }

        @Root(name = "items", strict = false)
        public static class Items {
            @ElementList(inline = true, entry = "item", required = false)
            private List<FarmTechGuideItem> itemList;

            public List<FarmTechGuideItem> getItemList() {
                return itemList;
            }
        }
    }
}
