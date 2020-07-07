package com.senla.carservice.repository;

import com.senla.carservice.domain.Master;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MasterRepositoryImpl implements MasterRepository, Serializable {
    private static MasterRepository instance;
    private static final long serialVersionUID = 1L;
    private final List<Master> masters;
    private final IdGenerator idGeneratorMaster;

    private MasterRepositoryImpl() {
        this.masters = new ArrayList<>();
        this.idGeneratorMaster = new IdGenerator();
    }

    public static MasterRepository getInstance() {
        if (instance == null) {
            instance = new MasterRepositoryImpl();
        }
        return instance;
    }

    @Override
    public IdGenerator getIdGeneratorMaster() {
        return idGeneratorMaster;
    }

    @Override
    public void addMaster(Master master) {
        master.setId(this.idGeneratorMaster.getId());
        this.masters.add(master);
    }

    @Override
    public void updateMaster(Master master) {
        int index = this.masters.indexOf(master);
        if (index == -1) {
            this.masters.add(master);
        } else {
            this.masters.set(index, master);
        }
    }

    @Override
    public void updateListMaster(List<Master> masters) {
        this.masters.clear();
        this.masters.addAll(masters);
    }

    @Override
    public void updateGenerator(IdGenerator idGenerator) {
        this.idGeneratorMaster.setId(idGenerator.getId());
    }

    @Override
    public void deleteMaster(Master master) {
        this.masters.remove(master);
    }

    @Override
    public List<Master> getMasters() {
        return new ArrayList<>(this.masters);
    }

    @Override
    public List<Master> getFreeMasters(Date date) {
        return this.masters.stream()
            .filter(master -> master.getOrders().isEmpty() ||
                              date.before(master.getOrders().get(master.getOrders().size() - 1).getLeadTime()))
            .collect(Collectors.toList());
    }
}