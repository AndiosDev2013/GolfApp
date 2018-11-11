package com.app.golfapp.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class ResourceUtil {
	/*
	 * Delete File
	 */
	public static boolean DeleteFile(File f) {
		if (f != null && f.exists() && !f.isDirectory()) {
			return f.delete();
		}
		return false;
	}

	public static boolean DeleteFile(String f) {
		if (!TextUtils.isEmpty(f)) {
			return DeleteFile(new File(f));
		}
		return false;
	}

	/**
	 *
	 * @param context
	 * @param fileName
	 * @throws IOException
	 */
	public static String readTextFileFromAsset(Context context, String fileName) {
		BufferedReader reader = null;
		StringBuilder builder = new StringBuilder();
		try {
			reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName), "UTF-8"));

			// do reading, usually loop until end of file reading
			String mLine;
			while ((mLine = reader.readLine()) != null) {
				//process line
				builder.append(mLine);
			}

		} catch (IOException e) {
			//log the exception
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					//log the exception
				}
			}
		}

		return builder.toString();
	}

	public static Bitmap getBitmapFromAsset(Context context, String filePath) {
		AssetManager assetManager = context.getAssets();

		InputStream istr;
		Bitmap bitmap = null;
		try {
			istr = assetManager.open(filePath);
			bitmap = BitmapFactory.decodeStream(istr);
		} catch (IOException e) {
			// handle exception
		}

		return bitmap;
	}
	
	/*
	 * File
	 */
	public static String getLogFile() {
		String tempDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GolfApp/";
		String tempFileName = "log.txt";

		File tempDir = new File(tempDirPath);
		if (!tempDir.exists())
			tempDir.mkdirs();
		File tempFile = new File(tempDirPath + tempFileName);
		if (!tempFile.exists())
			try {
				tempFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		return tempDirPath + tempFileName;
	}
}
