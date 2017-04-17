/**
 * 
 */
package test1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

/**
 * @author oa_wenzongyuan
 */

public class UploadServlet extends HttpServlet {

    /**
     */
    private static final long serialVersionUID = 1051862915197349031L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 编码格式
        resp.setContentType("text/html;charset=utf-8");

        // 获取项目部署到tomacat后的绝对磁盘路径的temp路径
        String realPath = this.getServletContext().getRealPath("/temp");
        // 用此路径创建文件
        File file = new File(realPath);
        // 创建文件磁盘工厂
        DiskFileItemFactory factory = new DiskFileItemFactory(1024 * 100, file);

        // 创建ServletFileUpload(使用磁盘文件工厂)
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 判断是否是上传文件操作
        Boolean flag = upload.isMultipartContent(req);
        // 如果是文件上传操作
        if (flag) {
            // 解决文件上传名称中文乱码问题
            upload.setHeaderEncoding("utf-8");
            // 设置文件上传大小100m
            upload.setSizeMax(1024 * 1024 * 100);
            try {
                // 解析request，得到所有的上传项FileItem
                List<FileItem> items = upload.parseRequest(req);
                // 得到所有的上传项FileItem
                for (FileItem item : items) {
                    // 如果item不是表单数据
                    if (!item.isFormField()) {
                        // 获取文件上传名称
                        String name = item.getName();
                        // 获取上传文件的真实名称
                        String fileName = UploadUtils.getFileRealName(name);
                        // 得到文件随机名称
                        String uuidName = UploadUtils.getUUIDFileName(fileName);
                        // 得到随机目录
                        String randomDir = UploadUtils.getRandomDirectory(fileName);
                        // 随机目录可能是不存在的，需要创建
                        // 先创建一个父级目录
                        String parentDir = this.getServletContext().getRealPath("/upload");
                        // 然后用此父级目录和随机目录创建文件夹
                        File rd = new File(parentDir, randomDir);
                        // 如果此文件夹不存在，就新创建一个
                        if (!rd.exists()) {
                            rd.mkdirs();
                        }
                        // 使用apche的IOUtils写出文件
                        IOUtils.copy(item.getInputStream(), new FileOutputStream(new File(rd, uuidName)));
                        // 获取上传后的文件路径
                        String uploadFilePath = parentDir + randomDir + "/" + uuidName;
                        // 删除临时文件
                        item.delete();
                    }
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
