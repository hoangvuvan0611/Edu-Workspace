package com.vvh.coresv.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class QRCodeRequest {
    private Double latitude;
    private Double longitude;
    private Integer width;
    private Integer height;
    private Long meetingId;
    private Long expireTime;
}
