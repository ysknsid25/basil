package jp.co.basil.app.fileManager;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * @author NOMOS) Yoshida
 * ファイルダウンロード機能を提供します。
 *
 */
public class FileDownloader {

	@Autowired
    private ResourceLoader resourceLoader;


	/**
	 * コンストラクタ
	 */
	protected FileDownloader() {
	}


	/**
	 * ファイルダウンロード処理を行う入り口
	 * @param response
	 * @param absolutePath
	 * @param fileName
	 */
	protected void download(HttpServletResponse response, String absolutePath, String fileName) {

		Resource resource = resourceLoader.getResource("file:" + absolutePath);

        byte[] fileContent = null;
        fileContent = this.streamToByte(resource);

      //ファイル書き込み
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setContentLength(fileContent.length);

	    this.outputSreamWrite(response, fileContent);

	}


    /**
     * InputStream から　バイト文字列に変換
     * @param filepath
     * @return
     */
    private byte[] streamToByte(Resource resource) {

	    int nRead;
	    InputStream is = null;
	    byte[] fileContent = new byte[16384];
	    ByteArrayOutputStream buffer = new ByteArrayOutputStream();

	    //ファイルをバイト形式に変換
	    try {

	        is = new FileInputStream(resource.getFile().toString());

	        while ((nRead = is.read(fileContent, 0, fileContent.length)) != -1) {

	              buffer.write(fileContent, 0, nRead);

	        }

	        buffer.flush();
	        is.close();
	        return buffer.toByteArray();

	    } catch (FileNotFoundException e) {

	        e.getStackTrace();

	    } catch (IOException e) {

	        e.printStackTrace();

	    }

    	return null;

    }


    /**
     * ダウンロードファイル書き込み
     * @param response
     * @param fileContent
     */
    private void outputSreamWrite(HttpServletResponse response, byte[] fileContent) {

        OutputStream os = null;

        try {

            os = response.getOutputStream();
            os.write(fileContent);
            os.flush();

        } catch (IOException e) {

            e.getStackTrace();

        }

    }


}
