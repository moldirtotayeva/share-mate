package org.example.sharemate.user;

import java.util.HashMap;
import java.util.Map;

public class UserRepositoryImpl {
    private final Map<Long, User> users = new HashMap<>();
    private long nextId = 1;

}
