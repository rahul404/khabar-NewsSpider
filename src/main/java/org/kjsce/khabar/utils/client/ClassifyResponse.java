package org.kjsce.khabar.utils.client;

import org.springframework.stereotype.Component;

public class ClassifyResponse {
    private String intent;
    private int code;

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
