package edu.bbte.idde.szim2182.backend.dao;

public abstract class DaoFactory {
    public abstract HikeDao getHikeDao();

    public abstract LocationDao getLocationDao();

}