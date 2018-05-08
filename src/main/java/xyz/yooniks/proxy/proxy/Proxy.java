package xyz.yooniks.proxy.proxy;

import xyz.yooniks.proxy.SuperProxy;

public class Proxy {

    public static void main(String[] args) {
        final MCProxy loader = new SuperProxy();
        loader.onEnable();
    }
}
