package cn.simon.controller;


import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Objects;

@Controller
@RequestMapping("/file")
public class FileController {

    @RequestMapping("/upload")
    public String upload(MultipartFile file, HttpServletRequest request) throws IOException {
        System.out.println(file.getBytes().length);
        System.out.println(file.getContentType());
        String realPath = request.getSession().getServletContext().getRealPath("/upload");
        file.transferTo(new File(realPath, Objects.requireNonNull(file.getOriginalFilename())));
        System.out.println("success");
        return "index";
    }

    @RequestMapping("download")
    public void download(String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("file name is: "+fileName);


        String realPath = request.getSession().getServletContext().getRealPath("/download");

        //通过文件输入流加载文件
        FileInputStream is = new FileInputStream(new File(realPath, fileName));
        //设置响应类型
        response.setContentType("text/plain;charset=UTF-8");
        //获取响应输出流
        ServletOutputStream os = response.getOutputStream();
        //附件下载
        response.setHeader("content-disposition","attachment;fileName="+ URLEncoder.encode(fileName,"UTF-8"));
//        int len;
//        //处理下载流
//        byte[] b = new byte[1024];
//
//        while (true){
//            len = is.read(b);
//            if (len == -1)break;
//            os.write(b,0,len);
//        }
//
//        os.close();
//        is.close();

        IOUtils.copy(is,os);
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(os);
    }
}
