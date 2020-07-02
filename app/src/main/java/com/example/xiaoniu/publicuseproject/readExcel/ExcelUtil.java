package com.example.xiaoniu.publicuseproject.readExcel;

import android.content.Context;
import android.os.Environment;
import android.util.Log;


import com.example.xiaoniu.publicuseproject.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import me.zhouzhuo.zzexcelcreator.ZzExcelCreator;
import me.zhouzhuo.zzexcelcreator.ZzFormatCreator;

public class ExcelUtil {

//    public static ArrayList<String> readExcelFile(String dir, String fileName, Context context) {
    public static ArrayList<String> readExcelFile(Context context) {
        ArrayList<String> list = new ArrayList<>();
        list.clear();
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.encrypt);
            Workbook book = Workbook.getWorkbook(inputStream);
//            Workbook book = Workbook.getWorkbook(new File(dir + "/" + fileName + ".xls"));
//            Log.e(">>>>>>number of sheet " , book.getNumberOfSheets() + "");
            // 获得第一个工作表对象
            Sheet sheet = book.getSheet(0);
            int Rows = sheet.getRows();
            int Cols = sheet.getColumns();
//            Log.e("当前工作表的名字:" , sheet.getName());
            Log.e("总行数:" , Rows + "");
            Log.e("总列数:" , Cols + "");
            for (int i = 0; i < Rows; ++i) {
                for (int j = 0; j < Cols; ++j) {
                    // getCell(Col,Row)获得单元格的值
                    String content = (sheet.getCell(j, i)).getContents();
                    Log.e("content", content);
                    if (content.contains(".")) {
                        list.add(content);
                    }
                }
            }
            book.close();

        } catch (Exception e) {
            Log.e("Exception: " , e.getMessage());
        }
        return list;
    }

    public static void writeExcel(String dir, String fileName, String testInfo) {
        try {
            initExcelWorkbook(dir, fileName);
            ZzExcelCreator.getInstance()
                    .openExcel(new File(dir, fileName + ".xls"))
                    .openSheet(0)
                    .fillContent(0, 1, testInfo, null)
//                    .fillContent(1, 1, testInfo, null)
                    .close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }


    }

    //sheet content
    private static void initExcelWorkbook(String dir, String fileName) throws IOException, WriteException {
        boolean flag;
        // 目标文件路径
        File desSavedFileDir = new File(dir);
        // 目标文件
        File desSavedFile = new File(dir, fileName+".xls");
        flag = desSavedFileDir.isDirectory();
        if (!flag) {
            newExcel(dir, fileName);
        } else {
            if (!desSavedFile.exists()) {
                newExcel(dir, fileName);
            }
        }
    }

    //sheet title
    private static void newExcel(String dir, String fileName) throws IOException, WriteException {
        WritableCellFormat titleFormat = ZzFormatCreator
                .getInstance()
                .createCellFont(WritableFont.ARIAL)
                .setAlignment(Alignment.CENTRE, VerticalAlignment.CENTRE)
                .setFontSize(19)
                .setFontColor(Colour.DARK_GREEN)
                .getCellFormat();
        ZzExcelCreator
                .getInstance()
                .createExcel(dir, fileName)
                .createSheet("jxl-test")
                .fillContent(0, 0, "encrypt", titleFormat)
//                .fillContent(1, 0, "内容2", titleFormat)
//                .merge(1, 0, 3, 0)
                .close();
    }

    @SuppressWarnings("unchecked")
    public static <T> void writeObjListToExcel(List<T> objList, String filePath, String fileName, Context c) {
        if (objList != null && objList.size() > 0) {
            WritableWorkbook writebook = null;
            InputStream in = null;
            try {
                File folder = new File(filePath);
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                File file = new File(filePath + File.separator + fileName + ".xls");
                if (!file.exists()) {
                    file.createNewFile();
                }

                WritableWorkbook book = Workbook.createWorkbook(file);
                WritableSheet sheet1 = book.createSheet("sheet1", 0);
                for (int i = 0; i < objList.size(); i++) {
                    String string = (String) objList.get(i);
//                    for (int j = 0; j < obj_list.size(); j++) {
//                        Label label = new Label(j, i, obj_list.get(j).toString());
//                        sheet1.addCell(label);
//                    }
                    Label label = new Label(0, i, string);
                    sheet1.addCell(label);
                }
                book.write();
                book.close();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("write", "Exception: " + e.getMessage());
            } finally {
                if (writebook != null) {
                    try {
                        writebook.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}