package com.senla.carservice.service;

import com.senla.carservice.comparator.MasterAlphabetComparator;
import com.senla.carservice.comparator.MasterBusyComparator;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.repository.MasterRepository;
import com.senla.carservice.repository.MasterRepositoryImpl;
import com.senla.carservice.repository.OrderRepository;
import com.senla.carservice.repository.OrderRepositoryImpl;
import com.senla.carservice.util.ExportUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public  class MasterServiceImpl implements MasterService {
    private static MasterService instance;
    private final MasterRepository masterRepository;

    private MasterServiceImpl() {
        this.masterRepository = MasterRepositoryImpl.getInstance();
    }

    public static MasterService getInstance() {
        if (instance == null) {
            instance = new MasterServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Master> getMasters() {
        return this.masterRepository.getMasters();
    }

    @Override
    public void addMaster(String name) {
        Master master = new Master(name);
        master.setId(this.masterRepository.getIdGeneratorMaster().getId());
        this.masterRepository.getMasters().add(master);
    }

    @Override
    public List<Master> getFreeMasters(Date executeDate, Date leadDate, List<Order> sortOrders){
        List<Master> freeMasters = new ArrayList<>(this.masterRepository.getMasters());
        if (executeDate == null || leadDate == null){
            return freeMasters;
        }
        for (Order order : sortOrders) {
            order.getMasters().forEach(freeMasters::remove);
        }
        return freeMasters;
    }

    @Override
    public void deleteMaster(Master master) {
        this.masterRepository.getMasters().remove(master);
    }

    @Override
    public List<Master> sortMasterByAlphabet(List<Master> masters) {
        List<Master> sortArrayMaster = new ArrayList<>(masters);
        MasterAlphabetComparator masterAlphabetComparator = new MasterAlphabetComparator();
        sortArrayMaster.sort(masterAlphabetComparator);
        return sortArrayMaster;
    }

    @Override
    public List<Master> sortMasterByBusy(List<Master> masters) {
        List<Master> sortArrayMaster = new ArrayList<>(masters);
        MasterBusyComparator masterBusyComparator = new MasterBusyComparator();
        sortArrayMaster.sort(masterBusyComparator);
        return sortArrayMaster;
    }

    @Override
    public String exportMasters() {
        MasterRepository masterRepository = MasterRepositoryImpl.getInstance();
        List<Master> masters = masterRepository.getMasters();
        StringBuilder valueCsv = new StringBuilder();
        for (int i = 0; i < masters.size(); i++){
            if (i == masters.size()-1) {
                valueCsv.append(convertToCsv(masters.get(i), false));
            } else{
                valueCsv.append(convertToCsv(masters.get(i), true));
            }
        }
        return ExportUtil.SaveCsv(valueCsv,"csv//masters.csv");
    }

    private String convertToCsv(Master master, boolean isLineFeed){
        if(isLineFeed){
            return String.format("%s,%s,%s\n", master.getId(), master.getName(), master.getNumberOrder());
        } else {
            return String.format("%s,%s,%s", master.getId(), master.getName(), master.getNumberOrder());
        }
    }
}