package com.demo.chat;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.util.ConstantConfig;
import com.demo.util.MongoDBConnection;
import com.google.gson.Gson;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;

import com.itextpdf.text.Paragraph;

import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

@Controller
public class DownloadFilesController {

	@RequestMapping(value = "/downloadExcel", method = RequestMethod.GET)
	public void downloadExcelFiles(HttpServletRequest request, HttpServletResponse response, @RequestParam String name)
			throws Exception {

		List<PostData> list = getData(name);
		generateExcelFile(list, name);
		File file = new File(ConstantConfig.DOWNLOAD_PATH + name + ".xlsx");

		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		System.out.println(file.getName());

		if (mimeType == null) {
			System.out.println("mimetype is not detectable, will take default");
			mimeType = "application/octet-stream";
		}

		System.out.println("mimetype : " + mimeType);

		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));
		response.setContentLength((int) file.length());

		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());

		if (file.delete()) {
			System.out.println("File deleted");
		} else {
			System.out.println("File doesn't exist");
		}

	}

	@RequestMapping(value = "/downloadPDF", method = RequestMethod.GET)
	public void downloadPDFFiles(HttpServletRequest request, HttpServletResponse response, @RequestParam String name)
			throws Exception {

		List<PostData> list = getData(name);
		generatePDFFile(list, name);
		File file = new File(ConstantConfig.DOWNLOAD_PATH + name + ".pdf");

		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		System.out.println(file.getName());

		if (mimeType == null) {
			System.out.println("mimetype is not detectable, will take default");
			mimeType = "application/octet-stream";
		}

		System.out.println("mimetype : " + mimeType);

		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));
		response.setContentLength((int) file.length());

		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());

		if (file.delete()) {
			System.out.println("File deleted");
		} else {
			System.out.println("File doesn't exist");
		}

	}

	private void generateExcelFile(List<PostData> list, String name) {

		final String FILE_NAME = ConstantConfig.DOWNLOAD_PATH + name + ".xlsx";
		try {

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Data");
			int rowNum = 0;

			Row row = sheet.createRow(rowNum);

			for (PostData pojo : list) {
				row = sheet.createRow(rowNum++);
				int colNum = 0;

				Cell cell = row.createCell(colNum++);
				cell.setCellValue((String) pojo.getUsername());

				cell = row.createCell(colNum++);
				cell.setCellValue((String) pojo.getMessage());

				cell = row.createCell(colNum++);
				cell.setCellValue((String) pojo.getTime());

			}
			FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
			workbook.write(fileOutputStream);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void generatePDFFile(List<PostData> list, String name) {

		final String FILE_NAME = ConstantConfig.DOWNLOAD_PATH + name + ".pdf";
		Document document = new Document();

		try {

			PdfWriter.getInstance(document, new FileOutputStream(FILE_NAME));

			document.open();

			Font font = new Font(Font.FontFamily.COURIER, 18, Font.ITALIC | Font.UNDERLINE | Font.BOLD);
			Paragraph p = new Paragraph("User Chat Details", font);
			p.setAlignment(Paragraph.ALIGN_CENTER);
			p.setSpacingAfter(30);

			document.add(p);

			PdfPTable table = new PdfPTable(3); // 3 columns.

			boolean color = true;
			boolean firstTime = true;

			for (PostData pojo : list) {
				PdfPCell cell = new PdfPCell();
				BaseColor myColor;

				if (firstTime) {

					myColor = WebColors.getRGBColor("#54853d");
					cell.setBackgroundColor(myColor);
					firstTime = false;
				} else {
					if (color) {
						myColor = WebColors.getRGBColor("#e6fafa");
						cell.setBackgroundColor(myColor);
						color = false;
					} else {
						myColor = WebColors.getRGBColor("#f0c9f2");

						color = true;
					}
				}

				cell = new PdfPCell(new Paragraph(pojo.getUsername()));
				cell.setBackgroundColor(myColor);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph(pojo.getMessage()));
				cell.setBackgroundColor(myColor);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph(pojo.getTime()));
				cell.setBackgroundColor(myColor);
				table.addCell(cell);

			}
			document.add(table);
			document.close();

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public List<PostData> getData(String name) {

		List<PostData> list = new ArrayList<PostData>();

		try {

			if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

				if (MongoDBConnection.makeDBConnection(ConstantConfig.DB_IP, ConstantConfig.DB_PORT)) {
					// System.out..println("DB is again connected");
				} else {

					// System.out..println("DB is not connecting.........");
				}
			}

			SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

			DBCollection table = MongoDBConnection.getDB().getCollection("tbl_chat_data");

			BasicDBObject query1 = new BasicDBObject();
			query1.put("friends", name);

			String[] ar = name.split("-");
			BasicDBObject query2 = new BasicDBObject();
			query2.put("friends", ar[1] + "-" + ar[0]);

			BasicDBList or = new BasicDBList();
			or.add(query1);
			or.add(query2);
			BasicDBObject query = new BasicDBObject("$or", or);

			DBCursor cursor = table.find(query);

			PostData p = new PostData();
			p.setMessage("Data");
			p.setUsername("Username");
			p.setTime("Time");

			list.add(p);
			Gson gson = new Gson();
			while (cursor.hasNext()) {

				BasicDBObject oneDetails = (BasicDBObject) cursor.next();
				BasicDBList data = (BasicDBList) oneDetails.get("chat");
				BasicDBObject[] dataArr = data.toArray(new BasicDBObject[0]);
			
				System.out.println("ddddd " + gson.toJson(dataArr));
				
				
				for (BasicDBObject dbObj : dataArr) {
					p = new PostData();
					p.setMessage(dbObj.get("message").toString());
					p.setUsername(dbObj.get("name").toString());
					p.setTime(sdf.format(dbObj.get("time")));

					list.add(p);
				}

			}
		
			System.out.println("ddddd " + gson.toJson(list));

			return list;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}

class PostData {

	String username, message, time;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
