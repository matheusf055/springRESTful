package com.api.springrest.vo;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class UploadFileResponseVO {

    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private Long size;
}
