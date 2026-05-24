package com.stream.app.services.impl;

import com.stream.app.entities.Video;
import com.stream.app.repositories.VideoRepository;
import com.stream.app.services.VideoService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.io.*;

@Service
public class VideoServiceImpl implements VideoService {

    @Value("${files.video}")
    String DIR;

    @Value("${file.video.hsl}")
    String HSL_DIR;


    private VideoRepository videoRepository;


    public VideoServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @PostConstruct
    public void init() {

        File file = new File(DIR);


        try {
            Files.createDirectories(Paths.get(HSL_DIR));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!file.exists()) {
            file.mkdir();
            System.out.println("Folder Created:");
        } else {
            System.out.println("Folder already created");
        }

    }

    @Override
    public Video save(Video video, MultipartFile file) {
        // original file name

        try {


            String filename = file.getOriginalFilename();
            String contentType = file.getContentType();
            InputStream inputStream = file.getInputStream();


            // file path
            String cleanFileName = StringUtils.cleanPath(filename);


            //folder path : create

            String cleanFolder = StringUtils.cleanPath(DIR);


            // folder path with  filename
            Path path = Paths.get(cleanFolder, cleanFileName);

            System.out.println(contentType);
            System.out.println(path);

            // copy file to the folder
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);


            // video meta data

            video.setContentType(contentType);
            video.setFilePath(path.toString());
            Video savedVideo = videoRepository.save(video);
            //processing video
            processVideo(savedVideo.getVideoId());

            //delete actual video file and database entry  if exception

            // metadata save
            return savedVideo;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error in processing video ");
        }


    }


    @Override
    public Video get(String videoId) {
        Video video = videoRepository.findById(videoId).orElseThrow(() -> new RuntimeException("video not found"));
        return video;
    }

    @Override
    public Video getByTitle(String title) {
        return null;
    }

    @Override
    public List<Video> getAll() {
        return videoRepository.findAll();
    }

@Override
public String processVideo(String videoId) {
    Video video = this.get(videoId);
    String filePath = video.getFilePath();

    // Path where to store data:
    Path videoPath = Paths.get(filePath);

    try {
        // ffmpeg command
        Path outputPath = Paths.get(HSL_DIR, videoId);
        Files.createDirectories(outputPath);

        String ffmpegCmd = "ffmpeg -i \"" + videoPath.toString() + "\" -c:v libx264 -c:a aac -strict -2 -f hls -hls_time 10 -hls_list_size 0 -hls_segment_filename \"" + outputPath.toString() + "\\segment_%03d.ts\" \"" + outputPath.toString() + "\\master.m3u8\"";

        System.out.println("Running ffmpeg command: " + ffmpegCmd);

        // Run the command on Windows using cmd.exe
        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", ffmpegCmd);
        processBuilder.inheritIO();
        Process process = processBuilder.start();
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw new RuntimeException("Video processing failed!");
        }

        return videoId;

    } catch (IOException ex) {
        throw new RuntimeException("Video processing failed due to IOException!", ex);
    } catch (InterruptedException ex) {
        throw new RuntimeException("Video processing was interrupted!", ex);
    }
}

}
