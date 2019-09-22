package com.github.poad.examples.java.webapps.database;

/** */
@javax.annotation.Generated(value = { "Doma", "2.24.0" }, date = "2019-03-12T21:30:21.969+0900")
public final class _MessageEntity extends org.seasar.doma.jdbc.entity.AbstractEntityType<com.github.poad.examples.java.webapps.database.MessageEntity> {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.24.0");
    }

    private static final _MessageEntity __singleton = new _MessageEntity();

    private final org.seasar.doma.jdbc.entity.NamingType __namingType = null;

    /** the id */
    public final org.seasar.doma.jdbc.entity.AssignedIdPropertyType<com.github.poad.examples.java.webapps.database.MessageEntity, java.lang.String, java.lang.String> $id = new org.seasar.doma.jdbc.entity.AssignedIdPropertyType<>(com.github.poad.examples.java.webapps.database.MessageEntity.class, () -> new org.seasar.doma.internal.jdbc.scalar.BasicScalar<>(org.seasar.doma.wrapper.StringWrapper::new, false), "id", "", __namingType, false);

    /** the message */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<com.github.poad.examples.java.webapps.database.MessageEntity, java.lang.String, java.lang.String> $message = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(com.github.poad.examples.java.webapps.database.MessageEntity.class, () -> new org.seasar.doma.internal.jdbc.scalar.BasicScalar<>(org.seasar.doma.wrapper.StringWrapper::new, false), "message", "", __namingType, true, true, false);

    private final java.util.function.Supplier<org.seasar.doma.jdbc.entity.NullEntityListener<com.github.poad.examples.java.webapps.database.MessageEntity>> __listenerSupplier;

    private final boolean __immutable;

    private final String __catalogName;

    private final String __schemaName;

    private final String __tableName;

    private final boolean __isQuoteRequired;

    private final String __name;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.github.poad.examples.java.webapps.database.MessageEntity, ?>> __idPropertyTypes;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.github.poad.examples.java.webapps.database.MessageEntity, ?>> __entityPropertyTypes;

    private final java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<com.github.poad.examples.java.webapps.database.MessageEntity, ?>> __entityPropertyTypeMap;

    private _MessageEntity() {
        __listenerSupplier = () -> ListenerHolder.listener;
        __immutable = true;
        __name = "MessageEntity";
        __catalogName = "";
        __schemaName = "";
        __tableName = "message";
        __isQuoteRequired = false;
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.github.poad.examples.java.webapps.database.MessageEntity, ?>> __idList = new java.util.ArrayList<>();
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.github.poad.examples.java.webapps.database.MessageEntity, ?>> __list = new java.util.ArrayList<>(2);
        java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<com.github.poad.examples.java.webapps.database.MessageEntity, ?>> __map = new java.util.HashMap<>(2);
        __idList.add($id);
        __list.add($id);
        __map.put("id", $id);
        __list.add($message);
        __map.put("message", $message);
        __idPropertyTypes = java.util.Collections.unmodifiableList(__idList);
        __entityPropertyTypes = java.util.Collections.unmodifiableList(__list);
        __entityPropertyTypeMap = java.util.Collections.unmodifiableMap(__map);
    }

    @Override
    public org.seasar.doma.jdbc.entity.NamingType getNamingType() {
        return __namingType;
    }

    @Override
    public boolean isImmutable() {
        return __immutable;
    }

    @Override
    public String getName() {
        return __name;
    }

    @Override
    public String getCatalogName() {
        return __catalogName;
    }

    @Override
    public String getSchemaName() {
        return __schemaName;
    }

    @Override
    public String getTableName() {
        return getTableName(org.seasar.doma.jdbc.Naming.DEFAULT::apply);
    }

    @Override
    public String getTableName(java.util.function.BiFunction<org.seasar.doma.jdbc.entity.NamingType, String, String> namingFunction) {
        if (__tableName.isEmpty()) {
            return namingFunction.apply(__namingType, __name);
        }
        return __tableName;
    }

    @Override
    public boolean isQuoteRequired() {
        return __isQuoteRequired;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preInsert(com.github.poad.examples.java.webapps.database.MessageEntity entity, org.seasar.doma.jdbc.entity.PreInsertContext<com.github.poad.examples.java.webapps.database.MessageEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preUpdate(com.github.poad.examples.java.webapps.database.MessageEntity entity, org.seasar.doma.jdbc.entity.PreUpdateContext<com.github.poad.examples.java.webapps.database.MessageEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preDelete(com.github.poad.examples.java.webapps.database.MessageEntity entity, org.seasar.doma.jdbc.entity.PreDeleteContext<com.github.poad.examples.java.webapps.database.MessageEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preDelete(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postInsert(com.github.poad.examples.java.webapps.database.MessageEntity entity, org.seasar.doma.jdbc.entity.PostInsertContext<com.github.poad.examples.java.webapps.database.MessageEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postUpdate(com.github.poad.examples.java.webapps.database.MessageEntity entity, org.seasar.doma.jdbc.entity.PostUpdateContext<com.github.poad.examples.java.webapps.database.MessageEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postDelete(com.github.poad.examples.java.webapps.database.MessageEntity entity, org.seasar.doma.jdbc.entity.PostDeleteContext<com.github.poad.examples.java.webapps.database.MessageEntity> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postDelete(entity, context);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.github.poad.examples.java.webapps.database.MessageEntity, ?>> getEntityPropertyTypes() {
        return __entityPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.EntityPropertyType<com.github.poad.examples.java.webapps.database.MessageEntity, ?> getEntityPropertyType(String __name) {
        return __entityPropertyTypeMap.get(__name);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.github.poad.examples.java.webapps.database.MessageEntity, ?>> getIdPropertyTypes() {
        return __idPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.GeneratedIdPropertyType<com.github.poad.examples.java.webapps.database.MessageEntity, ?, ?> getGeneratedIdPropertyType() {
        return null;
    }

    @Override
    public org.seasar.doma.jdbc.entity.VersionPropertyType<com.github.poad.examples.java.webapps.database.MessageEntity, ?, ?> getVersionPropertyType() {
        return null;
    }

    @Override
    public org.seasar.doma.jdbc.entity.TenantIdPropertyType<com.github.poad.examples.java.webapps.database.MessageEntity, ?, ?> getTenantIdPropertyType() {
        return null;
    }

    @Override
    public com.github.poad.examples.java.webapps.database.MessageEntity newEntity(java.util.Map<String, org.seasar.doma.jdbc.entity.Property<com.github.poad.examples.java.webapps.database.MessageEntity, ?>> __args) {
        return new com.github.poad.examples.java.webapps.database.MessageEntity(
            (java.lang.String)(__args.get("id") != null ? __args.get("id").get() : null),
            (java.lang.String)(__args.get("message") != null ? __args.get("message").get() : null));
    }

    @Override
    public Class<com.github.poad.examples.java.webapps.database.MessageEntity> getEntityClass() {
        return com.github.poad.examples.java.webapps.database.MessageEntity.class;
    }

    @Override
    public com.github.poad.examples.java.webapps.database.MessageEntity getOriginalStates(com.github.poad.examples.java.webapps.database.MessageEntity __entity) {
        return null;
    }

    @Override
    public void saveCurrentStates(com.github.poad.examples.java.webapps.database.MessageEntity __entity) {
    }

    /**
     * @return the singleton
     */
    public static _MessageEntity getSingletonInternal() {
        return __singleton;
    }

    /**
     * @return the new instance
     */
    public static _MessageEntity newInstance() {
        return new _MessageEntity();
    }

    private static class ListenerHolder {
        private static org.seasar.doma.jdbc.entity.NullEntityListener<com.github.poad.examples.java.webapps.database.MessageEntity> listener = new org.seasar.doma.jdbc.entity.NullEntityListener<>();
    }

}
