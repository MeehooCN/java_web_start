package com.meehoo.biz.core.basic.service.bos;

import com.meehoo.biz.core.basic.dao.bos.IMenuDao;
import com.meehoo.biz.core.basic.domain.bos.Menu;
import com.meehoo.biz.core.basic.service.BaseService;
import com.meehoo.biz.core.basic.util.VOUtil;
import com.meehoo.biz.core.basic.vo.bos.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * Created by cz on 2018/07/27.
 */
@Service
@Transactional
public class MenuServiceImpl extends BaseService implements IMenuService {

    private final IMenuDao menuDao;

    @Autowired
    public MenuServiceImpl(IMenuDao menuDao) {
        this.menuDao = menuDao;
    }

    @Override
    public List<MenuVO> listAll() throws Exception {
        List<Menu> menuList = menuDao.findAll();
        List<Menu> topMenus = new ArrayList<>(5);
        for (Menu menu : menuList) {
            if (menu.getStatus() == Menu.STATUS_ENABLE) {
                if (menu.getGrade() == Menu.GRADE_ROOT) {
                    topMenus.add(menu);
                } else {
                    menu.getParentMenu().addChildMenu(menu);
                }
            }
        }
        topMenus.sort(Comparator.comparingInt(Menu::getSeq));
        return VOUtil.convertDomainListToTempList(topMenus, MenuVO.class);
    }

}