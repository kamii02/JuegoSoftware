package org.kami.config;

public class UDPConfig implements IUDPConfig {

    private final IConfigReader configReader;
    private String playerId;

    public UDPConfig(IConfigReader configReader) {
        this.configReader = configReader;
        this.playerId = configReader.getString("id");
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    @Override
    public int getPort() {
        return configReader.getInt("port");
    }

    @Override
    public String getIp() {
        return configReader.getString("ip");
    }

    @Override
    public String getPlayerId() {
        return playerId;
    }


}