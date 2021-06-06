package com.demo.appfortinkoff;

import androidx.annotation.Nullable;

public class GifToBeShown {
    private String description;
    private String url;

    public GifToBeShown(String description, String url) {
        this.description = description;
        this.url = url;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;

        GifToBeShown gif = (GifToBeShown) obj;
            return this.url.equals(gif.url);
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }
}
