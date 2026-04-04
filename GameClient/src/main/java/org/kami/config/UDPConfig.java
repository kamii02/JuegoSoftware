package org.kami.config;

public class UDPConfig  implements  IUDPConfig{
    private final IConfigReader configReader;

    public UDPConfig(IConfigReader configReader) {
        this.configReader = configReader;
    }

    @Override
    public int getPort() {
        return configReader.getInt("port");
    }
}
