package com.se.dropboxaccess;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.DownloadBuilder;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderErrorException;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.sharing.PathLinkMetadata;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;

public class DropboxAccess {

	public String attachFile() {

		try {
			System.out.println("Inserting into Dropbox account:");
			// get the dropbox request confog
			DbxRequestConfig config = new DbxRequestConfig("Cit_SE");
			DbxClientV2 client = new DbxClientV2(config,
					"wIykpdJRXEAAAAAAAAAAEcQLBsGws6MTsElEM2mtw_aLdbuJz_0yFQXNYYKLamqV");

			File file = new File("hello.txt");
			InputStream is = new FileInputStream(file);

			byte[] byteArr = IOUtils.toByteArray(is);
			InputStream inputStream = new ByteArrayInputStream(byteArr);

			// try to create folder, if folder already exists it throws error,
			// its fine.

			try (InputStream in = inputStream) {
				String filename = file.getName();
				FileMetadata metadata = client.files().uploadBuilder("/" + filename).uploadAndFinish(in);

				// PathLinkMetadata r =
				// client.sharing().createSharedLink(metadata.getPathDisplay());
				SharedLinkMetadata r = client.sharing().createSharedLinkWithSettings(metadata.getPathDisplay());
				String fileUrl = r.getUrl();

				return fileUrl;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void listFilesDropbox() {
		System.out.println("Reading From Dropbox account:");
		// get the dropbox request confog
		DbxRequestConfig config = new DbxRequestConfig("Cit_SE");
		DbxClientV2 client = new DbxClientV2(config,
				"wIykpdJRXEAAAAAAAAAAEcQLBsGws6MTsElEM2mtw_aLdbuJz_0yFQXNYYKLamqV");

		try {
			ListFolderResult fileList = client.files().listFolder("");

			List<Metadata> entries = fileList.getEntries();

			for (Metadata metadata : entries) {

				System.out.println("File Path and Name is: " + metadata.getPathDisplay());
				PathLinkMetadata link = client.sharing().createSharedLink(metadata.getPathDisplay());
				System.out.println(link.getUrl());

				
				if (metadata.getName().contains(".txt")) {
					File f = new File("h.txt");
					DownloadBuilder builder = client.files().downloadBuilder(metadata.getPathDisplay());
					OutputStream out = new FileOutputStream(f);
					builder.download(out);
				}

			}
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

	public static void main(String[] args) {
		DropboxAccess da = new DropboxAccess();

		// String fileUrl = da.attachFile();
		// System.out.println(fileUrl);
		da.listFilesDropbox();

	}

}
