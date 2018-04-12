package org.sid.uploadfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.InputStreamResource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.sid.dao.DocumentRepository;
import org.sid.dao.ModuleRepository;
import org.sid.dao.QuizRepository;
import org.sid.entities.Document;
import org.sid.entities.Module;
import org.sid.entities.Quiz;
import org.sid.entities.Video;
import org.sid.uploadfile.StorageService;;

@CrossOrigin("*")
@RestController
public class RestUploadController {
	private static final String FILE_PATH = "C:/upload/";

	@Autowired
	StorageService storageService;

	@Autowired
	private ModuleRepository moduleRepository;

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	QuizRepository quizRepository;

	List<String> files = new ArrayList<String>();

	@GetMapping("/document")
	public List<Document> listDocuments() {
		return documentRepository.findAll();
	}

	/* Image upload */
	@PostMapping(value = "/uploadPicture/{id}")
	public String uploadImage(@RequestParam("uploadfile") MultipartFile file, @PathVariable Long id) throws Exception {
		try {
			storageService.storeImage(file, id + "");
			files.add(file.getOriginalFilename());
			Quiz q = quizRepository.findOne(id);
			q.setId(id);
			q.setUrlPicture(FILE_PATH + id + "/" + file.getOriginalFilename());
			quizRepository.save(q);
			return "You successfully uploaded - " + file.getOriginalFilename();
		} catch (Exception e) {
			throw new Exception("FAIL! Maybe You had uploaded the file before or the file's size > 500KB");
		}
	}

	/* Multiple file upload */
	@PostMapping(value = "/uploadfile/{username}")
	public String uploadFileMulti(@RequestParam("uploadfile") MultipartFile file,
			@RequestParam("idModule") Long idModule, @PathVariable String username) throws Exception {

		try {

			StringTokenizer st = new StringTokenizer(username);
			String userFile = st.nextToken("@");

			Module m = moduleRepository.findOne(idModule);
			Document d = new Document();
			d.setModule(m);
			d.setDocumentName(file.getOriginalFilename());
			d.setUsername(username);
			d.setPath(FILE_PATH + userFile + "/" + file.getOriginalFilename());
			if (documentRepository.save(d) != null) {
				storageService.store(file, userFile);
				files.add(file.getOriginalFilename());
			}

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
			// System.out.println(file.getName());
			if (file.isFile()) {
				lstFiles.add(file.getName());
			}
		}
		return lstFiles;
	}

	/** Download User Files **/
	@GetMapping(value = "/download/{username}/{fileName:.*}")
	public ResponseEntity<InputStreamResource> download(@PathVariable String username, @PathVariable String fileName)
			throws IOException {
		StringTokenizer st = new StringTokenizer(username);
		String userFile = st.nextToken("@");
		String fullPath = FILE_PATH + userFile + "/" + fileName;
		// System.out.println(fullPath);
		File file = new File(fullPath);
		HttpHeaders respHeaders = new HttpHeaders();
		respHeaders.setContentType(MediaType.APPLICATION_PDF);
		InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
		return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);

	}

	/** Download User Files **/
	@PostMapping(value = "/downloadFile")
	public ResponseEntity<InputStreamResource> download(@RequestBody String documentName) throws IOException {
		Document d = documentRepository.findByDocumentName(documentName);
		File file = new File(d.getPath());
		HttpHeaders respHeaders = new HttpHeaders();
		respHeaders.setContentType(MediaType.APPLICATION_PDF);
		InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
		return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);

	}

	@PostMapping(value = "/getDocumentsByModuleByUser/{id}")
	public List<String> getDocumentsByModuleByUser(@PathVariable Long id, @RequestBody String username) {
		List<Document> lisDoc = documentRepository.findAll();
		List<String> docByModuleAndUser = new ArrayList<>();
		StringTokenizer st1 = new StringTokenizer(username);
		String user1 = st1.nextToken("@");
		if (lisDoc.size() > 0) {
			for (int i = 0; i < lisDoc.size(); i++) {
				StringTokenizer st2 = new StringTokenizer(lisDoc.get(i).getUsername());
				String user2 = st2.nextToken("@");
				if ((user1.equals(user2)) && (lisDoc.get(i).getModule().getId() == id)) {
					docByModuleAndUser.add(lisDoc.get(i).getDocumentName());
				}
			}
			return docByModuleAndUser;
		}
		return docByModuleAndUser;
	}

	@GetMapping(value = "/getDocumentsByModule/{id}")
	public List<String> getDocumentsByModule(@PathVariable Long id) {
		List<Document> lisDoc = documentRepository.findAll();
		List<String> docByModule = new ArrayList<>();
		if (lisDoc.size() > 0) {
			for (int i = 0; i < lisDoc.size(); i++) {
				if (lisDoc.get(i).getModule().getId() == id) {
					docByModule.add(lisDoc.get(i).getDocumentName());
				}
			}
			return docByModule;
		}
		return docByModule;
	}

	@DeleteMapping(value = "/deleteUserFile/{id}")
	public void deleteUserFile(@PathVariable Long id) {
		Document d = documentRepository.findOne(id);
		documentRepository.delete(id);
		storageService.deleteFile(d.getPath());

	}

	@PostMapping(value = "/findDocumentByName")
	public Document findDocumentByName(@RequestBody String fileName) {
		Document d = documentRepository.findByDocumentName(fileName);
		return d;
	}

	@PostMapping(value = "/findDocumentByUseName")
	public List<Document> getDocumentsByUser(@RequestBody String username) {
		List<Document> lisDoc = documentRepository.findAll();
		List<Document> docByUser = new ArrayList<>();
		StringTokenizer st1 = new StringTokenizer(username);
		String user1 = st1.nextToken("@");
		if (lisDoc.size() > 0) {
			for (int i = 0; i < lisDoc.size(); i++) {
				StringTokenizer st2 = new StringTokenizer(lisDoc.get(i).getUsername());
				String user2 = st2.nextToken("@");
				if (user1.equals(user2)) {
					docByUser.add(lisDoc.get(i));
				}
			}
			return docByUser;
		}
		return docByUser;
	}
}
