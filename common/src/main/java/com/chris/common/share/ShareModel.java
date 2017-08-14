package com.chris.common.share;

import com.umeng.socialize.media.UMEmoji;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMusic;

/**
 * Created by zhangxiaolong on 2016/4/14.
 */
public class ShareModel {
    private String title;
    private boolean isInvite;
    private String content;
    private String shareUrl;
    private String shortUrl;
    private UMEmoji emojiMedia;
    private UMImage imageMedia;
    private UMusic musicMedia;
    private UMVideo videoMedia;

    private boolean isLogin;
    private boolean isSecondLogin;

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public boolean isInvite() {
        return isInvite;
    }

    public void setInvite(boolean invite) {
        isInvite = invite;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UMEmoji getEmojiMedia() {
        return emojiMedia;
    }

    public void setEmojiMedia(UMEmoji emojiMedia) {
        this.emojiMedia = emojiMedia;
    }

    public UMImage getImageMedia() {
        return imageMedia;
    }

    public void setImageMedia(UMImage imageMedia) {
        this.imageMedia = imageMedia;
    }

    public UMusic getMusicMedia() {
        return musicMedia;
    }

    public void setMusicMedia(UMusic musicMedia) {
        this.musicMedia = musicMedia;
    }

    public UMVideo getVideoMedia() {
        return videoMedia;
    }

    public void setVideoMedia(UMVideo videoMedia) {
        this.videoMedia = videoMedia;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public boolean isSecondLogin() {
        return isSecondLogin;
    }

    public void setSecondLogin(boolean secondLogin) {
        isSecondLogin = secondLogin;
    }
}
