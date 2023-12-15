package edu.bbte.idde.szim2182.backend.dao;

public abstract class DaoFactory {
    private static volatile DaoFactory instance;

    public abstract HikeDao getHikeDao();

    public abstract LocationDao getLocationDao();

    public static synchronized DaoFactory getInstance() {

        if (instance == null) {
            String profile = System.getenv("iddeVariable");
            if (profile == null || profile.isEmpty()) {
                profile = "dev";
            }

            if ("prod".equals(profile)) {
                instance = JdbcDaoFactory.getInstance();
            } else {
                instance = MemDaoFactory.getInstance();
            }
        }
        return instance;
    }
}