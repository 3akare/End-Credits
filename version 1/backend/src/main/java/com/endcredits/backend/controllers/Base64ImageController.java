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
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Base64;
import java.util.Map;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping(value = "/api/v1/b64-image", consumes = MediaType.APPLICATION_JSON_VALUE)
public class Base64ImageController{
    private static final Logger log = LoggerFactory.getLogger(Base64ImageController.class);

    @PostMapping("/create")
    public ResponseEntity<?> createVideo(@RequestBody RequestDto imgData){
        log.info(String.valueOf(imgData)); // check imgData

        // Extract data from client
        String imgName = imgData.getImgName();
        log.info("image name: {}", imgName);
        String b64data = imgData.getBase64img();
        log.info("base64data: {}", b64data);

        String imageDataBytes = b64data.substring(b64data.indexOf(',') + 1);
        try{
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(imageDataBytes)));
            if(new File("./images").mkdir()){
                try{
                    for (int i = 0; i <= bufferedImage.getHeight(); i += 10){
                        BufferedImage bufferedImage1 = bufferedImage.getSubimage(0, i, bufferedImage.getWidth(), bufferedImage.getHeight() / 2);
                        File imageOutFile2 = new File("./images/" + imgName + i + ".png");
                        ImageIO.write(bufferedImage1, "png", imageOutFile2);
                    }
                }
                catch (FileNotFoundException fileNotFoundException){
                    System.out.println("File not found");
                }
                catch (Exception exception){
                    System.out.println("Exceeded Raster");
                }
            }
        } catch (Exception exception){
            System.out.println("An Error Occurred");
        }
    return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("status", "success"));
    }
}