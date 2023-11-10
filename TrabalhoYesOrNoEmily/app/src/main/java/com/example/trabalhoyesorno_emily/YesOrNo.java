package com.example.trabalhoyesorno_emily;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YesOrNo {

    @SerializedName("answer")
    @Expose
    private String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "YesOrNo{" +
                "answer='" + answer + '\'' +
                '}';
    }
}
