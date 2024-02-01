package com.vvh.coresv.response.base;

import com.vvh.coresv.response.base.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponseListItem<T> extends BaseResponse {

    private DataList<T> data;

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataList<T>{
        private Integer total;
        private List<T> items;
    }

    public void setResult(Integer total, List<T> items){
        data = new DataList<>();
        data.setItems(items);
        data.setTotal(total);
    }

}
