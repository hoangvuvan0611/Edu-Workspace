package com.vvh.coresv.model;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class LocalAddress {
    private double latitude;
    private double longtitude;
}
