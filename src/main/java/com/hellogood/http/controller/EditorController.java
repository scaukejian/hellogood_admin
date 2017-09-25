package com.hellogood.http.controller;

import com.hellogood.exception.BusinessException;
import com.hellogood.utils.StaticFileUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by kejian
 */
@Controller
@RequestMapping("editor")
public class EditorController extends BaseController{

    /**
     * SpringMVC 用的是 的MultipartFile来进行文件上传
     * 这里用@RequestParam()来指定上传文件为MultipartFile
     * @throws IOException
     */
    @RequestMapping("uploadFile.do")
    @ResponseBody
    //这里upfile是config.json中图片提交的表单名称
    public Map<String,String> uploadFile(@RequestParam(value = "upfile", required = false) CommonsMultipartFile
                                                      upfile, HttpServletRequest request) throws IOException {

        if(upfile == null){
            return new HashMap<>();
        }

        //文件原名称
        String fileName = upfile.getOriginalFilename();


        // 生成文件夹路径
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        String folderPath = generateFolderPath(day,"UEditor");
        String folderName = generateFolderName(day,"UEditor");
        // 重命名规则 文件夹名+header+UUID+后缀名
        String newFileName = folderName + "_" + UUID.randomUUID() + fileName.substring(fileName.indexOf("."));

        //上传位置路径
        String filePath = folderPath + "/" + newFileName;

        //按照路径新建文件
        File newFile = new File(filePath);
        //复制
        FileCopyUtils.copy(upfile.getBytes(), newFile);

        //返回结果信息(UEditor需要)
        Map<String, String> map = new HashMap<>();
        //是否上传成功
        map.put("state", "SUCCESS");
        //现在文件名称
        map.put("title", newFileName);
        //文件原名称
        map.put("original", fileName);
        //文件类型 .+后缀名
        map.put("type", fileName.substring(upfile.getOriginalFilename().lastIndexOf(".")));
        //文件路径
        String basePath = StaticFileUtil.getProperty("systemConfig", "IMG_INTERFACE_SERVER");
        map.put("url", basePath + "/" + folderName + "/" + newFileName);

        //本地访问方式
        // map.put("url", request.getContextPath() +  "/editor/download.do?fileName=" + newFileName);

        //文件大小（字节数）
        map.put("size", upfile.getSize() + "");
        return map;
    }

    /**
     * 用户数据下载
     *
     * @param fileName
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/download.do")
    public ResponseEntity<byte[]> download(
            @RequestParam("fileName") String fileName,
            HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        // 文件路径
        String responeName = generateFilePath(fileName);
        byte[] bytes = null;
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        try {
            headers.setContentDispositionFormData("attachment", new String(
                    fileName.getBytes("gb2312"), "ISO8859-1"));
            File file = new File(responeName);
            if (!file.exists()) {
                file.delete();
                throw new BusinessException("下载失败: 文件不存在!");
            }
            bytes = FileUtils.readFileToByteArray(file);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new BusinessException("下载失败...");
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("下载失败...");
        }
        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
    }

    /**
     * 读取文件
     */
//    @RequestMapping("{imgName}/getImage")
//    public void readImg(@PathVariable("imgName") String imgName, HttpServletResponse response)
//            throws Exception {
//        //设置文件的返回类型
//        response.setContentType("image/*");
//        //文件路径(windows下是\\，linux下是//，都必须是绝对路径)
//        String imgPath="D:\\eclipseworkspace\\maven-web\\src\\main\\webapp\\upload\\"+imgName;
//        //java中用File类来表示一个文件
//        File image = new File(imgPath);
//        //测试这个文件路径是否存在（也就是这个文件是否存在）
//
//        if (!image.exists()) {
//
//            return;
//
//        }
//
//        //FileUtils.readFileToByteArray(File file)把一个文件转换成字节数组返回
//
//        response.getOutputStream().write(FileUtils.readFileToByteArray(image));
//
//        //java在使用流时,都会有一个缓冲区,按一种它认为比较高效的方法来发数据:
//
//        //把要发的数据先放到缓冲区,缓冲区放满以后再一次性发过去,而不是分开一次一次地发.
//
//        //而flush()表示强制将缓冲区中的数据发送出去,不必等到缓冲区满.
//
//        response.getOutputStream().flush();
//
//        response.getOutputStream().close();
//
//    }
}
