package com.github.nenomm.tckts.id;

public interface IdGenerator {

    enum Type {
        FINE,
        COARSE;
    }

    Id getNextId(String clientId);

}
