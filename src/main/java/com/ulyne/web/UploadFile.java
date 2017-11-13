package com.ulyne.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * 自己写的一个文件上传的方法...但是目前还是有点小问题. .. 还在找出问题的关键所在... 请勿用在实际项目中
 * Created by fanwei_last on 2017/10/27.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@EnableAutoConfiguration
public class UploadFile {

    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> hello(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> result = new HashMap<>();
        response.addHeader("Access-Control-Allow-Origin","*");
        response.addHeader("Access-Control-Allow-Headers","X-Requested-With");
        response.addHeader("Access-Control-Allow-Methods","GET,POST,OPTIONS,DELETE");
        response.addHeader("Access-Control-Allow-Headers","test");

        String path = request.getSession().getServletContext().getRealPath("/");
        InputStream in = request.getInputStream();
        //文件id
        String id = request.getParameter("id");
        //文件所在第几个位置
        String location = request.getParameter("location");
        //文件分块数量
        String size = request.getParameter("size");
        //最后一块文件的标记符
        String endLocation = request.getParameter("endLocation");
        // 这里是为了获取文件扩展名
        String lastSuffix = request.getParameter("lastSuffix");
        String[] lastTemp = lastSuffix.split("\\/");
        String extention = lastTemp[lastTemp.length-1];

        OutputStream out = new FileOutputStream(path + "\\" + id + location + ".tmp");
        int len = -1;
        byte[] buffer = new byte[1024];

        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }

        in.close();
        out.close();
        /**
         * 如果收到一个文件的结束标记符,则开始组装该文件
         */
        int sizeIn = Integer.valueOf(size);
        assembleFile(id, path, extention, sizeIn);

        /*String path_init = request.getSession().getServletContext().getRealPath("/");
        File file_init=new File(path_init);

        String str = UUID.randomUUID().toString();
        String path  =file_init.getParent()+"\\upload\\";
        System.out.println("文件上传路径：" + path);
        String fileName =file.getOriginalFilename();
        String fileNameTar =  str+fileName;
        String fileUrlMd5 = "123";
        File targetFile = new File(path, fileUrlMd5);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        //保存文件
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        InetAddress inetAddress = InetAddress.getLocalHost();
        String localCanonicalIp =inetAddress.getHostAddress();
        int localPort = request.getLocalPort();

        String fileUrl = "http://"+localCanonicalIp+":"+localPort+"/upload/"+fileUrlMd5;

        result.put("fileUrl", fileUrl);
        result.put("fileTargetUrl", "/upload/"+fileUrlMd5);
        result.put("fileName", fileName);
        result.put("success", true);*/

        return result;
    }

    @RequestMapping(value = "/index")
    @ResponseBody
    public String index(HttpServletRequest request){

        return "hello world this is boot!";
    }


    public static void main(String[] args){
        SpringApplication.run(UploadFile.class, args);
    }

    /**
     * 组装文件, 需要文件的id, 文件夹路径, 后缀
     * @param id
     */
    public void assembleFile(String id, String path, String  extention, int size){
        File file = new File(path);
        String[] filelist = file.list();
        List<File> files = new ArrayList<>();
        for (int i = 0; i < filelist.length; i++) {
            File readfile = new File(path + "\\" + filelist[i]);
            String name = readfile.getName();
            if (name.contains(id)){
                files.add(readfile);
            }
        }
        if (files.size() == size){
            //文件排序过滤器, 按文件名进行排序 a-z
            Comparator<File> fileComparator = new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    /*Comparator cmp = Collator.getInstance(java.util.Locale.CHINA);
                    return cmp.compare(o1.getName() , o2.getName());*/
                    String[] s1 = o1.getName().split("\\/");
                    String[] s2 = o2.getName().split("\\/");
                    String name1 = s1[s1.length - 1].substring(36, s1[s1.length - 1].length() - 4);
                    String name2 = s2[s2.length - 1].substring(36, s2[s2.length - 1].length() - 4);
                    if (Integer.parseInt(name1) > Integer.parseInt(name2)){
                        return 1;
                    }else if (Integer.parseInt(name1) == Integer.parseInt(name2)){
                        return 0;
                    }else {
                        return -1;
                    }
                }
            };
            FileInputStream fileInputStream = null;
            BufferedInputStream bufferedInputStream = null;
            BufferedOutputStream bufferedOutputStream = null;
            Collections.sort(files, fileComparator);
            OutputStream out = null;
            try {
                out = new FileOutputStream(path + "\\" + id + "." + extention);
                bufferedOutputStream = new BufferedOutputStream(out);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            /**
             * 真正的组装这里才开始
             */
            for (File tmp : files){
                try {
                    fileInputStream = new FileInputStream(tmp);
                    bufferedInputStream = new BufferedInputStream(fileInputStream);
                    int len = -1;
                    byte[] buffer = new byte[1024];
                    while ((len = bufferedInputStream.read(buffer)) != -1) {
                        bufferedOutputStream.write(buffer, 0, len);
                        bufferedOutputStream.flush();
                    }
                    bufferedInputStream.close();
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                //别忘了关闭各种流
                bufferedOutputStream.close();
                out.close();
                 for (int i = 0; i < files.size(); i++) {
                    File readfile = files.get(i);
                    readfile.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

