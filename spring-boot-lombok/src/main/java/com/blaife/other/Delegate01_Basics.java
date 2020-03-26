package com.blaife.other;

import lombok.experimental.Delegate;

import java.util.ArrayList;
import java.util.Collection;

public class Delegate01_Basics {
    private interface SimpleCollection {
        boolean add(String item);
        boolean remove(Object item);
    }

    @Delegate(types=SimpleCollection.class)
    private final Collection<String> collection = new ArrayList<String>();
}
