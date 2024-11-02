package com.endcredits.backend.controllers;

import com.endcredits.backend.RequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.Map;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping(value = "/api/v1/b64-image", consumes = MediaType.APPLICATION_JSON_VALUE)
public class Base64ImageController {
    private static final Logger log = LoggerFactory.getLogger(Base64ImageController.class);

    @PostMapping("/create")
    public ResponseEntity<?> createVideo(@RequestBody RequestDto imgData) {
        log.info(String.valueOf(imgData)); // check imgData

        // Extract data from client
        String imgName = imgData.getImgName();
        log.info("image name: {}", imgName);
        String b64data = imgData.getBase64img();
        log.info("base64data: {}", b64data.substring(0, 10));

        String imageDataBytes = b64data.substring(b64data.indexOf(',') + 1);
        try {
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(imageDataBytes)));
            log.info("Starting to write image");

            File imageDir = new File("./images");
            if (imageDir.mkdir() || imageDir.exists()) {
                try {
                    for (int i = 0; i <= bufferedImage.getHeight(); i++) {
                        BufferedImage subImage = bufferedImage.getSubimage(0, i, bufferedImage.getWidth(), bufferedImage.getHeight() / 2);
                        File imageOutFile = new File(imageDir, "image" + i + ".png");
                        ImageIO.write(subImage, "png", imageOutFile);
                    }
                } catch (FileNotFoundException fileNotFoundException) {
                    log.error("File not found", fileNotFoundException);
                } catch (Exception exception) {
                    log.error("An error occurred during image processing", exception);
                }
            }
        } catch (Exception exception) {
            log.error("An error occurred while decoding base64 image", exception);
        }

        log.info("Images created successfully");
        try {
            // Define the command and its arguments
            int exitCode = getExitCode();
            log.info("ffmpeg process exited with code: {}", exitCode);
        } catch (IOException | InterruptedException e) {
            log.error("An error occurred while running ffmpeg command", e);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("status", "success"));
    }

    private static int getExitCode() throws IOException, InterruptedException {
        String[] command = {
                "ffmpeg",
                "-framerate", "50",
                "-i", "image%d.png",
                "-c:v", "libx264",
                "-pix_fmt", "yuv420p",
                "output.mp4"
        };

        // Set the working directory (where your images are located)
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(new File("C:\\Users\\DAVID-BAKARE\\Documents\\EndCredits\\version 1\\backend\\images"));

        log.info("Started processing files with ffmpeg");

        // Make the subprocess inherit I/O (to see output directly in console)
        processBuilder.inheritIO();

        // Start the process
        Process process = processBuilder.start();

        // Wait for the process to complete and return the exit code
        return process.waitFor();
    }

}
