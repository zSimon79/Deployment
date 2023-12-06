package edu.bbte.idde.szim2182.backend.dao;

public final class JdbcDaoFactory extends DaoFactory {

    private static JdbcDaoFactory instance;

    private JdbcDaoFactory() {
        super();
    }

    @Override
    public HikeDao getHikeDao() {
        return new JdbcHikeDao();
    }

    @Override
    public LocationDao getLocationDao() {
        return new JdbcLocationDao();
    }

    public static synchronized JdbcDaoFactory getInstance() {
        if (instance == null) {
            instance = new JdbcDaoFactory();
        }
        return instance;
    }
}