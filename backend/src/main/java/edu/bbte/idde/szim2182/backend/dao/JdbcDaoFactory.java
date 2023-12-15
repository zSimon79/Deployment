package edu.bbte.idde.szim2182.backend.dao;

import edu.bbte.idde.szim2182.backend.datasource.DataSourceUtil;

public final class JdbcDaoFactory extends DaoFactory {

    private static JdbcDaoFactory instance;

    private JdbcDaoFactory() {
        super();
    }

    @Override
    public HikeDao getHikeDao() {
        return new JdbcHikeDao(DataSourceUtil.getDataSource());
    }

    @Override
    public LocationDao getLocationDao() {
        return new JdbcLocationDao(DataSourceUtil.getDataSource());
    }

    public static synchronized JdbcDaoFactory getInstance() {
        if (instance == null) {
            instance = new JdbcDaoFactory();
        }
        return instance;
    }
}