package com.ftninformatika.bisis.datawarehouse.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CustomRepository{

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void resetSequence(){
        entityManager.joinTransaction();
        entityManager.createNativeQuery("ALTER SEQUENCE bisis_reports.ITEM_ITEM_ID_SEQ RESTART 1;").executeUpdate();
        entityManager.createNativeQuery("ALTER SEQUENCE bisis_reports.MEMBERSHIP_MEMBERSHIP_ID_SEQ RESTART 1;").executeUpdate();
        entityManager.createNativeQuery("ALTER SEQUENCE bisis_reports.LENDING_LENDING_ID_SEQ RESTART 1;").executeUpdate();
    };

    @Transactional
    public void resetItemSequence() {
        entityManager.joinTransaction();
        entityManager.createNativeQuery("ALTER SEQUENCE bisis_reports.ITEM_ITEM_ID_SEQ RESTART 1;").executeUpdate();

    }

    @Transactional
    public void resetMembershipSequence() {
        entityManager.joinTransaction();
        entityManager.createNativeQuery("ALTER SEQUENCE bisis_reports.MEMBERSHIP_MEMBERSHIP_ID_SEQ RESTART 1;").executeUpdate();

    }

    @Transactional
    public void resetLendingSequence() {
        entityManager.joinTransaction();
        entityManager.createNativeQuery("ALTER SEQUENCE bisis_reports.LENDING_LENDING_ID_SEQ RESTART 1;").executeUpdate();

    }

    @Transactional
    public void deleteLendingByLibrary(String library) {
        entityManager.createNativeQuery("delete from bisis_reports.lending where library=:lib").
                setParameter("lib",library).executeUpdate();

    }

    @Transactional
    public void deleteMembershipByLibrary(String library) {
        entityManager.joinTransaction();
        entityManager.createNativeQuery("delete from bisis_reports.membership where library=:lib").
                setParameter("lib",library).executeUpdate();

    }

    @Transactional
    public void deleteItemByLibrary(String library) {
        entityManager.joinTransaction();
        entityManager.createNativeQuery("delete from bisis_reports.item where library=:lib").
                setParameter("lib",library).executeUpdate();

    }

    @Transactional
    public void deleteTaskByLibrary(String library) {
        entityManager.joinTransaction();
        entityManager.createNativeQuery("delete from bisis_reports.task where library=:lib").
                setParameter("lib",library).executeUpdate();

    }


    @Transactional
    public void deleteAllLendingInBatch() {
        entityManager.createNativeQuery("delete from bisis_reports.lending").executeUpdate();
    }

    @Transactional
    public void deleteAllMembershipInBatch() {
        entityManager.createNativeQuery("delete from bisis_reports.membership").executeUpdate();

    }

    @Transactional
    public void deleteAllItemInBatch() {
        entityManager.createNativeQuery("delete from bisis_reports.item").executeUpdate();

    }

}
