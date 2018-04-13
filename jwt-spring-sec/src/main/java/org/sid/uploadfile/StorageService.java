package org.sid.uploadfile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {

	Logger log = LoggerFactory.getLogger(this.getClass().getName());
	private final Path rootLocation = Paths.get("C:/upload");
	private Path rootLocation1;
	private Path imageLocation;
	
	public void storeImage(MultipartFile file, String username) {
		imageLocation = Paths.get("C:/Users/yborchani/angular/starter-kit/src/assets/img/UploadImage/" + username);

		try {

			if (Files.exists(imageLocation)) {
				// FileSystemUtils.deleteRecursively(imageLocation.toFile());
				Files.createDirectory(imageLocation);
			} else if (!Files.exists(imageLocation)) {
				Files.createDirectory(imageLocation);
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			Files.copy(file.getInputStream(), this.imageLocation.resolve(file.getOriginalFilename()));
		} catch (Exception e) {
			throw new RuntimeException("FAIL!");
		}
	}

	public void store(MultipartFile file, String username) {
		rootLocation1 = Paths.get("C:/upload/" + username);
		try {
			if (!Files.exists(rootLocation)) {
				Files.createDirectory(rootLocation);
			}
			if (!Files.exists(rootLocation1)) {
				Files.createDirectory(rootLocation1);
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			Files.copy(file.getInputStream(), this.rootLocation1.resolve(file.getOriginalFilename()));

		} catch (Exception e) {
			throw new RuntimeException("FAIL!");
		}
	}

	public Resource loadFile(String filename) {
		try {
			Path file = rootLocation.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("FAIL!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("FAIL!");
		}
	}

	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	public void deleteFile(String pathFile) {
		Path path = Paths.get(pathFile);
		FileSystemUtils.deleteRecursively(path.toFile());
	}

	public void init() {
		try {
			Files.createDirectory(rootLocation);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize storage!");
		}
	}
}
