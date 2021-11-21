package com.example.dog.diary;

import android.net.Uri;

public class DiaryItem {

    String diaryContent;
    String diaryImage;

    public DiaryItem(String diaryContent, String diaryImage) {
        this.diaryContent = diaryContent;
        this.diaryImage = diaryImage;
    }

    public String getDiaryContent() {
        return diaryContent;
    }

    public void setDiaryContent(String diaryContent) {
        this.diaryContent = diaryContent;
    }

    public String getDiaryImage() {
        return diaryImage;
    }

    public void setDiaryImage(String diaryImage) {
        this.diaryImage = diaryImage;
    }
}
