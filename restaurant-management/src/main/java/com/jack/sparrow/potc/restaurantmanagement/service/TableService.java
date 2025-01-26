package com.jack.sparrow.potc.restaurantmanagement.service;

import com.jack.sparrow.potc.restaurantmanagement.exception.RestaurantManagementException;
import com.jack.sparrow.potc.restaurantmanagement.model.RmTable;
import com.jack.sparrow.potc.restaurantmanagement.repository.RmTableRepository;
import com.jack.sparrow.potc.restaurantmanagement.requestModel.RestModel;
import com.jack.sparrow.potc.restaurantmanagement.requestModel.RmTableRestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TableService {

    @Autowired
    private RmTableRepository tableRepository;

    public List<RestModel> getAllTable(){
        try {
            List<RmTable> tableList = tableRepository.findAll(Sort.by(Sort.Direction.ASC, "tableNumber"));
            List<RestModel> tableRestModelList = new ArrayList<>();
            for (RmTable table : tableList) {
                tableRestModelList.add(convertRmTableToRestModel(table));
            }
            return tableRestModelList;
        } catch (Exception e) {
            throw new RestaurantManagementException("Exception while fetching all orders", e);
        }
    }

    public RmTableRestModel getTableByTableNumber(String tableNumber){
        try {
            RmTable table = tableRepository.findByTableNumber(tableNumber);
            return convertRmTableToRestModel(table);
        } catch (Exception e) {
            throw new RestaurantManagementException("Exception while fetching all orders", e);
        }
    }

    private RmTableRestModel convertRmTableToRestModel(RmTable table){
        return new RmTableRestModel(table.getTableId(), table.getTableNumber(), table.getTableStatus(), table.getCapacity());
    }
}
