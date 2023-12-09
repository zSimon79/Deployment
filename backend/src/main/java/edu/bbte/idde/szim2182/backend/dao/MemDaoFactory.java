package edu.bbte.idde.szim2182.backend.dao;

public final class MemDaoFactory extends DaoFactory {

    private static MemDaoFactory instance;

    private MemDaoFactory() {
        super();
    }

    @Override
    public HikeDao getHikeDao() {
        return new MemHikeDao();
    }

    @Override
    public LocationDao getLocationDao() {
        return new MemLocationDao();
    }

    public static synchronized MemDaoFactory getInstance() {
        if (instance == null) {
            instance = new MemDaoFactory();
        }
        return instance;
    }
}
