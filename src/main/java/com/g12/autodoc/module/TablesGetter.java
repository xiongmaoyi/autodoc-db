package com.g12.autodoc.module;

import com.g12.autodoc.bean.MetaTable;
import com.g12.autodoc.bean.TabColumn;
import com.g12.autodoc.util.db.DataBaseUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author sjx
 * @Classname TablesGetter
 * @Description 表结构数据获取器
 * @Date 2019/7/14 13:53
 */
@Slf4j
public class TablesGetter {


    private String dbType;
    private String host;
    private String port;
    private String database;
    private String user;
    private String pwd;


    private TablesGetter(){}

    private TablesGetter(String dbType, String host, String port, String database, String user, String pwd){
        this.dbType = dbType;
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.pwd = pwd;
    }

    public static TablesGetter getInstance(String dbType, String host, String port, String database, String user, String pwd){
        return new TablesGetter(dbType,host,port,database,user,pwd);
    }


    public List<MetaTable> getTables(List<String> tabNameList) throws Exception {

        List<MetaTable> metaTables = new ArrayList<>();


        DatabaseMetaData databaseMetaData = DataBaseUtil.getConnect(dbType,host,port,database,user,pwd).getMetaData();

        for(String tableName:tabNameList){
            MetaTable metaTable = MetaTable.getInstance();
            log.info("====================={}===========================",tableName);
            ResultSet tableRs = databaseMetaData.getTables(null, null, tableName, new String[] { "TABLE" });

            String tableDsc = "";
            while (tableRs.next()){
                tableDsc = tableRs.getString(5);
                log.info(tableDsc);
            }

            metaTable.initTableInfo(tableName,tableDsc);
            ResultSet pColRs = databaseMetaData.getPrimaryKeys(null, null, tableName);

            List<String> pkCol = new ArrayList();
            while (pColRs.next()){
                //主键名
                String pkName = pColRs.getString(6);
                //列名
                String colName = pColRs.getString(4);
                //主键中的第几列
                String keySequnce = pColRs.getString(5);
                pkCol.add(colName);
                log.info("主键名{},列名{},顺序{}",pkName,colName,keySequnce);
            }


            ResultSet cloumRs = databaseMetaData.getColumns(null, null, tableName, null);
            int i = 0;
            while (cloumRs.next()){
                TabColumn tabColumn = new TabColumn();

                String colName = cloumRs.getString(4);
                String dataType = cloumRs.getString(5);
                String typeName = cloumRs.getString(6);
                String colSize = cloumRs.getString(7);
                String nullAble = cloumRs.getString(11);
                String dsc = cloumRs.getString(12);
                String defValue = cloumRs.getString(13);
                String charMaxLenth = cloumRs.getString(16);
                String isNullAble = cloumRs.getString(18);
                String IS_AUTOINCREMENT = cloumRs.getString(23);
                String isPk = "";

                if(pkCol.contains(colName)){
                    isPk = "YES";
                }else {
                    isPk = "";
                }

                tabColumn.setColName(colName);
                tabColumn.setDataType(dataType);
                tabColumn.setTypeName(typeName);
                tabColumn.setColSize(colSize);
                tabColumn.setNullAble(nullAble);
                tabColumn.setDsc(dsc);
                tabColumn.setDefValue(defValue);
                tabColumn.setCharMaxLenth(charMaxLenth);
                tabColumn.setIsNullAble(isNullAble);
                tabColumn.setIsAutoIncrement(IS_AUTOINCREMENT);
                tabColumn.setIsPk(isPk);

                metaTable.addTabColumn(tabColumn);
                log.info("【colName:{}】,【dataType:{}】,【typeName:{}】,【colSize:{}】,【nullAble:{}】,【dsc:{}】,【defValue:{}】,【charMaxLenth:{}】,【isNullAble:{}】,【IS_AUTOINCREMENT:{}】,【isPk:{}】",colName,dataType,typeName,colSize,nullAble,dsc,defValue,charMaxLenth,isNullAble,IS_AUTOINCREMENT,isPk);

                i+=1;
            }

            metaTables.add(metaTable);

            log.info("表 {} 共有 {} 列", tableName,i);
            log.info("====================================================");
        }


        log.info(metaTables.toString());
        return metaTables;

    }

}
