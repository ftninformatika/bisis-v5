package com.ftninformatika.bisis.core.repositories;

import com.ftninformatika.bisis.coders.ItemStatus;

import java.util.List;

public interface ItemStatusRepository extends CoderRepository<ItemStatus> {

    List<ItemStatus> findAllByLibraryAndShowable(String library, boolean showable);

}
