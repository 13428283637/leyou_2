package com.leyou.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ExceptionEnum {

    CATEGORY_BOT_FOUND(404, "分类未找到"),
    BRAND_NOT_FOUND(404, "品牌未找到"),
    BRAND_CREATE_FAILED(404, "新增品牌失败"),
    UPLOAD_FILE_ERROR(500, "文件上传失败"),
    INVALID_FILE_TYPE(500, "非法文件类型"),
    UPLOAD_IMAGE_EXCEPTION(500, "文件上传异常"),
    ;
    int value;
    String message;
    public int value() {
        return this.value;
    }

    public String message() {
        return this.message;
    }
}
