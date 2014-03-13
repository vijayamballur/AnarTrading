package org.vijay.common;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Vijay
 * @version
 */
public class RemoteIpFinder {

    public static String getIPByAddress(String address) {
        String ipAddress = null;
        try {
            InetAddress addr = InetAddress.getByName(address);
            ipAddress = addr.getHostAddress();

        } catch (UnknownHostException e) {
        }

        return ipAddress;
    }
}
