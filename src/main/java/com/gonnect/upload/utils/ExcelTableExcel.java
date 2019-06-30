package com.gonnect.upload.utils;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gonnect.upload.utils.L;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Supplier;


/**
 *
 */
public class ExcelTableExcel extends ExcelDataTable {
   private	L l = new L();
   private static final Logger logger = LoggerFactory.getLogger(ExcelTableExcel.class);

   private ExcelTableExcel() {

   }

   public static ExcelDataTable load(byte[] bytes) {
      ExcelTableExcel table = new ExcelTableExcel();
      table.load(new ByteArrayInputStream(bytes));
      return table;
   }

   public static ExcelDataTable load(Supplier<InputStream> inputStreamReader) {
      ExcelTableExcel table = new ExcelTableExcel();
      return table.load(inputStreamReader.get());
   }

   private ExcelDataTable load(InputStream fis) {

      ExcelDataTable result = this;

      try {

         XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);

         XSSFSheet mySheet = myWorkBook.getSheetAt(0);
         Iterator<Row> rowIterator = mySheet.iterator();

         Map<Integer, String> columns = new HashMap<>();

         int rowIndex = 0;

         while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            ExcelDataRow excelDataRow = result.newRow();

            if (rowIndex == 0) {
               Iterator<Cell> cellIterator = row.cellIterator();
               int columnIndex = 0;

               while (cellIterator.hasNext()) {
                  Cell cell = cellIterator.next();
                  // skip hidden headers
                  if (cell.getCellStyle().getHidden()) {
                     logger.info("cell hidden");
                     continue;
                  }
                  String value;
                  try {
                     value = cell.getStringCellValue();
                  }
                  catch (IllegalStateException ex) {
                     try {
                        value = "" + cell.getNumericCellValue();
                     }
                     catch (IllegalStateException ex2) {
                        value = "INVALID_HEADER";
                     }
                  }

                  if (value == null) {
                     columnIndex++;
                     continue;
                  }
                  value = value.trim();
                  if (!StringUtils.isEmpty(value)) {
                     logger.info("column: {}", value);
                     columns.put(columnIndex, value);
                  }

                  columnIndex++;
               }

               for(String column : columns.values()) {
                  result.columns().add(column);
               }

            }

            else {
               ArrayList<Integer> columnKeys = new ArrayList<Integer>(columns.keySet());
               int numColumns = columnKeys.size();
               
               int n =l.getn("insert into swjl (id, nsh, qymc,mse,rtime)values(?, ?, ?,?,now())", 
               row.getCell(columnKeys.get(0)).getNumericCellValue(),
               row.getCell(columnKeys.get(1)).getStringCellValue(),
               row.getCell(columnKeys.get(2)).getStringCellValue(),
               row.getCell(columnKeys.get(3)).getNumericCellValue());           
               for (int i=0; i<numColumns; i++) {
                  // logger.info(columnKeys.get(i));
                  // logger.info(row.getCell(columnKeys.get(i)));
                  // logger.info(row.getCell(columnKeys.get(i)).getStringCellValue().toString());
                  int columnIndex = columnKeys.get(i);
                  Cell cell = row.getCell(columnIndex);
                  String value = "";

                  if (cell == null) {
                     value = "";
                  }
                  else {

                     try {
                        
                        value = cell.getStringCellValue();
                        logger.info("#" + (i + 1) + ": cell value (string): {}", value);

                     } catch (IllegalStateException ex) {
                        try {
                           value = "" + (int) cell.getNumericCellValue();
                           logger.info("#" + (i + 1) + ": cell value (numeric): {}", value);

                        } catch (IllegalStateException ex2) {
                           value = "";
                           logger.info("#" + (i + 1) + ": cell value (neither string nor numeric): {}", value);
                        }
                     }
                  }

                  // final check for null values
                  if (value == null) {
                     value = "";
//                     logger.info("#" + (i + 1) + ": cell value (value null): {}", value);
                  }

                  excelDataRow.cell(columns.get(columnIndex), value);
               }

               result.rows().add(excelDataRow);
            }

            rowIndex++;
         }

      }
      catch (IOException e) {
         logger.error("Failed to read the excel file", e);
      }


      return result;

   }
}
