package com.barracuda.fun.service.xlsx;

import java.util.Arrays;
import java.util.Comparator;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchParameterName {

    SAMPLE_NAME("Name"),

    PROJECT_NAME("Project"),

    PROCESS_TEMPLATE("Process Template"),

    CONCEPT_TYPE("Concept Type"),

    CONCEPT_NAME("Concept Name");

    private final String code;

    public Integer getLength() {
        return code.length();
    }

    public static SearchParameterName getByCode(@NonNull String code) {
        return Arrays.stream(SearchParameterName.values())
            .filter(e -> e.getCode().equals(code))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No such code found in SearchParameterName enum :" + code)); //TODO: create custom exception
    }

//    public static int getMaxLength() {
//        return Arrays.stream(SearchParameterName.values())
//            .map(SearchParameterName::getLength)
//            .max(Comparator.comparing(Integer::valueOf))
//            .orElseThrow();
//    }

}
