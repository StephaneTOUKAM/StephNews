package com.stephanetoukam.stephnews.services.impl;

import com.stephanetoukam.stephnews.config.StorageProperties;
import com.stephanetoukam.stephnews.error.ApiErrorException;
import com.stephanetoukam.stephnews.error.StorageException;
import com.stephanetoukam.stephnews.error.StorageFileNotFoundException;
import com.stephanetoukam.stephnews.services.StorageService;
import com.stephanetoukam.stephnews.utils.DataBucketUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class FileSystemStorageService implements StorageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileSystemStorageService.class);

    private final DataBucketUtil dataBucketUtil;

    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(DataBucketUtil dataBucketUtil, StorageProperties properties) {
        this.dataBucketUtil = dataBucketUtil;

        if(properties.getLocation().trim().length() == 0){
            throw new StorageException("File upload location can not be Empty.");
        }

        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public String storeInCloud(MultipartFile file) {
        LOGGER.debug("Start file uploading service");
        String fileUrl = "";
        String originalFileName = file.getOriginalFilename();
        if(originalFileName == null){
            throw new ApiErrorException("Original file name is null");
        }
        Path path = new File(originalFileName).toPath();

        try {
            String contentType = Files.probeContentType(path);
            fileUrl = dataBucketUtil.uploadFile(file, originalFileName, contentType);

            if (fileUrl != null) {
                LOGGER.debug("File uploaded successfully, file url: {}", fileUrl);
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred while uploading. Error: ", e);
            throw new StorageException("Error occurred while uploading");
        }
        return fileUrl;
    }

    @Override
    public String storeInLocal(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }

            String fileCustomName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            fileCustomName = Instant.now() + fileCustomName.toLowerCase().replaceAll(" ", "-");

            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(fileCustomName))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }

            return fileCustomName;
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
