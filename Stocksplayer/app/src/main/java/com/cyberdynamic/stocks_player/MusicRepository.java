package com.cyberdynamic.stocks_player;

import android.net.Uri;

import com.cyberdynamic.stocks_player.R;

//https://simpleguics2pygame.readthedocs.io/en/latest/_static/links/snd_links.html
final class MusicRepository {

    private final Track[] data = {
            new Track("OIL Price", "Loading", R.drawable.image266680,                   Uri.parse("https://cyberdynamic.eu/blank.mp3"), (60) * 1000),
    };

    private final int maxIndex = data.length - 1;
    private int currentItemIndex = 0;

    Track getNext() {
        if (currentItemIndex == maxIndex)
            currentItemIndex = 0;
        else
            currentItemIndex++;
        return getCurrent();
    }

    Track getPrevious() {
        if (currentItemIndex == 0)
            currentItemIndex = maxIndex;
        else
            currentItemIndex--;
        return getCurrent();
    }

    Track getCurrent() {
        return data[currentItemIndex];
    }

    static class Track {

        private String title;
        private String artist;
        private int bitmapResId;
        private Uri uri;
        private long duration; // in ms

        Track(String title, String artist, int bitmapResId, Uri uri, long duration) {
            this.title = title;
            this.artist = artist;
            this.bitmapResId = bitmapResId;
            this.uri = uri;
            this.duration = duration;
        }

        String getTitle() {
            return title;
        }

        String getArtist() {
            return artist;
        }

        int getBitmapResId() {
            return bitmapResId;
        }

        Uri getUri() {
            return uri;
        }

        long getDuration() {
            return duration;
        }
    }
}
