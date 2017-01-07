package com.zegome.utils.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class XMLParserHanler extends DefaultHandler {
	
	public interface XMLNodeKeyFactory {
		ArrayList<String> getKeys();
	}
	
	private final ArrayList<XMLNode> mNodeList;
	private XMLNodeKeyFactory mKeyFactory;
	private StringBuilder mBuilder;
	private String mParentNode;
	
	private XMLNode mNode;
	
	public XMLParserHanler(final String parent, final XMLNodeKeyFactory keyFactory) {
		mNodeList = new ArrayList<>();
		mParentNode = parent;
		setKeys(keyFactory);
	}
	
	public ArrayList<XMLNode> getResult() {
		return mNodeList;
	}
	
	public void setKeys(final XMLNodeKeyFactory keyFactory) {
		mKeyFactory = keyFactory;
	}
	
	public void setParentNode(final String parent) {
		mParentNode = parent;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// Create StringBuilder object to store xml node value
		mBuilder = new StringBuilder();

		if (localName.equals(mParentNode)) {
			final ArrayList<String> keys = mKeyFactory.getKeys();
			mNode = new XMLNode(mParentNode, keys);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (localName.equals(mParentNode)) {
			mNodeList.add(mNode);
			mNode = null;
		} else if (mNode != null &&mNode.check(localName)) {
			mNode.setValue(localName, mBuilder.toString());
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String tempString = new String(ch, start, length);
		mBuilder.append(tempString);
	}
}
