package com.stephanetoukam.stephnews.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.stephanetoukam.stephnews.error.ApiErrorException;
import com.stephanetoukam.stephnews.error.StorageException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;

@Component
public class DataBucketUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataBucketUtil.class);

    @Value("${gcp.config.type}")
    private String gcpType;

    @Value("${gcp.config.private_key_id}")
    private String gcpPrivateKeyId;

    @Value("${gcp.config.private_key}")
    private String gcpPrivateKey;

    @Value("${gcp.config.client_email}")
    private String gcpClientEmail;

    @Value("${gcp.config.client_id}")
    private String gcpClientId;

    @Value("${gcp.config.auth_uri}")
    private String gcpAuthUri;

    @Value("${gcp.config.token_uri}")
    private String gcpTokenUri;

    @Value("${gcp.config.auth_provider_x509_cert_url}")
    private String gcpAuthProvider;

    @Value("${gcp.config.client_x509_cert_url}")
    private String gcpClientCertUrl;

    @Value("${gcp.config.universe_domain}")
    private String gcpUniverseDomain;

    @Value("${gcp.project.id}")
    private String gcpProjectId;

    @Value("${gcp.bucket.id}")
    private String gcpBucketId;

    @Value("${gcp.dir.name}")
    private String gcpDirectoryName;


    public String uploadFile(MultipartFile multipartFile, String fileName, String contentType) {
        try{

            byte[] fileData = multipartFile.getBytes();

            HashMap<String, String> map = new HashMap<>();
            map.put("type", gcpType);
            map.put("project_id", gcpProjectId);
            map.put("private_key_id", gcpPrivateKeyId);
            map.put("private_key", gcpPrivateKey);
            map.put("client_email", gcpClientEmail);
            map.put("client_id", gcpClientId);
            map.put("auth_uri", gcpAuthUri);
            map.put("token_uri", gcpTokenUri);
            map.put("auth_provider_x509_cert_url", gcpAuthProvider);
            map.put("client_x509_cert_url", gcpClientCertUrl);
            map.put("universe_domain", gcpUniverseDomain);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(map);

            byte[] byteArray = jsonString.getBytes(StandardCharsets.UTF_8);

            InputStream inputStream = new ByteArrayInputStream(byteArray);

            StorageOptions options = StorageOptions.newBuilder().setProjectId(gcpProjectId)
                    .setCredentials(GoogleCredentials.fromStream(inputStream)).build();

            Storage storage = options.getService();
            Bucket bucket = storage.get(gcpBucketId, Storage.BucketGetOption.fields());

            var id = Instant.now().toString();
            Blob blob = bucket.create(gcpDirectoryName + "/file-" + id + checkFileExtension(fileName), fileData, contentType);

            if(blob != null){
                return "https://storage.googleapis.com/"+gcpBucketId+"/"+blob.getBlobId().getName();
            }
        }catch (Exception e){
            LOGGER.error("An error occurred while uploading data. Exception: ", e);
            throw new StorageException("An error occurred while storing data to GCS");
        }
        throw new StorageException("An error occurred while storing data to GCS");
    }

    private String checkFileExtension(String fileName) {
        if(fileName != null && fileName.contains(".")){
            String[] extensionList = {".png", ".jpeg", ".pdf", ".doc", ".mp3"};

            for(String extension: extensionList) {
                if (fileName.endsWith(extension)) {
                    return extension;
                }
            }
        }
        LOGGER.error("Not a permitted file type");
        throw new StorageException("Not a permitted file type");
    }
}