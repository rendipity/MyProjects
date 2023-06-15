package ReentrantReadWriteLock.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 
 * @TableName Student
 */
@TableName(value ="Student")
public class Student implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String sno;

    /**
     * 
     */
    private String sname;

    /**
     * 
     */
    private Object ssex;

    /**
     * 
     */
    private Integer sage;

    /**
     * 
     */
    private String clno;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     */
    public String getSno() {
        return sno;
    }

    /**
     * 
     */
    public void setSno(String sno) {
        this.sno = sno;
    }

    /**
     * 
     */
    public String getSname() {
        return sname;
    }

    /**
     * 
     */
    public void setSname(String sname) {
        this.sname = sname;
    }

    /**
     * 
     */
    public Object getSsex() {
        return ssex;
    }

    /**
     * 
     */
    public void setSsex(Object ssex) {
        this.ssex = ssex;
    }

    /**
     * 
     */
    public Integer getSage() {
        return sage;
    }

    /**
     * 
     */
    public void setSage(Integer sage) {
        this.sage = sage;
    }

    /**
     * 
     */
    public String getClno() {
        return clno;
    }

    /**
     * 
     */
    public void setClno(String clno) {
        this.clno = clno;
    }

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
        Student other = (Student) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSno() == null ? other.getSno() == null : this.getSno().equals(other.getSno()))
            && (this.getSname() == null ? other.getSname() == null : this.getSname().equals(other.getSname()))
            && (this.getSsex() == null ? other.getSsex() == null : this.getSsex().equals(other.getSsex()))
            && (this.getSage() == null ? other.getSage() == null : this.getSage().equals(other.getSage()))
            && (this.getClno() == null ? other.getClno() == null : this.getClno().equals(other.getClno()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSno() == null) ? 0 : getSno().hashCode());
        result = prime * result + ((getSname() == null) ? 0 : getSname().hashCode());
        result = prime * result + ((getSsex() == null) ? 0 : getSsex().hashCode());
        result = prime * result + ((getSage() == null) ? 0 : getSage().hashCode());
        result = prime * result + ((getClno() == null) ? 0 : getClno().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", sno=").append(sno);
        sb.append(", sname=").append(sname);
        sb.append(", ssex=").append(ssex);
        sb.append(", sage=").append(sage);
        sb.append(", clno=").append(clno);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}