package com.jack.sparrow.potc.restaurantmanagement.service;

import com.jack.sparrow.potc.restaurantmanagement.model.Cuisine;
import com.jack.sparrow.potc.restaurantmanagement.model.Menu;
import com.jack.sparrow.potc.restaurantmanagement.repository.CuisineRepository;
import com.jack.sparrow.potc.restaurantmanagement.repository.MenuRepository;
import com.jack.sparrow.potc.restaurantmanagement.requestModel.CuisineRestModel;
import com.jack.sparrow.potc.restaurantmanagement.requestModel.MenuRestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private CuisineRepository cuisineRepository;

    public MenuRestModel addItem(MenuRestModel menuObj){
        validateMenuItem(menuObj);
        Cuisine cuisine = cuisineRepository.findByCuisineName(menuObj.getCuisineName());
        Menu menu = new Menu(cuisine, menuObj.getPortion(), menuObj.getPrice());
        try {
            menuRepository.save(menu);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return menuObj;
    }

    public MenuRestModel findByItemId(Long itemId){
        try {
            Optional<Menu> menuObj = menuRepository.findById(itemId);
            return menuObj.map(menu -> new MenuRestModel(menu.getItemId(), menu.getCuisine().getCuisineName(),
                        menu.getPortion(), menu.getPrice()))
                    .orElseThrow(() -> new RuntimeException("Invalid itemId"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<MenuRestModel> findByCuisine(String cuisineName){
        if (cuisineName == null || cuisineName.isEmpty()) {
            throw new RuntimeException("cuisine name is null/empty");
        }
        try{
            Cuisine cuisine = cuisineRepository.findByCuisineName(cuisineName);
            if (cuisine == null) {
                throw new RuntimeException("Invalid cuisine name");
            }
            List<Menu> menuList = menuRepository.findByCuisine_CuisineId(cuisine.getCuisineId());
            return menuList.stream().map(this::convertMenuToRestModel)
                    .collect(Collectors.toList());
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private MenuRestModel convertMenuToRestModel(Menu menu){
        return new MenuRestModel(menu.getItemId(), menu.getCuisine().getCuisineName(),
                menu.getPortion(), menu.getPrice());
    }

    private void validateMenuItem(MenuRestModel menuItem) {
        if (menuItem.getItemId() != null) {
            throw new RuntimeException("itemId is an auto generated field");
        }

        if (menuItem.getCuisineName() == null) {
            throw new RuntimeException("Cuisine name cannot be null");
        }

        if (menuItem.getPrice() == null) {
            throw new RuntimeException("Menu item price cannot be null");
        }

        Cuisine cuisine = cuisineRepository.findByCuisineName(menuItem.getCuisineName());
        if (cuisine == null){
            throw new RuntimeException("Invalid cuisine name");
        }

        if (menuItem.getPortion() != null && !"FULL".equals(menuItem.getPortion())
                && !"HALF".equals(menuItem.getPortion())){
            throw new RuntimeException("Invalid portion");
        }

        if (!menuRepository.findByCuisine_CuisineIdAndPortion(cuisine.getCuisineId(), menuItem.getPortion()).isEmpty()){
            throw new RuntimeException("Menu item already exist with this cuisine and portion");
        }
    }
}
