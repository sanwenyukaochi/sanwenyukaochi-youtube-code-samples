package io.github.sanwenyukaochi.dto;

import org.springframework.data.web.JsonPath;
import org.springframework.data.web.ProjectedPayload;

@ProjectedPayload
public interface UserPayload {

    @JsonPath("$.data.user.id")
    Long getId();

    @JsonPath("$.data.user.info.nickname")
    String getNickname();

    @JsonPath("$.data.company.name")
    String getCompany();
}
