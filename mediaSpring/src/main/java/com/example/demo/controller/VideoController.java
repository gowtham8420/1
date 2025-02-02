package com.example.demo.controller;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.compresser.ImageUtils;
import com.example.demo.model.FileModel;
import com.example.demo.model.VideoDescription;
import com.example.demo.model.Videos;
import com.example.demo.repository.AddVideoDescriptionRepository;
import com.example.demo.repository.VideoRepository;
import com.example.demo.service.FileService;
import com.example.demo.service.VideoService;

import jakarta.servlet.http.HttpServletRequest;

//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.Files;


@RequestMapping("/api")
@RestController
@CrossOrigin("http://localhost:3000")
public class VideoController {

	@Value("${project.video}")
	private String path;
	
	@Autowired
	private VideoService videoService;

//	@Autowired
//	private VideoInterface service;

	@Autowired
	private FileService fileSevice;

	@Autowired
	private VideoRepository videoRepository;

	@Autowired
	private AddVideoDescriptionRepository videodescriptionRepository;
//	@PostMapping("/save")
//	public ResponseEntity<?> saveVideo(@RequestBody Videos video) {
//		try {
//			Videos saveVideos = service.createPost(video);
//			return new ResponseEntity<Videos>(saveVideos, HttpStatus.OK);
//		} catch (ResourceNotFound e) {
//			ControllerException controllerException = new ControllerException();
//			return new ResponseEntity<ControllerException>(controllerException, HttpStatus.BAD_REQUEST);
//		}
//	}
//-----------------------------gowtham--------------------------
	
