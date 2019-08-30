package com.tq.utility;

public class NetUtil {

  public static class SocketAddressParser {
    private String socketAddress;
    private int port;
    private String address;
      
    public int port() {
      return port;
    }
      
    public String address() {
      return address;
    }
      
    private void parse() {
      String[] part = socketAddress.split(":");
      if (part.length != 2) {
        throw new RuntimeException("invalid socket address: " + socketAddress);
      }

      address = part[0];
      port = Integer.valueOf(part[1]);
    }

    public SocketAddressParser(String aSocketAddress) {
      socketAddress = aSocketAddress;
      parse();
    }
  }
}
