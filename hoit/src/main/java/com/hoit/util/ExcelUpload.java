package com.hoit.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUpload  {
	public List<Map<String, Object>> upExcel(String fileurl, String[] cellTitle) throws FileNotFoundException, IOException{
		List<Map<String, Object>> list = new ArrayList();
		
		File file = new File(fileurl);
		String password = "";
		try (Workbook wb = WorkbookFactory.create(file, password)) {
			Sheet sheet = wb.getSheetAt(0);							//시트 가져오기
			
			int rows = sheet.getLastRowNum() + 1;					//Row 갯수 가져오기
			int startRow = fileurl.toLowerCase().endsWith("xlsx") ? 4 : 1;
			
			// 타이틀 매핑
			Map<Integer, String> titleMap = new HashMap<>();
			Row headerRow = sheet.getRow(startRow - 1);
			if (headerRow != null) {
				int headerCells = headerRow.getLastCellNum();
				for (int k = 0; k < headerCells; k++) {
					Cell cell = headerRow.getCell(k);
					if (cell != null && cell.getCellType() == CellType.STRING) {
						String headerVal = cell.getStringCellValue().trim();
						for (String title : cellTitle) {
							if (title.equals(headerVal)) {
								titleMap.put(k, title);
								break;
							}
						}
					}
				}
			}
			
			for( int i=startRow; i<rows; i++ ){
				Map<String, Object> param = new HashMap<>();
				Row row = sheet.getRow(i);							//Row 가져오기

				if( row != null ){
					for( Map.Entry<Integer, String> entry : titleMap.entrySet() ){
						int k = entry.getKey();
						String title = entry.getValue();
						Cell cell = row.getCell(k);
						
						if( cell != null ){
							switch(cell.getCellType()){	
							//cell 타입에 따른 데이터 저장
		                        case FORMULA:
		        					System.err.println("CELL_TYPE_FORMULA");
		                            String val1=cell.getCellFormula();
		                            val1 = val1.replace(" ", "");
		        					param.put(title, val1.trim());
		                            break;
		                        case NUMERIC:
		        					System.err.println("CELL_TYPE_NUMERIC");
		        					if (DateUtil.isCellDateFormatted(cell)){
		        				         SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		        				         String val2 = formatter.format(cell.getDateCellValue());
		        				         val2 = val2.replace(" ", "");
		        				         param.put(title, String.valueOf(val2.trim()));
		        				    } else {
		        				         double val2 = cell.getNumericCellValue();
		        				         if(Math.ceil(val2) == Math.floor(val2)){
		        				        	 param.put(title, ""+(long) val2 );
		        				         } else {
		        				        	 param.put(title, ""+(float) val2 );
		        				         }
		        				    }
		                            break;
		                        case STRING:
		        					System.err.println("CELL_TYPE_STRING");
		                            String val3=cell.getStringCellValue();
		        					param.put(title, val3.trim());
		                            break;
		                        case BLANK:
		        					System.err.println("CELL_TYPE_BLANK");
		        					param.put(title, "");
		                            break;
		                        case ERROR:
		        					System.err.println("CELL_TYPE_ERROR");
		                            byte val4=cell.getErrorCellValue();
		        					param.put(title, val4);
		                            break;
		                        default:
		                        	param.put(title, "");
		                    }
						}else{
	    					param.put(title, "");
						}
					}
				}
	
				list.add(param);
			}
		}
		
		return list;
	}
}