	@PostMapping("/uploaddescriprion")
    public ResponseEntity<VideoDescription> upload(@RequestParam("Movie_name") String moviename,
    		                                    @RequestParam("description") String description,
    		                                    @RequestParam("tags") String tags,
    		                                    @RequestParam("category") String category,
    		                                    @RequestParam("certificate") String certificate,
    		                                    @RequestParam("Language") String language,
    		                                    @RequestParam("Duration") String duration,
    		                                    @RequestParam("Year") String year,
    		                                    @RequestParam("thumbnail") MultipartFile thumbnail,
    		                                    @RequestParam("video") MultipartFile video,
                                                @RequestParam(value = "paid", required = false) boolean paid)
                                                {
        try {
        	FileModel upload= fileSevice.uploadVideo(path, video);
        	String name=upload.getVideoFileName();
        	VideoDescription savedesctiption = videoService.saveVideoDescriptio(moviename, description, tags, category, certificate, language, duration, year, thumbnail,name,paid);
//        	System.out.println(videodescriptionRepository.findAll());
            return ResponseEntity.ok().body(savedesctiption);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
	
	@PostMapping("/updatedescriprion")
    public ResponseEntity<VideoDescription> updatedescription(@RequestParam("Movie_name") String moviename,
    		                                    @RequestParam("description") String description,
    		                                    @RequestParam("tags") String tags,
    		                                    @RequestParam("category") String category,
    		                                    @RequestParam("certificate") String certificate,
    		                                    @RequestParam("Language") String language,
    		                                    @RequestParam("Duration") String duration,
    		                                    @RequestParam("Year") String year,
    		                                    @RequestParam("id") long id)
                                                {
        try {
        	VideoDescription updatedesctiption = videoService.saveVideoDescriptio(moviename, description, tags, category, certificate, language, duration, year,id);
//        	System.out.println(videodescriptionRepository.findAll());
            return ResponseEntity.ok().body(updatedesctiption);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
	
	
	
	//Update DataModel of Video .
			@PostMapping("/postit")
			public Videos uploadingVideo(@RequestParam("video") MultipartFile video) throws IOException
			{
//			try {
//				service.updateModel(updateModel, id);
//				return new ResponseEntity<UpdateModel>(updateModel, HttpStatus.OK);	
//				}catch(Exception e) {
//					throw new ResourceNotFound();
//				}
//				throws IOExceptions{}
				Integer id=1;
			
					Videos v=videoService.getVideosById(id);
					FileModel fileModel= fileSevice.uploadVideo(path, video);
					v.setVideoName(fileModel.getVideoFileName());
					Videos finallyUpload= videoService.updatePost1(v);	
					
					return finallyUpload;
	}

			//4.To play video .
//			@GetMapping(value = "/play/{id}", produces = MediaType.ALL_VALUE)
//			public void downloadImage(@PathVariable Long id, HttpServletResponse response) throws IOException {
//				Optional<VideoDescription> video = videodescriptionRepository.findById(id);
//				InputStream resource = this.fileSevice.getVideoFIle(path, video.get().getName(), id);
//				response.setContentType(MediaType.ALL_VALUE);
//				StreamUtils.copy(resource, response.getOutputStream());
//			}
			
			@GetMapping(value = "/play/{id}")
			 private ResponseEntity<?> getVideo(@PathVariable Long id, HttpServletRequest request) {
		        try {
//		            Optional<videoLessons> optionalLesson = lessonrepo.findById(lessId);
		            Optional<VideoDescription> optionalLesson = videodescriptionRepository.findById(id);
		            if (!optionalLesson.isPresent()) {
		                return ResponseEntity.notFound().build();
		            }
		            
		            
//		            videoLessons lesson = optionalLesson.get();
		            String filename =optionalLesson.get().getName();
		   
		            if (filename != null) {
		            	Path filePath = Paths.get(path, filename);
		            	System.out.println("filePath"+ filePath);
		    		    try {
		    		        if (filePath.toFile().exists() && filePath.toFile().isFile()) {
		    		            Resource resource = new UrlResource(filePath.toUri());
		    		            if (resource.exists() && resource.isReadable()) {
		    		                HttpHeaders headers = new HttpHeaders();

		    		                // Set the Content-Type based on the file's extension
		    		                String mimeType = Files.probeContentType(filePath);
		    		                if (mimeType == null) {
		    		                    mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
		    		                }
		    		                headers.add(HttpHeaders.CONTENT_TYPE, mimeType);

		    		                // Set Content-Disposition to "inline" to stream the video inline
		    		                headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline");

		    		                // Define the initial chunk size (5 MB)
		    		                final long INITIAL_CHUNK_SIZE = 5 * 1024 * 1024; // 5 MB
		    		                long fileSize = Files.size(filePath);

		    		                // Get the Range header from the request
		    		                String rangeHeader = request.getHeader(HttpHeaders.RANGE);

		    		                if (rangeHeader != null) {
		    		                    // Handle range request from the client
		    		                    String[] ranges = rangeHeader.replace("bytes=", "").split("-");
		    		                    long rangeStart = Long.parseLong(ranges[0]);
		    		                    long rangeEnd = ranges.length > 1 ? Long.parseLong(ranges[1]) : fileSize - 1;

		    		                    // Calculate the content length
		    		                    long contentLength = rangeEnd - rangeStart + 1;

		    		                    System.out.println("Range Start: " + rangeStart + ", Range End: " + rangeEnd + ", Content Length: " + contentLength);
		    		                    // Create a RandomAccessFile to read the specified range
		    		                    try (RandomAccessFile file = new RandomAccessFile(filePath.toFile(), "r")) {
		    		                        file.seek(rangeStart);
		    		                        byte[] buffer = new byte[(int) contentLength];
		    		                        file.readFully(buffer);

		    		                        // Create a ByteArrayResource to hold the requested range of bytes
		    		                        ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);

		    		                        // Set the Content-Range header
		    		                        headers.add(HttpHeaders.CONTENT_RANGE, String.format("bytes %d-%d/%d", rangeStart, rangeEnd, fileSize));
		    		                        System.out.println("Range Start: " + rangeStart + ", Range End: " + rangeEnd + ", Content Length: " + contentLength);

		    		                        // Return a 206 Partial Content response
		    		                        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
		    		                                .headers(headers)
		    		                                .contentLength(contentLength)
		    		                                .body(byteArrayResource);
		    		                    }
		    		                } else {
		    		                    // No range header, send the initial 5 MB chunk
		    		                    long rangeStart = 0;
		    		                    long rangeEnd = Math.min(INITIAL_CHUNK_SIZE - 1, fileSize - 1);
		    		                    long contentLength = rangeEnd - rangeStart + 1;
	    		                    System.out.println("Range Start: " + rangeStart + ", Range End: " + rangeEnd + ", Content Length: " + contentLength);

		    		                    // Create a RandomAccessFile to read the specified range
		    		                    try (RandomAccessFile file = new RandomAccessFile(filePath.toFile(), "r")) {
		    		                        file.seek(rangeStart);
		    		                        byte[] buffer = new byte[(int) contentLength];
		    		                        file.readFully(buffer);

		    		                        // Create a ByteArrayResource to hold the requested range of bytes
		    		                        ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);

		    		                        // Set the Content-Range header
		    		                        headers.add(HttpHeaders.CONTENT_RANGE, String.format("bytes %d-%d/%d", rangeStart, rangeEnd, fileSize));

		    		                        // Return a 206 Partial Content response
		    		                        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
		    		                                .headers(headers)
		    		                                .contentLength(contentLength)
		    		                                .body(byteArrayResource);
		    		                    }
		    		                }
		    		            }
		    		        }else {

				            	System.out.println("file is null");
		    		        }
		    		    } catch (Exception e) {
		    		        // Handle exceptions
		    		        e.printStackTrace();
		    		    }

		    		    // Return a 404 Not Found response if the file does not exist
		    		    return ResponseEntity.notFound().build();

		            } else {
		                return ResponseEntity.ok(filename);
		            }
		        } catch (Exception e) {
		            // Log the exception (you can use a proper logging library)
		            e.printStackTrace();
		            // Return an internal server error response
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		        }
		        
		    }
		
		
	
			//4.To get all video  .
			@GetMapping(value = "/updatevideo")
			public ResponseEntity<String> updateById() throws IOException {
				videoService.updatevideoWithFile();
//			    
			    return new ResponseEntity<>( HttpStatus.OK);
			}
			
//			@GetMapping(value = "/videogetall", produces = MediaType.ALL_VALUE)
//			public ResponseEntity<VideoDescription> videogetall() {
////				System.out.println(videodescriptionRepository.findById(id));
////				return videodescriptionRepository.findById(id).orElse(null);
//				try {
//		            Optional<List<VideoDescription>> videoDetail = Optional.of(videodescriptionRepository.findAll());
//		            System.out.println(videoDetail.get());
//		            
//		            if (videoDetail.isPresent()) {
////		            	System.out.println(videoDetail.get());
//		                return new ResponseEntity<>(videoDetail,HttpStatus.OK);
//		            } else {
//		                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		            }
//		        } catch (Exception e) {
//		            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		        }
//		    }
//			
			 @GetMapping(value = "/videogetall")
			    public ResponseEntity<List<VideoDescription>> videogetall() {
			        try {
			            List<VideoDescription> videoDetails = videodescriptionRepository.findAll();
			            System.out.println("All video passed");
			            if (!videoDetails.isEmpty()) {
			                return new ResponseEntity<>(videoDetails, HttpStatus.OK);
			            } else {
			                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			            }
			        } catch (Exception e) {
			            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			        }
			    }
			 
			 @GetMapping("/GetvideoThumbnail")
			    public ResponseEntity<List<byte[]>> getAllThumbnail() {
			        List<VideoDescription> getVideo = videodescriptionRepository.findAll();
			       
			        for (VideoDescription video : getVideo) {
			            byte[] images = ImageUtils.decompressImage(video.getThumbnail());
			            video.setThumbnail(images);
			        }

			        return ResponseEntity.ok()
			                .contentType(MediaType.APPLICATION_JSON)
			                .body(getVideo.stream().map(VideoDescription::getThumbnail).collect(Collectors.toList()));
			    }
			 @GetMapping("/video/{id}")
			    public ResponseEntity<String> deleteAudioById(@PathVariable Long id) {
				 System.out.println(id);
			        try {
			            // Call the service method to delete audio by ID
//			            boolean deleted = audioservice.deleteAudioById(id);
//			        	int videoId = Long.parseInt(id);
			        	boolean deleted = videoService.deletevideoById(id);
			        	
			        	
			        	System.out.println(id);
//			            boolean audiodeleted = audioservice.deleteAudioFile(fileName);

			            if (deleted) {
			                return ResponseEntity.ok().body("Audio deleted successfully");
			            } else {
			            	System.out.println("Audio not deleted ");
			                return ResponseEntity.notFound().build();
			            }
			        } catch (Exception e) {
			            e.printStackTrace();
			            return ResponseEntity.badRequest().build();
			        }
			    }
			 
			 
			 
			 
			 
			 
			
			 @GetMapping("/GetvideoDetail/{id}")
			    public ResponseEntity<VideoDescription> getAudioDetail(@PathVariable Long id) {
			        try {
			            Optional<VideoDescription> audioDetail = videodescriptionRepository.findById(id);
			            
			            
			            if (audioDetail.isPresent()) {
			            	System.out.println(audioDetail.get());
			                return new ResponseEntity<>(audioDetail.get(), HttpStatus.OK);
			            } else {
			                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			            }
			        } catch (Exception e) {
			            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			        }
			    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@PostMapping("/save")
	public ResponseEntity<?> saveVideo(@RequestBody Videos video) {
//		try {
//			Videos saveVideos = service.createPost(video);
//			return new ResponseEntity<Videos>(saveVideos, HttpStatus.OK);
//		} catch (ResourceNotFound e) {
//			ControllerException controllerException = new ControllerException();
			return new ResponseEntity<Videos>(videoService.createPost(video),HttpStatus.OK);
//		}
	}

	//1. get Video by id ;
//	@GetMapping("/get/{id}")
//	public ResponseEntity<?> getDataById(@PathVariable Integer id) {
//		try {
//			Videos video = service.getVideosById(id);
//			return new ResponseEntity<Videos>(video, HttpStatus.CREATED);
//
//		} catch (ResourceNotFound e) {
//			ControllerException controllerException = new ControllerException();
//			return new ResponseEntity<ControllerException>(controllerException, HttpStatus.BAD_REQUEST);
//		} catch (Exception e) {
//			ControllerException controllerException = new ControllerException();
//			return new ResponseEntity<ControllerException>(controllerException, HttpStatus.BAD_REQUEST);
//		}
//	}
	
//	----------------------------------------------------------
//	@GetMapping("/get/{id}")
//	public Videos getVideosById(@PathVariable Integer id) {
//		
//	
//		return videoService.getVideosById(id);
//	}
////	
	

//	//2. Get List Of videos .
//	@SuppressWarnings("unused")
//	@GetMapping("/all")
//	public ResponseEntity<?> getListofData() {
//		Videos videos = new Videos();
//		try {
//			if (videos == null) {
//				throw new ResourceNotFound();
//			}
//			List<Videos> v = service.getAllVideos();
//			return new ResponseEntity<List<Videos>>(v, HttpStatus.CREATED);
//		} catch (Exception e) {
//			ControllerException controllerException = new ControllerException();
//			return new ResponseEntity<ControllerException>(controllerException, HttpStatus.BAD_REQUEST);
//		}
//	}

//	//3. Posting Video api .
//	@PostMapping("/upload/{id}")
//	public ResponseEntity<Videos> uploadVideo(@RequestParam("video") MultipartFile video, @PathVariable Integer id)
//			throws IOException {
//		Videos v = this.service.getVideosById(id);
//		FileModel fileModel = this.fileSevice.uploadVideo(path, video);
//		v.setVideoName(fileModel.getVideoFileName());
//		Videos uploadVideo = this.service.updatePost(v, id);
//		return new ResponseEntity<Videos>(uploadVideo, HttpStatus.OK);
//	}



//	//5. Delete videos by id .
//	@DeleteMapping("/{id}")
//	public String deleteVideo(@PathVariable Integer id) throws IOException {
//		Optional<Videos> video = videoRepository.findById(id);
//		Path exactPath = Paths.get(path + File.separator + video.get().getVideoName());
//		System.out.println(exactPath);
//		try {
//			Files.deleteIfExists(exactPath);
//
//		} catch (Exception e1) {
//			e1.getMessage();
//			System.out.println(e1.getMessage()+"can not delete now ");
//		}
//		this.service.deleteVideos(id);
//
//		return "video deleted successfully";
//	}
	
//	//Update DataModel of Video .
//	@PutMapping("/update/{id}")
//	public ResponseEntity<UpdateModel> setVideoData( @RequestBody UpdateModel updateModel,@PathVariable int id){
//		try {
//		service.updateModel(updateModel, id);
//		return new ResponseEntity<UpdateModel>(updateModel, HttpStatus.OK);	
//		}catch(Exception e) {
//			throw new ResourceNotFound();
//		}
//	}
//	
	
////	---------------------playvideo--------------------
//	@GetMapping(value="/playit/{id}",produces=MediaType.ALL_VALUE)
//	public void playVideo(@PathVariable Integer id,HttpServletResponse response) throws IOException {
//		Optional<Videos> video= videoRepository.findById(id);
//		List<Videos> samp= videoRepository.findAll();
//		System.out.print(samp);
//		InputStream resourse = fileSevice.getVideoFIle(path,video.get().getVideoName(), id);
//		response.setContentType(MediaType.ALL_VALUE);
//		StreamUtils.copy(resourse,response.getOutputStream());
//	}
	
	
	
			
		
}
		
		

