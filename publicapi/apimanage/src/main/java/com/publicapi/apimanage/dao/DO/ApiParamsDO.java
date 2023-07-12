package com.publicapi.apimanage.dao.DO;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName api_params
 */
@TableName(value ="api_params")
@Data
public class ApiParamsDO implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "param_name")
    private String paramName;

    /**
     * 
     */
    @TableField(value = "param_type")
    private String paramType;

    /**
     * request or response
     */
    @TableField(value = "type")
    private String type;

    /**
     * 是否必传
     */
    @TableField(value = "required")
    private Integer required;

    /**
     * 
     */
    @TableField(value = "example_value")
    private String exampleValue;

    /**
     * 
     */
    @TableField(value = "param_desc")
    private String paramDesc;

    /**
     * 
     */
    @TableField(value = "api_code")
    private String apiCode;

    /**
     * 
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 
     */
    @TableField(value = "is_delete")
    @TableLogic(delval = "1",value = "0")
    private Integer isDelete;

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
        ApiParamsDO other = (ApiParamsDO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getParamName() == null ? other.getParamName() == null : this.getParamName().equals(other.getParamName()))
            && (this.getParamType() == null ? other.getParamType() == null : this.getParamType().equals(other.getParamType()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getRequired() == null ? other.getRequired() == null : this.getRequired().equals(other.getRequired()))
            && (this.getExampleValue() == null ? other.getExampleValue() == null : this.getExampleValue().equals(other.getExampleValue()))
            && (this.getApiCode() == null ? other.getApiCode() == null : this.getApiCode().equals(other.getApiCode()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getParamName() == null) ? 0 : getParamName().hashCode());
        result = prime * result + ((getParamType() == null) ? 0 : getParamType().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getRequired() == null) ? 0 : getRequired().hashCode());
        result = prime * result + ((getExampleValue() == null) ? 0 : getExampleValue().hashCode());
        result = prime * result + ((getApiCode() == null) ? 0 : getApiCode().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", paramName=").append(paramName);
        sb.append(", paramType=").append(paramType);
        sb.append(", type=").append(type);
        sb.append(", required=").append(required);
        sb.append(", exampleValue=").append(exampleValue);
        sb.append(", apiCode=").append(apiCode);
        sb.append(", createTime=").append(createTime);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}