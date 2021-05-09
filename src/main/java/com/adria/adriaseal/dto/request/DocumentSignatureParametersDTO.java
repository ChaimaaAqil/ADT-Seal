package com.adria.adriaseal.dto.request;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@Valid
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentSignatureParametersDTO {
    @NotNull
    private Boolean visible;
    //max size of a 300dpi 4A0 file 19866 x 28087
    @Range(min = 0, max = 30000)
    private Float originX;
    @Range(min = 0, max = 30000)
    private Float originY;
    @Range(min = 50, max = 1000)
    private Float height;
    @Range(min = 50, max = 1000)
    private Float width;
    @Positive
    private Integer pageSeal;

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Float getOriginX() {
        return originX;
    }

    public void setOriginX(Float originX) {
        this.originX = originX;
    }

    public Float getOriginY() {
        return originY;
    }

    public void setOriginY(Float originY) {
        this.originY = originY;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public Integer getPageSeal() {
        return pageSeal;
    }

    public void setPageSeal(Integer pageSeal) {
        this.pageSeal = pageSeal;
    }
}
