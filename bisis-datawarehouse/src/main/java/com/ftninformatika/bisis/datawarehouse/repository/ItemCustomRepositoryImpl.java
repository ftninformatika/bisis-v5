package com.ftninformatika.bisis.datawarehouse.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ItemCustomRepositoryImpl implements ItemCustomRepository{

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void resetSequence(){
        entityManager.joinTransaction();
        entityManager.createNativeQuery("ALTER SEQUENCE bisis_reports.ITEM_ITEM_ID_SEQ RESTART 1;").executeUpdate();
        entityManager.createNativeQuery("ALTER SEQUENCE bisis_reports.MEMBERSHIP_MEMBERSHIP_ID_SEQ RESTART 1;").executeUpdate();
        entityManager.createNativeQuery("ALTER SEQUENCE bisis_reports.LENDING_LENDING_ID_SEQ RESTART 1;").executeUpdate();
    };

    @Override
    @Transactional
    public void resetItemSequence() {
        entityManager.joinTransaction();
        entityManager.createNativeQuery("ALTER SEQUENCE bisis_reports.ITEM_ITEM_ID_SEQ RESTART 1;").executeUpdate();

    }

    @Override
    @Transactional
    public void resetMembershipSequence() {
        entityManager.joinTransaction();
        entityManager.createNativeQuery("ALTER SEQUENCE bisis_reports.MEMBERSHIP_MEMBERSHIP_ID_SEQ RESTART 1;").executeUpdate();

    }

    @Override
    @Transactional
    public void resetLendingSequence() {
        entityManager.joinTransaction();
        entityManager.createNativeQuery("ALTER SEQUENCE bisis_reports.LENDING_LENDING_ID_SEQ RESTART 1;").executeUpdate();

    }


}
