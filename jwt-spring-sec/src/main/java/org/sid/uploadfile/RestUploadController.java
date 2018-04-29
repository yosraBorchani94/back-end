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
import org.sid.dao.UserRepository;
import org.sid.entities.AppUser;
import org.sid.entities.Document;
import org.sid.entities.Module;
import org.sid.entities.Quiz;
import org.sid.entities.Video;
import org.sid.uploadfile.StorageService;;

@CrossOrigin("*")
@RestController
public class RestUploadController {
	private static final String FILE_PATH = "C:/upload/";
	private static final String IMAGE_PATH = "C:/Users/yborchani/angular/starter-kit/src/assets/img/UploadImage/";
	@Autowired
	StorageService storageService;

	@Autowired
	private ModuleRepository moduleRepository;

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private QuizRepository quizRepository;

	@Autowired
	private UserRepository userRepository;

	
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
			if (files.add(file.getOriginalFilename())) {
				Quiz q = quizRepository.findOne(id);
				q.setId(id);
				q.setUrlPicture(IMAGE_PATH + id + "/" + file.getOriginalFilename());
				quizRepository.save(q);
			}
			return "You successfully uploaded - " + file.getOriginalFilename();
		} catch (Exception e) {
			throw new Exception("FAIL! Maybe You had uploaded the file before or the file's size > 500KB");
		}
	}

	/* Multiple file upload */
	@PostMapping(value = "/uploadfile")
	public String uploadFileMulti(@RequestParam("uploadfile") MultipartFile file,
			@RequestParam("idModule") Long idModule, @RequestParam("username")  String username) throws Exception {

		try {
			System.out.println(username);
			AppUser user = userRepository.findByUsername(username);
			StringTokenizer st = new StringTokenizer(username);
			String userFile = st.nextToken("@");
			Module m = moduleRepository.findOne(idModule);
			Document d = new Document();
			d.setModule(m);
			d.setDocumentName(file.getOriginalFilename());
			d.setUser(user);
			d.setAccpetd(true);
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
	
	
	@PostMapping(value = "/uploadUserfile")
	public String uploadUserfile(@RequestParam("uploadfile") MultipartFile file,
			@RequestParam("idModule") Long idModule, @RequestParam("username")  String username) throws Exception {

		try {
			System.out.println(username);
			AppUser user = userRepository.findByUsername(username);
			StringTokenizer st = new StringTokenizer(username);
			String userFile = st.nextToken("@");
			Module m = moduleRepository.findOne(idModule);
			Document d = new Document();
			d.setModule(m);
			d.setDocumentName(file.getOriginalFilename());
			d.setUser(user);
			d.setAccpetd(false);
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

	/** Download User File **/
	@GetMapping(value = "/download/{username}/{fileName:.*}")
	public ResponseEntity<InputStreamResource> download(@PathVariable String username, @PathVariable String fileName)
			throws IOException {
		StringTokenizer st = new StringTokenizer(username);
		String userFile = st.nextToken("@");
		String fullPath = FILE_PATH + userFile + "/" + fileName;
		System.out.println(fullPath);
		File file = new File(fullPath);
		HttpHeaders respHeaders = new HttpHeaders();
		respHeaders.setContentType(MediaType.APPLICATION_PDF);
		InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
		return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);

	}

	/** Download All User Files **/
	@GetMapping(value = "/downloadAllUserFile/{username}")
	public void downloadAllUserFiles(@PathVariable String username) throws IOException {
		AppUser user = userRepository.findByUsername(username);
		List<Document> ls = getDocumentsByUser(username);
		if (ls.size() > 0) {
			for (int i = 0; i < ls.size(); i++) {
				System.out.println("username " + username + " ls.getUser().getId() " + ls.get(i).getUser().getId());
				if (ls.get(i).getUser().getId() == user.getId()) {
					File file = new File(ls.get(i).getPath());
					HttpHeaders respHeaders = new HttpHeaders();
					respHeaders.setContentType(MediaType.APPLICATION_PDF);
					InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
				}
			}

		}
	}

	/** Download Files By module **/
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
		AppUser user = userRepository.findByUsername(username);
		if (lisDoc.size() > 0) {
			for (int i = 0; i < lisDoc.size(); i++) {
				if ((lisDoc.get(i).getUser().getId() == user.getId()) && (lisDoc.get(i).getModule().getId() == id) && (lisDoc.get(i).isAccpetd())) {
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
				if ((lisDoc.get(i).getModule().getId()) == id && (lisDoc.get(i).isAccpetd())) {
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
	
	
	
	@PostMapping(value = "/AcceptDocument")
	public void AcceptDocument(@RequestBody Document doc) {
		doc.setAccpetd(true);
		documentRepository.save(doc);
	}
	
	
	@DeleteMapping(value = "/RefuseDocument/{id}")
	public void AcceptDocument(@PathVariable Long id) {
		documentRepository.delete(id);
	}

	@PostMapping(value = "/findDocumentByUseName")
	public List<Document> getDocumentsByUser(@RequestBody String username) {
		List<Document> lisDoc = documentRepository.findAll();
		AppUser user = userRepository.findByUsername(username);
		List<Document> docByUser = new ArrayList<>();
		if (lisDoc.size() > 0) {
			for (int i = 0; i < lisDoc.size(); i++) {
				if (lisDoc.get(i).getId() == user.getId()) {
					docByUser.add(lisDoc.get(i));
				}
			}
			return docByUser;
		}
		return docByUser;
	}
	
	
	@GetMapping(value = "/getNonAcceptedDocuments")
	public List<Document> getNonAcceptedDocuments() {
		List<Document> lisDoc = documentRepository.findAll();
		List<Document>NonAcceptedDocuments = new ArrayList<>();
		if (lisDoc.size() > 0) {
			for (int i = 0; i < lisDoc.size(); i++) {
				if (!lisDoc.get(i).isAccpetd()) {
					NonAcceptedDocuments.add(lisDoc.get(i));
				}
			}
			return NonAcceptedDocuments;
		}
		return NonAcceptedDocuments;
	}
}
