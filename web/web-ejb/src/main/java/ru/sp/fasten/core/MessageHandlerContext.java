package ru.sp.fasten.core;

import ru.sp.fasten.ejb.PersistenceService;
import javax.enterprise.concurrent.ManagedExecutorService;

public class MessageHandlerContext {

    public final ManagedExecutorService executorService;
    public final PersistenceService persistenceService;

    public MessageHandlerContext(ManagedExecutorService executorService, PersistenceService persistenceService) {
        this.executorService = executorService;
        this.persistenceService = persistenceService;
    }
}
