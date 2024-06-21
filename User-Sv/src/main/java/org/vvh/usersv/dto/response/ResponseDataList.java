package org.vvh.usersv.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
public class ResponseDataList<T> {
    private final int total;
    private final List<T> dataList;

    public ResponseDataList( List<T> dataList) {
        this.dataList = dataList;
        this.total = dataList.size();
    }
}
