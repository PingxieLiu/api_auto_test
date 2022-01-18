package auto.Day18.lianxi.util;

import auto.Day18.lianxi.pojo.CaseData;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;

import java.io.File;
import java.util.List;

/**
 * @Classname ExcelUtli
 * @Description TODO
 * @Date 1/13/22 11:11 PM
 * @Created by lhk
 */
public class ExcelUtli {

    public static final String EXCEL_FILE_PATH="src/test/resources/caseData.xlsx";


    /**
     * 读取外部中的Excel中的数据
     * @param sheetNum  读取数据的页码，索引值从0开始
     * @return  读取到的数据；
     */
    public static List<CaseData> readExcel(int sheetNum) {
        //1、读取Excel？？Easypoi
        ImportParams importParams = new ImportParams();
        importParams.setStartSheetIndex(sheetNum);
        //读取的文件src\test\resources\caseData.xlsx
        List<CaseData> datas = ExcelImportUtil.importExcel(new File(EXCEL_FILE_PATH), CaseData.class,importParams);
        return datas;

    }
}
