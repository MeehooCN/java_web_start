package com.meehoo.biz.core.basic.dao.bos;

import com.meehoo.biz.core.basic.domain.bos.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by CZ on 2018/7/27.
 */
public interface IMenuDao extends JpaRepository<Menu, String> {

    @Query("from Menu e where e.menuType = ?1")
    List<Menu> queryByMenuType(Integer menuType)throws Exception;

}
