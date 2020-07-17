package com.internetBanking.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLUtils {
	public static FileInputStream fi;
	public static FileOutputStream fo;
	public static XSSFWorkbook wb;
	public static XSSFSheet ws;
	public static XSSFRow row;
	public static XSSFCell cell;
	public static Font font ;
	public static CellStyle cellStyle ;


	public static int getRowCount(String xlfile,String xlsheet) throws IOException 
	{
		fi=new FileInputStream(xlfile);
		wb=new XSSFWorkbook(fi);
		ws=wb.getSheet(xlsheet);
		int rowcount=ws.getLastRowNum();
		wb.close();
		fi.close();
		return rowcount;		
	}


	public static int getCellCount(String xlfile,String xlsheet,int rownum) throws IOException
	{
		fi=new FileInputStream(xlfile);
		wb=new XSSFWorkbook(fi);
		ws=wb.getSheet(xlsheet);
		row=ws.getRow(rownum);
		int cellcount=row.getLastCellNum();
		wb.close();
		fi.close();
		return cellcount;
	}


	public static String getCellData(String xlfile,String xlsheet,int rownum,int colnum) throws IOException
	{
		fi=new FileInputStream(xlfile);
		wb=new XSSFWorkbook(fi);
		ws=wb.getSheet(xlsheet);
		row=ws.getRow(rownum);
		cell=row.getCell(colnum);
		String data;
		try 
		{
			DataFormatter formatter = new DataFormatter();
			String cellData = formatter.formatCellValue(cell);
			return cellData;
		}
		catch (Exception e) 
		{
			data="";
		}
		wb.close();
		fi.close();
		return data;
	}

	public static void setCellData(String xlfile,String xlsheet,int rownum,int colnum,String data,int pathNum) throws IOException
	{

		fi=new FileInputStream(xlfile);
		wb=new XSSFWorkbook(fi);
		ws=wb.getSheet(xlsheet);
		row=ws.getRow(rownum);
		font = wb.createFont();
		font.setColor(IndexedColors.WHITE1.getIndex());
		font.setBold(true);
		if(data.equalsIgnoreCase("Completed"))
		{
			cell=row.createCell(colnum);
			cell.setCellValue(data);
			cellStyle = wb.createCellStyle();
	        cellStyle.setFont(font);
	        cellStyle.setFillBackgroundColor(IndexedColors.SEA_GREEN.getIndex());
	        cellStyle.setFillPattern(FillPatternType.ALT_BARS);
	        cell.setCellStyle(cellStyle);    		
		}
		else if(pathNum==2)
		{
			cell=row.createCell(colnum);
			cell.setCellValue(data);
			cellStyle = wb.createCellStyle();
	        cellStyle.setFont(font);
	        cellStyle.setFillBackgroundColor(IndexedColors.AUTOMATIC.getIndex());
	        cellStyle.setFillPattern(FillPatternType.ALT_BARS);
	        cell.setCellStyle(cellStyle);    					
		}
		else if(data.equalsIgnoreCase("Failed"))
		{
			cell=row.createCell(colnum);
			cell.setCellValue(data);
			cellStyle = wb.createCellStyle();
	        cellStyle.setFont(font);
	        cellStyle.setFillBackgroundColor(IndexedColors.RED.getIndex());
	        cellStyle.setFillPattern(FillPatternType.ALT_BARS);
	        cell.setCellStyle(cellStyle);    		
		}
		
		fo=new FileOutputStream(xlfile);
		wb.write(fo);		
		wb.close();
		fi.close();
		fo.close();
	}

}
