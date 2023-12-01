package com.webProject.webProject.Menu;

import com.webProject.webProject.Store.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    public void saveMenus(Store store, List<Menu> menuList) {

        for (Menu menu : menuList) {
            menu.setStore(store);
            this.menuRepository.save(menu);
        }
    }

    public List<Menu> getstoreMenu(Store store) {
        List<Menu> storeMenu = new ArrayList<>();
        for (Menu menu : this.menuRepository.findAll()) {
            if (menu.getStore() != null) {
                if (menu.getStore().getId() == store.getId()) {
                    storeMenu.add(menu);
                }
            }
        }
        return storeMenu;
    }

    public Menu setDefaultMenu(Store store) {
        Menu menu = new Menu();
        menu.setMenuName("메뉴");
        menu.setPrice(000);
        menu.setStore(store);
        return this.menuRepository.save(menu);
    }


}
