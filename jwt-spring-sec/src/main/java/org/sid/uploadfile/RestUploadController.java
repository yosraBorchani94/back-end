package org.sid.uploadfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.InputStreamResource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.sid.dao.QuizRepository;
import org.sid.entities.Quiz;
import org.sid.uploadfile.StorageService;;

@CrossOrigin("*")
@RestController
public class RestUploadController {
	private static final String FILE_PATH = "C:/upload/";
	@Autowired
	StorageService storageService;
	
	
	@Autowired
	QuizRepository quizRepository;
	
	List<String> files = new ArrayList<String>();
	
	/* Image upload */
	@PostMapping(value = "/uploadPicture/{id}")
	public String uploadImage(@RequestParam("uploadfile") MultipartFile file, @PathVariable Long id)
			throws Exception {
		try {
			storageService.storeImage(file, id+"");
			files.add(file.getOriginalFilename());
			Quiz q = quizRepository.findOne(id);
			q.setId(id);
			q.setUrlPicture(FILE_PATH+id+"/"+file.getOriginalFilename());
			quizRepository.save(q);
			return "You successfully uploaded - " + file.getOriginalFilename();
		} catch (Exception e) {
			throw new Exception("FAIL! Maybe You had uploaded the file before or the file's size > 500KB");
		}
	}
	
	
	

	/* Multiple file upload */
	@PostMapping(value = "/uploadfile/{username}")
	public String uploadFileMulti(@RequestParam("uploadfile") MultipartFile file, @PathVariable String username)
			throws Exception {
		try {
			
			StringTokenizer st = new StringTokenizer(username);  
			String userFile = st.nextToken("@");
			storageService.store(file, userFile);
			files.add(file.getOriginalFilename());
			return "You successfully uploaded - " + file.getOriginalFilename();
		} catch (Exception e) {
			throw new Exception("FAIL! Maybe You had uploaded the file before or the file's size > 500KB");
		}
	}

	/* List of Files in directory */
	@GetMapping(value = "/getallfiles/{username}")
	public List<String> getListFiles(@PathVariable String username) {
		List<String> lstFiles = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(username);  
		String userFile = st.nextToken("@");
		File folder = new File("C:/upload/" + userFile);
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
			System.out.println(file.getName());
			if (file.isFile()) {
				lstFiles.add(file.getName());
			}
		}
		return lstFiles;
	}

	/* Download */
	@GetMapping(value = "/download/{username}/{fileName:.*}")
	public ResponseEntity<InputStreamResource> download(@PathVariable String fileName, @PathVariable String username)
			throws IOException {
		StringTokenizer st = new StringTokenizer(username);  
		String userFile = st.nextToken("@");
		String fullPath = FILE_PATH + userFile + "/" + fileName;
		//System.out.println(fullPath);
		File file = new File(fullPath);
		HttpHeaders respHeaders = new HttpHeaders();
		respHeaders.setContentType(MediaType.APPLICATION_PDF);
		InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
		return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);

	}
}
