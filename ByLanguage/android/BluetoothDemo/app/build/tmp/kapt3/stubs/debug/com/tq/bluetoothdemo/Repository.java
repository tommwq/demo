package com.tq.bluetoothdemo;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u0000 \r2\u00020\u0001:\u0001\rB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0012\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006J\u0019\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u000e"}, d2 = {"Lcom/tq/bluetoothdemo/Repository;", "", "database", "Lcom/tq/bluetoothdemo/AppDatabase;", "(Lcom/tq/bluetoothdemo/AppDatabase;)V", "getNotices", "Landroidx/lifecycle/LiveData;", "", "Lcom/tq/bluetoothdemo/Notice;", "insertNotice", "", "notice", "(Lcom/tq/bluetoothdemo/Notice;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
public final class Repository {
    private final com.tq.bluetoothdemo.AppDatabase database = null;
    @org.jetbrains.annotations.NotNull()
    public static com.tq.bluetoothdemo.Repository instance;
    public static final com.tq.bluetoothdemo.Repository.Companion Companion = null;
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.tq.bluetoothdemo.Notice>> getNotices() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insertNotice(@org.jetbrains.annotations.NotNull()
    com.tq.bluetoothdemo.Notice notice, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> p1) {
        return null;
    }
    
    private Repository(com.tq.bluetoothdemo.AppDatabase database) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\r"}, d2 = {"Lcom/tq/bluetoothdemo/Repository$Companion;", "", "()V", "instance", "Lcom/tq/bluetoothdemo/Repository;", "getInstance", "()Lcom/tq/bluetoothdemo/Repository;", "setInstance", "(Lcom/tq/bluetoothdemo/Repository;)V", "initialize", "", "database", "Lcom/tq/bluetoothdemo/AppDatabase;", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final com.tq.bluetoothdemo.Repository getInstance() {
            return null;
        }
        
        public final void setInstance(@org.jetbrains.annotations.NotNull()
        com.tq.bluetoothdemo.Repository p0) {
        }
        
        public final void initialize(@org.jetbrains.annotations.NotNull()
        com.tq.bluetoothdemo.AppDatabase database) {
        }
        
        private Companion() {
            super();
        }
    }
}