package com.zegome.utils.xml;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.zegome.utils.xml.XMLParserHanler.XMLNodeKeyFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * (C) 2013 ZeroTeam
 * @author QuanLT
 */

public class XMLReaderUtil {

	private static String readTextFile(final Context context, final String fileName) {
		final AssetManager assetManager = context.getAssets();
		InputStream inputStream = null;
		try {
			inputStream = assetManager.open(fileName);
		} catch (IOException e) {
			Log.e("tag", e.getMessage());
		}
		
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		byte buf[] = new byte[1024];
		int len;
		try {
			while ((len = inputStream.read(buf)) != -1) {
				outputStream.write(buf, 0, len);
			}
			outputStream.close();
			inputStream.close();
		} catch (IOException e) {

		}
		return outputStream.toString();
	}
	
	public static ArrayList<XMLNode> read(final Context context, final String fileName, final String parent, String...keys) {
		final ArrayList<String> keyForRead = new ArrayList<String>();
		for (int i = 0; i < keys.length; i++) {
			keyForRead.add(keys[i]);
		}
		return read(context, fileName, keyForRead, parent);
	}
	
	public static ArrayList<XMLNode> read(final Context context, final String fileName, final ArrayList<String> keys, final String parent) {
		try {
			final String output = readTextFile(context, fileName);

			/************** Read XML *************/

			final BufferedReader br = new BufferedReader(new StringReader(output));
			final InputSource is = new InputSource(br);

			/************ Parse XML **************/
			XMLParserHanler handler = new XMLParserHanler(parent, new XMLNodeKeyFactory() {
				
				@Override
				public ArrayList<String> getKeys() {
					return keys;
				}
			});
			
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser sp = factory.newSAXParser();
			XMLReader reader = sp.getXMLReader();
			reader.setContentHandler(handler);
			reader.parse(is);
			
			return handler.getResult();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
