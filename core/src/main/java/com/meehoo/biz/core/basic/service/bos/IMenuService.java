package com.meehoo.biz.core.basic.service.bos;


import com.meehoo.biz.core.basic.service.IBaseService;
import com.meehoo.biz.core.basic.vo.bos.MenuVO;

import java.util.List;


/**
 * Created by cz on 2018/07/27.
 */
public interface IMenuService extends IBaseService {
    List<MenuVO> listAll() throws Exception;
}