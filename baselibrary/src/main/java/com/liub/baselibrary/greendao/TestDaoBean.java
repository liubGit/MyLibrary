package com.liub.baselibrary.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Create by liub on 2019/4/22
 * Describe: 生成文件，项目需要时放到外面
 */
@Entity
public class TestDaoBean {
    @Id(autoincrement = false)
    private Long id;

    @Generated(hash = 1958240012)
    public TestDaoBean(Long id) {
        this.id = id;
    }

    @Generated(hash = 650418494)
    public TestDaoBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
