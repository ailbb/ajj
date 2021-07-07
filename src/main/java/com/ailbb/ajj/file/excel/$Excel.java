package com.ailbb.ajj.file.excel;

import com.ailbb.ajj.$;
import com.ailbb.ajj.entity.$Result;
import com.ailbb.ajj.entity.Interlayer;
import com.ailbb.ajj.file.$FileInfo;
import org.apache.poi.hssf.record.chart.NumberFormatIndexRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.math.BigInteger;
import java.text.DateFormat;
import java.util.*;
import java.util.regex.Pattern;

/*
 * Created by Wz on 9/13/2018.
 */
public class $Excel {
    public static final String $SUFFIX_XLS = "xls";
    public static final String $SUFFIX_XLSX = "xlsx";
    public static final short $BASE_WIDTH = (short)(0.72 * 256); // 基础宽度
    private short maxCellWidth = 50 * 256 + $BASE_WIDTH; // 最大字符宽度
    private Interlayer interlayer; // 中间处理器

    /*
     * 读取Excel数据
     * @param path 文件路径
     * @return 路径
     */
    public $Result parseExcel(String path) {
        return parseExcel(path, null);
    }

    /*
     * 读取Excel数据
     * @param path 文件路径
     * @return 路径
     */
    public $Result parseExcel(String path, List<String> header) {
        return parseExcel($.file.getFile(path), header, 0, null);
    }


    /*
     * 读取Excel数据
     * @param file 文件
     * @return 路径
     */
    public $Result parseExcel(File file) {
        return parseExcel(file, null, 0, null);
    }

    /*
     * 读取Excel数据
     * @param file 文件
     * @return 路径
     */
    public $Result parseExcel(File file, List<String> headers, int dataIndexRow, String sheetName) {
        $Result rs = $.result();
        InputStream is = null;
        Map<String, List> datas = new HashMap<>();
        $FileInfo fi = new $FileInfo(file);
        int version = fi.getType().equalsIgnoreCase($SUFFIX_XLS) ? 2003 : 2007;

        try {
            $.timeclock();
            boolean hasHeaders = !$.isEmptyOrNull(headers); // 是否指定标题
            is = new FileInputStream(fi.getFile());
            Workbook workBook = version == 2003 ? new HSSFWorkbook(is) : new XSSFWorkbook(is);

            // 循环工作表Sheet
            for (int numSheet = 0; numSheet < workBook.getNumberOfSheets(); numSheet++) {
                Sheet sheet = workBook.getSheetAt(numSheet);
                if (sheet == null || (!$.isEmptyOrNull(sheetName) && !sheetName.equalsIgnoreCase(sheet.getSheetName()))) continue;

                List list = new ArrayList();

                for (int rowNum = 1, lastRowNum = sheet.getLastRowNum() ; rowNum <= lastRowNum; rowNum++) {
                    Row row = sheet.getRow(rowNum);
                    if (row == null || dataIndexRow > rowNum)  continue;

                    List<Object> rowDataList = new ArrayList<>();
                    Map<String, Object> rowDataMap = new HashMap<>();

                    // 循环列Cell
                    for (int cellNum = 0, lastCellNum = hasHeaders ? headers.size() : row.getLastCellNum(); cellNum < lastCellNum; cellNum++) {
                        Cell cell = row.getCell(cellNum);

                        // 如果有中间处理器，则进行处理
                        Object cellValue = !$.isEmptyOrNull(interlayer) ? interlayer.doAs(sheetName, rowNum, cellNum, getCellObjectValue(cell)) : getCellObjectValue(cell);

                        if(hasHeaders) {
                            rowDataMap.put(headers.get(cellNum), cellValue);
                        } else {
                            rowDataList.add(cellValue);
                        }
                    }

                    list.add(hasHeaders ? rowDataMap : rowDataList);
                }

                datas.put(sheet.getSheetName(), list);
            }

            // 循环行Row
            $.info(String.format("Read File: [%s], Size: [%s], RunTime: [%s]",fi.getFileName(), fi.getSize(), $.timeclock()));
        } catch (IOException e) {
            rs.addError($.exception(e));
        } finally{
            $.file.closeStearm(is);
        }

        return rs.setData(datas);
    }


