package common;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class MyFileRenamePolicy implements FileRenamePolicy {

	@Override
	public File rename(File originFile) {
		long currentTime = System.currentTimeMillis();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		int ranNum = (int) (Math.random() * 100000);

		String name = originFile.getName();
		String ext = null;

		int dot = name.lastIndexOf(".");
		if (dot != -1) {
			ext = name.substring(dot);
			// 확장자를 넣고 있음!
		} else {
			ext = "";
		}
		String fileName = sdf.format(new Date(currentTime)) + ranNum + ext;
		File newFile = new File(originFile.getParent(), fileName);
		return newFile;
	}

}
