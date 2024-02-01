package com.vvh.coresv.response.base;

import com.vvh.coresv.response.base.BaseResponse;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponseItem<T> extends BaseResponse {
    private T data;
    public BaseResponseItem(boolean success, T data){
        super(success);
        this.data = data;
    }
}
