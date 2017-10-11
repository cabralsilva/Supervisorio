package WebService.extensao.impl;

import java.io.*;

public class Cache {
	private static Cache instance = new Cache();

	public static Cache getInstance() {
		return instance;
	}

	private byte[] image;

	private Cache() {
	}

	public void put(byte[] image) {
		this.image = image;
	}

	public byte[] get() {
		return image;
	}	
}