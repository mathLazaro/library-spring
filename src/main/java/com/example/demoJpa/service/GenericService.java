package com.example.demoJpa.service;

import com.example.demoJpa.domain.Auditable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenericService {

    protected String[] ignoreFields(List<String> refEntities, String... str) {

        List<String> auditable = Auditable.AUDITABLE_FIELDS;
        List<String> arr = new ArrayList<>(auditable);
        for (String ref : refEntities)
            auditable.forEach(field -> arr.add(ref + "." + field));
        arr.addAll(Arrays.stream(str).toList());
        return arr.toArray(new String[0]);
    }


}
