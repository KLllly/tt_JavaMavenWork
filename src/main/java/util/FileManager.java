package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author ztang generated at : Jul 1, 2016 - 8:58:33 AM
 */
public final class FileManager {

	public static final File UserHomeFold = new File(System.getProperty("user.home"));
	public static final File UserFold = new File(System.getProperty("user.dir"));

	// where you launch the main method
	private static final String rootFold = System.getProperty("user.dir");

	public static final String fileSeparator = System.getProperty("file.separator");
	public static final String newline = System.getProperty("line.separator", "\n");

	public static File getSampleFile(String filename) {
		return new File(getSampleDataFold(), filename);
	}

	public static File getSampleFile(String subFoldname, String filename) {
		File subFolder = getSampleDataFold(subFoldname);
		return new File(subFolder, filename);
	}

	public static File getSampleDataFold() {
		File rootDir = new File(rootFold + fileSeparator + "zSampleData");
		if (!rootDir.exists())
			rootDir.mkdirs();
		return rootDir;
	}

	public static File getSampleDataFold(String subFoldname) {
		File rootDir = getSampleDataFold();
		File newDir = new File(rootDir, subFoldname);
		if (!newDir.exists())
			newDir.mkdirs();
		return newDir;
	}

	public static File getTempFold() {
		File dir = new File(rootFold + fileSeparator + "zKillableFiles");
		if (!dir.exists())
			dir.mkdirs();
		return dir;
	}

	public static File getTempFold(String subFoldname) {
		File dir = new File(getTempFold(), subFoldname);
		if (!dir.exists())
			dir.mkdirs();
		return dir;
	}

	public static File getTempFile(String filename) {
		return new File(getTempFold(), filename);
	}


	public static void cloneFile(File srcFile, File dstFile) {
		try {
			cloneFile(new FileInputStream(srcFile), new FileOutputStream(dstFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void cloneFile(FileInputStream srcStream, FileOutputStream dstStream) {
		try {
			// Create channel on the source
			FileChannel srcChannel = srcStream.getChannel();
			// Create channel on the destination
			FileChannel dstChannel = dstStream.getChannel();
			// Copy file contents from source to destination
			dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
			// Close the channels
			srcChannel.close();
			dstChannel.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static String loadText(File file) throws IOException {
		RandomAccessFile random = new RandomAccessFile(file, "r");
		byte ab[] = new byte[(int) random.length()];
		random.seek(0L);
		random.readFully(ab);
		random.close();
		return new String(ab);
	}

	public static void saveText(File file, String sText) throws IOException {
		RandomAccessFile random = new RandomAccessFile(file, "rw");
		random.seek(0L);
		byte ab[] = sText.getBytes();
		random.write(ab);
		random.close();
	}

	public static File saveDocument(File outFile, byte[] context) throws Exception {
		if (context == null) {
			throw new Exception("File body is null");
		}
		if (outFile == null) {
			throw new Exception("File is null");
		}

		RandomAccessFile raf = new RandomAccessFile(outFile, "rw");
		FileChannel outChannel = raf.getChannel();
		ByteBuffer byteBuf = ByteBuffer.wrap(context);
		outChannel.write(byteBuf);
		outChannel.close();
		raf.close();
		return outFile;
	}

}
