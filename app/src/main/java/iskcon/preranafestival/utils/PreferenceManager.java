/*
 * Copyright © 2016, Craftsvilla.com
 *  Written under contract by Robosoft Technologies Pvt. Ltd.
 */

package iskcon.preranafestival.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Sibaprasad
 */
public class PreferenceManager {

	private static final String FILE_NAME = "pref";
	private static PreferenceManager        sInstance;
	private        SharedPreferences        mPrefs;
	private        SharedPreferences.Editor mEditor;

	private PreferenceManager( Context ctx ) {
		mPrefs = ctx.getApplicationContext().getSharedPreferences( ctx.getPackageName(), Context.MODE_PRIVATE );
		mEditor = mPrefs.edit();
	}

	public static PreferenceManager getInstance( Context ctx ) {
		if ( sInstance == null ) {
			sInstance = new PreferenceManager( ctx );
		}
		return sInstance;
	}

	public static void storePrefBoolean( Context context, String key, boolean value ) {
		SharedPreferences        sp     = context.getSharedPreferences( FILE_NAME, context.MODE_PRIVATE );
		SharedPreferences.Editor editor = sp.edit();
		editor.putBoolean( key, value );
		editor.commit();
	}

	public static boolean getPrefBoolean( Context context, String key ) {
		SharedPreferences sp = context.getSharedPreferences( FILE_NAME, context.MODE_PRIVATE );
		return sp.getBoolean( key, false );
	}

	public static int getIntPref( Context context, String key ) {
		SharedPreferences sp = context.getSharedPreferences( FILE_NAME, context.MODE_PRIVATE );
		return sp.getInt( key, 0 );
	}

	public static void storePrefInt( Context context, String key, int value ) {
		SharedPreferences        sp     = context.getSharedPreferences( FILE_NAME, context.MODE_PRIVATE );
		SharedPreferences.Editor editor = sp.edit();
		editor.putInt( key, value );
		editor.commit();
	}

	public static String getPrefString( Context context, String key ) {
		SharedPreferences sp = context.getSharedPreferences( FILE_NAME, context.MODE_PRIVATE );
		return sp.getString( key, "" );
	}

	public static void storePrefString( Context context, String key, String value ) {
		SharedPreferences        sp     = context.getSharedPreferences( FILE_NAME, context.MODE_PRIVATE );
		SharedPreferences.Editor editor = sp.edit();
		editor.putString( key, value );
		editor.commit();
	}

	public String getDate() {
		return mPrefs.getString( Keys.DATE, "" );
	}

	public void setDate( String date ) {
		mEditor.putString( Keys.DATE, date ).commit();
	}

	public String getTitle() {
		return mPrefs.getString( Keys.TITLE, "" );
	}

	public void setTitle( String title ) {
		mEditor.putString( Keys.TITLE, title ).commit();
	}


	public String getSpeaker() {
		return mPrefs.getString( Keys.SPEAKER, "" );
	}

	public void setSpeaker( String speaker ) {
		mEditor.putString( Keys.SPEAKER, speaker ).commit();
	}


	public String getLink() {
		return mPrefs.getString( Keys.YOUTUBE_LINK, "" );
	}

	public void setLink( String link ) {
		mEditor.putString( Keys.YOUTUBE_LINK, link ).commit();
	}

	public String getBanner() {
		return mPrefs.getString( Keys.BANNER, "" );
	}

	public void setBanner( String link ) {
		mEditor.putString( Keys.BANNER, link ).commit();
	}

	public interface Keys {

		static final String DATE         = "date";
		static final String TITLE        = "title";
		static final String SPEAKER      = "speaker";
		static final String YOUTUBE_LINK = "link";
		static final String BANNER       = "banner";
	}
}
