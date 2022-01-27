package com.glanner.api.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardDownloadReqDto {
    private String savedFolder;
    private String savedFile;
    private String originalFile;
}
