package com.blaife.model;

import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(prefix = "pre_", chain = true)
@Setter
public class Accessors03_Prefix {
    private String pre_attribute1;
    private String attribute2;
}
