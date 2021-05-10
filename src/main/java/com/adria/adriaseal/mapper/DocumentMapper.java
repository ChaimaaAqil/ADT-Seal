package com.adria.adriaseal.mapper;

import com.adria.adriaseal.dto.request.DocumentSignatureParametersDTO;
import com.adria.adriaseal.model.entities.DocumentEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

@Mapper(builder = @Builder(disableBuilder = true))

public interface DocumentMapper extends GeneralMapper {
    DocumentMapper INSTANCE = Mappers.getMapper(DocumentMapper.class);
    @BeanMapping(qualifiedByName = "multipart")
//    @Mapping(source = "params.originX", target = "originX")
//    @Mapping(source = "params.originY", target = "originY")
//    @Mapping(source = "params.height", target = "height")
//    @Mapping(source = "params.width", target = "width")
//    @Mapping(source = "params.pageSeal", target = "pageSeal")
//    @Mapping(source = "params.visible", target = "visible")
    DocumentEntity multiPartFormDataToDocument(MultipartFile multipartFile, DocumentSignatureParametersDTO params);
}
