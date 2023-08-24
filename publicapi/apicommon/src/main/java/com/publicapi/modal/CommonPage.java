package com.publicapi.modal;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CommonPage<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer pageNum;

    private Integer pageSize;

    private Long totalPage;

    private Long total;

    private List<T> lists;

    public CommonPage(Integer pageNum, Integer pageSize, Long totalPage, Long total, List<T> lists) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.total = total;
        this.lists = lists;
    }

    public static <T> CommonPage<T> build(Integer pageNum, Integer pageSize, Long totalPage, Long total, List<T> lists){
       return new CommonPage<>(pageNum, pageSize, totalPage, total, lists);
    }

    public static <T,U> CommonPage<U> convert(CommonPage<T> commonPage,List<U> list){
        return CommonPage.build(commonPage.getPageNum(),
                commonPage.getPageSize(),
                commonPage.getTotalPage(),
                commonPage.getTotal(),
                list);
    }
}
