package com.mycompany.lawfirm.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.lawfirm.model.CaseFile;
import okhttp3.*;
import java.io.*;
import java.util.List;

public class CaseFileService {
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String BASE_URL = "http://localhost:8080/cases";

    public List<CaseFile> getFilesByCase(Long caseId) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/" + caseId + "/files")
                .build();
        try (Response response = client.newCall(request).execute()) {
            String json = response.body().string();
            return mapper.readValue(json, new TypeReference<>() {});
        }
    }

    public void uploadFile(Long caseId, File file) throws IOException {
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(file, MediaType.parse("application/octet-stream")))
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL + "/" + caseId + "/files/upload")
                .post(body)
                .build();

        client.newCall(request).execute().close();
    }

    public void downloadFile(Long fileId, File destination) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/dummy/files/" + fileId + "/download") // hoặc chỉnh lại path tùy BE
                .build();

        try (Response response = client.newCall(request).execute();
             InputStream in = response.body().byteStream();
             FileOutputStream out = new FileOutputStream(destination)) {

            in.transferTo(out);
        }
    }

    public void deleteFile(Long fileId) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/dummy/files/" + fileId)
                .delete()
                .build();
        client.newCall(request).execute().close();
    }
}
