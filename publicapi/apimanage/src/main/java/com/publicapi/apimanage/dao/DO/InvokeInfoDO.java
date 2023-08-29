package com.publicapi.apimanage.dao.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName invoke_info
 */
@TableName(value ="invoke_info")
@Data
public class InvokeInfoDO implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    @TableField(value = "username")
    private String username;

    /**
     * api编码
     */
    @TableField(value = "api_code")
    private String apiCode;

    /**
     * 调用次数
     */
    @TableField(value = "invoke_times")
    private Integer invokeTimes;

    /**
     * 总次数
     */
    @TableField(value = "total_times")
    private Integer totalTimes;

    /**
     * 状态
     */
    @TableField(value = "status")
    private String status;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "modify_time")
    private Date modifyTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        InvokeInfoDO other = (InvokeInfoDO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getApiCode() == null ? other.getApiCode() == null : this.getApiCode().equals(other.getApiCode()))
            && (this.getInvokeTimes() == null ? other.getInvokeTimes() == null : this.getInvokeTimes().equals(other.getInvokeTimes()))
            && (this.getTotalTimes() == null ? other.getTotalTimes() == null : this.getTotalTimes().equals(other.getTotalTimes()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getApiCode() == null) ? 0 : getApiCode().hashCode());
        result = prime * result + ((getInvokeTimes() == null) ? 0 : getInvokeTimes().hashCode());
        result = prime * result + ((getTotalTimes() == null) ? 0 : getTotalTimes().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", apiCode=").append(apiCode);
        sb.append(", invokeTimes=").append(invokeTimes);
        sb.append(", totalTimes=").append(totalTimes);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}