    /*
     * 向Excel内写入数据
     * @param fileName 导出文件名
     * @param data 数据
     * @return 路径
     */
    public $Result writeToExcel(String fileName, List data) {
        return writeToExcel(fileName, data, null);
    }

    /*
     * 向Excel内写入数据
     * @param fileName 导出文件名
     * @param data 数据
     * @return 路径
     */
    public $Result writeToExcel(String fileName, List data, String type) {
        return writeToExcel(null, fileName, data, type);
    }

    /*
     * 向Excel内写入数据
     * @param fileName 导出文件名
     * @param data 数据
     * @return 路径
     */
    public $Result writeToExcel(String path, String fileName, List data) {
        return writeToExcel(path, fileName, data, null);
    }

    /*
     * 向Excel内写入数据
     * @param path 导出路径
     * @param fileName 导出文件名
     * @param data 数据
     * @return 路径
     */
    public $Result writeToExcel(String path, String fileName, List data, String type) {
        return writeToExcel(path, fileName, null, data, type);
    }

    /*
     * 向Excel内写入数据
     * @param path 导出路径
     * @param fileName 导出文件名
     * @param data 数据
     * @param sheetName sheet名
     * @return 路径
     */
    public $Result writeToExcel(String path, String fileName, String sheetName, List data, String type) {
        List[] list = $.file.parseHeaderAndData(data);
        return writeToExcel(path, fileName, sheetName, list[0], list[1], type);
    }

