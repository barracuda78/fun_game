package com.barracuda.fun.api.dto;

import com.barracuda.fun.api.dto.FunDto.FunDtoBuilder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = FunDtoBuilder.class)
public class FunDto {

    Long id;

    @NotNull
    @NonNull
    String name;

    @NotNull
    @NonNull
    Boolean isActive;

    @JsonPOJOBuilder(withPrefix = "")
    public static class FunDtoBuilder {

    }

}
