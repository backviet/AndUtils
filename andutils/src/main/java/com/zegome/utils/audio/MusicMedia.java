package com.zegome.utils.audio;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * (C) 2013 ZeroTeam
 * @author QuanLT
 */

public class MusicMedia {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	public MediaPlayer mMediaPlayer;
	public String mName;
	
	// ===========================================================
	// Constructor
	// ===========================================================
	public MusicMedia(final String name, final Context context, final int id) {
		mName = name;
		mMediaPlayer = MediaPlayer.create(context, id);
	}
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public void setVolume(final float left, final float right) {
		mMediaPlayer.setVolume(left, right);
	}
	
	public void setLooping(final boolean isLooping) {
		mMediaPlayer.setLooping(isLooping);
	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	public void pause() {
		if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
			mMediaPlayer.pause();
		}
	}
	
	public boolean isPlaying() {
		return mMediaPlayer != null && mMediaPlayer.isPlaying();
	}
	
	public void play() {
		if (mMediaPlayer != null) {
			mMediaPlayer.start();
		}
	}
	
	public void dispose() {
		if (mMediaPlayer != null) {
			if (mMediaPlayer.isPlaying()) {
				mMediaPlayer.pause();
			}
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