    /*
     * 向Excel内写入数据
     * @param path 导出路径
     * @param fileName 导出文件名
     * @param headers 导出title
     * @param datas 数据
     * @param sheetName sheet名
     * @return 路径
     */
    public $Result writeToExcel(String path, String fileName, String sheetName, List<Object> headers, List<List<Object>> datas, String type) {
        $Result rs = $.result();
        $.timeclock();

        path = !$.isEmptyOrNull(path) ? $.path.getPath(path) : $.path.getPath($.path.getRootPath(), "export/excel", $.now("nm")) ;

        String[] fileNameSplit = (!$.isEmptyOrNull(fileName) ? fileName : $.now("nss")).split("\\.");

        fileName = fileNameSplit[0];

        type = fileNameSplit.length > 1 ? fileNameSplit[fileNameSplit.length-1] : ((!$.isEmptyOrNull(type) && type.equalsIgnoreCase($SUFFIX_XLS)) ? $SUFFIX_XLS : $SUFFIX_XLSX);

        $FileInfo fi = new $FileInfo(new File($.path.getPath(path, $.concat(fileName, "." ,type))));
        FileOutputStream fileOutputStream = null;
        FileInputStream fileInputStream = null;

        try {
            $.file.mkdir($.path.getPath(path)); // 创建路径

            Workbook wb;
            Sheet sheet;

            if(fi.getFile().exists()) {
                fileInputStream = new FileInputStream(fi.getFile());
                wb = type.equalsIgnoreCase($SUFFIX_XLSX) ? new XSSFWorkbook(fileInputStream) : new HSSFWorkbook(fileInputStream);
            } else {
                wb = type.equalsIgnoreCase($SUFFIX_XLSX) ? new XSSFWorkbook() : new HSSFWorkbook();
            }

            if($.isEmptyOrNull(sheetName)) {
                sheet = wb.createSheet();
            } else {
                sheet = wb.getSheet(sheetName);
                if(null == sheet)
                    sheet = wb.createSheet(sheetName);
            }

            if(!$.isEmptyOrNull(headers)) datas.add(0, headers); // 向第一行插入标题
            writeData(sheet, datas); // 写数据

            fileOutputStream = new FileOutputStream(fi.getFile());
            wb.write(fileOutputStream);
        } catch (IOException e) {
            rs.addError($.exception(e));
        } finally {
            if(null != fileInputStream)
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    rs.addError($.exception(e));
                }
            if(null != fileOutputStream)
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    rs.addError($.exception(e));
                }
        }

        $.info(String.format("Write [%s], Length [%s] ，Used：[%s] ms.", fi.getPath(), datas.size(), $.timeclock()));

        return rs.setData(fi);
    }

    /*
     * 向sheet内写入数据
     * @param sheet
     * @param data
     * @return
     */
    public List<List<Object>> writeData(Sheet sheet, List<List<Object>> data){
        if($.isEmptyOrNull(data))  return data;

        String sheetName = sheet.getSheetName();

        for (int i=0, datasize = data.size(), startRow = sheet.getLastRowNum(); i<datasize; i++) {
            int rowNum = i + startRow;
            Row row = sheet.createRow(rowNum);
            List<Object> li = data.get(i);
            for(int j=0; j<li.size(); j++) {
                Object v = li.get(j);

                // 如果有中间处理器，则进行处理
                Object cellValue = !$.isEmptyOrNull(interlayer) ? interlayer.doAs(sheetName, rowNum, j, v) : v;

                if(!$.isEmptyOrNull(cellValue)){
                    int colWidth = sheet.getColumnWidth(j);
                    if(colWidth < maxCellWidth) {
                        short width = (short)($.str(cellValue).getBytes().length  * 256 + $BASE_WIDTH);
                        // 宽度设置
                        if(width > colWidth) sheet.setColumnWidth(j, width > maxCellWidth ? maxCellWidth : width);
                    }
                }

                Cell cell = row.createCell(j);

                try {
                    if(cellValue instanceof String) {
                        cell.setCellValue($.str(cellValue));
                    } else if(cellValue instanceof Double) {
                        cell.setCellValue(Double.parseDouble($.str(cellValue)));
                    } else if(cellValue instanceof Boolean) {
                        cell.setCellValue(Boolean.parseBoolean($.str(cellValue)));
                    } else if(cellValue instanceof Date) {
                        cell.setCellValue((Date)cellValue);
                    } else if(cellValue instanceof Calendar) {
                        cell.setCellValue((Calendar)(cellValue));
                    } else if(cellValue instanceof RichTextString) {
                        cell.setCellValue((RichTextString)(cellValue));
                    } else if(cellValue instanceof Byte) {
                        cell.setCellValue((Byte)(cellValue));
                    }
                } catch (Exception e) {
                    cell.setCellValue($.str(cellValue));
                }
            }
        }

        return data;
    }

    public Object getCellObjectValue(Cell cell){
        Object v = "";

        if($.isEmptyOrNull(cell) || cell.getCellTypeEnum() == CellType._NONE || cell.getCellTypeEnum() == CellType.BLANK) {
            v = "";
        } else if(cell.getCellTypeEnum() == CellType.STRING) {
            v = cell.getStringCellValue();
        } else if(cell.getCellTypeEnum() == CellType.BOOLEAN) {
            v = cell.getBooleanCellValue();
        } else if(cell.getCellTypeEnum() == CellType.ERROR) {
            v = cell.getErrorCellValue();
        } else if(cell.getCellTypeEnum() == CellType.NUMERIC || cell.getCellTypeEnum() == CellType.FORMULA) { // 公式
            if(DateUtil.isCellDateFormatted(cell)) {
                v = cell.getDateCellValue();
            } else {
                double d = cell.getNumericCellValue();
                long longVal = Math.round(d);

                if(Double.parseDouble(longVal + ".0") == d)
                    v = longVal;
                else
                    v = d;
            }
        }

        return v;
    }

    public short getMaxCellWidth() {
        return maxCellWidth;
    }

    public $Excel setMaxCellWidth(short maxCellWidth) {
        this.maxCellWidth = maxCellWidth;
        return this;
    }

    public Interlayer getInterlayer() {
        return interlayer;
    }

    public $Excel setInterlayer(Interlayer interlayer) {
        this.interlayer = interlayer;
        return this;
    }
}
