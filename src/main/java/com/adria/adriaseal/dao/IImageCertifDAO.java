package com.adria.adriaseal.dao;

import com.adria.adriaseal.model.entities.ImageCertifEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public interface IImageCertifDAO {
    ImageCertifEntity save(MultipartFile[] signatureImage, Date uploadDate);
}
