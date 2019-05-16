package tk.mybatis.springboot.modules.sys.service;

import com.github.pagehelper.PageHelper;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.springboot.conf.FileProperties;
import tk.mybatis.springboot.modules.sys.mapper.FileMapper;
import tk.mybatis.springboot.modules.sys.model.UploadFile;
import tk.mybatis.springboot.modules.sys.vo.UserVo;
import tk.mybatis.springboot.common.util.UUIDGenerator;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private FileProperties fileProperties;

    @Autowired
    private FileService fileService;

    public int save(MultipartFile file, UserVo userVo, String status) {
        UploadFile f = new UploadFile();
        String fileName = file.getOriginalFilename();
        String type = fileName.substring(fileName.lastIndexOf("."));
        f.setId(UUIDGenerator.getUUID());
        f.setFileName(fileName);
        f.setFileSize(file.getSize());
        f.setFileType(type);
        f.setFileState(status);
        f.setUploadTime(new Date());
        f.setUploadUser(userVo.getLoginName());
        int i = fileMapper.insert(f);
        return i;
    }

    public PageInfo<UploadFile> findAll(UploadFile uploadFile) {
        PageHelper.startPage(uploadFile.getPage(), uploadFile.getRows());
        Example example = new Example(UploadFile.class);
        Example.Criteria criteria = example.createCriteria();
        if (uploadFile.getFileName() != null && uploadFile.getFileName().length() > 0) {
            criteria.andLike("fileName", "%" + uploadFile.getFileName() + "%");
        }
        if (uploadFile.getFileType() != null && uploadFile.getFileType().length() > 0) {
            criteria.andLike("fileType", "%" + uploadFile.getFileType() + "%");
        }
        Example.OrderBy orderBy = example.orderBy("uploadTime");
        orderBy.desc();
        List<UploadFile> list = fileMapper.selectByExample(example);

        return new PageInfo<UploadFile>(list);
    }

    public UploadFile findById(String id) {
        return fileMapper.selectByPrimaryKey(id);
    }

    public int delete(String id) {
        return fileMapper.deleteByPrimaryKey(id);
    }


    public void uploadFile(MultipartFile file, HttpServletRequest request) throws IOException {
        String fileSavePath = fileProperties.getFileUploadPath();
        String name = file.getOriginalFilename();
        file.transferTo(new File(fileSavePath + name));

        UserVo sysUser = (UserVo) request.getSession().getAttribute("user");
        System.out.println("-----userVO : "+sysUser.toString());
        fileService.save(file, sysUser, "发送成功");

    }


}
