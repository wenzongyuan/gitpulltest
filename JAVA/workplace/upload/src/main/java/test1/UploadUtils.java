package test1;

import java.util.UUID;

/**
 * 文件上传工具类
 * 
 * @author Wenzongyuan
 */
public class UploadUtils {
    // 得到文件上传的真实名称
    public static String getFileRealName(String FileName) {
        int index = FileName.lastIndexOf("\\") + 1;
        return FileName.substring(index);
    }

    // 随机获取文件名称（避免名称重复）
    public static String getUUIDFileName(String FileName) {
        int index = FileName.lastIndexOf(".");
        if (index != -1) {
            return UUID.randomUUID() + FileName.substring(index);
        } else {
            return UUID.randomUUID().toString();
        }
    }

    // 目录分离算法（避免同一个文件夹过大问题）
    public static String getRandomDirectory(String FileName) {
        // 将文件名称通过hash算法
        int hashCode = FileName.hashCode();
        //
        int a = hashCode & 0xf;
        //
        hashCode = hashCode >>> 4;
        //
        int b = hashCode & 0xf;
        // 返回目录
        return "/" + a + "/" + b;

    }
}
