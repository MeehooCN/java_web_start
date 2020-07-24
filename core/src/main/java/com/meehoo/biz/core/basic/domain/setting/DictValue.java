package com.meehoo.biz.core.basic.domain.setting;

import com.meehoo.biz.core.basic.domain.IdEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 数据字典的值
 *
 * @author xg
 * @create 2017-05-11-10:36
 */
@Entity
@Table(name = "sys_dict_value")
@DynamicInsert
@DynamicUpdate
public class DictValue extends IdEntity {

    //是系统预设
    public static final int ISSYSSET_YES = 1;
    //非系统预设
    public static final int ISSYSSET_NO = 0;

//    @Id
//    @GeneratedValue(generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
//    private String id;

    /**
     * 键
     */
    @Column(length = 64)
    private String mkey;

    /**
     * 值
     */
    @Column(length = 255)
    private String mvalue;

    /**
     * 所属类型  外键
     */
    @ManyToOne
    private DictType dictType;


    /**
     * 是否系统预设
     */
    @Column
    private int isSysSet = ISSYSSET_NO;

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public String getMkey() {
        return mkey;
    }

    public void setMkey(String mkey) {
        this.mkey = mkey;
    }

    public String getMvalue() {
        return mvalue;
    }

    public void setMvalue(String mvalue) {
        this.mvalue = mvalue;
    }

    public DictType getDictType() {
        return dictType;
    }

    public void setDictType(DictType dictType) {
        this.dictType = dictType;
    }

    public int getIsSysSet() {
        return isSysSet;
    }

    public void setIsSysSet(int isSysSet) {
        this.isSysSet = isSysSet;
    }
}
