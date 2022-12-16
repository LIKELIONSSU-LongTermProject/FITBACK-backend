package com.fitback.ssu.util;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class OCR {
    public static String extractEmail(MultipartFile image){ // 명함 사진 입력 받아야함
        String email = "";
        try {
            List<AnnotateImageRequest> requests = new ArrayList<>();

            // Reads the image file into memory
            ByteString imgBytes = ByteString.readFrom(new ByteArrayInputStream(image.getBytes()));
            Image img = Image.newBuilder().setContent(imgBytes).build();

            Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
            AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
            requests.add(request);
            try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
                BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
                List<AnnotateImageResponse> responses = response.getResponsesList();

                for (AnnotateImageResponse res : responses) {
                    if (res.hasError()) {
                        System.out.printf("Error: %s\n", res.getError().getMessage());
                        return "";
                    }
                    String str = res.getTextAnnotationsList().get(0).getDescription();

                    int targetNum = str.indexOf("E ");
                    String searchEmail = str.substring(targetNum,(str.substring(targetNum).indexOf("\n")+targetNum));
                    email = searchEmail.split(" ")[1];
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
        return email;
    }
}
