package com.se.sync;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.DownloadBuilder;
import com.dropbox.core.v2.files.ListFolderErrorException;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;

public class CloudAccess {

	public void checkin(String filepath) {
		DbxRequestConfig config = new DbxRequestConfig(Constants.APPNAME);
		DbxClientV2 client = new DbxClientV2(config, Constants.TOKEN);

		try {

			File src = new File(filepath);
			File dest = new File("tmp.txt");
			if (dest.exists())
				dest.delete();

			FileUtils.copyFile(src, dest);
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(dest)));
			File dest2 = new File(filepath.substring(filepath.lastIndexOf("/"), filepath.length()));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dest2)));
			String line = "";
			while ((line = br.readLine()) != null) {
				bw.write(line);
				bw.newLine();
			}
			br.close();
			bw.close();

			InputStream is = new FileInputStream(dest2);

			byte[] byteArr = IOUtils.toByteArray(is);
			InputStream inputStream = new ByteArrayInputStream(byteArr);

			// try to create folder, if folder already exists it throws error,
			// its fine.

			try (InputStream in = inputStream) {
				String filename = dest2.getName();
				try {
					client.files().delete("/" + filename);
				} catch (Exception e) {
					// IGNORE
				}
				client.files().uploadBuilder("/" + filename).uploadAndFinish(in);
			}
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void delete(String filepath) {
		DbxRequestConfig config = new DbxRequestConfig(Constants.APPNAME);
		DbxClientV2 client = new DbxClientV2(config, Constants.TOKEN);

		try {
			String f = filepath.substring(filepath.lastIndexOf("/") + 1, filepath.length());
			client.files().delete("/" + f);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void checkout(String path) {
		DbxRequestConfig config = new DbxRequestConfig(Constants.APPNAME);
		DbxClientV2 client = new DbxClientV2(config, Constants.TOKEN);

		System.out.println("Starting to download all files into "+path);
		try {
			ListFolderResult fileList = client.files().listFolder("");

			List<Metadata> entries = fileList.getEntries();

			for (Metadata metadata : entries) {

				if (metadata.getName().contains(".txt")) {
					System.out.println("processing "+metadata.getName());

					File f = new File(path + "/" + metadata.getName());
					DownloadBuilder builder = client.files().downloadBuilder(metadata.getPathDisplay());
					OutputStream out = new FileOutputStream(f);
					builder.download(out);					
					out.close();
					
					/*
					File enc_file = new File(path + "/" + metadata.getName());
					BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(enc_file)));
					BufferedWriter bw = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream(new File(path + "/" + metadata.getName()))));
					String line = "";
					while ((line = br.readLine()) != null) {
						System.out.println("line .. "+line);
						bw.write(line);
						bw.newLine();
					}
					br.close();
					bw.close();

					//enc_file.delete();
					*/
				}
			}
			System.out.println("All files downloaded successfully");
		} catch (ListFolderErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
