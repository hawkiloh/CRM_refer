package tk.mybatis.springboot.modules.sys.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.springboot.modules.sys.model.UploadFile;
import tk.mybatis.springboot.modules.sys.service.FileService;
import tk.mybatis.springboot.common.util.DateUtil;
import tk.mybatis.springboot.common.util.JsonResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(value = "/file")
public class FileController {

    @Autowired
    private FileService fileService;


    /**
     * 文件上传功能
     *
     * @param
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    //@ResponseBody
    public String upload(MultipartFile file, HttpServletRequest request) throws IOException {
        //String path = request.getSession().getServletContext().getRealPath("upload");
       /* UserVo userVo=(UserVo) request.getSession().getAttribute("user");
        String path="D:/data";
        String fileName = file.getOriginalFilename();
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdirs();
        }
        //MultipartFile自带的解析方法
        file.transferTo(dir);
        if(dir.exists()){

        }else{
            fileService.save(file,userVo,"发送失败");
        }*/
        fileService.uploadFile(file, request);

        return "/file/manager";
    }


    @RequestMapping(value = "/findAll")
    @ResponseBody
    public Map<String, Object> findAll(UploadFile uploadFile, Integer pageIndex, Integer pageSize) {

        uploadFile.setPage(pageIndex + 1);
        uploadFile.setRows(pageSize);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        PageInfo<UploadFile> sysLogPageInfo = fileService.findAll(uploadFile);
        resultMap.put("data", sysLogPageInfo.getList());
        resultMap.put("total", sysLogPageInfo.getTotal());
        return resultMap;
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public JsonResult<Integer> delete(String id) {
        int i = 0;
        try {
            i = fileService.delete(id);
        } catch (Exception e) {
            return new JsonResult<Integer>(e);
        }
        return new JsonResult<Integer>(i);
    }

    /**
     * 文件下载功能
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/down")
    public void down(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //JsonResult<String> jsonResult=new JsonResult<String>();
        String id = request.getParameter("id");
        UploadFile uploadFile = fileService.findById(id);
        if (uploadFile != null && "发送失败".equalsIgnoreCase(uploadFile.getFileState())) {
            /*jsonResult.setState(1);
            jsonResult.setMessage("该文件不存在");
            return jsonResult;*/
            return;
        }
        //模拟文件，myfile.txt为需要下载的文件
        String fileName = "D:\\新建文件夹\\" + uploadFile.getFileName();
        //获取输入流
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
        //假如以中文名下载的话
        String filename = DateUtil.getNowTime() + "" + uploadFile.getFileName();
        //转码，免得文件名中文乱码
        filename = URLEncoder.encode(filename, "UTF-8");
        //设置文件下载头
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        int len = 0;
        while ((len = bis.read()) != -1) {
            out.write(len);
            out.flush();
        }
        out.close();
        //return new JsonResult<String>(uploadFile.getFileName());
    }
}

