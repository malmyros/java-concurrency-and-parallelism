package com.example;

import javax.naming.Context;
import java.util.ArrayList;
import java.util.List;

interface RequestHandler<T, K> {

    K handleRequest(T s3Event, Context context);
}

class S3Bucket {

    private final String name;

    S3Bucket(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class S3Object {

    private final String key;

    public S3Object(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}

class S3 {

    private final S3Bucket bucket;

    private final S3Object object;

    public S3(S3Bucket bucket, S3Object object) {
        this.bucket = bucket;
        this.object = object;
    }

    public S3Bucket getBucket() {
        return bucket;
    }

    public S3Object getObject() {
        return object;
    }
}

class S3Record {

    private final S3 s3;

    public S3Record(S3 s3) {
        this.s3 = s3;
    }

    public S3 getS3() {
        return s3;
    }
}

class S3Event {

    public List<S3Record> getRecords() {
        return new ArrayList<>();
    }
}

public class S3ObjectProcessor implements RequestHandler<S3Event, String> {

    @Override
    public String handleRequest(S3Event s3Event, Context context) {

        for (S3Record s3Record : s3Event.getRecords()) {
            String bucketName = s3Record.getS3().getBucket().getName();
            String objectKey = s3Record.getS3().getObject().getKey();
            // ...process uploaded object
        }

        return "Processing complete";
    }
}
