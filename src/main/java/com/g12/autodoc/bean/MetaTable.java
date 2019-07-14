package com.g12.autodoc.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author sjx
 * @Classname MetaTable
 * @Description Table structure bean..
 * @Date 2019/7/14 0:02
 */

@Data
public class MetaTable {
    private String tableName;
    private String tableRemarks;

    private List<TabColumn> columns = new ArrayList<>();

    private MetaTable(){}

    public static MetaTable getInstance(){
        return new MetaTable();
    }

    public void initTableInfo(String tableName,String tableRemarks){
        this.tableName = tableName;
        this.tableRemarks = tableRemarks;
    }

    public void addTabColumn(TabColumn tabColumn){
        this.columns.add(tabColumn);
    }


}
