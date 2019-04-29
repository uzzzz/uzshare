/*
+--------------------------------------------------------------------------
|   
|   ========================================
|    
|   
|
+---------------------------------------------------------------------------
*/
package uzblog.base.upload;

import java.io.IOException;
import java.net.URL;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 *
 */
public interface FileRepo {
	/**
	 * 存储图片
	 * 
	 * @param file
	 * @param basePath
	 * @return
	 * @throws IOException
	 */
	String store(MultipartFile file, String basePath) throws IOException;

	/**
	 * 存储压缩图片
	 * 
	 * @param file
	 * @param basePath
	 * @return
	 * @throws IOException
	 */
	String storeScale(MultipartFile file, String basePath, int maxWidth) throws Exception;

	/**
	 * 存储压缩图片
	 * 
	 * @param file
	 * @param basePath
	 * @return
	 * @throws IOException
	 */
	String storeScale(MultipartFile file, String basePath, int width, int height) throws Exception;

	/**
	 * 获取图片大小
	 *
	 * @param storePath
	 * @return
	 */
	int[] imageSize(String storePath);

	String getRoot();

	/**
	 * 存储路径
	 * 
	 * @param storePath
	 */
	void deleteFile(String storePath);

	/**
	 * 存储网络图片
	 * 
	 * @param fileUrl
	 * @param basePath
	 * @return
	 * @throws IOException
	 */
	String store(URL url, String basePath) throws IOException;

	String storeScale(URL url, String basePath, int maxWidth) throws Exception;
	
	String storeScale(URL url, String basePath, int width, int height) throws Exception;
}
