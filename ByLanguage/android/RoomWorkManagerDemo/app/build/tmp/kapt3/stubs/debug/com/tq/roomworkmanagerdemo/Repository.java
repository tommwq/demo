package com.tq.roomworkmanagerdemo;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\u0018\u0000 \f2\u00020\u0001:\u0001\fB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0012\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/tq/roomworkmanagerdemo/Repository;", "", "userDao", "Lcom/tq/roomworkmanagerdemo/UserDao;", "(Lcom/tq/roomworkmanagerdemo/UserDao;)V", "getUsers", "Landroidx/lifecycle/LiveData;", "", "Lcom/tq/roomworkmanagerdemo/User;", "insertUser", "", "user", "Companion", "app_debug"})
public final class Repository {
    private final com.tq.roomworkmanagerdemo.UserDao userDao = null;
    @org.jetbrains.annotations.NotNull()
    public static com.tq.roomworkmanagerdemo.Repository instance;
    public static final com.tq.roomworkmanagerdemo.Repository.Companion Companion = null;
    
    public final long insertUser(@org.jetbrains.annotations.NotNull()
    com.tq.roomworkmanagerdemo.User user) {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.tq.roomworkmanagerdemo.User>> getUsers() {
        return null;
    }
    
    private Repository(com.tq.roomworkmanagerdemo.UserDao userDao) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\r"}, d2 = {"Lcom/tq/roomworkmanagerdemo/Repository$Companion;", "", "()V", "instance", "Lcom/tq/roomworkmanagerdemo/Repository;", "getInstance", "()Lcom/tq/roomworkmanagerdemo/Repository;", "setInstance", "(Lcom/tq/roomworkmanagerdemo/Repository;)V", "init", "", "userDao", "Lcom/tq/roomworkmanagerdemo/UserDao;", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final com.tq.roomworkmanagerdemo.Repository getInstance() {
            return null;
        }
        
        public final void setInstance(@org.jetbrains.annotations.NotNull()
        com.tq.roomworkmanagerdemo.Repository p0) {
        }
        
        public final void init(@org.jetbrains.annotations.NotNull()
        com.tq.roomworkmanagerdemo.UserDao userDao) {
        }
        
        private Companion() {
            super();
        }
    }
}