package com.vvh.coresv.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class SetupDataRequest {
    @Getter
    @Setter
    @AllArgsConstructor
    public class DataAppRequestDTO {
        @NotBlank(message = "userId must be not blank")
        private String userId;
        @NotBlank(message = "semester must be not blank")
        private String semester;
    }
}
