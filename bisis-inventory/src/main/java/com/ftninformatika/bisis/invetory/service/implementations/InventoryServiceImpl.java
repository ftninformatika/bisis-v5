package com.ftninformatika.bisis.invetory.service.implementations;

import com.ftninformatika.bisis.inventory.Inventory;
import com.ftninformatika.bisis.invetory.repository.InventoryRepository;
import com.ftninformatika.bisis.invetory.service.interfaces.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private InventoryRepository inventoryRepository;

    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository){
        this.inventoryRepository = inventoryRepository;

    }

    @Override
    public Inventory create(Inventory inventory) {
        if (inventory == null || inventory.get_id() != null) {
            return null;
        }
        try {
            return inventoryRepository.insert(inventory);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Inventory update(Inventory inventory) {
        if (inventory == null || inventory.get_id() == null) {
            //todo put logger
            return null;
        }
        try {
            return inventoryRepository.save(inventory);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(Inventory inventory) {
        try {
            inventoryRepository.delete(inventory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Inventory getOne(String _id) {
        return null;
    }

    @Override
    public List<Inventory> getAllForLib(String lib) {
        return inventoryRepository.findAllByLibrary(lib);
    }
}
