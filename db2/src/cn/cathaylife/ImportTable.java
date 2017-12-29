package cn.cathaylife;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 * 
 * @author 0100060928 往数据库批量导数据 1、读配置文件的SQL 2、读数据文件 3、写数据
 */
public class ImportTable {

	private static String readSQL() {
		Reader reader = null;
		BufferedReader br = null;
		StringBuilder sql=new StringBuilder();
		try {
			reader = new FileReader("src/sql.txt");
			br = new BufferedReader(reader);
			String line=null;
			while((line=br.readLine())!=null) {
				sql.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sql.toString();
	}

	private static void executeDTFAC013_SALARY_BASESQL(Connection conn, String sql, List<List<String>> excelList)
			throws SQLException, ParseException {
		
		PreparedStatement ps=conn.prepareStatement(sql);
		int sum=0;
		int k=0;
		for(List<String> excel:excelList) {
			k++;
			for(int i=0;i<excel.size();i++) {
				if(i==1||i==10) {
					ps.setInt(i+1, Integer.parseInt(excel.get(i)));
					continue;
				}
				if(i==16||(i>=23&&i<=43)) {
					ps.setFloat(i+1, Float.parseFloat(excel.get(i)));
					continue;
				}
				if(i==44) {
					String date=excel.get(i);
					ps.setDate(i+1, new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime()));
					continue;
				}
				ps.setString(i+1, excel.get(i));
			}
			ps.addBatch();
			System.out.println(k);
			sum++;
			if(sum%1000==0) {
				ps.executeBatch();
				sum=0;
			}
		}
		ps.executeBatch();
		
	}
	
private static void executeDTFAC012_INCOME_DETAILSQL(Connection conn,String sql,List<List<String>> excelList) throws SQLException {
		
		PreparedStatement ps=conn.prepareStatement(sql);
		int sum=0;
		int k=0;
		for(List<String> excel:excelList) {
			k++;
			for(int i=0;i<excel.size();i++) {
				if(i==1) {
					ps.setInt(i+1, Integer.parseInt(excel.get(i)));
					continue;
				}
				ps.setString(i+1, excel.get(i));
			}
			ps.addBatch();
			System.out.println(k);
			sum++;
			if(sum%1000==0) {
				ps.executeBatch();
				sum=0;
			}
		}
		ps.executeBatch();
		
	}
	
	private static List<List<String>> parseEXCEL(String path,int columnNums) throws InvalidFormatException, IOException {
		List<List<String>> excelList=new ExcelCompareWithXml().readExcel(path, columnNums);
		return excelList;
	}
	
	public static void main(String[] args) throws InvalidFormatException, IOException, ClassNotFoundException, SQLException, ParseException {
		Connection conn=new DataSourceConnection().getDataSouceConnection();
		String sql=ImportTable.readSQL();
		
		String path = "E:\\pan\\job_bak\\new\\薪水相关\\01.xls";
		int columnNums=18;
		List<List<String>> excelList=ImportTable.parseEXCEL(path,columnNums);
		ImportTable.executeDTFAC012_INCOME_DETAILSQL(conn, sql, excelList);
		
		path = "E:\\pan\\job_bak\\new\\薪水相关\\02.xls";
		columnNums=18;
		excelList=ImportTable.parseEXCEL(path,columnNums);
		ImportTable.executeDTFAC012_INCOME_DETAILSQL(conn, sql, excelList);
		
//		String path = "E:\\pan\\job_bak\\new\\薪水相关\\03.xls";
//		int columnNums=50;
//		List<List<String>> excelList=ImportTable.parseEXCEL(path,columnNums);
//		ImportTable.executeDTFAC013_SALARY_BASESQL(conn, sql, excelList);
//		
//		path = "E:\\pan\\job_bak\\new\\薪水相关\\04.xls";
//		columnNums=50;
//		excelList=ImportTable.parseEXCEL(path,columnNums);
//		ImportTable.executeDTFAC013_SALARY_BASESQL(conn, sql, excelList);
		
	}
}
