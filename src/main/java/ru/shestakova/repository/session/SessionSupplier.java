package ru.shestakova.repository.session;

import org.hibernate.Session;

public interface SessionSupplier {
    Session supplySession();
}
