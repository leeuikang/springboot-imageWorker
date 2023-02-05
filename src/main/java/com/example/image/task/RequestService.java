package com.example.image.task;

import com.example.image.container.Image;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
@Data
public class RequestService {
    private int retryCount;
    private static final String path = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

    public boolean request(Image image) {
        try {
            URL url = new URL(image.getImage());
            String filePath = makeFilePath(image);

            if (!checkExistFolder())
                mkDir();

            for (int tryCount = 1; tryCount <= retryCount; tryCount++) {

                ReadableByteChannel channel = Channels.newChannel(url.openStream());
                try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                    fileOutputStream.getChannel().transferFrom(channel, 0, Long.MAX_VALUE);
                } catch (Exception e){
                    System.err.printf("request fail, retry count = %s%n",tryCount);
                    continue;
                }

                if (imageDownloadSuccessCheck(filePath))
                    return true;
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

    private void mkDir() {
        new File(RequestService.path).mkdir();
    }

    private boolean checkExistFolder() {
        return new File(RequestService.path).exists();
    }

    private String makeFilePath(Image image) {
        return path + "//" + image.getHash() + ".jpg";
    }

    private boolean imageDownloadSuccessCheck(String filePath) {
        return new File(filePath).exists();
    }
}
