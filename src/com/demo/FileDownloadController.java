//package com.demo;
//
//import java.io.BufferedInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
//import java.net.URLConnection;
//
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.util.FileCopyUtils;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping("/download")
//public class FileDownloadController {
//	@RequestMapping("/pdf/{fileName:.+}")
//	public void downloadPDFResource(HttpServletRequest request, HttpServletResponse response,
//			@PathVariable("fileName") String fileName) throws IOException {
//		File file = null;
//		System.out.println(fileName);
//		file = new File(request.getServletContext().getRealPath("/resources/FileStorage/" + fileName));
//
//		File file2 = new File(request.getServletContext().getRealPath("/resources/FileStorage/abc.txt"));
//		if (file2.delete()) {
//			System.out.println("File deleted");
//		} else
//			System.out.println("File doesn't exist");
//
//		if (!file.exists()) {
//			String errorMessage = "Sorry. The file you are looking for does not exist";
//			System.out.println(errorMessage);
//			// OutputStream outputStream = response.getOutputStream();
//			// outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
//			// outputStream.close();
//			// return;
//		}
//
//		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
//
//		if (mimeType == null) {
//			System.out.println("mimetype is not detectable, will take default");
//			mimeType = "application/octet-stream";
//		}
//
//		System.out.println("mimetype : " + mimeType);
//
//		response.setContentType(mimeType);
//
//		/*
//		 * "Content-Disposition : inline" will show viewable types [like
//		 * images/text/pdf/anything viewable by browser] right on browser while
//		 * others(zip e.g) will be directly downloaded [may provide save as
//		 * popup, based on your browser setting.]
//		 */
//		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
//
//		/*
//		 * "Content-Disposition : attachment" will be directly download, may
//		 * provide save as popup, based on your browser setting
//		 */
//		// response.setHeader("Content-Disposition", String.format("attachment;
//		// filename=\"%s\"", file.getName()));
//
//		response.setContentLength((int) file.length());
//
//		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
//
//		// Copy bytes from source to destination(outputstream in this example),
//		// closes both streams.
//		FileCopyUtils.copy(inputStream, response.getOutputStream());
//	}
//
//	@RequestMapping("/pdf2/{fileName:.+}")
//	public void downloadPDFResource2(HttpServletRequest request, HttpServletResponse response,
//			@PathVariable("fileName") String fileName) {
//		// If user is not authorized - he should be thrown out from here itself
//		System.out.println(fileName);
//		// Authorized user will download the file
//		String dataDirectory = request.getServletContext().getRealPath("/resources/FileStorage/");
//		System.out.println(dataDirectory);
//		Path file = Paths.get(dataDirectory, fileName);
//		if (Files.exists(file)) {
//			response.setContentType("application/txt");
//			response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
//			try {
//				Files.copy(file, response.getOutputStream());
//				response.getOutputStream().flush();
//			} catch (IOException ex) {
//				ex.printStackTrace();
//			}
//		}
//	}
//}
//
//
//class DeleteFile {
//
//    /**
//     * This class shows how to delete a File in Java
//     * @param args
//     */
//    public static void main(String[] args) {
//        //absolute file name with path
//        File file = new File("/Users/pankaj/file.txt");
//        if(file.delete()){
//            System.out.println("/Users/pankaj/file.txt File deleted");
//        }else System.out.println("File /Users/pankaj/file.txt doesn't exist");
//        
//        //file name only
//        file = new File("file.txt");
//        if(file.delete()){
//            System.out.println("file.txt File deleted from Project root directory");
//        }else System.out.println("File file.txt doesn't exist in the project root directory");
//        
//        //relative path
//        file = new File("temp/file.txt");
//        if(file.delete()){
//            System.out.println("temp/file.txt File deleted from Project root directory");
//        }else System.out.println("File temp/file.txt doesn't exist in the project root directory");
//        
//        //delete empty directory
//        file = new File("temp");
//        if(file.delete()){
//            System.out.println("temp directory deleted from Project root directory");
//        }else System.out.println("temp directory doesn't exist or not empty in the project root directory");
//        
//        //try to delete directory with files
//        file = new File("/Users/pankaj/project");
//        if(file.delete()){
//            System.out.println("/Users/pankaj/project directory deleted from Project root directory");
//        }else System.out.println("/Users/pankaj/project directory doesn't exist or not empty");
//    }
//
//}
//
