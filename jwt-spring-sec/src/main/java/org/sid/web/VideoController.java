package org.sid.web;

import java.util.ArrayList;
import java.util.List;

import org.sid.dao.VideoRepository;
import org.sid.entities.Event;
import org.sid.entities.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class VideoController {
	@Autowired
	private VideoRepository videoRepository;
	
	@PostMapping("/video")
	public Video save(@RequestBody Video v){
		return videoRepository.save(v);
	}
	
	@GetMapping("/video")
	public List<Video> listVideos(){
		return videoRepository.findAll();
	}
	
	@GetMapping("/video/{id}")
	public Video getVideo(@PathVariable Long id){
		return videoRepository.findOne(id);
	}
	
	@DeleteMapping("/video/{id}")
	public boolean delete(@PathVariable Long id){
		videoRepository.delete(id);
		return true ;
	}
	@PutMapping("/video/{id}")
	public Video updateVideo (@PathVariable Long id,@RequestBody Video v) {
		v.setId(id);
		return videoRepository.save(v);
	}
	
	@GetMapping("/getVideosByModule/{idModule}")
	public List<Video> getVideosByModule (@PathVariable Long idModule) {
		List<Video> video = videoRepository.findAll();
		List<Video> videosByModule = new ArrayList<>();
		for (int i = 0; i < video.size(); i++) {
			if (video.get(i).getModule().getId() == idModule) {
				videosByModule.add(video.get(i));
			}
		}
		
		return videosByModule;
	}
	
	
}
