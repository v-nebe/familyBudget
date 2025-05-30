package org.shavneva.familybudget.reports;

import org.springframework.http.MediaType;

public class ReportWrapper {

    public final byte[] data;
    public final String fileExtension;
    public final MediaType mediaType;

    public ReportWrapper(byte[] data, String fileExtension, MediaType mediaType) {
        this.data = data;
        this.fileExtension = fileExtension;
        this.mediaType = mediaType;
    }
}