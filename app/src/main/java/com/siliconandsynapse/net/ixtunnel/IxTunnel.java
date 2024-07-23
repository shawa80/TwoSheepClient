package com.siliconandsynapse.net.ixtunnel;

import java.net.*;
import java.io.*;

public class IxTunnel
{
	private Socket connection;
	private DataInputStream in;
	private DataOutputStream out;


	public IxTunnel(Socket connection) throws IOException
	{
		this.connection = connection;

		try {
			connection.setTcpNoDelay(true);
			connection.setPerformancePreferences(0, 1, 0);

			in = new DataInputStream(connection.getInputStream());
			out = new DataOutputStream(connection.getOutputStream());
		} catch (Exception e) {
			throw new IOException();
		}
	}



	protected void sendDocument(KeyDocPair doc)
	{
		String key = "";
		String json = null;

		synchronized (out) {

			try {

				key = doc.getKey().getFullPath();
				json = doc.getDoc();

				System.out.println("Key: " + key);
				System.out.println("Json: " + json);

			} catch (Exception e) {
				e.printStackTrace();

				return;
			}

			try {
				byte keyBuffer[] = new byte[0];
				byte objBuffer[] = new byte[0];

				keyBuffer = key.getBytes("UTF-8");
				objBuffer = json.getBytes("UTF-8");

				out.writeInt(keyBuffer.length);
				out.write(keyBuffer, 0, keyBuffer.length);
				out.writeInt(objBuffer.length);
				out.write(objBuffer, 0, objBuffer.length);
				out.flush();

			} catch (Exception e) {
				e.printStackTrace();
				return;
			}


		}
	}


	public KeyDocPair receiveDocument() throws IOException {

		int keySize = -1;
		byte keyBuffer[] = new byte[0];
		int docSize = -1;
		byte docBuffer[] = new byte[0];

		int readIn = 0;
		synchronized (in) {

			try {
				keySize = in.readInt();
				keyBuffer = new byte[keySize];
				readIn = 0;

				while (readIn < keySize)
					readIn += in.read(keyBuffer, readIn, keySize-readIn);

				docSize = in.readInt();
				docBuffer = new byte[docSize];
				readIn = 0;

				while (readIn < docSize)
					readIn += in.read(docBuffer, readIn, docSize-readIn);

				var key = new String(keyBuffer, "UTF-8");
				var doc = new String(docBuffer, "UTF-8");

				return new KeyDocPair(IxAddress.parse(key), doc);

			} catch (IOException | ParseError e) {
				close();
				throw new IOException("Connection error");
			}
		}

	}

	public void close()
	{
		try {
			connection.close();
		} catch (Exception e) {

		}
	}
}
