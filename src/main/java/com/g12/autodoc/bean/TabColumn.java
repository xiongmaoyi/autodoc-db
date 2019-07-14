package com.g12.autodoc.bean;

import lombok.Data;

/**
 * @Author sjx
 * @Classname TabColumn
 * @Description Column
 * @Date 2019/7/14 0:04
 */
@Data
public class TabColumn {

    private String colName;
    private String dataType;
    private String typeName;
    private String colSize;
    private String nullAble;
    private String dsc;
    private String defValue;
    private String charMaxLenth;
    private String isNullAble;
    private String isAutoIncrement;

    private String isPk;

    public String getMixColType(){
        if("varchar".equals(typeName)||"VARCHAR".equals(typeName) ||"varchar2".equals(typeName)||"VARCHAR2".equals(typeName)){
            StringBuilder result = new StringBuilder();
            return result.append(this.typeName).append("(").append(this.colSize).append(")").toString();
        }else{
            return typeName;
        }

    }


    public String getColInfoByKey(String key){

        switch(key){
            case "colName" :
                return this.colName;
            case "dataType" :
                return this.dataType;
            case "typeName" :
                return this.typeName;
            case "mixTypeName" :
                return getMixColType();
            case "colSize" :
                return this.colSize;
            case "nullAble" :
                return this.nullAble;
            case "dsc" :
                return this.dsc;
            case "defValue" :
                return this.defValue;
            case "charMaxLenth" :
                return this.charMaxLenth;
            case "isNullAble" :
                if("YES".equals(isNullAble)){
                    return "Y";
                }else {
                    return "N";
                }
            case "isAutoIncrement" :
                if("YES".equals(isAutoIncrement)){
                    return "√";
                }else {
                    return "";
                }
            case "isPk" :
                if("YES".equals(isPk)){
                    return "√";
                }else {
                    return "";
                }

            default : //可选
                return "";
        }
    }


}
