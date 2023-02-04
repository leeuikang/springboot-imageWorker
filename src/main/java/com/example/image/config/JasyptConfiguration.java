package com.example.image.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.encryption.pbe.config.StringPBEConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.Files;


@Configuration
@EnableEncryptableProperties
public class JasyptConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "jasypt.encryptor")
    public StringPBEConfig stringPBEConfig() {
        return new SimpleStringPBEConfig();
    }

    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor(StringPBEConfig stringPBEConfig) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(stringPBEConfig);
        encryptor.setPassword(getPassword());
        return encryptor;
    }

    private static String getPassword() {
        try {
            File file = new File("./encryption_key.txt");

            if (file.exists())
                return Files.readAllLines(file.getAbsoluteFile().toPath()).get(0);

        } catch (Exception e) {
            System.err.println("log");
        }

        return "";
    }
}