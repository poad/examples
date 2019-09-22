package com.github.poad.examples.java.webapps.database;

/** */
@org.springframework.stereotype.Repository()
@javax.annotation.Generated(value = { "Doma", "2.24.0" }, date = "2019-03-12T21:28:35.063+0900")
public class MessageDaoImpl extends org.seasar.doma.internal.jdbc.dao.AbstractDao implements com.github.poad.examples.java.webapps.database.MessageDao {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.24.0");
    }

    private static final java.lang.reflect.Method __method0 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(com.github.poad.examples.java.webapps.database.MessageDao.class, "uuid");

    private static final java.lang.reflect.Method __method1 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(com.github.poad.examples.java.webapps.database.MessageDao.class, "selectAll", org.seasar.doma.jdbc.SelectOptions.class);

    private static final java.lang.reflect.Method __method2 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(com.github.poad.examples.java.webapps.database.MessageDao.class, "selectById", java.lang.String.class);

    private static final java.lang.reflect.Method __method3 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(com.github.poad.examples.java.webapps.database.MessageDao.class, "insert", com.github.poad.examples.java.webapps.database.MessageEntity.class);

    private static final java.lang.reflect.Method __method4 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(com.github.poad.examples.java.webapps.database.MessageDao.class, "update", com.github.poad.examples.java.webapps.database.MessageEntity.class);

    private static final java.lang.reflect.Method __method5 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(com.github.poad.examples.java.webapps.database.MessageDao.class, "delete", com.github.poad.examples.java.webapps.database.MessageEntity.class);

    /**
     * @param config the config
     */
    @org.springframework.beans.factory.annotation.Autowired()
    public MessageDaoImpl(org.seasar.doma.jdbc.Config config) {
        super(config);
    }

    @Override
    public java.lang.String uuid() {
        entering("com.github.poad.examples.java.webapps.database.MessageDaoImpl", "uuid");
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method0);
            __query.setMethod(__method0);
            __query.setConfig(__config);
            __query.setSqlFilePath("com.github.poad.examples.java.webapps.database.MessageDao#uuid");
            __query.setCallerClassName("com.github.poad.examples.java.webapps.database.MessageDaoImpl");
            __query.setCallerMethodName("uuid");
            __query.setResultEnsured(true);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.lang.String> __command = getCommandImplementors().createSelectCommand(__method0, __query, new org.seasar.doma.internal.jdbc.command.BasicSingleResultHandler<java.lang.String>(org.seasar.doma.wrapper.StringWrapper::new, false));
            java.lang.String __result = __command.execute();
            __query.complete();
            exiting("com.github.poad.examples.java.webapps.database.MessageDaoImpl", "uuid", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("com.github.poad.examples.java.webapps.database.MessageDaoImpl", "uuid", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<com.github.poad.examples.java.webapps.database.MessageEntity> selectAll(org.seasar.doma.jdbc.SelectOptions options) {
        entering("com.github.poad.examples.java.webapps.database.MessageDaoImpl", "selectAll", options);
        try {
            if (options == null) {
                throw new org.seasar.doma.DomaNullPointerException("options");
            }
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method1);
            __query.setMethod(__method1);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/com/github/poad/examples/java/webapps/database/MessageDao/selectAll.sql");
            __query.setOptions(options);
            __query.setEntityType(com.github.poad.examples.java.webapps.database._MessageEntity.getSingletonInternal());
            __query.setCallerClassName("com.github.poad.examples.java.webapps.database.MessageDaoImpl");
            __query.setCallerMethodName("selectAll");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<com.github.poad.examples.java.webapps.database.MessageEntity>> __command = getCommandImplementors().createSelectCommand(__method1, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<com.github.poad.examples.java.webapps.database.MessageEntity>(com.github.poad.examples.java.webapps.database._MessageEntity.getSingletonInternal()));
            java.util.List<com.github.poad.examples.java.webapps.database.MessageEntity> __result = __command.execute();
            __query.complete();
            exiting("com.github.poad.examples.java.webapps.database.MessageDaoImpl", "selectAll", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("com.github.poad.examples.java.webapps.database.MessageDaoImpl", "selectAll", __e);
            throw __e;
        }
    }

    @Override
    public com.github.poad.examples.java.webapps.database.MessageEntity selectById(java.lang.String id) {
        entering("com.github.poad.examples.java.webapps.database.MessageDaoImpl", "selectById", id);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = getQueryImplementors().createSqlFileSelectQuery(__method2);
            __query.setMethod(__method2);
            __query.setConfig(__config);
            __query.setSqlFilePath("META-INF/com/github/poad/examples/java/webapps/database/MessageDao/selectById.sql");
            __query.setEntityType(com.github.poad.examples.java.webapps.database._MessageEntity.getSingletonInternal());
            __query.addParameter("id", java.lang.String.class, id);
            __query.setCallerClassName("com.github.poad.examples.java.webapps.database.MessageDaoImpl");
            __query.setCallerMethodName("selectById");
            __query.setResultEnsured(true);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<com.github.poad.examples.java.webapps.database.MessageEntity> __command = getCommandImplementors().createSelectCommand(__method2, __query, new org.seasar.doma.internal.jdbc.command.EntitySingleResultHandler<com.github.poad.examples.java.webapps.database.MessageEntity>(com.github.poad.examples.java.webapps.database._MessageEntity.getSingletonInternal()));
            com.github.poad.examples.java.webapps.database.MessageEntity __result = __command.execute();
            __query.complete();
            exiting("com.github.poad.examples.java.webapps.database.MessageDaoImpl", "selectById", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("com.github.poad.examples.java.webapps.database.MessageDaoImpl", "selectById", __e);
            throw __e;
        }
    }

    @Override
    public org.seasar.doma.jdbc.Result<com.github.poad.examples.java.webapps.database.MessageEntity> insert(com.github.poad.examples.java.webapps.database.MessageEntity message) {
        entering("com.github.poad.examples.java.webapps.database.MessageDaoImpl", "insert", message);
        try {
            if (message == null) {
                throw new org.seasar.doma.DomaNullPointerException("message");
            }
            org.seasar.doma.jdbc.query.AutoInsertQuery<com.github.poad.examples.java.webapps.database.MessageEntity> __query = getQueryImplementors().createAutoInsertQuery(__method3, com.github.poad.examples.java.webapps.database._MessageEntity.getSingletonInternal());
            __query.setMethod(__method3);
            __query.setConfig(__config);
            __query.setEntity(message);
            __query.setCallerClassName("com.github.poad.examples.java.webapps.database.MessageDaoImpl");
            __query.setCallerMethodName("insert");
            __query.setQueryTimeout(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.setNullExcluded(false);
            __query.setIncludedPropertyNames();
            __query.setExcludedPropertyNames();
            __query.prepare();
            org.seasar.doma.jdbc.command.InsertCommand __command = getCommandImplementors().createInsertCommand(__method3, __query);
            int __count = __command.execute();
            __query.complete();
            org.seasar.doma.jdbc.Result<com.github.poad.examples.java.webapps.database.MessageEntity> __result = new org.seasar.doma.jdbc.Result<com.github.poad.examples.java.webapps.database.MessageEntity>(__count, __query.getEntity());
            exiting("com.github.poad.examples.java.webapps.database.MessageDaoImpl", "insert", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("com.github.poad.examples.java.webapps.database.MessageDaoImpl", "insert", __e);
            throw __e;
        }
    }

    @Override
    public org.seasar.doma.jdbc.Result<com.github.poad.examples.java.webapps.database.MessageEntity> update(com.github.poad.examples.java.webapps.database.MessageEntity message) {
        entering("com.github.poad.examples.java.webapps.database.MessageDaoImpl", "update", message);
        try {
            if (message == null) {
                throw new org.seasar.doma.DomaNullPointerException("message");
            }
            org.seasar.doma.jdbc.query.AutoUpdateQuery<com.github.poad.examples.java.webapps.database.MessageEntity> __query = getQueryImplementors().createAutoUpdateQuery(__method4, com.github.poad.examples.java.webapps.database._MessageEntity.getSingletonInternal());
            __query.setMethod(__method4);
            __query.setConfig(__config);
            __query.setEntity(message);
            __query.setCallerClassName("com.github.poad.examples.java.webapps.database.MessageDaoImpl");
            __query.setCallerMethodName("update");
            __query.setQueryTimeout(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.setNullExcluded(false);
            __query.setVersionIgnored(false);
            __query.setIncludedPropertyNames();
            __query.setExcludedPropertyNames();
            __query.setUnchangedPropertyIncluded(false);
            __query.setOptimisticLockExceptionSuppressed(false);
            __query.prepare();
            org.seasar.doma.jdbc.command.UpdateCommand __command = getCommandImplementors().createUpdateCommand(__method4, __query);
            int __count = __command.execute();
            __query.complete();
            org.seasar.doma.jdbc.Result<com.github.poad.examples.java.webapps.database.MessageEntity> __result = new org.seasar.doma.jdbc.Result<com.github.poad.examples.java.webapps.database.MessageEntity>(__count, __query.getEntity());
            exiting("com.github.poad.examples.java.webapps.database.MessageDaoImpl", "update", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("com.github.poad.examples.java.webapps.database.MessageDaoImpl", "update", __e);
            throw __e;
        }
    }

    @Override
    public org.seasar.doma.jdbc.Result<com.github.poad.examples.java.webapps.database.MessageEntity> delete(com.github.poad.examples.java.webapps.database.MessageEntity message) {
        entering("com.github.poad.examples.java.webapps.database.MessageDaoImpl", "delete", message);
        try {
            if (message == null) {
                throw new org.seasar.doma.DomaNullPointerException("message");
            }
            org.seasar.doma.jdbc.query.AutoDeleteQuery<com.github.poad.examples.java.webapps.database.MessageEntity> __query = getQueryImplementors().createAutoDeleteQuery(__method5, com.github.poad.examples.java.webapps.database._MessageEntity.getSingletonInternal());
            __query.setMethod(__method5);
            __query.setConfig(__config);
            __query.setEntity(message);
            __query.setCallerClassName("com.github.poad.examples.java.webapps.database.MessageDaoImpl");
            __query.setCallerMethodName("delete");
            __query.setQueryTimeout(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.setVersionIgnored(false);
            __query.setOptimisticLockExceptionSuppressed(false);
            __query.prepare();
            org.seasar.doma.jdbc.command.DeleteCommand __command = getCommandImplementors().createDeleteCommand(__method5, __query);
            int __count = __command.execute();
            __query.complete();
            org.seasar.doma.jdbc.Result<com.github.poad.examples.java.webapps.database.MessageEntity> __result = new org.seasar.doma.jdbc.Result<com.github.poad.examples.java.webapps.database.MessageEntity>(__count, __query.getEntity());
            exiting("com.github.poad.examples.java.webapps.database.MessageDaoImpl", "delete", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("com.github.poad.examples.java.webapps.database.MessageDaoImpl", "delete", __e);
            throw __e;
        }
    }

}
