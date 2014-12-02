package com.github.mread.config;

public class Use {
    public static void use(String rootPath, Router router) {
        router.configure(new Binder(rootPath));
    }
}
