package com.barracuda.fun.service.xlsx;

import java.util.Arrays;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchResultColumnHeaderName {

    SAMPLE_NAME("Sample Name"),

    SOURCE_PROCESS("Source Process"),

    ISN("ISN"),

    LATIN_NAME("Latin Name"),

    PROJECT_CODES("Project Codes"),

    PHYSICAL_FORM("Physical Form"),

    PRODUCTION_DATE("Production Date"),

    VESSEL("Vessel"),

    LINKED_CONCEPTS("Linked Concepts");

    private final String code;

    public Integer getLength() {
        return code.length();
    }

    public int getColumnNumber() {
        return ordinal();
    }

    public static SearchResultColumnHeaderName getByCode(@NonNull String code) {
        return Arrays.stream(SearchResultColumnHeaderName.values())
            .filter(e -> e.getCode().equals(code))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No such code found in SearchResultColumnHeaderName enum :" + code)); //TODO: create custom exception
    }

//    public static int getMaxLength() {
//        return Arrays.stream(SearchParameterName.values())
//            .map(SearchParameterName::getLength)
//            .max(Comparator.comparing(Integer::valueOf))
//            .orElseThrow();
//    }

}
