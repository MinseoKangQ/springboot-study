package com.likelion.community.service;

import com.likelion.community.model.MediaDescriptorDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public interface MediaService {
    MediaDescriptorDto saveFile(MultipartFile file);
    Collection<MediaDescriptorDto> saveFileBulk(MultipartFile[] files);
    byte[] getFileAsBytes(String resourcePath);
}
