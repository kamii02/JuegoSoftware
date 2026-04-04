package org.kami.config;

//Decide que valores usar
public class LayoutConfig implements ILayoutConfig {
    private final IConfigReader configReader;

    public LayoutConfig(IConfigReader configReader) {
        this.configReader = configReader;
    }


    @Override
    public String getMapFolder() {
        return configReader.getString("map_folder");
    }

    @Override
    public String getPlayerIconsFolder() {
        return configReader.getString("player_icons_folder");
    }

    @Override
    public int getHeight() {
        return configReader.getInt("height");
    }

    @Override
    public int getWidth() {
        return configReader.getInt("width");
    }

    @Override
    public int getPlayerSize() {
        return configReader.getInt("player_size");
    }

}
