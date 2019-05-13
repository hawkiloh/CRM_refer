package tk.mybatis.springboot.modules.sys.model;

import tk.mybatis.springboot.common.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 秦建平 on 2018/3/5.
 */
public class UploadFile extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fileName;
    private String fileType;
    private Long fileSize;
    private String fileState;
    private Date uploadTime;
    private String uploadUser;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileState() {
        return fileState;
    }

    public void setFileState(String fileState) {
        this.fileState = fileState;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getUploadUser() {
        return uploadUser;
    }

    public void setUploadUser(String uploadUser) {
        this.uploadUser = uploadUser;
    }
}
